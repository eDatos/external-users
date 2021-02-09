package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.FilterEntity;
import es.gobcan.istac.edatos.external.users.core.domain.UsuarioEntity;
import es.gobcan.istac.edatos.external.users.core.repository.FilterRepository;
import es.gobcan.istac.edatos.external.users.core.service.FilterService;

@Service
public class FilterServiceImpl implements FilterService {

    private final FilterRepository filterRepository;

    public FilterServiceImpl(FilterRepository filterRepository) {
        this.filterRepository = filterRepository;
    }

    @Override
    public FilterEntity create(FilterEntity filter) {
        // TODO(EDATOS-3280): Filter validation needed
        return filterRepository.saveAndFlush(filter);
    }

    @Override
    public FilterEntity update(FilterEntity filter) {
        // TODO(EDATOS-3280): Filter validation needed
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
        // TODO(EDATOS-3280)
        throw new NotImplementedException("TODO(EDATOS-3280)");
    }

    @Override
    public List<FilterEntity> find(String query, Sort sort) {
        // TODO(EDATOS-3280)
        throw new NotImplementedException("TODO(EDATOS-3280)");
    }

    @Override
    public void delete(FilterEntity filter) {
        filterRepository.delete(filter);
    }
}
