package com.khureturn.community.service;

import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.repository.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DiaryService {
    @Autowired
    private DiaryRepository diaryRepository;

    public void saveDiary(Diary diary){
        diaryRepository.save(diary);
    }

    @Transactional
    public Long update(Long id){
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new
                        IllegalArgumentException("해당 일기장이 존재하지 않습니다."));
        diary.update(diary.getDiary_title(), diary.getDiary_content());
        return id;
    }

    public List<Diary> findDiary() {return diaryRepository.findAll();}
    public Optional<Diary> findById(Long id) {return diaryRepository.findById(id);}

    public Long deleteDiary(Long id) {
        diaryRepository.deleteById(id);
        return id;
    }

}
