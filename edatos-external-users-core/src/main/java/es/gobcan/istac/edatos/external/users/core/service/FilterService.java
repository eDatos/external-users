package es.gobcan.istac.edatos.external.users.core.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.domain.FilterEntity;

public interface FilterService {

    FilterEntity create(FilterEntity filter);

    FilterEntity update(FilterEntity filter);

    FilterEntity find(Long id);

    List<FilterEntity> findAll();

    List<FilterEntity> findAllByUser(ExternalUserEntity user);

    Page<FilterEntity> find(String query, Pageable pageable);

    List<FilterEntity> find(String query, Sort sort);

    void delete(FilterEntity filter);

    public Page<FilterEntity> findByExternalUser(String query, Pageable pageable);
}
