package org.example.knockin.repository.life;

import org.example.knockin.entity.member.Member;

import java.util.Optional;

public interface MemberLifePatternLogDegreeRepositoryCustom {
    Optional<Long> findMaxmemberLifePatternLogDegree(Member member);
}