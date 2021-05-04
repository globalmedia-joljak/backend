package kr.joljak.domain.ideaboard.repository;

import kr.joljak.domain.ideaboard.entity.IdeaBoard;
import kr.joljak.domain.common.repository.ExtendRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeaBoardRepository extends ExtendRepository<IdeaBoard> {

}
