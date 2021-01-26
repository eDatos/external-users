package es.gobcan.istac.statistical.operations.roadmap.core.service.impl;

import java.util.List;

import org.siemac.edatos.core.common.exception.EDatosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.CollMethodEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.CostEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceTypeEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.OfficialityTypeEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.SurveySourceEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.SurveyTypeEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.errors.ServiceExceptionType;
import es.gobcan.istac.statistical.operations.roadmap.core.repository.CollMethodRepository;
import es.gobcan.istac.statistical.operations.roadmap.core.repository.CostRepository;
import es.gobcan.istac.statistical.operations.roadmap.core.repository.InstanceTypeRepository;
import es.gobcan.istac.statistical.operations.roadmap.core.repository.OfficialityTypeRepository;
import es.gobcan.istac.statistical.operations.roadmap.core.repository.SurveySourceRepository;
import es.gobcan.istac.statistical.operations.roadmap.core.repository.SurveyTypeRepository;
import es.gobcan.istac.statistical.operations.roadmap.core.service.CommonListService;

@Service
public class CommonListServiceImpl implements CommonListService {

    @Autowired
    private SurveyTypeRepository surveyTypeRepository;

    @Autowired
    private InstanceTypeRepository instanceTypeRepository;

    @Autowired
    private SurveySourceRepository surveySourceRepository;

    @Autowired
    private OfficialityTypeRepository officialityTypeRepository;

    @Autowired
    private CollMethodRepository collMethodRepository;

    @Autowired
    private CostRepository costRepository;

    @Override
    public SurveyTypeEntity findSurveyTypeById(Long id) {
        SurveyTypeEntity surveyType = surveyTypeRepository.findOne(id);
        if (surveyType == null) {
            throw new EDatosException(ServiceExceptionType.SURVEY_TYPE_NOT_FOUND, id);
        }
        return surveyType;
    }

    @Override
    public List<SurveyTypeEntity> findAllSurveyTypes() {
        return surveyTypeRepository.findAll();
    }

    @Override
    public InstanceTypeEntity findInstanceTypeById(Long id) {
        InstanceTypeEntity instanceType = instanceTypeRepository.findOne(id);
        if (instanceType == null) {
            throw new EDatosException(ServiceExceptionType.ACTIVITY_TYPE_NOT_FOUND, id);
        }
        return instanceType;
    }

    @Override
    public List<InstanceTypeEntity> findAllInstanceTypes() {
        return instanceTypeRepository.findAll();
    }

    @Override
    public SurveySourceEntity findSurveySourceById(Long id) {
        SurveySourceEntity surveySource = surveySourceRepository.findOne(id);
        if (surveySource == null) {
            throw new EDatosException(ServiceExceptionType.SOURCE_DATA_NOT_FOUND, id);
        }
        return surveySource;
    }

    @Override
    public List<SurveySourceEntity> findAllSurveySources() {
        return surveySourceRepository.findAll();
    }

    @Override
    public OfficialityTypeEntity findOfficialityTypeById(Long id) {
        OfficialityTypeEntity officialityType = officialityTypeRepository.findOne(id);
        if (officialityType == null) {
            throw new EDatosException(ServiceExceptionType.OFFICIALITY_TYPE_NOT_FOUND, id);
        }
        return officialityType;
    }

    @Override
    public List<OfficialityTypeEntity> findAllOfficialityTypes() {
        return officialityTypeRepository.findAll();
    }

    @Override
    public CollMethodEntity findCollMethodById(Long id) {
        CollMethodEntity collMethod = collMethodRepository.findOne(id);
        if (collMethod == null) {
            throw new EDatosException(ServiceExceptionType.COLL_METHOD_NOT_FOUND, id);
        }
        return collMethod;
    }

    @Override
    public List<CollMethodEntity> findAllCollMethods() {
        return collMethodRepository.findAll();
    }

    @Override
    public CostEntity findCostById(Long id) {
        CostEntity cost = costRepository.findOne(id);
        if (cost == null) {
            throw new EDatosException(ServiceExceptionType.COST_NOT_FOUND, id);
        }
        return cost;
    }

    @Override
    public List<CostEntity> findAllCosts() {
        return costRepository.findAll();
    }
}
