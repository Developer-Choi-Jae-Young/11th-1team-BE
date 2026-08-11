package org.example.knockin.repository.life;

import org.example.knockin.entity.life.PreferenceConditionLogDegree;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceConditionLogDegreeRepository extends JpaRepository<PreferenceConditionLogDegree, Long>, PreferenceConditionLogDegreeRepositoryCustom {
}