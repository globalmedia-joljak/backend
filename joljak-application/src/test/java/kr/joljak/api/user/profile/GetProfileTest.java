package kr.joljak.api.user.profile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.Collections;
import kr.joljak.api.common.CommonApiTest;
import kr.joljak.domain.user.dto.RegisterProfile;
import kr.joljak.domain.user.entity.Portfolio;
import kr.joljak.domain.user.entity.Profile;
import kr.joljak.domain.user.entity.UserProjectRole;
import kr.joljak.domain.user.repository.ProfileRepository;
import kr.joljak.domain.user.service.ProfileService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

public class GetProfileTest extends CommonApiTest {

  @Autowired
  private ProfileService profileService;

  @Autowired
  private ProfileRepository profileRepository;

  private Profile profile;

  @Before
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void init() {
    Portfolio portfolio = Portfolio
        .builder()
        .title("test_portfolio")
        .link("test_link")
        .build();
    Profile mockProfile = Profile.builder()
      .content("mock profile")
      .portfolioLinks(new ArrayList<>(Collections.singleton(portfolio)))
      .build();

    RegisterProfile registerProfile = RegisterProfile.builder()
        .classOf(TEST_USER_CLASS_OF)
        .mainRole(UserProjectRole.DEVELOPER)
        .subRole(UserProjectRole.MEDIA_ART)
        .profile(mockProfile)
        .image(null)
        .build();

    profile = profileRepository.findByUserClassOf(TEST_USER_CLASS_OF)
      .orElseGet(
        () -> profileService.registerProfile(registerProfile)
    );
  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void getProfile_Success() throws Exception {
    MvcResult mvcResult = mockMvc.perform(
        get(PROFILE_URL + "/" + TEST_USER_CLASS_OF)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();

    assertEquals(200, mvcResult.getResponse().getStatus());
  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void getProfile_Fail_ProfileNotFoundException() throws Exception {
    MvcResult mvcResult = mockMvc.perform(
        get(PROFILE_URL + "/" + TEST_ADMIN_CLASS_OF)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();

    assertEquals(404, mvcResult.getResponse().getStatus());
  }
}
