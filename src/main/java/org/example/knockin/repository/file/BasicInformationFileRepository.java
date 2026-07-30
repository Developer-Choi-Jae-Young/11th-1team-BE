package org.example.knockin.repository.file;

import org.example.knockin.entity.file.BasicInformationFile;
import org.example.knockin.entity.member.BasicInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasicInformationFileRepository extends JpaRepository<BasicInformationFile, Long> {
    Optional<BasicInformationFile> findByBasicInformation(BasicInformation basicInformation);
}