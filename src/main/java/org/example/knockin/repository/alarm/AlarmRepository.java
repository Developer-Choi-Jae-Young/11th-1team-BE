package org.example.knockin.repository.alarm;

import org.example.knockin.entity.alarm.Alarm;
import org.example.knockin.entity.member.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findByMember(Member member, Pageable pageable);
}