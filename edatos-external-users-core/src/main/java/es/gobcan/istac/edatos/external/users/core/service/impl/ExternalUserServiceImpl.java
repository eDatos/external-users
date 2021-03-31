package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.time.Instant;
import java.util.HashSet;

import es.gobcan.istac.edatos.external.users.core.errors.ErrorMessagesConstants;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.errors.CustomParameterizedExceptionBuilder;
import es.gobcan.istac.edatos.external.users.core.errors.ErrorConstants;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalUserRepository;
import es.gobcan.istac.edatos.external.users.core.security.SecurityUtils;
import es.gobcan.istac.edatos.external.users.core.service.ExternalUserService;
import es.gobcan.istac.edatos.external.users.core.service.validator.ExternalUserValidator;
import es.gobcan.istac.edatos.external.users.core.util.QueryUtil;

@Service
public class ExternalUserServiceImpl implements ExternalUserService {

    private final ExternalUserRepository externalUserRepository;
    private final QueryUtil queryUtil;

    public ExternalUserServiceImpl(ExternalUserRepository externalUserRepository, QueryUtil queryUtil) {
        this.externalUserRepository = externalUserRepository;
        this.queryUtil = queryUtil;
    }

    @Autowired
    private ExternalUserValidator externalUserValidator;

    @Override
    public ExternalUserEntity create(ExternalUserEntity user) {
        externalUserValidator.checkEmailEnUso(user);
        return externalUserRepository.saveAndFlush(user);
    }

    @Override
    public ExternalUserEntity update(ExternalUserEntity user) {
        return externalUserRepository.saveAndFlush(user);
    }

    @Override
    public ExternalUserEntity delete(Long id) {
        ExternalUserEntity usuario = externalUserRepository.findOneByIdAndDeletionDateIsNull(id)
                .orElseThrow(() -> new CustomParameterizedExceptionBuilder().message("Usuario no válido").code(ErrorConstants.USUARIO_NO_VALIDO).build()); // TODO(EDATOS-3278): Replace with
                                                                                                                                                           // EDatosException
        usuario.setDeletionDate(Instant.now());
        usuario.setDeletedBy(SecurityUtils.getCurrentUserLogin());
        return externalUserRepository.saveAndFlush(usuario);
    }

    @Override
    public ExternalUserEntity recover(Long id) {
        ExternalUserEntity usuario = externalUserRepository.findOneByIdAndDeletionDateIsNotNull(id)
                .orElseThrow(() -> new CustomParameterizedExceptionBuilder().message("Usuario no válido").code(ErrorConstants.ENTIDAD_NO_ENCONTRADA, id.toString()).build());
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
        return queryUtil.queryToUserExternalUserCriteria(pageable, finalQuery);
    }

    private String getFinalQuery(Boolean includeDeleted, StringBuilder queryBuilder) {
        String finalQuery = queryBuilder.toString();
        if (BooleanUtils.isTrue(includeDeleted)) {
            finalQuery = queryUtil.queryIncludingDeleted(finalQuery);
        }
        return finalQuery;
    }

    @Override
    public ExternalUserEntity getUsuarioWithAuthorities() {
        ExternalUserEntity returnValue = externalUserRepository.findOneWithRolesByEmail(SecurityUtils.getCurrentUserLogin()).orElse(new ExternalUserEntity());
        if (returnValue.getDeletionDate() != null) {
            returnValue.setRoles(new HashSet<>());
        }

        return returnValue;
    }

    @Override
    public void updateExternalUserAccountPassword(ExternalUserEntity user, String currentPassword, String newPassword) {
        if (!SecurityUtils.passwordEncoderMatches(currentPassword, user.getPassword())) {
            throw new CustomParameterizedExceptionBuilder().code(ErrorConstants.PASSWORD_NOT_MATCH).message(ErrorMessagesConstants.PASSWORD_NOT_MATCH).build();
        }
        String passwordEncoder = SecurityUtils.passwordEncoder(newPassword);
        user.setPassword(passwordEncoder);
        externalUserRepository.saveAndFlush(user);
    }

}
