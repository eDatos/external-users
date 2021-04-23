package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.hibernate.criterion.DetachedCriteria;
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
import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;
import es.gobcan.istac.edatos.external.users.core.repository.FavoriteRepository;
import es.gobcan.istac.edatos.external.users.core.repository.OperationRepository;
import es.gobcan.istac.edatos.external.users.core.service.FavoriteService;
import es.gobcan.istac.edatos.external.users.core.service.validator.FavoriteValidator;
import es.gobcan.istac.edatos.external.users.core.util.QueryUtil;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final OperationRepository operationRepository;
    private final FavoriteValidator favoriteValidator;
    private final QueryUtil queryUtil;

    private final Logger log = LoggerFactory.getLogger(FavoriteServiceImpl.class);

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository, OperationRepository operationRepository, FavoriteValidator favoriteValidator, QueryUtil queryUtil) {
        this.favoriteRepository = favoriteRepository;
        this.operationRepository = operationRepository;
        this.favoriteValidator = favoriteValidator;
        this.queryUtil = queryUtil;
    }

    @Override
    public FavoriteEntity create(FavoriteEntity favorite) {
        favoriteValidator.validate(favorite);
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
        for (OperationEntity operation : operationRepository.getByCategory(parent)) {
            if (favoriteRepository.findByExternalUser(externalUser).stream().map(FavoriteEntity::getOperation).noneMatch(op -> Objects.equals(op, operation))) {
                favoriteRepository.save(newFavorite(externalUser, operation));
            }
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

    private FavoriteEntity newFavorite(ExternalUserEntity externalUser, OperationEntity operation) {
        FavoriteEntity favorite = new FavoriteEntity();
        favorite.setExternalUser(externalUser);
        favorite.setOperation(operation);
        return favorite;
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
    public void delete(FavoriteEntity favorite) {
        if (favorite.getCategory() != null) {
            delete(favorite.getExternalUser(), favorite.getCategory());
        } else {
            // operation favorites doesn't have children
            favoriteRepository.delete(favorite);
        }
    }

    @Override
    @Cacheable(cacheManager = "requestScopedCacheManager", cacheNames = "categories")
    public Map<Long, Long> getCategorySubscribers() {
        return favoriteRepository.getCategorySubscribers().stream().collect(Collectors.toMap(ImmutablePair::getLeft, ImmutablePair::getRight));
    }

    @Override
    @Cacheable(cacheManager = "requestScopedCacheManager", cacheNames = "operations")
    public Map<Long, Long> getOperationSubscribers() {
        return favoriteRepository.getOperationSubscribers().stream().collect(Collectors.toMap(ImmutablePair::getLeft, ImmutablePair::getRight));
    }

    public void delete(ExternalUserEntity externalUser, CategoryEntity parent) {
        favoriteRepository.deleteByExternalUserAndCategory(externalUser, parent);
        for (OperationEntity operation : operationRepository.getByCategory(parent)) {
            favoriteRepository.deleteByExternalUserAndOperation(externalUser, operation);
        }
        for (CategoryEntity child : parent.getChildren()) {
            delete(externalUser, child);
        }
    }
}
