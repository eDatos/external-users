package es.gobcan.istac.edatos.external.users.external.api.v1_0.service;

import static org.siemac.edatos.rest.search.util.PaginationUtils.getLimitAsInt;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.core.Response.Status;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.siemac.edatos.rest.exception.RestCommonServiceExceptionType;
import org.siemac.edatos.rest.exception.RestException;
import org.siemac.edatos.rest.exception.util.RestExceptionUtils;
import org.siemac.metamac.rest.api.constants.RestApiConstants;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.CollMethods;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Costs;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Families;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Family;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Instance;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.InstanceTypes;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Instances;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.OfficialityTypes;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Operation;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Operations;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.StatisticalOperationSources;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.StatisticalOperationTypes;
import org.siemac.metamac.statistical_operations.rest.common.StatisticalOperationsRestConstants;
import org.siemac.metamac.statistical_operations.rest.external.v1_0.service.StatisticalOperationsV1_0;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.CollMethodEntity;
import es.gobcan.istac.edatos.external.users.core.domain.CostEntity;
import es.gobcan.istac.edatos.external.users.core.domain.FamilyEntity;
import es.gobcan.istac.edatos.external.users.core.domain.InstanceEntity;
import es.gobcan.istac.edatos.external.users.core.domain.InstanceTypeEntity;
import es.gobcan.istac.edatos.external.users.core.domain.OfficialityTypeEntity;
import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;
import es.gobcan.istac.edatos.external.users.core.domain.SurveySourceEntity;
import es.gobcan.istac.edatos.external.users.core.domain.SurveyTypeEntity;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.ProcStatusEnum;
import es.gobcan.istac.edatos.external.users.core.service.CommonListService;
import es.gobcan.istac.edatos.external.users.core.service.FamilyService;
import es.gobcan.istac.edatos.external.users.core.service.InstanceService;
import es.gobcan.istac.edatos.external.users.core.service.OperationService;
import es.gobcan.istac.edatos.external.users.core.util.QueryUtil;
import es.gobcan.istac.edatos.external.users.external.api.exception.RestServiceExceptionType;
import es.gobcan.istac.edatos.external.users.external.api.v1_0.mapper.Do2RestExternalMapperV10;
import es.gobcan.istac.edatos.external.users.external.api.v1_0.mapper.RestPagination2PageableMapper;

@Transactional
@Service("statisticalOperationsRestExternalFacadeV10")
public class StatisticalOperationsRestExternalFacadeV10Impl implements StatisticalOperationsV1_0 {

    @Autowired
    private FamilyService familyService;

    @Autowired
    private OperationService operationService;

    @Autowired
    private InstanceService instanceService;

    @Autowired
    private CommonListService commonListService;

    @Autowired
    private Do2RestExternalMapperV10 do2RestExternalMapper;

    @Autowired
    private QueryUtil queryUtil;

    @Autowired
    private RestPagination2PageableMapper restPagination2PageableMapper;

    private final Logger logger = LoggerFactory.getLogger(StatisticalOperationsRestExternalFacadeV10Impl.class);

    @Override
    public Operation retrieveOperationById(String id) {
        try {
            // Retrieve
            OperationEntity operationEntity = retrieveOperationEntityPublishedExternally(id);

            // Transform
            return do2RestExternalMapper.toOperation(operationEntity);
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public Operations findOperations(String query, String orderBy, String limit, String offset) {
        try {
            int limitAsInt = getLimitAsInt(limit);
            Pageable pageable = restPagination2PageableMapper.getOperationCriteriaMapper().toPageable(orderBy, limit, offset);

            DetachedCriteria criteria = queryUtil.queryToOperationCriteria(pageable, query);
            criteria.add(Restrictions.eq(OperationEntity.Properties.PROC_STATUS, ProcStatusEnum.EXTERNALLY_PUBLISHED));

            Page<OperationEntity> entities = operationService.find(criteria, pageable);
            return do2RestExternalMapper.toOperations(entities, query, orderBy, limitAsInt);
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public Instances findInstances(String operationId, String query, String orderBy, String limit, String offset) {

        try {
            int limitAsInt = getLimitAsInt(limit);
            Pageable pageable = restPagination2PageableMapper.getInstanceCriteriaMapper().toPageable(orderBy, limit, offset);

            DetachedCriteria criteria = queryUtil.queryToInstanceCriteria(pageable, query);
            criteria.add(Restrictions.eq(OperationEntity.Properties.PROC_STATUS, ProcStatusEnum.EXTERNALLY_PUBLISHED));

            // Find for operation
            OperationEntity operationEntity = null;
            if (!StatisticalOperationsRestConstants.WILDCARD_ALL.equals(operationId)) {
                operationEntity = retrieveOperationEntityPublishedExternally(operationId);
                criteria.createAlias(InstanceEntity.Properties.OPERATION, "o").add(Restrictions.eq("o." + OperationEntity.Properties.CODE, operationEntity.getCode()));
            }

            Page<InstanceEntity> entities = instanceService.find(criteria, pageable);
            return do2RestExternalMapper.toInstances(operationEntity, entities, query, orderBy, limitAsInt);
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public Families retrieveFamiliesByOperation(String id) {
        try {
            // Retrieve operation to check exists and it is published
            retrieveOperationEntityPublishedExternally(id);

            String query = null;
            String orderBy = null;
            Integer limitAsInt = RestApiConstants.MAXIMUM_LIMIT;

            Pageable pageable = restPagination2PageableMapper.getFamilyCriteriaMapper().toPageable(orderBy, limitAsInt.toString(), RestApiConstants.DEFAULT_OFFSET.toString());

            DetachedCriteria criteria = queryUtil.queryToFamilyCriteria(pageable, null);
            criteria.add(Restrictions.eq(FamilyEntity.Properties.PROC_STATUS, ProcStatusEnum.EXTERNALLY_PUBLISHED));
            criteria.createAlias(FamilyEntity.Properties.OPERATIONS, "o").add(Restrictions.eq("o." + OperationEntity.Properties.CODE, id));

            Page<FamilyEntity> entities = familyService.find(criteria, pageable);
            return do2RestExternalMapper.toFamilies(entities, query, orderBy, limitAsInt);
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public Family retrieveFamilyById(String id) {
        try {
            // Retrieve
            FamilyEntity familyEntity = retrieveFamilyEntityPublishedExternally(id);

            // Transform
            return do2RestExternalMapper.toFamily(familyEntity);

        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public Families findFamilies(String query, String orderBy, String limit, String offset) {
        try {
            int limitAsInt = getLimitAsInt(limit);
            Pageable pageable = restPagination2PageableMapper.getFamilyCriteriaMapper().toPageable(orderBy, limit, offset);

            DetachedCriteria criteria = queryUtil.queryToFamilyCriteria(pageable, query);
            criteria.add(Restrictions.eq(FamilyEntity.Properties.PROC_STATUS, ProcStatusEnum.EXTERNALLY_PUBLISHED));

            Page<FamilyEntity> entities = familyService.find(criteria, pageable);
            return do2RestExternalMapper.toFamilies(entities, query, orderBy, limitAsInt);
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    public Operations findOperationsByFamily(String id, String query, String orderBy, String limit, String offset) {
        try {
            // Validate family exists and it is published
            FamilyEntity family = retrieveFamilyEntityPublishedExternally(id);

            int limitAsInt = getLimitAsInt(limit);
            Pageable pageable = restPagination2PageableMapper.getOperationCriteriaMapper().toPageable(orderBy, limit, offset);

            DetachedCriteria criteria = queryUtil.queryToOperationCriteria(pageable, query);
            criteria.add(Restrictions.eq(OperationEntity.Properties.PROC_STATUS, ProcStatusEnum.EXTERNALLY_PUBLISHED));
            criteria.createAlias(OperationEntity.Properties.FAMILIES, "f").add(Restrictions.eq("f." + FamilyEntity.Properties.CODE, family.getCode()));

            Page<OperationEntity> entities = operationService.find(criteria, pageable);
            return do2RestExternalMapper.toOperations(entities, query, orderBy, limitAsInt);
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public Instance retrieveInstanceById(String operationId, String id) {
        try {
            InstanceEntity instanceEntity = instanceService.findInstanceByCode(id);
            if (instanceEntity == null || !ProcStatusEnum.EXTERNALLY_PUBLISHED.equals(instanceEntity.getProcStatus())) {
                // Instance not found
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.INSTANCE_NOT_FOUND, id);
                throw new RestException(exception, Status.NOT_FOUND);
            }

            // Transform and return
            return do2RestExternalMapper.toInstance(instanceEntity);

        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public StatisticalOperationTypes retrieveStatisticalOperationTypes() {
        try {
            // Retrieve all
            List<SurveyTypeEntity> entitiesResult = commonListService.findAllSurveyTypes();

            // Transform
            return do2RestExternalMapper.toStatisticalOperationTypes(entitiesResult);
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public OfficialityTypes retrieveOfficialityTypes() {
        try {
            // Retrieve all
            List<OfficialityTypeEntity> entitiesResult = commonListService.findAllOfficialityTypes();

            // Transform
            return do2RestExternalMapper.toOfficialityTypes(entitiesResult);
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public InstanceTypes retrieveInstanceTypes() {
        try {
            // Retrieve all
            List<InstanceTypeEntity> entitiesResult = commonListService.findAllInstanceTypes();

            // Transform
            return do2RestExternalMapper.toInstanceTypes(entitiesResult);
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public StatisticalOperationSources retrieveStatisticalOperationSources() {
        try {
            // Retrieve all
            List<SurveySourceEntity> entitiesResult = commonListService.findAllSurveySources();

            // Transform
            return do2RestExternalMapper.toStatisticalOperationSources(entitiesResult);
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public CollMethods retrieveCollMethods() {
        try {
            // Retrieve all
            List<CollMethodEntity> entitiesResult = commonListService.findAllCollMethods();

            // Transform
            return do2RestExternalMapper.toCollMethods(entitiesResult);
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public Costs retrieveCosts() {
        try {
            // Retrieve all
            List<CostEntity> entitiesResult = commonListService.findAllCosts();

            // Transform
            return do2RestExternalMapper.toCosts(entitiesResult);
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    /**
     * Retrieve operation by code (id in Api) and check it is published externally
     */
    private OperationEntity retrieveOperationEntityPublishedExternally(String id) {
        OperationEntity operationEntity = operationService.findOperationByCode(id);
        if (operationEntity == null || !ProcStatusEnum.EXTERNALLY_PUBLISHED.equals(operationEntity.getProcStatus())) {
            // Operation not found
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.OPERATION_NOT_FOUND, id);
            throw new RestException(exception, Status.NOT_FOUND);

        }
        return operationEntity;
    }

    /**
     * Retrieve family by code (id in Api) and check it is published externally
     */
    private FamilyEntity retrieveFamilyEntityPublishedExternally(String id) {
        FamilyEntity familyEntity = familyService.findFamilyByCode(id);

        if (familyEntity == null || !ProcStatusEnum.EXTERNALLY_PUBLISHED.equals(familyEntity.getProcStatus())) {
            // Family not found or not published externally
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.FAMILY_NOT_FOUND, id);
            throw new RestException(exception, Status.NOT_FOUND);

        }
        return familyEntity;
    }

    /**
     * Throws response error, logging exception
     */
    private RestException manageException(Exception e) {
        logger.error("Error", e);
        if (e instanceof RestException) {
            return (RestException) e;
        } else {
            // do not show information details about exception to user
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestCommonServiceExceptionType.UNKNOWN);
            return new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }
}
