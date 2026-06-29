package org.example.knockin.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.knockin.dto.*;
import org.example.knockin.entity.agreement.Agreement;
import org.example.knockin.entity.agreement.AgreementLog;
import org.example.knockin.entity.life.LifePattern;
import org.example.knockin.entity.life.LifePatternInformation;
import org.example.knockin.entity.room.Region;
import org.example.knockin.entity.room.RoomExtraOption;
import org.example.knockin.entity.room.RoomType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class MetaServiceImpl {
    private final LifeStyleServiceImpl lifeStyleService;
    private final RegionServiceImpl regionService;
    private final RoomTypeServiceImpl roomTypeService;
    private final RoomExtraOptionServiceImpl roomExtraOptionService;
    private final AgreementServiceImpl agreementService;
    private final SearchServiceImpl searchService;

    public List<AgreementLog> findByAgreementLogIsCurrent(List<Long> agreementIds) {
        return agreementService.findByAgreementLogIsCurrent(agreementIds);
    }

    public List<LifePatternInformation> findByLifeStyle(List<Long> lifeStyles) {
        return lifeStyleService.findByLifeStyles(lifeStyles);
    }

    public List<LifePattern> findLifePatternByLifeStyle(List<Long> lifeStyles) {
        return lifeStyleService.findAllById(lifeStyles);
    }

    public Optional<Region> findByRegionId(Long id) {
        return regionService.findById(id);
    }

    public List<Region> findByRegions(List<Long> regions) {
        return regionService.findByRegions(regions);
    }

    public List<Region> findByRegionAndChild(List<Long> regionIds) {
        return regionService.findByIdInWithChild(regionIds);
    }

    public RoomType findByRoomTypeId(Long roomTypeId) {
        return roomTypeService.findRoomType(roomTypeId);
    }

    public List<RoomType> findByRoomTypes(List<Long> roomTypes) {
        return roomTypeService.findByRoomTypes(roomTypes);
    }

    public List<RoomExtraOption> findRoomExtraOptionsByIdIn(List<Long> ids) {
        return roomExtraOptionService.findAllById(ids);
    }

    public TermsListDto.Response findTermList() {
        List<TermsListDto.Response.TermsItem> termsItemList = agreementService.findByAgreementsIsCurrentAndIsDeleted().stream().map(item ->
            TermsListDto.Response.TermsItem.builder().id(item.getId()).title(item.getTitle()).build()).toList();
        return TermsListDto.Response.builder().terms(termsItemList).build();
    }

    public TermsDetailDto.Response findTermDetail(Long termsId) {
        Agreement agreement = agreementService.findAgreement(termsId);
        return TermsDetailDto.Response.builder().id(agreement.getId()).contents(agreement.getContents()).build();
    }

    public PopularSearchDto.Response findPopSearch() {
        List<PopularSearchDto.Response.RankItem> rankItems = searchService.findPopSearch();
        IntStream.range(0, rankItems.size()).forEach(i -> rankItems.get(i).setId(i + 1L));
        return PopularSearchDto.Response.builder().rank(rankItems).build();
    }

    public MetaLifestylePatternsDto.Response findLifeStylePatterns() {
        return MetaLifestylePatternsDto.Response.builder().patterns(lifeStyleService.findLifeStylePatterns()).build();
    }

    public MetaRoomTypesDto.Response findRoomTypes() {
        return MetaRoomTypesDto.Response.builder()
                .roomType(roomTypeService.findAllByIsDeleted(false).stream().map(item ->
                        MetaRoomTypesDto.Response.RoomTypeItem.builder().id(item.getId()).name(item.getName()).build()).toList())
                .build();
    }

    public MetaRegionsDto.Response findRegions() {
        return MetaRegionsDto.Response.builder()
                .region(regionService.findAll().stream().map(item ->
                        MetaRegionsDto.Response.RegionItem.builder().id(item.getId()).name(item.getName()).parentId(item.getParent() != null ? item.getParent().getId() : null).build()).toList())
                .build();
    }

    public MetaRoomAddOptionsDto.Response findRoomAddOptions() {
        return MetaRoomAddOptionsDto.Response.builder()
                .roomAddOption(roomExtraOptionService.findAllByIsDeleted(false).stream().map(item ->
                        MetaRoomAddOptionsDto.Response.RoomAddOptionItem.builder().id(item.getId()).name(item.getName()).build()).toList()).build();
    }
}
