package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;
import es.gobcan.istac.edatos.external.users.core.repository.OperationRepository;
import es.gobcan.istac.edatos.external.users.core.service.OperationService;
import es.gobcan.istac.edatos.external.users.core.util.QueryUtil;

@Service
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final QueryUtil queryUtil;

    public OperationServiceImpl(OperationRepository operationRepository, QueryUtil queryUtil) {
        this.operationRepository = operationRepository;
        this.queryUtil = queryUtil;
    }

    @Override
    public OperationEntity findOperationById(Long id) {
        return operationRepository.findOne(id);
    }

    @Override
    public List<OperationEntity> findAllOperations() {
        return operationRepository.findAll();
    }

    @Override
    public Page<OperationEntity> find(String query, Pageable pageable) {
        DetachedCriteria criteria = queryUtil.queryToOperationCriteria(pageable, query);
        return find(criteria, pageable);
    }

    @Override
    public Page<OperationEntity> find(DetachedCriteria criteria, Pageable pageable) {
        return operationRepository.findAll(criteria, pageable);
    }

    @Override
    public OperationEntity createOperation(OperationEntity operation) {
        return operationRepository.save(operation);
    }

    @Override
    public OperationEntity updateOperation(OperationEntity operation) {
        return operationRepository.save(operation);
    }

    @Override
    public void deleteOperation(Long operationId) {
        OperationEntity operation = findOperationById(operationId);
        operationRepository.delete(operation);
    }

}
