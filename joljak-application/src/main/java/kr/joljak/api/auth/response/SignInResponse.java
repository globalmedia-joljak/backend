package kr.joljak.api.auth.response;

import kr.joljak.core.jwt.AccessToken;
import kr.joljak.domain.user.dto.SimpleUser;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInResponse {
  private SimpleUser user;
  private AccessToken accessToken;
  private String refreshToken;

  @Builder
  public SignInResponse(SimpleUser user, AccessToken accessToken, String refreshToken) {
    this.user = user;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }
}
