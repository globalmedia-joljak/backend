package kr.joljak.domain.team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kr.joljak.domain.common.CommonDomainTest;
import kr.joljak.domain.team.dto.SimpleTeam;
import kr.joljak.domain.team.entity.Team;
import kr.joljak.domain.team.service.TeamService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.multipart.MultipartFile;

public class TeamServiceTest extends CommonDomainTest {

  @Autowired
  private TeamService teamService;

  private List<String> teamMember;
  private List<MultipartFile> imageFile;
  private SimpleTeam simpleTeam;

  @Before
  public void initTeam() throws Exception {
    // given
    teamMember = new ArrayList<>();
    teamMember.add("kevin");
    teamMember.add("alex");

    imageFile = new ArrayList<>(
      Arrays.asList(
        createMockImageFile("test" + nextId++),
        createMockTextFile("test" + nextId++)
      )
    );

    simpleTeam = createSimpleTeam(
      "test", "test", teamMember, "test", "test"
    );
  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void createTeam_Success() {
    // when
    Team team = teamService.addTeam(simpleTeam, imageFile);

    // then
    Assertions.assertThat(team).isNotNull();
  }

  @Test(expected = Exception.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void uploadImages_Fail_FileIsNotImageException() throws Exception {
    // given
    String path = "/" + TEST_USER_CLASS_OF;
    List<MultipartFile> imageFiles = new ArrayList<>(
      Arrays.asList(
        createMockImageFile("test" + nextId++),
        createMockTextFile("test" + nextId++)
      )
    );

    // when, then
    Team team = teamService.addTeam(simpleTeam, imageFiles);

  }

  private SimpleTeam createSimpleTeam(
    String workName, String teamName, List<String> teamMember,
    String content, String teamVideoUrl
  ) {
    return SimpleTeam.builder()
      .workName(workName)
      .teamMember(teamMember)
      .teamName(teamName)
      .content(content)
      .teamVideoUrl(teamVideoUrl)
      .build();
  }

}
