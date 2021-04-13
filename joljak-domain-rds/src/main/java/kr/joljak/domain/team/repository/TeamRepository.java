package kr.joljak.domain.team.repository;

import kr.joljak.domain.common.repository.ExtendRepository;
import kr.joljak.domain.team.entity.Team;
import kr.joljak.domain.work.entity.ProjectCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends ExtendRepository<Team> {
  
  Page<Team> findAllByProjectCategory(ProjectCategory projectCategory, Pageable pageable);
}
