package kr.joljak.api.user.request;

import java.util.List;
import kr.joljak.domain.user.dto.UpdateProfile;
import kr.joljak.domain.user.entity.Profile;
import kr.joljak.domain.user.entity.UserProjectRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class UpdateProfileRequest {
  private UserProjectRole mainRole;
  private UserProjectRole subRole;
  private String introduce;
  private List<String> portfolioLinks;
  private String deleteFileName;

  public UpdateProfile of(String classOf, MultipartFile image) {
    Profile profile = Profile.builder()
      .portfolioLinks(this.portfolioLinks)
      .content(this.introduce)
      .build();

    return UpdateProfile.builder()
      .classOf(classOf)
      .mainRole(this.mainRole)
      .subRole(this.subRole)
      .profile(profile)
      .deleteFileName(deleteFileName)
      .image(image)
      .build();
  }

}
