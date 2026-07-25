package org.example.knockin.service.impl;

import org.example.knockin.dto.*;
import org.example.knockin.entity.member.Member;
import org.example.knockin.entity.member.DevicePlatform;
import org.example.knockin.entity.member.MemberRole;
import org.example.knockin.entity.member.MemberState;
import org.example.knockin.entity.member.State;
import org.example.knockin.exception.BusinessException;
import org.example.knockin.exception.MemberErrorCode;
import org.example.knockin.repository.alarm.AlarmSettingRepository;
import org.example.knockin.repository.life.MemberLifePatternRepository;
import org.example.knockin.repository.member.BasicInformationRepository;
import org.example.knockin.repository.member.MemberRepository;
import org.example.knockin.repository.member.StateRepository;
import org.example.knockin.repository.room.RoomProfileRepository;
import org.example.knockin.auth.service.Oauth2DeleteFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("회원 서비스 테스트")
class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private StateRepository stateRepository;

    @Mock
    private Oauth2DeleteFactory oauth2DeleteFactory;

    @Mock
    private MemberLifePatternRepository memberLifePatternRepository;

    @Mock
    private BasicInformationRepository basicInformationRepository;

    @Mock
    private RoomProfileRepository roomProfileRepository;

    @Mock
    private AlarmSettingRepository alarmSettingRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

    @Test
    @DisplayName("백오피스 회원 목록 조회 성공 테스트")
    void findBackOfficeMemberListSuccessTest() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        BoMemberListDto.Response.MemberInfo info = new BoMemberListDto.Response.MemberInfo();
        info.setId(1L);
        info.setName("홍길동");
        info.setEmail("test@test.com");
        BoMemberListDto.Request request = new BoMemberListDto.Request();

        given(memberRepository.findBackOfficeMemberList(pageable, request)).willReturn(List.of(info));

        // when
        BoMemberListDto.Response response = memberService.findBackOfficeMemberList(pageable, request);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getMemberInfoList()).hasSize(1);
        assertThat(response.getMemberInfoList().get(0).getId()).isEqualTo(1L);
        assertThat(response.getMemberInfoList().get(0).getName()).isEqualTo("홍길동");
        verify(memberRepository).findBackOfficeMemberList(pageable, request);
    }

    @Test
    @DisplayName("백오피스 회원 상세 조회 성공 테스트")
    void findBackOfficeMemberSuccessTest() {
        // given
        Long memberId = 1L;
        BoMemberDetailDto.Response detail = new BoMemberDetailDto.Response();
        detail.setId(memberId);
        detail.setName("홍길동");

        given(memberRepository.findBackOfficeMember(memberId)).willReturn(detail);

        // when
        BoMemberDetailDto.Response response = memberService.findBackOfficeMember(memberId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(memberId);
        assertThat(response.getName()).isEqualTo("홍길동");
        verify(memberRepository).findBackOfficeMember(memberId);
    }

    @Test
    @DisplayName("회원 상태 수정 성공 테스트")
    void setMemberStateSuccessTest() {
        // given
        Member member = mock(Member.class);
        State state = spy(State.builder().states(MemberState.ACTIVE).build());

        given(stateRepository.findByMember(member)).willReturn(List.of(state));

        // when
        State result = memberService.setMemberState(member, MemberState.INACTIVE);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getStates()).isEqualTo(MemberState.INACTIVE);
        verify(stateRepository).findByMember(member);
        verify(state).changeState(MemberState.INACTIVE);
    }

    @Test
    @DisplayName("회원 권한(Role) 수정 성공 테스트")
    void setMemberAuthSuccessTest() {
        // given
        Member member = spy(Member.builder().role(MemberRole.USER).build());

        // when
        Member result = memberService.setMemberAuth(member, MemberRole.ADMIN);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getRole()).isEqualTo(MemberRole.ADMIN);
        verify(member).changeRole(MemberRole.ADMIN);
    }

    @Test
    @DisplayName("FCM 디바이스 정보를 모두 저장한다")
    void upsertFcmPropsSuccessTest() {
        // given
        Long memberId = 1L;
        Member member = Member.builder().id(memberId).build();
        FcmDto.Request request = new FcmDto.Request();
        request.setDeviceId("550e8400-e29b-41d4-a716-446655440000");
        request.setFcmToken("fcm-token");
        request.setPlatform(DevicePlatform.ANDROID);
        given(memberRepository.findById(memberId)).willReturn(Optional.of(member));

        // when
        FcmDto.Response response = memberService.upsertFcmProps(memberId, request);

        // then
        assertThat(response.getUpdatedAt()).isNotNull();
        assertThat(member.getDeviceId()).isEqualTo(request.getDeviceId());
        assertThat(member.getFcmToken()).isEqualTo(request.getFcmToken());
        assertThat(member.getPlatform()).isEqualTo(DevicePlatform.ANDROID);
        verify(memberRepository).findById(memberId);
    }

    @Test
    @DisplayName("존재하지 않는 회원의 FCM 디바이스 정보는 저장할 수 없다")
    void upsertFcmPropsFailsWhenMemberDoesNotExist() {
        // given
        Long memberId = 1L;
        FcmDto.Request request = new FcmDto.Request();
        request.setDeviceId("550e8400-e29b-41d4-a716-446655440000");
        request.setFcmToken("fcm-token");
        request.setPlatform(DevicePlatform.IOS);
        given(memberRepository.findById(memberId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> memberService.upsertFcmProps(memberId, request))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", MemberErrorCode.MEMBER_NOT_FOUND);
    }
}
