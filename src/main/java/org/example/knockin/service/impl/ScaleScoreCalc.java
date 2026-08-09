package org.example.knockin.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.life.LifePattern;
import org.example.knockin.entity.life.LifePatternInformation;
import org.example.knockin.entity.life.LifePatternType;
import org.example.knockin.service.LifePatternTypeScoreCalc;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ScaleScoreCalc implements LifePatternTypeScoreCalc {
    @Override
    public Integer calculateSimilarity(LifePatternInformation source, LifePatternInformation target, LifePattern lifePattern) {
        return Objects.equals(source.getId(), target.getId()) ? 100 : 0;
    }

    @Override
    public LifePatternType supports() {
        return LifePatternType.SCALE;
    }
}
