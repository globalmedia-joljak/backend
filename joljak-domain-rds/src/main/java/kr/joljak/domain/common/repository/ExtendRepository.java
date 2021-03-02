package kr.joljak.domain.common.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface ExtendRepository<T> extends JpaRepository<T, Long> {
    @Transactional
    @Modifying
    @Query("delete from #{#entityName} entity where entity.id in :id")
    void deleteById(@Param("id") Long id);

    Optional<T> findById(Long id, Class<T> type);

    List<T> findAllBy(Class<T> type);

    List<T> findAllByIdIn(Collection<Long> ids, Class<T> type);
}
