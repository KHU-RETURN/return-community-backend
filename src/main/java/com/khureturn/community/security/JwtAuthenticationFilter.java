package com.khureturn.community.security;

import com.khureturn.community.security.UserDetails.PrincipalService;
import com.khureturn.community.service.RedisService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {//모든 서블릿 컨테이너에서 요청 디스패치당 단일 실행을 보장하는 것을 목표로 하는 필터 기본 클래스
    private final JwtProvider jwtProvider;

    private final PrincipalService principalService;


    private final RedisService redisService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (request.getRequestURI().equals("/reissue")) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<String> accessToken= jwtProvider.extractAccessToken(request);
        boolean isAccessToken = false;
        if (accessToken.isPresent()) {
            try {
                isAccessToken = jwtProvider.validateToken(accessToken.get());
            } catch (ExpiredJwtException e) {
                request.setAttribute("exception", "ExpiredJwtException"); //만료 에러.
                filterChain.doFilter(request, response);
                return;
            } catch (SecurityException | IllegalArgumentException | JwtException e) {
                request.setAttribute("exception", "AccessJwtException"); //유효하지 않은 예외.
                filterChain.doFilter(request, response);
                return;
            }


            if (isAccessToken == false) {
                request.setAttribute("exception", "AccessJwtException"); //엑세스 토큰이 아니기에 예외 반환.
            }
            else if(redisService.hasKeyBlackListToken(accessToken.get())){
                request.setAttribute("exception", "AccessJwtException"); //블랙리스트에 있는 토큰이기에 예외 반환.
            }
            else {
                String username = jwtProvider.getGoogleSub(accessToken.get());
                try {
                    UserDetails userDetails = principalService.loadUserByUsername(username);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    context.setAuthentication(authentication);
                    SecurityContextHolder.setContext(context);
                } catch (UsernameNotFoundException e) {
                    request.setAttribute("exception", "UsernameNotFoundException"); //유저 정보를 찾을 수 없다는 에러.

                }

            }
        }

        filterChain.doFilter(request, response);
    }
}
