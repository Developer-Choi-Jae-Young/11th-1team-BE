package org.example.knockin.repository.life.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.knockin.repository.life.MemberLifePatternRepositoryCustom;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberLifePatternRepositoryImpl implements MemberLifePatternRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
}
