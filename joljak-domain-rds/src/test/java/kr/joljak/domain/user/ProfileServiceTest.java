package kr.joljak.domain.user;

import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import kr.joljak.core.jwt.PermissionException;
import kr.joljak.core.security.UserRole;
import kr.joljak.domain.common.CommonDomainTest;
import kr.joljak.domain.upload.service.UploadService;
import kr.joljak.domain.user.dto.RegisterProfile;
import kr.joljak.domain.user.dto.UpdateProfile;
import kr.joljak.domain.user.entity.Portfolio;
import kr.joljak.domain.user.entity.Profile;
import kr.joljak.domain.user.entity.UserProjectRole;
import kr.joljak.domain.user.exception.AlreadyProfileExistException;
import kr.joljak.domain.user.exception.ProfileNotFoundException;
import kr.joljak.domain.user.repository.ProfileRepository;
import kr.joljak.domain.user.service.ProfileService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

public class ProfileServiceTest extends CommonDomainTest {
  @Autowired
  private UploadService uploadService;

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
      .portfolioLinks(new ArrayList<>(Collections.singleton(new Portfolio(null, "test", "link"))))
      .build();

    // when
    RegisterProfile registerProfile = RegisterProfile.builder()
      .classOf(TEST_USER_CLASS_OF)
      .mainRole(UserProjectRole.DEVELOPER)
      .subRole(UserProjectRole.MEDIA_ART)
      .profile(buildProfile)
      .image(null)
      .build();

    Optional<Profile> profile = profileRepository.findByUserClassOf(TEST_USER_CLASS_OF);
    commonProfile = profile.orElseGet(
      () -> profileService.registerProfile(registerProfile)
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
    RegisterProfile registerProfile = RegisterProfile.builder()
        .classOf(TEST_USER_CLASS_OF)
        .mainRole(UserProjectRole.DEVELOPER)
        .subRole(UserProjectRole.MEDIA_ART)
        .profile(commonProfile)
        .image(null)
        .build();

    profileService.registerProfile(registerProfile);

    // then
  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void updateProfile_Success() throws Exception {
    // given
    Profile beforeProfile = commonProfile;
    Profile afterProfile = Profile.builder()
        .portfolioLinks(Collections.emptyList())
        .content("update profile")
        .build();

    UpdateProfile updateProfile = UpdateProfile.builder()
      .classOf(TEST_USER_CLASS_OF)
      .mainRole(UserProjectRole.MEDIA_ART)
      .subRole(UserProjectRole.PLANNER)
      .profile(afterProfile)
      .deleteFileName(null)
      .image(createMockImageFile("test" + nextId++))
      .build();

    // when
    afterProfile = profileService.updateProfile(updateProfile);

    // then
    Assertions.assertThat(afterProfile.getMedia()).isNotNull();
    assertNotEquals(beforeProfile.getContent(), afterProfile.getContent());
    assertNotEquals(beforeProfile.getUser().getMainProjectRole(), beforeProfile.getUser().getSubProjectRole());

    uploadService.deleteFile(afterProfile.getMedia().getModifyName(), "/" + TEST_USER_CLASS_OF);
  }

  @Test(expected = ProfileNotFoundException.class)
  @WithMockUser(username = TEST_ADMIN_CLASS_OF, roles = "USER")
  public void updateProfile_Fail_NotFoundProfileException() throws Exception {
    // given
    Profile beforeProfile = commonProfile;
    Profile afterProfile = Profile.builder()
        .portfolioLinks(Collections.emptyList())
        .content("update profile")
        .build();

    UpdateProfile updateProfile = UpdateProfile.builder()
        .classOf(TEST_ADMIN_CLASS_OF)
        .mainRole(UserProjectRole.MEDIA_ART)
        .subRole(UserProjectRole.PLANNER)
        .profile(afterProfile)
        .deleteFileName(null)
        .image(createMockImageFile("test" + nextId++))
        .build();

    // when
    afterProfile = profileService.updateProfile(updateProfile);

    // then
  }

  @Test(expected = ProfileNotFoundException.class)
  public void deleteProfile_Success() {
    setAuthentication(UserRole.ADMIN);

    // given
    String classOf = TEST_ADMIN_CLASS_OF;
    Profile buildProfile = Profile.builder()
        .content("test profile")
        .portfolioLinks(new ArrayList<>(Collections.singleton(new Portfolio(null, "test", "link"))))
        .build();

    RegisterProfile registerProfile = RegisterProfile.builder()
      .classOf(TEST_ADMIN_CLASS_OF)
      .mainRole(UserProjectRole.DEVELOPER)
      .subRole(UserProjectRole.MEDIA_ART)
      .profile(buildProfile)
      .image(null)
      .build();

    profileService.registerProfile(registerProfile);

    // when
    profileService.deleteProfile(TEST_ADMIN_CLASS_OF);

    // then
    profileService.getProfile(TEST_ADMIN_CLASS_OF);
  }

  @Test(expected = PermissionException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void deleteProfile_Fail_PermissionException() {
    // given, when
    profileService.deleteProfile(TEST_ADMIN_CLASS_OF);

    // then
    profileService.getProfile(TEST_ADMIN_CLASS_OF);
  }

  @Test(expected = ProfileNotFoundException.class)
  public void deleteProfile_Fail_ProfileNotFoundException() {
    // given
    setAuthentication(UserRole.ADMIN);

    // when
    profileService.deleteProfile(TEST_ADMIN_CLASS_OF);

    // then
    profileService.getProfile(TEST_ADMIN_CLASS_OF);
  }
}
