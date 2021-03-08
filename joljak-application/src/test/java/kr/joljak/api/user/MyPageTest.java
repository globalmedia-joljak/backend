package kr.joljak.api.user;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import kr.joljak.api.common.CommonApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

public class MyPageTest extends CommonApiTest {

  @Test
  @WithMockUser(username = "testUser1", roles = "USER")
  public void myPage_Success() throws Exception {
    //given, when
    MvcResult mvcResult = mockMvc.perform(
      get(USER_URL + "/" + TEST_USER_CLASS_OF)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();

    //then
    System.out.println(mvcResult.getResponse().getErrorMessage());
    assertEquals(200, mvcResult.getResponse().getStatus());
  }
}
