package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.domain.FavoriteEntity;
import es.gobcan.istac.edatos.external.users.core.repository.FavoriteRepository;
import es.gobcan.istac.edatos.external.users.core.service.FavoriteService;
import es.gobcan.istac.edatos.external.users.core.util.QueryUtil;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository filterRepository;

    private final QueryUtil queryUtil;

    public FavoriteServiceImpl(FavoriteRepository filterRepository, QueryUtil queryUtil) {
        this.filterRepository = filterRepository;
        this.queryUtil = queryUtil;
    }

    @Override
    public FavoriteEntity create(FavoriteEntity filter) {
        return filterRepository.saveAndFlush(filter);
    }

    @Override
    public FavoriteEntity update(FavoriteEntity filter) {
        return filterRepository.saveAndFlush(filter);
    }

    @Override
    public FavoriteEntity find(Long id) {
        return filterRepository.findOne(id);
    }

    @Override
    public List<FavoriteEntity> findAll() {
        return filterRepository.findAll();
    }

    @Override
    public List<FavoriteEntity> findAllByUser(ExternalUserEntity user) {
        return filterRepository.findAllByExternalUserOrderByCreatedDate(user);
    }

    @Override
    public Page<FavoriteEntity> find(String query, Pageable pageable) {
        DetachedCriteria criteria = queryUtil.queryToFavoriteCriteria(query, pageable);
        return filterRepository.findAll(criteria, pageable);
    }

    @Override
    public List<FavoriteEntity> find(String query, Sort sort) {
        DetachedCriteria criteria = queryUtil.queryToFavoriteSortCriteria(query, sort);
        return filterRepository.findAll(criteria);
    }

    @Override
    public void delete(FavoriteEntity filter) {
        filterRepository.delete(filter);
    }
}
