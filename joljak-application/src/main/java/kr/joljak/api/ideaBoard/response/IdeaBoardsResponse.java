package kr.joljak.api.ideaBoard.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IdeaBoardsResponse {

  private List<IdeaBoardResponse> ideaBoardResponseList;
  private Pageable page;

  @Builder
  public IdeaBoardsResponse(List<IdeaBoardResponse> ideaBoardResponseList, Pageable page){
    this.ideaBoardResponseList = ideaBoardResponseList;
    this.page = page;
  }

}
