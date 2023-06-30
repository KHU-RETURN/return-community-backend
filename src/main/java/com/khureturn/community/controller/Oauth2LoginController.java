package com.khureturn.community.controller;

import com.khureturn.community.exception.authentication.AlreadyLoginException;
import com.khureturn.community.exception.authentication.InvalidAccessTokenException;
import com.khureturn.community.service.Oauth2LoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/sign-in")
@RestController
public class Oauth2LoginController {

    private final Oauth2LoginService oauth2LoginService;


    @PostMapping("/google")
    public ResponseEntity<?> googleOauthLogin(
            Principal principal,
            @RequestBody Map<String, String> accessToken,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) throws InvalidAccessTokenException, ParseException, ServletException, IOException {
        if (principal != null) throw new AlreadyLoginException();
        String token = accessToken.get("accessToken");
        String idToken = accessToken.get("idToken");
        ResponseEntity<String> GoogleUserinfo = oauth2LoginService.googleUserInfo(token, idToken);
        return oauth2LoginService.googleLogin(GoogleUserinfo, httpServletRequest, httpServletResponse);
    }
}

