package com.khureturn.community.service;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryComment;
import com.khureturn.community.dto.DiaryCommentRequestDto;
import com.khureturn.community.dto.DiaryCommentResponseDto;
import com.khureturn.community.dto.MemberResponseDto;
import com.khureturn.community.exception.NotFoundException;
import com.khureturn.community.repository.DiaryCommentRepository;
import com.khureturn.community.repository.DiaryRepository;
import com.khureturn.community.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryCommentService {

    private final DiaryRepository diaryRepository;
    private final DiaryCommentRepository diaryCommentRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public DiaryComment create(Long diaryId, DiaryCommentRequestDto.CreateCommentDto request, Principal principal){

        Member member = memberRepository.findByGoogleSub(principal.getName())
                .orElseThrow(()-> new NotFoundException("유저를 찾을 수 없습니다."));
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NotFoundException("Diary를 찾을 수 없습니다"));
        String diaryHashtag = String.join(",", request.getHashtagList());

        DiaryComment diaryComment = DiaryComment.builder()
                .diaryCommentContent(request.getContent())
                .diaryHashtag(diaryHashtag)
                .diary(diary)
                .member(member)
                .build();

        return diaryCommentRepository.save(diaryComment);
    }

    @Transactional
    public DiaryComment update(Long diaryId, Long diaryCommentId, DiaryCommentRequestDto.UpdateCommentDto request){
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(()-> new NotFoundException("Diary를 찾을 수 없습니다."));
        DiaryComment diaryComment = diaryCommentRepository.findByIdAndDiary(diaryCommentId, diary);
        diaryComment.update(request.getContent());
        return diaryComment;
    }

    @Transactional
    public void delete(Long diaryId, Long diaryCommentId){
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NotFoundException("Diary를 찾을 수 없습니다"));
        DiaryComment diaryComment = diaryCommentRepository.findByIdAndDiary(diaryCommentId, diary);
        diaryCommentRepository.delete(diaryComment);
    }

    @Transactional
    public DiaryComment createReComment(Principal principal, Long diaryId, Long commentId, DiaryCommentRequestDto.CreateRecommentDto request){
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(()-> new NotFoundException("Diary를 찾을 수 없습니다."));
        Member member = memberRepository.findByGoogleSub(principal.getName())
                .orElseThrow(()-> new NotFoundException("유저를 찾을 수 없습니다."));
        DiaryComment diaryComment = diaryCommentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
        DiaryComment diaryRecomment = DiaryComment.builder()
                .diaryCommentContent(request.getContent())
                .parent(diaryComment)
                .diary(diary)
                .member(member)
                .build();

        return diaryCommentRepository.save(diaryRecomment);

    }

    @Transactional
    public DiaryComment updateReComment(Long diaryId, Long commentId, Long recommentId, DiaryCommentRequestDto.UpdateCommentDto request){
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(()-> new NotFoundException("Diary를 찾을 수 없습니다."));
        DiaryComment parent = diaryCommentRepository.findByIdAndDiary(commentId, diary);
        DiaryComment reComment = diaryCommentRepository.findByIdAndDiaryAndParent(recommentId, diary, parent);
        reComment.update(request.getContent());
        return reComment;

    }

    public List<DiaryCommentResponseDto.CommentDto> getCommentList(Long postId){
        List<DiaryCommentResponseDto.CommentDto> list = new ArrayList<>();
        Diary diary = diaryRepository.findById(postId)
                .orElseThrow(()-> new NotFoundException("일기장이 존재하지 않습니다."));
        List<DiaryComment> diaryCommentList = diaryCommentRepository.findAllByDiaryAndParent(diary, null);
        for(DiaryComment c: diaryCommentList){
            Member member = c.getMember();
            int reCommentCount = diaryCommentRepository.countAllByParent(c);
            List<DiaryComment> recommentList = diaryCommentRepository.findAllByDiaryAndParent(diary, c);
            List<DiaryCommentResponseDto.ReCommentDto> recommentResultList = new ArrayList<>();
            for(DiaryComment re: recommentList){
                DiaryCommentResponseDto.ReCommentDto result = DiaryCommentResponseDto.ReCommentDto.builder()
                        .recommentId(re.getId())
                        .content(re.getDiaryCommentContent())
                        .user(MemberResponseDto.MemberSortDto.builder().memberId(re.getMember().getMemberId()).profileImgURL(re.getMember().getProfileImg()).name(re.getMember().getName()).nickname(re.getMember().getNickname()).build())
                        .createdDate(re.getCreatedAt())
                        .build();
                recommentResultList.add(result);
            }
            DiaryCommentResponseDto.CommentDto comment = DiaryCommentResponseDto.CommentDto.builder()
                    .commentId(c.getId())
                    .content(c.getDiaryCommentContent())
                    .recommentCount(reCommentCount)
                    .recomments(recommentResultList)
                    .user(MemberResponseDto.MemberSortDto.builder().memberId(member.getMemberId()).profileImgURL(member.getProfileImg()).name(member.getName()).nickname(member.getNickname()).build())
                    .createdDate(c.getCreatedAt())
                    .build();
            list.add(comment);
        }
        return list;
    }

    @Transactional
    public void deleteRecomment(Long diaryId, Long commentId, Long recommentId){
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NotFoundException("Diary를 찾을 수 없습니다."));
        DiaryComment parent = diaryCommentRepository.findByIdAndDiary(commentId, diary);
        DiaryComment recomment = diaryCommentRepository.findByIdAndDiaryAndParent(recommentId, diary, parent);
        diaryCommentRepository.delete(recomment);

    }


}
