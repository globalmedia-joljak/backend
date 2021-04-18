package kr.joljak.domain.user.repository;

import java.util.Optional;
import kr.joljak.domain.common.repository.ExtendRepository;
import kr.joljak.domain.user.entity.Profile;
import kr.joljak.domain.user.entity.UserProjectRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends ExtendRepository<Profile> {
  
  boolean existsByUserClassOf(String classOf);
  
  Optional<Profile> findByUserClassOf(String classOf);
  
  Page<Profile> findByUserNameOrUserClassOfOrUserMainProjectRole(
    String name, String classOf, UserProjectRole mainProjectRole,
    Pageable pageable);
}
