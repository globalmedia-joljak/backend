package kr.joljak.api.notice.request;


import javax.validation.constraints.NotNull;
import kr.joljak.domain.notice.entity.Notice;
import kr.joljak.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoticeRequest {

  @NotNull
  private String classOf;
  @NotNull
  private String title;
  @NotNull
  private String content;

  public Notice to(User user) {

    return Notice.builder()
        .title(title)
        .classOf(classOf)
        .content(content)
        .user(user)
        .build();
  }

}
