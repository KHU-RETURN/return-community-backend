package com.khureturn.community.service;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.exam.Exam;
import com.khureturn.community.domain.exam.ExamLike;
import com.khureturn.community.exception.DuplicateInsertionException;
import com.khureturn.community.exception.NotFoundException;
import com.khureturn.community.repository.ExamLikeRepository;
import com.khureturn.community.repository.ExamRepository;
import com.khureturn.community.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ExamLikeService {

    private final MemberRepository memberRepository;
    private final ExamRepository examRepository;
    private final ExamLikeRepository examLikeRepository;

    public void examLike(Long examId, Principal principal){
        Member member = memberRepository.findByGoogleSub(principal.getName())
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new NotFoundException("시험 정보를 찾을 수 없습니다"));
        if(examLikeRepository.existsByMemberAndExam(member, exam)){
            throw new DuplicateInsertionException("이미 좋아요가 존재합니다.");
        }

        ExamLike examLike = ExamLike.builder()
                .member(member)
                .exam(exam)
                .build();

        examLikeRepository.save(examLike);
        exam.increaseLike();

    }

    public void examUnlike(Long examId, Principal principal){
        Member member = memberRepository.findByGoogleSub(principal.getName())
                .orElseThrow(()-> new NotFoundException("유저를 찾을 수 없습니다"));
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new NotFoundException("시험 정보를 찾을 수 없습니다"));
        ExamLike examLike = examLikeRepository.findByMemberAndExam(member, exam)
                .orElseThrow(() -> new NotFoundException("좋아요를 찾을 수 없습니다"));

        examLikeRepository.delete(examLike);
        exam.decreaseLike();

    }
}
