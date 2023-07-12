package com.khureturn.community.security;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component //빈 등록
public class JwtProvider {


    @Value("${jwt.secret}")
    private String secret;

    private final String accessHeader = "authorization";

    private static final String BEARER = "Bearer ";

    private final long accesstokenValidityInMilliseconds = 604800 * 1000L; //엑세스 토큰 유효기간 1주


    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createAccessToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("Return")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accesstokenValidityInMilliseconds))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact(); //토큰생성
    }



//    public Optional<String> extractAccessToken(HttpServletRequest request) throws IOException, ServletException {
//        return Optional.ofNullable(request.getHeader(accessHeader)).filter(accessToken -> accessToken.startsWith(BEARER)).map(accessToken -> accessToken.replace(BEARER, ""));
//    }


    public String extractAccessTokenFromCookie(HttpServletRequest httpServletRequest) throws IOException{
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies == null){
            return null;
        }
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("authorization")){
                return cookie.getValue();
            }
        }
        return null;
    }

    public Long getExpireTime(String token) {
        Date expirationDate =  Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getExpiration();
        Long now = new Date().getTime();
        return ((expirationDate.getTime() - now) % 1000) + 1;
    }


    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }



    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            return true;
        } catch (SignatureException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        } catch (Exception e) {
            log.info("JWT에 알 수 없는 문제가 발생하였습니다");
        }
        return false;
    }
}