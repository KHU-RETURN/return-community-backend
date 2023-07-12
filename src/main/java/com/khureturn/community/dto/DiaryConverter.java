package com.khureturn.community.dto;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryFile;
import com.khureturn.community.service.DiaryFileService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

public class DiaryConverter {

    public static Diary toDiary(DiaryRequestDto.CreateDiaryDto request){
        return Diary.builder()
                .diaryTitle(request.getTitle())
                .diaryContent(request.getContent())
                .isAnonymous(request.getIsAnonymous())
                .thumbnailIndex(request.getThumbnailIndex())
                .build();
    }
    public static DiaryFile toDiaryFile(DiaryRequestDto.CreateDiaryDto request, String media){
        return DiaryFile.builder()
                .diaryThumb(request.getThumbnailIndex())
                .diaryOriginalUrl(media)
                .build();
    }

//    public static DiaryResponseDto.DiaryDto toDiaryDto(Diary diary){
//        return DiaryResponseDto.DiaryDto.builder()
//                .isLiked()
//                .isBookmarked()
//                .member()
//                .title(diary.getDiaryTitle())
//                .content(diary.getDiaryContent())
//                .mediaList(diaryFile.getDiaryOriginalUrl())
//                .viewCount(diary.getDiaryViewCount())
//                .isAnonymous(diary.getIsAnonymous())
//                .bookmarkedCount(diary.getDiaryScrapCount())
//                .commentCount(diary.getDiaryCommentCount())
//                .createdDate(diary.getCreatedAt())
//                .build();
//    }

//    public static List<DiaryResponseDto.DiaryDto> toDiaryDtoList(List<Diary> diaries){
//        return diaries.stream()
//                .map(diary -> toDiaryDto(diary))
//                .collect(Collectors.toList());
//    }

//    public static DiaryResponseDto.DiaryListDto toDiaryListDto(List<Diary> diaries){
//        return DiaryResponseDto.DiaryListDto.builder()
//                .diaryDtoList(toDiaryDtoList(diaries))
//                .size(diaries.size())
//                .build();
//    }



}
