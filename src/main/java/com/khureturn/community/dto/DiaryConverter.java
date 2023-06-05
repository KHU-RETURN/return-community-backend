package com.khureturn.community.dto;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryFile;
import java.util.List;
import java.util.stream.Collectors;

public class DiaryConverter {

    public static Diary toDiary(DiaryRequestDto.CreateDiaryDto request, Member member){
        return Diary.builder()
                .diaryTitle(request.getTitle())
                .diaryContent(request.getContent())
                .isAnonymous(request.getIsAnonymous())
                .member(member)
                .build();
    }
    public static DiaryFile toDiaryFile(DiaryRequestDto.CreateDiaryDto request, Diary diary){
        return DiaryFile.builder()
                .diaryThumb(request.getThumbnailIndex())
                .diaryOriginalUrl(request.getMedia())
                .diary(diary)
                .build();
    }

    public static DiaryResponseDto.DiaryDto toDiaryDto(Diary diary){
        return DiaryResponseDto.DiaryDto.builder()
                .diaryId(diary.getId())
                .title(diary.getDiaryTitle())
                .content(diary.getDiaryContent())
                .isAnonymous(diary.getIsAnonymous())
                .bookmarkedCount(diary.getDiaryScrapCount())
                .commentCount(diary.getDiaryCommentCount())
                .createdDate(diary.getCreatedAt())
                .build();
    }

    public static List<DiaryResponseDto.DiaryDto> toDiaryDtoList(List<Diary> diaries){
        return diaries.stream()
                .map(diary -> toDiaryDto(diary))
                .collect(Collectors.toList());
    }

    public static DiaryResponseDto.DiaryListDto toDiaryListDto(List<Diary> diaries){
        return DiaryResponseDto.DiaryListDto.builder()
                .diaryDtoList(toDiaryDtoList(diaries))
                .size(diaries.size())
                .build();
    }


}
