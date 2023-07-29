package com.khureturn.community.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

public class MemberRequestDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignUpRequestDto {
        @NotNull
        private String googleSub;

        @NotNull
        private String phoneNumber;

        @NotNull
        private String email;

        @NotNull
        private String name;

        @NotNull
        private String nickname;

        @NotNull
        private Long studentId;

    }


    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateRequestDto {

        @NotNull
        private String phoneNumber;

        @NotNull
        private String email;

        @NotNull
        private String name;

        @NotNull
        private String nickname;

        @NotNull
        private Long studentId;

        @NotNull
        private Boolean isProfileImgChanged;
    }
}
