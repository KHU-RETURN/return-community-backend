package com.khureturn.community.service;


import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryScrap;
import com.khureturn.community.exception.DuplicateInsertionException;
import com.khureturn.community.exception.NotFoundException;
import com.khureturn.community.repository.DiaryRepository;
import com.khureturn.community.repository.DiaryScrapRepository;
import com.khureturn.community.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class DiaryScrapService {

    private final DiaryRepository diaryRepository;
    private final DiaryScrapRepository diaryScrapRepository;
    private final MemberRepository memberRepository;

    public void diaryScrap(Long diaryId, Principal principal){
        Member member = memberRepository.findByGoogleSub(principal.getName())
                .orElseThrow(()-> new NotFoundException("유저를 찾을 수 없습니다."));

        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NotFoundException("Diary를 찾을 수 없습니다"));
        if(diaryScrapRepository.existsDiaryScrapByMemberAndDiary(member, diary)){
            throw new DuplicateInsertionException("이미 스크랩이 완료되었습니다.");
        }

        DiaryScrap diaryScrap = DiaryScrap.builder()
                .member(member)
                .diary(diary)
                .build();

        diaryScrapRepository.save(diaryScrap);
        diary.increaseScrap();

    }

    public void diaryUnScrap(Long diaryId, Principal principal){
        Member member = memberRepository.findByGoogleSub(principal.getName())
                .orElseThrow(()-> new NotFoundException("유저를 찾을 수 없습니다."));

        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NotFoundException("Diary를 찾을 수 없습니다"));
        DiaryScrap diaryScrap = diaryScrapRepository.findByMemberAndDiary(member, diary)
                .orElseThrow(() -> new NotFoundException("스크랩을 찾을 수 없습니다"));

        diaryScrapRepository.delete(diaryScrap);
        diary.decreaseScrap();

    }

}
