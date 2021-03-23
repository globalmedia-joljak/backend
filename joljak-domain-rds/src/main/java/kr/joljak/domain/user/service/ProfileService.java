package kr.joljak.domain.user.service;

import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.upload.service.UploadService;
import kr.joljak.domain.user.dto.RegisterProfile;
import kr.joljak.domain.user.entity.Profile;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.exception.AlreadyProfileExistException;
import kr.joljak.domain.user.exception.ProfileNotFoundException;
import kr.joljak.domain.user.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {
  private final ProfileRepository profileRepository;
  private final UserService userService;
  private final UploadService uploadService;

  @Transactional
  public Profile registerProfile(RegisterProfile registerProfile) {
    String classOf = registerProfile.getClassOf();
    userService.validExistClassOf(classOf);
    checkRegisteredProfile(classOf);

    Media media = null;
    if (registerProfile.getImage() != null) {
      media = uploadService.uploadImage(registerProfile.getImage(), "/" + classOf);
    }

    Profile profile = registerProfile.getProfile();
    profile.setMedia(media);

    User user = userService.getUserByClassOf(classOf);
    user.setMainProjectRole(registerProfile.getMainRole());
    user.setSubProjectRole(registerProfile.getSubRole());
    profile.setUser(user);

    return profileRepository.save(profile);
  }

  private void checkRegisteredProfile(String classOf) {
    if (profileRepository.existsByUserClassOf(classOf)) {
      throw new AlreadyProfileExistException();
    }
  }

  @Transactional(readOnly = true)
  public Page<Profile> getProfiles(PageRequest pageRequest) {
    return profileRepository.findAll(pageRequest);
  }

  @Transactional(readOnly = true)
  public Profile getProfile(String classOf) {
    return profileRepository.findByUserClassOf(classOf)
      .orElseThrow(ProfileNotFoundException::new);
  }
}
