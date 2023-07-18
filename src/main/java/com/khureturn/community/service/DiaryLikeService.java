package com.khureturn.community.service;


import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryLike;
import com.khureturn.community.exception.DuplicateInsertionException;
import com.khureturn.community.exception.NotFoundException;
import com.khureturn.community.repository.DiaryLikeRepository;
import com.khureturn.community.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DiaryLikeService {
    private final DiaryRepository diaryRepository;
    private final DiaryLikeRepository diaryLikeRepository;

    public void diaryLike(Long diaryId, Member member){
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NotFoundException("Diary를 찾을 수 없습니다"));
        if(diaryLikeRepository.existsDiaryLikeByMemberAndDiary(member.getMemberId(), diaryId)){
            throw new DuplicateInsertionException("이미 좋아요가 존재합니다.");
        }

        DiaryLike diaryLike = DiaryLike.builder()
                .member(member)
                .diary(diary)
                .build();

        diaryLikeRepository.save(diaryLike);
        diary.increaseLike();

    }

    public void diaryUnlike(Long diaryId, Member member){
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NotFoundException("Diary를 찾을 수 없습니다"));
        DiaryLike diaryLike = diaryLikeRepository.findByMemberAndDiary(member.getMemberId(), diaryId)
                .orElseThrow(() -> new NotFoundException("좋아요를 찾을 수 없습니다"));

        diaryLikeRepository.delete(diaryLike);
        diary.decreaseLike();

    }

    public Boolean findDiaryLikeByMemberAndDiary(Long memberId, Long diaryId){
        Boolean isLiked = diaryLikeRepository.existsDiaryLikeByMemberAndDiary(memberId, diaryId);
        return isLiked;
    }

}