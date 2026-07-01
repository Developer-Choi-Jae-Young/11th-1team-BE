package org.example.knockin.service.impl;
 
import org.example.knockin.entity.member.Member;
import org.example.knockin.entity.member.MemberPrivacy;
import org.example.knockin.repository.member.MemberPrivacyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
 
import java.util.List;
 
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
 
@ExtendWith(MockitoExtension.class)
class MemberPrivacyServiceImplTest {
 
    @Mock
    private MemberPrivacyRepository memberPrivacyRepository;
 
    @InjectMocks
    private MemberPrivacyServiceImpl memberPrivacyService;
 
    @Test
    @DisplayName("회원별 공개범위 설정 조회 테스트")
    void findByMemberTest() {
        Member member = mock(Member.class);
        MemberPrivacy privacy = mock(MemberPrivacy.class);
        given(memberPrivacyRepository.findByMember(member)).willReturn(List.of(privacy));
 
        List<MemberPrivacy> result = memberPrivacyService.findByMember(member);
 
        assertThat(result).hasSize(1);
        verify(memberPrivacyRepository).findByMember(member);
    }
 
    @Test
    @DisplayName("공개범위 설정 저장 테스트")
    void saveTest() {
        MemberPrivacy privacy = mock(MemberPrivacy.class);
        given(memberPrivacyRepository.save(any(MemberPrivacy.class))).willReturn(privacy);
 
        MemberPrivacy result = memberPrivacyService.save(privacy);
 
        assertThat(result).isEqualTo(privacy);
        verify(memberPrivacyRepository).save(privacy);
    }
}
