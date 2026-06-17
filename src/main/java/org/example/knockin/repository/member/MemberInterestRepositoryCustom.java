package org.example.knockin.repository.member;

import java.util.Collection;
import java.util.List;

public interface MemberInterestRepositoryCustom {
    List<Long> findActiveReceiverIdsBySenderIdAndReceiverIds(Long senderId, Collection<Long> receiverIds);
}
