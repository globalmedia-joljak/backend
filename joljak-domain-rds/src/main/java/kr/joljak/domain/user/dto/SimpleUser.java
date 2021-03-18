package kr.joljak.domain.user.dto;

import java.util.List;
import kr.joljak.core.security.UserRole;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.entity.UserProjectRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleUser {
  private Long id;
  private String classOf;
  private String name;
  private String phoneNumber;
  private String instagramId;
  private String kakaoId;
  private List<UserRole> userRoles;
  private UserProjectRole mainProjectRole;
  private UserProjectRole subProjectRole;

  @Builder
  public SimpleUser(Long id, String classOf, String name, String phoneNumber,
      String instagramId, String kakaoId, List<UserRole> userRoles,
      UserProjectRole mainProjectRole, UserProjectRole subProjectRole) {
    this.id = id;
    this.classOf = classOf;
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.instagramId = instagramId;
    this.kakaoId = kakaoId;
    this.userRoles = userRoles;
    this.mainProjectRole = mainProjectRole;
    this.subProjectRole = subProjectRole;
  }

  public static SimpleUser of(User user) {
    return SimpleUser.builder()
      .id(user.getId())
      .classOf(user.getClassOf())
      .name(user.getName())
      .phoneNumber(user.getPhoneNumber())
      .userRoles(user.getUserRoles())
      .instagramId(user.getInstagramId())
      .kakaoId(user.getKakaoId())
      .mainProjectRole(user.getMainProjectRole())
      .subProjectRole(user.getSubProjectRole())
      .build();
  }
}
