package kr.joljak.api.auth;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import kr.joljak.api.common.CommonApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

public class ReissueAccessTokenTest extends CommonApiTest {

  @Test
  public void reissueAccessToken_Success() throws Exception {
    // given
    String refreshToken = getUserRefreshToken();

    //when
    MvcResult mvcResult = mockMvc.perform(
      post(AUTH_URL + "/reissue/accesstoken")
        .header("refreshToken", refreshToken)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();

    //then
    assertEquals(200, mvcResult.getResponse().getStatus());
  }

  @Test
  public void reissueAccessToken_Fail_IsNotRefreshToken() throws Exception {
    // given
    String refreshToken = getUserAccessToken();

    //when
    MvcResult mvcResult = mockMvc.perform(
        post(AUTH_URL + "/reissue/accesstoken")
            .header("refreshToken", refreshToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();

    //then
    assertEquals(401, mvcResult.getResponse().getStatus());
  }

  @Test
  public void reissueAccessToken_Fail_InvalidToekn() throws Exception {
    // given
    String refreshToken = getUserAccessToken() + nextId++;

    //when
    MvcResult mvcResult = mockMvc.perform(
        post(AUTH_URL + "/reissue/accesstoken")
            .header("refreshToken", refreshToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();

    //then
    assertEquals(401, mvcResult.getResponse().getStatus());
  }
}
