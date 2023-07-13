package com.khureturn.community.service;


import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryComment;
import com.khureturn.community.dto.DiaryCommentRequestDto;
import com.khureturn.community.repository.DiaryCommentRepository;
import com.khureturn.community.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryCommentService {

    private final DiaryRepository diaryRepository;

    private final DiaryCommentRepository diaryCommentRepository;

    public DiaryComment create(Long postId, DiaryCommentRequestDto.CreateCommentDto request){
        Diary diary = diaryRepository.findById(postId).get();

        DiaryComment diaryComment = DiaryComment.builder()
                .diaryCommentContent(request.getContent())
                .build();
        return diaryCommentRepository.save(diaryComment);
    }

    public DiaryComment update(Long postId, Long commentId, DiaryCommentRequestDto.UpdateCommentDto request){
        DiaryComment diaryComment = diaryCommentRepository.findDiaryCommentByDiaryAndDiaryComment(postId, commentId);
        diaryComment.update(request.getContent());
        return diaryComment;
    }


}
