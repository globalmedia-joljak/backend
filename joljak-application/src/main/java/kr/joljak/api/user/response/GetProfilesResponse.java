package kr.joljak.api.user.response;

import kr.joljak.domain.user.dto.SimpleProfile;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetProfilesResponse {
  private Page<SimpleProfile> simpleProfilePage;

  @Builder
  public GetProfilesResponse(Page<SimpleProfile> simpleProfilePage) {
    this.simpleProfilePage = simpleProfilePage;
  }
}
