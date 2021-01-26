package es.gobcan.istac.edatos.external.users.core.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import es.gobcan.istac.edatos.external.users.core.domain.CollMethodEntity;
import es.gobcan.istac.edatos.external.users.core.domain.CostEntity;
import es.gobcan.istac.edatos.external.users.core.domain.InstanceTypeEntity;
import es.gobcan.istac.edatos.external.users.core.domain.OfficialityTypeEntity;
import es.gobcan.istac.edatos.external.users.core.domain.SurveySourceEntity;
import es.gobcan.istac.edatos.external.users.core.domain.SurveyTypeEntity;
import es.gobcan.istac.edatos.external.users.EdatosExternalUsersCoreTestApp;
import es.gobcan.istac.edatos.external.users.util.EdatosExternalUsersBaseTest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdatosExternalUsersCoreTestApp.class)
public class CommonListServiceIntTest extends EdatosExternalUsersBaseTest {

    @Autowired
    protected CommonListService commonListService;

    @Autowired
    private EntityManager em;

    @Before
    public void initTest() {
        createOfficialityTypes(em, "ESTUDIO", "OFICIAL");
        createSurveyTypes(em, "COYUNTURAL", "ESTRUCTURAL", "OTRAS");
        createInstanceTypes(em, "SERIE", "SECCION");
        createCosts(em, "MUY_BAJO", "BAJO", "MEDIO", "ALTO", "MUY_ALTO");
        createCollMethods(em, "AUTOENUMERACION", "ENTREVISTA_DIRECTA", "CONVERSACION_TELEFONO", "TRANSCRIPCION_DOCUMENTO", "OBSERVACION_DIRECTA", "FORMAS_MIXTAS", "OTRAS", "NO_APLICABLE",
                "INDETERMINADA");
        createSurveySources(em, "ENCUESTA", "ADMINISTRACION");
    }

    @Test
    @Transactional
    public void testFindSurveyTypeById() throws Exception {
        List<SurveyTypeEntity> surveyTypes = commonListService.findAllSurveyTypes();
        SurveyTypeEntity surveyType = commonListService.findSurveyTypeById(surveyTypes.get(ThreadLocalRandom.current().nextInt(0, surveyTypes.size())).getId());
        assertNotNull(surveyType);
    }

    @Test
    @Transactional
    public void testFindAllSurveyTypes() throws Exception {
        List<SurveyTypeEntity> surveyTypesList = commonListService.findAllSurveyTypes();
        assertTrue(!surveyTypesList.isEmpty());
    }

    @Test
    @Transactional
    public void testFindInstanceTypeById() throws Exception {
        List<InstanceTypeEntity> instanceTypes = commonListService.findAllInstanceTypes();
        InstanceTypeEntity instanceType = commonListService.findInstanceTypeById(instanceTypes.get(ThreadLocalRandom.current().nextInt(0, instanceTypes.size())).getId());
        assertNotNull(instanceType);
    }

    @Test
    @Transactional
    public void testFindAllInstanceTypes() throws Exception {
        List<InstanceTypeEntity> instanceTypesList = commonListService.findAllInstanceTypes();
        assertTrue(!instanceTypesList.isEmpty());
    }

    @Test
    @Transactional
    public void testFindSurveySourceById() throws Exception {
        List<SurveySourceEntity> surveySources = commonListService.findAllSurveySources();
        SurveySourceEntity surveySource = commonListService.findSurveySourceById(surveySources.get(ThreadLocalRandom.current().nextInt(0, surveySources.size())).getId());
        assertNotNull(surveySource);
    }

    @Test
    @Transactional
    public void testFindAllSurveySources() throws Exception {
        List<SurveySourceEntity> sourcesDataList = commonListService.findAllSurveySources();
        assertTrue(!sourcesDataList.isEmpty());
    }

    @Test
    @Transactional
    public void testFindOfficialityTypeById() throws Exception {
        List<OfficialityTypeEntity> officialityTypes = commonListService.findAllOfficialityTypes();
        OfficialityTypeEntity officialityType = commonListService.findOfficialityTypeById(officialityTypes.get(ThreadLocalRandom.current().nextInt(0, officialityTypes.size())).getId());
        assertNotNull(officialityType);
    }

    @Test
    @Transactional
    public void testFindAllOfficialityTypes() throws Exception {
        List<OfficialityTypeEntity> officialityTypesList = commonListService.findAllOfficialityTypes();
        assertTrue(!officialityTypesList.isEmpty());
    }

    @Test
    @Transactional
    public void testFindCollMethodById() throws Exception {
        List<CollMethodEntity> collMethods = commonListService.findAllCollMethods();
        CollMethodEntity collMethod = commonListService.findCollMethodById(collMethods.get(ThreadLocalRandom.current().nextInt(0, collMethods.size())).getId());
        assertNotNull(collMethod);
    }

    @Test
    @Transactional
    public void testFindAllCollMethods() throws Exception {
        List<CollMethodEntity> collMethodsList = commonListService.findAllCollMethods();
        assertTrue(!collMethodsList.isEmpty());
    }

    @Test
    @Transactional
    public void testFindCostById() throws Exception {
        List<CostEntity> costs = commonListService.findAllCosts();
        CostEntity cost = commonListService.findCostById(costs.get(ThreadLocalRandom.current().nextInt(0, costs.size())).getId());
        assertNotNull(cost);
    }

    @Test
    @Transactional
    public void testFindAllCosts() throws Exception {
        List<CostEntity> costsList = commonListService.findAllCosts();
        assertTrue(!costsList.isEmpty());
    }
}
