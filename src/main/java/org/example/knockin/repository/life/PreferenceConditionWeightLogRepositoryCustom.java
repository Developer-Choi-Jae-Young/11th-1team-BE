package org.example.knockin.repository.life;

import java.util.List;
import org.example.knockin.entity.life.PreferenceConditionWeightLog;

public interface PreferenceConditionWeightLogRepositoryCustom {
    List<PreferenceConditionWeightLog> findLatestLogsWithFetchByMemberId(Long memberId);
}
