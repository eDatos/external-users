package es.gobcan.istac.statistical.operations.roadmap.core.service;

import java.util.List;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.CollMethodEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.CostEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceTypeEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.OfficialityTypeEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.SurveySourceEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.SurveyTypeEntity;

public interface CommonListService {

    public SurveyTypeEntity findSurveyTypeById(Long id);

    public List<SurveyTypeEntity> findAllSurveyTypes();

    public InstanceTypeEntity findInstanceTypeById(Long id);

    public List<InstanceTypeEntity> findAllInstanceTypes();

    public SurveySourceEntity findSurveySourceById(Long id);

    public List<SurveySourceEntity> findAllSurveySources();

    public OfficialityTypeEntity findOfficialityTypeById(Long id);

    public List<OfficialityTypeEntity> findAllOfficialityTypes();

    public CollMethodEntity findCollMethodById(Long id);

    public List<CollMethodEntity> findAllCollMethods();

    public CostEntity findCostById(Long id);

    public List<CostEntity> findAllCosts();

}
