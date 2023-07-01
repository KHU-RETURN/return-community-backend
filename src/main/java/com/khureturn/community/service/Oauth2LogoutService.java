package com.khureturn.community.service;

import com.khureturn.community.security.JwtProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class Oauth2LogoutService {

    private final JwtProvider jwtTokenProvider;


    private final RedisService redisService;


    public void logout(String accessToken, HttpServletResponse httpServletResponse) throws IOException {

        String getAccessToken = jwtTokenProvider.extractAccessToken(accessToken).orElse(null);

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
