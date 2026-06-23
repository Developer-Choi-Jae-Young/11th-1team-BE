package org.example.knockin.repository.member;

import java.util.Optional;
import org.example.knockin.entity.member.BasicInformation;
import org.example.knockin.entity.member.Member;

public interface BasicInformationRepositoryCustom {
    boolean isExsitBasicInformation(Member member);

    Optional<BasicInformation> findLatestBasicInformation(Member member);
}
