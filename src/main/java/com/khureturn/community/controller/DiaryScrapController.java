package com.khureturn.community.controller;

import com.khureturn.community.domain.Member;
import com.khureturn.community.service.DiaryLikeService;
import com.khureturn.community.service.DiaryScrapService;
import com.khureturn.community.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryScrapController {

    private final DiaryScrapService diaryScrapService;
    private final MemberService memberService;

//    @PostMapping("/{postId}/bookmark")
//    public ResponseEntity<Void> createLike(@PathVariable(name = "postId")Long postId){
//        Member memberId =
//        diaryScrapService.diaryScrap(postId, memberId);
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/{postId}/bookmark")
//    public ResponseEntity<Void> deleteLike(@PathVariable(name = "postId")Long postId){
//        Member memberId =
//        diaryScrapService.diaryUnScrap(postId, memberId);
//        return ResponseEntity.ok().build();
//    }

}
