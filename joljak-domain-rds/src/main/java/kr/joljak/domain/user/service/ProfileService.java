package kr.joljak.domain.user.service;

import kr.joljak.core.security.AuthenticationUtils;
import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.upload.service.UploadService;
import kr.joljak.domain.user.entity.Profile;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.entity.UserProjectRole;
import kr.joljak.domain.user.exception.AlreadyProfileExistException;
import kr.joljak.domain.user.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfileService {
  private final ProfileRepository profileRepository;
  private final UserService userService;
  private final UploadService uploadService;

  @Transactional
  public Profile registerProfile(Profile profile, UserProjectRole mainRole, UserProjectRole subRole
    , MultipartFile image) {
    String classOf = AuthenticationUtils.getClassOf();
    checkRegisteredProfile(classOf);

    Media media = null;
    if (image != null) {
      media = uploadService.uploadImage(image, "/" + classOf);
    }
    profile.setMedia(media);

    User user = userService.getUserByClassOf(classOf);
    user.setMainProjectRole(mainRole);
    user.setSubProjectRole(subRole);
    profile.setUser(user);

    return profileRepository.save(profile);
  }

  private void checkRegisteredProfile(String classOf) {
    if (profileRepository.existsByUserClassOf(classOf)) {
      throw new AlreadyProfileExistException();
    }
  }

}
