package kr.joljak.api.user.request;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import kr.joljak.domain.user.dto.RegisterProfile;
import kr.joljak.domain.user.dto.SimplePortfolio;
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
public class RegisterProfileRequest {
  private UserProjectRole mainRole;
  private UserProjectRole subRole;
  private String introduce;
  private List<SimplePortfolio> portfolioLinks;
  private MultipartFile image;

  public RegisterProfile of(String classOf, MultipartFile image) {
    List<Portfolio> portfolios = Collections.emptyList();
    if (portfolioLinks != null) {
      portfolios = portfolioLinks.stream()
        .map(SimplePortfolio::to)
        .collect(Collectors.toList());
    }

    Profile profile = Profile.builder()
      .content(this.introduce)
      .portfolioLinks(portfolios)
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
