package kr.joljak.api.auth.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import kr.joljak.api.auth.request.SignUpRequest;
import kr.joljak.api.auth.response.SignInResponse;

import kr.joljak.domain.invite.service.InviteService;
import kr.joljak.core.jwt.AccessToken;
import kr.joljak.core.jwt.JwtTokenProvider;
import kr.joljak.core.security.UserRole;
import kr.joljak.domain.user.dto.SimpleUser;
import kr.joljak.domain.user.service.UserService;
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
  private final UserService userService;
  private final InviteService inviteService;
  private final JwtTokenProvider jwtTokenProvider;

  @ApiOperation("회원가입 API")
  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.CREATED)
  public SignInResponse signUp(@RequestBody SignUpRequest signUpRequest) {
    inviteService.validateAndExpireInvite(signUpRequest.getClassOf(), signUpRequest.getInviteCode());

    SimpleUser simpleUser = SimpleUser.of(userService.signUp(signUpRequest.toSignUpUser()));
    List<String> userRoles = simpleUser.getUserRoles().stream()
      .map(UserRole::getRoleName)
      .collect(Collectors.toList());
    AccessToken accessToken = jwtTokenProvider.generateAccessToken(simpleUser.getClassOf(), userRoles);
    String refreshToken = jwtTokenProvider.generateRefreshToken(simpleUser.getClassOf(), userRoles);

    return SignInResponse.builder()
      .accessToken(accessToken)
      .refreshToken(refreshToken)
      .user(simpleUser)
      .build();
  }
}
