package kr.joljak.domain.work.repository;

import kr.joljak.domain.common.repository.ExtendRepository;
import kr.joljak.domain.work.entity.ProjectCategory;
import kr.joljak.domain.work.entity.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository extends ExtendRepository<Work> {
  
  Page<Work> findAllByProjectCategoryAndYearContaining(ProjectCategory category, String year, Pageable pageable);
  
  Page<Work> findAllByProjectCategory(ProjectCategory category, Pageable pageable);
  
  Page<Work> findAllByYear(String year, Pageable pageable);

}
