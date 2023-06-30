package com.khureturn.community.service;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryFile;
import com.khureturn.community.dto.DiaryConverter;
import com.khureturn.community.dto.DiaryRequestDto;
import com.khureturn.community.repository.DiaryFileRepository;
import com.khureturn.community.repository.DiaryRepository;
import com.khureturn.community.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService{

    private final DiaryRepository diaryRepository;
    private final DiaryFileRepository diaryFileRepository;

    @Transactional
    @Override
    public Diary create(DiaryRequestDto.CreateDiaryDto request, Member member){
        Diary diary = DiaryConverter.toDiary(request, member);
        return diaryRepository.save(diary);
    }

    @Transactional
    @Override
    public DiaryFile create(DiaryRequestDto.CreateDiaryDto request,Diary diary){
        DiaryFile diaryFile = DiaryConverter.toDiaryFile(request, diary);
        return diaryFileRepository.save(diaryFile);
    }

    @Transactional
    @Override
    public Long update(Long diaryId, DiaryRequestDto.UpdateDiaryDto request){
        Diary diary = diaryRepository.findById(diaryId).get();
        diary.update(request.getTitle(), request.getContent(), request.getIsAnonymous());
        return diaryId;
    }

    @Transactional
    @Override
    public void delete(Long diaryId){
        diaryRepository.deleteById(diaryId);
    }

    @Override
    public Diary findById(Long diaryId){
        return diaryRepository.findById(diaryId).get();
    }


    @Override
    public List<Diary> findAllByMember(Member member){
        List<Diary> diaries = diaryRepository.findAllByMember(member.getMemberId());
        return diaries;

    }

    @Override
    public List<Diary> findAll(){
        return diaryRepository.findAll();
    }
}
