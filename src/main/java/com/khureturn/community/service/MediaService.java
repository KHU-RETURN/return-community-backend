package com.khureturn.community.service;

import com.khureturn.community.domain.common.MediaType;
import com.khureturn.community.domain.common.UploadType;
import com.khureturn.community.exception.Media.InvalidMediaType;
import com.khureturn.community.exception.ServerInternalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;

import java.util.*;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MediaService {
    private final String MAIN_DIR_NAME = System.getProperty("user.dir") + "/src/main/webapp/WEB-INF";
    private final String SUB_DIR_NAME = "/static";




    public String uploadProfileImg(MultipartFile media, UploadType uploadType) {

        String mediaURL = "";
        try {
            File folder = new File(MAIN_DIR_NAME + SUB_DIR_NAME + uploadType.getDirName());

            if (!folder.exists()) {
                folder.mkdirs();
            }

            String fileName = media.getOriginalFilename();
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            String generateFileName;
            MediaType mediaType = findMediaType(extension);
            if (mediaType.equals(MediaType.IMAGE)) {
                generateFileName = UUID.randomUUID().toString() + "." + extension;
            } else {
                throw new InvalidMediaType();
            }
            mediaURL = SUB_DIR_NAME + uploadType.getDirName() + File.separator + generateFileName;
            String destinationPath = MAIN_DIR_NAME + mediaURL;
            File destination = new File(destinationPath);
            media.transferTo(destination);

        } catch (ServerInternalException | IOException e) {
            log.error("error: " + e.getMessage());
            throw new ServerInternalException(e.getMessage());

        }
        return mediaURL;

    }





    public MediaType findMediaType(String fileName) {

        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        List<String> videoExtension = Arrays.asList("mp4", "mov", "mpg", "mpeg", "rm", "vob");

        if (videoExtension.contains(extension)) {

            return MediaType.VIDEO;

        } else {

            return MediaType.IMAGE;

        }
    }





}
