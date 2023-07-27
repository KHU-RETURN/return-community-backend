package com.khureturn.community.controller;

import com.khureturn.community.dto.MemberResponseDto;
import com.khureturn.community.exception.authentication.AlreadyLoginException;
import com.khureturn.community.service.AccountService;
import com.khureturn.community.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    private final AccountService accountService;


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public ResponseEntity<MemberResponseDto.FullUserInformationResponse> fullUserInformation(Principal principal) {
        log.info(principal.getName());
        return ResponseEntity.ok().body(memberService.getFullUserInformation(principal.getName()));
    }


    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "/profile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> profileUpdate(
            Principal principal,
            @RequestPart String updateRequest,
            @RequestPart(required = false) MultipartFile profileImg) {
        log.info(principal.getName());

        return memberService.updateProfile(principal.getName(), updateRequest, profileImg);
    }



    @GetMapping("/validate-phone-number/{phoneNumber}")
    public ResponseEntity<Void> validatePhoneNumber(Principal principal, @PathVariable String phoneNumber) {
        String username = null;
        if (principal != null) {
            username = principal.getName();
        }
        memberService.validatePhoneNumber(phoneNumber, username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate-email/{email}")
    public ResponseEntity<Void> validateEmail(Principal principal, @PathVariable String email) {
        String username = null;
        if (principal != null) {
            username = principal.getName();
        }
        memberService.validateEmail(email, username);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/sign-up", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> signUp(
            Principal principal,
            @RequestPart String signUpRequest,
            @RequestPart(required = false) MultipartFile profileImg,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) throws ServletException, IOException {
        if (principal != null) throw new AlreadyLoginException();
        return memberService.signUp(signUpRequest, profileImg, httpServletRequest, httpServletResponse);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/sign-out")
    public ResponseEntity<Void> logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        accountService.logout(httpServletRequest, httpServletResponse);
        return ResponseEntity.ok().build();
    }
}
