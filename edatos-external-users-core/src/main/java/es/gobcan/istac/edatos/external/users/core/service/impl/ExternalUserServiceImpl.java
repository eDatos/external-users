package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.time.Instant;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.errors.CustomParameterizedExceptionBuilder;
import es.gobcan.istac.edatos.external.users.core.errors.ErrorConstants;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalUserRepository;
import es.gobcan.istac.edatos.external.users.core.security.SecurityUtils;
import es.gobcan.istac.edatos.external.users.core.service.ExternalUserService;
import es.gobcan.istac.edatos.external.users.core.util.QueryUtil;

@Service
public class ExternalUserServiceImpl implements ExternalUserService {

    private final ExternalUserRepository externalUserRepository;
    private final QueryUtil queryUtil;

    public ExternalUserServiceImpl(ExternalUserRepository externalUserRepository, QueryUtil queryUtil) {
        this.externalUserRepository = externalUserRepository;
        this.queryUtil = queryUtil;
    }

    @Override
    public ExternalUserEntity create(ExternalUserEntity user) {
        return externalUserRepository.saveAndFlush(user);
    }

    @Override
    public void update(String firstName, String surname1, String surname2, String email) {
        externalUserRepository.findOneByEmail(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
            user.setName(firstName);
            user.setSurname1(surname1);
            user.setSurname2(surname2);
            user.setEmail(email);
        });
    }

    @Override
    public ExternalUserEntity update(ExternalUserEntity user) {
        return externalUserRepository.saveAndFlush(user);
    }

    @Override
    public ExternalUserEntity delete(String email) {
        ExternalUserEntity usuario = externalUserRepository.findOneByEmailAndDeletionDateIsNull(email)
                .orElseThrow(() -> new CustomParameterizedExceptionBuilder().message("Usuario no válido").code(ErrorConstants.USUARIO_NO_VALIDO).build());
        usuario.setDeletionDate(Instant.now());
        usuario.setDeletedBy(SecurityUtils.getCurrentUserLogin());
        return externalUserRepository.saveAndFlush(usuario);
    }

    @Override
    public ExternalUserEntity recover(String email) {
        ExternalUserEntity usuario = externalUserRepository.findOneByEmailAndDeletionDateIsNotNull(email)
                .orElseThrow(() -> new CustomParameterizedExceptionBuilder().message("Usuario no válido").code(ErrorConstants.ENTIDAD_NO_ENCONTRADA, email).build());
        usuario.setDeletionDate(null);
        usuario.setDeletedBy(null);
        return externalUserRepository.saveAndFlush(usuario);
    }

    @Override
    public ExternalUserEntity find(Long id) {
        return externalUserRepository.findOne(id);
    }

    @Override
    public Page<ExternalUserEntity> find(Pageable pageable, Boolean includeDeleted, String query) {
        DetachedCriteria criteria = buildExternalUserCriteria(pageable, includeDeleted, query);
        return externalUserRepository.findAll(criteria, pageable);
    }

    private DetachedCriteria buildExternalUserCriteria(Pageable pageable, Boolean includeDeleted, String query) {
        StringBuilder queryBuilder = new StringBuilder();
        if (StringUtils.isNotBlank(query)) {
            queryBuilder.append(query);
        }
        String finalQuery = getFinalQuery(includeDeleted, queryBuilder);
        return queryUtil.queryToUserCriteria(pageable, finalQuery);
    }

    private String getFinalQuery(Boolean includeDeleted, StringBuilder queryBuilder) {
        String finalQuery = queryBuilder.toString();
        if (BooleanUtils.isTrue(includeDeleted)) {
            finalQuery = queryUtil.queryIncludingDeleted(finalQuery);
        }
        return finalQuery;
    }
}
