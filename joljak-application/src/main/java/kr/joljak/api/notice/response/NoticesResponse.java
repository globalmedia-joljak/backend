package kr.joljak.api.notice.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticesResponse {
  
  private Page<NoticeResponse> noticeResponseList;
  
  
  @Builder
  public NoticesResponse(Page<NoticeResponse> noticeResponseList) {
    this.noticeResponseList = noticeResponseList;
  }
}
