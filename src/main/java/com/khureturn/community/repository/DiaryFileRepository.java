package com.khureturn.community.repository;

import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryFileRepository extends JpaRepository<DiaryFile, Long> {
    DiaryFile findByDiary(Diary diary);
}
