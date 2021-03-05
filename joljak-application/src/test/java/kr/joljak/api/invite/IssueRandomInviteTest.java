package kr.joljak.api.invite;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.joljak.api.common.CommonApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

public class IssueRandomInviteTest extends CommonApiTest {

  @Test
  public void issueRandomInvite_Success() throws Exception {
    // given
    String classOf = "testClassOf" + nextId++;
    String request = new ObjectMapper().writeValueAsString(classOf);

    //when
    MvcResult mvcResult = mockMvc.perform(
      post(INVITE_URL + "/issue/random")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(request)
        .header("Authorization", getAdminAccessToken())
    ).andReturn();

    //then
    assertEquals(201, mvcResult.getResponse().getStatus());
  }

  @Test
  public void issueRandomInvite_Fail_AlreadyInviteExistException() throws Exception {
    // given
    String classOf = "testClassOf" + nextId++;
    String request = new ObjectMapper().writeValueAsString(classOf);

    MvcResult mvcResult = mockMvc.perform(
        post(INVITE_URL + "/issue/random")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request)
            .header("Authorization", getAdminAccessToken())
    ).andReturn();

    // when
    mvcResult = mockMvc.perform(
        post(INVITE_URL + "/issue/random")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request)
            .header("Authorization", getAdminAccessToken())
    ).andReturn();

    //then
    assertEquals(409, mvcResult.getResponse().getStatus());
  }
}
