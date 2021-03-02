package kr.joljak.user.service;

import kr.joljak.user.entity.User;
import kr.joljak.user.exception.AlreadyClassOfExistException;
import kr.joljak.user.repository.UserRepository;
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
  public void checkDuplicateClassOf(String classOf) {
    boolean isDuplicate = userRepository.existsByClassOf(classOf);
    if (isDuplicate) {
      throw new AlreadyClassOfExistException();
    }
  }

}
