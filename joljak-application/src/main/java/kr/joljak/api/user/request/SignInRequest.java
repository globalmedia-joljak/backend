package kr.joljak.api.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignInRequest {
  private String classOf;
  private String password;
}
