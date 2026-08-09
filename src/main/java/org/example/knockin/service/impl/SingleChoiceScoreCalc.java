package org.example.knockin.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.life.LifePattern;
import org.example.knockin.entity.life.LifePatternInformation;
import org.example.knockin.entity.life.LifePatternType;
import org.example.knockin.repository.life.LifePatternInformationRepository;
import org.example.knockin.service.LifePatternTypeScoreCalc;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SingleChoiceScoreCalc implements LifePatternTypeScoreCalc {
    private final LifePatternInformationRepository lifePatternInformationRepository;
    private static final int TOTAL_POINT = 100;

    @Override
    public LifePatternType supports() {
        return LifePatternType.SINGLE_CHOICE;
    }

    @Override
    public Integer calculateSimilarity(LifePatternInformation source, LifePatternInformation target, LifePattern lifePattern) {
        List<LifePatternInformation> lifePatternInformations = lifePatternInformationRepository.findByLifePattern(lifePattern);
        int min = lifePatternInformations.stream().mapToInt(item -> Integer.parseInt(item.getDvalue())).min().orElse(0);
        int max = lifePatternInformations.stream().mapToInt(item -> Integer.parseInt(item.getDvalue())).max().orElse(0);

        double lifePatternPartPoint = (double) TOTAL_POINT / (max - min);
        int diff = Math.abs(Integer.parseInt(source.getDvalue()) - Integer.parseInt(target.getDvalue()));

        return (int) (TOTAL_POINT - lifePatternPartPoint * diff);
    }
}
