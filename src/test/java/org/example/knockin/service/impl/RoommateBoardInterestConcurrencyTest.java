package org.example.knockin.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.example.knockin.entity.auth.LoginProviderType;
import org.example.knockin.entity.board.RoommateBoard;
import org.example.knockin.entity.board.RoommateBoardInterest;
import org.example.knockin.entity.member.Member;
import org.example.knockin.entity.member.MemberRole;
import org.example.knockin.entity.room.Region;
import org.example.knockin.entity.room.RoomType;
import org.example.knockin.repository.board.RoommateBoardInterestRepository;
import org.example.knockin.repository.board.RoommateBoardRepository;
import org.example.knockin.repository.member.MemberRepository;
import org.example.knockin.repository.room.RegionRepository;
import org.example.knockin.repository.room.RoomTypeRepository;
import org.example.knockin.service.RoommateBoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("룸메이트 게시글 좋아요 동시성")
class RoommateBoardInterestConcurrencyTest {
    @Autowired
    private RoommateBoardService roommateBoardService;

    @Autowired
    private RoommateBoardInterestRepository roommateBoardInterestRepository;

    @Autowired
    private RoommateBoardRepository roommateBoardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    @DisplayName("같은 회원이 같은 게시글을 동시에 좋아요해도 관심 row는 하나만 생성된다")
    void likeBoardCreatesOnlyOneInterestWhenRequestedConcurrently() throws Exception {
        TestData testData = transactionTemplate.execute(status -> {
            Member member = memberRepository.save(Member.builder()
                    .providerType(LoginProviderType.KAKAO)
                    .providerId("concurrency-member")
                    .role(MemberRole.USER)
                    .isDelete(false)
                    .build());
            RoomType roomType = roomTypeRepository.save(createRoomType("원룸"));
            Region region = regionRepository.save(createRegion("역삼동", 3, null));
            RoommateBoard board = roommateBoardRepository.save(RoommateBoard.builder()
                    .member(member)
                    .title("동시성 테스트 게시글")
                    .contents("동시에 좋아요를 누르는 상황")
                    .deposit(1_000)
                    .monthlyRent(50)
                    .managementCost(10)
                    .roomType(roomType)
                    .region(region)
                    .comeableDate(LocalDateTime.of(2026, 7, 1, 9, 0))
                    .build());
            return new TestData(board.getId(), member.getId());
        });

        int threadCount = 20;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch readyLatch = new CountDownLatch(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);

        List<Callable<Void>> tasks = java.util.stream.IntStream.range(0, threadCount)
                .mapToObj(i -> (Callable<Void>) () -> {
                    readyLatch.countDown();
                    startLatch.await();
                    roommateBoardService.likeBoard(testData.boardId(), testData.memberId());
                    return null;
                })
                .toList();

        List<Future<Void>> futures = tasks.stream()
                .map(executorService::submit)
                .toList();

        readyLatch.await();
        startLatch.countDown();
        for (Future<Void> future : futures) {
            future.get();
        }
        executorService.shutdown();

        List<RoommateBoardInterest> interests = roommateBoardInterestRepository.findAllByRoommateBoardIdAndMemberId(
                testData.boardId(), testData.memberId());

        assertThat(interests).hasSize(1);
        assertThat(interests.getFirst().getIsDeleted()).isFalse();
    }

    private RoomType createRoomType(String name) {
        RoomType roomType = newInstance(RoomType.class);
        ReflectionTestUtils.setField(roomType, "name", name);
        ReflectionTestUtils.setField(roomType, "isDeleted", false);
        return roomType;
    }

    private Region createRegion(String name, Integer scope, Region parent) {
        Region region = newInstance(Region.class);
        ReflectionTestUtils.setField(region, "name", name);
        ReflectionTestUtils.setField(region, "scope", scope);
        ReflectionTestUtils.setField(region, "parent", parent);
        return region;
    }

    private <T> T newInstance(Class<T> type) {
        try {
            Constructor<T> constructor = type.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new IllegalStateException("테스트 엔티티 생성에 실패했습니다.", e);
        }
    }

    private record TestData(Long boardId, Long memberId) {
    }
}
