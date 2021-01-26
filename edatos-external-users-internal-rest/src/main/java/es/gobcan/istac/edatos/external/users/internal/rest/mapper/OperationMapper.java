package es.gobcan.istac.edatos.external.users.internal.rest.mapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.siemac.edatos.core.common.exception.EDatosException;
import org.siemac.edatos.core.common.exception.EDatosExceptionItem;
import org.siemac.edatos.core.common.exception.utils.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.ProcStatusEnum;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionParameters;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionType;
import es.gobcan.istac.edatos.external.users.core.repository.OperationRepository;
import es.gobcan.istac.edatos.external.users.core.util.StatisticalOperationsValidationUtils;
import es.gobcan.istac.edatos.external.users.internal.rest.dto.OperationBaseDto;
import es.gobcan.istac.edatos.external.users.internal.rest.dto.OperationDto;

@Component
public class OperationMapper {

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private InternationalStringMapper internationalStringMapper;

    @Autowired
    private ExternalItemMapper externalItemMapper;

    @Autowired
    private SurveyTypeMapper surveyTypeMapper;

    @Autowired
    private OfficialityTypeMapper officialityTypeMapper;

    public OperationBaseDto toBaseDto(OperationEntity source) {

        if (source == null) {
            return null;
        }

        OperationBaseDto target = new OperationBaseDto();
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
        target.setDescription(internationalStringMapper.toDto(source.getDescription()));

        // TITLE_ALTERNATIVE
        target.setAcronym(internationalStringMapper.toDto(source.getAcronym()));

        // PROC_STATUS
        // Not necessary

        // SUBJECT_AREA
        target.setSubjectArea(externalItemMapper.toDto(source.getSubjectArea()));

        // SURVEY_TYPE
        target.setSurveyType(surveyTypeMapper.toDto(source.getSurveyType()));

        // OFFICIALITY_TYPE
        target.setOfficialityType(officialityTypeMapper.toDto(source.getOfficialityType()));

        // CURRENTLY ACTIVE
        // Not necessary

        // STATUS
        // Not necessary

        // INTERNAL INVENTORY DATE
        // Not necessary

        target.setOptLock(source.getOptLock());

        return target;
    }

    public OperationDto toDto(OperationEntity source) {
        if (source == null) {
            return null;
        }

        OperationDto target = new OperationDto();
        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setCreatedBy(source.getCreatedBy());
        target.setCreatedDate(source.getCreatedDate());
        target.setLastModifiedBy(source.getLastModifiedBy());
        target.setLastModifiedDate(source.getLastModifiedDate());

        // CODE
        target.setCode(source.getCode());

        // APP_COMMON_METADATA
        target.setCommonMetadata(externalItemMapper.toDto(source.getCommonMetadata()));

        // URN
        target.setUrn(source.getUrn());

        // TITLE
        target.setTitle(internationalStringMapper.toDto(source.getTitle()));

        // TITLE_ALETERNATIVE
        target.setAcronym(internationalStringMapper.toDto(source.getAcronym()));

        // SUBJECT_AREA
        target.setSubjectArea(externalItemMapper.toDto(source.getSubjectArea()));

        // SECONDARY_SUBJECT_AREAS
        target.getSecondarySubjectAreas().addAll(externalItemMapper.toDtos(source.getSecondarySubjectAreas()));

        // OBJECTIVE
        target.setObjective(internationalStringMapper.toDto(source.getObjective()));

        // DESCRIPTION
        target.setDescription(internationalStringMapper.toDto(source.getDescription()));

        // SURVEY_TYPE
        target.setSurveyType(surveyTypeMapper.toDto(source.getSurveyType()));

        // OFFICIALITY_TYPE
        target.setOfficialityType(officialityTypeMapper.toDto(source.getOfficialityType()));

        // INDICATOR_SYSTEM
        // Not necessary

        // PRODUCER
        target.getProducer().addAll(externalItemMapper.toDtos(source.getProducer()));

        // REGIONAL_RESPONSIBLE
        target.getRegionalResponsible().addAll(externalItemMapper.toDtos(source.getRegionalResponsible()));

        // REGIONAL_CONTRIBUTOR
        target.getRegionalContributor().addAll(externalItemMapper.toDtos(source.getRegionalContributor()));

        // INTERNAL_INVENTORY_DATE
        target.setInternalInventoryDate(source.getInternalInventoryDate());

        // CURRENTLY_ACTIVE
        target.setCurrentlyActive(source.getCurrentlyActive());

        // PROC_STATUS
        target.setProcStatus(source.getProcStatus());

        // CURRENT_INTERNAL_INSTANCE
        // Transformed in service

        // PUBLISHER
        target.getPublisher().addAll(externalItemMapper.toDtos(source.getPublisher()));

        // REL_POL_US_AC
        target.setRelPolUsAc(internationalStringMapper.toDto(source.getRelPolUsAc()));
        // REL_POL_US_AC_URL
        // Not necessary

        // RELEASE_CALENDAR
        target.setReleaseCalendar(source.getReleaseCalendar());
        // RELEASE_CALENDAR_ACCESS
        target.setReleaseCalendarAccess(source.getReleaseCalendarAccess());

        // UPDATE_FREQUENCY
        target.getUpdateFrequency().addAll(externalItemMapper.toDtos(source.getUpdateFrequency()));

        // CURRENT_INSTANCE
        // Transformed in service

        // INVENTORY_DATE
        // Not necessary

        // REV_POLICY
        target.setRevPolicy(internationalStringMapper.toDto(source.getRevPolicy()));
        // REV_POLICY_URL
        // Not necessary

        // REV_PRACTICE
        target.setRevPractice(internationalStringMapper.toDto(source.getRevPractice()));
        // REV_PRACTICE_URL
        // Not necessary

        // CONTACT: Extracted from AppCommonMetadata

        // COMMON_LEGAL_ACTS: Extracted from AppCommonMetadata
        target.setSpecificLegalActs(internationalStringMapper.toDto(source.getSpecificLegalActs()));
        // COMMON_DATA_SHARING: Extracted from AppCommonMetadata
        target.setSpecificDataSharing(internationalStringMapper.toDto(source.getSpecificDataSharing()));
        // CONFIDENCIALITY_POLICY: Extracted from AppCommonMetadata
        // CONFIDENCIALITY_DATA_TREATMENT: Extracted from AppCommonMetadata

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

    public OperationEntity toEntity(OperationDto source) {
        if (source == null) {
            return null;
        }

        // If exists, retrieves existing entity. Otherwise, creates new entity
        OperationEntity target = new OperationEntity();
        if (source.getId() != null) {
            target = operationRepository.findOne(source.getId());

            // Metadata unmodifiable
            // It's necessary to check that all the metadata that conforms the URN are unmodifibale.
            if (!ProcStatusEnum.DRAFT.equals(target.getProcStatus())) {
                List<EDatosExceptionItem> exceptions = new ArrayList<>();
                StatisticalOperationsValidationUtils.checkMetadataUnmodifiable(target.getCode(), source.getCode(), ServiceExceptionParameters.OPERATION_CODE, exceptions);
                ExceptionUtils.throwIfException(exceptions);
            }
        }

        return operationDtoToEntity(source, target);
    }

    private OperationEntity operationDtoToEntity(OperationDto source, OperationEntity target) {
        if (target == null) {
            throw new EDatosException(ServiceExceptionType.PARAMETER_REQUIRED);
        }

        // APP_COMMON_METADATA
        target.setCommonMetadata(externalItemMapper.toEntity(source.getCommonMetadata(), target.getCommonMetadata(), ServiceExceptionParameters.OPERATION_COMMON_METADATA));

        // CODE
        target.setCode(source.getCode());

        // URN
        // Not necessary. It can't be manually modified

        // TITLE
        target.setTitle(internationalStringMapper.toEntity(source.getTitle(), target.getTitle(), ServiceExceptionParameters.OPERATION_TITLE));

        // TITLE_ALETERNATIVE
        target.setAcronym(internationalStringMapper.toEntity(source.getAcronym(), target.getAcronym(), ServiceExceptionParameters.OPERATION_ACRONYM));

        // FAMILY_CODE
        // Not necessary.It's a relation

        // FAMILY_TITLE
        // Not necessary. It's a relation

        // SUBJECT_AREA
        target.setSubjectArea(externalItemMapper.toEntity(source.getSubjectArea(), target.getSubjectArea(), ServiceExceptionParameters.OPERATION_SUBJECT_AREA));

        // SECONDARY_SUBJECT_AREAS
        target.getSecondarySubjectAreas()
                .addAll(externalItemMapper.toEntities(source.getSecondarySubjectAreas(), target.getSecondarySubjectAreas(), ServiceExceptionParameters.OPERATION_SECONDARY_SUBJECT_AREAS));

        // OBJECTIVE
        target.setObjective(internationalStringMapper.toEntity(source.getObjective(), target.getObjective(), ServiceExceptionParameters.OPERATION_OBJECTIVE));

        // DESCRIPTION
        target.setDescription(internationalStringMapper.toEntity(source.getDescription(), target.getDescription(), ServiceExceptionParameters.OPERATION_DESCRIPTION));

        // INSTANCE_CODE
        // Not necessary. It's a relation

        // INSTANCE_TITLE
        // Not necessary. It's a relation

        // SURVEY_TYPE
        target.setSurveyType(surveyTypeMapper.toEntity(source.getSurveyType()));

        // INDICATOR_SYSTEM
        target.setIndicatorSystem(source.getIndicatorSystem());

        // OFFICIALITY_TYPE
        target.setOfficialityType(officialityTypeMapper.toEntity(source.getOfficialityType()));

        // PRODUCER
        target.getProducer().addAll(externalItemMapper.toEntities(source.getProducer(), target.getProducer(), ServiceExceptionParameters.OPERATION_PRODUCER));

        // REGIONAL_RESPONSIBLE
        target.getRegionalResponsible()
                .addAll(externalItemMapper.toEntities(source.getRegionalResponsible(), target.getRegionalResponsible(), ServiceExceptionParameters.OPERATION_REGIONAL_RESPONSIBLE));

        // REGIONAL_CONTRIBUTOR
        target.getRegionalContributor()
                .addAll(externalItemMapper.toEntities(source.getRegionalContributor(), target.getRegionalContributor(), ServiceExceptionParameters.OPERATION_REGIONAL_CONTRIBUTOR));

        // INTERNAL_INVENTORY_DATE
        // Not necessary. It can't be manually modified

        // CURRENTLY_ACTIVE
        target.setCurrentlyActive(source.getCurrentlyActive());

        // INACTIVE_DATE
        // Not necessary. It can't be manually modified

        // STATUS
        target.setStatus(source.getStatus());

        // PROC_STATUS
        // Not necessary. It can't be manually modified

        // PUBLISHER
        target.getPublisher().addAll(externalItemMapper.toEntities(source.getPublisher(), target.getPublisher(), ServiceExceptionParameters.OPERATION_PUBLISHER));

        // REL_POL_US_AC
        target.setRelPolUsAc(internationalStringMapper.toEntity(source.getRelPolUsAc(), target.getRelPolUsAc(), ServiceExceptionParameters.OPERATION_REL_POL_US_AC));

        // RELEASE_CALENDAR
        target.setReleaseCalendar(source.getReleaseCalendar());

        // RELEASE_CALENDAR_ACCESS
        target.setReleaseCalendarAccess(source.getReleaseCalendarAccess());

        // UPDATE_FREQUENCY
        target.getUpdateFrequency().addAll(externalItemMapper.toEntities(source.getUpdateFrequency(), target.getUpdateFrequency(), ServiceExceptionParameters.OPERATION_UPDATE_FREQUENCY));

        // CURRENT_INTERNAL_INSTANCE
        // Not necessary. Extracted from instances order.

        // CURRENT_INSTANCE.
        // Not necessary. Extracted from instances order.

        // INVENTORY_DATE
        // Not necessary. It can't be manually modified.

        // REV_POLICY
        target.setRevPolicy(internationalStringMapper.toEntity(source.getRevPolicy(), target.getRevPolicy(), ServiceExceptionParameters.OPERATION_REV_POLICY));

        // REV_PRACTICE
        target.setRevPractice(internationalStringMapper.toEntity(source.getRevPractice(), target.getRevPractice(), ServiceExceptionParameters.OPERATION_REV_PRACTICE));

        // CONTACT: Extracted from AppCommonMetadata

        // LEGAL_ACTS: Extracted from AppCommonMetadata
        target.setSpecificLegalActs(internationalStringMapper.toEntity(source.getSpecificLegalActs(), target.getSpecificLegalActs(), ServiceExceptionParameters.OPERATION_SPECIFIC_LEGAL_ACTS));
        // DATA_SHARING: Extracted from AppCommonMetadata
        target.setSpecificDataSharing(internationalStringMapper.toEntity(source.getSpecificDataSharing(), target.getSpecificDataSharing(), ServiceExceptionParameters.OPERATION_SPECIFIC_DATA_SHARING));
        // CONFIDENCIALITY_POLICY: Extracted from AppCommonMetadata
        // CONFIDENCIALITY_DATA_TREATMENT: Extracted from AppCommonMetadata

        // COMMENT
        target.setComment(internationalStringMapper.toEntity(source.getComment(), target.getComment(), ServiceExceptionParameters.OPERATION_COMMENT));

        // NOTES
        target.setNotes(internationalStringMapper.toEntity(source.getNotes(), target.getNotes(), ServiceExceptionParameters.OPERATION_NOTES));

        // Optimistic locking: Update "update date" attribute to force update of the root entity in order to increase attribute "version"
        target.setUpdateDate(Instant.now());

        target.setOptLock(source.getOptLock());

        return target;
    }
}
