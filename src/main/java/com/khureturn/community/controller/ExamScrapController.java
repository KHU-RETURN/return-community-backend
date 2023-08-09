package com.khureturn.community.controller;

import com.khureturn.community.service.ExamScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exam")
public class ExamScrapController {

    private final ExamScrapService examScrapService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{examId}/bookmark")
    public ResponseEntity<Void> createScrap(Principal principal, @PathVariable(name = "examId")Long examId){
        examScrapService.examScrap(examId, principal);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{examId}/bookmark")
    public ResponseEntity<Void> deleteScrap(Principal principal, @PathVariable(name = "examId")Long examId){
        examScrapService.examUnScrap(examId, principal);
        return ResponseEntity.ok().build();
    }
}
