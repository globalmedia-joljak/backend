package kr.joljak.api.teams.controller;

import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import kr.joljak.api.teams.request.RegisterTeamRequest;
import kr.joljak.api.teams.response.RegisterTeamResponse;
import kr.joljak.domain.team.dto.SimpleTeam;
import kr.joljak.domain.team.entity.Team;
import kr.joljak.domain.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
  public RegisterTeamResponse create(
    @Valid RegisterTeamRequest registerTeamRequest
  ) {
    SimpleTeam simpleTeam = RegisterTeamRequest.toRegisterTeam(registerTeamRequest);
    Team team = teamService.addTeam(simpleTeam);
    return RegisterTeamResponse.of(team);
  }

}
