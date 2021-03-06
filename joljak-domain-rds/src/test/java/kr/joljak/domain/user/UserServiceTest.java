package kr.joljak.domain.user;

import kr.joljak.core.security.UserRole;
import kr.joljak.domain.common.CommonDomainTest;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.exception.AlreadyClassOfExistException;
import kr.joljak.domain.user.exception.UserNotFoundException;
import kr.joljak.domain.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTest extends CommonDomainTest {
  @Autowired
  private UserService userService;

  @Test
  public void signUp_Success() {
    // given
    User mockUser = createMockUser(UserRole.USER);

    // when
    User result = userService.signUp(mockUser);

    // then
    Assertions.assertThat(result).isNotNull();
  }

  @Test(expected = AlreadyClassOfExistException.class)
  public void signUp_Fail_AlreadyClassOfExistException() {
    // given
    User alreadyExistUser = getUser();

    // when
    userService.signUp(alreadyExistUser);
  }

  @Test
  public void checkDuplicateClassOf_Success_AlreadyClassOfExistException() {
    // given
    String successClass = "successClass";

    // when
    userService.checkDuplicateClassOf(successClass);
  }

  @Test(expected = AlreadyClassOfExistException.class)
  public void checkDuplicateClassOf_Fail_AlreadyClassOfExistException() {
    // given
    String successClass = getUser().getClassOf();

    // when
    userService.checkDuplicateClassOf(successClass);
  }

  @Test
  public void getUserByClassOf_Success() {
    // given
    User user = getUser();

    // when
    User result = userService.getUserByClassOf(user.getClassOf());

    // then
    Assertions.assertThat(result).isNotNull();
  }

  @Test(expected = UserNotFoundException.class)
  public void getUserByClassOf_Fail_UserNotFoundException() {
    // given
    String notExistUser = "notExistUser" + nextId++;

    // when
    User result = userService.getUserByClassOf(notExistUser);

    // then
  }

}
