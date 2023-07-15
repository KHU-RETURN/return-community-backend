package com.khureturn.community.controller;

import com.khureturn.community.exception.authentication.AlreadyLoginException;
import com.khureturn.community.exception.authentication.InvalidAccessTokenException;
import com.khureturn.community.service.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.security.Principal;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/sign-in")
@RestController
public class Oauth2LoginController {

    private final AccountService accountService;

    @PostMapping("/google")
    public ResponseEntity<?> googleOauthLogin(
            Principal principal,
            @RequestParam String code,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) throws InvalidAccessTokenException, ParseException, ServletException, IOException {
        if (principal != null) throw new AlreadyLoginException();
        ResponseEntity<String> GoogleUserinfo = accountService.googleUserInfo(code);
        return accountService.googleLogin(GoogleUserinfo, httpServletRequest, httpServletResponse);
    }
}

