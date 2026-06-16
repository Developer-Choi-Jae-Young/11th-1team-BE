package org.example.knockin.repository.life.row;

import org.example.knockin.global.util.HasMemberId;

public record MatchingPreferenceConditionWeightRow(
        Long memberId,
        Long conditionWeightId,
        String name
) implements HasMemberId {
}