package org.example.knockin.repository.agreement.Impl;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.agreement.AgreementLog;
import org.example.knockin.repository.agreement.AgreementLogRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import static org.example.knockin.entity.agreement.QAgreementLog.agreementLog;
import static org.example.knockin.entity.agreement.QAgreement.agreement;
import static org.example.knockin.entity.agreement.QAgreementType.agreementType;

@Repository
@RequiredArgsConstructor
public class AgreementLogRepositoryImpl implements AgreementLogRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<AgreementLog> findByAgreementLogIsCurrent(List<Long> agreementIds) {
        return jpaQueryFactory.selectFrom(agreementLog).join(agreementLog.agreement, agreement).where(agreement.id.in(agreementIds), agreementLog.isCurrent.eq(true)).fetch();
    }

    @Override
    public List<AgreementLog> findByAgreemnetIsCurrent(boolean isCurrent, Pageable pageable, Long agreementTypeId) {
        return jpaQueryFactory.selectFrom(agreementLog)
                .where(agreementLog.id.in(JPAExpressions.select(agreementLog.id)
                                        .from(agreementLog)
                                        .join(agreementLog.agreement, agreement)
                                        .join(agreement.type, agreementType)
                                        .where(agreementType.id.eq(agreementTypeId), agreement.isDeleted.eq(false))
                                        .offset(pageable.getOffset()).limit(pageable.getPageSize()))).fetch();
    }
}