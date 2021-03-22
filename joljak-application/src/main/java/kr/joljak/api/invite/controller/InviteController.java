package kr.joljak.api.invite.controller;

import io.swagger.annotations.ApiOperation;
import kr.joljak.domain.invite.entity.Invite;
import kr.joljak.domain.invite.service.InviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/invites")
public class InviteController {
  private final InviteService inviteService;

  @ApiOperation("랜덤 인증코드 발급 API")
  @PostMapping("/issue/random")
  @ResponseStatus(HttpStatus.CREATED)
  public Invite issueRandomInvite(@RequestBody String classOf) {
    return inviteService.issueRandomInvite(classOf);
  }
}
