package com.khureturn.community.service;


import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryLike;
import com.khureturn.community.domain.diary.DiaryScrap;
import com.khureturn.community.exception.DuplicateInsertionException;
import com.khureturn.community.exception.NotFoundException;
import com.khureturn.community.repository.DiaryLikeRepository;
import com.khureturn.community.repository.DiaryRepository;
import com.khureturn.community.repository.DiaryScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryScrapService {

//    private final DiaryRepository diaryRepository;
//    private final DiaryScrapRepository diaryScrapRepository;
//
//    public void diaryScrap(Long diaryId, Member member){
//        Diary diary = diaryRepository.findById(diaryId)
//                .orElseThrow(() -> new NotFoundException("Diary를 찾을 수 없습니다"));
//        if(diaryScrapRepository.existsDiaryScrapByMemberIdAndDiaryId(member.getMemberId(), diaryId)){
//            throw new DuplicateInsertionException("이미 스크랩이 완료되었습니다.");
//        }
//
//        DiaryScrap diaryScrap = DiaryScrap.builder()
//                .member(member)
//                .diary(diary)
//                .build();
//
//        diaryScrapRepository.save(diaryScrap);
//
//    }
//
//    public void diaryUnScrap(Long diaryId, Member member){
//        Diary diary = diaryRepository.findById(diaryId)
//                .orElseThrow(() -> new NotFoundException("Diary를 찾을 수 없습니다"));
//        DiaryScrap diaryScrap = diaryScrapRepository.findByMemberIdAndDiaryId(member.getMemberId(), diaryId)
//                .orElseThrow(() -> new NotFoundException("스크랩을 찾을 수 없습니다"));
//
//        diaryScrapRepository.delete(diaryScrap);
//
//    }
}
