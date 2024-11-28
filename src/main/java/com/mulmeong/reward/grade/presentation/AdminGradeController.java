package com.mulmeong.reward.grade.presentation;


import com.mulmeong.reward.common.response.BaseResponse;
import com.mulmeong.reward.grade.application.GradeService;
import com.mulmeong.reward.grade.dto.in.GradeCreateDto;
import com.mulmeong.reward.grade.dto.in.GradeUpdateDto;
import com.mulmeong.reward.grade.vo.in.GradeCreateVo;
import com.mulmeong.reward.grade.vo.in.GradeUpdateVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 관리자 전용 등급 API 컨트롤러, 관리자 권한 필요.
 * 등급 생성, 수정, 삭제.
 */
@Tag(name = "등급 관련 API", description = "등급 CRUD")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/v1/grade")
public class AdminGradeController {

    private final GradeService gradeService;

    @Operation(summary = "(관리자) 등급 생성", description = "모든 필드는 필수 값")
    @PostMapping()
    public BaseResponse<Void> createGrade(@RequestBody @Valid GradeCreateVo requestVo) {
        gradeService.createGrade(GradeCreateDto.toDto(requestVo));
        return new BaseResponse<>();
    }

    @Operation(summary = "(관리자) 등급 수정", description = "모든 필드는 NotBlank")
    @PutMapping("/{gradeId}")
    public BaseResponse<Void> updateGrade(@PathVariable @Valid Long gradeId,
                                          @RequestBody GradeUpdateVo requestVo) {
        gradeService.updateGrade(GradeUpdateDto.toDto(requestVo, gradeId));
        return new BaseResponse<>();
    }

    @Operation(summary = "(관리자) 등급 삭제")
    @DeleteMapping("/{gradeId}")
    public BaseResponse<Void> deleteGrade(@PathVariable Long gradeId) {
        gradeService.deleteGrade(gradeId);
        return new BaseResponse<>();
    }
}
