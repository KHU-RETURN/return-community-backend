package com.khureturn.community.controller;

import com.khureturn.community.domain.exam.Exam;
import com.khureturn.community.domain.exam.ExamFile;
import com.khureturn.community.dto.ExamRequestDto;
import com.khureturn.community.dto.ExamResponseDto;
import com.khureturn.community.service.ExamFileService;
import com.khureturn.community.service.ExamService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/exam")
public class ExamController {

    private final ExamService examService;
    private final ExamFileService examFileService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ExamResponseDto.CreateExamDto> createExam(@RequestPart(value = "fileList", required= false) List<MultipartFile> fileList,
                                                                    @RequestPart(value="data") String data, Principal principal) throws IOException{
        Exam exam = examService.create(fileList, data, principal);
        return ResponseEntity.ok(ExamResponseDto.CreateExamDto.builder().examId(exam.getId()).build());
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{examId}")
    public ResponseEntity<ExamResponseDto.UpdateExamDto> updateExam(@PathVariable(name = "examId") Long examId, @RequestBody ExamRequestDto.UpdateExamDto request){
        Exam exam = examService.update(examId, request);
        return ResponseEntity.ok(ExamResponseDto.UpdateExamDto.builder().examId(exam.getId()).build());
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{examId}")
    public ResponseEntity<Void> deleteExam(@PathVariable(name = "examId")Long examId){
        examService.delete(examId);
        return ResponseEntity.ok().build();
    }

    // 시험 정보 상세 조회
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{examId}")
    public ResponseEntity<ExamResponseDto.ExamDto> getExam(@PathVariable(name = "examId")Long examId, Principal principal){
        List<ExamFile> fileList = examFileService.findAllByExam(examId);
        return ResponseEntity.ok(examService.getExam(examId, fileList, principal));
    }

    // 시험 정보 정렬
    @PreAuthorize("isAuthenticated()")
    @GetMapping("")
    @Operation(description = "좋아요순의 정렬을 원할 경우 sort에 likecount 조회수순의 정렬을 원할 경우 sort에 viewcount")
    public ResponseEntity<List<ExamResponseDto.ExamSortDto>> getExamList(@RequestParam(name = "page")int page,
                                                                         @RequestParam(name = "search", required = false) String search,
                                                                         @RequestParam(name = "sort", defaultValue = "createdAt")String sort,
                                                                         Principal principal){

        if(search != null){
            List<Exam> examList = examService.getPage(page-1, sort, search);
            return ResponseEntity.ok(examService.findExamSort(examList, principal));
        } else{
            List<Exam> examList = examService.findAll(page-1, sort);
            return ResponseEntity.ok(examService.findExamSort(examList, principal));
        }
    }






}
