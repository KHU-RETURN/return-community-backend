package com.khureturn.community.repository;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryComment;
import com.khureturn.community.domain.diary.DiaryLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiaryLikeRepository extends JpaRepository<DiaryLike, Long> {
    boolean existsDiaryLikeByMemberAndDiary(Long memberId, Long diaryId);

    Optional<DiaryLike> findByMemberAndDiary(Long memberId, Long diaryId);
}
