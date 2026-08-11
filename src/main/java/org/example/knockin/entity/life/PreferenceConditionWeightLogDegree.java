package org.example.knockin.entity.life;

import jakarta.persistence.*;
import lombok.*;
import org.example.knockin.entity.member.Member;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "preference_condition_weight_log_degree")
public class PreferenceConditionWeightLogDegree {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long degree;
}
