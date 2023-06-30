package com.khureturn.community.repository;

import com.khureturn.community.domain.diary.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Optional<Diary> findById(Long diaryId);
    void deleteById(Long diaryId);
    List<Diary> findAllByMember(Long memberId);

}
