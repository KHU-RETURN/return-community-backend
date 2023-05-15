package com.khureturn.community.service;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryComment;
import com.khureturn.community.repository.DiaryCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiaryCommentService {

    @Autowired
    private DiaryCommentRepository diaryCommentRepository;

    private DiaryService diaryService;
    private MemberService memberService;

    public void saveDiaryComment(DiaryComment diaryComment){
        diaryCommentRepository.save(diaryComment);
    }

    public Long update(Long id){
        DiaryComment diaryComment = diaryCommentRepository.findById(id)
                .orElseThrow(() -> new
                        IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        diaryComment.update(diaryComment.getComment_content());
        return id;
    }

    public List<DiaryComment> findAllCommentsInDiary(Long id){
        Optional<Diary> diary = diaryService.findById(id);
        return diaryCommentRepository.findByDiary(diary);
    }

    public DiaryComment findCommentByMemberAndDiary(Long memberId, Long diaryId){
        Optional<Member> member = memberService.findOne(memberId);
        Optional<Diary> diary = diaryService.findById(diaryId);
        return diaryCommentRepository.findByMemberAndDiary(member, diary)
                .orElseThrow(()-> new
                        IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
    }


}
