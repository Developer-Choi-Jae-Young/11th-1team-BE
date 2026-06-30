package org.example.knockin.repository.life;

import java.util.List;
import org.example.knockin.entity.life.MemberLifePatternLog;

public interface MemberLifePatternLogRepositoryCustom {
    List<MemberLifePatternLog> findLatestLogsWithFetchByMemberId(Long memberId);
}
