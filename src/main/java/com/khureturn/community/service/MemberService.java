package com.khureturn.community.service;

import com.khureturn.community.repository.MemberRepository;
import com.khureturn.community.security.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final RedisService redisService;

    private final JwtProvider jwtProvider;




//    @Transactional
//    public ResponseEntity<?> signUp(String signUpRequest, List<MultipartFile> profileImg, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
//
//
//        SignUpRequestDto signUpRequestDto = transformUserInformationToObject(signUpRequest);
//
//        validateUserInformation(signUpRequestDto);
//
//        String originalProfileImgURL = null;
//        String compressedProfileImgURL = null;
//
//
//        if (profileImg != null) {
//            originalProfileImgURL = mediaService.uploadMedias(profileImg, UploadType.ORIGNAL_PROFILE_IMG).get(0);
//            compressedProfileImgURL = mediaService.compressImage(originalProfileImgURL);
//        }
//
//        User user = new User();
//        user.setRequestFields(
//                originalProfileImgURL,
//                compressedProfileImgURL,
//                signUpRequestDto.getNickname(),
//                signUpRequestDto.getPhoneNumber(),
//                signUpRequestDto.getEmail(),
//                signUpRequestDto.getRealName(),
//                signUpRequestDto.getStatusMsg(),
//                signUpRequestDto.getUsername(),
//                Role.valueOf(signUpRequestDto.getRole().toUpperCase(Locale.ROOT))
//        );
//
//        userRepository.save(user);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        String userAgentType = distinguishUserAgent.extractUserAgent(userAgent);
//        httpHeaders.set("authorization", jwtTokenProvider.createAccessToken(signUpRequestDto.getUsername(), userAgentType));
//        String refreshjwt = jwtTokenProvider.createRefreshToken(signUpRequestDto.getUsername());
//        if(userAgentType.equals("WEB")) {
//            Cookie cookie = new Cookie("authorization-refresh", refreshjwt);
//            cookie.setMaxAge(14 * 24 * 60 * 60); //2주
//            cookie.setHttpOnly(true);
//            cookie.setPath("/");
//            httpServletResponse.addCookie(cookie);
//        }
//        else{
//            httpHeaders.set("authorization-refresh", refreshjwt);
//        }
//        redisService.setDataWithExpiration(userAgentType + "_" + signUpRequestDto.getUsername(), refreshjwt, 2 * 604800L); //리플리쉬 토큰 redis에 저장.
//
//        UserBriefInformationResponseDto userBriefInformationResponseDto = UserBriefInformationResponseDto.builder()
//                .balance(user.getBalance())
//                .userId(user.getId())
//                .nickname(user.getNickname())
//                .profileImgURL(user.getOriginalProfileImgURL())
//                .build();
//
//        return new ResponseEntity<UserBriefInformationResponseDto>(userBriefInformationResponseDto, httpHeaders, HttpStatus.CREATED);
//    }













}
