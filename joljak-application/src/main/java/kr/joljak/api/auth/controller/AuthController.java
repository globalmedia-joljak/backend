package kr.joljak.api.auth.controller;

import io.swagger.annotations.ApiOperation;
import kr.joljak.api.auth.request.SignInRequest;
import kr.joljak.api.auth.request.SignUpRequest;
import kr.joljak.api.auth.response.SignInResponse;
import kr.joljak.api.auth.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
  private final AuthService authService;

  @ApiOperation("회원가입 API")
  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.CREATED)
  public SignInResponse signUp(@RequestBody SignUpRequest signUpRequest) {
    return authService.signUp(signUpRequest);
  }

  @ApiOperation("로그인 API")
  @PostMapping("/signin")
  @ResponseStatus(HttpStatus.OK)
  public SignInResponse signIn(@RequestBody SignInRequest signInRequest) {
    return authService.signIn(signInRequest);
  }
}
