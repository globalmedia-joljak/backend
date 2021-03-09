package kr.joljak.api.notice.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticesResponse {

  private List<NoticeResponse> noticeResponseList;
  private int page;

  @Builder
  public NoticesResponse(List<NoticeResponse> noticeResponseList, int page){
    this.noticeResponseList = noticeResponseList;
    this.page = page;
  }
}
