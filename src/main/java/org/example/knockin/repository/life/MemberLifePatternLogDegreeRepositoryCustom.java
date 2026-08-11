package org.example.knockin.repository.life;

import org.example.knockin.entity.member.Member;

public interface MemberLifePatternLogDegreeRepositoryCustom {
    Long findMaxmemberLifePatternLogDegree(Member member);
}