package kr.joljak.domain.team;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kr.joljak.domain.common.CommonDomainTest;
import kr.joljak.domain.team.dto.SimpleTeam;
import kr.joljak.domain.team.dto.UpdateTeam;
import kr.joljak.domain.team.entity.Team;
import kr.joljak.domain.team.service.TeamService;
import kr.joljak.domain.upload.exception.FileIsNotImageException;
import kr.joljak.domain.upload.exception.NotMatchingFileNameException;
import kr.joljak.domain.user.exception.UserNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.multipart.MultipartFile;

public class TeamServiceTest extends CommonDomainTest {
  
  @Autowired
  private TeamService teamService;
  
  private SimpleTeam simpleTeam;
  private List<MultipartFile> mockIamges;
  
  @Before
  public void initTeam() throws Exception {
    
    mockIamges = new ArrayList<>(
      Arrays.asList(
        createMockImageFile("test1" + nextId++),
        createMockImageFile("test2" + nextId++)
      )
    );
    
    simpleTeam = createSimpleTeam(
      "test", "test", "test", mockIamges
    );
    
  }
  
  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void createTeam_Success() {
    
    Team team = teamService.addTeam(simpleTeam);
    
    Assertions.assertThat(team).isNotNull();
  }
  
  @Test(expected = FileIsNotImageException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void createWork_Fail_FileIsNotImageException() throws Exception {
    // given
    String path = "/" + TEST_USER_CLASS_OF;
    List<MultipartFile> mockFiles = new ArrayList<>(
      Arrays.asList(
        createMockImageFile("test" + nextId++),
        createMockTextFile("test" + nextId++)
      )
    );
    
    SimpleTeam FileSimpleTeam = createSimpleTeam("test", "test", "test", mockFiles);
    
    // when, then
    teamService.addTeam(FileSimpleTeam);
  }
  
  @Test(expected = UserNotFoundException.class)
  @WithMockUser(username = "Wrong User", roles = "USER")
  public void createWork_Fail_UserNotFoundException() {
    
    // when
    teamService.addTeam(simpleTeam);
    
  }
  
  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void updateTeam_Success() throws Exception {
    
    // given
    Team team = teamService.addTeam(simpleTeam);
    
    List<MultipartFile> updateImages = new ArrayList<>(
      Arrays.asList(
        createMockImageFile("update test" + nextId++)
      )
    );
    
    List<String> deleteFileName = new ArrayList<>(
      Arrays.asList(
        team.getImages().get(0).getModifyName()
      )
    );
    
    UpdateTeam updateTeam = createUpdateTeam(
      "ManU", "media", "Test", deleteFileName, updateImages);
    
    // when
    Team newTeam = teamService.updateTeam(team.getId(), updateTeam);
    
    // then
    Assertions.assertThat(newTeam.getImages()).isNotNull();
    assertEquals(newTeam.getImages().size(), 2);
    assertNotEquals(newTeam.getTeamName(), team.getTeamName());
    assertNotEquals(newTeam.getImages().get(0).getOriginalName(),
      team.getImages().get(0).getOriginalName());
  }
  
  @Test(expected = NotMatchingFileNameException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void updateTeam_Fail_NotMatchingFileNameException() throws Exception {
    // given
    Team team = teamService.addTeam(simpleTeam);
    
    List<MultipartFile> updateImages = new ArrayList<>(
      Arrays.asList(
        createMockImageFile("update test" + nextId++)
      )
    );
    
    List<String> deleteFileName = new ArrayList<>(
      Arrays.asList(
        "no image.jpg"
      )
    );
    
    UpdateTeam updateTeam = createUpdateTeam(
      "ManU", "media", "Test", deleteFileName, updateImages);
    
    // when
    teamService.updateTeam(team.getId(), updateTeam);
  }
  
  @Test(expected = UserNotFoundException.class)
  @WithMockUser(username = "Wrong User", roles = "USER")
  public void updateTeam_Fail_UserNotFoundException() throws Exception {
    // given
    Team team = teamService.addTeam(simpleTeam);
    
    List<MultipartFile> updateImages = new ArrayList<>(
      Arrays.asList(
        createMockImageFile("update test" + nextId++)
      )
    );
    
    List<String> deleteFileName = new ArrayList<>(
      Arrays.asList(
        team.getImages().get(0).getModifyName()
      )
    );
    
    UpdateTeam updateTeam = createUpdateTeam(
      "ManU", "media", "Test", deleteFileName, updateImages);
    
    // when
    Team newTeam = teamService.updateTeam(team.getId(), updateTeam);
  }
  
  public SimpleTeam createSimpleTeam(
    String teamName, String category, String content,
    List<MultipartFile> images
  ) {
    return SimpleTeam.builder()
      .teamName(teamName)
      .category(category)
      .content(content)
      .images(images)
      .build();
  }
  
  private UpdateTeam createUpdateTeam(
    String teamName, String category, String content,
    List<String> deleteFileName, List<MultipartFile> images
  ) {
    return UpdateTeam.builder()
      .teamName(teamName)
      .category(category)
      .content(content)
      .deleteFileName(deleteFileName)
      .images(images)
      .build();
  }
}
