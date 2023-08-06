package com.khureturn.community.controller;

import com.khureturn.community.domain.exam.Exam;
import com.khureturn.community.domain.exam.ExamFile;
import com.khureturn.community.dto.ExamRequestDto;
import com.khureturn.community.dto.ExamResponseDto;
import com.khureturn.community.service.ExamFileService;
import com.khureturn.community.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exam")
public class ExamController {

    private final ExamService examService;
    private final ExamFileService examFileService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ExamResponseDto.CreateExamDto> createExam(Principal principal, @RequestPart(value = "fileList", required= false) List<MultipartFile> fileList,
                                                                    @RequestPart(value="data") ExamRequestDto.CreateExamDto data) throws IOException{
        Exam exam = examService.create(fileList, data, principal);
        return ResponseEntity.ok(ExamResponseDto.CreateExamDto.builder().examId(exam.getId()).build());
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{examId}")
    public ResponseEntity<ExamResponseDto.UpdateExamDto> updateExam(@PathVariable(name = "examId") Long examId, @RequestBody ExamRequestDto.UpdateExamDto request){
        Exam exam = examService.update(examId, request);
        return ResponseEntity.ok(ExamResponseDto.UpdateExamDto.builder().examId(exam.getId()).build());
    }

    @DeleteMapping("/{examId}")
    public ResponseEntity<Void> deleteExam(@PathVariable(name = "examId")Long examId){
        examService.delete(examId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{examId}")
    public ResponseEntity<ExamResponseDto.ExamDto> getExam(@PathVariable(name = "examId")Long examId, Principal principal){
        List<ExamFile> fileList = examFileService.findAllByExam(examId);
        return ResponseEntity.ok(examService.getExam(examId, fileList, principal));
    }
}
