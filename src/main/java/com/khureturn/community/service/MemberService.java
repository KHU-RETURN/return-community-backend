package com.khureturn.community.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.common.UploadType;
import com.khureturn.community.dto.MemberRequestDto;
import com.khureturn.community.dto.MemberResponseDto;
import com.khureturn.community.exception.ErrCode;
import com.khureturn.community.exception.Member.FailedMemberInfo;
import com.khureturn.community.exception.Member.InvalidMemberInfoException;
import com.khureturn.community.exception.NotFoundException;
import com.khureturn.community.exception.ServerInternalException;
import com.khureturn.community.exception.authentication.InvalidAccessTokenException;
import com.khureturn.community.repository.MemberRepository;
import com.khureturn.community.security.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final RedisService redisService;

    private final JwtProvider jwtProvider;

    private final MediaService mediaService;


    public ResponseEntity<?> signUp(String signUpRequest, MultipartFile profileImg, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {


        MemberRequestDto.SignUpRequestDto signUpRequestDto = transformSignUpInformationToObject(signUpRequest);

        validateSignUpInformation(signUpRequestDto);

        String profileImgURL = null;


        if (profileImg != null) {
            profileImgURL = mediaService.uploadProfileImg(profileImg, UploadType.PROFILE_IMG);
        }

        Member member = Member.builder().build();
        member.transferSignUpDtoToMember(signUpRequestDto, profileImgURL);

        memberRepository.save(member);

        HttpHeaders httpHeaders = new HttpHeaders();
        String accessToken = jwtProvider.createAccessToken(signUpRequestDto.getGoogleSub());
        httpHeaders.add("Authorization", accessToken);


        MemberResponseDto.MemberBriefInfoDto memberBriefInfoDto = responseBriefInformation(member);

        return new ResponseEntity<MemberResponseDto.MemberBriefInfoDto>(memberBriefInfoDto, httpHeaders, HttpStatus.CREATED);
    }


    public MemberResponseDto.MemberBriefInfoDto responseBriefInformation(Member member) {
        return MemberResponseDto.MemberBriefInfoDto.builder()
                .memberId(member.getMemberId())
                .studentId(member.getStudentId())
                .nickname(member.getNickname())
                .name(member.getName())
                .profileImgURL(member.getProfileImg())
                .build();
    }

    private MemberRequestDto.SignUpRequestDto transformSignUpInformationToObject(String signUpRequest) {

        ObjectMapper mapper = new ObjectMapper();
        MemberRequestDto.SignUpRequestDto signUpRequestDto;

        try {
            signUpRequestDto = mapper.readerFor(MemberRequestDto.SignUpRequestDto.class).readValue(signUpRequest);
        } catch (Exception e) {
            throw new ServerInternalException(e.getMessage());
        }

        return signUpRequestDto;
    }


    private MemberRequestDto.UpdateRequestDto transformUpdateInformationToObject(String updateRequest) {

        ObjectMapper mapper = new ObjectMapper();
        MemberRequestDto.UpdateRequestDto updateRequestDto;

        try {
            updateRequestDto = mapper.readerFor(MemberRequestDto.UpdateRequestDto.class).readValue(updateRequest);
        } catch (Exception e) {
            throw new ServerInternalException(e.getMessage());
        }

        return updateRequestDto;
    }







    public void validateUpdateInformation(MemberRequestDto.UpdateRequestDto updateRequestDto, String googleSub) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<MemberRequestDto.UpdateRequestDto>> violations = validator.validate(updateRequestDto);

        for (ConstraintViolation<MemberRequestDto.UpdateRequestDto> violation : violations) {
            throw new MissingFormatArgumentException(String.valueOf(violation.getPropertyPath()));
        }

        validatePhoneNumber(updateRequestDto.getPhoneNumber(), googleSub);
        validateEmail(updateRequestDto.getEmail(), googleSub);
        validateNickname(updateRequestDto.getNickname(), googleSub);

    }

    public void validateSignUpInformation(MemberRequestDto.SignUpRequestDto signUpRequestDto) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<MemberRequestDto.SignUpRequestDto>> violations = validator.validate(signUpRequestDto);

        for (ConstraintViolation<MemberRequestDto.SignUpRequestDto> violation : violations) {
            throw new MissingFormatArgumentException(String.valueOf(violation.getPropertyPath()));
        }

        validatePhoneNumber(signUpRequestDto.getPhoneNumber(), null);
        validateEmail(signUpRequestDto.getEmail(), null);
        validateNickname(signUpRequestDto.getNickname(), null);

    }


    public void validatePhoneNumber(String phoneNumber, String googleSub) {

        if (googleSub != null) {
            String currentUserPhoneNumber = memberRepository.findByGoogleSub(googleSub).orElseThrow(InvalidAccessTokenException::new).getPhoneNumber();
            if (currentUserPhoneNumber.equals(phoneNumber)) {
                return;
            }
        }

        if (!Pattern.matches("^010\\d{8}$", phoneNumber)) {
            throw new InvalidMemberInfoException(FailedMemberInfo.PHONENUMBER.getInvalid_format_errorCode());
        }

        Optional<Member> member = memberRepository.findByPhoneNumber(phoneNumber);
        if (member.isPresent()) {
            throw new InvalidMemberInfoException(FailedMemberInfo.PHONENUMBER.getDuplicated_errorCode());
        }
    }

    public void validateEmail(String email, String googleSub) {

        if (googleSub != null) {
            String currentUserEmail = memberRepository.findByGoogleSub(googleSub).orElseThrow(InvalidAccessTokenException::new).getEmail();
            if (currentUserEmail.equals(email)) {
                return;
            }
        }

        if (!Pattern.matches("^[A-Za-z0-9._%+-]+@khu\\.ac\\.kr$", email)) {
            throw new InvalidMemberInfoException(FailedMemberInfo.EMAIL.getInvalid_format_errorCode());
        }

        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            throw new InvalidMemberInfoException(FailedMemberInfo.EMAIL.getDuplicated_errorCode());
        }
    }



    public void validateNickname(String nickname, String googleSub){
        if(googleSub != null){
            String  currentNickname = memberRepository.findByGoogleSub(googleSub).orElseThrow(InvalidAccessTokenException::new).getNickname();
            if(currentNickname.equals(nickname)){
                return;
            }
        }
        Optional<Member> member = memberRepository.findByNickname(nickname);
        if(member.isPresent()){
            throw new InvalidMemberInfoException(ErrCode.DUPLICATED_NICKNAME);
        }

    }

    public MemberResponseDto.FullUserInformationResponse getFullUserInformation(String username) {
        Member member = memberRepository.findByGoogleSub(username).orElseThrow(InvalidAccessTokenException::new);
        return MemberResponseDto.FullUserInformationResponse.builder()
                .name(member.getName())
                .nickname(member.getNickname())
                .phoneNumber(member.getPhoneNumber())
                .email(member.getEmail())
                .studentId(member.getStudentId())
                .role(member.getRoleStatus())
                .profileImgURL(member.getProfileImg())
                .managerType(member.getManagerStatus())
                .isPaid(member.isPaid())
                .build();
    }


    public ResponseEntity<Void> updateProfile(String googleSub, String updateRequestDto, MultipartFile newProfileImg) {

        Member member = memberRepository.findByGoogleSub(googleSub).orElseThrow(InvalidAccessTokenException::new);

        MemberRequestDto.UpdateRequestDto updateUserRequestDto = transformUpdateInformationToObject(updateRequestDto);

        validateUpdateInformation(updateUserRequestDto, googleSub );

        String toSetProfileImgUrl = null;

        String currentProfileImgUrl = member.getProfileImg();


        if (updateUserRequestDto.getIsProfileImgChanged()) {
            if (currentProfileImgUrl != null && newProfileImg == null) {
                List<String> url = new ArrayList<>();
                url.add(currentProfileImgUrl);
                mediaService.deleteMedias(url);
            }

            else if (currentProfileImgUrl == null && newProfileImg != null) {
                toSetProfileImgUrl = mediaService.uploadProfileImg(newProfileImg, UploadType.PROFILE_IMG);
            }

            else if (currentProfileImgUrl != null && newProfileImg != null) {
                List<String> url = new ArrayList<>();
                url.add(currentProfileImgUrl);
                mediaService.deleteMedias(url);
                toSetProfileImgUrl = mediaService.uploadProfileImg(newProfileImg, UploadType.PROFILE_IMG);

            }

        } else {
            toSetProfileImgUrl = currentProfileImgUrl;

        }

        member.transferUpdateDtoToMember(updateUserRequestDto, toSetProfileImgUrl);

        memberRepository.save(member);

        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);

    }



}
