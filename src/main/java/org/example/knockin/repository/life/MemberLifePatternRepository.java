package org.example.knockin.repository.life;

import org.example.knockin.entity.life.MemberLifePattern;
import org.example.knockin.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberLifePatternRepository extends JpaRepository<MemberLifePattern, Long>, MemberLifePatternRepositoryCustom {
    List<MemberLifePattern> findByMember(Member member);
}