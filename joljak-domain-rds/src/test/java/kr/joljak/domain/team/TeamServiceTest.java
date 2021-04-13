package kr.joljak.domain.team;

import static org.junit.Assert.assertNotEquals;

import kr.joljak.core.jwt.PermissionException;
import kr.joljak.core.security.UserRole;
import kr.joljak.domain.common.CommonDomainTest;
import kr.joljak.domain.team.dto.SimpleTeam;
import kr.joljak.domain.team.dto.UpdateTeam;
import kr.joljak.domain.team.entity.Team;
import kr.joljak.domain.team.exception.TeamNotFoundException;
import kr.joljak.domain.team.service.TeamService;
import kr.joljak.domain.upload.exception.NotMatchingFileNameException;
import kr.joljak.domain.user.exception.UserNotFoundException;
import kr.joljak.domain.work.entity.ProjectCategory;
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
  private MultipartFile mockFile;
  
  @Before
  public void initTeam() throws Exception {
    
    mockFile = createMockImageFile("test1" + nextId++);
    
    simpleTeam = createSimpleTeam(
      "test", ProjectCategory.WEB_APP, "test", mockFile
    );
    
  }
  
  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void createTeam_Success() {
    
    Team team = teamService.addTeam(simpleTeam);
    
    Assertions.assertThat(team).isNotNull();
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
    
    MultipartFile updateFile = createMockTextFile("update test" + nextId++);
    
    String deleteFileName =
        team.getMedia().getModifyName();
    
    UpdateTeam updateTeam = createUpdateTeam(
      "ManU", ProjectCategory.WEB_APP, "Test", deleteFileName, updateFile);
    
    // when
    Team newTeam = teamService.updateTeam(team.getId(), updateTeam);
    
    // then
    Assertions.assertThat(newTeam.getMedia()).isNotNull();
    assertNotEquals(newTeam.getTeamName(), team.getTeamName());
    assertNotEquals(newTeam.getMedia().getOriginalName(),
      team.getMedia().getOriginalName());
  }
  
  @Test(expected = NotMatchingFileNameException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void updateTeam_Fail_NotMatchingFileNameException() throws Exception {
    // given
    Team team = teamService.addTeam(simpleTeam);
    
    MultipartFile updateImages = createMockTextFile("update test" + nextId++);
    
    
    String deleteFileName = "no image.jpg";
 
    
    UpdateTeam updateTeam = createUpdateTeam(
      "ManU", ProjectCategory.WEB_APP, "Test", deleteFileName, updateImages);
    
    // when
    teamService.updateTeam(team.getId(), updateTeam);
  }
  
  @Test(expected = UserNotFoundException.class)
  @WithMockUser(username = "Wrong User", roles = "USER")
  public void updateTeam_Fail_UserNotFoundException() throws Exception {
    // given
    Team team = teamService.addTeam(simpleTeam);
    
    MultipartFile updateImages = createMockImageFile("update test" + nextId++);
    
    
    String deleteFileName = team.getMedia().getModifyName();
   
    
    UpdateTeam updateTeam = createUpdateTeam(
      "ManU", ProjectCategory.WEB_APP, "Test", deleteFileName, updateImages);
    
    // when
    Team newTeam = teamService.updateTeam(team.getId(), updateTeam);
  }
  
  @Test(expected = TeamNotFoundException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void deleteTeam_Success() {
    // given
    Team team = teamService.addTeam(simpleTeam);
    
    // when
    teamService.deleteTeamById(team.getId());
    
    // then
    teamService.getTeamById(team.getId());
  }
  
  @Test(expected = PermissionException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void deleteTeam_Fail_PermissionException() {
    
    // given
    setAuthentication(UserRole.USER);
    Team team = teamService.addTeam(simpleTeam);
    
    // when, then
    setAuthentication(UserRole.ADMIN);
    teamService.deleteTeamById(team.getId());
  }
  
  @Test(expected = TeamNotFoundException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void deleteTeam_Fail_TeamNotFoundException() {
    
    // given
    Team team = teamService.addTeam(simpleTeam);
    
    // when, then
    teamService.deleteTeamById(team.getId() + 1);
  }
  
  public SimpleTeam createSimpleTeam(
    String teamName, ProjectCategory category, String content,
    MultipartFile file
  ) {
    return SimpleTeam.builder()
      .teamName(teamName)
      .category(category)
      .content(content)
      .file(file)
      .build();
  }
  
  private UpdateTeam createUpdateTeam(
    String teamName, ProjectCategory category, String content,
    String deleteFileName, MultipartFile file
  ) {
    return UpdateTeam.builder()
      .teamName(teamName)
      .category(category)
      .content(content)
      .deleteFileName(deleteFileName)
      .file(file)
      .build();
  }
}
