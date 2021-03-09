package kr.joljak.domain.notice.repository;

import kr.joljak.domain.common.repository.ExtendRepository;
import kr.joljak.domain.notice.entity.Notice;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends ExtendRepository<Notice> {

}
