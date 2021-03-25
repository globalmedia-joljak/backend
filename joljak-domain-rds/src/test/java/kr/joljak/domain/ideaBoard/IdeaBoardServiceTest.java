package kr.joljak.domain.ideaBoard;

import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import kr.joljak.core.jwt.PermissionException;
import kr.joljak.core.security.UserRole;
import kr.joljak.domain.Ideaboard.dto.SimpleIdeaBoard;
import kr.joljak.domain.Ideaboard.entity.IdeaBoard;
import kr.joljak.domain.Ideaboard.entity.ProjectStatus;
import kr.joljak.domain.Ideaboard.exception.IdeaBoardNotFoundException;
import kr.joljak.domain.Ideaboard.service.IdeaBoardService;
import kr.joljak.domain.common.CommonDomainTest;
import kr.joljak.domain.upload.exception.NotMatchingFileNameException;
import kr.joljak.domain.upload.service.UploadService;
import kr.joljak.domain.user.entity.UserProjectRole;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;

public class IdeaBoardServiceTest extends CommonDomainTest {

  @Autowired
  private UploadService uploadService;

  @Autowired
  private IdeaBoardService ideaBoardService;

  private MockMultipartFile textFile;
  private SimpleIdeaBoard simpleIdeaBoard;
  private List<UserProjectRole> requiredPosition;
  private IdeaBoard ideaBoard;

  @Before
  public void initIdeaBoard() {
    setAuthentication(UserRole.USER);
    // given
    requiredPosition = new ArrayList<>();
    requiredPosition.add(UserProjectRole.DESIGNER);

    textFile = createMockTextFile("test" + nextId++);
    simpleIdeaBoard = createSimpleIdeaBoard(
      "test", "test", "010123415678", ProjectStatus.ONGOING,
      requiredPosition, UserProjectRole.DEVELOPER, null);

    ideaBoard = ideaBoardService.addIdeaBoard(simpleIdeaBoard, textFile);
  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void createIdeaBoard_Success() {
    // when
    IdeaBoard ideaBoard = ideaBoardService.addIdeaBoard(simpleIdeaBoard, textFile);

    // then
    Assertions.assertThat(ideaBoard).isNotNull();

  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void updateIdeaBoard_Success() throws Exception {
    // given
    MockMultipartFile newTextFile = createMockTextFile("test2" + nextId++);

    SimpleIdeaBoard newSimpleIdeaBoard = createSimpleIdeaBoard(
      "test2", "test2", "01079846512", ProjectStatus.COMPLETE,
      requiredPosition, UserProjectRole.DEVELOPER, null);

    IdeaBoard ideaBoard = ideaBoardService.addIdeaBoard(simpleIdeaBoard, textFile);

    // when
    IdeaBoard newIdeaBoard = ideaBoardService
      .updateIdeaBoardById(ideaBoard.getId(), newSimpleIdeaBoard, newTextFile);

    // then
    Assertions.assertThat(newIdeaBoard.getMedia()).isNotNull();
    assertNotEquals(ideaBoard.getContact(), newIdeaBoard.getContact());
    assertNotEquals(ideaBoard.getMedia(), newIdeaBoard.getMedia());

  }

  @Test(expected = NotMatchingFileNameException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void updateIdeaBoard_Fail_NotMatchingFIleNameException() throws Exception {
    // given
    MockMultipartFile newTextFile = createMockTextFile("test2" + nextId++);

    SimpleIdeaBoard newSimpleIdeaBoard = createSimpleIdeaBoard(
      "test2", "test2", "01079846512", ProjectStatus.COMPLETE,
      requiredPosition, UserProjectRole.DEVELOPER, "any");

    IdeaBoard ideaBoard = ideaBoardService.addIdeaBoard(simpleIdeaBoard, textFile);

    // when
    IdeaBoard newIdeaBoard = ideaBoardService
      .updateIdeaBoardById(ideaBoard.getId(), newSimpleIdeaBoard, newTextFile);

    // then
    Assertions.assertThat(newIdeaBoard.getMedia()).isNotNull();
    assertNotEquals(ideaBoard.getContact(), newIdeaBoard.getContact());
    assertNotEquals(ideaBoard.getMedia(), newIdeaBoard.getMedia());

  }

  @Test(expected = PermissionException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void updateIdeaBoard_Fail_UseNotMatchException() throws Exception {
    // given
    setAuthentication(UserRole.ADMIN);
    MockMultipartFile newTextFile = createMockTextFile("test2" + nextId++);

    SimpleIdeaBoard newSimpleIdeaBoard = createSimpleIdeaBoard(
      "test2", "test2", "01079846512", ProjectStatus.COMPLETE,
      requiredPosition, UserProjectRole.DEVELOPER, null);

    // when
    IdeaBoard newIdeaBoard = ideaBoardService
      .updateIdeaBoardById(ideaBoard.getId(), newSimpleIdeaBoard, newTextFile);

  }

  @Test(expected = IdeaBoardNotFoundException.class)
  public void deleteIdeaBoard_Success() {
    setAuthentication(UserRole.USER);

    // given
    ideaBoardService.deleteIdeaBoardById(ideaBoard.getId());

    // then
    ideaBoardService.getIdeaBoardsById(ideaBoard.getId());
  }

  @Test(expected = PermissionException.class)
  public void deleteIdeaBoard_Fail_PermissionException() {
    setAuthentication(UserRole.ADMIN);

    // given, when
    ideaBoardService.deleteIdeaBoardById(ideaBoard.getId());
  }

  @Test(expected = IdeaBoardNotFoundException.class)
  public void deleteIdeaBoard_Fail_IdeaBoardNotFoundException() {
    setAuthentication(UserRole.USER);

    // given, when
    ideaBoardService.deleteIdeaBoardById(ideaBoard.getId() + 1L);
  }

  private SimpleIdeaBoard createSimpleIdeaBoard(
    String title, String content, String contact,
    ProjectStatus status, List<UserProjectRole> requiredPositions,
    UserProjectRole mainRole, String deleteFileName
  ) {
    return SimpleIdeaBoard.builder()
      .title(title)
      .content(content)
      .contact(contact)
      .status(status)
      .requiredPositions(requiredPositions)
      .mainRole(mainRole)
      .deleteFileName(deleteFileName)
      .build();
  }

}
