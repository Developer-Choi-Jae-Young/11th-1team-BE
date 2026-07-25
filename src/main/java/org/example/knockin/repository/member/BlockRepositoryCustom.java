package org.example.knockin.repository.member;

import java.util.List;
import org.example.knockin.dto.BlockListDto.Response.Block;

public interface BlockRepositoryCustom {
    List<Block> findMyList(Long blockerId);

    boolean existsActiveBlockBetweenMembers(Long firstMemberId, Long secondMemberId);
}
