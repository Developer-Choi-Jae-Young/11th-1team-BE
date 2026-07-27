package org.example.knockin.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.knockin.dto.ChatSocketResponse;
import org.example.knockin.dto.EventType;
import org.example.knockin.dto.RoommateRequestDto;
import org.example.knockin.dto.RoommateRequestDto.Response;
import org.example.knockin.dto.RoommateRequestDto.RoommateMatchingRequiredInfo;
import org.example.knockin.dto.RoommateRequestListDto;
import org.example.knockin.entity.alarm.AlarmSettingType;
import org.example.knockin.entity.chat.ChatRoomMember;
import org.example.knockin.entity.chat.ChattingRoom;
import org.example.knockin.entity.member.BasicInformation;
import org.example.knockin.entity.member.Member;
import org.example.knockin.entity.member.MemberPrivacy;
import org.example.knockin.entity.member.MemberPrivacyType;
import org.example.knockin.entity.room.RoommateMatchingRequired;
import org.example.knockin.entity.room.RoommateRequiredStatus;
import org.example.knockin.exception.BusinessException;
import org.example.knockin.exception.RequiredErrorCode;
import org.example.knockin.global.entity.RoommateRequiredMessageTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoommateRequestServiceImpl {

    private final SimpMessageSendingOperations messagingTemplate;
    private final RoommateMatchingRequiredServiceImpl roommateMatchingRequiredService;
    private final ChatRoomMemberServiceImpl chatRoomMemberService;
    private final RoommateMatchingRequiredAlarmServiceImpl roommateMatchingRequiredAlarmService;
    private final MyRoomMateServiceImpl myRoomMateService;
    private final MemberPrivacyServiceImpl memberPrivacyService;
    private final PushNotificationServiceImpl pushNotificationService;
    private final BasicInformationServiceImpl basicInformationService;

    @Transactional
    public RoommateRequestDto.Response saveRoommateRequest(Long requesterId, RoommateRequestDto.Request request) {
        Long chatRoomId = request.getChatRoomId();
        ChatRoomMember chatRoomMember = chatRoomMemberService.findActiveMemberByRoomIdAndMemberId(chatRoomId, requesterId);
        ChattingRoom chattingRoom = chatRoomMember.getChattingRoom();
        Member requester = chatRoomMember.getMember();
        Long chattingRoomId = chattingRoom.getId();
        Member requestee = chatRoomMemberService.findPartnerMember(chatRoomMember, chattingRoomId);

        RoommateMatchingRequired roommateMatchingRequired = roommateMatchingRequiredService.findLatest(chatRoomId)
                .map(previous -> {
                    if (previous.getStatus().equals(RoommateRequiredStatus.PENDING)) {
                        throw new BusinessException(RequiredErrorCode.ROOMMATE_DUPLICATE);
                    }
                    return roommateMatchingRequiredService.savePending(requester, requestee, chattingRoom);
                })
                .orElseGet(() -> roommateMatchingRequiredService.savePending(requester, requestee, chattingRoom));

        Response response = toDto(roommateMatchingRequired);
        sendAlarms(requestee, requester, roommateMatchingRequired);
        sendRequestMessage(chatRoomId, response);
        return response;
    }

    private void sendAlarms(Member receiver, Member sender, RoommateMatchingRequired required) {
        BasicInformation basicInformation = basicInformationService.findLatestBasicInformation(sender);
        String senderName = basicInformation.getName();

        RoommateRequiredMessageTemplate template = RoommateRequiredMessageTemplate.of(required.getStatus());
        String title = template.formatTitle(senderName);
        String contents = template.formatContents(senderName);
        String deepLink = template.formatDeepLink(required.getChattingRoom().getId());

        roommateMatchingRequiredAlarmService.send(receiver, title, contents, required);
        pushNotificationService.send(receiver, AlarmSettingType.NOTIFICATION, title, contents, deepLink);
    }

    private RoommateRequestDto.Response toDto(RoommateMatchingRequired roommateMatchingRequired) {
        RoommateMatchingRequiredInfo roommateMatchingRequiredInfo = RoommateMatchingRequiredInfo.builder()
                .requiredId(roommateMatchingRequired.getId())
                .requesterMemberId(roommateMatchingRequired.getRequester().getId())
                .requesteeMemberId(roommateMatchingRequired.getRequestee().getId())
                .status(roommateMatchingRequired.getStatus())
                .createdAt(roommateMatchingRequired.getCreatedAt())
                .updatedAt(roommateMatchingRequired.getUpdatedAt())
                .build();

        return RoommateRequestDto.Response.builder()
                .roommateMatchingRequiredInfo(roommateMatchingRequiredInfo)
                .build();
    }

    private void sendRequestMessage(Long chatRoomId, RoommateRequestDto.Response response) {
        ChatSocketResponse<RoommateRequestDto.Response> socketResponse = ChatSocketResponse.of(
                EventType.ROOMMATE_REQUEST,
                chatRoomId,
                response
        );
        messagingTemplate.convertAndSend("/sub/chats/" + chatRoomId, socketResponse);
    }

    @Transactional
    public RoommateRequestDto.Response acceptRequired(Long memberId, Long requestId) {
        RoommateMatchingRequired roommateMatchingRequired = roommateMatchingRequiredService.findByIdOrThrow(requestId);

        if (!roommateMatchingRequired.isRequestee(memberId)) {
            throw new BusinessException(RequiredErrorCode.ROOMMATE_ACCESS_DENIED);
        }

        validateRequired(roommateMatchingRequired);
        roommateMatchingRequired.accept();
        myRoomMateService.save(roommateMatchingRequired);

        Member requester = roommateMatchingRequired.getRequester();
        Member requestee = roommateMatchingRequired.getRequestee();
        sendAlarms(requester, requestee, roommateMatchingRequired);

        Response response = toDto(roommateMatchingRequired);
        sendRequestMessage(roommateMatchingRequired.getChattingRoom().getId(), response);

        MemberPrivacy memberPrivacy = memberPrivacyService.findByMemberId(memberId).getFirst();
        memberPrivacy.changeState(MemberPrivacyType.PRIVATE);

        return response;
    }

    @Transactional
    public RoommateRequestDto.Response rejectRequired(Long memberId, Long requestId) {
        RoommateMatchingRequired roommateMatchingRequired = roommateMatchingRequiredService.findByIdOrThrow(requestId);

        if (!roommateMatchingRequired.isRequestee(memberId)) {
            throw new BusinessException(RequiredErrorCode.ROOMMATE_ACCESS_DENIED);
        }

        validateRequired(roommateMatchingRequired);
        roommateMatchingRequired.reject();

        Member requester = roommateMatchingRequired.getRequester();
        Member requestee = roommateMatchingRequired.getRequestee();
        sendAlarms(requester, requestee, roommateMatchingRequired);

        Response response = toDto(roommateMatchingRequired);
        sendRequestMessage(roommateMatchingRequired.getChattingRoom().getId(), response);
        return response;
    }

    @Transactional
    public RoommateRequestDto.Response cancelRequired(Long memberId, Long requestId) {
        RoommateMatchingRequired roommateMatchingRequired = roommateMatchingRequiredService.findByIdOrThrow(requestId);

        if (!roommateMatchingRequired.isRequester(memberId)) {
            throw new BusinessException(RequiredErrorCode.ROOMMATE_ACCESS_DENIED);
        }

        validateRequired(roommateMatchingRequired);
        roommateMatchingRequired.cancel();
        Response response = toDto(roommateMatchingRequired);
        sendRequestMessage(roommateMatchingRequired.getChattingRoom().getId(), response);
        return response;
    }

    public Page<RoommateRequestListDto.Response> getRequiredList(Long memberId, Pageable pageable) {
        return roommateMatchingRequiredService.findMyRequiredList(memberId, pageable).map(this::toListDto);
    }

    private RoommateRequestListDto.Response toListDto(RoommateMatchingRequired roommateMatchingRequired) {
        return RoommateRequestListDto.Response.builder()
                .requiredId(roommateMatchingRequired.getId())
                .requesterId(roommateMatchingRequired.getRequester().getId())
                .requesteeId(roommateMatchingRequired.getRequestee().getId())
                .chatRoomId(roommateMatchingRequired.getChattingRoom().getId())
                .status(roommateMatchingRequired.getStatus())
                .createAt(roommateMatchingRequired.getCreatedAt())
                .build();
    }

    private void validateRequired(RoommateMatchingRequired roommateMatchingRequired) {
        if (!roommateMatchingRequired.isPending()) {
            throw new BusinessException(RequiredErrorCode.ROOMMATE_INVALID_STATUS);
        }
    }
}
