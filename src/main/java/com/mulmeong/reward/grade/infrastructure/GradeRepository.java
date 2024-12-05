package com.mulmeong.reward.grade.infrastructure;

import com.mulmeong.reward.grade.domain.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    Optional<Grade> findByName(String name);
}
