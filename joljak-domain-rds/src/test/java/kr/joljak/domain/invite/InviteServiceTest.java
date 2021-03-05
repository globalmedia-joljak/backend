package kr.joljak.domain.invite;

import kr.joljak.domain.common.CommonDomainTest;
import kr.joljak.domain.invite.entity.Invite;
import kr.joljak.domain.invite.exception.AlreadyExistInviteException;
import kr.joljak.domain.invite.service.InviteService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class InviteServiceTest extends CommonDomainTest {
  @Autowired
  private InviteService inviteService;

  @Test
  public void issueRandomInvite_Success() {
    // given
    String classOf = getUser().getClassOf() + nextId++;

    // when
    Invite invite = inviteService.issueRandomInvite(classOf);

    // then
    Assertions.assertThat(invite).isNotNull();
  }

  @Test
  public void issueInvite_Success() {
    // given
    String inviteCode = "TESTIV";
    String classOf = getUser().getClassOf() + nextId++;

    // when
    Invite invite = inviteService.issueInvite(classOf, inviteCode);

    // then
    Assertions.assertThat(invite.getInviteCode()).isEqualTo(inviteCode);
  }

  @Test(expected = AlreadyExistInviteException.class)
  public void issueInvite_Fail_AlreadyExistInviteException() {
    // given
    String inviteCode = "TESTIV";
    Invite invite = inviteService.issueInvite(getUser().getClassOf(), inviteCode);

    // when
    inviteService.issueInvite(getUser().getClassOf(), inviteCode);
  }
}
