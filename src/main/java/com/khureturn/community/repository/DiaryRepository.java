package com.khureturn.community.repository;

import com.khureturn.community.domain.diary.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Optional<Diary> findById(Long diaryId);
    void deleteById(Long diaryId);
<<<<<<< HEAD
    List<Diary> findAllByMemberId(Long memberId);
=======
    List<Diary> findAllByMember(Long memberId);
>>>>>>> f3efc04018e9c94e60ee77a0deb5d92d7c626e2e

}
