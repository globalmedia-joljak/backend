package kr.joljak.domain.user.service;

import kr.joljak.core.jwt.PermissionException;
import kr.joljak.core.security.AuthenticationUtils;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.exception.AlreadyClassOfExistException;
import kr.joljak.domain.user.exception.UserNotFoundException;
import kr.joljak.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  @Transactional
  public User signUp(User user) {
    checkDuplicateClassOf(user.getClassOf());
    return userRepository.save(user);
  }

  @Transactional(readOnly = true)
  public User getUserByClassOf(String classOf) {
    return userRepository.findByClassOf(classOf)
      .orElseThrow(UserNotFoundException::new);
  }

  @Transactional(readOnly = true)
  public void checkDuplicateClassOf(String classOf) {
    boolean isDuplicate = userRepository.existsByClassOf(classOf);
    if (isDuplicate) {
      throw new AlreadyClassOfExistException();
    }
  }

  @Transactional
  public void updatePhoneNumber(String classOf, String phoneNumber) {
    validExistClassOf(classOf);
    User user = userRepository.findByClassOf(classOf)
      .orElseThrow(UserNotFoundException::new);

    user.setPhoneNumber(phoneNumber);
  }

  @Transactional
  public void updateInstagramId(String classOf, String instagramId) {
    validExistClassOf(classOf);
    User user = userRepository.findByClassOf(classOf)
      .orElseThrow(UserNotFoundException::new);

    user.setInstagramId(instagramId);
  }

  @Transactional
  public void updateKakaoId(String classOf, String kakaoId) {
    validExistClassOf(classOf);
    User user = userRepository.findByClassOf(classOf)
        .orElseThrow(UserNotFoundException::new);

    user.setKakaoId(kakaoId);
  }

  public void validExistClassOf(String classOf) {
    validAuthenticationClassOf(classOf);

    if (!userRepository.existsByClassOf(classOf)) {
      throw new UserNotFoundException();
    }
  }

  public static void validAuthenticationClassOf(String classOf) {
    String authenticationClassOf = AuthenticationUtils.getClassOf();
    if (!classOf.equals(authenticationClassOf)) {
      throw new PermissionException("User does not match");
    }
  }

}
