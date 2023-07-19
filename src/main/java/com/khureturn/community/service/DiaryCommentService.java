package com.khureturn.community.service;


import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryComment;
import com.khureturn.community.dto.DiaryCommentRequestDto;
import com.khureturn.community.exception.NotFoundException;
import com.khureturn.community.repository.DiaryCommentRepository;
import com.khureturn.community.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryCommentService {

    private final DiaryRepository diaryRepository;

    private final DiaryCommentRepository diaryCommentRepository;

    public DiaryComment create(Long diaryId, DiaryCommentRequestDto.CreateCommentDto request, Principal principal){
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NotFoundException("Diary를 찾을 수 없습니다"));
        String diaryHashtag = String.join(",", request.getHashtagList());

        DiaryComment diaryComment = DiaryComment.builder()
                .diaryCommentContent(request.getContent())
                .diaryHashtag(diaryHashtag)
                .diary(diary)
                .member((Member) principal)
                .build();

        return diaryCommentRepository.save(diaryComment);
    }

    public DiaryComment update(Long diaryId, Long diaryCommentId, DiaryCommentRequestDto.UpdateCommentDto request){
        Diary diary = diaryRepository.findById(diaryId).get();
        DiaryComment diaryComment = diaryCommentRepository.findByIdAndDiary(diaryCommentId, diary);
        diaryComment.update(request.getContent());
        return diaryComment;
    }

    public List<DiaryComment> findAllByDiary(Long diaryId){
        return diaryCommentRepository.findAllByDiary(diaryId);
    }

    public void delete(Long diaryId, Long diaryCommentId){
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NotFoundException("Diary를 찾을 수 없습니다"));;
        diaryCommentRepository.deleteByDiaryAndId(diaryId, diaryCommentId);
    }


}
