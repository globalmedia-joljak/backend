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
public class UpdateProfile {
  private String classOf;
  private UserProjectRole mainRole;
  private UserProjectRole subRole;
  private Profile profile;
  private MultipartFile image;
  private String deleteFileName;

  @Builder
  public UpdateProfile(String classOf, UserProjectRole mainRole,
      UserProjectRole subRole, Profile profile, MultipartFile image, String deleteFileName) {
    this.classOf = classOf;
    this.mainRole = mainRole;
    this.subRole = subRole;
    this.profile = profile;
    this.image = image;
    this.deleteFileName = deleteFileName;
  }
}
