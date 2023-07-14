package com.khureturn.community.repository;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryCommentRepository extends JpaRepository<DiaryComment, Long> {

    List<DiaryComment> findAllByDiary(Long diaryId);

    void deleteByDiaryAndId(Long diaryId, Long diaryCommentId);

    DiaryComment findByIdAndDiary(Long diaryCommentId, Diary diary);

    int countAllByDiary(Long diaryId);


}
