package kr.joljak.api.ideaboard.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IdeaBoardsResponse {
  
  private Page<IdeaBoardResponse> ideaBoardResponseList;
  
  @Builder
  public IdeaBoardsResponse(Page<IdeaBoardResponse> ideaBoardResponseList) {
    this.ideaBoardResponseList = ideaBoardResponseList;
  }
  
}
