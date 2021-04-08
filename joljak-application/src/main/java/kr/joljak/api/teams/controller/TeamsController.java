package kr.joljak.api.teams.controller;

import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import kr.joljak.api.teams.request.RegisterTeamRequest;
import kr.joljak.api.teams.request.UpdateTeamRequest;
import kr.joljak.api.teams.response.GetTeamResponse;
import kr.joljak.api.teams.response.GetTeamsResponse;
import kr.joljak.domain.team.dto.SimpleTeam;
import kr.joljak.domain.team.dto.UpdateTeam;
import kr.joljak.domain.team.entity.Team;
import kr.joljak.domain.team.service.TeamService;
import kr.joljak.domain.util.FetchPages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/teams")
public class TeamsController {

  private final TeamService teamService;

  @ApiOperation("팀 게시판 생성 API")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public GetTeamResponse create(
    @Valid RegisterTeamRequest registerTeamRequest
  ) {
    SimpleTeam simpleTeam = RegisterTeamRequest.toRegisterTeam(registerTeamRequest);
    Team team = teamService.addTeam(simpleTeam);
    return GetTeamResponse.of(team);
  }
  
  @ApiOperation("팀 게시판 리스트 조회 API")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public GetTeamsResponse getTeams(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size
  ){
    Page<GetTeamResponse> teamResponsePage = teamService.getTeams(FetchPages.of(page, size))
      .map(GetTeamResponse::of);
    
    return GetTeamsResponse.builder()
      .getTeamResponsePage(teamResponsePage)
      .build();
  }
  
  @ApiOperation("팀 게시판 상세 조회 API")
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public GetTeamResponse getTeam(@PathVariable Long id){
    Team team = teamService.getTeam(id);
    return GetTeamResponse.of(team);
  }
  
  @ApiOperation("팀 게시판 업데이트 API")
  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public GetTeamResponse updateTeam(
    @PathVariable Long id,
    UpdateTeamRequest updateTeamRequest
  ){
    UpdateTeam updateTeam = UpdateTeamRequest.toUpdateTeam(updateTeamRequest);
    Team team = teamService.updateTeam(id, updateTeam);
    
    return GetTeamResponse.of(team);
  }
  
}
