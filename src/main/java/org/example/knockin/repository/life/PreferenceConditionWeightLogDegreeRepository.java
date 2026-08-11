package org.example.knockin.repository.life;

import org.example.knockin.entity.life.PreferenceConditionWeightLogDegree;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceConditionWeightLogDegreeRepository extends JpaRepository<PreferenceConditionWeightLogDegree, Long>, PreferenceConditionWeightLogDegreeRepositoryCustom {
}