package kr.joljak.api.auth.controller;

import io.swagger.annotations.ApiOperation;
import kr.joljak.api.auth.request.SignInRequest;
import kr.joljak.api.auth.request.SignUpRequest;
import kr.joljak.api.auth.response.SignInResponse;
import kr.joljak.api.auth.service.AuthService;
import kr.joljak.core.jwt.AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
  private final AuthService authService;

  @ApiOperation("회원가입 API")
  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.CREATED)
  public SignInResponse signUp(@RequestBody SignUpRequest signUpRequest) {
    log.info( "]-----] AuthController::signUp [-----[ classOf : {}", signUpRequest.getClassOf());
    return authService.signUp(signUpRequest);
  }

  @ApiOperation("로그인 API")
  @PostMapping("/signin")
  @ResponseStatus(HttpStatus.OK)
  public SignInResponse signIn(@RequestBody SignInRequest signInRequest) {
    log.info( "]-----] AuthController::signIn [-----[ classOf : {}", signInRequest.getClassOf());
    return authService.signIn(signInRequest);
  }

  @ApiOperation("RefreshToken으로 AccessToken 재발급")
  @PostMapping("/refreshtoken/reissue/accesstoken")
  @ResponseStatus(HttpStatus.OK)
  public AccessToken reissueAccessTokenByRefreshToken(@RequestHeader String refreshToken) {
    log.info( "]-----] AuthController::reissueAccessTokenByRefreshToken [-----[ refreshToken : {}"
      , refreshToken);
    return authService.reissueAccessTokenByRefreshToken(refreshToken);
  }

  @ApiOperation("AccessToken으로 AccessToken 재발급")
  @PostMapping("/reissue/accesstoken")
  @ResponseStatus(HttpStatus.OK)
  public AccessToken reissueAccessTokenByAccessToken(@RequestHeader String accessToken) {
    log.info( "]-----] AuthController::reissueAccessTokenByAccessToken [-----[ accessToken : {}"
      , accessToken);
    return authService.reissueAccessTokenByAccessToken(accessToken);
  }
}
