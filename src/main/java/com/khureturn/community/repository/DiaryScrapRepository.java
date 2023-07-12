package com.khureturn.community.repository;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryLike;
import com.khureturn.community.domain.diary.DiaryScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiaryScrapRepository extends JpaRepository<DiaryScrap, Long> {

//    boolean existsDiaryScrapByMemberAndDiary(Long memberId, Long diaryId);
//
//    Optional<DiaryScrap> findByMemberAndDiary(Long memberId, Long diaryId);
}
