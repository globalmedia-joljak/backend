package kr.joljak.domain.invite.repository;

import java.util.Optional;
import kr.joljak.domain.common.repository.ExtendRepository;
import kr.joljak.domain.invite.entity.Invite;
import org.springframework.stereotype.Repository;

@Repository
public interface InviteRepository extends ExtendRepository<Invite> {
  Optional<Invite> findByClassOf(String classOf);

  Boolean existsByClassOf(String classOf);
}
