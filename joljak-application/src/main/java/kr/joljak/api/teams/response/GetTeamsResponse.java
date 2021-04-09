package kr.joljak.api.teams.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetTeamsResponse {
  
  private Page<GetTeamResponse> getTeamResponsePage;
  
  @Builder
  public GetTeamsResponse(Page<GetTeamResponse> getTeamResponsePage){
    this.getTeamResponsePage = getTeamResponsePage;
  }

}
