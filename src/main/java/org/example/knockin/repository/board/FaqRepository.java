package org.example.knockin.repository.board;

import org.example.knockin.entity.board.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq,Long>, FaqRepositoryCustom {
    List<Faq> findBySort(Integer sort);
}
