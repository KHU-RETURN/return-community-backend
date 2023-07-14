package com.khureturn.community.controller;

import com.khureturn.community.domain.Member;
import com.khureturn.community.service.DiaryScrapService;
import com.khureturn.community.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryScrapController {

    private final DiaryScrapService diaryScrapService;
    private final MemberService memberService;

    @PostMapping("/{postId}/bookmark")
    public ResponseEntity<Void> createLike(Principal principal, @PathVariable(name = "postId")Long postId){
        String name = principal.getName();
        Member member = memberService.findByName(name);
        diaryScrapService.diaryScrap(postId, member);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}/bookmark")
    public ResponseEntity<Void> deleteLike(Principal principal, @PathVariable(name = "postId")Long postId){
        String name = principal.getName();
        Member member = memberService.findByName(name);
        diaryScrapService.diaryUnScrap(postId, member);
        return ResponseEntity.ok().build();
    }

}
