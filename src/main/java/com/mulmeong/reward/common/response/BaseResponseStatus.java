package com.mulmeong.reward.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum BaseResponseStatus {
    /*
     * 응답 코드와 메시지 표준화하는 ENUM.
     * Http 상태코드, 성공 여부, 응답 메시지, 커스텀 응답 코드, 데이터를 반환.
     */

    // 200: 요청 성공.
    SUCCESS(HttpStatus.OK, true, 200, "요청에 성공하였습니다."),

    // 400 : 사용자 요청 오류
    ILLEGAL_ARGUMENT(HttpStatus.BAD_REQUEST, false, 400, "잘못된 요청입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, false, 401, "적절하지 않은 요청값입니다."),
    NO_SIGN_IN(HttpStatus.UNAUTHORIZED, false, 402, "로그인을 먼저 진행해주세요"),

    // 900 : 기타 에러
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, 900, "요청 처리 중 에러가 발생하였습니다."),

    // 1700 : 뱃지 관련 에러
    NO_BADGE(HttpStatus.BAD_REQUEST, false, 1700, "존재하지 않는 뱃지입니다."),
    EXIST_BADGE(HttpStatus.BAD_REQUEST, false, 1701, "이미 존재하는 뱃지입니다."),

    // 1800 : 포인트 관련 에러
    NO_POINT(HttpStatus.BAD_REQUEST, false, 1800, "존재하지 않는 포인트입니다."),
    INVALID_HISTORY_TYPE(HttpStatus.BAD_REQUEST, false, 1801, "적절하지 않은 히스토리 타입입니다."),
    INVALID_HISTORY_REASON(HttpStatus.BAD_REQUEST, false, 1802, "적절하지 않은 히스토리 이유입니다."),


    // 1900 : 등급 관련 에러
    NO_GRADE(HttpStatus.BAD_REQUEST, false, 1900, "존재하지 않는 등급입니다."),
    EXIST_GRADE(HttpStatus.BAD_REQUEST, false, 1901, "이미 존재하는 등급입니다.");

    private final HttpStatus httpStatusCode;
    private final boolean isSuccess;
    private final int code;
    private final String message;

}

