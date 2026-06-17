package org.example.knockin.repository.room.Impl;

import static org.example.knockin.entity.room.QRoomType.roomType;
import static org.example.knockin.entity.room.QSeekerRoomType.seekerRoomType;
import static org.example.knockin.entity.room.QRoomSeekerProfile.roomSeekerProfile;
import static org.example.knockin.entity.room.QRoomSeekerProfileRegion.roomSeekerProfileRegion;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.room.QRegion;
import org.example.knockin.entity.room.QRoomProfile;
import org.example.knockin.repository.room.RoomSeekerProfileRepositoryCustom;
import org.example.knockin.repository.room.row.MatchingSeekerProfileRow;
import org.example.knockin.repository.room.row.MatchingSeekerRegionRow;
import org.example.knockin.repository.room.row.MatchingSeekerRoomTypeRow;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoomSeekerProfileRepositoryImpl implements RoomSeekerProfileRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MatchingSeekerProfileRow> findAllSeekerProfileByMemberIdIn(List<Long> memberIds) {

        QRoomProfile latestRoomProfile = new QRoomProfile("latestRoomProfile");

        return jpaQueryFactory
                .select(Projections.constructor(
                        MatchingSeekerProfileRow.class,
                        roomSeekerProfile.member.id,
                        roomSeekerProfile.minDeposit,
                        roomSeekerProfile.maxDeposit,
                        roomSeekerProfile.minMonthlyRent,
                        roomSeekerProfile.maxMonthlyRent
                ))
                .from(roomSeekerProfile)
                .where(
                        roomSeekerProfile.member.id.in(memberIds),
                        roomSeekerProfile.id.eq(
                                JPAExpressions
                                        .select(latestRoomProfile.id.max())
                                        .from(latestRoomProfile)
                                        .where(latestRoomProfile.member.id.eq(roomSeekerProfile.member.id))
                        )
                )
                .fetch();
    }

    @Override
    public List<MatchingSeekerRegionRow> findAllSeekerRegionByMemberIdIn(List<Long> memberIds) {
        if (memberIds == null || memberIds.isEmpty()) {
            return List.of();
        }

        QRoomProfile latestRoomProfile = new QRoomProfile("latestRoomProfile");
        QRegion seekerRegion = new QRegion("seekerRegion");
        QRegion parentRegion = new QRegion("parentRegion");
        QRegion grandParentRegion = new QRegion("grandParentRegion");

        return jpaQueryFactory
                .select(Projections.constructor(
                        MatchingSeekerRegionRow.class,
                        roomSeekerProfile.member.id,
                        seekerRegion.name,
                        parentRegion.name,
                        grandParentRegion.name
                ))
                .from(roomSeekerProfileRegion)
                .join(roomSeekerProfileRegion.roomSeekerProfile, roomSeekerProfile)
                .join(roomSeekerProfileRegion.region, seekerRegion)
                .leftJoin(seekerRegion.parent, parentRegion)
                .leftJoin(parentRegion.parent, grandParentRegion)
                .where(
                        roomSeekerProfile.member.id.in(memberIds),
                        roomSeekerProfile.id.eq(
                                JPAExpressions
                                        .select(latestRoomProfile.id.max())
                                        .from(latestRoomProfile)
                                        .where(latestRoomProfile.member.id.eq(roomSeekerProfile.member.id))
                        )
                )
                .fetch();
    }

    @Override
    public List<MatchingSeekerRoomTypeRow> findAllSeekerRoomTypeByMemberIdIn(List<Long> memberIds) {
        if (memberIds == null || memberIds.isEmpty()) {
            return List.of();
        }

        QRoomProfile latestRoomProfile = new QRoomProfile("latestRoomProfile");

        return jpaQueryFactory
                .select(Projections.constructor(
                        MatchingSeekerRoomTypeRow.class,
                        roomSeekerProfile.member.id,
                        roomType.name
                ))
                .from(seekerRoomType)
                .join(seekerRoomType.roomSeekerProfile, roomSeekerProfile)
                .join(seekerRoomType.roomType, roomType)
                .where(
                        roomSeekerProfile.member.id.in(memberIds),
                        roomSeekerProfile.id.eq(
                                JPAExpressions
                                        .select(latestRoomProfile.id.max())
                                        .from(latestRoomProfile)
                                        .where(latestRoomProfile.member.id.eq(roomSeekerProfile.member.id))
                        )
                )
                .fetch();
    }
}
