package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalOperationEntity;
import es.gobcan.istac.edatos.external.users.core.service.OperationService;
import es.gobcan.istac.edatos.external.users.core.util.QueryUtil;

@Service
public class OperationServiceImpl implements OperationService {

    //private final OperationRepository operationRepository;
    //private final QueryUtil queryUtil;
    //
    //public OperationServiceImpl(OperationRepository operationRepository, QueryUtil queryUtil) {
    //    this.operationRepository = operationRepository;
    //    this.queryUtil = queryUtil;
    //}
    //
    //@Override
    //public ExternalOperationEntity findOperationById(Long id) {
    //    return operationRepository.findOne(id);
    //}
    //
    //@Override
    //public List<ExternalOperationEntity> findAllOperations() {
    //    return operationRepository.findAll();
    //}
    //
    //@Override
    //public Page<ExternalOperationEntity> find(String query, Pageable pageable) {
    //    DetachedCriteria criteria = queryUtil.queryToOperationCriteria(pageable, query);
    //    return find(criteria, pageable);
    //}
    //
    //@Override
    //public Page<ExternalOperationEntity> find(DetachedCriteria criteria, Pageable pageable) {
    //    return operationRepository.findAll(criteria, pageable);
    //}
    //
    //@Override
    //public ExternalOperationEntity createOperation(ExternalOperationEntity operation) {
    //    return operationRepository.save(operation);
    //}
    //
    //@Override
    //public ExternalOperationEntity updateOperation(ExternalOperationEntity operation) {
    //    return operationRepository.save(operation);
    //}
    //
    //@Override
    //public void deleteOperation(Long operationId) {
    //    ExternalOperationEntity operation = findOperationById(operationId);
    //    operationRepository.delete(operation);
    //}

}
