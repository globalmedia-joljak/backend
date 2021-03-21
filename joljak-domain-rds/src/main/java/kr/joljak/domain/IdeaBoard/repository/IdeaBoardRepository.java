package kr.joljak.domain.IdeaBoard.repository;

import kr.joljak.domain.IdeaBoard.entity.IdeaBoard;
import kr.joljak.domain.common.repository.ExtendRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeaBoardRepository extends ExtendRepository<IdeaBoard> {

}
