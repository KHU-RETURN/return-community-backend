package com.khureturn.community.controller;

import com.khureturn.community.domain.Member;
import com.khureturn.community.service.DiaryScrapService;
import com.khureturn.community.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryScrapController {

    private final DiaryScrapService diaryScrapService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{postId}/bookmark")
    public ResponseEntity<Void> createLike(Principal principal, @PathVariable(name = "postId")Long postId){

        diaryScrapService.diaryScrap(postId, principal);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{postId}/bookmark")
    public ResponseEntity<Void> deleteLike(Principal principal, @PathVariable(name = "postId")Long postId){
        diaryScrapService.diaryUnScrap(postId, principal);
        return ResponseEntity.ok().build();
    }

}
