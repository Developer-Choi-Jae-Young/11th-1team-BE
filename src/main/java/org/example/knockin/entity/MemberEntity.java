package org.example.knockin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.knockin.dto.MemberRole;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String name;

    @Enumerated(EnumType.STRING)
    private LoginProviderType providerType;

    private String providerId;

    @Enumerated(EnumType.STRING)
    private MemberRole role;
}
