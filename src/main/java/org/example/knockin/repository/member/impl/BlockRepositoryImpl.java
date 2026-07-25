package org.example.knockin.repository.member.impl;

import static org.example.knockin.entity.member.QBasicInformation.basicInformation;
import static org.example.knockin.entity.member.QBlock.block;
import static org.example.knockin.entity.member.QMember.member;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.knockin.dto.BlockListDto;
import org.example.knockin.dto.BlockListDto.Response.Block;
import org.example.knockin.repository.member.BlockRepositoryCustom;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BlockRepositoryImpl implements BlockRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<BlockListDto.Response.Block> findMyList(Long blockerId) {

        return jpaQueryFactory
                .select(Projections.constructor(
                        Block.class,
                        block.blocked.id,
                        basicInformation.name,
                        block.createdAt
                ))
                .from(block)
                .join(block.blocked, member)
                .leftJoin(basicInformation)
                .on(basicInformation.id.eq(
                        JPAExpressions
                                .select(basicInformation.id.max())
                                .from(basicInformation)
                                .where(basicInformation.member.id.eq(member.id))
                ))
                .where(block.blocker.id.eq(blockerId))
                .fetch();
    }
}
