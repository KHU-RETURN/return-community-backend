package com.khureturn.community.service;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.exam.Exam;
import com.khureturn.community.domain.exam.ExamFile;
import com.khureturn.community.dto.ExamRequestDto;
import com.khureturn.community.dto.ExamResponseDto;
import com.khureturn.community.dto.MemberResponseDto;
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
    public Exam create(List<MultipartFile> fileList, ExamRequestDto.CreateExamDto request, Principal principal) throws IOException {
        Member member = memberRepository.findByName(principal.getName());
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

    @Transactional
    public void delete(Long examId){
        examRepository.findById(examId)
                .orElseThrow(()-> new NotFoundException("족보를 찾을 수 없습니다."));
        examRepository.deleteById(examId);
    }

    public ExamResponseDto.ExamDto getExam(Long examId, List<ExamFile> examFileList, Principal principal){
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new NotFoundException("족보를 찾을 수 없습니다."));
        Member nowMember = memberRepository.findByName(principal.getName());
        Member examMember = exam.getMember();
        Boolean isMyPost = examRepository.existsByMember(nowMember);
        ExamResponseDto.ExamDto result = ExamResponseDto.ExamDto.builder()
                .examId(exam.getId())
                .title(exam.getExamTitle())
                .content(exam.getExamContent())
                .createdDate(exam.getCreatedAt())
                .modifiedDate(exam.getUpdateAt())
                .isAnonymous(exam.getIsAnonymous())
                .isMyPost(isMyPost)
                .member(MemberResponseDto.MemberSortDto.builder().memberId(examMember.getMemberId()).profileImgURL(examMember.getProfileImg()).name(examMember.getName()).nickname(examMember.getNickname()).build())
                .build();
        if(examFileList != null){
            List<String> urlList = new ArrayList<>();
            for(ExamFile file: examFileList){
                String url = file.getExamFileUrl();
                urlList.add(url);
                result.setFileList(urlList);
            }
        }
        return result;
    }







}
