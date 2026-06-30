package org.example.knockin.entity.chat;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.knockin.entity.life.MemberLifePatternLog;
import org.example.knockin.entity.life.PreferenceConditionLog;
import org.example.knockin.entity.life.PreferenceConditionWeightLog;


@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chatting_score")
public class ChattingScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatting_required_id", nullable = false)
    private ChattingRequired chattingRequired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preference_condition_log_id")
    private PreferenceConditionLog preferenceConditionLog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "life_pattern_information_log_id", nullable = false)
    private MemberLifePatternLog lifePatternInformationLog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preference_condition_weight_log_id")
    private PreferenceConditionWeightLog preferenceConditionWeightLog;

    @Column(name = "score", nullable = false)
    private Integer score;
}
