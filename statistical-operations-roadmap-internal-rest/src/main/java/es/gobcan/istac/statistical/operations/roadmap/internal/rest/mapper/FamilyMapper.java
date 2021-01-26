package es.gobcan.istac.statistical.operations.roadmap.internal.rest.mapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.siemac.edatos.core.common.exception.EDatosException;
import org.siemac.edatos.core.common.exception.EDatosExceptionItem;
import org.siemac.edatos.core.common.exception.utils.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.FamilyEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.enumeration.ProcStatusEnum;
import es.gobcan.istac.statistical.operations.roadmap.core.errors.ServiceExceptionParameters;
import es.gobcan.istac.statistical.operations.roadmap.core.errors.ServiceExceptionType;
import es.gobcan.istac.statistical.operations.roadmap.core.repository.FamilyRepository;
import es.gobcan.istac.statistical.operations.roadmap.core.util.StatisticalOperationsValidationUtils;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.dto.FamilyDto;

@Component
public class FamilyMapper {

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private InternationalStringMapper internationalStringMapper;

    public FamilyEntity toEntity(FamilyDto source) {
        if (source == null) {
            return null;
        }

        // If exists, retrieves existing entity. Otherwise, creates new entity
        FamilyEntity target = new FamilyEntity();
        if (source.getId() != null) {
            target = familyRepository.findOne(source.getId());

            // Metadata unmodifiable
            // It's necessary to check that all the metadata that conforms the URN are unmodifibale.
            if (!ProcStatusEnum.DRAFT.equals(target.getProcStatus())) {
                List<EDatosExceptionItem> exceptions = new ArrayList<>();
                StatisticalOperationsValidationUtils.checkMetadataUnmodifiable(target.getCode(), source.getCode(), ServiceExceptionParameters.FAMILY_CODE, exceptions);
                ExceptionUtils.throwIfException(exceptions);
            }
        }

        return toEntity(source, target);
    }

    private FamilyEntity toEntity(FamilyDto source, FamilyEntity target) {
        if (target == null) {
            throw new EDatosException(ServiceExceptionType.PARAMETER_REQUIRED);
        }

        // CODE
        target.setCode(source.getCode());

        // URN
        // Not necessary. It can't be manually modified

        // TITLE
        target.setTitle(internationalStringMapper.toEntity(source.getTitle(), target.getTitle(), ServiceExceptionParameters.FAMILY_TITLE));

        // ACRONYM
        target.setAcronym(internationalStringMapper.toEntity(source.getAcronym(), target.getAcronym(), ServiceExceptionParameters.FAMILY_ACRONYM));

        // DESCRIPTION
        target.setDescription(internationalStringMapper.toEntity(source.getDescription(), target.getDescription(), ServiceExceptionParameters.FAMILY_DESCRIPTION));

        // SURVEY_CODE
        // Not necessary. It's a relation

        // SURVEY_TITLE
        // Not necessary. It's a relation

        // INTERNAL_INVENTORY_DATE
        // Not necessary. It can't be manually modified

        // PROC STATUS
        // Not necessary. It can't be manually modified

        // INVENTORY_DATE
        // Not necessary. It can't be manually modified

        target.setUpdateDate(Instant.now());

        target.setOptLock(source.getOptLock());

        return target;
    }

    public FamilyDto toDto(FamilyEntity source) {
        if (source == null) {
            return null;
        }

        FamilyDto target = new FamilyDto();
        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setCreatedBy(source.getCreatedBy());
        target.setCreatedDate(source.getCreatedDate());
        target.setLastModifiedBy(source.getLastModifiedBy());
        target.setLastModifiedDate(source.getLastModifiedDate());

        // CODE
        target.setCode(source.getCode());

        // URN
        target.setUrn(source.getUrn());

        // TITLE
        target.setTitle(internationalStringMapper.toDto(source.getTitle()));

        // TITLE_ALTERNATIVE
        target.setAcronym(internationalStringMapper.toDto(source.getAcronym()));

        // DESCRIPTION
        target.setDescription(internationalStringMapper.toDto(source.getDescription()));

        // INTERNAL_INVENTORY_DATE
        target.setInternalInventoryDate(source.getInternalInventoryDate());

        // PROC STATUS
        target.setProcStatus(source.getProcStatus());

        // INVENTORY_DATE
        target.setInventoryDate(source.getInventoryDate());

        target.setOptLock(source.getOptLock());

        return target;
    }
}
