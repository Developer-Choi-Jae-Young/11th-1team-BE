package org.example.knockin.entity.room;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "room_seeker_profile")
@DiscriminatorValue(RoomProfileType.Values.SEEKER)
public class RoomSeekerProfile extends RoomProfile {
    @Column(name = "min_deposit", nullable = false)
    private Integer minDeposit;

    @Column(name = "max_deposit", nullable = false)
    private Integer maxDeposit;

    @Column(name = "min_monthly_rent", nullable = false)
    private Integer minMonthlyRent;

    @Column(name = "max_monthly_rent", nullable = false)
    private Integer maxMonthlyRent;

    @Column(name ="is_comeable_at_negotiable")
    private Boolean isComeableAtNegotiable;

    @Column(name = "comeable_at", nullable = false)
    private LocalDateTime comeableAt;
}
