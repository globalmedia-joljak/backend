package kr.joljak.domain.user.service;

import kr.joljak.core.jwt.PermissionException;
import kr.joljak.core.security.AuthenticationUtils;
import kr.joljak.domain.user.dto.SimpleUser;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.exception.AlreadyClassOfExistException;
import kr.joljak.domain.user.exception.UserNotFoundException;
import kr.joljak.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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
  public SimpleUser getMyPage(String classOf) {
    validExistClassOf(classOf);

    return SimpleUser.of(getUserByClassOf(classOf));
  }

  @Transactional
  public void updatePassword(String classOf, String password) {
    log.info( "]-----] UserService::updatePassword [-----[ classOf : {}", classOf);

    validAuthenticationClassOf(classOf);
    User user = getUserByClassOf(classOf);
    user.setPassword(new BCryptPasswordEncoder().encode(password));
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
    log.info( "]-----] UserService::updatePhoneNumber [-----[ classOf : {}", classOf);

    validExistClassOf(classOf);
    User user = userRepository.findByClassOf(classOf)
      .orElseThrow(UserNotFoundException::new);

    user.setPhoneNumber(phoneNumber);
  }

  @Transactional
  public void updateInstagramId(String classOf, String instagramId) {
    log.info( "]-----] UserService::updateInstagramId [-----[ classOf : {}", classOf);

    validExistClassOf(classOf);
    User user = userRepository.findByClassOf(classOf)
      .orElseThrow(UserNotFoundException::new);

    user.setInstagramId(instagramId);
  }

  @Transactional
  public void updateKakaoId(String classOf, String kakaoId) {
    log.info( "]-----] UserService::updateKakaoId [-----[ classOf : {}", classOf);

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

  public User getUserByAuthentication() {
    String authenticationClassOf = AuthenticationUtils.getClassOf();

    return getUserByClassOf(authenticationClassOf);
  }

  public static void validAuthenticationClassOf(String classOf) {
    String authenticationClassOf = AuthenticationUtils.getClassOf();

    if (!classOf.equals(authenticationClassOf)) {
      throw new PermissionException("User does not match");
    }
  }

}
