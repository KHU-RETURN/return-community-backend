package com.khureturn.community.service;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryFile;
import com.khureturn.community.dto.DiaryConverter;
import com.khureturn.community.dto.DiaryRequestDto;
import com.khureturn.community.repository.DiaryFileRepository;
import com.khureturn.community.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService{

    private final DiaryRepository diaryRepository;
    private final DiaryFileRepository diaryFileRepository;

    @Transactional
    public Diary create(List<MultipartFile> mediaList, DiaryRequestDto.CreateDiaryDto request) throws IOException {

        Diary diary = DiaryConverter.toDiary(request);
        diaryRepository.save(diary);
        DiaryFile diaryFile = DiaryConverter.toDiaryFile(request, DiaryFileService.fileUpload(mediaList));
        diaryFileRepository.save(diaryFile);
        return diary;
    }

    @Transactional
    public Diary update(Long diaryId, DiaryRequestDto.UpdateDiaryDto request){
        Diary diary = diaryRepository.findById(diaryId).get();
        diary.update(request.getTitle(), request.getContent(), request.getIsAnonymous());
        return diary;
    }

    @Transactional
    public void delete(Long diaryId){
        diaryRepository.deleteById(diaryId);
    }

    public Diary findById(Long diaryId){
        return diaryRepository.findById(diaryId).get();
    }



    public List<Diary> findAllByMember(Member member){
        List<Diary> diaries = diaryRepository.findAllByMember(member.getMemberId());
        return diaries;

    }

    public List<Diary> findAll(){
        return diaryRepository.findAll();
    }
}
