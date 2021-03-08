package kr.joljak.api.notice.response;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeResponse {

  private String classOf;
  private String title;
  private String content;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;

  @Builder
  public NoticeResponse(String classOf, String title, String content, LocalDateTime createdDate,
      LocalDateTime modifiedDate) {
    this.classOf = classOf;
    this.title = title;
    this.content = content;
    this.createdDate = createdDate;
    this.modifiedDate = modifiedDate;
  }

}
