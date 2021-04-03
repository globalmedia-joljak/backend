package kr.joljak.domain.work;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.validation.ConstraintViolationException;
import kr.joljak.core.jwt.PermissionException;
import kr.joljak.core.security.UserRole;
import kr.joljak.domain.common.CommonDomainTest;
import kr.joljak.domain.upload.exception.FileIsNotImageException;
import kr.joljak.domain.upload.exception.NotMatchingFileNameException;
import kr.joljak.domain.user.exception.UserNotFoundException;
import kr.joljak.domain.work.dto.SimpleWork;
import kr.joljak.domain.work.dto.UpdateWork;
import kr.joljak.domain.work.entity.Work;
import kr.joljak.domain.work.exception.WorkNotFoundException;
import kr.joljak.domain.work.service.WorkService;
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
  private List<MultipartFile> updateImageFile;
  private SimpleWork simpleWork;
  private UpdateWork updateWork;
  private Work deleteWork;

  @Before
  public void initWork() throws Exception {

    // given
    teamMember = new ArrayList<>();
    teamMember.add("kevin");
    teamMember.add("alex");

    imageFile = new ArrayList<>(
      Arrays.asList(
        createMockImageFile("test1" + nextId++),
        createMockImageFile("test2" + nextId++)
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
    Work work = workService.addWork(simpleWork, imageFile);

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
    workService.addWork(simpleWork, imageFiles);
  }

  @Test(expected = ConstraintViolationException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void createWork_Fail_TooManyFiles() throws Exception {

    List<MultipartFile> manyImageFiles = new ArrayList<>(
      Arrays.asList(
        createMockImageFile("test1" + nextId++),
        createMockImageFile("test2" + nextId++),
        createMockImageFile("test3" + nextId++),
        createMockImageFile("test4" + nextId++),
        createMockImageFile("test5" + nextId++),
        createMockImageFile("test6" + nextId++)
      )
    );

    workService.addWork(simpleWork, manyImageFiles);
  }

  @Test(expected = UserNotFoundException.class)
  @WithMockUser(username = "Wrong User", roles = "USER")
  public void createWork_Fail_UserNotFoundException() {

    // when
    Work work = workService.addWork(simpleWork, imageFile);

  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void updateWork_Success() throws Exception {

    updateImageFile = new ArrayList<>(
      Arrays.asList(
        createMockImageFile("update test" + nextId++)
      )
    );

    Work work = workService.addWork(simpleWork, imageFile);

    List<String> deleteFileName = new ArrayList<>(
      Arrays.asList(
        work.getImages().get(0).getModifyName()
      )
    );

    updateWork = createUpdateWork(
      "update test", "update test", teamMember,
      "update test", "update test", deleteFileName);

    Work newWork = workService.updateWorkById(work.getId(), updateWork, updateImageFile);

    Assertions.assertThat(newWork.getImages()).isNotNull();
    assertEquals(newWork.getImages().size(), 2);
    assertNotEquals(newWork.getWorkName(), work.getWorkName());
    assertNotEquals(newWork.getImages().get(0).getOriginalName(),
      work.getImages().get(0).getOriginalName());
  }

  @Test(expected = NotMatchingFileNameException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void updateWork_Fail_NotMatchingFileNameException() throws Exception {
    updateImageFile = new ArrayList<>(
      Arrays.asList(
        createMockImageFile("update test" + nextId++)
      )
    );

    Work work = workService.addWork(simpleWork, imageFile);

    List<String> deleteFileName = new ArrayList<>(
      Arrays.asList(
        "no image.jpg"
      )
    );

    updateWork = createUpdateWork(
      "update test", "update test", teamMember,
      "update test", "update test", deleteFileName);

    Work newWork = workService.updateWorkById(work.getId(), updateWork, updateImageFile);
  }

  @Test(expected = UserNotFoundException.class)
  @WithMockUser(username = "Wrong User", roles = "USER")
  public void updateWork_Fail_UserNotFoundException() throws Exception {

    updateImageFile = new ArrayList<>(
      Arrays.asList(
        createMockImageFile("update test" + nextId++)
      )
    );

    Work work = workService.addWork(simpleWork, imageFile);

    List<String> deleteFileName = new ArrayList<>(
      Arrays.asList(
        work.getImages().get(0).getModifyName()
      )
    );

    updateWork = createUpdateWork(
      "update test", "update test", teamMember,
      "update test", "update test", deleteFileName);

    Work newWork = workService.updateWorkById(work.getId(), updateWork, updateImageFile);
  }

  @Test(expected = WorkNotFoundException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void deleteWork_Success() throws Exception {

    Work work = workService.addWork(simpleWork, imageFile);
    // when
    workService.deleteWorkById(work.getId());

    // then
    workService.getWorkById(work.getId());
  }

  @Test(expected = PermissionException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void deleteWork_Fail_PermissionException() {

    // given
    setAuthentication(UserRole.USER);
    Work work = workService.addWork(simpleWork, imageFile);

    // when, then
    setAuthentication(UserRole.ADMIN);
    workService.deleteWorkById(work.getId());
  }

  @Test(expected = WorkNotFoundException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void deleteWork_Fail_WorkNotFoundException() {

    // given
    Work work = workService.addWork(simpleWork, imageFile);

    // when, then
    workService.deleteWorkById(work.getId() + 1L);
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

  private UpdateWork createUpdateWork(
    String workName, String teamName, List<String> teamMember,
    String content, String teamVideoUrl, List<String> deleteFileName
  ) {
    return UpdateWork.builder()
      .workName(workName)
      .teamMember(teamMember)
      .teamName(teamName)
      .content(content)
      .teamVideoUrl(teamVideoUrl)
      .deleteFileName(deleteFileName)
      .build();
  }

}
