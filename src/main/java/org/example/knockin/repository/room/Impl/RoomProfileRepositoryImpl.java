package org.example.knockin.repository.room.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.knockin.repository.room.RoomProfileRepositoryCustom;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoomProfileRepositoryImpl implements RoomProfileRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
}