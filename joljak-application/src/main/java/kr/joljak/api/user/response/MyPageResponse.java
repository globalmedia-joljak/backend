package kr.joljak.api.user.response;

import kr.joljak.domain.user.dto.SimpleUser;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPageResponse {
  private SimpleUser user;

  @Builder
  public MyPageResponse(SimpleUser user) {
    this.user = user;
  }
}
