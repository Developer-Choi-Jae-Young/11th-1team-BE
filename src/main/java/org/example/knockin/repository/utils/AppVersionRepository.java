package org.example.knockin.repository.utils;

import org.example.knockin.entity.utils.AppVersion;
import org.example.knockin.entity.utils.PlatformType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppVersionRepository extends JpaRepository<AppVersion, Long> {
    List<AppVersion> findByPlatformType(PlatformType platformType, Pageable pageable);
    List<AppVersion> findByPlatformTypeOrderByCreatedAtDesc(PlatformType platformType);
}
