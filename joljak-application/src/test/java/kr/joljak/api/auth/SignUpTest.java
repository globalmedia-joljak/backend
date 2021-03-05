package kr.joljak.api.auth;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.joljak.api.common.CommonApiTest;
import kr.joljak.api.auth.request.SignUpRequest;
import kr.joljak.domain.invite.entity.Invite;
import kr.joljak.domain.user.entity.UserProjectRole;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

public class SignUpTest extends CommonApiTest {

  @Test
  public void signUp_Success() throws Exception {
    // given
    Invite invite = createInvite("TEST"+nextId++, "TEST01");
    SignUpRequest signUpRequest = createSignUpRequest(invite.getClassOf(), invite.getInviteCode());
    String request = new ObjectMapper().writeValueAsString(signUpRequest);

    //when
    MvcResult mvcResult = mockMvc.perform(
      post(AUTH_URL + "/signup")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(request)
    ).andReturn();

    //then
    assertEquals(201, mvcResult.getResponse().getStatus());
  }

  @Test
  public void signUp_Fail_AlreadyClassOfExistException() throws Exception {
    // given
    Invite invite = createInvite(getUser().getClassOf(), "TEST01");
    SignUpRequest signUpRequest = createSignUpRequest(getUser().getClassOf(), "TEST01");
    String request = new ObjectMapper().writeValueAsString(signUpRequest);

    //when
    MvcResult mvcResult = mockMvc.perform(
        post(AUTH_URL + "/signup")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request)
    ).andReturn();

    //then
    assertEquals(409, mvcResult.getResponse().getStatus());
  }

  @Test
  public void signUp_Fail_InvalidInviteException() throws Exception {
    // given
    Invite invite = createInvite("TEST" + nextId++, "TEST01");
    SignUpRequest signUpRequest = createSignUpRequest(invite.getClassOf(), invite.getInviteCode() + nextId++);
    String request = new ObjectMapper().writeValueAsString(signUpRequest);

    //when
    MvcResult mvcResult = mockMvc.perform(
        post(AUTH_URL + "/signup")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request)
    ).andReturn();

    //then
    assertEquals(401, mvcResult.getResponse().getStatus());
  }

  @Test
  public void signUp_Fail_NotFoundInviteException() throws Exception {
    // given
    String notFoundClassOf = "classOf" + nextId++;
    SignUpRequest signUpRequest = createSignUpRequest(notFoundClassOf, notFoundClassOf + nextId++);
    String request = new ObjectMapper().writeValueAsString(signUpRequest);

    //when
    MvcResult mvcResult = mockMvc.perform(
        post(AUTH_URL + "/signup")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request)
    ).andReturn();

    //then
    assertEquals(404, mvcResult.getResponse().getStatus());
  }

  private SignUpRequest createSignUpRequest(String classOf, String inviteCode) {
    return new SignUpRequest(
      classOf,
      "123456789012345678901234567890123456789012345678901234",
      "testClassOf",
      "123-4567-8900",
      inviteCode,
      UserProjectRole.DEVELOPER,
      null
    );
  }
}
