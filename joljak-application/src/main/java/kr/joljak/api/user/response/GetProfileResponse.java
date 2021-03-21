package kr.joljak.api.user.response;

import kr.joljak.domain.user.dto.SimpleProfile;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetProfileResponse {
  private SimpleProfile simpleProfile;

  @Builder
  public GetProfileResponse(SimpleProfile simpleProfile) {
    this.simpleProfile = simpleProfile;
  }
}
