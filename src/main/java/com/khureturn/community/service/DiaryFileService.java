package com.khureturn.community.service;

import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryFile;
import com.khureturn.community.repository.DiaryFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryFileService {

    public final DiaryFileRepository diaryFileRepository;

    public static String fileUpload(List<MultipartFile> medias) throws IOException {

        String rootPath = System.getProperty("user.dir");
        String fileDir = rootPath + "/medias/";
        List<String> list = new ArrayList<>();
        for(MultipartFile media: medias){
            File saveMedia = new File(fileDir, media.getOriginalFilename());
            media.transferTo(saveMedia);
            list.add(saveMedia.getPath());
        }
        return String.join(",",list);

    }

    public DiaryFile findByDiary(Long diaryId){
        return diaryFileRepository.findByDiary(diaryId);

    }
}
