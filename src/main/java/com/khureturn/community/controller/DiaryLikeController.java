package com.khureturn.community.controller;


import com.khureturn.community.domain.Member;
import com.khureturn.community.service.DiaryLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryLikeController {

    private final DiaryLikeService diaryLikeService;

    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> createLike(@PathVariable(name = "postId")Long postId){
        Member memberId =
    }

}
