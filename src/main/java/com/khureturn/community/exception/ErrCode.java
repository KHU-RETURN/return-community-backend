package com.khureturn.community.exception;

public enum ErrCode {



    REQUIRE_LOGIN("RE000", "로그인이 필요합니다."),
    INVALID_ACCESS_TOKEN("RE001", "Access Token이 유효하지 않습니다."),
    EXPIRED_ACCESS_TOKEN("RE002", "Access Token이 만료되었습니다."),

    ALREADY_LOGIN("RE003", " 로그인된 유저입니다."),
    NO_PERMISSION("BSE004", "해당 기능에 대한 권한이 없습니다."),
    BAD_REQUEST("RE005", "클라이언트 요청오류."),
    NO_SUCH_USER("RE006", "해당 유저를 찾을 수 없습니다."),
    INVALID_PHONE_NUMBER_FORMAT("RE008", "전화번호 형식이 잘못되었습니다."),
    INVALID_EMAIL_FORMAT("RE009", "이메일 형식이 잘못되었습니다."),
    INVALID_USER_ROLE("RE010", "유저 형식이 잘못되었습니다."),
    DUPLICATED_PHONE_NUMBER("RE012", "사용중인 전화번호입니다."),
    DUPLICATED_EMAIL("RE013", "사용중인 이메일입니다."),

    INVALID_MEDIA_TYPE("RE014", "허용되지 않은 미디어 타입"),


    INTERNAL_SERVER_ERROR("RE500", "서버 요청 처리 실패.");


    private final String code;
    private final String message;

    ErrCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
