package es.gobcan.istac.statistical.operations.roadmap.external.api.v1_0.mapper;

import java.util.List;

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
import org.springframework.data.domain.Page;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.CollMethodEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.CostEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.FamilyEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceTypeEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.OfficialityTypeEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.SurveySourceEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.SurveyTypeEntity;

public interface Do2RestExternalMapperV10 {

    // --------------
    // Operations
    // --------------
    Operation toOperation(OperationEntity source);

    Operations toOperations(Page<OperationEntity> sources, String query, String orderBy, Integer limit);

    Operations toOperationsByFamily(FamilyEntity family, Page<OperationEntity> sources, String query, String orderBy, Integer limit);

    // --------------
    // Families
    // --------------
    Family toFamily(FamilyEntity source);

    Families toFamilies(Page<FamilyEntity> sources, String query, String orderBy, Integer limit);

    Families toFamiliesByOperation(List<FamilyEntity> sources);

    // --------------
    // Instances
    // --------------
    Instance toInstance(InstanceEntity source);

    Instances toInstances(OperationEntity operation, Page<InstanceEntity> sources, String query, String orderBy, Integer limit);

    // --------------
    // List of values
    // --------------
    StatisticalOperationTypes toStatisticalOperationTypes(List<SurveyTypeEntity> sources);

    OfficialityTypes toOfficialityTypes(List<OfficialityTypeEntity> entitiesResult);

    InstanceTypes toInstanceTypes(List<InstanceTypeEntity> entitiesResult);

    StatisticalOperationSources toStatisticalOperationSources(List<SurveySourceEntity> entitiesResult);

    CollMethods toCollMethods(List<CollMethodEntity> entitiesResult);

    Costs toCosts(List<CostEntity> entitiesResult);

}
