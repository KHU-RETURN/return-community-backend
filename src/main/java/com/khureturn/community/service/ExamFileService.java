package com.khureturn.community.service;

import com.khureturn.community.domain.exam.ExamFile;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@NoArgsConstructor
public class ExamFileService {

    public static String fileUpload(MultipartFile file) throws IOException {

        String rootPath = System.getProperty("user.dir") + "/src/main/webapp/WEB-INF";
        String fileDir = rootPath + "/static/examFiles";
        UUID uuid = UUID.randomUUID();
        String savedFileName = uuid.toString() + "_" + file.getOriginalFilename();
        File saveFile = new File(fileDir, savedFileName);
        if(!saveFile.exists()){
            saveFile.mkdir();
        }
        file.transferTo(saveFile);
        return saveFile.getPath();

    }
}
