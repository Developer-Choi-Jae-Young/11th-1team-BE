package org.example.knockin.repository.life;

import org.example.knockin.entity.life.LifePatternInformation;

import java.util.List;

public interface LifePatternInformationRepositoryCustom {
    List<LifePatternInformation> findByLifeStyles(List<Long> lifeStyles);
}