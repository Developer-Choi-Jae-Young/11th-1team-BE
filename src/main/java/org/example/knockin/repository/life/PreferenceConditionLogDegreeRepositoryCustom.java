package org.example.knockin.repository.life;

import org.example.knockin.entity.member.Member;

import java.util.Optional;

public interface PreferenceConditionLogDegreeRepositoryCustom {
    Optional<Long> findMaxPreferenceConditionLogDegree(Member member);
}
