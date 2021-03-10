package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.mapstruct.Mapper;

import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;
import es.gobcan.istac.edatos.external.users.core.repository.OperationRepository;
import es.gobcan.istac.edatos.external.users.rest.common.dto.OperationBaseDto;
import es.gobcan.istac.edatos.external.users.rest.common.dto.OperationDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.resolver.GenericMapperResolver;

@Mapper(componentModel = "spring", uses = {GenericMapperResolver.class, ExternalItemMapper.class, InternationalStringVOMapper.class, CategoryMapper.class, OperationRepository.class})
public abstract class OperationMapper implements EntityMapper<OperationDto, OperationEntity> {

    public abstract OperationBaseDto toBaseDto(OperationEntity source);

    public abstract OperationDto toDto(OperationEntity source);

    public abstract OperationEntity toEntity(OperationDto source);
    //    if (source == null) {
    //        return null;
    //    }
    //
    //    // If exists, retrieves existing entity. Otherwise, creates new entity
    //    OperationEntity target = new OperationEntity();
    //    if (source.getId() != null) {
    //        target = operationRepository.findOne(source.getId());
    //

    // TODO(EDATOS-3294): This should go into validator/service

    //        // Metadata unmodifiable
    //        // It's necessary to check that all the metadata that conforms the URN are unmodifibale.
    //        if (!ProcStatusEnum.DRAFT.equals(target.getProcStatus())) {
    //            List<EDatosExceptionItem> exceptions = new ArrayList<>();
    //            StatisticalOperationsValidationUtils.checkMetadataUnmodifiable(target.getCode(), source.getCode(), ServiceExceptionParameters.OPERATION_CODE, exceptions);
    //            ExceptionUtils.throwIfException(exceptions);
    //        }
    //    }
    //
    //    return operationDtoToEntity(source, target);
    //}

    //public abstract OperationEntity operationDtoToEntity(OperationDto source, OperationEntity target);
    //    if (target == null) {
    //        throw new EDatosException(ServiceExceptionType.PARAMETER_REQUIRED);
    //    }
    //
    //    // APP_COMMON_METADATA
    //    target.setCommonMetadata(externalItemMapper.toEntity(source.getCommonMetadata(), target.getCommonMetadata(), ServiceExceptionParameters.OPERATION_COMMON_METADATA));
    //
    //    // CODE
    //    target.setCode(source.getCode());
    //
    //    // URN
    //    // Not necessary. It can't be manually modified
    //
    //    // TITLE
    //    target.setTitle(internationalStringMapper.toEntity(source.getTitle(), target.getTitle(), ServiceExceptionParameters.OPERATION_TITLE));
    //
    //    // TITLE_ALETERNATIVE
    //    target.setAcronym(internationalStringMapper.toEntity(source.getAcronym(), target.getAcronym(), ServiceExceptionParameters.OPERATION_ACRONYM));
    //
    //    // FAMILY_CODE
    //    // Not necessary.It's a relation
    //
    //    // FAMILY_TITLE
    //    // Not necessary. It's a relation
    //
    //    // SUBJECT_AREA
    //    target.setSubjectArea(externalItemMapper.toEntity(source.getSubjectArea(), target.getSubjectArea(), ServiceExceptionParameters.OPERATION_SUBJECT_AREA));
    //
    //    // SECONDARY_SUBJECT_AREAS
    //    target.getSecondarySubjectAreas()
    //            .addAll(externalItemMapper.toEntities(source.getSecondarySubjectAreas(), target.getSecondarySubjectAreas(), ServiceExceptionParameters.OPERATION_SECONDARY_SUBJECT_AREAS));
    //
    //    // OBJECTIVE
    //    target.setObjective(internationalStringMapper.toEntity(source.getObjective(), target.getObjective(), ServiceExceptionParameters.OPERATION_OBJECTIVE));
    //
    //    // DESCRIPTION
    //    target.setDescription(internationalStringMapper.toEntity(source.getDescription(), target.getDescription(), ServiceExceptionParameters.OPERATION_DESCRIPTION));
    //
    //    // INSTANCE_CODE
    //    // Not necessary. It's a relation
    //
    //    // INSTANCE_TITLE
    //    // Not necessary. It's a relation
    //
    //    // SURVEY_TYPE
    //    target.setSurveyType(surveyTypeMapper.toEntity(source.getSurveyType()));
    //
    //    // INDICATOR_SYSTEM
    //    target.setIndicatorSystem(source.getIndicatorSystem());
    //
    //    // OFFICIALITY_TYPE
    //    target.setOfficialityType(officialityTypeMapper.toEntity(source.getOfficialityType()));
    //
    //    // PRODUCER
    //    target.getProducer().addAll(externalItemMapper.toEntities(source.getProducer(), target.getProducer(), ServiceExceptionParameters.OPERATION_PRODUCER));
    //
    //    // REGIONAL_RESPONSIBLE
    //    target.getRegionalResponsible()
    //            .addAll(externalItemMapper.toEntities(source.getRegionalResponsible(), target.getRegionalResponsible(), ServiceExceptionParameters.OPERATION_REGIONAL_RESPONSIBLE));
    //
    //    // REGIONAL_CONTRIBUTOR
    //    target.getRegionalContributor()
    //            .addAll(externalItemMapper.toEntities(source.getRegionalContributor(), target.getRegionalContributor(), ServiceExceptionParameters.OPERATION_REGIONAL_CONTRIBUTOR));
    //
    //    // INTERNAL_INVENTORY_DATE
    //    // Not necessary. It can't be manually modified
    //
    //    // CURRENTLY_ACTIVE
    //    target.setCurrentlyActive(source.getCurrentlyActive());
    //
    //    // INACTIVE_DATE
    //    // Not necessary. It can't be manually modified
    //
    //    // STATUS
    //    target.setStatus(source.getStatus());
    //
    //    // PROC_STATUS
    //    // Not necessary. It can't be manually modified
    //
    //    // PUBLISHER
    //    target.getPublisher().addAll(externalItemMapper.toEntities(source.getPublisher(), target.getPublisher(), ServiceExceptionParameters.OPERATION_PUBLISHER));
    //
    //    // REL_POL_US_AC
    //    target.setRelPolUsAc(internationalStringMapper.toEntity(source.getRelPolUsAc(), target.getRelPolUsAc(), ServiceExceptionParameters.OPERATION_REL_POL_US_AC));
    //
    //    // RELEASE_CALENDAR
    //    target.setReleaseCalendar(source.getReleaseCalendar());
    //
    //    // RELEASE_CALENDAR_ACCESS
    //    target.setReleaseCalendarAccess(source.getReleaseCalendarAccess());
    //
    //    // UPDATE_FREQUENCY
    //    target.getUpdateFrequency().addAll(externalItemMapper.toEntities(source.getUpdateFrequency(), target.getUpdateFrequency(), ServiceExceptionParameters.OPERATION_UPDATE_FREQUENCY));
    //
    //    // CURRENT_INTERNAL_INSTANCE
    //    // Not necessary. Extracted from instances order.
    //
    //    // CURRENT_INSTANCE.
    //    // Not necessary. Extracted from instances order.
    //
    //    // INVENTORY_DATE
    //    // Not necessary. It can't be manually modified.
    //
    //    // REV_POLICY
    //    target.setRevPolicy(internationalStringMapper.toEntity(source.getRevPolicy(), target.getRevPolicy(), ServiceExceptionParameters.OPERATION_REV_POLICY));
    //
    //    // REV_PRACTICE
    //    target.setRevPractice(internationalStringMapper.toEntity(source.getRevPractice(), target.getRevPractice(), ServiceExceptionParameters.OPERATION_REV_PRACTICE));
    //
    //    // CONTACT: Extracted from AppCommonMetadata
    //
    //    // LEGAL_ACTS: Extracted from AppCommonMetadata
    //    target.setSpecificLegalActs(internationalStringMapper.toEntity(source.getSpecificLegalActs(), target.getSpecificLegalActs(), ServiceExceptionParameters.OPERATION_SPECIFIC_LEGAL_ACTS));
    //    // DATA_SHARING: Extracted from AppCommonMetadata
    //    target.setSpecificDataSharing(internationalStringMapper.toEntity(source.getSpecificDataSharing(), target.getSpecificDataSharing(), ServiceExceptionParameters.OPERATION_SPECIFIC_DATA_SHARING));
    //    // CONFIDENCIALITY_POLICY: Extracted from AppCommonMetadata
    //    // CONFIDENCIALITY_DATA_TREATMENT: Extracted from AppCommonMetadata
    //
    //    // COMMENT
    //    target.setComment(internationalStringMapper.toEntity(source.getComment(), target.getComment(), ServiceExceptionParameters.OPERATION_COMMENT));
    //
    //    // NOTES
    //    target.setNotes(internationalStringMapper.toEntity(source.getNotes(), target.getNotes(), ServiceExceptionParameters.OPERATION_NOTES));
    //
    //    // Optimistic locking: Update "update date" attribute to force update of the root entity in order to increase attribute "version"
    //    target.setUpdateDate(Instant.now());
    //
    //    target.setOptLock(source.getOptLock());
    //
    //    return target;
    //}

}
