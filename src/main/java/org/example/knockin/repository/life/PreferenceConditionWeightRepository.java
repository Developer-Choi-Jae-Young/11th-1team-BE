package org.example.knockin.repository.life;

import org.example.knockin.entity.life.PreferenceConditionWeight;
import org.example.knockin.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PreferenceConditionWeightRepository extends JpaRepository<PreferenceConditionWeight, Long>, PreferenceConditionWeightRepositoryCustom {
    void deleteByMember(Member member);

    List<PreferenceConditionWeight> findByMember(Member member);

    List<PreferenceConditionWeight> findAllByMemberIn(Collection<Member> members);

    List<PreferenceConditionWeight> findAllByMember(Member member);
}