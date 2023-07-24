package com.khureturn.community.dto;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryComment;
import com.khureturn.community.domain.diary.DiaryFile;
import com.khureturn.community.repository.DiaryCommentRepository;
import com.khureturn.community.repository.DiaryFileRepository;
import com.khureturn.community.repository.MemberRepository;
import com.khureturn.community.service.DiaryCommentService;
import com.khureturn.community.service.DiaryLikeService;
import com.khureturn.community.service.DiaryScrapService;
import com.khureturn.community.service.DiaryService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DiaryConverter {
    private static DiaryLikeService diaryLikeService;
    private static DiaryScrapService diaryScrapService;
    private static DiaryFileRepository diaryFileRepository;

    private static DiaryCommentService diaryCommentService;
    private static DiaryCommentRepository diaryCommentRepository;
    private static DiaryService diaryService;

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

    public static DiaryResponseDto.DiaryDto toDiaryDto(Diary diary, DiaryFile diaryFile, Member member){
        Boolean isLiked = diaryLikeService.findDiaryLikeByMemberAndDiary(member.getMemberId(), diary.getId());
        Boolean isBookmarked = diaryScrapService.findDiaryScrapByMemberAndDiary(member.getMemberId(), diary.getId());
        Boolean isMyPost = diaryService.findByMember(member.getMemberId());
        String url = diaryFile.getDiaryOriginalUrl();
        List<String> list = Arrays.asList(url.split(","));
        return DiaryResponseDto.DiaryDto.builder()
                .isLiked(isLiked)
                .isBookmarked(isBookmarked)
                .member(MemberResponseDto.MemberDto.builder().memberId(member.getMemberId()).profileImgURL(member.getProfileImg()).name(member.getName()).phoneNumber(member.getPhoneNumber()).build())
                .title(diary.getDiaryTitle())
                .content(diary.getDiaryContent())
                .mediaList(list)
                .createdDate(diary.getCreatedAt())
                .modifiedDate(diary.getUpdateAt())
                .viewCount(diary.getDiaryViewCount())
                .bookmarkedCount(diary.getDiaryScrapCount())
                .isAnonymous(diary.getIsAnonymous())
                .isMyPost(isMyPost)
                .likeCount(diary.getDiaryLikeCount())
                .commentCount(diary.getDiaryCommentCount())
                .build();
    }

    public static List<DiaryResponseDto.DiarySortDto> toDiarySortDto(List<Diary> diaryList){

        List<DiaryResponseDto.DiarySortDto> sortList = new ArrayList<>();
        for(Diary d: diaryList){
            Member member = d.getMember();
            DiaryFile diaryFile = diaryFileRepository.findByDiary(d);
            String url = diaryFile.getDiaryOriginalUrl();
            List<String> list = Arrays.asList(url.split(","));
            Boolean isLiked = diaryLikeService.findDiaryLikeByMemberAndDiary(member.getMemberId(), d.getId());
            Boolean isBookmarked = diaryScrapService.findDiaryScrapByMemberAndDiary(member.getMemberId(), d.getId());
            Boolean isMyPost = diaryService.findByMember(member.getMemberId());
            DiaryResponseDto.DiarySortDto result =DiaryResponseDto.DiarySortDto.builder()
                    .diaryId(d.getId())
                    .title(d.getDiaryTitle())
                    .thumbnailImgURL(list.get(d.getThumbnailIndex()))
                    .likeCount(d.getDiaryLikeCount())
                    .commentCount(d.getDiaryCommentCount())
                    .viewCount(d.getDiaryViewCount())
                    .member(MemberResponseDto.MemberSortDto.builder().memberId(member.getMemberId()).profileImgURL(member.getProfileImg()).name(member.getName()).build())
                    .createdDate(d.getCreatedAt())
                    .isAnonymous(d.getIsAnonymous())
                    .isMyPost(isMyPost)
                    .isLiked(isLiked)
                    .isBookmarked(isBookmarked)
                    .build();
            sortList.add(result);

        }
        return sortList;

    }


    public static List<DiaryCommentResponseDto.CommentDto> toCommentListDto(List<DiaryComment> diaryCommentList){
        List<DiaryCommentResponseDto.CommentDto> list = new ArrayList<>();
        for(DiaryComment c: diaryCommentList){
            Member member = c.getMember();
            int reCommentCount = diaryCommentRepository.countAllByParent(c.getParent());
            DiaryCommentResponseDto.CommentDto comment = DiaryCommentResponseDto.CommentDto.builder()
                    .commentId(c.getId())
                    .content(c.getDiaryCommentContent())
                    .recommentCount(reCommentCount)
                    .user(MemberResponseDto.MemberDto.builder().memberId(member.getMemberId()).profileImgURL(member.getProfileImg()).name(member.getName()).build())
                    .createdDate(c.getCreatedAt())
                    .build();
            list.add(comment);
        }
        return list;
    }


}
