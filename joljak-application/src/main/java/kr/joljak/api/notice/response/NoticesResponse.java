package kr.joljak.api.notice.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticesResponse {

  private List<NoticeResponse> noticeResponseList;
  private Pageable page;

  @Builder
  public NoticesResponse(List<NoticeResponse> noticeResponseList, Pageable page){
    this.noticeResponseList = noticeResponseList;
    this.page = page;
  }
}
