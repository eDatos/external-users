package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.FilterEntity;
import es.gobcan.istac.edatos.external.users.core.domain.UsuarioEntity;
import es.gobcan.istac.edatos.external.users.core.repository.FilterRepository;
import es.gobcan.istac.edatos.external.users.core.service.FilterService;
import es.gobcan.istac.edatos.external.users.core.util.QueryUtil;

@Service
public class FilterServiceImpl implements FilterService {

    private final FilterRepository filterRepository;

    private final QueryUtil queryUtil;

    public FilterServiceImpl(FilterRepository filterRepository, QueryUtil queryUtil) {
        this.filterRepository = filterRepository;
        this.queryUtil = queryUtil;
    }

    @Override
    public FilterEntity create(FilterEntity filter) {
        // TODO(EDATOS-3280): Discuss with @frodgar if filter validation is needed
        return filterRepository.saveAndFlush(filter);
    }

    @Override
    public FilterEntity update(FilterEntity filter) {
        // TODO(EDATOS-3280): Discuss with @frodgar if filter validation is needed
        return filterRepository.saveAndFlush(filter);
    }

    @Override
    public FilterEntity find(Long id) {
        return filterRepository.findOne(id);
    }

    @Override
    public List<FilterEntity> findAll() {
        return filterRepository.findAll();
    }

    @Override
    public List<FilterEntity> findAllByUser(UsuarioEntity user) {
        return filterRepository.findAllByUserOrderByCreatedDate(user);
    }

    @Override
    public Page<FilterEntity> find(String query, Pageable pageable) {
        // TODO(EDATOS-3280): Debate with @frodgar if there's a better method to process query params. Maybe
        //  Spring Data offers a simpler way that fit us?
        //  For reference: Spring Data criteria querys (https://www.baeldung.com/spring-data-criteria-queries)
        DetachedCriteria criteria = queryUtil.queryToFilterCriteria(query, pageable);
        return filterRepository.findAll(criteria, pageable);
    }

    @Override
    public List<FilterEntity> find(String query, Sort sort) {
        // TODO(EDATOS-3280): Debate with @frodgar if there's a better method to process query params. Maybe
        //  Spring Data offers a simpler way that fit us?
        //  For reference: Spring Data criteria querys (https://www.baeldung.com/spring-data-criteria-queries)
        DetachedCriteria criteria = queryUtil.queryToFilterSortCriteria(query, sort);
        return filterRepository.findAll(criteria);
    }

    @Override
    public void delete(FilterEntity filter) {
        filterRepository.delete(filter);
    }
}
