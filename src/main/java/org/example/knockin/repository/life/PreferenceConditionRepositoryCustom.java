package org.example.knockin.repository.life;


import java.util.List;
import org.example.knockin.dto.BoardDetailDto;
import org.example.knockin.repository.life.row.MatchingPreferenceConditionRow;

public interface PreferenceConditionRepositoryCustom {
    List<BoardDetailDto.Response.Condition> getConditionDtoByMemberId(Long memberId);

    List<MatchingPreferenceConditionRow> findAllPreferenceConditionByMemberIdIn(List<Long> memberIds);

    List<Long> findLifeInformationIdByMemberId(Long memberId);
}
