package com.khureturn.community.controller;

import com.khureturn.community.repository.MemberRepository;
import com.khureturn.community.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TestController {
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;


//    @PostMapping("/join")
//    public Long join(@RequestBody Map<String, String> user) {
//        return userRepository.save(User.builder()
//                .username(user.get("username"))
//                .password(passwordEncoder.encode(UUID.randomUUID().toString()))
//                .build()).getId();
//    }
//
//
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/hellorest")
//    public ResponseEntity<?> hello() {
//        System.out.println("hello");
//        return ResponseEntity.ok().body("hello");
//    }


    @GetMapping("/login/oauth2/code/google")
    public String googleCode(@RequestParam String code) throws ParseException {
        log.info("여기 거침");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders accessTokenHeaders = new HttpHeaders();
        accessTokenHeaders.add("Content-type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> accessTokenParams = new LinkedMultiValueMap<>();
        accessTokenParams.add("client_id", "68725075427-bnc849gn7fsfpliptlula9pc73nu50mm.apps.googleusercontent.com");
        accessTokenParams.add("client_secret", "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");
        accessTokenParams.add("code", code);
        accessTokenParams.add("grant_type", "authorization_code");
        accessTokenParams.add("redirect_uri", "http://localhost:8080/login/oauth2/code/google");

        HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(accessTokenParams, accessTokenHeaders);

        ResponseEntity<String> accessTokenResponse = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v4/token",
                HttpMethod.POST,
                accessTokenRequest,
                String.class
        );

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(accessTokenResponse.getBody());
        log.info(jsonObject.toJSONString());
        String accessToken = (String) jsonObject.get("access_token");
        String idToken = (String) jsonObject.get("id_token");
        Map<String, String> token = new LinkedHashMap<>();
        token.put("accesstoken", accessToken);
        token.put("id_token", idToken);


        return token.toString();
    }
}