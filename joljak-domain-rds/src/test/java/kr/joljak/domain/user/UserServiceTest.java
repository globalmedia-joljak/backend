package kr.joljak.domain.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import kr.joljak.core.security.UserRole;
import kr.joljak.domain.common.CommonDomainTest;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.exception.AlreadyClassOfExistException;
import kr.joljak.domain.user.exception.UserNotFoundException;
import kr.joljak.domain.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

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

  @Test
  @WithMockUser(username = "testUser1", roles = "USER")
  public void updatePhoneNumber_success() {
    // given
    User user = userService.getUserByClassOf(TEST_USER_CLASS_OF);
    String changePhoneNumber = user.getPhoneNumber() + nextId++;

    // when
    userService.updatePhoneNumber(user.getClassOf(), changePhoneNumber);
    user = userService.getUserByClassOf(user.getClassOf());

    // then
    assertEquals(user.getPhoneNumber(), changePhoneNumber);
  }

  @Test
  @WithMockUser(username = "testUser1", roles = "USER")
  public void updateInstagramId_success() {
    // given
    User user = userService.getUserByClassOf(TEST_USER_CLASS_OF);
    String changeInstagramId = "changeInstagramId" + nextId++;

    // when
    userService.updateInstagramId(user.getClassOf(), changeInstagramId);
    user = userService.getUserByClassOf(user.getClassOf());

    // then
    assertEquals(user.getInstagramId(), changeInstagramId);
  }

  @Test
  @WithMockUser(username = "testUser1", roles = "USER")
  public void updateKakaoId_success() {
    // given
    User user = userService.getUserByClassOf(TEST_USER_CLASS_OF);
    String changeKakaoId = "changeKakaoId" + nextId++;

    // when
    userService.updateKakaoId(user.getClassOf(), changeKakaoId);
    user = userService.getUserByClassOf(user.getClassOf());

    // then
    assertEquals(user.getKakaoId(), changeKakaoId);
  }

  @Test
  @WithMockUser(username = "testUser1", roles = "USER")
  public void updatePassword_success() {
    // given
    User user = userService.getUserByClassOf(TEST_USER_CLASS_OF);
    String originalHashedPassword = user.getPassword();
    String afterPassword = "1234567890";

    // when
    userService.updatePassword(user.getClassOf(), afterPassword);
    user = userService.getUserByClassOf(user.getClassOf());

    // then
    assertNotEquals(originalHashedPassword, user.getPassword());
  }
}
