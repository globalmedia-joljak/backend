package kr.joljak.domain.notice.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleNoticeRequest {

  private String classOf;
  private String content;
  private String title;

  @Builder
  public SimpleNoticeRequest(String classOf, String content, String title) {
    this.classOf = classOf;
    this.content = content;
    this.title = title;
  }

}
