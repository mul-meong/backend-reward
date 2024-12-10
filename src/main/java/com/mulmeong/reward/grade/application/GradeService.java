package com.mulmeong.reward.grade.application;

import com.mulmeong.reward.grade.dto.in.GradeCreateDto;
import com.mulmeong.reward.grade.dto.in.GradeUpdateDto;
import com.mulmeong.reward.grade.dto.out.GradeDto;

import java.util.List;

public interface GradeService {

    void createGrade(GradeCreateDto gradeCreateVo);

    void updateGrade(GradeUpdateDto gradeUpdateDto);

    void deleteGrade(Long gradeId);

    GradeDto getGrade(Long gradeId);

    List<GradeDto> getAllGrades();
}
