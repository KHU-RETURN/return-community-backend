package com.khureturn.community.service;

import com.khureturn.community.domain.exam.Exam;
import com.khureturn.community.domain.exam.ExamFile;
import com.khureturn.community.exception.NotFoundException;
import com.khureturn.community.repository.ExamFileRepository;
import com.khureturn.community.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExamFileService {

    private final ExamRepository examRepository;
    private final ExamFileRepository examFileRepository;

    public static String fileUpload(MultipartFile file) throws IOException {

        String rootPath = System.getProperty("user.dir") + "/src/main/webapp/WEB-INF";
        String fileDir = rootPath + "/static/examFiles";
        UUID uuid = UUID.randomUUID();
        String savedFileName = uuid.toString() + "_" + file.getOriginalFilename();
        File saveFile = new File(fileDir, savedFileName);
        if(!saveFile.exists()){
            saveFile.mkdirs();
        }
        file.transferTo(saveFile);
        return saveFile.getPath();

    }

    public List<ExamFile> findAllByExam(Long examId){
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new NotFoundException("족보를 찾을 수 없습니다."));
        return examFileRepository.findAllByExam(exam);
    }
}
