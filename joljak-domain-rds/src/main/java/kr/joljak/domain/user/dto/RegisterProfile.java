package kr.joljak.domain.user.dto;

import kr.joljak.domain.user.entity.Profile;
import kr.joljak.domain.user.entity.UserProjectRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterProfile {
  private String classOf;
  private UserProjectRole mainRole;
  private UserProjectRole subRole;
  private Profile profile;
  private MultipartFile image;

  @Builder
  public RegisterProfile(String classOf, UserProjectRole mainRole,
      UserProjectRole subRole, Profile profile, MultipartFile image) {
    this.classOf = classOf;
    this.mainRole = mainRole;
    this.subRole = subRole;
    this.profile = profile;
    this.image = image;
  }
}
