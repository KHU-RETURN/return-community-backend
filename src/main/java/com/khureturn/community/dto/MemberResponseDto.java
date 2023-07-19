package com.khureturn.community.dto;
import com.khureturn.community.domain.common.ManagerStatus;
import com.khureturn.community.domain.common.RoleStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class MemberResponseDto {
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GoogleAccountInfoDto {
        private Long memberId;
        private String profileImgURL;
        private String name;
        private String phoneNumber;
    }


    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberBriefInfoDto{
        private Long memberId;
        private String studentId;
        private String name;
        private String profileImgURL;

    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FullUserInformationResponse{


        String name;

        String phoneNumber;

        String email;

        String studentId;

        RoleStatus role;

        ManagerStatus managerType;

        Boolean isPaid;

        String profileImgURL;
    }
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberDto{
        private Long memberId;
        private String profileImgURL;
        private String name;
        private String phoneNumber;

    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberSortDto{
        private Long memberId;
        private String profileImgURL;
        private String name;

    }

}
