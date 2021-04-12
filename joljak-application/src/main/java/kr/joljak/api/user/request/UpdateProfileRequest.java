package kr.joljak.api.user.request;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import kr.joljak.domain.user.dto.SimplePortfolio;
import kr.joljak.domain.user.dto.UpdateProfile;
import kr.joljak.domain.user.entity.Portfolio;
import kr.joljak.domain.user.entity.Profile;
import kr.joljak.domain.user.entity.UserProjectRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest {
  private UserProjectRole mainRole;
  private UserProjectRole subRole;
  private String introduce;
  private List<SimplePortfolio> portfolioLinks;
  private String deleteFileName;
  private MultipartFile image;

  public UpdateProfile of(String classOf, MultipartFile image) {
    List<Portfolio> portfolios = Collections.emptyList();
    if (portfolioLinks != null) {
      portfolios = portfolioLinks.stream()
          .map(SimplePortfolio::to)
          .collect(Collectors.toList());
    }

    Profile profile = Profile.builder()
      .portfolioLinks(portfolios)
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
