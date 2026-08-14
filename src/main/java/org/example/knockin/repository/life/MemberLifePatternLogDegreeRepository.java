package org.example.knockin.repository.life;

import org.example.knockin.entity.life.MemberLifePatternLogDegree;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberLifePatternLogDegreeRepository extends JpaRepository<MemberLifePatternLogDegree, Long>, MemberLifePatternLogDegreeRepositoryCustom {
}