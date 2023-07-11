package com.khureturn.community.service;


import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryLike;
import com.khureturn.community.exception.DuplicateInsertionException;
import com.khureturn.community.exception.NotFoundException;
import com.khureturn.community.repository.DiaryLikeRepository;
import com.khureturn.community.repository.DiaryRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiaryLikeService {
    private final DiaryRepository diaryRepository;
    private final DiaryLikeRepository diaryLikeRepository;

    public void diaryLike(Long diaryId, Member member){
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NotFoundException("Diary를 찾을 수 없습니다"));
        if(diaryLikeRepository.existsDiaryLikesByMemberIdAndDiaryId(member, diary)){
            throw new DuplicateInsertionException("이미 좋아요가 존재합니다.");
        }

        DiaryLike diaryLike = DiaryLike.builder()
                .memberId(member)
                .diaryId(diary)
                .build();

        diaryLikeRepository.save(diaryLike);

    }

    public void diaryUnlike(Long diaryId, Member member){
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NotFoundException("Diary를 찾을 수 없습니다"));
        DiaryLike diaryLike = diaryLikeRepository.findByMemberIdAndDiaryId(member, diary)
                .orElseThrow(() -> new NotFoundException("좋아요를 찾을 수 없습니다"));

        diaryLikeRepository.delete(diaryLike);

    }
}
