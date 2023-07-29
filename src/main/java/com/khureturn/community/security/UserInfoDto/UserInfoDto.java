package com.khureturn.community.security.UserInfoDto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Setter
@EqualsAndHashCode
public class UserInfoDto {
    private String fullname;

    private String googleSub;

    private String email;

    private String profileImgURL;
}
