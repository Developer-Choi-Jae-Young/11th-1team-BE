package org.example.knockin.repository.life;

import org.example.knockin.entity.member.Member;

import java.util.Optional;

public interface PreferenceConditionWeightLogDegreeRepositoryCustom {
    Optional<Long> findMaxPreferenceConditionWeightLogDegree(Member member);
}
