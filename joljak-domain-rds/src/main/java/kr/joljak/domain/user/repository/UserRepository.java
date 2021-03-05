package kr.joljak.domain.user.repository;

import java.util.Optional;
import kr.joljak.domain.common.repository.ExtendRepository;
import kr.joljak.domain.user.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ExtendRepository<User> {
  Optional<User> findById(Long userId);

  Boolean existsByClassOf(String classOf);

  Optional<User> findByClassOf(String classOf);
}
