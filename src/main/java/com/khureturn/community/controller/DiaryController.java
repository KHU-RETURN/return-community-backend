package com.khureturn.community.controller;


import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryFile;
import com.khureturn.community.dto.DiaryConverter;
import com.khureturn.community.dto.DiaryRequestDto;
import com.khureturn.community.dto.DiaryResponseDto;
import com.khureturn.community.service.DiaryFileService;
import com.khureturn.community.service.DiaryService;
import com.khureturn.community.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.ModCheck;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;

    private final DiaryFileService diaryFileService;

    private final MemberService memberService;

    @PostMapping("")
    public ResponseEntity<DiaryResponseDto.CreateDiaryDto> createDiary(@RequestPart(value = "mediaList", required= false)List<MultipartFile> mediaList,
                                                                       @RequestPart(value = "data")DiaryRequestDto.CreateDiaryDto data) throws IOException {

        Diary diary = diaryService.create(mediaList, data);
        return ResponseEntity.ok(DiaryResponseDto.CreateDiaryDto.builder().postId(diary.getId()).build());
    }

    @PutMapping("/{postId}")
    public ResponseEntity<DiaryResponseDto.UpdateDiaryDto> updateDiary(@PathVariable(name = "postId")Long postId, @RequestBody DiaryRequestDto.UpdateDiaryDto data){
        Diary diary = diaryService.update(postId, data);
        return ResponseEntity.ok(DiaryResponseDto.UpdateDiaryDto.builder().postId(diary.getId()).build());
    }

    // 일기장 상세조회
    @GetMapping("/{postId}")
    public ResponseEntity<DiaryResponseDto.DiaryDto> getDiary(Principal principal, @PathVariable(name = "postId") Long postId){
        Diary diary = diaryService.findById(postId);
        DiaryFile diaryFile = diaryFileService.findByDiary(postId);
        String name = principal.getName();
        Member member = memberService.findByName(name);
        return ResponseEntity.ok(DiaryConverter.toDiaryDto(diary, diaryFile, member));
    }

    // 일기장 메인화면 (기본 최신순)
    @GetMapping("")
    public ResponseEntity<DiaryResponseDto.DiarySortDto> getDiaryList(Principal principal, @RequestParam(name = "cursor")int cursor, @RequestParam(name = "size")int size){
        List<Diary> diaryList = diaryService.getPage((long) cursor,size);
        String name = principal.getName();
        Member member = memberService.findByName(name);
        return ResponseEntity.ok((DiaryResponseDto.DiarySortDto) DiaryConverter.toDiarySortDto(diaryList, member));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deleteDiary(@PathVariable(name = "postId")Long postId){
        diaryService.delete(postId);
        return ResponseEntity.ok().build();
    }


}
