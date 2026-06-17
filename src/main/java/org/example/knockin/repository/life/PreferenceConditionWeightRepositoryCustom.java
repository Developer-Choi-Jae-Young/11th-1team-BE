package org.example.knockin.repository.life;

import java.util.List;
import org.example.knockin.dto.BoardDetailDto.Response.ConditionWeight;
import org.example.knockin.repository.life.row.MatchingPreferenceConditionWeightRow;

public interface PreferenceConditionWeightRepositoryCustom {
    List<ConditionWeight> getConditionWeightDtoByMemberId(Long memberId);

    List<MatchingPreferenceConditionWeightRow> findAllPreferenceConditionWeightByMemberIdIn(List<Long> memberIds);
}
