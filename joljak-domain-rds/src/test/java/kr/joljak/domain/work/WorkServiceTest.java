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
import kr.joljak.domain.work.entity.ProjectCategory;
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
      "test", "test", teamMember, ProjectCategory.ANIMATION_FILM,
      "2018", "test", "test", imageFile
    );
  }


  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void createWork_Success() {
    // when
    Work work = workService.addWork(simpleWork);

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
  
    SimpleWork simpleWork1 = createSimpleWork(
      "test", "test", teamMember, ProjectCategory.ANIMATION_FILM,
      "2018", "test", "test", imageFiles
    );

    // when, then
    workService.addWork(simpleWork1);
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
  
    SimpleWork simpleWork1 = createSimpleWork(
      "test", "test", teamMember, ProjectCategory.ANIMATION_FILM,
      "2018", "test", "test", manyImageFiles
    );
    
    workService.addWork(simpleWork1);
  }

  @Test(expected = UserNotFoundException.class)
  @WithMockUser(username = "Wrong User", roles = "USER")
  public void createWork_Fail_UserNotFoundException() {

    // when
    Work work = workService.addWork(simpleWork);

  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void updateWork_Success() throws Exception {

    updateImageFile = new ArrayList<>(
      Arrays.asList(
        createMockImageFile("update test" + nextId++)
      )
    );

    Work work = workService.addWork(simpleWork);

    List<String> deleteFileName = new ArrayList<>(
      Arrays.asList(
        work.getImages().get(0).getModifyName()
      )
    );

    updateWork = createUpdateWork(
      "update test",  "update test", teamMember, ProjectCategory.ANIMATION_FILM,
       "test content", updateImageFile, deleteFileName, "2018");

    Work newWork = workService.updateWorkById(work.getId(), updateWork);

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
  
    SimpleWork simpleWork1 = createSimpleWork(
      "test", "test", teamMember, ProjectCategory.ANIMATION_FILM,
      "2018", "test", "test", updateImageFile
    );
    
    Work work = workService.addWork(simpleWork1);

    List<String> deleteFileName = new ArrayList<>(
      Arrays.asList(
        "no image.jpg"
      )
    );

    updateWork = createUpdateWork(
      "update test",  "update test", teamMember, ProjectCategory.ANIMATION_FILM,
      "update test", null, deleteFileName, "2018");

    Work newWork = workService.updateWorkById(work.getId(), updateWork);
  }

  @Test(expected = UserNotFoundException.class)
  @WithMockUser(username = "Wrong User", roles = "USER")
  public void updateWork_Fail_UserNotFoundException() throws Exception {

    updateImageFile = new ArrayList<>(
      Arrays.asList(
        createMockImageFile("update test" + nextId++)
      )
    );

    Work work = workService.addWork(simpleWork);

    List<String> deleteFileName = new ArrayList<>(
      Arrays.asList(
        work.getImages().get(0).getModifyName()
      )
    );

    updateWork = createUpdateWork(
      "update test",  "update test", teamMember, ProjectCategory.ANIMATION_FILM,
      "test content", updateImageFile, deleteFileName, "2018");

    Work newWork = workService.updateWorkById(work.getId(), updateWork);
  }

  @Test(expected = WorkNotFoundException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void deleteWork_Success() throws Exception {

    Work work = workService.addWork(simpleWork);
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
    Work work = workService.addWork(simpleWork);

    // when, then
    setAuthentication(UserRole.ADMIN);
    workService.deleteWorkById(work.getId());
  }

  @Test(expected = WorkNotFoundException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void deleteWork_Fail_WorkNotFoundException() {

    // given
    Work work = workService.addWork(simpleWork);

    // when, then
    workService.deleteWorkById(work.getId() + 1L);
  }

  private SimpleWork createSimpleWork(
    String workName, String teamName, List<String> teamMember, ProjectCategory projectCategory, String exhibitedYear,
    String content, String teamVideoUrl, List<MultipartFile> images
  ) {
    return SimpleWork.builder()
      .workName(workName)
      .teamMember(teamMember)
      .teamName(teamName)
      .projectCategory(projectCategory)
      .exhibitedYear(exhibitedYear)
      .content(content)
      .teamVideoUrl(teamVideoUrl)
      .images(images)
      .build();
  }

  private UpdateWork createUpdateWork(
    String workName, String teamName, List<String> teamMember, ProjectCategory projectCategory,
    String content, List<MultipartFile> images, List<String> deleteFileName, String exhibitedyear
  ) {
    return UpdateWork.builder()
      .workName(workName)
      .teamMember(teamMember)
      .teamName(teamName)
      .content(content)
      .projectCategory(projectCategory)
      .exhibitedYear(exhibitedyear)
      .images(images)
      .deleteFileName(deleteFileName)
      .build();
  }

}
