package org.example.knockin.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.knockin.entity.file.BasicInformationFile;
import org.example.knockin.entity.file.File;
import org.example.knockin.entity.member.BasicInformation;
import org.example.knockin.entity.member.Member;
import org.example.knockin.exception.BusinessException;
import org.example.knockin.exception.MemberErrorCode;
import org.example.knockin.repository.file.BasicInformationFileRepository;
import org.example.knockin.repository.member.BasicInformationRepository;
import org.example.knockin.repository.member.row.ChattingRoomBasicInfoRow;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasicInformationServiceImpl {
    private final BasicInformationRepository basicInformationRepository;
    private final BasicInformationFileRepository basicInformationFileRepository;


    public List<BasicInformation> findByMember(Member member) {
        return basicInformationRepository.findByMember(member);
    }

    public BasicInformation findLatestBasicInformation(Member member) {
        return basicInformationRepository.findLatestBasicInformation(member)
                .orElseThrow(() -> new BusinessException(MemberErrorCode.BASIC_INFO_NOT_FOUND));
    }

    public List<ChattingRoomBasicInfoRow> findChattingRoomBasicInfoRows(List<Long> memberIds) {
        return basicInformationRepository.findChattingRoomBasicInfoRows(memberIds);
    }

    @Transactional
    public BasicInformation save(BasicInformation basicInformation) {
        return basicInformationRepository.save(basicInformation);
    }

    @Transactional
    public BasicInformationFile save(BasicInformationFile basicInformationFile) {
        return basicInformationFileRepository.save(basicInformationFile);
    }

    public BasicInformationFile findBasicInformationFile(BasicInformation basicInformation) {
        return basicInformationFileRepository.findByBasicInformation(basicInformation).orElse(null);
    }

    public ChattingRoomBasicInfoRow findChattingRoomBasicInfoRowByMemberId(Long memberId) {
        return basicInformationRepository.findChattingRoomBasicInfoRow(memberId)
                .orElseThrow(() -> new BusinessException(MemberErrorCode.BASIC_INFO_NOT_FOUND));
    }
}
