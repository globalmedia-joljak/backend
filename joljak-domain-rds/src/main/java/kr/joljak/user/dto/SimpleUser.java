package kr.joljak.user.dto;

import java.util.List;
import kr.joljak.security.UserRole;
import kr.joljak.user.entity.User;
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
  private List<UserRole> userRoles;

  @Builder
  public SimpleUser(Long id, String classOf, String name, String phoneNumber, List<UserRole> userRoles) {
    this.id = id;
    this.classOf = classOf;
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.userRoles = userRoles;
  }

  public static SimpleUser of(User user) {
    return SimpleUser.builder()
      .id(user.getId())
      .classOf(user.getClassOf())
      .name(user.getName())
      .phoneNumber(user.getPhoneNumber())
      .userRoles(user.getUserRoles())
      .build();
  }
}
