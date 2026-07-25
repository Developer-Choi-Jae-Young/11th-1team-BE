package org.example.knockin.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.knockin.dto.BlockDto;
import org.example.knockin.dto.BlockListDto;
import org.example.knockin.dto.BlockListDto.Response;
import org.example.knockin.entity.member.Block;
import org.example.knockin.entity.member.Member;
import org.example.knockin.exception.BlockErrorCode;
import org.example.knockin.exception.BusinessException;
import org.example.knockin.repository.member.BlockRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlockServiceImpl {

    private final MemberServiceImpl memberService;
    private final BlockRepository blockRepository;

    public BlockDto.Response saveBlock(Long blockerId, Long blockedId) {
        Member blocker = memberService.findByIdOrThrow(blockerId);
        Member blocked = memberService.findByIdOrThrow(blockedId);

        if (blockRepository.existsByBlockerIdAndBlockedId(blockerId, blockedId)) {
            throw new BusinessException(BlockErrorCode.DUPLICATE);
        }

        blockRepository.save(Block.builder().blocker(blocker).blocked(blocked).build());
        return BlockDto.Response.builder().updatedAt(LocalDateTime.now()).build();
    }

    public BlockListDto.Response findMyList(Long memberId, Pageable pageable) {
        Member blocker = memberService.findByIdOrThrow(memberId);
        Page<Block> page = blockRepository.findByBlocker(blocker, pageable);
        List<Response.Block> blocks = page.getContent().stream().map(this::toDto).toList();
        return BlockListDto.Response.builder().blocks(blocks).build();
    }

    private BlockListDto.Response.Block toDto(Block block) {
        return BlockListDto.Response.Block.builder()
                .userId(block.getBlocked().getId())
                // 이름 fetch: blocked -> basicInfo
                // .name()
                .createAt(block.getBlocked().getCreatedAt())
                .build();
    }

    public BlockDto.Response deleteBlock(Long blockerId, Long blockedId) {
        Member blocker = memberService.findByIdOrThrow(blockerId);
        Member blocked = memberService.findByIdOrThrow(blockedId);

        Block block = blockRepository.findOneByBlockerAndBlocked(blocker, blocked)
                .orElseThrow(() -> new BusinessException(BlockErrorCode.NOT_FOUND));

        blockRepository.delete(block);

        return BlockDto.Response.builder().updatedAt(LocalDateTime.now()).build();
    }
}
