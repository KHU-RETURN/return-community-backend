package com.khureturn.community.service;

import com.khureturn.community.domain.exam.Exam;
import com.khureturn.community.domain.exam.ExamFile;
import com.khureturn.community.exception.NotFoundException;
import com.khureturn.community.repository.ExamFileRepository;
import com.khureturn.community.repository.ExamRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExamFileService {

    private final ExamRepository examRepository;
    private final ExamFileRepository examFileRepository;

    public static ExamFile fileUpload(MultipartFile file, Exam exam) throws IOException {

        String rootPath = System.getProperty("user.dir") + "/src/main/webapp/WEB-INF";
        String fileDir = "/static/examFiles";
        String filePath = rootPath + fileDir;
        UUID uuid = UUID.randomUUID();
        String savedFileName = uuid.toString() + "_" + file.getOriginalFilename();
        File saveFile = new File(filePath, savedFileName);
        if(!saveFile.exists()){
            saveFile.mkdirs();
        }
        file.transferTo(saveFile);
        ExamFile examFile = ExamFile.builder()
                .examFileUrl(fileDir + "/" + savedFileName)
                .examFileName(savedFileName)
                .exam(exam)
                .build();
        return examFile;

    }

    public List<ExamFile> findAllByExam(Long examId){
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new NotFoundException("족보를 찾을 수 없습니다."));
        return examFileRepository.findAllByExam(exam);
    }

}
