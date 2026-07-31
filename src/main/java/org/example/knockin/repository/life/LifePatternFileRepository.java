package org.example.knockin.repository.life;

import org.example.knockin.entity.life.LifePattern;
import org.example.knockin.entity.life.LifePatternFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LifePatternFileRepository extends JpaRepository<LifePatternFile, Long> {
    Optional<LifePatternFile> findByLifePattern(LifePattern lifePattern);
}