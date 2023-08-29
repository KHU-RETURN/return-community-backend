package com.khureturn.community.service;

import com.khureturn.community.domain.common.MediaType;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryFile;
import com.khureturn.community.exception.NotFoundException;
import com.khureturn.community.repository.DiaryFileRepository;
import com.khureturn.community.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiaryFileService {

    public final DiaryFileRepository diaryFileRepository;
    public final DiaryRepository diaryRepository;

    public static DiaryFile fileUpload(MultipartFile media, Diary diary) throws IOException {

        String rootPath = System.getProperty("user.dir") + "/src/main/webapp/WEB-INF";
        String fileDir = "/static/thumbnailDiaryMedia";
        String filePath = rootPath + fileDir;
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        MediaType mediaType;
        String extension = Objects.requireNonNull(media.getOriginalFilename()).substring(media.getOriginalFilename().lastIndexOf(".") + 1);
        if (extension.equals("png") || extension.equals("jpeg") || extension.equals("gif") || extension.equals("jpg") || extension.equals("PNG") || extension.equals("JPEG") || extension.equals("GIF") || extension.equals("JPG")) {
            mediaType = MediaType.IMAGE;
        } else {
            mediaType = MediaType.VIDEO;
        }
        UUID uuid = UUID.randomUUID();
        String savedMediaName = uuid.toString() + "_" + media.getOriginalFilename();
        File saveMedia = new File(filePath, savedMediaName);
        media.transferTo(saveMedia);

        DiaryFile diaryFile = DiaryFile.builder()
                .mediaType(mediaType)
                .diaryOriginalUrl(fileDir + "/" + savedMediaName)
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
