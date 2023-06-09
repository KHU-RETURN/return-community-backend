package com.khureturn.community.service;

import com.khureturn.community.domain.Member;
import com.khureturn.community.dto.MemberResponseDto;
import com.khureturn.community.exception.authentication.InvalidAccessTokenException;
import com.khureturn.community.repository.MemberRepository;
import com.khureturn.community.security.JwtProvider;
import com.khureturn.community.security.UserInfoDto.UserInfoDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {

    private final MemberRepository userRepository;

    private final JwtProvider jwtTokenProvider;



    private final RedisService redisService;

    private final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@khu\\.ac\\.kr$";

    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public ResponseEntity<String> googleUserInfo(String accessToken, String idToken) {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.add("Authorization", "Bearer " + accessToken);
        ResponseEntity<String> GoogleUserinfo;
        try {
            HttpEntity profileRequest = new HttpEntity(headers);
            GoogleUserinfo = restTemplate.exchange(
                    "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken,
                    HttpMethod.GET,
                    profileRequest,
                    String.class
            );
        } catch (Exception e) {
            log.info(e.toString());
            throw new InvalidAccessTokenException();
        }
        return GoogleUserinfo;
    }


    public ResponseEntity<?> googleLogin(ResponseEntity<String> GoogleUserinfo, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ParseException, ServletException, IOException {

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(GoogleUserinfo.getBody());
        log.info(jsonObject.toJSONString());
        UserInfoDto dto = new UserInfoDto();
        dto.setGoogldSub(jsonObject.get("sub").toString());

        if (jsonObject.containsKey("email")) {
            dto.setEmail(jsonObject.get("email").toString());
        } else {
            log.info("유저 이메일을 불러올 수 없음");
            dto.setEmail(null);
        }
        if (jsonObject.containsKey("given_name")) {
            dto.setFullname(jsonObject.get("given_name").toString());
        } else {
            log.info("유저 이름을 불러올 수 없음");
            dto.setFullname(null);
        }
        if (jsonObject.containsKey("picture")) {

            dto.setProfileImgURL(jsonObject.get("picture").toString());
        } else {
            log.info("기존 프로필 사진을 불러올 수 없음");
            dto.setProfileImgURL(null);
        }
        if (!userRepository.existsByGoogleSub(dto.getGoogldSub())) {
            log.info("유저 정보가 없음");
            if(isValidEmail(dto.getEmail())){
                return ResponseEntity.status(200).body(dto);

            }
            else{
                return ResponseEntity.badRequest().build();
            }
        } else {
            log.info("유저 정보가 있다.");
            String accessToken = jwtTokenProvider.createAccessToken(dto.getGoogldSub());
            HttpHeaders httpHeaders = new HttpHeaders();
            Cookie cookie = new Cookie("authorization", accessToken);
            int expireTime = 604800;
            cookie.setMaxAge(expireTime);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);


            redisService.setDataWithExpiration(dto.getGoogldSub(), accessToken, expireTime);
            Member member = userRepository.findByGoogleSub(dto.getGoogldSub()).get();
            MemberResponseDto.GoogleAccountInfoDto googleAccountInfoDto = MemberResponseDto.GoogleAccountInfoDto.builder()
                    .memberId(member.getMemberId())
                    .profileImgURL(member.getProfileImg())
                    .name(member.getName())
                    .phoneNumber(member.getPhoneNumber())
                    .build();
            return new ResponseEntity<MemberResponseDto.GoogleAccountInfoDto>(googleAccountInfoDto, httpHeaders, HttpStatus.OK);
        }
    }


    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {

        String getAccessToken = jwtTokenProvider.extractAccessTokenFromCookie(httpServletRequest);

        String username = jwtTokenProvider.getUsername(getAccessToken);

        Cookie cookie= new Cookie("authorization", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);

        Long expiration = jwtTokenProvider.getExpireTime(getAccessToken);
        redisService.setBlackListToken(getAccessToken, "BLACKLIST_ACCESSTOKEN_" + username, expiration);
    }
}