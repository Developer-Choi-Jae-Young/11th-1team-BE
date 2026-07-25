package org.example.knockin.repository.member;

import java.util.List;
import java.util.Optional;
import org.example.knockin.entity.member.Block;
import org.example.knockin.entity.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {
    boolean existsByBlockerIdAndBlockedId(Long blockerId, Long blockedId);

    Optional<Block> findByBlockerIdAndBlockedId(Long blockerId, Long blockedId);

    Optional<Block> findOneByBlockerAndBlocked(Member blocker, Member blocked);

    List<Block> findByBlocker(Member blocker);

    Page<Block> findByBlocker(Member blocker, Pageable pageable);
}