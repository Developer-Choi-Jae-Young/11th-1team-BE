package org.example.knockin.repository.member.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.member.BasicInformation;
import org.example.knockin.entity.member.Member;
import org.example.knockin.repository.member.BasicInformationRepositoryCustom;
import org.springframework.stereotype.Repository;

import static org.example.knockin.entity.member.QBasicInformation.basicInformation;

@Repository
@RequiredArgsConstructor
public class BasicInformationRepositoryImpl implements BasicInformationRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean isExsitBasicInformation(Member member) {
        Long result = jpaQueryFactory.select(basicInformation.id).from(basicInformation).where(basicInformation.member.eq(member)).fetchFirst();
        return result != null;
    }

    @Override
    public Optional<BasicInformation> findLatestBasicInformation(Member member) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(basicInformation)
                .where(basicInformation.member.eq(member))
                .orderBy(basicInformation.id.desc())
                .fetchFirst()
        );
    }
}
