package org.example.knockin.repository;

import java.util.Optional;
import org.example.knockin.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long>, MemberRepositoryCustom {
    Optional<MemberEntity> findByMemberId(Long memberId);
}
