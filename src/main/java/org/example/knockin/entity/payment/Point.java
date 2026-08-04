package org.example.knockin.entity.payment;

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
@Table(name = "point")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long points;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) private Member member;
}
