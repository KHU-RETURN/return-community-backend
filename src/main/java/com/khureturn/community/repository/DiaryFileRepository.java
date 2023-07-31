package com.khureturn.community.repository;

import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryFileRepository extends JpaRepository<DiaryFile, Long> {


    DiaryFile findByDiary(Diary diary);
}
