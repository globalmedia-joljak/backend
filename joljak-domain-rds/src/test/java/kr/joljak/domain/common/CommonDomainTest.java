package kr.joljak.domain.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import kr.joljak.core.jwt.AccessToken;
import kr.joljak.core.jwt.JwtTokenProvider;
import kr.joljak.core.security.UserRole;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.entity.UserProjectRole;
import kr.joljak.domain.user.exception.UserNotFoundException;
import kr.joljak.domain.user.repository.UserRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class CommonDomainTest {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  private AccessToken adminAccessToken;
  private String adminRefreshToken;
  private AccessToken userAccessToken;
  private String userRefreshToken;

  public static final String TEST_ADMIN_CLASS_OF = "testUser0";
  public static final String TEST_USER_CLASS_OF = "testUser1";

  protected static int nextId;

  @Before
  public void setup() {
    User admin = userRepository.save(createMockUser(UserRole.ADMIN));
    setToken(admin, UserRole.ADMIN);

    User user = userRepository.save(createMockUser(UserRole.USER));
    setToken(user, UserRole.USER);
  }


  public User createMockUser(UserRole userRole) {
    List<UserRole> userRoles = new ArrayList<>(Collections.singleton(UserRole.USER));

    if (userRole.equals(UserRole.ADMIN)) {
      userRoles.add(UserRole.ADMIN);
    }

    return User.builder()
        .classOf("testUser" + nextId++)
        .mainProjectRole(UserProjectRole.DEVELOPER)
        .password("123456789012345678901234567890123456789012345678901234")
        .userRoles(userRoles)
        .phoneNumber("010-1234-5678")
        .name("testUser")
        .build();
  }


  public void setToken(User user, UserRole userRole) {
    List<String> userRoles = user.getUserRoles().stream()
      .map(UserRole::getRoleName)
      .collect(Collectors.toList());

    switch (userRole) {
      case ADMIN:
        this.adminAccessToken = jwtTokenProvider.generateAccessToken(user.getClassOf(), userRoles);
        this.adminRefreshToken = jwtTokenProvider.generateRefreshToken(user.getClassOf(), userRoles);
        break;
      case USER:
        this.userAccessToken = jwtTokenProvider.generateAccessToken(user.getClassOf(), userRoles);
        this.userRefreshToken = jwtTokenProvider.generateRefreshToken(user.getClassOf(), userRoles);
        break;
    }
  }

  public void setAuthentication(UserRole userRole) {
    String classOf = TEST_USER_CLASS_OF;
    if (userRole.equals(UserRole.ADMIN)) {
      classOf = TEST_ADMIN_CLASS_OF;
    }

    List<String> roles = new ArrayList<>(Collections.singleton(userRole.getRoleName()));
    List<? extends GrantedAuthority> authorities = roles.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());

    org.springframework.security.core.userdetails.User principal =
        new org.springframework.security.core.userdetails.User(classOf, "", authorities);

    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(principal, null, authorities)
    );
  }

  public User getAdmin() {
    return getUserByClassOf(TEST_USER_CLASS_OF);
  }

  public User getUser() {
    return getUserByClassOf(TEST_ADMIN_CLASS_OF);
  }

  public User getUserByClassOf(String classOf) {
    return userRepository.findByClassOf(classOf)
        .orElseThrow(UserNotFoundException::new);
  }

  public AccessToken getAdminAccessToken() {
    return adminAccessToken;
  }

  public String getAdminRefreshToken() {
    return adminRefreshToken;
  }

  public AccessToken getUserAccessToken() {
    return userAccessToken;
  }

  public String getUserRefreshToken() {
    return userRefreshToken;
  }
}
