package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import es.gobcan.istac.edatos.external.users.core.domain.*;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalUserRepository;
import es.gobcan.istac.edatos.external.users.core.security.SecurityUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.CategoryEntity;
import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.domain.FavoriteEntity;
import es.gobcan.istac.edatos.external.users.core.repository.FavoriteRepository;
import es.gobcan.istac.edatos.external.users.core.service.FavoriteService;
import es.gobcan.istac.edatos.external.users.core.util.QueryUtil;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ExternalUserRepository externalUserRepository;
    private final QueryUtil queryUtil;

    private final Logger log = LoggerFactory.getLogger(FavoriteServiceImpl.class);

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository, ExternalUserRepository externalUserRepository, QueryUtil queryUtil) {
        this.favoriteRepository = favoriteRepository;
        this.externalUserRepository = externalUserRepository;
        this.queryUtil = queryUtil;
    }

    @Override
    public FavoriteEntity create(FavoriteEntity favorite) {
        if (favorite.getCategory() != null) {
            create(favorite.getExternalUser(), favorite.getCategory());
            return favoriteRepository.findByExternalUserAndCategory(favorite.getExternalUser(), favorite.getCategory()).get();
        } else {
            return favoriteRepository.saveAndFlush(favorite);
        }
    }

    private void create(ExternalUserEntity externalUser, CategoryEntity parent) {
        if (favoriteRepository.findByExternalUser(externalUser).stream().map(FavoriteEntity::getCategory).noneMatch(cat -> Objects.equals(cat, parent))) {
            favoriteRepository.save(newFavorite(externalUser, parent));
        }
        for (CategoryEntity child : parent.getChildren()) {
            create(externalUser, child);
        }
    }

    private FavoriteEntity newFavorite(ExternalUserEntity externalUser, CategoryEntity category) {
        FavoriteEntity favorite = new FavoriteEntity();
        favorite.setExternalUser(externalUser);
        favorite.setCategory(category);
        return favorite;
    }

    @Override
    public FavoriteEntity update(FavoriteEntity favorite) {
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
        return favoriteRepository.findByExternalUser(user);
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
    public List<FavoriteEntity> findByExternalUser() {
        ExternalUserEntity externalUser = externalUserRepository.findOneByEmail(SecurityUtils.getCurrentUserLogin()).get();
        List<FavoriteEntity> list = favoriteRepository.findByExternalUser(externalUser);
        return list;
    }

    @Override
    public void delete(FavoriteEntity favorite) {
        delete(favorite.getExternalUser(), favorite.getCategory());
    }

    @Override
    @Cacheable(cacheManager = "requestScopedCacheManager", cacheNames = "categories")
    public Map<Long, Long> getCategorySubscribers() {
        return favoriteRepository.getCategorySubscribers().stream().collect(Collectors.toMap(ImmutablePair::getLeft, ImmutablePair::getRight));
    }

    public void delete(ExternalUserEntity externalUser, CategoryEntity parent) {
        favoriteRepository.deleteByExternalUserAndCategory(externalUser, parent);
        for (CategoryEntity child : parent.getChildren()) {
            delete(externalUser, child);
        }
    }
}
