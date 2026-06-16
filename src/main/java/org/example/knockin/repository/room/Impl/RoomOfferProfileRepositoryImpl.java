package org.example.knockin.repository.room.Impl;

import static org.example.knockin.entity.room.QOfferRoomType.offerRoomType;
import static org.example.knockin.entity.room.QRoomOfferProfile.roomOfferProfile;
import static org.example.knockin.entity.room.QRoomType.roomType;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.room.QOfferRoomType;
import org.example.knockin.entity.room.QRegion;
import org.example.knockin.entity.room.QRoomProfile;
import org.example.knockin.repository.room.RoomOfferProfileRepositoryCustom;
import org.example.knockin.repository.room.row.MatchingOfferProfileRow;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoomOfferProfileRepositoryImpl implements RoomOfferProfileRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MatchingOfferProfileRow> findAllOfferProfileByMemberIdIn(List<Long> memberIds) {
        if (memberIds == null || memberIds.isEmpty()) {
            return List.of();
        }

        QRoomProfile latestRoomProfile = new QRoomProfile("latestRoomProfile");
        QOfferRoomType latestOfferRoomType = new QOfferRoomType("latestOfferRoomType");

        QRegion offerRegion = new QRegion("offerRegion");
        QRegion parentRegion = new QRegion("parentRegion");
        QRegion grandParentRegion = new QRegion("grandParentRegion");

        return jpaQueryFactory
                .select(Projections.constructor(
                        MatchingOfferProfileRow.class,
                        roomOfferProfile.member.id,
                        roomOfferProfile.deposit,
                        roomOfferProfile.monthlyRent,
                        offerRegion.name,
                        parentRegion.name,
                        grandParentRegion.name,
                        roomType.name
                ))
                .from(roomOfferProfile)
                .join(roomOfferProfile.region, offerRegion)
                .leftJoin(offerRegion.parent, parentRegion)
                .leftJoin(parentRegion.parent, grandParentRegion)
                .join(offerRoomType)
                .on(
                        offerRoomType.roomOfferProfile.id.eq(roomOfferProfile.id),
                        offerRoomType.id.eq(
                                JPAExpressions
                                        .select(latestOfferRoomType.id.max())
                                        .from(latestOfferRoomType)
                                        .where(latestOfferRoomType.roomOfferProfile.id.eq(roomOfferProfile.id))
                        )
                )
                .join(offerRoomType.roomType, roomType)
                .where(
                        roomOfferProfile.member.id.in(memberIds),
                        roomOfferProfile.id.eq(
                                JPAExpressions
                                        .select(latestRoomProfile.id.max())
                                        .from(latestRoomProfile)
                                        .where(latestRoomProfile.member.id.eq(roomOfferProfile.member.id))
                        )
                )
                .fetch();
    }
}
