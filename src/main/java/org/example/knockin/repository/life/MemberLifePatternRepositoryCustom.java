package org.example.knockin.repository.life;

import java.util.List;
import org.example.knockin.dto.BoardDetailDto;
import org.example.knockin.entity.member.Member;
import org.example.knockin.repository.life.row.MatchingLifestyleRow;

public interface MemberLifePatternRepositoryCustom {
    List<BoardDetailDto.Response.Lifestyle> getLifeStyleDto(Long memberId);
    boolean isExsitLifeStyle(Member member);
    List<MatchingLifestyleRow> findAllLifestyleByMemberIdIn(List<Long> memberIds);
}