package com.khureturn.community.dto;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryFile;

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
    public static DiaryFile toDiaryFile(DiaryRequestDto.CreateDiaryDto request, String media, Diary diary){
        return DiaryFile.builder()
                .diaryThumb(request.getThumbnailIndex())
                .diaryOriginalUrl(media)
                .diary(diary)
                .build();
    }



}
