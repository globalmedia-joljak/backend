package kr.joljak.domain.user.repository;

import kr.joljak.domain.common.repository.ExtendRepository;
import kr.joljak.domain.user.entity.Portfolio;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends ExtendRepository<Portfolio> {

}
