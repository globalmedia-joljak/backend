package kr.joljak.domain.ideaBoard;

import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import kr.joljak.domain.Ideaboard.dto.SimpleIdeaBoard;
import kr.joljak.domain.Ideaboard.entity.IdeaBoard;
import kr.joljak.domain.Ideaboard.entity.ProjectStatus;
import kr.joljak.domain.Ideaboard.service.IdeaBoardService;
import kr.joljak.domain.common.CommonDomainTest;
import kr.joljak.domain.upload.exception.NotMatchingFIleNameException;
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

  @Before
  public void initIdeaBoard(){
    // given
    requiredPosition = new ArrayList<>();
    requiredPosition.add(UserProjectRole.DESIGNER);

    textFile = createMockTextFile("test" + nextId++);
    simpleIdeaBoard = createSimpleIdeaBoard(
      "test", "test", "010123415678", ProjectStatus.ONGOING,
      requiredPosition, UserProjectRole.DEVELOPER, null);
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
    IdeaBoard newIdeaBoard = ideaBoardService.updateIdeaBoardById(ideaBoard.getId(), newSimpleIdeaBoard, newTextFile);

    // then
    Assertions.assertThat(newIdeaBoard.getFile()).isNotNull();
    assertNotEquals(ideaBoard.getContact(), newIdeaBoard.getContact());
    assertNotEquals(ideaBoard.getFile(), newIdeaBoard.getFile());

  }

  @Test(expected = NotMatchingFIleNameException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void updateIdeaBoard_Fail_NotMatchingFIleNameException() throws Exception {
    // given
    MockMultipartFile newTextFile = createMockTextFile("test2" + nextId++);

    SimpleIdeaBoard newSimpleIdeaBoard = createSimpleIdeaBoard(
      "test2", "test2", "01079846512", ProjectStatus.COMPLETE,
      requiredPosition, UserProjectRole.DEVELOPER, "any");

    IdeaBoard ideaBoard = ideaBoardService.addIdeaBoard(simpleIdeaBoard, textFile);

    // when
    IdeaBoard newIdeaBoard = ideaBoardService.updateIdeaBoardById(ideaBoard.getId(), newSimpleIdeaBoard, newTextFile);

    // then
    Assertions.assertThat(newIdeaBoard.getFile()).isNotNull();
    assertNotEquals(ideaBoard.getContact(), newIdeaBoard.getContact());
    assertNotEquals(ideaBoard.getFile(), newIdeaBoard.getFile());

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
