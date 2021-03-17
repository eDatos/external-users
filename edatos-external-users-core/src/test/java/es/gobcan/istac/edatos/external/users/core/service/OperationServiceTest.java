package es.gobcan.istac.edatos.external.users.core.service;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.siemac.edatos.common.test.EDatosBaseTest;
import org.siemac.edatos.core.common.exception.EDatosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import es.gobcan.istac.edatos.external.users.EdatosExternalUsersCoreTestApp;
import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionParameters;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionType;

import static es.gobcan.istac.edatos.external.users.util.StatisticalOperationsMocks.mockOperation;
import static es.gobcan.istac.edatos.external.users.util.StatisticalOperationsMocks.mockOperationWithDescription;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdatosExternalUsersCoreTestApp.class)
public class OperationServiceTest extends EDatosBaseTest {

    @InjectMocks
    @Resource
    private OperationService operationService;

    @Autowired
    private EntityManager em;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Transactional
    public void testFindOperationById() {
        OperationEntity operation = operationService.createOperation(mockOperation());
        assertNotNull(operation);

        OperationEntity operationRetrieved = operationService.findOperationById(operation.getId());
        assertNotNull(operationRetrieved);

        assertTrue(operation.equals(operationRetrieved));
    }

    @Test
    @Transactional
    public void testFindOperationByCode() throws Exception {
        String operation_code = "ope_01";

        OperationEntity operation = mockOperation();
        operation.setCode(operation_code);

        operationService.createOperation(operation);

        OperationEntity operationRetrieved = operationService.findOperationByCode(operation_code);
        assertNotNull(operationRetrieved);
        assertEquals(operation_code, operation.getCode());
    }

    @Test
    @Transactional
    public void testFindOperationByCodeNotExists() throws Exception {
        String operation_code = "not_exists";
        expectedEDatosException(new EDatosException(ServiceExceptionType.OPERATION_CODE_NOT_FOUND, operation_code));

        operationService.findOperationByCode(operation_code);
    }

    @Test
    @Transactional
    public void testFindOperationByUrn() throws Exception {
        String operation_urn = operationService.createOperation(mockOperation()).getUrn();

        OperationEntity operationRetrieved = operationService.findOperationByUrn(operation_urn);
        assertNotNull(operationRetrieved);
        assertEquals(operation_urn, operationRetrieved.getUrn());
    }

    @Test
    @Transactional
    public void testFindOperationByUrnNotExists() throws Exception {
        String urn = "not_exists";
        expectedEDatosException(new EDatosException(ServiceExceptionType.OPERATION_NOT_FOUND, urn));

        operationService.findOperationByUrn(urn);
    }

    @Test
    @Transactional
    public void testCreateOperation() {
        OperationEntity operation = operationService.createOperation(mockOperation());
        assertNotNull(operation);
        assertEquals("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Operation=" + operation.getCode(), operation.getUrn());
    }

    //@Test
    //@Transactional
    //public void testCreateOperationWithSpecificLegalActs() {
    //    OperationEntity expected = mockOperation();
    //    expected.setSpecificLegalActs(StatisticalOperationsMocks.mockInternationalString());
    //
    //    OperationEntity operation = operationService.createOperation(expected);
    //
    //    assertNotNull(operation);
    //    assertEquals("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Operation=" + operation.getCode(), operation.getUrn());
    //    StatisticalOperationsAsserts.assertEqualsInternationalString(expected.getSpecificLegalActs(), operation.getSpecificLegalActs());
    //}
    //
    //@Test
    //@Transactional
    //public void testCreateOperationWithSpecificDataSharing() {
    //    OperationEntity expected = mockOperation();
    //    expected.setSpecificDataSharing(StatisticalOperationsMocks.mockInternationalString());
    //
    //    OperationEntity operation = operationService.createOperation(expected);
    //
    //    assertNotNull(operation);
    //    assertEquals("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Operation=" + operation.getCode(), operation.getUrn());
    //    StatisticalOperationsAsserts.assertEqualsInternationalString(expected.getSpecificDataSharing(), operation.getSpecificDataSharing());
    //}

    @Test
    @Transactional
    public void testCreateOperationWithInvalidSemanticIdentifier() {
        expectedEDatosException(new EDatosException(ServiceExceptionType.METADATA_INCORRECT, ServiceExceptionParameters.OPERATION_CODE));

        OperationEntity operation = mockOperation();
        operation.setCode("OPERATION@IPC");
        operation = operationService.createOperation(operation);
    }

    @Test
    @Transactional
    public void testCreateOperationWithoutDescription() {
        OperationEntity operation = mockOperation();
        operation.setDescription(null);

        operation = operationService.createOperation(operation);

        assertNotNull(operation);
        assertEquals(null, operation.getDescription());
    }

    //@Test
    //@Transactional
    //public void testCreateOperationWithInvalidAccess() {
    //    expectedEDatosException(new EDatosException(ServiceExceptionType.METADATA_INVALID_URL, ServiceExceptionParameters.OPERATION_RELEASE_CALENDAR_ACCESS));
    //
    //    OperationEntity operation = mockOperation();
    //    operation.setReleaseCalendar(true);
    //    operation.setReleaseCalendarAccess("invalidUrl");
    //
    //    operation = operationService.createOperation(operation);
    //}

    //@Test
    //@Transactional
    //public void testSaveOperationWithFamilies() {
    //    OperationEntity operation = operationService.createOperation(createOperationWithFamilies());
    //    assertNotNull(operation);
    //}

    @Test
    @Transactional
    public void testDeleteOperation() {
        OperationEntity operation = operationService.createOperation(mockOperation());
        assertNotNull(operation);
        List<OperationEntity> operations = operationService.findAllOperations();

        operationService.deleteOperation(operation.getId());

        assertTrue(operationService.findAllOperations().size() < operations.size());
    }

    //@Test
    //@Transactional
    //public void testDeleteOperationWithType() {
    //    int operationsNumberBefore = operationService.findAllOperations().size();
    //    int operationsTypeBefore = commonListService.findAllSurveyTypes().size();
    //
    //    OperationEntity operation = operationService.createOperation(createOperationWithType());
    //    operationService.deleteOperation(operation.getId());
    //    assertTrue(operationService.findAllOperations().size() <= operationsNumberBefore);
    //    assertTrue(commonListService.findAllSurveyTypes().size() == operationsTypeBefore);
    //}

    @Test
    @Transactional
    public void testFindAllOperations() {
        operationService.createOperation(mockOperation());

        List<OperationEntity> operations = operationService.findAllOperations();
        assertTrue(!operations.isEmpty());
    }

    @Test
    @Transactional
    public void testFindOperationByCondition() {
        operationService.createOperation(mockOperation());
        Page<OperationEntity> operations = operationService.find("CODE LIKE 'OPE-%'", new PageRequest(0, 1));
        assertTrue(operations.hasContent());
    }

    @Test
    @Transactional
    public void testFindOperationByConditionPaginated() {
        operationService.createOperation(mockOperation());
        operationService.createOperation(mockOperation());
        operationService.createOperation(mockOperation());

        Page<OperationEntity> operationsList1 = operationService.find("CODE LIKE 'OPE-%'", new PageRequest(0, 2));
        Page<OperationEntity> operationsList2 = operationService.find("CODE LIKE 'OPE-%'", new PageRequest(1, 2));

        assertTrue(operationsList1.hasContent());
        assertEquals(2, operationsList1.getNumberOfElements());
        assertEquals(2, operationsList1.getTotalPages());
        assertTrue(operationsList1.getTotalElements() >= 2);

        assertTrue(operationsList2.hasContent());
        assertEquals(2, operationsList2.getSize());
    }

    @Test
    @Transactional
    public void testUpdateOperation() throws Exception {
        OperationEntity operation = mockOperationWithDescription();
        operation = operationService.createOperation(operation);
        assertNotNull(operation.getDescription());

        operation.setDescription(null);
        operation = operationService.updateOperation(operation);
        assertNotNull(operation);
        assertEquals(null, operation.getDescription());
    }

    //@Test
    //@Transactional
    //public void testUpdateOperationWithIncorrectReleaseCalendarAccess() throws Exception {
    //    expectedEDatosException(new EDatosException(ServiceExceptionType.METADATA_INVALID_URL, ServiceExceptionParameters.OPERATION_RELEASE_CALENDAR_ACCESS));
    //
    //    OperationEntity operation = mockOperation();
    //    operation = operationService.createOperation(operation);
    //    assertNull(operation.getReleaseCalendarAccess());
    //
    //    operation.setReleaseCalendar(true);
    //    operation.setReleaseCalendarAccess("incorrectUrl");
    //
    //    operationService.updateOperation(operation);
    //}

    //@Test
    //@Transactional
    //public void testUpdateOperationWithReleaseCalendarAccessAndWithoutReleaseCalendar() throws Exception {
    //    expectedEDatosException(new EDatosException(ServiceExceptionType.METADATA_UNEXPECTED, ServiceExceptionParameters.OPERATION_RELEASE_CALENDAR_ACCESS));
    //
    //    OperationEntity operation = mockOperation();
    //    operation = operationService.createOperation(operation);
    //    assertNull(operation.getReleaseCalendarAccess());
    //
    //    operation.setReleaseCalendar(Boolean.FALSE);
    //    operation.setReleaseCalendarAccess("http://tutu.com");
    //
    //    operationService.updateOperation(operation);
    //}

    //@Test
    //@Transactional
    //public void testUpdateOperationWithReleaseCalendarAndWithoutReleaseCalendarAccess() throws Exception {
    //    expectedEDatosException(new EDatosException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.OPERATION_RELEASE_CALENDAR_ACCESS));
    //
    //    OperationEntity operation = mockOperation();
    //    operation = operationService.createOperation(operation);
    //    assertNull(operation.getReleaseCalendarAccess());
    //
    //    operation.setReleaseCalendar(Boolean.TRUE);
    //    operation.setReleaseCalendarAccess(null);
    //
    //    operationService.updateOperation(operation);
    //}

    @Test
    @Transactional
    public void testUpdateOperationWithoutOperation() throws Exception {
        OperationEntity operation = mockOperation();
        operation.setDescription(null);
        operation = operationService.createOperation(operation);
        assertEquals(null, operation.getDescription());

        operation = operationService.updateOperation(operation);
        assertNotNull(operation);
        assertEquals(null, operation.getDescription());
    }

    //@Test
    //@Transactional
    //public void testPublishInternallyOperation() {
    //    // Create and Publish operation
    //    OperationEntity operation = operationService
    //            .createOperation(mockOperationForInternalPublishing(commonListService.findAllOfficialityTypes().get(0), commonListService.findAllSurveyTypes().get(0)));
    //    int operationsBefore = operationService.findAllOperations().size();
    //
    //    operation = operationService.publishInternallyOperation(operation.getId());
    //
    //    // Validations
    //    assertNotNull(operation.getInternalInventoryDate());
    //    assertTrue(ProcStatusEnum.INTERNALLY_PUBLISHED.equals(operation.getProcStatus()));
    //
    //    int operationsAfter = operationService.findAllOperations().size();
    //    assertEquals(operationsBefore, operationsAfter);
    //}

    //@Test
    //@Transactional
    //public void testPublishExternallyOperation() {
    //
    //    // Create and Publish operation
    //    OperationEntity operation = operationService
    //            .createOperation(mockOperationForInternalPublishing(commonListService.findAllOfficialityTypes().get(0), commonListService.findAllSurveyTypes().get(0)));
    //    int operationsBefore = operationService.findAllOperations().size();
    //
    //    operation = operationService.publishInternallyOperation(operation.getId());
    //    operation = operationService.publishExternallyOperation(operation.getId());
    //
    //    // Validations
    //    assertNotNull(operation.getInternalInventoryDate());
    //    assertNotNull(operation.getInventoryDate());
    //    assertTrue(ProcStatusEnum.EXTERNALLY_PUBLISHED.equals(operation.getProcStatus()));
    //
    //    // Check number of operations
    //    int operationsAfter = operationService.findAllOperations().size();
    //    assertEquals(operationsBefore, operationsAfter);
    //
    //    // Check that operation can't be internally published
    //    try {
    //        operationService.publishInternallyOperation(operation.getId());
    //    } catch (EDatosException e) {
    //        assertEquals(ServiceExceptionType.INVALID_PROC_STATUS.getCode(), e.getExceptionItems().get(0).getCode());
    //    }
    //}

    //private OperationEntity createOperationWithFamilies() {
    //    OperationEntity operation = mockOperation();
    //
    //    // FAMILIES
    //    for (int i = 0; i < 4; i++) {
    //        FamilyEntity family = familyService.create(mockFamily());
    //        operation.getFamilies().add(family);
    //    }
    //
    //    return operation;
    //}

    private OperationEntity createOperationWithType() {
        OperationEntity operation = mockOperation();

        // TYPE
        //operation.setSurveyType(commonListService.findAllSurveyTypes().get(0));

        return operation;
    }
}
