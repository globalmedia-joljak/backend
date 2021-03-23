package kr.joljak.domain.user.repository;

import java.util.Optional;
import kr.joljak.domain.common.repository.ExtendRepository;
import kr.joljak.domain.user.entity.Profile;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends ExtendRepository<Profile> {
  boolean existsByUserClassOf(String classOf);

  Optional<Profile> findByUserClassOf(String classOf);
}
