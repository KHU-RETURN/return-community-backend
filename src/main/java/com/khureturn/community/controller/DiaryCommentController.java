package com.khureturn.community.controller;

import com.khureturn.community.domain.diary.DiaryComment;
import com.khureturn.community.dto.DiaryCommentRequestDto;
import com.khureturn.community.dto.DiaryCommentResponseDto;
import com.khureturn.community.dto.DiaryConverter;
import com.khureturn.community.service.DiaryCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DiaryCommentController {

    private final DiaryCommentService diaryCommentService;

    @PostMapping("/diary/{postId}/comment")
    public ResponseEntity<DiaryCommentResponseDto.CreateDiaryCommentDto> createComment(Principal principal, @PathVariable(name = "postId")Long postId, @RequestBody DiaryCommentRequestDto.CreateCommentDto request){
        DiaryComment diaryComment = diaryCommentService.create(postId, request, principal);
        return ResponseEntity.ok(DiaryCommentResponseDto.CreateDiaryCommentDto.builder().commentId(diaryComment.getId()).build());
    }

    @PatchMapping("/diary/{postId}/comment/{commentId}")
    public ResponseEntity<DiaryCommentResponseDto.UpdateDiaryCommentDto> updateComment(@PathVariable(name = "postId")Long postId, @PathVariable(name = "commentId")Long commentId, @RequestBody DiaryCommentRequestDto.UpdateCommentDto request){
        DiaryComment diaryComment = diaryCommentService.update(postId, commentId, request);
        return ResponseEntity.ok(DiaryCommentResponseDto.UpdateDiaryCommentDto.builder().commentId(diaryComment.getId()).build());
    }

    // 댓글 조회
    @GetMapping("/diary/{postId}/comment")
    public ResponseEntity<List<DiaryCommentResponseDto.CommentDto>> getComment(@PathVariable(name = "postId")Long postId){
        List<DiaryComment> commentList = diaryCommentService.findAllByDiary(postId);
        return ResponseEntity.ok(DiaryConverter.toCommentListDto(commentList));
    }

    @DeleteMapping("/diary/{postId}/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable(name = "postId")Long postId, @PathVariable(name = "commentId")Long commentId){
        diaryCommentService.delete(postId,commentId);
        return ResponseEntity.ok().build();
    }



}