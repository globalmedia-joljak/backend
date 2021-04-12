package kr.joljak.api.work;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;
import kr.joljak.api.common.CommonApiTest;
import kr.joljak.api.work.request.RegisterWorkRequest;
import kr.joljak.domain.work.dto.SimpleWork;
import kr.joljak.domain.work.entity.ProjectCategory;
import kr.joljak.domain.work.entity.Work;
import kr.joljak.domain.work.service.WorkService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

public class GetWork extends CommonApiTest {

  @Autowired
  private WorkService workService;

  private RegisterWorkRequest registerWorkRequest;
  private SimpleWork simpleWork;
  private Long id;

  @Before
  public void initGetIdeaBoard() {
    List<String> teamMember = new ArrayList<>();

    teamMember.add("test");
    registerWorkRequest = createWorkRequest(
      "test workName", "test teamName", 2020, ProjectCategory.WEB_APP,
      teamMember, "test content");

    simpleWork = RegisterWorkRequest.toDomainWorkRequest(registerWorkRequest);
    Work work = workService.addWork(simpleWork, null);
    id = work.getId();
  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void getWork_Success() throws Exception {

    MvcResult mvcResult = mockMvc.perform(
      get(WORK_URL + "/" + id)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();

    assertEquals(200, mvcResult.getResponse().getStatus());
  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "User")
  public void getWork_Fail_WorkNotFoundException() throws Exception {

    MvcResult mvcResult = mockMvc.perform(
      get(WORK_URL + "/" + id + 1)
        .contentType(MediaType.APPLICATION_JSON_VALUE)

    ).andReturn();

    assertEquals(404, mvcResult.getResponse().getStatus());
  }

}
