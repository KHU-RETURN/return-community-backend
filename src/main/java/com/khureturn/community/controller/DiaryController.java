package com.khureturn.community.controller;


import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryFile;
import com.khureturn.community.dto.DiaryRequestDto;
import com.khureturn.community.dto.DiaryResponseDto;
import com.khureturn.community.service.DiaryFileService;
import com.khureturn.community.service.DiaryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value ="", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<DiaryResponseDto.CreateDiaryDto> createDiary(@RequestPart(value = "mediaList", required= false) List<MultipartFile> mediaList,
                                                                       @RequestPart(value = "data")String data, Principal principal) throws IOException {

        Diary diary = diaryService.create(mediaList, data, principal);
        return ResponseEntity.ok(DiaryResponseDto.CreateDiaryDto.builder().postId(diary.getId()).build());
    }


    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{postId}")
    public ResponseEntity<DiaryResponseDto.UpdateDiaryDto> updateDiary(@PathVariable(name = "postId")Long postId, @RequestBody DiaryRequestDto.UpdateDiaryDto data){
        Diary diary = diaryService.update(postId, data);
        return ResponseEntity.ok(DiaryResponseDto.UpdateDiaryDto.builder().postId(diary.getId()).build());
    }


    // 일기장 상세조회
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{postId}")
    public ResponseEntity<DiaryResponseDto.DiaryDto> getDiary(@PathVariable(name = "postId") Long postId, Principal principal){
        Diary diary = diaryService.findById(postId);
        DiaryFile diaryFile = diaryFileService.findByDiary(postId);
        return ResponseEntity.ok(diaryService.findDiary(diary, diaryFile, principal));
    }



    // 일기장 정렬
    @PreAuthorize("isAuthenticated()")
    @GetMapping("")
    @Operation(description = "좋아요순의 정렬을 원할 경우 sort에 likecount 조회수순의 정렬을 원할 경우 sort에 viewcount")
    public ResponseEntity<List<DiaryResponseDto.DiarySortDto>> getDiaryList(@RequestParam(name = "page") int page,
                                                                            @RequestParam(name = "search", required = false) String search,
                                                                            @RequestParam(name = "sort", defaultValue = "createdAt") String sort, Principal principal){
        if(search != null){
            List<Diary> diaryList = diaryService.getPage(page-1, sort, search);
            return ResponseEntity.ok(diaryService.findDiarySort(diaryList, principal));
        } else{
            List<Diary> diaryList = diaryService.findAll(page-1, sort);
            return ResponseEntity.ok(diaryService.findDiarySort(diaryList, principal));
        }
    }


    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deleteDiary(@PathVariable(name = "postId")Long postId){
        diaryService.delete(postId);
        return ResponseEntity.ok().build();
    }


}
