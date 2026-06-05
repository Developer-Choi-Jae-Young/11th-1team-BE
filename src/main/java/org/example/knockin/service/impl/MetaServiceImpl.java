package org.example.knockin.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.agreement.AgreementLog;
import org.example.knockin.entity.life.LifePatternInformation;
import org.example.knockin.repository.agreement.AgreementLogRepository;
import org.example.knockin.repository.life.LifePatternInformationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MetaServiceImpl {
    private final AgreementLogRepository agreementLogRepository;
    private final LifePatternInformationRepository lifePatternInformationRepository;

    public List<AgreementLog> findByAgreementLogIsCurrent(List<Long> agreementIds) {
        return agreementLogRepository.findByAgreementLogIsCurrent(agreementIds);
    }

    public List<LifePatternInformation> findByLifeStyle(List<Long> lifeStyles) {
        return lifePatternInformationRepository.findByLifeStyles(lifeStyles);
    }
}
