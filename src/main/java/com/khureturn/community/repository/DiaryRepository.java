package com.khureturn.community.repository;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Optional<Diary> findById(Long diaryId);
    void deleteById(Long diaryId);
    List<Diary> findAllByMember(Long memberId);

    boolean existsByMember(Member member);

    @Query("select d from Diary d where d.diaryContent like %?1%")
    Page<Diary> findByDiaryContentContainingIgnoreCase(String search, Pageable pageable);

    Page<Diary> findAll(Pageable pageable);



}
