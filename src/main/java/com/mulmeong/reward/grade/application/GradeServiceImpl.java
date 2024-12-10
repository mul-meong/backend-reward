package com.mulmeong.reward.grade.application;

import com.mulmeong.reward.common.exception.BaseException;
import com.mulmeong.reward.grade.dto.in.GradeCreateDto;
import com.mulmeong.reward.grade.dto.in.GradeUpdateDto;
import com.mulmeong.reward.grade.dto.out.GradeDto;
import com.mulmeong.reward.grade.infrastructure.GradeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mulmeong.reward.common.response.BaseResponseStatus.NO_GRADE;

@Slf4j
@RequiredArgsConstructor
@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;

    /* (관리자) 등급 생성 */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createGrade(GradeCreateDto requestDto) {
        gradeRepository.save(requestDto.toEntity());
    }

    /* (관리자) 등급 수정(이름, 임계값) */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGrade(GradeUpdateDto requestDto) {
        gradeRepository.save(requestDto.toEntity(gradeRepository.findById(requestDto.getId())
                .orElseThrow(() -> new BaseException(NO_GRADE))));
    }

    /* (관리자) 등급 삭제 */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteGrade(Long gradeId) {
        gradeRepository.delete(gradeRepository.findById(gradeId)
                .orElseThrow(() -> new BaseException(NO_GRADE)));
    }

    /* 등급 ID로 단건 조회 */
    @Override
    @Transactional(readOnly = true)
    public GradeDto getGrade(Long gradeId) {
        return GradeDto.fromEntity(gradeRepository.findById(gradeId)
                .orElseThrow(() -> new BaseException(NO_GRADE)));
    }

    /* 모든 등급 조회 */
    @Override
    @Transactional(readOnly = true)
    public List<GradeDto> getAllGrades() {
        return gradeRepository.findAll().stream()
                .map(GradeDto::fromEntity)
                .toList();
    }
}
