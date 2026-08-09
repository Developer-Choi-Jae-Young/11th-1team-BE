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
public class BooleanScoreCalc implements LifePatternTypeScoreCalc {
    @Override
    public LifePatternType supports() {
        return LifePatternType.BOOLEAN;
    }

    @Override
    public Integer calculateSimilarity(LifePatternInformation source, LifePatternInformation target, LifePattern lifePattern) {
        return Objects.equals(source.getDvalue(), target.getDvalue()) ? 100 : 0;
    }
}
