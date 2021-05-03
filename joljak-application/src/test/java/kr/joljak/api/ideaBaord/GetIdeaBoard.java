package kr.joljak.api.ideaBaord;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import kr.joljak.api.common.CommonApiTest;
import kr.joljak.api.ideaboard.request.IdeaBoardRequest;
import kr.joljak.domain.ideaboard.dto.SimpleIdeaBoard;
import kr.joljak.domain.ideaboard.entity.IdeaBoard;
import kr.joljak.domain.ideaboard.entity.ProjectStatus;
import kr.joljak.domain.ideaboard.service.IdeaBoardService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.NestedServletException;

public class GetIdeaBoard extends CommonApiTest {

  @Autowired
  private IdeaBoardService ideaBoardService;

  private IdeaBoardRequest ideaBoardRequest;
  private SimpleIdeaBoard simpleIdeaBoard;
  private Long id;

  @Before
  public void initGetIdeaBoard() {
    ideaBoardRequest = createIdeaBoardRequest("test", "test", ProjectStatus.ONGOING);

    simpleIdeaBoard = ideaBoardRequest
      .toDomainIdeaBoardRequest(ideaBoardRequest);
    IdeaBoard ideaBoard = ideaBoardService.addIdeaBoard(simpleIdeaBoard);

    id = ideaBoard.getId();
  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void getIdeaBoard_Success() throws Exception {

    // when
    MvcResult mvcResult = mockMvc.perform(
      get(IDEABOARD_URL + "/" + id)
        .contentType(MediaType.APPLICATION_JSON_VALUE)

    ).andReturn();

    // then
    assertEquals(200, mvcResult.getResponse().getStatus());
  }

  @Test(expected = NestedServletException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void getIdeaBoard_Fail_IdeaBoardNotFoundException() throws Exception {

    // when
    MvcResult mvcResult = mockMvc.perform(
      get(IDEABOARD_URL + "/" + id + 1)
        .contentType(MediaType.APPLICATION_JSON_VALUE)

    ).andReturn();

  }
}