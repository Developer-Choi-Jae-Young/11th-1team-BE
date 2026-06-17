package org.example.knockin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.knockin.dto.MatchListDto;
import org.example.knockin.entity.room.RoomProfileType;
import org.example.knockin.global.util.DateUtils;
import org.example.knockin.global.util.HasMemberId;
import org.example.knockin.global.util.StringUtils;
import org.example.knockin.repository.life.MemberLifePatternRepository;
import org.example.knockin.repository.life.PreferenceConditionRepository;
import org.example.knockin.repository.life.PreferenceConditionWeightRepository;
import org.example.knockin.repository.life.row.MatchingLifestyleRow;
import org.example.knockin.repository.life.row.MatchingPreferenceConditionRow;
import org.example.knockin.repository.life.row.MatchingPreferenceConditionWeightRow;
import org.example.knockin.repository.member.MemberInterestRepository;
import org.example.knockin.repository.member.MemberRepository;
import org.example.knockin.repository.member.row.MatchingListBasicInfoRow;
import org.example.knockin.repository.room.RoomOfferProfileRepository;
import org.example.knockin.repository.room.RoomSeekerProfileRepository;
import org.example.knockin.repository.room.row.MatchingOfferProfileRow;
import org.example.knockin.repository.room.row.MatchingSeekerProfileRow;
import org.example.knockin.repository.room.row.MatchingSeekerRegionRow;
import org.example.knockin.repository.room.row.MatchingSeekerRoomTypeRow;
import org.example.knockin.service.RoommateMatchingService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoommateMatchingServiceImpl implements RoommateMatchingService {
    private final MemberRepository memberRepository;
    private final MemberInterestRepository memberInterestRepository;
    private final RoomSeekerProfileRepository roomSeekerProfileRepository;
    private final RoomOfferProfileRepository roomOfferProfileRepository;
    private final MemberLifePatternRepository memberLifePatternRepository;
    private final PreferenceConditionRepository preferenceConditionRepository;
    private final PreferenceConditionWeightRepository preferenceConditionWeightRepository;

    @Override
    public Slice<MatchListDto.Response> findMatchingList(Long memberId, MatchListDto.Request request) {
        int size = request.getSize();
        List<Long> excludeMemberIds = resolveExcludeMemberIds(memberId, request);

        List<MatchingListBasicInfoRow> rawRows = memberRepository.findMatchingListBasicRow(excludeMemberIds, size + 1);
        boolean hasNext = rawRows.size() > size;
        List<MatchingListBasicInfoRow> matchingListBasicRow = rawRows.stream().limit(size).toList();
        List<Long> memberIds = matchingListBasicRow.stream().map(MatchingListBasicInfoRow::memberId).toList();

        if (memberIds.isEmpty()) {
            return new SliceImpl<>(List.of(), PageRequest.of(0, size), false);
        }

        List<MatchingOfferProfileRow> matchingOfferProfileRows = roomOfferProfileRepository.findAllOfferProfileByMemberIdIn(memberIds);

        List<MatchingSeekerProfileRow> matchingSeekerProfileRows = roomSeekerProfileRepository.findAllSeekerProfileByMemberIdIn(memberIds);
        List<MatchingSeekerRegionRow> matchingSeekerRegionRows = roomSeekerProfileRepository.findAllSeekerRegionByMemberIdIn(memberIds);
        List<MatchingSeekerRoomTypeRow> matchingSeekerRoomTypeRows = roomSeekerProfileRepository.findAllSeekerRoomTypeByMemberIdIn(memberIds);

        List<MatchingLifestyleRow> matchingLifestyleRows = memberLifePatternRepository.findAllLifestyleByMemberIdIn(memberIds);
        List<MatchingPreferenceConditionRow> matchingPreferenceConditionRows = preferenceConditionRepository.findAllPreferenceConditionByMemberIdIn(memberIds);
        List<MatchingPreferenceConditionWeightRow> matchingPreferenceConditionWeightRows = preferenceConditionWeightRepository.findAllPreferenceConditionWeightByMemberIdIn(memberIds);

        Map<Long, MatchingOfferProfileRow> offerMap = HasMemberId.toMapByMemberId(matchingOfferProfileRows);
        Map<Long, MatchingSeekerProfileRow> seekerMap = HasMemberId.toMapByMemberId(matchingSeekerProfileRows);

        Map<Long, List<String>> seekerRegionMap = HasMemberId.groupingByMemberId(
                matchingSeekerRegionRows,
                row -> StringUtils.parseToRegionFullName(
                        row.grandParentRegionName(),
                        row.parentRegionName(),
                        row.regionName()
                )
        );

        Map<Long, List<String>> seekerRoomTypeMap = HasMemberId.groupingByMemberId(
                matchingSeekerRoomTypeRows,
                MatchingSeekerRoomTypeRow::roomTypeName
        );

        Map<Long, List<MatchListDto.Lifestyle>> lifestyleMap = HasMemberId.groupingByMemberId(
                matchingLifestyleRows,
                this::toLifestyle
        );

        Map<Long, List<MatchListDto.Condition>> conditionMap = HasMemberId.groupingByMemberId(
                matchingPreferenceConditionRows,
                this::toCondition
        );

        Map<Long, List<MatchListDto.ConditionWeight>> conditionWeightMap = HasMemberId.groupingByMemberId(
                matchingPreferenceConditionWeightRows,
                this::toConditionWeight
        );

        Set<Long> likedMemberIds = findLikedMemberIds(memberId, memberIds);

        List<MatchListDto.Response> responses = matchingListBasicRow.stream()
                .map(row -> toResponse(
                        row,
                        offerMap,
                        seekerMap,
                        seekerRegionMap,
                        seekerRoomTypeMap,
                        lifestyleMap,
                        conditionMap,
                        conditionWeightMap,
                        likedMemberIds
                ))
                .toList();

        return new SliceImpl<>(
                responses,
                PageRequest.of(0, size),
                hasNext
        );
    }

    private List<Long> resolveExcludeMemberIds(Long memberId, MatchListDto.Request request) {
        List<Long> excludeMemberIds = new ArrayList<>();

        if (request != null && request.getExcludeMemberIds() != null) {
            excludeMemberIds.addAll(request.getExcludeMemberIds());
        }

        if (memberId != null) {
            excludeMemberIds.add(memberId);
        }

        return excludeMemberIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .toList();
    }

    private Set<Long> findLikedMemberIds(Long memberId, List<Long> memberIds) {
        if (memberId == null || memberIds.isEmpty()) {
            return Set.of();
        }

        return Set.copyOf(memberInterestRepository.findActiveReceiverIdsBySenderIdAndReceiverIds(memberId, memberIds));
    }

    private MatchListDto.Lifestyle toLifestyle(MatchingLifestyleRow row) {
        return MatchListDto.Lifestyle.builder()
                .lifestyleId(row.lifestyleId())
                .name(row.name())
                .value(row.value())
                .description(row.description())
                .type(row.type())
                .build();
    }

    private MatchListDto.Condition toCondition(MatchingPreferenceConditionRow row) {
        return MatchListDto.Condition.builder()
                .conditionId(row.conditionId())
                .name(row.name())
                .value(row.value())
                .description(row.description())
                .type(row.type())
                .build();
    }

    private MatchListDto.ConditionWeight toConditionWeight(MatchingPreferenceConditionWeightRow row) {
        return MatchListDto.ConditionWeight.builder()
                .conditionWeightId(row.conditionWeightId())
                .name(row.name())
                .build();
    }

    private MatchListDto.Response toResponse(
            MatchingListBasicInfoRow row,
            Map<Long, MatchingOfferProfileRow> offerMap,
            Map<Long, MatchingSeekerProfileRow> seekerMap,
            Map<Long, List<String>> seekerRegionMap,
            Map<Long, List<String>> seekerRoomTypeMap,
            Map<Long, List<MatchListDto.Lifestyle>> lifestyleMap,
            Map<Long, List<MatchListDto.Condition>> conditionMap,
            Map<Long, List<MatchListDto.ConditionWeight>> conditionWeightMap,
            Set<Long> likedMemberIds
    ) {
        Long candidateId = row.memberId();

        MatchListDto.OfferProfile offerProfile = null;
        MatchListDto.SeekerProfile seekerProfile = null;

        if (row.roomProfileType() == RoomProfileType.OFFER) {
            offerProfile = toOfferProfile(candidateId, offerMap);
        }

        if (row.roomProfileType() == RoomProfileType.SEEKER) {
            seekerProfile = toSeekerProfile(candidateId, seekerMap, seekerRegionMap, seekerRoomTypeMap);
        }

        return MatchListDto.Response.builder()
                .memberId(candidateId)
                .memberProfileImageUrl(row.memberProfileImageUrl())
                .memberName(row.memberName())
                .memberAge(DateUtils.calculateAge(row.birth()))
                .gender(row.gender())
                .isLike(likedMemberIds.contains(candidateId))
                .roomProfileType(row.roomProfileType())
                .offerProfile(offerProfile)
                .seekerProfile(seekerProfile)
                // TODO: 계산식 확정 후 변경
                .score(null)
                .lifeStyles(lifestyleMap.getOrDefault(candidateId, List.of()))
                .conditions(conditionMap.getOrDefault(candidateId, List.of()))
                .conditionWeights(conditionWeightMap.getOrDefault(candidateId, List.of()))
                .build();
    }

    private MatchListDto.OfferProfile toOfferProfile(Long memberId, Map<Long, MatchingOfferProfileRow> offerMap) {
        MatchingOfferProfileRow row = offerMap.get(memberId);

        if (row == null) {
            return null;
        }

        String regionFullName = StringUtils.parseToRegionFullName(
                row.grandParentRegionName(),
                row.parentRegionName(),
                row.regionName()
        );

        return MatchListDto.OfferProfile.builder()
                .deposit(row.deposit())
                .monthlyRent(row.monthlyRent())
                .regionFullName(regionFullName)
                .roomTypeName(row.roomTypeName())
                .build();
    }

    private MatchListDto.SeekerProfile toSeekerProfile(
            Long memberId,
            Map<Long, MatchingSeekerProfileRow> seekerMap,
            Map<Long, List<String>> seekerRegionMap,
            Map<Long, List<String>> seekerRoomTypeMap
    ) {
        MatchingSeekerProfileRow row = seekerMap.get(memberId);
        if (row == null) {
            return null;
        }

        return MatchListDto.SeekerProfile.builder()
                .minDeposit(row.minDeposit())
                .maxDeposit(row.maxDeposit())
                .minMonthlyRent(row.minMonthlyRent())
                .maxMonthlyRent(row.maxMonthlyRent())
                .roomTypeNames(seekerRoomTypeMap.getOrDefault(memberId, List.of()))
                .regionFullNames(seekerRegionMap.getOrDefault(memberId, List.of()))
                .build();
    }

}
