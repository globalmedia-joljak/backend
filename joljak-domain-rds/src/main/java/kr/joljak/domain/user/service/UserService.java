package kr.joljak.domain.user.service;

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

}
