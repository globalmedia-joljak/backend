package kr.joljak.domain.ideaBoard;

import java.util.ArrayList;
import java.util.List;
import kr.joljak.domain.IdeaBoard.dto.SimpleIdeaBoard;
import kr.joljak.domain.IdeaBoard.entity.IdeaBoard;
import kr.joljak.domain.IdeaBoard.entity.ProjectStatus;
import kr.joljak.domain.IdeaBoard.service.IdeaBoardService;
import kr.joljak.domain.common.CommonDomainTest;
import kr.joljak.domain.upload.service.UploadService;
import kr.joljak.domain.user.entity.UserProjectRole;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;

public class IdeaBoardServiceTest extends CommonDomainTest {

  @Autowired
  private UploadService uploadService;

  @Autowired
  private IdeaBoardService ideaBoardService;

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void createIdeaBoard_Success() {
    // given
    List<UserProjectRole> requiredPosition = new ArrayList<>();
    requiredPosition.add(UserProjectRole.DESIGNER);

    MockMultipartFile textFile = createMockTextFile("test" + nextId++);
    SimpleIdeaBoard simpleIdeaBoard = createSimpleIdeaBoard(
      "test", "test", "010123415678", ProjectStatus.ONGOING,
      requiredPosition, UserProjectRole.DEVELOPER);
    // when
    IdeaBoard ideaBoard = ideaBoardService.addIdeaBoard(simpleIdeaBoard, textFile);

    // then
    Assertions.assertThat(ideaBoard).isNotNull();

  }

  private SimpleIdeaBoard createSimpleIdeaBoard(
    String title, String content, String contact,
    ProjectStatus status, List<UserProjectRole> requiredPositions,
    UserProjectRole mainRole
  ) {
    return SimpleIdeaBoard.builder()
      .title(title)
      .content(content)
      .contact(contact)
      .status(status)
      .requiredPositions(requiredPositions)
      .mainRole(mainRole)
      .build();
  }

  private MockMultipartFile createMockTextFile(String fileName) {
    return new MockMultipartFile(
      "file",
      fileName + ".txt",
      "text/plain",
      "hello file".getBytes()
    );
  }

}
