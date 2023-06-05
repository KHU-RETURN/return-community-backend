package com.khureturn.community.repository;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryComment;
import com.khureturn.community.domain.diary.DiaryLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryLikeRepository extends JpaRepository<DiaryLike, Long> {
    DiaryLike findByMemberAndDiary(Member member, Diary diary);
}
