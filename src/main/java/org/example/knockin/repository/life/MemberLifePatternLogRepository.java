package org.example.knockin.repository.life;

import java.util.List;
import org.example.knockin.entity.life.MemberLifePatternLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberLifePatternLogRepository extends JpaRepository<MemberLifePatternLog, Long>, MemberLifePatternLogRepositoryCustom {
    List<MemberLifePatternLog> findByMemberId(Long memberId);
}