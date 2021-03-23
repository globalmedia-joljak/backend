package kr.joljak.api.user.request;

import java.util.List;
import kr.joljak.domain.user.dto.RegisterProfile;
import kr.joljak.domain.user.entity.Profile;
import kr.joljak.domain.user.entity.UserProjectRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class RegisterProfileRequest {
  private UserProjectRole mainRole;
  private UserProjectRole subRole;
  private String introduce;
  private List<String> portfolioLinks;

  public RegisterProfile of(String classOf, MultipartFile image) {
    Profile profile = Profile.builder()
      .content(this.introduce)
      .portfolioLinks(this.portfolioLinks)
      .build();

    return RegisterProfile.builder()
      .classOf(classOf)
      .mainRole(this.mainRole)
      .subRole(this.subRole)
      .profile(profile)
      .image(image)
      .build();
  }
}
