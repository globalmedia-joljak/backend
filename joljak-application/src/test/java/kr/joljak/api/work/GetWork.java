package kr.joljak.api.work;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;
import kr.joljak.api.common.CommonApiTest;
import kr.joljak.api.work.request.WorkRequest;
import kr.joljak.domain.Ideaboard.entity.IdeaBoard;
import kr.joljak.domain.Ideaboard.entity.ProjectStatus;
import kr.joljak.domain.work.dto.SimpleWork;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

public class GetWork extends CommonApiTest {

  private WorkRequest workRequest;
  private SimpleWork simpleWork;

  @Before
  public void initGetIdeaBoard() {
    List<String> teamMember = new ArrayList<>();

    teamMember.add("test");
    workRequest = createWorkRequest(
      "test workName", "test teamName",
      teamMember, "test content");

    simpleWork = WorkRequest.toDomainTeamRequest(simpleWork);
  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void getWork_Success() {
    MvcResult mvcResult = mockMvc.perform(
      get(WORK_URL + "/" + id)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();

    assertEquals(200, mvcResult.getResponse().getStatus());
  }

}
