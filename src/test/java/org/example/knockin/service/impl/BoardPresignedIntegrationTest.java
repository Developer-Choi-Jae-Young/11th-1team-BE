package org.example.knockin.service.impl;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.example.knockin.board.dto.BoardPreSignedDto;
import org.example.knockin.board.repository.RoommateBoardRepository;
import org.example.knockin.board.repository.RoommateBoardFileRepository;
import org.example.knockin.board.service.impl.RoommateBoardServiceImpl;
import org.example.knockin.board.service.impl.RoommateBoardFileServiceImpl;
import org.example.knockin.board.service.impl.RoommateBoardOptionServiceImpl;
import org.example.knockin.board.service.impl.RoommateBoardInterestServiceImpl;
import org.example.knockin.global.config.JpaConfig;
import org.example.knockin.global.config.QueryDslConfig;
import org.example.knockin.global.config.RoommateBoardPolicy;
import org.example.knockin.global.exception.BusinessException;
import org.example.knockin.global.exception.FileErrorCode;
import org.example.knockin.mate.service.impl.MetaServiceImpl;
import org.example.knockin.member.entity.Member;
import org.example.knockin.member.entity.MemberRole;
import org.example.knockin.member.service.impl.MemberServiceImpl;
import org.example.knockin.meta.entity.Region;
import org.example.knockin.meta.repository.FileRepository;
import org.example.knockin.meta.service.FileUploadService;
import org.example.knockin.meta.service.impl.FileServiceImpl;
import org.example.knockin.room.entity.RoomType;
import org.example.knockin.verification.entity.LoginProviderType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.transaction.TestTransaction;

@DataJpaTest(properties = "spring.jpa.show-sql=false")
@ActiveProfiles("test")
@Import({JpaConfig.class, QueryDslConfig.class, RoommateBoardPolicy.class, RoommateBoardServiceImpl.class,
        RoommateBoardFileServiceImpl.class, FileServiceImpl.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BoardPresignedIntegrationTest {
    @Autowired RoommateBoardServiceImpl service;
    @Autowired RoommateBoardRepository boards;
    @Autowired RoommateBoardFileRepository boardFiles;
    @Autowired FileRepository files;
    @Autowired EntityManager em;
    @Autowired RoommateBoardPolicy policy;
    @MockitoBean MemberServiceImpl members;
    @MockitoBean MetaServiceImpl meta;
    @MockitoBean FileUploadService storage;
    @MockitoBean RoommateBoardOptionServiceImpl options;
    @MockitoBean RoommateBoardInterestServiceImpl interests;
    @MockitoBean org.example.knockin.util.service.RoommateScoreService scores;
    @MockitoBean org.example.knockin.life.service.impl.PreferenceConditionServiceImpl preferences;
    @MockitoBean org.example.knockin.life.service.impl.MemberLifePatternService patterns;
    @MockitoBean org.example.knockin.verification.service.impl.AuthenticationServiceImpl authentication;
    @MockitoBean org.example.knockin.meta.service.impl.AlarmServiceImpl alarms;
    @MockitoBean org.example.knockin.meta.service.impl.PushNotificationServiceImpl notifications;
    @MockitoBean org.example.knockin.declaration.service.impl.DeclarationServiceImpl declarations;
    private Member member;
    private RoomType roomType;
    private Region region;

    @BeforeEach
    void setUp() {
        policy.setImageMaxCount(10);
        policy.setThumbnailImageMaxCount(1);
        member = Member.builder().providerType(LoginProviderType.KAKAO).providerId(UUID.randomUUID().toString())
                .role(MemberRole.USER).build();
        roomType = RoomType.builder().name("원룸").build();
        region = Region.builder().name("서초동").scope(3).build();
        em.persist(member); em.persist(roomType); em.persist(region);
        when(members.findById(member.getId())).thenReturn(Optional.of(member));
        when(meta.findByRoomTypeId(roomType.getId())).thenReturn(roomType);
        when(meta.findByRegionId(region.getId())).thenReturn(Optional.of(region));
    }

    @Test
    void persistsBoardAndFileMetadataWithoutReuploadingImageBytes() throws Exception {
        when(storage.getUploadedImageUrl("owned-first.jpg", member.getId())).thenReturn("https://images.example.com/owned-first.jpg");
        var request = request(List.of(new BoardPreSignedDto.Request.FileDto("owned-first.jpg", "room.jpeg", true)));
        var response = service.saveBoardPreSigned(request, member.getId());
        assertThat(response.getUpdatedAt()).isNotNull();
        TestTransaction.flagForCommit();
        TestTransaction.end();
        assertThat(boards.count()).isEqualTo(1);
        assertThat(files.count()).isEqualTo(1);
        assertThat(boardFiles.count()).isEqualTo(1);
        var file = files.findAll().getFirst();
        assertThat(file.getOriginalFileName()).isEqualTo("room.jpeg");
        assertThat(file.getFileExt()).isEqualTo("jpg");
        assertThat(file.getSavedFileName()).isEqualTo("https://images.example.com/owned-first.jpg");
        verify(storage, never()).uploadImage(any());
    }

    @Test
    void secondMissingImageRollsBackBoardAndFirstFileButKeepsR2ObjectsForRetry() throws Exception {
        when(storage.getUploadedImageUrl("owned-first.jpg", member.getId())).thenReturn("https://images.example.com/owned-first.jpg");
        when(storage.getUploadedImageUrl("owned-second.jpg", member.getId())).thenThrow(new BusinessException(FileErrorCode.FILE_NOT_FOUND));
        var request = request(List.of(new BoardPreSignedDto.Request.FileDto("owned-first.jpg", "first.jpg", true),
                new BoardPreSignedDto.Request.FileDto("owned-second.jpg", "second.jpg", false)));
        assertThatThrownBy(() -> service.saveBoardPreSigned(request, member.getId())).isInstanceOf(BusinessException.class);
        TestTransaction.end();
        assertThat(boards.count()).isZero();
        assertThat(files.count()).isZero();
        assertThat(boardFiles.count()).isZero();
        verify(storage, never()).deleteImage(any());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void commitsBoardWithoutImagesAndDoesNotCallStorage(boolean omitImages) {
        var request = request(omitImages ? null : List.of());
        var response = service.saveBoardPreSigned(request, member.getId());
        assertThat(response.getUpdatedAt()).isNotNull();
        TestTransaction.flagForCommit();
        TestTransaction.end();
        assertThat(boards.count()).isEqualTo(1);
        assertThat(files.count()).isZero();
        assertThat(boardFiles.count()).isZero();
        verifyNoInteractions(storage);
    }

    private BoardPreSignedDto.Request request(List<BoardPreSignedDto.Request.FileDto> images) {
        var r = new BoardPreSignedDto.Request();
        r.setTitle("제목"); r.setContents("내용"); r.setDeposit(1000); r.setMountlyRent(50); r.setManagementCost(5);
        r.setRoomTypeId(roomType.getId()); r.setRegionId(region.getId()); r.setImages(images);
        return r;
    }
}
