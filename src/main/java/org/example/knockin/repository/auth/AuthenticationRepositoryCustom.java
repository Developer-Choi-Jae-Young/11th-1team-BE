package org.example.knockin.repository.auth;

import java.util.List;

import org.example.knockin.dto.*;
import org.example.knockin.entity.auth.AuthenticationType;
import org.example.knockin.entity.member.Member;
import org.example.knockin.repository.auth.row.MemberAuthenticationRow;
import org.springframework.data.domain.Pageable;

public interface AuthenticationRepositoryCustom {
    List<AuthenticationType> getAcceptedAuthenticationTypeByMemberId(Long memberId);

    List<MemberAuthenticationRow> findAcceptedByMemberIds(List<Long> memberIds);

    List<BoVerificationApproveListDto.Response.EmployeeAuthItem> findVerificationApproves(Pageable pageable);

    List<BoVerificationCancelListDto.Response.EmployeeAuthItem> findVerificationCancels(Pageable pageable);

    List<BoVerificationWaitingListDto.Response.EmployeeAuthItem> findVerificationsList(Pageable pageable);

    BoVerificationWaitingDetailDto.Response findVerifications(Long id);

    MyVerificationListDto.Response.AuthInfo findVerificationList(Pageable pageable, Member member, AuthenticationType authenticationType);
}
