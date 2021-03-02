package kr.joljak.invite.service;

import java.util.Random;
import kr.joljak.invite.entity.Invite;
import kr.joljak.invite.exception.AlreadyExistInviteException;
import kr.joljak.invite.exception.InvalidInviteException;
import kr.joljak.invite.exception.InviteNotFoundException;
import kr.joljak.invite.repository.InviteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InviteService {
  private final InviteRepository inviteRepository;

  @Transactional
  public void validateAndExpireInvite(String classOf, String inviteCode) {
    Invite invite = inviteRepository.findByClassOf(classOf)
      .orElseThrow(InviteNotFoundException::new);

    if (!inviteCode.equals(invite.getInviteCode())) {
      throw new InvalidInviteException();
    }

    invite.expired();
  }

  @Transactional
  public Invite issueRandomInvite(String classOf) {
    String randomInviteCode = generateRandomInviteCode();

    return issueInvite(classOf, randomInviteCode);
  }

  private String generateRandomInviteCode() {
    char[] codes = {
      'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
      'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
      'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
    };
    Random random = new Random(System.currentTimeMillis());
    StringBuilder randomInviteCode = new StringBuilder();

    for (int i = 0; i < 6; i++) {
      randomInviteCode.append(codes[random.nextInt(codes.length)]);
    }

    return randomInviteCode.toString();
  }

  @Transactional
  public Invite issueInvite(String classOf, String inviteCode) {
    checkDuplicateInvite(classOf);
    Invite invite = Invite.builder()
    .classOf(classOf)
    .inviteCode(inviteCode)
    .build();

    return inviteRepository.save(invite);
  }

  @Transactional(readOnly = true)
  public void checkDuplicateInvite(String classOf) {
    boolean isDuplicate = inviteRepository.existsByClassOf(classOf);
    if (isDuplicate) {
      throw new AlreadyExistInviteException();
    }
  }

}
