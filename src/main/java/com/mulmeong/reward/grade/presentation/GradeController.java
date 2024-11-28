package com.mulmeong.reward.grade.presentation;

import com.mulmeong.reward.common.response.BaseResponse;
import com.mulmeong.reward.grade.application.GradeService;
import com.mulmeong.reward.grade.dto.out.GradeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "등급 관련 API", description = "등급 CRUD")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/grade")
public class GradeController {


    private final GradeService gradeService;

    @Operation(summary = "등급 ID로 단건 조회")
    @GetMapping("/{gradeId}")
    public BaseResponse<GradeDto> getGrade(@PathVariable Long gradeId) {

        return new BaseResponse<>(gradeService.getGrade(gradeId));
    }

    @Operation(summary = "모든 등급 조회", description = "모든 등급을 조회하는 페이지에서 사용")
    @GetMapping
    public BaseResponse<List<GradeDto>> getAllGrades() {
        return new BaseResponse<>(gradeService.getAllGrades());
    }

}
