package org.example.knockin.repository;

import java.util.List;
import org.example.knockin.entity.Member;

public interface MemberRepositoryCustom {
    List<Member> searchMembers(String providerId, String providerType);
}
