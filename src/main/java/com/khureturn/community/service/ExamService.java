package com.khureturn.community.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.exam.Exam;
import com.khureturn.community.domain.exam.ExamFile;
import com.khureturn.community.dto.ExamRequestDto;
import com.khureturn.community.dto.ExamResponseDto;
import com.khureturn.community.dto.MemberResponseDto;
import com.khureturn.community.dto.converter.JacksonUtil;
import com.khureturn.community.exception.NotFoundException;
import com.khureturn.community.repository.ExamFileRepository;
import com.khureturn.community.repository.ExamRepository;
import com.khureturn.community.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;

    private final MemberRepository memberRepository;
    private final ExamFileRepository examFileRepository;

    @Transactional
    public Exam create(List<MultipartFile> fileList, String examCreateDto, Principal principal) throws IOException {
        Member member = memberRepository.findByName(principal.getName());
        JacksonUtil jacksonUtil = new JacksonUtil();
        ExamRequestDto.CreateExamDto request = (ExamRequestDto.CreateExamDto) jacksonUtil.strToObj(examCreateDto, ExamRequestDto.CreateExamDto.class);
        Exam exam = Exam.builder()
                .examTitle(request.getTitle())
                .examContent(request.getContent())
                .isAnonymous(request.getIsAnonymous())
                .member(member)
                .build();
        examRepository.save(exam);

        if(fileList != null){
            for(MultipartFile file: fileList){
                ExamFile examFile = ExamFile.builder()
                        .examFileUrl(ExamFileService.fileUpload(file))
                        .exam(exam)
                        .build();
                examFileRepository.save(examFile);

            }
        }
        return exam;
    }

    @Transactional
    public Exam update(Long examId, ExamRequestDto.UpdateExamDto request){
        Exam exam = examRepository.findById(examId)
                .orElseThrow(()-> new NotFoundException("족보를 찾을 수 없습니다."));
        exam.update(request.getTitle(), request.getContent(), request.getIsAnonymous());
        return exam;
    }


}
