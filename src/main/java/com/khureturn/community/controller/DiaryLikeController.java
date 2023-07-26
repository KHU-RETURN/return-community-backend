package com.khureturn.community.controller;


import com.khureturn.community.domain.Member;
import com.khureturn.community.service.DiaryLikeService;
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
public class DiaryLikeController {

    private final DiaryLikeService diaryLikeService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> createLike(Principal principal, @PathVariable(name = "postId")Long postId){
        String name = principal.getName();
        Member member = memberService.findByName(name);
        diaryLikeService.diaryLike(postId, member);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{postId}/like")
    public ResponseEntity<Void> deleteLike(Principal principal, @PathVariable(name = "postId")Long postId){
        String name = principal.getName();
        Member member = memberService.findByName(name);
        diaryLikeService.diaryUnlike(postId, member);
        return ResponseEntity.ok().build();
    }

}
