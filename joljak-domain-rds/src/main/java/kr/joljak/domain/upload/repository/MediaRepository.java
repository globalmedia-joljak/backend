package kr.joljak.domain.upload.repository;

import java.util.List;
import kr.joljak.domain.common.repository.ExtendRepository;
import kr.joljak.domain.upload.entity.Media;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends ExtendRepository<Media> {
  
  void deleteByIdIn(List<Long> ids);
}