package com.khureturn.community.service;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryFile;
import com.khureturn.community.dto.converter.DiaryConverter;
import com.khureturn.community.dto.DiaryRequestDto;
import com.khureturn.community.dto.DiaryResponseDto;
import com.khureturn.community.dto.MemberResponseDto;
import com.khureturn.community.dto.converter.JacksonUtil;
import com.khureturn.community.exception.NotFoundException;
import com.khureturn.community.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService{

    private final DiaryRepository diaryRepository;
    private final DiaryFileRepository diaryFileRepository;

    private final DiaryLikeRepository diaryLikeRepository;
    private final DiaryScrapRepository diaryScrapRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public Diary create(List<MultipartFile> mediaList, String diaryCreateDto, Principal principal) throws IOException {

        JacksonUtil jacksonUtil = new JacksonUtil();
        DiaryRequestDto.CreateDiaryDto request = (DiaryRequestDto.CreateDiaryDto) jacksonUtil.strToObj(diaryCreateDto, DiaryRequestDto.CreateDiaryDto.class);
        Member member = memberRepository.findByName(principal.getName());
        Diary diary = DiaryConverter.toDiary(request, member);
        diaryRepository.save(diary);
        for(MultipartFile media: mediaList){
            DiaryFile diaryFile = DiaryFileService.fileUpload(media, diary);
            diaryFileRepository.save(diaryFile);
        }
        return diary;
    }

    @Transactional
    public Diary update(Long diaryId, DiaryRequestDto.UpdateDiaryDto request) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NotFoundException("Diary를 찾을 수 없습니다."));
        diary.update(request.getTitle(), request.getContent(), request.getIsAnonymous());
        return diary;
    }

    @Transactional
    public void delete(Long diaryId){
        diaryRepository.deleteById(diaryId);
    }

    public Diary findById(Long diaryId){
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NotFoundException("Diary를 찾을 수 없습니다"));
        return diary;
    }

    public DiaryResponseDto.DiaryDto findDiary(Diary diary, DiaryFile diaryFile, Principal principal){
        diary.increaseHit();
        Member nowMember = memberRepository.findByName(principal.getName());
        Member diaryMember = diary.getMember();
        Boolean isLiked = diaryLikeRepository.existsDiaryLikeByMemberAndDiary(nowMember, diary);
        Boolean isBookmarked = diaryScrapRepository.existsDiaryScrapByMemberAndDiary(nowMember, diary);
        Boolean isMyPost = diaryRepository.existsByMember(nowMember);
        DiaryResponseDto.DiaryDto result = DiaryResponseDto.DiaryDto.builder()
                .isLiked(isLiked)
                .isBookmarked(isBookmarked)
                .member(MemberResponseDto.MemberDto.builder().memberId(diaryMember.getMemberId()).profileImgURL(diaryMember.getProfileImg()).name(diaryMember.getName()).phoneNumber(diaryMember.getPhoneNumber()).build())
                .title(diary.getDiaryTitle())
                .content(diary.getDiaryContent())
                .createdDate(diary.getCreatedAt())
                .modifiedDate(diary.getUpdateAt())
                .viewCount(diary.getDiaryViewCount())
                .bookmarkedCount(diary.getDiaryScrapCount())
                .isAnonymous(diary.getIsAnonymous())
                .isMyPost(isMyPost)
                .likeCount(diary.getDiaryLikeCount())
                .commentCount(diary.getDiaryCommentCount())
                .build();
        if(diaryFile != null){
            String url = diaryFile.getDiaryOriginalUrl();
            List<String> list = Arrays.asList(url.split(","));
            result.setMediaList(list);
        }
        return result;
    }


    public List<DiaryResponseDto.DiarySortDto> findDiarySort(List<Diary> diaryList, Principal principal){
        Member nowMember = memberRepository.findByName(principal.getName());
        List<DiaryResponseDto.DiarySortDto> sortList = new ArrayList<>();
        for(Diary d: diaryList){
            Member diarymember = d.getMember();
            Boolean isLiked = diaryLikeRepository.existsDiaryLikeByMemberAndDiary(nowMember, d);
            Boolean isBookmarked = diaryScrapRepository.existsDiaryScrapByMemberAndDiary(nowMember, d);
            Boolean isMyPost = diaryRepository.existsByMember(nowMember);
            DiaryResponseDto.DiarySortDto result =DiaryResponseDto.DiarySortDto.builder()
                    .diaryId(d.getId())
                    .title(d.getDiaryTitle())
                    .likeCount(d.getDiaryLikeCount())
                    .commentCount(d.getDiaryCommentCount())
                    .viewCount(d.getDiaryViewCount())
                    .member(MemberResponseDto.MemberSortDto.builder().memberId(diarymember.getMemberId()).profileImgURL(diarymember.getProfileImg()).name(diarymember.getName()).build())
                    .createdDate(d.getCreatedAt())
                    .isAnonymous(d.getIsAnonymous())
                    .isMyPost(isMyPost)
                    .isLiked(isLiked)
                    .isBookmarked(isBookmarked)
                    .build();
            DiaryFile diaryFile = diaryFileRepository.findByDiary(d);
            if(diaryFile != null){
                String url = diaryFile.getDiaryOriginalUrl();
                List<String> list = Arrays.asList(url.split(","));
                result.setThumbnailImgURL(list.get(d.getThumbnailIndex()));
            }
            sortList.add(result);
        }
        return sortList;
    }


    // 한 페이지에 5개씩
    public List<Diary> getPage(int page, String sort, String search){
        if(sort == "likecount"){
            Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "diaryLikeCount"));
            Page<Diary> likesort = diaryRepository.findByDiaryContentContainingIgnoreCase(search, pageable);

            return likesort.getContent();
        } else if (sort == "viewcount") {
            Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "diaryViewCount"));
            Page<Diary> viewsort = diaryRepository.findByDiaryContentContainingIgnoreCase(search, pageable);
            return viewsort.getContent();
        } else{
            Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
            Page<Diary> datesort = diaryRepository.findByDiaryContentContainingIgnoreCase(search, pageable);
            return datesort.getContent();
        }
    }

    public List<Diary> findAll(int page, String sort){
        if(sort == "likecount"){
            Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "diaryLikeCount"));
            Page<Diary> likesort = diaryRepository.findAll(pageable);
            return likesort.getContent();
        } else if (sort == "viewcount") {
            Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "diaryViewCount"));
            Page<Diary> viewsort = diaryRepository.findAll(pageable);
            return viewsort.getContent();
        } else{
            Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
            Page<Diary> datesort = diaryRepository.findAll(pageable);
            return datesort.getContent();
        }
    }



}
