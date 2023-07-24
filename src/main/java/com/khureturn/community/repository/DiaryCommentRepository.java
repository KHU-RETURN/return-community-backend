package com.khureturn.community.repository;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryCommentRepository extends JpaRepository<DiaryComment, Long> {

    List<DiaryComment> findAllByDiary(Diary diary);

    void deleteByDiaryAndId(Diary diary, Long diaryCommentId);

    DiaryComment findByIdAndDiary(Long diaryCommentId, Diary diary);

    DiaryComment findByIdAndDiaryAndParent(Long recommentId, Diary diary, DiaryComment parent);

    int countAllByParent(DiaryComment parent);

    void deleteByDiaryAndParentAndId(Diary diary, DiaryComment parent, Long recommendId);

    List<DiaryComment> findAllByDiaryAndParent(Diary diary, DiaryComment parent);



}
