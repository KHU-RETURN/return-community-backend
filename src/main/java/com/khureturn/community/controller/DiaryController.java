package com.khureturn.community.controller;


import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.dto.DiaryConverter;
import com.khureturn.community.dto.DiaryRequestDto;
import com.khureturn.community.dto.DiaryResponseDto;
import com.khureturn.community.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;

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

    @GetMapping("/{postId}")
    public ResponseEntity<DiaryResponseDto.DiaryDto> getDiary(@PathVariable(name = "postId") Long postId){
        Diary diary = diaryService.findById(postId);
        return ResponseEntity.ok(DiaryConverter.toDiaryDto(diary));
    }

}
