package es.gobcan.istac.edatos.external.users.core.service;

import static es.gobcan.istac.edatos.external.users.util.StatisticalOperationsMocks.mockInstance;
import static es.gobcan.istac.edatos.external.users.util.StatisticalOperationsMocks.mockOperation;
import static es.gobcan.istac.edatos.external.users.util.StatisticalOperationsMocks.mockOperationForInternalPublishing;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.siemac.edatos.core.common.exception.EDatosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import es.gobcan.istac.edatos.external.users.core.domain.InstanceEntity;
import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.ProcStatusEnum;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionParameters;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionType;
import es.gobcan.istac.edatos.external.users.EdatosExternalUsersCoreTestApp;
import es.gobcan.istac.edatos.external.users.util.StatisticalOperationsMocks;
import es.gobcan.istac.edatos.external.users.util.StatisticalOperationsRoadmapBaseTest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdatosExternalUsersCoreTestApp.class)
public class InstanceServiceIntTest extends StatisticalOperationsRoadmapBaseTest {

    @Autowired
    private OperationService operationService;

    @InjectMocks
    @Resource
    private InstanceService instanceService;

    @InjectMocks
    @Resource
    private CommonListService commonListService;

    @Autowired
    private EntityManager em;

    private OperationEntity operationPublishedInternally;
    private OperationEntity operationPublishedExternally;

    @Before
    public void initTest() {
        createOfficialityTypes(em, "ESTUDIO", "OFICIAL");
        createSurveyTypes(em, "COYUNTURAL", "ESTRUCTURAL", "OTRAS");
        createInstanceTypes(em, "SERIE", "SECCION");

        operationPublishedInternally = operationService
                .createOperation(mockOperationForInternalPublishing(commonListService.findAllOfficialityTypes().get(0), commonListService.findAllSurveyTypes().get(0)));
        operationPublishedInternally = operationService.publishInternallyOperation(operationPublishedInternally.getId());

        operationPublishedExternally = operationService
                .createOperation(mockOperationForInternalPublishing(commonListService.findAllOfficialityTypes().get(0), commonListService.findAllSurveyTypes().get(0)));
        operationPublishedExternally = operationService.publishInternallyOperation(operationPublishedExternally.getId());
        operationPublishedExternally = operationService.publishExternallyOperation(operationPublishedExternally.getId());
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Transactional
    public void testFindInstanceById() {
        InstanceEntity instance = instanceService.createInstance(operationPublishedInternally.getId(), createInstance());
        assertNotNull(instance);

        InstanceEntity instanceRetrieved = instanceService.findInstanceById(instance.getId());
        assertNotNull(instanceRetrieved);

        assertTrue(instance.equals(instanceRetrieved));
    }

    @Test
    @Transactional
    public void testFindInstanceByCode() throws Exception {
        String instance_code = "instance_01";
        InstanceEntity instance = createInstance();
        instance.setCode(instance_code);

        Long operationId = operationPublishedInternally.getId();
        instanceService.createInstance(operationId, instance);

        InstanceEntity instanceRetrieved = instanceService.findInstanceByCode(instance_code);
        assertNotNull(instanceRetrieved);
        assertEquals(instance_code, instance.getCode());
    }

    @Test
    @Transactional
    public void testFindInstanceByCodeNotExists() throws Exception {
        String instance_code = "not_exists";
        expectedEDatosException(new EDatosException(ServiceExceptionType.INSTANCE_CODE_NOT_FOUND, instance_code));

        instanceService.findInstanceByCode(instance_code);
    }

    @Test
    @Transactional
    public void testFindInstanceByUrn() throws Exception {

        Long operationId = operationPublishedInternally.getId();
        String instance_urn = instanceService.createInstance(operationId, createInstance()).getUrn();

        InstanceEntity instanceRetrieved = instanceService.findInstanceByUrn(instance_urn);
        assertNotNull(instanceRetrieved);
        assertEquals(instance_urn, instanceRetrieved.getUrn());
    }

    @Test
    @Transactional
    public void testFindInstanceByUrnNotExists() throws Exception {
        String urn = "not_exists";
        expectedEDatosException(new EDatosException(ServiceExceptionType.INSTANCE_NOT_FOUND, urn));

        instanceService.findInstanceByUrn(urn);
    }

    @Test
    @Transactional
    public void testCreateInstance() {
        InstanceEntity instance = instanceService.createInstance(operationPublishedInternally.getId(), createInstance());
        assertEquals("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Instance=" + operationPublishedInternally.getCode() + "." + instance.getCode(), instance.getUrn());
        assertNotNull(instance);
    }

    @Test
    @Transactional
    public void testCreateInstanceWithGranularities() {
        InstanceEntity expected = createInstanceWithGranularities();
        InstanceEntity actual = instanceService.createInstance(operationPublishedInternally.getId(), expected);

        assertNotNull(actual);
        assertEquals("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Instance=" + operationPublishedInternally.getCode() + "." + actual.getCode(), actual.getUrn());
        assertEquals(expected.getGeographicGranularity().size(), actual.getGeographicGranularity().size());
        assertEquals(expected.getTemporalGranularity().size(), actual.getTemporalGranularity().size());
    }

    @Test
    @Transactional
    public void testCreateInstanceOperationNotPublished() {
        expectedEDatosException(new EDatosException(ServiceExceptionType.INSTANCE_INCORRECT_OPERATION_PROC_STATUS));

        OperationEntity operation = operationService.createOperation(StatisticalOperationsMocks.mockOperation());
        instanceService.createInstance(operation.getId(), createInstance());
    }

    @Test
    @Transactional
    public void testDeleteInstance() {
        InstanceEntity instance = mockInstance();
        instance.setOperation(operationPublishedInternally);

        instance = instanceService.createInstance(operationPublishedInternally.getId(), instance);

        List<InstanceEntity> instances = instanceService.findAllInstances();

        instanceService.deleteInstance(instance.getId());

        assertTrue(instanceService.findAllInstances().size() < instances.size());
    }

    @Test
    @Transactional
    public void testDeleteInstanceCheckUpdateOrder() {

        InstanceEntity instance01 = instanceService.createInstance(operationPublishedInternally.getId(), createInstance());
        InstanceEntity instance02 = instanceService.createInstance(operationPublishedInternally.getId(), createInstance());

        List<InstanceEntity> instances = instanceService.findAllInstances();

        instanceService.deleteInstance(instance01.getId());

        instance02 = instanceService.findInstanceById(instance02.getId());
        assertEquals(Integer.valueOf(0), instance02.getOrder());

        assertTrue(instanceService.findAllInstances().size() < instances.size());
    }

    @Test
    @Transactional
    public void testFindAllInstances() {
        instanceService.createInstance(operationPublishedInternally.getId(), createInstance());

        List<InstanceEntity> instances = instanceService.findAllInstances();
        assertTrue(!instances.isEmpty());
    }

    @Test
    @Transactional
    public void testFindInstanceByCondition() {
        instanceService.createInstance(operationPublishedInternally.getId(), createInstance());
        Page<InstanceEntity> operations = instanceService.find("CODE LIKE 'INSTANCE-%'", new PageRequest(0, 1));
        assertTrue(operations.hasContent());
    }

    @Test
    @Transactional
    public void testFindInstanceByConditionPaginated() {
        instanceService.createInstance(operationPublishedInternally.getId(), createInstance());
        instanceService.createInstance(operationPublishedInternally.getId(), createInstance());
        instanceService.createInstance(operationPublishedInternally.getId(), createInstance());

        Page<InstanceEntity> instances1 = instanceService.find("CODE LIKE 'INSTANCE-%'", new PageRequest(0, 2));
        Page<InstanceEntity> instances2 = instanceService.find("CODE LIKE 'INSTANCE-%'", new PageRequest(1, 2));

        assertTrue(instances1.hasContent());
        assertEquals(2, instances1.getNumberOfElements());
        assertEquals(2, instances1.getTotalPages());
        assertTrue(instances1.getTotalElements() >= 2);

        assertTrue(instances2.hasContent());
        assertEquals(2, instances2.getSize());
    }

    @Test
    @Transactional
    public void testUpdateInstance() throws Exception {
        InstanceEntity instance = instanceService.createInstance(operationPublishedInternally.getId(), createInstance());
        instance.setBasePeriod("2005-Q1");
        instanceService.updateInstance(instance);
    }

    @Test
    @Transactional
    public void testUpdateInstanceWithIncorrectBasePeriod() throws Exception {
        expectedEDatosException(new EDatosException(ServiceExceptionType.METADATA_INCORRECT, ServiceExceptionParameters.INSTANCE_BASE_PERIOD));
        InstanceEntity instance = instanceService.createInstance(operationPublishedInternally.getId(), createInstance());
        instance.setBasePeriod("2005Q1error");
        instanceService.updateInstance(instance);
    }

    @Test
    @Transactional
    public void testUpdateInstancesOrder() throws Exception {
        // Create instances
        InstanceEntity instance01 = instanceService.createInstance(operationPublishedInternally.getId(), createInstance());
        InstanceEntity instance02 = instanceService.createInstance(operationPublishedInternally.getId(), createInstance());
        InstanceEntity instance03 = instanceService.createInstance(operationPublishedInternally.getId(), createInstance());

        // Change order
        List<Long> instancesIds = new ArrayList<>();
        instancesIds.add(instance03.getId());
        instancesIds.add(instance02.getId());
        instancesIds.add(instance01.getId());

        List<InstanceEntity> orderedInstances = instanceService.updateInstancesOrder(operationPublishedInternally.getId(), instancesIds);

        // Check correct order
        assertEquals(orderedInstances.get(2).getId(), instance01.getId());
        assertEquals(orderedInstances.get(1).getId(), instance02.getId());
        assertEquals(orderedInstances.get(0).getId(), instance03.getId());
    }

    @Test
    @Transactional
    public void testUpdateInstancesOrderIncorrectParameter() throws Exception {
        expectedEDatosException(new EDatosException(ServiceExceptionType.PARAMETER_INCORRECT, ServiceExceptionParameters.INSTANCES_ID_LIST_SIZE));

        // Create instances
        instanceService.createInstance(operationPublishedInternally.getId(), createInstance());
        InstanceEntity instance02 = instanceService.createInstance(operationPublishedInternally.getId(), createInstance());
        InstanceEntity instance03 = instanceService.createInstance(operationPublishedInternally.getId(), createInstance());

        // Change order
        List<Long> instancesIds = new ArrayList<Long>();
        instancesIds.add(instance03.getId());
        instancesIds.add(instance02.getId());

        instanceService.updateInstancesOrder(operationPublishedInternally.getId(), instancesIds);
    }

    @Test
    @Transactional
    public void testPublishInternallyInstance() {
        // Service doesn't check if the associated operation is PUBLISHED_INTERNALLY because it's a requirement for create it.

        // Create a INTERNALLY_PUBLISHED Operation
        assertTrue(ProcStatusEnum.INTERNALLY_PUBLISHED.equals(operationPublishedInternally.getProcStatus()));

        // Create instance
        InstanceEntity instance = mockInstance();
        instance.setInstanceType(commonListService.findAllInstanceTypes().get(0));
        instance = instanceService.createInstance(operationPublishedInternally.getId(), instance);

        int instancesBeforePublish = instanceService.findAllInstances().size();
        int operationsBeforePublish = operationService.findAllOperations().size();

        // Publish instance
        instance = instanceService.publishInternallyInstance(instance.getId());

        // Validations
        assertNotNull(operationPublishedInternally.getInternalInventoryDate());
        assertTrue(ProcStatusEnum.INTERNALLY_PUBLISHED.equals(instance.getProcStatus()));

        // Check number of operations and instances
        int instancesAfterPublish = instanceService.findAllInstances().size();
        int operationsAfterPublish = operationService.findAllOperations().size();
        assertEquals(instancesBeforePublish, instancesAfterPublish);
        assertEquals(operationsBeforePublish, operationsAfterPublish);
    }

    @Test
    @Transactional
    public void testPublishExternallyInstance() {
        // Create a EXTERNALLY_PUBLISHED Operation
        assertTrue(ProcStatusEnum.EXTERNALLY_PUBLISHED.equals(operationPublishedExternally.getProcStatus()));

        // Create instance
        InstanceEntity instance = mockInstance();
        instance.setInstanceType(commonListService.findAllInstanceTypes().get(0));
        instance = instanceService.createInstance(operationPublishedExternally.getId(), instance);

        int instancesBefore = instanceService.findAllInstances().size();

        // Publish instance
        instance = instanceService.publishInternallyInstance(instance.getId());
        instance = instanceService.publishExternallyInstance(instance.getId());

        // Validations
        assertNotNull(operationPublishedExternally.getInternalInventoryDate());
        assertNotNull(operationPublishedExternally.getInventoryDate());
        assertTrue(ProcStatusEnum.EXTERNALLY_PUBLISHED.equals(instance.getProcStatus()));

        // Check number of instances
        int instancesAfter = instanceService.findAllInstances().size();
        assertEquals(instancesBefore, instancesAfter);

        // Check that instance can't be internally published
        try {
            instanceService.publishInternallyInstance(instance.getId());
        } catch (EDatosException e) {
            assertEquals(ServiceExceptionType.INVALID_PROC_STATUS.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }

    private InstanceEntity createInstance() {
        InstanceEntity instance = mockInstance();
        OperationEntity operation = operationService.createOperation(mockOperation());
        instance.setOperation(operation);
        return instance;
    }

    private InstanceEntity createInstanceWithGranularities() {
        InstanceEntity instance = createInstance();

        instance.addGeographicGranularity(StatisticalOperationsMocks.mockCodeExternalItem());
        instance.addGeographicGranularity(StatisticalOperationsMocks.mockCodeExternalItem());

        instance.addTemporalGranularity(StatisticalOperationsMocks.mockCodeExternalItem());
        instance.addTemporalGranularity(StatisticalOperationsMocks.mockCodeExternalItem());
        instance.addTemporalGranularity(StatisticalOperationsMocks.mockCodeExternalItem());

        return instance;
    }
}
