package kr.joljak.api.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import kr.joljak.api.ideaboard.request.IdeaBoardRequest;
import kr.joljak.api.notice.request.NoticeRequest;
import kr.joljak.api.work.request.RegisterWorkRequest;
import kr.joljak.core.jwt.JwtTokenProvider;
import kr.joljak.core.security.UserRole;
import kr.joljak.domain.Ideaboard.entity.ProjectStatus;
import kr.joljak.domain.invite.entity.Invite;
import kr.joljak.domain.invite.repository.InviteRepository;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.entity.UserProjectRole;
import kr.joljak.domain.user.exception.UserNotFoundException;
import kr.joljak.domain.user.repository.UserRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

  protected static final String TEST_ADMIN_CLASS_OF = "testUser0";
  protected static final String TEST_USER_CLASS_OF = "testUser1";

  protected final String URL = "http://localhost:";
  protected final String NOTICE_URL = URL + port + "/api/v1/notices";
  protected final String IDEABOARD_URL = URL + port + "/api/v1/ideaboards";
  protected final String WORK_URL = URL +port + "/api/v1/works";
  protected final String AUTH_URL = URL + port + "/api/v1/auth";
  protected final String INVITE_URL = URL + port + "/api/v1/invites";
  protected final String USER_URL = URL + port + "/api/v1/users";
  protected final String PROFILE_URL = URL + port + "/api/v1/profiles";

  protected static int nextId = 0;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders
      .webAppContextSetup(webApplicationContext)
      .build();

    User admin = userRepository.save(createMockUser(UserRole.ADMIN));
    setToken(admin, UserRole.ADMIN);

    User user = userRepository.save(createMockUser(UserRole.USER));
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
    List<String> userRoles = user.getUserRoles().stream()
      .map(UserRole::getRoleName)
      .collect(Collectors.toList());

    switch (userRole) {
      case ADMIN:
        this.adminAccessToken =
          jwtTokenProvider.generateAccessToken(user.getClassOf(), userRoles)
            .getToken();
        this.adminRefreshToken = jwtTokenProvider
          .generateRefreshToken(user.getClassOf(), userRoles);
        break;
      case USER:
        this.userAccessToken =
          jwtTokenProvider.generateAccessToken(user.getClassOf(), userRoles)
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

  public NoticeRequest createNoticeRequest(String classOf, String title, String content) {
    return new NoticeRequest(
      classOf,
      title,
      content
    );
  }

  public IdeaBoardRequest createIdeaBoardRequest(String title, String content,
    ProjectStatus status) {
    return IdeaBoardRequest.builder()
      .title(title)
      .content(content)
      .status(status)
      .build();
  }

  public RegisterWorkRequest createWorkRequest(String workName, String teamName, List<String> teamMember,
    String content) {
    return RegisterWorkRequest.builder()
      .workName(workName)
      .teamName(teamName)
      .teamMember(teamMember)
      .content(content)
      .build();
  }
}
