package es.gobcan.istac.statistical.operations.roadmap.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.TableValueEntity;

@NoRepositoryBean
public interface TableValueRepository<E extends TableValueEntity> extends JpaRepository<E, java.lang.Long> {

    public E findByCode(String code);
}
