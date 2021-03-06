package kr.joljak.api.auth.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.joljak.api.auth.request.SignInRequest;
import kr.joljak.api.auth.request.SignUpRequest;
import kr.joljak.api.auth.response.SignInResponse;
import kr.joljak.core.jwt.AccessToken;
import kr.joljak.core.jwt.JwtTokenProvider;
import kr.joljak.core.security.UserRole;
import kr.joljak.domain.invite.service.InviteService;
import kr.joljak.domain.user.dto.SimpleUser;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.exception.InvalidPasswordException;
import kr.joljak.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserService userService;
  private final InviteService inviteService;
  private final JwtTokenProvider jwtTokenProvider;

  @Transactional
  public SignInResponse signUp(SignUpRequest signUpRequest) {
    inviteService.validateAndExpireInvite(signUpRequest.getClassOf(), signUpRequest.getInviteCode());
    User user = userService.signUp(signUpRequest.toSignUpUser());

    return getSignInResponse(SimpleUser.of(user));
  }

  @Transactional
  public SignInResponse signIn(SignInRequest signInRequest) {
    User user = userService.getUserByClassOf(signInRequest.getClassOf());

    if (!new BCryptPasswordEncoder().matches(signInRequest.getPassword(), user.getPassword())) {
      throw new InvalidPasswordException("password does not match.");
    }

    return getSignInResponse(SimpleUser.of(user));
  }

  private SignInResponse getSignInResponse(SimpleUser simpleUser) {
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
