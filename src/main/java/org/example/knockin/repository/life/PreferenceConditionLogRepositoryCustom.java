package org.example.knockin.repository.life;

import java.util.List;
import org.example.knockin.entity.life.PreferenceConditionLog;

public interface PreferenceConditionLogRepositoryCustom {
    List<PreferenceConditionLog> findLatestLogsWithFetchByMemberId(Long memberId, List<Long> lifePatternInformationIds);
}
