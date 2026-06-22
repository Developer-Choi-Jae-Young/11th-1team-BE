package org.example.knockin.repository.life;

import org.example.knockin.dto.BoLifeStylePatternDetailDto;
import org.example.knockin.dto.BoLifeStylePatternListDto;
import org.example.knockin.dto.MetaLifestylePatternsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LifePatternRepositoryCustom {
    List<MetaLifestylePatternsDto.Response.PatternItem> findLifeStylePatterns();
    List<BoLifeStylePatternListDto.Response.PatternItem> findLifeStylePatternList(Pageable pageable);
    BoLifeStylePatternDetailDto.Response findLifeStylePattern(Long patternId);
}