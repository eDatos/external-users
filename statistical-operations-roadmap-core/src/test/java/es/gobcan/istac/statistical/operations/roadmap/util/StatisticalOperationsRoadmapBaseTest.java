package es.gobcan.istac.statistical.operations.roadmap.util;

import javax.persistence.EntityManager;

import org.siemac.edatos.common.test.EDatosBaseTest;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.CollMethodEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.CostEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceTypeEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.OfficialityTypeEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.SurveySourceEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.SurveyTypeEntity;

public abstract class StatisticalOperationsRoadmapBaseTest extends EDatosBaseTest {

    protected void createOfficialityTypes(EntityManager em, String... identifiers) {
        for (String identifier : identifiers) {
            OfficialityTypeEntity officialityType = new OfficialityTypeEntity();
            officialityType.setIdentifier(identifier);
            em.persist(officialityType);
        }
    }

    protected void createSurveyTypes(EntityManager em, String... identifiers) {
        for (String identifier : identifiers) {
            SurveyTypeEntity surveyType = new SurveyTypeEntity();
            surveyType.setIdentifier(identifier);
            em.persist(surveyType);
        }
    }

    protected void createInstanceTypes(EntityManager em, String... identifiers) {
        for (String identifier : identifiers) {
            InstanceTypeEntity instanceType = new InstanceTypeEntity();
            instanceType.setIdentifier(identifier);
            em.persist(instanceType);
        }
    }

    protected void createCosts(EntityManager em, String... identifiers) {
        for (String identifier : identifiers) {
            CostEntity instanceType = new CostEntity();
            instanceType.setIdentifier(identifier);
            em.persist(instanceType);
        }
    }

    protected void createCollMethods(EntityManager em, String... identifiers) {
        for (String identifier : identifiers) {
            CollMethodEntity instanceType = new CollMethodEntity();
            instanceType.setIdentifier(identifier);
            em.persist(instanceType);
        }
    }
    
    protected void createSurveySources(EntityManager em, String... identifiers) {
        for (String identifier : identifiers) {
            SurveySourceEntity instanceType = new SurveySourceEntity();
            instanceType.setIdentifier(identifier);
            em.persist(instanceType);
        }
    }
}
