package kr.joljak.api.auth.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.joljak.api.auth.request.SignUpRequest;
import kr.joljak.api.auth.response.SignInResponse;
import kr.joljak.core.jwt.AccessToken;
import kr.joljak.core.jwt.JwtTokenProvider;
import kr.joljak.core.security.UserRole;
import kr.joljak.domain.invite.service.InviteService;
import kr.joljak.domain.user.dto.SimpleUser;
import kr.joljak.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
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
