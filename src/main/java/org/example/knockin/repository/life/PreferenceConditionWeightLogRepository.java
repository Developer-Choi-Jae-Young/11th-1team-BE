package org.example.knockin.repository.life;

import org.example.knockin.entity.life.PreferenceConditionWeightLog;
import org.example.knockin.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceConditionWeightLogRepository extends JpaRepository<PreferenceConditionWeightLog, Long>, PreferenceConditionWeightLogRepositoryCustom {
    void deleteByMember(Member member);
}