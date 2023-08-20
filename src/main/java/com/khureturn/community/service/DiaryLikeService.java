package com.khureturn.community.service;


import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryLike;
import com.khureturn.community.exception.DuplicateInsertionException;
import com.khureturn.community.exception.NotFoundException;
import com.khureturn.community.repository.DiaryLikeRepository;
import com.khureturn.community.repository.DiaryRepository;
import com.khureturn.community.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.security.Principal;


@Service
@RequiredArgsConstructor
public class DiaryLikeService {
    private final DiaryRepository diaryRepository;
    private final DiaryLikeRepository diaryLikeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void diaryLike(Long diaryId, Principal principal){
        Member member = memberRepository.findByGoogleSub(principal.getName())
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NotFoundException("Diary를 찾을 수 없습니다"));
        if(diaryLikeRepository.existsDiaryLikeByMemberAndDiary(member, diary)){
            throw new DuplicateInsertionException("이미 좋아요가 존재합니다.");
        }

        DiaryLike diaryLike = DiaryLike.builder()
                .member(member)
                .diary(diary)
                .build();

        diaryLikeRepository.save(diaryLike);
        diary.increaseLike();

    }


    @Transactional
    public void diaryUnlike(Long diaryId, Principal principal){
        Member member = memberRepository.findByGoogleSub(principal.getName())
                .orElseThrow(()-> new NotFoundException("유저를 찾을 수 없습니다"));

        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NotFoundException("Diary를 찾을 수 없습니다"));
        DiaryLike diaryLike = diaryLikeRepository.findByMemberAndDiary(member, diary)
                .orElseThrow(() -> new NotFoundException("좋아요를 찾을 수 없습니다"));

        diaryLikeRepository.delete(diaryLike);
        diary.decreaseLike();

    }


}
