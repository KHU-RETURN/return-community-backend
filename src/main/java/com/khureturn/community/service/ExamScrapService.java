package com.khureturn.community.service;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.exam.Exam;
import com.khureturn.community.domain.exam.ExamScrap;
import com.khureturn.community.exception.DuplicateInsertionException;
import com.khureturn.community.exception.NotFoundException;
import com.khureturn.community.repository.ExamRepository;
import com.khureturn.community.repository.ExamScrapRepository;
import com.khureturn.community.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ExamScrapService {
    private final MemberRepository memberRepository;
    private final ExamRepository examRepository;
    private final ExamScrapRepository examScrapRepository;

    public void examScrap(Long examId, Principal principal){
        Member member = memberRepository.findByGoogleSub(principal.getName())
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new NotFoundException("시험 정보를 찾을 수 없습니다"));
        if(examScrapRepository.existsByMemberAndExam(member, exam)){
            throw new DuplicateInsertionException("이미 스크랩이 존재합니다.");
        }

        ExamScrap examScrap = ExamScrap.builder()
                .member(member)
                .exam(exam)
                .build();

        examScrapRepository.save(examScrap);
        exam.increaseScrap();

    }

    public void examUnScrap(Long examId, Principal principal){
        Member member = memberRepository.findByGoogleSub(principal.getName())
                .orElseThrow(()-> new NotFoundException("유저를 찾을 수 없습니다"));
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new NotFoundException("시험 정보를 찾을 수 없습니다"));
        ExamScrap examScrap = examScrapRepository.findByMemberAndExam(member, exam)
                .orElseThrow(() -> new NotFoundException("스크랩을 찾을 수 없습니다"));

        examScrapRepository.delete(examScrap);
        exam.decreaseScrap();

    }
}
