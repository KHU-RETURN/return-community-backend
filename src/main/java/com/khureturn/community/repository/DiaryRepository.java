package com.khureturn.community.repository;

import com.khureturn.community.domain.diary.Diary;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DiaryRepository extends JpaRepository<Diary, Long> {

}
