package com.khureturn.community.repository;

import com.khureturn.community.domain.diary.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Optional<Diary> findById(Long diaryId);
    void deleteById(Long diaryId);
    List<Diary> findAllByMember(Long memberId);

    boolean existsByMember(Long memberId);

    Page<Diary> findByIdLessThanOrderByCreatedAtDesc(Long cursorId, PageRequest pageRequest);

    Page<Diary> findByDiaryContentContainingIgnoreCaseAndIdLessThanOrderByDiaryLikeCountDesc(String search, Long cursorId, PageRequest pageRequest);

    Page<Diary> findByDiaryContentContainingIgnoreCaseAndIdLessThanOrderByDiaryViewCountDesc(String search, Long cursorId, PageRequest pageRequest);

}
