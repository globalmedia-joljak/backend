package kr.joljak.api.user.profile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import kr.joljak.api.common.CommonApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

public class GetProfilesTest extends CommonApiTest {
  @Test
  public void getProfiles_Success() throws Exception {
    MvcResult mvcResult = mockMvc.perform(
        get(PROFILE_URL + "?page=0&size=10")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();

    assertEquals(200, mvcResult.getResponse().getStatus());
  }

  @Test
  public void getProfiles_SizeAndPageUnderZero() throws Exception {
    MvcResult mvcResult = mockMvc.perform(
        get(PROFILE_URL + "?page=-1&size=-1")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();

    assertEquals(200, mvcResult.getResponse().getStatus());
  }
}
