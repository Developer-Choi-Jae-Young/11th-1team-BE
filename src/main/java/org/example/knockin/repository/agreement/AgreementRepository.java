package org.example.knockin.repository.agreement;

import org.example.knockin.entity.agreement.Agreement;
import org.example.knockin.entity.agreement.AgreementType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AgreementRepository extends JpaRepository<Agreement, Long>, AgreementRepositoryCustom {
    List<Agreement> findAllByIsDeleted(Boolean isDeleted);

    Collection<Agreement> findByType(AgreementType type);
}