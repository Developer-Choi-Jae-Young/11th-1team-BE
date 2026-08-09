package org.example.knockin.service;

import org.example.knockin.entity.life.LifePattern;
import org.example.knockin.entity.life.LifePatternInformation;
import org.example.knockin.entity.life.LifePatternType;

public interface LifePatternTypeScoreCalc {
    Integer calculateSimilarity(LifePatternInformation source, LifePatternInformation target, LifePattern lifePattern);
    LifePatternType supports();
}
