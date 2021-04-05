package kr.joljak.domain.team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kr.joljak.domain.common.CommonDomainTest;
import kr.joljak.domain.team.dto.SimpleTeam;
import kr.joljak.domain.team.entity.Team;
import kr.joljak.domain.team.service.TeamService;
import kr.joljak.domain.upload.exception.FileIsNotImageException;
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

}
