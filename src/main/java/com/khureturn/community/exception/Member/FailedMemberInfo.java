package com.khureturn.community.exception.Member;

import com.khureturn.community.exception.ErrCode;

public enum FailedMemberInfo {
    PHONENUMBER(ErrCode.INVALID_PHONE_NUMBER_FORMAT, ErrCode.DUPLICATED_PHONE_NUMBER),
    EMAIL(ErrCode.INVALID_EMAIL_FORMAT, ErrCode.DUPLICATED_EMAIL);

    private final ErrCode invalid_format_errorCode;
    private final ErrCode duplicated_errorCode;

    FailedMemberInfo(ErrCode invalid_format_errorCode, ErrCode duplicated_errorCode) {

        this.invalid_format_errorCode = invalid_format_errorCode;
        this.duplicated_errorCode = duplicated_errorCode;
    }


    public ErrCode getInvalid_format_errorCode() {
        return invalid_format_errorCode;
    }

    public ErrCode getDuplicated_errorCode() {
        return duplicated_errorCode;
    }
}
