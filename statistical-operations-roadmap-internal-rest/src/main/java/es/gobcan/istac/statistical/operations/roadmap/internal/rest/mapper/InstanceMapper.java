package es.gobcan.istac.statistical.operations.roadmap.internal.rest.mapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.siemac.edatos.core.common.exception.EDatosException;
import org.siemac.edatos.core.common.exception.EDatosExceptionItem;
import org.siemac.edatos.core.common.exception.utils.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.enumeration.ProcStatusEnum;
import es.gobcan.istac.statistical.operations.roadmap.core.errors.ServiceExceptionParameters;
import es.gobcan.istac.statistical.operations.roadmap.core.errors.ServiceExceptionType;
import es.gobcan.istac.statistical.operations.roadmap.core.repository.InstanceRepository;
import es.gobcan.istac.statistical.operations.roadmap.core.util.StatisticalOperationsValidationUtils;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.dto.InstanceBaseDto;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.dto.InstanceDto;

@Component
public class InstanceMapper {

    @Autowired
    private InstanceRepository instanceRepository;

    @Autowired
    private InternationalStringMapper internationalStringMapper;

    @Autowired
    private ExternalItemMapper externalItemMapper;

    @Autowired
    private InstanceTypeMapper instanceTypeMapper;

    @Autowired
    private SurveySourceMapper surveySourceMapper;

    @Autowired
    private CollMethodMapper collMethodMapper;

    @Autowired
    private CostMapper costMapper;

    public InstanceBaseDto toBaseDto(InstanceEntity source) {
        if (source == null) {
            return null;
        }

        InstanceBaseDto target = new InstanceBaseDto();
        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setCreatedBy(source.getCreatedBy());
        target.setCreatedDate(source.getCreatedDate());
        target.setLastModifiedBy(source.getLastModifiedBy());
        target.setLastModifiedDate(source.getLastModifiedDate());

        // CODE
        // Not necessary

        // URN
        // Not necessary

        // TITLE
        target.setTitle(internationalStringMapper.toDto(source.getTitle()));

        // DESCRIPTION
        target.setDataDescription(internationalStringMapper.toDto(source.getDataDescription()));

        // ACRONYM
        target.setAcronym(internationalStringMapper.toDto(source.getAcronym()));

        // PROC_STATUS
        // Not necessary

        // INSTANCE TYPE
        // Not necessary

        // INTERNAL INVENTORY DATE
        // Not necessary

        // ORDER
        // Not necessary

        target.setOptLock(source.getOptLock());

        return target;
    }

    public InstanceDto toDto(InstanceEntity source) {
        if (source == null) {
            return null;
        }

        InstanceDto target = new InstanceDto();
        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setCreatedBy(source.getCreatedBy());
        target.setCreatedDate(source.getCreatedDate());
        target.setLastModifiedBy(source.getLastModifiedBy());
        target.setLastModifiedDate(source.getLastModifiedDate());

        // ORDER
        // Not necessary

        // CODE
        target.setCode(source.getCode());

        // URN
        target.setUrn(source.getUrn());

        // TITLE
        target.setTitle(internationalStringMapper.toDto(source.getTitle()));

        // TITLE_ALETERNATIVE
        target.setAcronym(internationalStringMapper.toDto(source.getAcronym()));

        // SURVEY_CODE
        // Instance DTO doesn't have Operation Information.

        // DATA_DESCRIPTION
        target.setDataDescription(internationalStringMapper.toDto(source.getDataDescription()));

        // STATISTICAL_POPULATION
        target.setStatisticalPopulation(internationalStringMapper.toDto(source.getStatisticalPopulation()));

        // STATISTICAL_UNIT
        target.getStatisticalUnit().addAll(externalItemMapper.toDtos(source.getStatisticalUnit()));

        // GEOGRAPHIC_GRANULARITY
        target.getGeographicGranularity().addAll(externalItemMapper.toDtos(source.getGeographicGranularity()));

        // GEOGRAPHIC_COMPARABILITY
        target.setGeographicComparability(internationalStringMapper.toDto(source.getGeographicComparability()));

        // TEMPORAL_GRANULARITY
        target.getTemporalGranularity().addAll(externalItemMapper.toDtos(source.getTemporalGranularity()));

        // TEMPORAL_COMPARABILITY
        target.setTemporalComparability(internationalStringMapper.toDto(source.getTemporalComparability()));

        // BASE_PERIOD
        // Not necessary

        // UNIT_MEASURE
        target.getUnitMeasure().addAll(externalItemMapper.toDtos(source.getUnitMeasure()));

        // STAT_CONC_DEF
        target.setStatConcDef(internationalStringMapper.toDto(source.getStatConcDef()));

        // STAT_CONC_DEF_LIST
        target.getStatConcDefList().addAll(externalItemMapper.toDtos(source.getStatConcDefList()));

        // CLASS_SYSTEM
        target.setClassSystem(internationalStringMapper.toDto(source.getClassSystem()));

        // CLASS_SYSTEM_LIST
        target.getClassSystemList().addAll(externalItemMapper.toDtos(source.getClassSystemList()));

        // INSTANCE_TYPE
        target.setInstanceType(instanceTypeMapper.toDto(source.getInstanceType()));

        // INTERNAL_INVENTORY_DATE
        // Not necessary

        // INACTIVE_DATE
        // Not necessary

        // PROC_STATUS
        // Not necessary

        // DOC_METHOD
        target.setDocMethod(internationalStringMapper.toDto(source.getDocMethod()));

        // DOC_METHOD_URL
        // Not necessary

        // SURVEY_SOURCE
        target.setSurveySource(surveySourceMapper.toDto(source.getSurveySource()));

        // COLL_METHOD
        target.setCollMethod(collMethodMapper.toDto(source.getCollMethod()));

        // INFORMATION_SUPPLIERS
        target.getInformationSuppliers().addAll(externalItemMapper.toDtos(source.getInformationSuppliers()));

        // FREQ_COLL
        target.getFreqColl().addAll(externalItemMapper.toDtos(source.getFreqColl()));

        // DATA_VALIDATION
        target.setDataValidation(internationalStringMapper.toDto(source.getDataValidation()));
        // DATA_VALIDATION_URL
        // Not necessary

        // DATA_COMPILATION
        target.setDataCompilation(internationalStringMapper.toDto(source.getDataCompilation()));
        // DATA_COMPILATION_URL
        // Not necessary

        // ADJUSTMENT
        target.setAdjustment(internationalStringMapper.toDto(source.getAdjustment()));
        // ADJUSTMENT_URL
        // Not necessary

        // COST_BURDEN
        target.setCostBurden(internationalStringMapper.toDto(source.getCostBurden()));
        // COST_BURDEN_URL
        // Not necessary

        // COST
        target.getCost().addAll(costMapper.toDtos(source.getCost()));

        // INVENTORY_DATE
        // Not necessary

        // QUALITY_DOC
        target.setQualityDoc(internationalStringMapper.toDto(source.getQualityDoc()));
        // QUALITY_DOC_URL
        // Not necessary

        // QUALITY_ASSURE
        target.setQualityAssure(internationalStringMapper.toDto(source.getQualityAssure()));
        // QUALITY_ASSURE_URL
        // Not necessary

        // QUALITY_ASSMNT
        target.setQualityAssmnt(internationalStringMapper.toDto(source.getQualityAssmnt()));
        // QUALITY_ASSMNT_URL
        // Not necessary

        // USER_NEEDS
        target.setUserNeeds(internationalStringMapper.toDto(source.getUserNeeds()));
        // USER_NEEDS_URL
        // Not necessary

        // USER_SAT
        target.setUserSat(internationalStringMapper.toDto(source.getUserSat()));
        // USER_SAT_URL
        // Not necessary

        // COMPLETENESS
        target.setCompleteness(internationalStringMapper.toDto(source.getCompleteness()));
        // COMPLETENESS_URL
        // Not necessary

        // TIMELINESS
        target.setTimeliness(internationalStringMapper.toDto(source.getTimeliness()));
        // TIMELINESS_URL
        // Not necessary

        // PUNCTUALITY
        target.setPunctuality(internationalStringMapper.toDto(source.getPunctuality()));
        // PUNCTUALITY_URL
        // Not necessary

        // ACCURACY_OVERALL
        target.setAccuracyOverall(internationalStringMapper.toDto(source.getAccuracyOverall()));
        // ACCURACY_OVERALL_URL
        // Not necessary

        // SAMPLING_ERR
        target.setSamplingErr(internationalStringMapper.toDto(source.getSamplingErr()));
        // SAMPLING_ERR_URL
        // Not necessary

        // NONSAMPLING_ERR
        target.setNonsamplingErr(internationalStringMapper.toDto(source.getNonsamplingErr()));
        // NONSAMPLING_ERR_URL
        // Not necessary

        // COHER_X_DOMAIN
        target.setCoherXDomain(internationalStringMapper.toDto(source.getCoherXDomain()));
        // COHER_X_DOMAIN_URL
        // Not necessary

        // COHER_INTERNAL
        target.setCoherInternal(internationalStringMapper.toDto(source.getCoherInternal()));
        // COHER_INTERNAL_URL
        // Not necessary

        // COMMENT
        target.setComment(internationalStringMapper.toDto(source.getComment()));
        // COMMENT_URL
        // Not necessary

        // NOTES
        target.setNotes(internationalStringMapper.toDto(source.getNotes()));
        // NOTES_URL
        // Not necessary

        target.setOptLock(source.getOptLock());

        return target;
    }

    public InstanceEntity toEntity(InstanceDto source) {
        if (source == null) {
            return null;
        }

        // If exists, retrieves existing entity. Otherwise, creates new entity
        InstanceEntity target = new InstanceEntity();
        if (source.getId() != null) {
            target = instanceRepository.findOne(source.getId());

            // Metadata unmodifiable
            // It's necessary to check that all the metadata that conforms the URN are unmodifibale.
            if (!ProcStatusEnum.DRAFT.equals(target.getProcStatus())) {
                List<EDatosExceptionItem> exceptions = new ArrayList<>();
                StatisticalOperationsValidationUtils.checkMetadataUnmodifiable(target.getCode(), source.getCode(), ServiceExceptionParameters.INSTANCE_CODE, exceptions);
                ExceptionUtils.throwIfException(exceptions);
            }
        }

        return instanceDtoToEntity(source, target);
    }

    private InstanceEntity instanceDtoToEntity(InstanceDto source, InstanceEntity target) {
        if (target == null) {
            throw new EDatosException(ServiceExceptionType.PARAMETER_REQUIRED);
        }

        // ORDER
        // Not necessary. It can't be manually edited.

        // CODE
        target.setCode(source.getCode());

        // URN
        // Not necessary. It can't be manually modified

        // TITLE
        target.setTitle(internationalStringMapper.toEntity(source.getTitle(), target.getTitle(), ServiceExceptionParameters.INSTANCE_TITLE));

        // ACRONYM
        target.setAcronym(internationalStringMapper.toEntity(source.getAcronym(), target.getAcronym(), ServiceExceptionParameters.INSTANCE_ACRONYM));

        // SURVEY_CODE
        // Not necessary. It's a relation

        // SURVEY_TITLE
        // Not necessary. It's a relation

        // SUCCESSOR
        // Not necessary. Extracted from instances order.

        // PREDECESSOR
        // Not necessary. Extracted from instances order.

        // DATA_DESCRIPTION
        target.setDataDescription(internationalStringMapper.toEntity(source.getDataDescription(), target.getDataDescription(), ServiceExceptionParameters.INSTANCE_DESCRIPTION));

        // STATISTICAL_POPULATION
        target.setStatisticalPopulation(
                internationalStringMapper.toEntity(source.getStatisticalPopulation(), target.getStatisticalPopulation(), ServiceExceptionParameters.INSTANCE_STATISTICAL_POPULATION));

        // STATISTICAL_UNIT
        target.getStatisticalUnit().addAll(externalItemMapper.toEntities(source.getStatisticalUnit(), target.getStatisticalUnit(), ServiceExceptionParameters.INSTANCE_STATISTICAL_UNIT));

        // GEOGRAPHIC_GRANULARITY
        target.getGeographicGranularity()
                .addAll(externalItemMapper.toEntities(source.getGeographicGranularity(), target.getGeographicGranularity(), ServiceExceptionParameters.INSTANCE_GEOGRAPHIC_GRANULARITY));

        // GEOGRAPHIC_COMPARABILITY
        target.setGeographicComparability(
                internationalStringMapper.toEntity(source.getGeographicComparability(), target.getGeographicComparability(), ServiceExceptionParameters.INSTANCE_GEOGRAPHIC_COMPARABILITY));

        // TEMPORAL_GRANULARITY
        target.getTemporalGranularity()
                .addAll(externalItemMapper.toEntities(source.getTemporalGranularity(), target.getTemporalGranularity(), ServiceExceptionParameters.INSTANCE_TEMPORAL_GRANULARITY));

        // TEMPORAL_COMPARABILITY
        target.setTemporalComparability(
                internationalStringMapper.toEntity(source.getTemporalComparability(), target.getTemporalComparability(), ServiceExceptionParameters.INSTANCE_TEMPORAL_COMPARABILITY));

        // BASE_PERIOD
        target.setBasePeriod(source.getBasePeriod());

        // UNIT_MEASURE
        target.getUnitMeasure().addAll(externalItemMapper.toEntities(source.getUnitMeasure(), target.getUnitMeasure(), ServiceExceptionParameters.INSTANCE_UNIT_MEASURE));

        // STAT_CONC_DEF
        target.setStatConcDef(internationalStringMapper.toEntity(source.getStatConcDef(), target.getStatConcDef(), ServiceExceptionParameters.INSTANCE_STAT_CONC_DEF));

        // STAT_CONC_DEF_LIST
        target.getStatConcDefList().addAll(externalItemMapper.toEntities(source.getStatConcDefList(), target.getStatConcDefList(), ServiceExceptionParameters.INSTANCE_STAT_CONC_DEF_LIST));

        // CLASS_SYSTEM
        target.setClassSystem(internationalStringMapper.toEntity(source.getClassSystem(), target.getClassSystem(), ServiceExceptionParameters.INSTANCE_CLASS_SYSTEM));

        // CLASS_SYSTEM_LIST
        target.getClassSystemList().addAll(externalItemMapper.toEntities(source.getClassSystemList(), target.getClassSystemList(), ServiceExceptionParameters.INSTANCE_CLASS_SYSTEM_LIST));

        // INSTANCE_TYPE
        target.setInstanceType(instanceTypeMapper.toEntity(source.getInstanceType()));

        // INTERNAL_INVENTORY_DATE
        // Not necessary. It can't be manually edited.

        // PROC_STATUS
        // Not necessary. It can't be manually edited.

        // DOC_METHOD
        target.setDocMethod(internationalStringMapper.toEntity(source.getDocMethod(), target.getDocMethod(), ServiceExceptionParameters.INSTANCE_DOC_METHOD));

        // SURVEY_SOURCE
        target.setSurveySource(surveySourceMapper.toEntity(source.getSurveySource()));

        // COLL_METHOD
        target.setCollMethod(collMethodMapper.toEntity(source.getCollMethod()));

        // INFORMATION_SUPPLIERS
        target.getInformationSuppliers()
                .addAll(externalItemMapper.toEntities(source.getInformationSuppliers(), target.getInformationSuppliers(), ServiceExceptionParameters.INSTANCE_INFORMATION_SUPPLIERS));

        // FREQ_COLL
        target.getFreqColl().addAll(externalItemMapper.toEntities(source.getFreqColl(), target.getFreqColl(), ServiceExceptionParameters.INSTANCE_FREQ_COLL));

        // DATA_VALIDATION
        target.setDataValidation(internationalStringMapper.toEntity(source.getDataValidation(), target.getDataValidation(), ServiceExceptionParameters.INSTANCE_DATA_VALIDATION));

        // DATA_COMPILATION
        target.setDataCompilation(internationalStringMapper.toEntity(source.getDataCompilation(), target.getDataCompilation(), ServiceExceptionParameters.INSTANCE_DATA_COMPILATION));

        // ADJUSTMENT
        target.setAdjustment(internationalStringMapper.toEntity(source.getAdjustment(), target.getAdjustment(), ServiceExceptionParameters.INSTANCE_ADJUSTMENT));

        // COST_BURDEN
        target.setCostBurden(internationalStringMapper.toEntity(source.getCostBurden(), target.getCostBurden(), ServiceExceptionParameters.INSTANCE_COST_BURDEN));

        // COST
        target.getCost().addAll(costMapper.toEntities(source.getCost()));

        // INVENTORY_DATE
        // Not necessary. It can't be manually edited.

        // QUALITY_DOC
        target.setQualityDoc(internationalStringMapper.toEntity(source.getQualityDoc(), target.getQualityDoc(), ServiceExceptionParameters.INSTANCE_QUALITY_DOC));

        // QUALITY_ASSURE
        target.setQualityAssure(internationalStringMapper.toEntity(source.getQualityAssure(), target.getQualityAssure(), ServiceExceptionParameters.INSTANCE_QUALITY_ASSURE));

        // QUALITY_ASSMNT
        target.setQualityAssmnt(internationalStringMapper.toEntity(source.getQualityAssmnt(), target.getQualityAssmnt(), ServiceExceptionParameters.INSTANCE_QUALITY_ASSMNT));

        // USER_NEEDS
        target.setUserNeeds(internationalStringMapper.toEntity(source.getUserNeeds(), target.getUserNeeds(), ServiceExceptionParameters.INSTANCE_USER_NEEDS));

        // USER_SAT
        target.setUserSat(internationalStringMapper.toEntity(source.getUserSat(), target.getUserSat(), ServiceExceptionParameters.INSTANCE_USER_SAT));

        // COMPLETENESS
        target.setCompleteness(internationalStringMapper.toEntity(source.getCompleteness(), target.getCompleteness(), ServiceExceptionParameters.INSTANCE_COMPLETENESS));

        // TIMELINESS
        target.setTimeliness(internationalStringMapper.toEntity(source.getTimeliness(), target.getTimeliness(), ServiceExceptionParameters.INSTANCE_TIMELINESS));

        // PUNCTUALITY
        target.setPunctuality(internationalStringMapper.toEntity(source.getPunctuality(), target.getPunctuality(), ServiceExceptionParameters.INSTANCE_PUNCTUALITY));

        // ACCURACY_OVERALL
        target.setAccuracyOverall(internationalStringMapper.toEntity(source.getAccuracyOverall(), target.getAccuracyOverall(), ServiceExceptionParameters.INSTANCE_ACCURACY_OVERALL));

        // SAMPLING_ERR
        target.setSamplingErr(internationalStringMapper.toEntity(source.getSamplingErr(), target.getSamplingErr(), ServiceExceptionParameters.INSTANCE_SAMPLING_ERR));

        // NONSAMPLING_ERR
        target.setNonsamplingErr(internationalStringMapper.toEntity(source.getNonsamplingErr(), target.getNonsamplingErr(), ServiceExceptionParameters.INSTANCE_NONSAMPLING_ERR));

        // COHER_X_DOMAIN
        target.setCoherXDomain(internationalStringMapper.toEntity(source.getCoherXDomain(), target.getCoherXDomain(), ServiceExceptionParameters.INSTANCE_COHER_X_DOMAIN));

        // COHER_INTERNAL
        target.setCoherInternal(internationalStringMapper.toEntity(source.getCoherInternal(), target.getCoherInternal(), ServiceExceptionParameters.INSTANCE_COHER_INTERNAL));

        // COMMENT
        target.setComment(internationalStringMapper.toEntity(source.getComment(), target.getComment(), ServiceExceptionParameters.INSTANCE_COMMENT));

        // NOTES
        target.setNotes(internationalStringMapper.toEntity(source.getNotes(), target.getNotes(), ServiceExceptionParameters.INSTANCE_NOTES));

        // Optimistic locking: Update "update date" attribute to force update of the root entity in order to increase attribute "version"
        target.setUpdateDate(Instant.now());

        target.setOptLock(source.getOptLock());

        return target;
    }
}
