package kr.joljak.api.user.request;

import java.util.List;
import kr.joljak.domain.user.entity.Profile;
import kr.joljak.domain.user.entity.UserProjectRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterProfileRequest {
  private UserProjectRole mainRole;
  private UserProjectRole subRole;
  private String introduce;
  private List<String> portfolioLinks;

  public Profile of() {
    return Profile.builder()
      .content(this.introduce)
      .portfolioLinks(this.portfolioLinks)
      .build();
  }
}
