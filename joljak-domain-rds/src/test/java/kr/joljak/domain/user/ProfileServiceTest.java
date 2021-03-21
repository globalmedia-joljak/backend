package kr.joljak.domain.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import kr.joljak.core.security.UserRole;
import kr.joljak.domain.common.CommonDomainTest;
import kr.joljak.domain.user.entity.Profile;
import kr.joljak.domain.user.entity.UserProjectRole;
import kr.joljak.domain.user.exception.AlreadyProfileExistException;
import kr.joljak.domain.user.repository.ProfileRepository;
import kr.joljak.domain.user.service.ProfileService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;

public class ProfileServiceTest extends CommonDomainTest {
  @Autowired
  private ProfileService profileService;
  @Autowired
  private ProfileRepository profileRepository;

  private Profile commonProfile;

  @Before
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void initProfile() {
    // given
    String classOf = TEST_USER_CLASS_OF;
    Profile buildProfile = Profile.builder()
      .content("test profile")
      .portfolioLinks(new ArrayList<>(Collections.singleton("http://localhost:8080")))
      .build();

    // when
    Optional<Profile> profile = profileRepository.getByUserClassOf(TEST_USER_CLASS_OF);
    commonProfile = profile.orElseGet(
      () -> profileService.registerProfile(
        buildProfile,
        UserProjectRole.DEVELOPER,
        UserProjectRole.MEDIA_ART,
        null
      )
    );

  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void registerProfile_Success() {
    // then
    Assertions.assertThat(commonProfile.getId()).isNotNull();
  }

  @Test(expected = AlreadyProfileExistException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void registerProfile_Fail_AlreadyProfileExistException() {
    // when
    profileService.registerProfile(
      commonProfile,
      UserProjectRole.DEVELOPER,
      UserProjectRole.MEDIA_ART,
      null
    );

    // then
  }

}
