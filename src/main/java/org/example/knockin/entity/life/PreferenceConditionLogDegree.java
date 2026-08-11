package org.example.knockin.entity.life;

import jakarta.persistence.*;
import lombok.*;
import org.example.knockin.entity.member.Member;
import org.example.knockin.global.entity.CreatedAtEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "preference_condition_log_degree")
public class PreferenceConditionLogDegree extends CreatedAtEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Long degree;
}
