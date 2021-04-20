package kr.joljak.domain.user.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.joljak.core.jwt.PermissionException;
import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.upload.exception.NotMatchingFileNameException;
import kr.joljak.domain.upload.service.UploadService;
import kr.joljak.domain.user.dto.RegisterProfile;
import kr.joljak.domain.user.dto.UpdateProfile;
import kr.joljak.domain.user.entity.Portfolio;
import kr.joljak.domain.user.entity.Profile;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.entity.UserProjectRole;
import kr.joljak.domain.user.exception.AlreadyProfileExistException;
import kr.joljak.domain.user.exception.ProfileNotFoundException;
import kr.joljak.domain.user.repository.PortfolioRepository;
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
  private final PortfolioRepository portfolioRepository;
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
    for (Portfolio portfolio : profile.getPortfolioLinks()) {
      portfolio.setProfile(profile);
    }
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
  
  @Transactional
  public Profile updateProfile(UpdateProfile updateProfile) {
    String classOf = updateProfile.getClassOf();
    UserService.validAuthenticationClassOf(classOf);
    Profile profile = getProfile(classOf);
    
    User user = profile.getUser();
    validateUserProfilePermissions(classOf, user.getClassOf());
    user.setMainProjectRole(updateProfile.getMainRole());
    user.setSubProjectRole(updateProfile.getSubRole());

    List<Long> deleteIds = profile.getPortfolioLinks()
      .stream()
      .map(Portfolio::getId)
      .collect(Collectors.toList());
    profile.setPortfolioLinks(null);
    portfolioRepository.deleteByIds(deleteIds);
    profile.setPortfolioLinks(updateProfile.getProfile().getPortfolioLinks());
    for (Portfolio portfolio : profile.getPortfolioLinks()) {
      portfolio.setProfile(profile);
    }

    profile.setContent(updateProfile.getProfile().getContent());

    // 삭제할 프로필 이미지가 있거나 새로운 등록할 이미지가 있다면 기존 이미지 삭제 처리
    if (updateProfile.getDeleteFileName() != null) {
      Media media = profile.getMedia();
      if (!media.getModifyName().equals(updateProfile.getDeleteFileName())) {
        throw new NotMatchingFileNameException("file name does not match when you delete.");
      }
      profile.setMedia(null);
      uploadService.deleteFile(media.getModifyName(), "/" + classOf);
    }
    
    // 새로운 이미지로 등록한 경우
    if (updateProfile.getImage() != null) {
      Media media = uploadService.uploadImage(updateProfile.getImage(), "/" + classOf);
      profile.setMedia(media);
    }
    
    return profile;
  }
  
  @Transactional
  public void deleteProfile(String classOf) {
    UserService.validAuthenticationClassOf(classOf);
    
    Profile profile = getProfile(classOf);
    validateUserProfilePermissions(classOf, profile.getUser().getClassOf());
    profile.setUser(null);
    
    profileRepository.delete(profile);
  }
  
  private void validateUserProfilePermissions(String classOf, String author) {
    if (!classOf.equals(author)) {
      throw new PermissionException("User does not have profile permission.");
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
  
  @Transactional(readOnly = true)
  public Page<Profile> getProfilesByKeyWord(
    String name, String classOf,
    UserProjectRole role, PageRequest pageRequest
  ) {
    return profileRepository
      .findByUserNameOrUserClassOfOrUserMainProjectRole(
        name, classOf, role, pageRequest);
  }
}
