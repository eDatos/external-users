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
import es.gobcan.istac.edatos.external.users.core.service.validator.FavoriteValidator;
import es.gobcan.istac.edatos.external.users.core.util.QueryUtil;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final FavoriteValidator favoriteValidator;
    private final QueryUtil queryUtil;

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository, FavoriteValidator favoriteValidator, QueryUtil queryUtil) {
        this.favoriteRepository = favoriteRepository;
        this.favoriteValidator = favoriteValidator;
        this.queryUtil = queryUtil;
    }

    @Override
    public FavoriteEntity create(FavoriteEntity favorite) {
        favoriteValidator.validate(favorite);
        return favoriteRepository.saveAndFlush(favorite);
    }

    @Override
    public FavoriteEntity update(FavoriteEntity favorite) {
        favoriteValidator.validate(favorite);
        return favoriteRepository.saveAndFlush(favorite);
    }

    @Override
    public FavoriteEntity find(Long id) {
        return favoriteRepository.findOne(id);
    }

    @Override
    public List<FavoriteEntity> findAll() {
        return favoriteRepository.findAll();
    }

    @Override
    public List<FavoriteEntity> findAllByUser(ExternalUserEntity user) {
        return favoriteRepository.findAllByExternalUserOrderByCreatedDate(user);
    }

    @Override
    public Page<FavoriteEntity> find(String query, Pageable pageable) {
        DetachedCriteria criteria = queryUtil.queryToFavoriteCriteria(query, pageable);
        return favoriteRepository.findAll(criteria, pageable);
    }

    @Override
    public List<FavoriteEntity> find(String query, Sort sort) {
        DetachedCriteria criteria = queryUtil.queryToFavoriteSortCriteria(query, sort);
        return favoriteRepository.findAll(criteria);
    }

    @Override
    public void delete(FavoriteEntity favorite) {
        favoriteRepository.delete(favorite);
    }
}
