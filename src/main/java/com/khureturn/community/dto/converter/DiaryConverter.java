package com.khureturn.community.dto.converter;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.dto.DiaryRequestDto;

public class DiaryConverter {

    public static Diary toDiary(DiaryRequestDto.CreateDiaryDto request, Member member){
        return Diary.builder()
                .diaryTitle(request.getTitle())
                .diaryContent(request.getContent())
                .isAnonymous(request.getIsAnonymous())
                .thumbnailIndex(request.getThumbnailIndex())
                .member(member)
                .build();
    }

}
