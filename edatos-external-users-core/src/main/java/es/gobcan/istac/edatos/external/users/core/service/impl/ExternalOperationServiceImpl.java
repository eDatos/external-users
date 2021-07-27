package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalOperationEntity;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalOperationRepository;
import es.gobcan.istac.edatos.external.users.core.service.ExternalOperationService;
import es.gobcan.istac.edatos.external.users.core.service.FavoriteService;
import es.gobcan.istac.edatos.external.users.core.util.QueryUtil;

@Service
public class ExternalOperationServiceImpl implements ExternalOperationService {

    private final ExternalOperationRepository externalOperationRepository;
    private final QueryUtil queryUtil;
    private final FavoriteService favoriteService;

    public ExternalOperationServiceImpl(ExternalOperationRepository externalOperationRepository, QueryUtil queryUtil, FavoriteService favoriteService) {
        this.externalOperationRepository = externalOperationRepository;
        this.queryUtil = queryUtil;
        this.favoriteService = favoriteService;
    }

    @Override
    public Page<ExternalOperationEntity> find(String query, Pageable pageable) {
        DetachedCriteria criteria = queryUtil.queryToExternalOperationCriteria(query, pageable);
        return externalOperationRepository.findAll(criteria, pageable);
    }

    @Override
    public ExternalOperationEntity create(ExternalOperationEntity operation) {
        return externalOperationRepository.saveAndFlush(operation);
    }

    @Override
    public ExternalOperationEntity update(ExternalOperationEntity operation) {
        if (!operation.isEnabled()) {
            deleteSuscriptions(operation);
        }

        return externalOperationRepository.saveAndFlush(operation);
    }

    @Override
    public void delete(String urn) {
        externalOperationRepository.deleteByUrn(urn);
    }

    @Override
    public void delete(ExternalOperationEntity operation) {
        externalOperationRepository.delete(operation);
    }

    @Override
    @Cacheable(cacheManager = "requestScopedCacheManager", cacheNames = "externalOperationsByUrns")
    public List<ExternalOperationEntity> findByExternalCategoryUrnIn(List<String> urns) {
        return externalOperationRepository.findByExternalCategoryUrnIn(urns);
    }

    private void deleteSuscriptions(ExternalOperationEntity operation) {
        favoriteService.deleteBySuscription(operation);
    }
}
