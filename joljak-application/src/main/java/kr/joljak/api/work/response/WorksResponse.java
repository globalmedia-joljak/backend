package kr.joljak.api.work.response;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorksResponse {
  
  private Page<WorkResponse> workResponseList;
  
  @Builder
  public WorksResponse(Page<WorkResponse> workResponseList) {
    this.workResponseList = workResponseList;
  }
}
