package kr.joljak.api.team.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import kr.joljak.api.team.request.TeamRequest;
import kr.joljak.api.team.response.TeamResponse;
import kr.joljak.domain.team.dto.SimpleTeam;
import kr.joljak.domain.team.entity.Team;
import kr.joljak.domain.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {

  private final TeamService teamService;

  @ApiOperation("팀 목록 게시판 생성 API")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TeamResponse create(
    @RequestPart TeamRequest teamRequest,
    @RequestPart(required = false) List<MultipartFile> images
  ) {
    SimpleTeam simpleTeam = TeamRequest.toDomainTeamRequest(teamRequest);
    Team team = teamService.addTeam(simpleTeam, images);
    return TeamResponse.of(team);
  }

}
