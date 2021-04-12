package kr.joljak.api.team;

import kr.joljak.api.common.CommonApiTest;
import kr.joljak.api.teams.request.RegisterTeamRequest;
import kr.joljak.domain.team.dto.SimpleTeam;
import kr.joljak.domain.team.entity.Team;
import kr.joljak.domain.team.service.TeamService;
import kr.joljak.domain.work.entity.ProjectCategory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

public class GetTeam extends CommonApiTest {
  
  @Autowired
  private TeamService teamService;
  
  private RegisterTeamRequest registerTeamRequest;
  private SimpleTeam simpleTeam;
  private Long id;
  
  @Before
  public void initTeam() {
    registerTeamRequest = createTeamReqeust(
      "test teamName", "test content", ProjectCategory.WEB_APP
    );
    
    simpleTeam = RegisterTeamRequest.toRegisterTeam(registerTeamRequest);
    Team team = teamService.addTeam(simpleTeam);
    id = team.getId();
  }
  
  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void getTeam_Success() throws Exception {
    
    MvcResult mvcResult = mockMvc.perform(
      get(TEAM_URL + "/" + id)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();
    
    assertEquals(200, mvcResult.getResponse().getStatus());
  }
  
  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void getTeam_Fail_TeamNotFoundException() throws Exception {
    
    MvcResult mvcResult = mockMvc.perform(
      get(TEAM_URL + "/" + id + 1)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ) .andReturn();
    
    assertEquals(404, mvcResult.getResponse().getStatus());
  }
  
  private RegisterTeamRequest createTeamReqeust(String teamName, String content, ProjectCategory category) {
    return RegisterTeamRequest.builder()
      .teamName(teamName)
      .content(content)
      .category(category)
      .build();
  }
  
}
