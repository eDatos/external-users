package es.gobcan.istac.statistical.operations.roadmap.core.service;

import static es.gobcan.istac.statistical.operations.roadmap.util.StatisticalOperationsMocks.mockFamily;
import static es.gobcan.istac.statistical.operations.roadmap.util.StatisticalOperationsMocks.mockOperation;
import static es.gobcan.istac.statistical.operations.roadmap.util.StatisticalOperationsMocks.mockOperationForInternalPublishing;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
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

import es.gobcan.istac.statistical.operations.roadmap.StatisticalOperationsRoadmapCoreTestApp;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.FamilyEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.InternationalStringEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.LocalisedStringEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.enumeration.ProcStatusEnum;
import es.gobcan.istac.statistical.operations.roadmap.core.errors.ServiceExceptionType;
import es.gobcan.istac.statistical.operations.roadmap.util.StatisticalOperationsRoadmapBaseTest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StatisticalOperationsRoadmapCoreTestApp.class)
public class FamilyServiceIntTest extends StatisticalOperationsRoadmapBaseTest {

    @InjectMocks
    @Resource
    private FamilyService familyService;

    @InjectMocks
    @Resource
    private OperationService operationService;

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
    public void testFindFamilyById() {
        FamilyEntity family = familyService.create(mockFamily());
        assertNotNull(family);

        FamilyEntity familyRetrieved = familyService.findFamilyById(family.getId());
        assertNotNull(familyRetrieved);

        assertTrue(family.getId().equals(familyRetrieved.getId()));
        assertTrue(family.getCode().equals(familyRetrieved.getCode()));
    }

    @Test
    @Transactional
    public void testFindFamilyByCode() throws Exception {
        String family_code = "family_01";

        FamilyEntity family = mockFamily();
        family.setCode(family_code);

        familyService.create(family);

        FamilyEntity familyRetrieved = familyService.findFamilyByCode(family_code);
        assertNotNull(familyRetrieved);
        assertEquals(family_code, family.getCode());
    }

    @Test
    @Transactional
    public void testFindFamilyByCodeNotExists() throws Exception {
        String familyCode = "not_exists";
        expectedEDatosException(new EDatosException(ServiceExceptionType.FAMILY_CODE_NOT_FOUND, familyCode));

        familyService.findFamilyByCode(familyCode);
    }

    @Test
    @Transactional
    public void testFindFamilyByUrn() throws Exception {
        String familyUrn = familyService.create(mockFamily()).getUrn();

        FamilyEntity familyRetrieved = familyService.findFamilyByUrn(familyUrn);
        assertNotNull(familyRetrieved);
        assertEquals(familyUrn, familyRetrieved.getUrn());
    }

    @Test
    @Transactional
    public void testFindFamilyByUrnNotExists() throws Exception {
        String urn = "not_exists";
        expectedEDatosException(new EDatosException(ServiceExceptionType.FAMILY_NOT_FOUND, urn));

        familyService.findFamilyByUrn(urn);
    }

    @Test
    @Transactional
    public void testSaveFamilyWithOperations() {
        FamilyEntity family = familyService.create(createFamilyWithOperations());
        assertNotNull(family);
    }

    @Test
    @Transactional
    public void testDeleteFamily() {
        FamilyEntity family = familyService.create(mockFamily());

        List<FamilyEntity> families = familyService.findAllFamilies();

        familyService.deleteFamily(family.getId());

        assertTrue(familyService.findAllFamilies().size() < families.size());
    }

    @Test
    @Transactional
    public void testFindAllFamilies() {
        FamilyEntity family = familyService.create(mockFamily());
        assertNotNull(family);

        List<FamilyEntity> families = familyService.findAllFamilies();
        assertTrue(!families.isEmpty());
    }

    @Test
    @Transactional
    public void testFindFamilyByCondition() {
        familyService.create(mockFamily());
        Page<FamilyEntity> families = familyService.find("CODE LIKE 'FAMILY-%'", new PageRequest(0, 1));
        assertTrue(families.hasContent());
    }

    // TODO EDATOS-3130
    // @Test
    // @Transactional
    // public void testFindFamilyByConditionInventoryDate() {
    //
    // // Insert data
    // FamilyEntity family01 = createFamily();
    // family01.setInventoryDate(new DateTime(2012, 2, 1, 12, 0, 0, 0));
    // familyService.create(family01);
    //
    // FamilyEntity family02 = createFamily();
    // family02.setInventoryDate(new DateTime(2012, 2, 5, 12, 0, 0, 0));
    // familyService.create(family02);
    //
    // FamilyEntity family03 = createFamily();
    // family03.setInventoryDate(new DateTime(2012, 1, 31, 12, 0, 0, 0));
    // familyService.create(family03);
    //
    // // Find from 01/02/2012 to 28/02/2012 --> family01 and family02
    // {
    // List<ConditionalCriteria> conditions = criteriaFor(Family.class)
    // .withProperty(new LeafProperty<Family>(FamilyProperties.inventoryDate().getName(), CoreCommonConstants.CRITERIA_DATETIME_COLUMN_DATETIME, true, Family.class))
    // .greaterThanOrEqual(CoreCommonUtil.transformDateTimeToDate(new DateTime(2012, 2, 1, 12, 0, 0, 0)))
    // .withProperty(new LeafProperty<Family>(FamilyProperties.inventoryDate().getName(), CoreCommonConstants.CRITERIA_DATETIME_COLUMN_DATETIME, true, Family.class))
    // .lessThanOrEqual(CoreCommonUtil.transformDateTimeToDate(new DateTime(2012, 2, 28, 12, 0, 0, 0))).distinctRoot().build();
    //
    // List<FamilyEntity> familiesList = statisticalOperationsBaseService.findFamilyByCondition(conditions);
    // assertEquals(2, familiesList.size());
    // boolean family1Retrieved = false;
    // boolean family2Retrieved = false;
    // for (Iterator<FamilyEntity> iterator = familiesList.iterator(); iterator.hasNext();) {
    // FamilyEntity family = iterator.next();
    // if (family01.getCode().equals(family.getCode())) {
    // family1Retrieved = true;
    // } else if (family02.getCode().equals(family.getCode())) {
    // family2Retrieved = true;
    // }
    // }
    // assertTrue(family1Retrieved);
    // assertTrue(family2Retrieved);
    // }
    //
    // // Find from 01/01/2012 to 28/02/2012 --> familyDto01, familyDto02 and familyDto03
    // {
    // List<ConditionalCriteria> conditions = criteriaFor(Family.class)
    // .withProperty(new LeafProperty<Family>(FamilyProperties.inventoryDate().getName(), CoreCommonConstants.CRITERIA_DATETIME_COLUMN_DATETIME, true, Family.class))
    // .greaterThanOrEqual(CoreCommonUtil.transformDateTimeToDate(new DateTime(2012, 1, 1, 12, 0, 0, 0)))
    // .withProperty(new LeafProperty<Family>(FamilyProperties.inventoryDate().getName(), CoreCommonConstants.CRITERIA_DATETIME_COLUMN_DATETIME, true, Family.class))
    // .lessThanOrEqual(CoreCommonUtil.transformDateTimeToDate(new DateTime(2012, 2, 28, 12, 0, 0, 0))).distinctRoot().build();
    //
    // List<FamilyEntity> familiesList = statisticalOperationsBaseService.findFamilyByCondition(conditions);
    // assertEquals(3, familiesList.size());
    // boolean family1Retrieved = false;
    // boolean family2Retrieved = false;
    // boolean family3Retrieved = false;
    // for (Iterator<FamilyEntity> iterator = familiesList.iterator(); iterator.hasNext();) {
    // FamilyEntity family = iterator.next();
    // if (family01.getCode().equals(family.getCode())) {
    // family1Retrieved = true;
    // } else if (family02.getCode().equals(family.getCode())) {
    // family2Retrieved = true;
    // } else if (family03.getCode().equals(family.getCode())) {
    // family3Retrieved = true;
    // }
    // }
    // assertTrue(family1Retrieved);
    // assertTrue(family2Retrieved);
    // assertTrue(family3Retrieved);
    // }
    //
    // // Find from 01/01/2012 to 03/02/2012 --> familyDto01 and familyDto03
    // {
    // List<ConditionalCriteria> conditions = criteriaFor(Family.class)
    // .withProperty(new LeafProperty<Family>(FamilyProperties.inventoryDate().getName(), CoreCommonConstants.CRITERIA_DATETIME_COLUMN_DATETIME, true, Family.class))
    // .greaterThanOrEqual(CoreCommonUtil.transformDateTimeToDate(new DateTime(2012, 1, 1, 12, 0, 0, 0)))
    // .withProperty(new LeafProperty<Family>(FamilyProperties.inventoryDate().getName(), CoreCommonConstants.CRITERIA_DATETIME_COLUMN_DATETIME, true, Family.class))
    // .lessThanOrEqual(CoreCommonUtil.transformDateTimeToDate(new DateTime(2012, 2, 3, 12, 0, 0, 0))).distinctRoot().build();
    //
    // List<Family> familiesList = statisticalOperationsBaseService.findFamilyByCondition(conditions);
    // assertEquals(2, familiesList.size());
    // boolean family1Retrieved = false;
    // boolean family3Retrieved = false;
    // for (Iterator<Family> iterator = familiesList.iterator(); iterator.hasNext();) {
    // Family family = iterator.next();
    // if (family01.getCode().equals(family.getCode())) {
    // family1Retrieved = true;
    // } else if (family03.getCode().equals(family.getCode())) {
    // family3Retrieved = true;
    // }
    // }
    // assertTrue(family1Retrieved);
    // assertTrue(family3Retrieved);
    // }
    //
    // // Find from 01/02/2012 --> familyDto01
    // {
    // List<ConditionalCriteria> conditions = criteriaFor(Family.class)
    // .withProperty(new LeafProperty<Family>(FamilyProperties.inventoryDate().getName(), CoreCommonConstants.CRITERIA_DATETIME_COLUMN_DATETIME, true, Family.class))
    // .eq(CoreCommonUtil.transformDateTimeToDate(new DateTime(2012, 2, 1, 12, 0, 0, 0))).distinctRoot().build();
    //
    // List<Family> familiesList = statisticalOperationsBaseService.findFamilyByCondition(conditions);
    // assertEquals(1, familiesList.size());
    // boolean family1Retrieved = false;
    // for (Iterator<Family> iterator = familiesList.iterator(); iterator.hasNext();) {
    // Family family = iterator.next();
    // if (family01.getCode().equals(family.getCode())) {
    // family1Retrieved = true;
    // }
    // }
    // assertTrue(family1Retrieved);
    // }
    // }

    @Test
    @Transactional
    public void testFindFamilyByConditionPaginated() {
        familyService.create(mockFamily());
        familyService.create(mockFamily());
        familyService.create(mockFamily());

        Page<FamilyEntity> families1 = familyService.find(StringUtils.EMPTY, new PageRequest(2, 1));
        Page<FamilyEntity> families2 = familyService.find(StringUtils.EMPTY, new PageRequest(2, 2));

        assertTrue(families1.getTotalElements() != 0);
        assertEquals(1, families1.getNumberOfElements());
        assertEquals(2, families1.getNumber());
        assertTrue(families1.getTotalPages() >= 2);

        assertTrue(families2.getTotalElements() != 0);
        assertEquals(0, families2.getNumberOfElements());
    }

    @Test
    @Transactional
    public void testCreateFamily() throws Exception {
        FamilyEntity family = familyService.create(mockFamily());
        assertNotNull(family);
        assertEquals("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Family=" + family.getCode(), family.getUrn());
    }

    @Test
    @Transactional
    public void testUpdateFamily() throws Exception {
        FamilyEntity family = familyService.create(mockFamily());
        assertNotNull(family);

        // TITLE
        InternationalStringEntity title = new InternationalStringEntity();
        LocalisedStringEntity title_es = new LocalisedStringEntity();
        title_es.setLabel("Título MOD en español de familia");
        title_es.setLocale("es");
        LocalisedStringEntity title_en = new LocalisedStringEntity();
        title_en.setLabel("Título MOD en inglés de familia");
        title_en.setLocale("en");
        title.addText(title_es);
        title.addText(title_en);
        family.setTitle(title);

        family = familyService.updateFamily(family);
        assertNotNull(family);
    }

    @Test
    @Transactional
    public void testPublishInternallyFamilyError() {

        FamilyEntity family = familyService.create(mockFamily());
        int familiesBefore = familyService.findAllFamilies().size();

        OperationEntity operation = operationService.createOperation(mockOperation());
        familyService.addOperationFamilyAssociation(family.getId(), operation.getId());

        try {
            family = familyService.publishInternallyFamily(family.getId());
        } catch (EDatosException e) {
            assertEquals(ServiceExceptionType.FAMILY_WITHOUT_PUBLISHED_INTERNALLY_OPERATIONS.getCode(), e.getExceptionItems().get(0).getCode());
            assertEquals(1, e.getExceptionItems().size());
        }

        int familiesAfter = familyService.findAllFamilies().size();
        assertTrue(familiesBefore == familiesAfter);
    }

    @Test
    @Transactional
    public void testPublishInternallyFamily() {

        assertNotNull(operationPublishedInternally.getInternalInventoryDate());
        assertEquals(ProcStatusEnum.INTERNALLY_PUBLISHED, operationPublishedInternally.getProcStatus());

        // Create family
        FamilyEntity family = familyService.create(mockFamily());
        int familiesBefore = familyService.findAllFamilies().size();

        // Associate
        familyService.addOperationFamilyAssociation(family.getId(), operationPublishedInternally.getId());

        // Reload family
        family = familyService.findFamilyById(family.getId());

        // Publish family
        family = familyService.publishInternallyFamily(family.getId());

        // Validations
        assertNotNull(family);
        assertNotNull(family.getInternalInventoryDate());
        assertTrue(ProcStatusEnum.INTERNALLY_PUBLISHED.equals(family.getProcStatus()));
        int familiesAfter = familyService.findAllFamilies().size();
        assertTrue(familiesBefore == familiesAfter);
    }

    @Test
    @Transactional
    public void testPublishExternallyFamily() {
        assertNotNull(operationPublishedExternally.getInternalInventoryDate());
        assertNotNull(operationPublishedExternally.getInventoryDate());
        assertEquals(ProcStatusEnum.EXTERNALLY_PUBLISHED, operationPublishedExternally.getProcStatus());

        // Create family
        FamilyEntity family = familyService.create(mockFamily());

        int familiesBefore = familyService.findAllFamilies().size();

        // Associate
        familyService.addOperationFamilyAssociation(family.getId(), operationPublishedExternally.getId());

        // Reload family
        family = familyService.findFamilyById(family.getId());

        // Publish family
        family = familyService.publishInternallyFamily(family.getId());
        family = familyService.publishExternallyFamily(family.getId());
        assertTrue(ProcStatusEnum.EXTERNALLY_PUBLISHED.equals(family.getProcStatus()));

        // Validations
        assertNotNull(family);
        assertNotNull(family.getInternalInventoryDate());
        assertNotNull(family.getInventoryDate());
        assertTrue(ProcStatusEnum.EXTERNALLY_PUBLISHED.equals(family.getProcStatus()));
        int familiesAfter = familyService.findAllFamilies().size();
        assertEquals(familiesBefore, familiesAfter);

        // Check that family can't be internally published
        try {
            familyService.publishInternallyFamily(family.getId());
        } catch (EDatosException e) {
            assertEquals(ServiceExceptionType.INVALID_PROC_STATUS.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }

    private FamilyEntity createFamilyWithOperations() {
        FamilyEntity family = mockFamily();

        // OPERATIONS
        for (int i = 0; i < 4; i++) {
            OperationEntity operation = operationService.createOperation(mockOperation());
            family.getOperations().add(operation);
        }

        return family;
    }
}
