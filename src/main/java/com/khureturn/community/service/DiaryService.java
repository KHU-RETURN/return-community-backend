package com.khureturn.community.service;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryFile;
import com.khureturn.community.dto.DiaryRequestDto;

import java.util.List;

public interface DiaryService {

    Diary create(DiaryRequestDto.CreateDiaryDto request, Member member);
    public DiaryFile create(DiaryRequestDto.CreateDiaryDto request, Diary diary);
    Long update(Long diaryId, DiaryRequestDto.UpdateDiaryDto request);
    void delete(Long diaryId);
    Diary findById(Long diaryId);

    List<Diary> findAll();
    List<Diary> findAllByMember(Member member);
}
