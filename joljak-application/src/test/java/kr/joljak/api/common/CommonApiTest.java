package kr.joljak.api.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import kr.joljak.core.jwt.JwtTokenProvider;
import kr.joljak.core.security.UserRole;
import kr.joljak.domain.invite.entity.Invite;
import kr.joljak.domain.invite.repository.InviteRepository;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.entity.UserProjectRole;
import kr.joljak.domain.user.repository.UserRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class CommonApiTest {
  @Autowired
  private InviteRepository inviteRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  private User admin;
  private User user;
  private String adminAccessToken;
  private String adminRefreshToken;
  private String userAccessToken;
  private String userRefreshToken;
  private String originalPassword = "12345678901234567890123456789012345678901234567890";

  @LocalServerPort
  protected int port;

  @Autowired
  protected WebApplicationContext webApplicationContext;

  protected MockMvc mockMvc;

  protected final String URL = "http://localhost:";
  protected final String AUTH_URL = URL + port + "/api/v1/auth";
  protected final String INVITE_URL = URL + port + "/api/v1/invites";

  protected static int nextId = 0;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders
      .webAppContextSetup(webApplicationContext)
      .build();

    admin = userRepository.save(createMockUser(UserRole.ADMIN));
    setToken(admin, UserRole.ADMIN);

    user = userRepository.save(createMockUser(UserRole.USER));
    setToken(user, UserRole.USER);
  }

  public User createMockUser(UserRole userRole) {
    List<UserRole> userRoles = new ArrayList<>(Collections.singleton(UserRole.USER));
    String hashPassword = new BCryptPasswordEncoder().encode(originalPassword);
    if (userRole.equals(UserRole.ADMIN)) {
      userRoles.add(UserRole.ADMIN);
    }

    return User.builder()
        .classOf("testUser" + nextId++)
        .mainProjectRole(UserProjectRole.DEVELOPER)
        .password(hashPassword)
        .userRoles(userRoles)
        .phoneNumber("010-1234-5678")
        .name("testUser")
        .build();
  }

  public void setToken(User user, UserRole userRole) {
    final String BEARER = "Bearer";
    List<String> userRoles = user.getUserRoles().stream()
        .map(UserRole::getRoleName)
        .collect(Collectors.toList());

    switch (userRole) {
      case ADMIN:
        this.adminAccessToken = BEARER + jwtTokenProvider.generateAccessToken(user.getClassOf(), userRoles)
          .getToken();
        this.adminRefreshToken = jwtTokenProvider.generateRefreshToken(user.getClassOf(), userRoles);
        break;
      case USER:
        this.userAccessToken = BEARER + jwtTokenProvider.generateAccessToken(user.getClassOf(), userRoles)
          .getToken();
        this.userRefreshToken = jwtTokenProvider.generateRefreshToken(user.getClassOf(), userRoles);
        break;
    }
  }

  public Invite createInvite(String classOf, String inviteCode) {
    Invite invite = Invite.builder()
      .inviteCode(inviteCode)
      .classOf(classOf)
      .build();

    return inviteRepository.save(invite);
  }

  public User getAdmin() {
    return admin;
  }

  public User getUser() {
    return user;
  }

  public String getAdminAccessToken() {
    return adminAccessToken;
  }

  public String getAdminRefreshToken() {
    return adminRefreshToken;
  }

  public String getUserAccessToken() {
    return userAccessToken;
  }

  public String getUserRefreshToken() {
    return userRefreshToken;
  }

  public String getOriginalPassword() {
    return originalPassword;
  }
}
