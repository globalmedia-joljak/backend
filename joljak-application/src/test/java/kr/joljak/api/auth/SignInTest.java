package kr.joljak.api.auth;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.joljak.api.auth.request.SignInRequest;
import kr.joljak.api.common.CommonApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

public class SignInTest extends CommonApiTest {

  @Test
  public void signIn_Success() throws Exception {
    // given
    SignInRequest signInRequest = crateSignInRequest(getUser().getClassOf(), getOriginalPassword());
    String request = new ObjectMapper().writeValueAsString(signInRequest);

    //when
    MvcResult mvcResult = mockMvc.perform(
      post(AUTH_URL + "/signin")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(request)
    ).andReturn();

    //then
    assertEquals(200, mvcResult.getResponse().getStatus());
  }

  @Test
  public void signIn_Fail_InvalidPasswordException() throws Exception {
    // given
    SignInRequest signInRequest = crateSignInRequest(getUser().getClassOf(), getOriginalPassword() + "error");
    String request = new ObjectMapper().writeValueAsString(signInRequest);

    //when
    MvcResult mvcResult = mockMvc.perform(
        post(AUTH_URL + "/signin")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request)
    ).andReturn();

    //then
    assertEquals(401, mvcResult.getResponse().getStatus());
  }

  @Test
  public void signIn_Fail_UserNotFoundException() throws Exception {
    // given
    SignInRequest signInRequest = crateSignInRequest(getUser().getClassOf() + nextId++, getOriginalPassword());
    String request = new ObjectMapper().writeValueAsString(signInRequest);

    //when
    MvcResult mvcResult = mockMvc.perform(
        post(AUTH_URL + "/signin")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request)
    ).andReturn();

    //then
    assertEquals(404, mvcResult.getResponse().getStatus());
  }

  private SignInRequest crateSignInRequest(String classOf, String password) {
    return new SignInRequest(classOf, password);
  }
}
