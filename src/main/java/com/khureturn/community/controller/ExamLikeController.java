package com.khureturn.community.controller;

import com.khureturn.community.service.ExamLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exam")
public class ExamLikeController {

    private final ExamLikeService examLikeService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{examId}/like")
    public ResponseEntity<Void> createLike(Principal principal, @PathVariable(name = "examId")Long examId){
        examLikeService.examLike(examId, principal);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{examId}/like")
    public ResponseEntity<Void> deleteLike(Principal principal, @PathVariable(name = "examId")Long examId){
        examLikeService.examUnlike(examId, principal);
        return ResponseEntity.ok().build();
    }

}
