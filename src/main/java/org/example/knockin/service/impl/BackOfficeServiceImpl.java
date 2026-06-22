package org.example.knockin.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.knockin.dto.BoTermsDetailDto;
import org.example.knockin.dto.BoTermsDto;
import org.example.knockin.dto.BoTermsListDto;
import org.example.knockin.entity.agreement.Agreement;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BackOfficeServiceImpl {
    private final AgreementServiceImpl agreementService;

    @Transactional
    public BoTermsDto.Response saveTerms(BoTermsDto.Request request) {
        agreementService.saveAgreement(Agreement.builder().title(request.getTitle()).contents(request.getContents()).isRequired(request.getIsRequired()).build());
        return BoTermsDto.Response.builder().updatedAt(LocalDateTime.now()).build();
    }

    @Transactional
    public BoTermsDto.Response modifyTerms(BoTermsDto.Request request, Long termsId) {
        agreementService.modifyTemporaryAgreement(Agreement.builder().title(request.getTitle()).contents(request.getContents()).isRequired(request.getIsRequired()).build(), termsId);
        return BoTermsDto.Response.builder().updatedAt(LocalDateTime.now()).build();
    }

    public BoTermsListDto.Response findTermsList(Pageable pageable) {
        List<BoTermsListDto.Response.TermsItem> termsItemList = agreementService.findAgreementList(pageable).stream().map(item ->
                BoTermsListDto.Response.TermsItem.builder().title(item.getTitle()).createAt(item.getCreatedAt()).id(item.getId()).build()).toList();
        return BoTermsListDto.Response.builder().terms(termsItemList).build();
    }

    public BoTermsDetailDto.Response findTerms(Long termsId) {
        Agreement agreement = agreementService.findAgreement(termsId);
        return BoTermsDetailDto.Response.builder().id(agreement.getId()).title(agreement.getTitle()).contents(agreement.getContents()).createAt(agreement.getCreatedAt()).build();
    }

    @Transactional
    public BoTermsDto.Response deleteTerms(Long termsId) {
        agreementService.deleteAgreement(termsId);
        return BoTermsDto.Response.builder().updatedAt(LocalDateTime.now()).build();
    }

    @Transactional
    public BoTermsDto.Response modifyLastTerms(BoTermsDto.Request request, Long termsId) {
        agreementService.modifyAgreement(Agreement.builder().title(request.getTitle()).contents(request.getContents()).isRequired(request.getIsRequired()).build(), termsId);
        return BoTermsDto.Response.builder().updatedAt(LocalDateTime.now()).build();
    }
}
