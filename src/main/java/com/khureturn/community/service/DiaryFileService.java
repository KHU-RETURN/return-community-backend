package com.khureturn.community.service;

import com.khureturn.community.domain.common.MediaType;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryFile;
import com.khureturn.community.exception.NotFoundException;
import com.khureturn.community.repository.DiaryFileRepository;
import com.khureturn.community.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiaryFileService {

    public final DiaryFileRepository diaryFileRepository;
    public final DiaryRepository diaryRepository;

    public static DiaryFile fileUpload(MultipartFile media, Diary diary) throws IOException {

        String rootPath = System.getProperty("user.dir") + "/src/main/webapp/WEB-INF";
        String fileDir = rootPath + "/static/thumbnailDiaryMedia";
        MediaType mediaType = null;
        if(media.getContentType().startsWith("image")){
            mediaType = MediaType.IMAGE;
        }else{
            mediaType = MediaType.VIDEO;
        }
        UUID uuid = UUID.randomUUID();
        String savedMediaName = uuid.toString() +"_" +media.getOriginalFilename();
        File saveMedia = new File(fileDir, savedMediaName);
        if(!saveMedia.exists()){
            saveMedia.mkdirs();
        }
        media.transferTo(saveMedia);
        DiaryFile diaryFile = DiaryFile.builder()
                .mediaType(mediaType)
                .diaryOriginalUrl(saveMedia.getPath())
                .diary(diary)
                .build();

        return diaryFile;
    }
    public DiaryFile findByDiary(Long diaryId){
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NotFoundException("Diary를 찾을 수 없습니다."));
        return diaryFileRepository.findByDiary(diary);

    }

}
