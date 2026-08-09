package org.example.knockin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.knockin.dto.Compatibility;
import org.example.knockin.dto.Compatibility.LifeStyleInfo;
import org.example.knockin.entity.chat.ChattingRequired;
import org.example.knockin.entity.chat.ChattingScore;
import org.example.knockin.entity.life.*;
import org.example.knockin.entity.room.MyRoommate;
import org.example.knockin.entity.room.RoommateScore;
import org.example.knockin.exception.BusinessException;
import org.example.knockin.exception.EtcErrorCode;
import org.example.knockin.repository.life.*;
import org.example.knockin.repository.life.row.LifePatternInformationValueRow;
import org.example.knockin.repository.life.row.MatchingLifestyleRow;
import org.example.knockin.repository.life.row.MatchingPreferenceConditionRow;
import org.example.knockin.repository.life.row.MatchingPreferenceConditionWeightRow;
import org.example.knockin.service.LifePatternTypeScoreCalc;
import org.example.knockin.service.RoommateScoreService;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class JavaRoommateScoreV2Service extends RoommateScoreService {
    private final MemberLifePatternRepository memberLifePatternRepository;
    private final PreferenceConditionRepository preferenceConditionRepository;
    private final PreferenceConditionWeightRepository preferenceConditionWeightRepository;
    private final LifePatternInformationRepository lifePatternInformationRepository;
    private final RoommateScorePolicy scorePolicy;
    private final MemberLifePatternLogRepository memberLifePatternLogRepository;
    private final PreferenceConditionLogRepository preferenceConditionLogRepository;
    private final PreferenceConditionWeightLogRepository preferenceConditionWeightLogRepository;
    private final List<LifePatternTypeScoreCalc> lifePatternTypeScoreCalcList;

    private static final Long TOTAL_POINT = 100L;
    private static final Integer NON_PREFERENCE_WEIGHT = 1;
    private static final Integer PREFERENCE_WEIGHT = 2;

    @Override
    public Map<Long, Compatibility> calculateScores(Long requesterId, List<Long> targetMemberIds) {
        if (requesterId == null || targetMemberIds == null || targetMemberIds.isEmpty()) return Map.of();

        List<Long> memberIds = includeRequester(targetMemberIds, requesterId);
        List<MatchingLifestyleRow> lifestyleRows = memberLifePatternRepository.findAllLifestyleByMemberIdIn(memberIds);
        List<MatchingPreferenceConditionRow> conditionRows = preferenceConditionRepository.findAllPreferenceConditionByMemberIdIn(memberIds);
        List<MatchingPreferenceConditionWeightRow> conditionWeightRows = preferenceConditionWeightRepository.findAllPreferenceConditionWeightByMemberIdIn(memberIds);

        return null;
    }

    @Override
    public Map<Long, Integer> calculateSimpleScores(Long requesterId, List<Long> targetMemberIds) {
        return null;
    }

    @Override
    public Compatibility calculateScore(Long requesterId, Long targetMemberId) {
        if (targetMemberId == null) return null;
        return calculateScores(requesterId, List.of(targetMemberId)).get(targetMemberId);
    }

    @Override
    public Integer calculateSimpleScore(Long requesterId, Long targetMemberId) {
        Compatibility compatibility = calculateScore(requesterId, targetMemberId);
        return compatibility == null ? null : compatibility.getTotalScore();
    }

    @Override
    public List<ChattingScore> createChattingScores(ChattingRequired chattingRequired) {
        return null;
    }

    @Override
    public List<RoommateScore> createRoommateScores(MyRoommate myRoommate) {
        return null;
    }

    @Override
    public Compatibility calculateChattingCompatibility(Long memberId, List<ChattingScore> chattingScores) {
        return null;
    }

    @Override
    public Compatibility calculateRoommateCompatibility(Long memberId, List<RoommateScore> roommateScores) {
        return null;
    }

    private List<Long> includeRequester(List<Long> targetMemberIds, Long requesterId) {
        return Stream.concat(targetMemberIds.stream(), Stream.of(requesterId))
                .filter(Objects::nonNull)
                .distinct()
                .toList();
    }

    public Compatibility calculateScores(List<LifePatternInformation> me, List<LifePatternInformation> target, List<PreferenceConditionWeight> preferenceConditionWeightList) {
        int lifePatternMaxSize = Math.max(me.size(), target.size());
        int lifePatternMinSize = Math.min(me.size(), target.size());
        double lifePartternPartPoint = (double) TOTAL_POINT / (lifePatternMaxSize + preferenceConditionWeightList.size());
        List<Compatibility.LifeStyleInfo> lifeStyleInfoList = new ArrayList<>();
        Integer totalScore = 0;

        for(int i = 0; i < lifePatternMaxSize; i++) {
            LifePattern lifePattern = me.get(i).getLifePattern();
            if(i > lifePatternMinSize ) {
                boolean isPreferenceWeight = preferenceConditionWeightList.stream().anyMatch(item -> item.getLifePattern() == lifePattern);
                int preferenceWeight = isPreferenceWeight ? PREFERENCE_WEIGHT : NON_PREFERENCE_WEIGHT;

                Compatibility.LifeStyleInfo lifeStyleInfo = calculateScores(me.get(i), target.get(i), lifePattern);
                lifeStyleInfoList.add(lifeStyleInfo);
                totalScore += (int) ((lifePartternPartPoint * preferenceWeight) * ((double) lifeStyleInfo.getPercent() / TOTAL_POINT));
            } else {
                lifeStyleInfoList.add(Compatibility.LifeStyleInfo.builder().id(lifePattern.getId()).name(lifePattern.getName()).percent(0).build());
            }
        }

        return Compatibility.builder().totalScore(totalScore).lifeStyleInfo(lifeStyleInfoList).build();
    }

    public Compatibility.LifeStyleInfo calculateScores(LifePatternInformation me, LifePatternInformation target, LifePattern lifePattern) {
        if(!me.getLifePattern().equals(target.getLifePattern())) throw new BusinessException(EtcErrorCode.SCORE_CALC_ERROR);


        Integer similarity = lifePatternTypeScoreCalcList.stream()
                .filter(item -> item.supports() == lifePattern.getDtype()).findFirst()
                .map(calc -> calc.calculateSimilarity(me, target, lifePattern))
                .orElseThrow(() -> new BusinessException(EtcErrorCode.SCORE_CALC_ERROR));

        return Compatibility.LifeStyleInfo.builder().id(lifePattern.getId()).name(lifePattern.getName()).percent(similarity).build();
    }
}
