package kr.joljak.invite.repository;

import java.util.Optional;
import kr.joljak.common.repository.ExtendRepository;
import kr.joljak.invite.entity.Invite;
import org.springframework.stereotype.Repository;

@Repository
public interface InviteRepository extends ExtendRepository<Invite> {
  Optional<Invite> findByClassOf(String classOf);

  Boolean existsByClassOf(String classOf);
}
