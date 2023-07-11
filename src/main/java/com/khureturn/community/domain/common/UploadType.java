package com.khureturn.community.domain.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UploadType {

    PROFILE_IMG("profileImgDirName" , "/profileImg");


    private final String uploadDivision;
    private final String DirName;

}
