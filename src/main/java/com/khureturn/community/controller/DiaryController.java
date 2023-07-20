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
    public ResponseEntity<DiaryResponseDto.CreateDiaryDto> createDiary(Principal principal, @RequestPart(value = "mediaList", required= false)List<MultipartFile> mediaList,
                                                                       @RequestPart(value = "data")DiaryRequestDto.CreateDiaryDto data) throws IOException {


        Diary diary = diaryService.create(mediaList, data, principal);
        return ResponseEntity.ok(DiaryResponseDto.CreateDiaryDto.builder().postId(diary.getId()).build());
    }

    @PutMapping("/{postId}")
    public ResponseEntity<DiaryResponseDto.UpdateDiaryDto> updateDiary(@PathVariable(name = "postId")Long postId, @RequestBody DiaryRequestDto.UpdateDiaryDto data){
        Diary diary = diaryService.update(postId, data);
        return ResponseEntity.ok(DiaryResponseDto.UpdateDiaryDto.builder().postId(diary.getId()).build());
    }

    // 일기장 상세조회
    @GetMapping("/{postId}")
    public ResponseEntity<DiaryResponseDto.DiaryDto> getDiary(@PathVariable(name = "postId") Long postId){
        Diary diary = diaryService.findById(postId);
        DiaryFile diaryFile = diaryFileService.findByDiary(postId);
        Member member = diary.getMember();
        return ResponseEntity.ok(DiaryConverter.toDiaryDto(diary, diaryFile, member));
    }

    // 일기장 메인화면 (기본 최신순)
    @GetMapping("/list")
    public ResponseEntity<List<DiaryResponseDto.DiarySortDto>> getDiaryList(@RequestParam(name = "cursor")int cursor, @RequestParam(name = "size")int size){
        List<Diary> diaryList = diaryService.getPage((long) cursor,size);
        return ResponseEntity.ok(DiaryConverter.toDiarySortDto(diaryList));
    }

    // 일기장 좋아요순
    @GetMapping("/likelist")
    public ResponseEntity<List<DiaryResponseDto.DiarySortDto>> getDiaryListByLike(@RequestParam(name = "cursor")int cursor,
                                                                            @RequestParam(name = "size")int size, @RequestParam(name = "search", required = false)String search,
                                                                            @RequestParam(name = "sort", defaultValue = "likecount")String sort){
        List<Diary> diaryList = diaryService.getPageByLike((long)cursor, size, search);
        return ResponseEntity.ok(DiaryConverter.toDiarySortDto(diaryList));

    }

    // 일기장 조회수순
    @GetMapping("/viewlist")
    public ResponseEntity<List<DiaryResponseDto.DiarySortDto>> getDiaryListByView(@RequestParam(name = "cursor")int cursor,
                                                                            @RequestParam(name = "size")int size, @RequestParam(name = "search", required = false)String search,
                                                                            @RequestParam(name = "sort", defaultValue = "viewcount")String sort){
        List<Diary> diaryList = diaryService.getPageByView((long)cursor, size, search);
        return ResponseEntity.ok(DiaryConverter.toDiarySortDto(diaryList));

    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deleteDiary(@PathVariable(name = "postId")Long postId){
        diaryService.delete(postId);
        return ResponseEntity.ok().build();
    }


}
