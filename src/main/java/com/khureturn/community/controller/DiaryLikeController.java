package com.khureturn.community.controller;


import com.khureturn.community.domain.Member;
import com.khureturn.community.security.UserDetails.PrincipalDetails;
import com.khureturn.community.security.UserDetails.PrincipalService;
import com.khureturn.community.service.DiaryLikeService;
import com.khureturn.community.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryLikeController {

    private final DiaryLikeService diaryLikeService;
    private final MemberService memberService;

    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> createLike(@PathVariable(name = "postId")Long postId, @AuthenticationPrincipal Member member){
        Member member1 = member;
        diaryLikeService.diaryLike(postId, member1);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}/like")
    public ResponseEntity<Void> deleteLike(@PathVariable(name = "postId")Long postId, @AuthenticationPrincipal Member member){
        Member member1 = member;
        diaryLikeService.diaryUnlike(postId, member1);
        return ResponseEntity.ok().build();
    }

}
