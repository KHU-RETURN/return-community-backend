package com.khureturn.community.service;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.exam.Exam;
import com.khureturn.community.domain.exam.ExamFile;
import com.khureturn.community.dto.ExamRequestDto;
import com.khureturn.community.dto.ExamResponseDto;
import com.khureturn.community.dto.MemberResponseDto;
import com.khureturn.community.dto.converter.JacksonUtil;
import com.khureturn.community.exception.NotFoundException;
import com.khureturn.community.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final MemberRepository memberRepository;
    private final ExamFileRepository examFileRepository;
    private final ExamLikeRepository examLikeRepository;
    private final ExamScrapRepository examScrapRepository;

    @Transactional
    public Exam create(List<MultipartFile> fileList, String examCreateDto, Principal principal) throws IOException {
        Member member = memberRepository.findByGoogleSub(principal.getName())
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));
        JacksonUtil jacksonUtil = new JacksonUtil();
        ExamRequestDto.CreateExamDto request = (ExamRequestDto.CreateExamDto) jacksonUtil.strToObj(examCreateDto, ExamRequestDto.CreateExamDto.class);
        Exam exam = Exam.builder()
                .examTitle(request.getTitle())
                .examContent(request.getContent())
                .isAnonymous(request.getIsAnonymous())
                .member(member)
                .build();
        examRepository.save(exam);

        if(fileList != null){
            for(MultipartFile file: fileList){
                ExamFile examFile = ExamFile.builder()
                        .examFileUrl(ExamFileService.fileUpload(file))
                        .exam(exam)
                        .build();
                examFileRepository.save(examFile);

            }
        }
        return exam;
    }

    @Transactional
    public Exam update(Long examId, ExamRequestDto.UpdateExamDto request){
        Exam exam = examRepository.findById(examId)
                .orElseThrow(()-> new NotFoundException("시험 정보를 찾을 수 없습니다."));
        exam.update(request.getTitle(), request.getContent(), request.getIsAnonymous());
        return exam;
    }

    @Transactional
    public void delete(Long examId){
        examRepository.findById(examId)
                .orElseThrow(()-> new NotFoundException("시험 정보를 찾을 수 없습니다."));
        examRepository.deleteById(examId);
    }

    public ExamResponseDto.ExamDto getExam(Long examId, List<ExamFile> examFileList, Principal principal){

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new NotFoundException("시험 정보를 찾을 수 없습니다."));
        Member nowMember = memberRepository.findByGoogleSub(principal.getName())
                .orElseThrow(()-> new NotFoundException("유저를 찾을 수 없습니다."));
        Member examMember = exam.getMember();

        Boolean isLiked = examLikeRepository.existsByMemberAndExam(nowMember, exam);
        Boolean isBookmarked = examScrapRepository.existsByMemberAndExam(nowMember, exam);
        Boolean isMyPost = examRepository.existsByMember(nowMember);

        ExamResponseDto.ExamDto result = ExamResponseDto.ExamDto.builder()
                .examId(exam.getId())
                .title(exam.getExamTitle())
                .content(exam.getExamContent())
                .createdDate(exam.getCreatedAt())
                .modifiedDate(exam.getUpdateAt())
                .likedCount(exam.getExamLikeCount())
                .bookmarkedCount(exam.getExamScrapCount())
                .viewCount(exam.getExamViewCount())
                .isLiked(isLiked)
                .isBookmarked(isBookmarked)
                .isAnonymous(exam.getIsAnonymous())
                .isMyPost(isMyPost)
                .member(MemberResponseDto.MemberSortDto.builder().memberId(examMember.getMemberId()).profileImgURL(examMember.getProfileImg()).name(examMember.getName()).nickname(examMember.getNickname()).build())
                .build();

        if(examFileList != null){
            List<String> urlList = new ArrayList<>();
            for(ExamFile file: examFileList){
                String url = file.getExamFileUrl();
                urlList.add(url);
                result.setFileList(urlList);
            }
        }

        return result;
    }

    // 한 페이지에 5개씩
    public List<Exam> getPage(int page, String sort, String search){
        if(Objects.equals(sort, "likecount")){
            Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "examLikeCount"));
            Page<Exam> likesort = examRepository.findByExamContentContainingIgnoreCaseOrExamTitleContainingIgnoreCase(search, pageable);

            return likesort.getContent();
        } else if (Objects.equals(sort, "viewcount")) {
            Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "examViewCount"));
            Page<Exam> viewsort = examRepository.findByExamContentContainingIgnoreCaseOrExamTitleContainingIgnoreCase(search, pageable);
            return viewsort.getContent();
        } else{
            Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
            Page<Exam> datesort = examRepository.findByExamContentContainingIgnoreCaseOrExamTitleContainingIgnoreCase(search, pageable);
            return datesort.getContent();
        }
    }

    public List<Exam> findAll(int page, String sort){
        if(Objects.equals(sort, "likecount")){
            Pageable pageable = PageRequest.of(page, 5, Sort.by( Sort.Direction.DESC,"examLikeCount"));
            Page<Exam> likesort = examRepository.findAll(pageable);
            return likesort.getContent();
        } else if (Objects.equals(sort, "viewcount")) {
            Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "examViewCount"));
            Page<Exam> viewsort = examRepository.findAll(pageable);
            return viewsort.getContent();
        } else{
            Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
            Page<Exam> datesort = examRepository.findAll(pageable);
            return datesort.getContent();
        }
    }

    public List<ExamResponseDto.ExamSortDto> findExamSort(List<Exam> examList, Principal principal){
        Member nowMember = memberRepository.findByGoogleSub(principal.getName())
                .orElseThrow(()-> new NotFoundException("유저를 찾을 수 없습니다."));
        List<ExamResponseDto.ExamSortDto> sortList = new ArrayList<>();
        for(Exam exam: examList){
            Member examMember = exam.getMember();
            Boolean isLiked = examLikeRepository.existsByMemberAndExam(nowMember, exam);
            Boolean isBookmarked = examScrapRepository.existsByMemberAndExam(nowMember, exam);
            Boolean isMyPost = examRepository.existsByMember(nowMember);
            ExamResponseDto.ExamSortDto result = ExamResponseDto.ExamSortDto.builder()
                    .examId(exam.getId())
                    .title(exam.getExamTitle())
                    .likeCount(exam.getExamLikeCount())
                    .viewCount(exam.getExamViewCount())
                    .member(MemberResponseDto.MemberSortDto.builder().memberId(examMember.getMemberId()).profileImgURL(examMember.getProfileImg()).name(examMember.getName()).build())
                    .createdDate(exam.getCreatedAt())
                    .isAnonymous(exam.getIsAnonymous())
                    .isMyPost(isMyPost)
                    .isLiked(isLiked)
                    .isBookmarked(isBookmarked)
                    .build();
            sortList.add(result);
        }
        return sortList;
    }


}
