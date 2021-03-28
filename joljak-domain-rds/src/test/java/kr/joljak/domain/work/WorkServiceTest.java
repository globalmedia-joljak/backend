package kr.joljak.domain.work;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kr.joljak.domain.common.CommonDomainTest;
import kr.joljak.domain.work.dto.SimpleWork;
import kr.joljak.domain.work.entity.Work;
import kr.joljak.domain.work.service.WorkService;
import kr.joljak.domain.upload.exception.FileIsNotImageException;
import kr.joljak.domain.user.exception.UserNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.multipart.MultipartFile;

public class WorkServiceTest extends CommonDomainTest {

  @Autowired
  private WorkService workService;

  private List<String> teamMember;
  private List<MultipartFile> imageFile;
  private SimpleWork simpleWork;

  @Before
  public void initWork() throws Exception {
    // given
    teamMember = new ArrayList<>();
    teamMember.add("kevin");
    teamMember.add("alex");

    imageFile = new ArrayList<>(
      Arrays.asList(
        createMockImageFile("test" + nextId++),
        createMockImageFile("test" + nextId++)
      )
    );

    simpleWork = createSimpleWork(
      "test", "test", teamMember, "test", "test"
    );
  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void createWork_Success() {
    // when
    Work work = workService.addTeam(simpleWork, imageFile);

    // then
    Assertions.assertThat(work).isNotNull();
  }

  @Test(expected = FileIsNotImageException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void createWork_Fail_FileIsNotImageException() throws Exception {
    // given
    String path = "/" + TEST_USER_CLASS_OF;
    List<MultipartFile> imageFiles = new ArrayList<>(
      Arrays.asList(
        createMockImageFile("test" + nextId++),
        createMockTextFile("test" + nextId++)
      )
    );

    // when, then
    Work work = workService.addTeam(simpleWork, imageFiles);
  }

  @Test(expected = UserNotFoundException.class)
  @WithMockUser(username = "Worng User", roles = "USER")
  public void createWork_Fail_UserNotFoundException() {
    // when
    Work work = workService.addTeam(simpleWork, imageFile);

    // then
    Assertions.assertThat(work).isNotNull();
  }

  private SimpleWork createSimpleWork(
    String workName, String teamName, List<String> teamMember,
    String content, String teamVideoUrl
  ) {
    return SimpleWork.builder()
      .workName(workName)
      .teamMember(teamMember)
      .teamName(teamName)
      .content(content)
      .teamVideoUrl(teamVideoUrl)
      .build();
  }

}
