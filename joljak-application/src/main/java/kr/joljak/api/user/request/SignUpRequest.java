package kr.joljak.api.user.request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kr.joljak.core.security.UserRole;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.entity.UserProjectRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@AllArgsConstructor
public class SignUpRequest {
  private String classOf;
  private String password;
  private String name;
  private String phoneNumber;
  private String inviteCode;
  private UserProjectRole mainProjectRole;
  private UserProjectRole subProjectRole;

  public User toSignUpUser() {
    String hashPassword = new BCryptPasswordEncoder().encode(password);
    List<UserRole> userRoles = new ArrayList<>(Collections.singleton(UserRole.USER));

    return User.builder()
      .classOf(classOf)
      .password(hashPassword)
      .phoneNumber(phoneNumber)
      .name(name)
      .userRoles(userRoles)
      .mainProjectRole(mainProjectRole)
      .subProjectRole(subProjectRole)
      .build();
  }
}
