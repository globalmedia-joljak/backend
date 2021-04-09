package kr.joljak.api.work.response;


import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorksResponse {

  private List<WorkResponse> workResponseList;
  private Pageable page;

  @Builder
  public WorksResponse(List<WorkResponse> workResponseList, Pageable page){
    this.workResponseList = workResponseList;
    this.page = page;
  }
}
