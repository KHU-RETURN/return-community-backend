package com.khureturn.community.security;

import com.khureturn.community.exception.ErrCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Slf4j
public class JwtEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {


        String exceptionInfo = (String) request.getAttribute("exception");

        //JwtAuthenticationFilter에서 전달받은 예외 내용
        if (exceptionInfo != null) {
            log.info(exceptionInfo.toString());
            setException(response, exceptionInfo);
        }
        //PreAuthorize 어노테이션에 의한 예외
        else if (authException instanceof InsufficientAuthenticationException) {
            exceptionInfo = "InsufficientAuthenticationException";
            setException(response, exceptionInfo);
        }
    }

    private void setException(HttpServletResponse response, String exceptionInfo) throws IOException {
        final String errorCode;
        final String message;
        if (exceptionInfo.equals("UsernameNotFoundException")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            setResponse(response, ErrCode.NO_SUCH_USER.getCode(), ErrCode.NO_SUCH_USER.getMessage());
        } else if (exceptionInfo.equals("ExpiredJwtException")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            setResponse(response, ErrCode.EXPIRED_ACCESS_TOKEN.getCode(), ErrCode.EXPIRED_ACCESS_TOKEN.getMessage());
        } else if (exceptionInfo.equals("AccessJwtException")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            setResponse(response, ErrCode.INVALID_ACCESS_TOKEN.getCode(), ErrCode.INVALID_ACCESS_TOKEN.getMessage());
        } else if( exceptionInfo.equals("InsufficientAuthenticationException")){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            setResponse(response, ErrCode.REQUIRE_LOGIN.getCode(),ErrCode.REQUIRE_LOGIN.getMessage() );
        }
    }


    private void setResponse(HttpServletResponse response, String errorCode, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(response.getStatus());
        JSONObject responseJson = new JSONObject();
        responseJson.put("code", errorCode);
        responseJson.put("message", message);
        response.getWriter().print(responseJson);
    }
}
