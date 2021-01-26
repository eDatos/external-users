package es.gobcan.istac.statistical.operations.roadmap.internal.rest.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.OfficialityTypeEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.repository.OfficialityTypeRepository;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.dto.OfficialityTypeDto;

@Component
public class OfficialityTypeMapper {

    @Autowired
    private InternationalStringMapper internationalStringMapper;

    @Autowired
    private OfficialityTypeRepository officialityTypeRepository;

    public OfficialityTypeDto toDto(OfficialityTypeEntity source) {
        if (source == null) {
            return null;
        }

        OfficialityTypeDto target = new OfficialityTypeDto();
        target.setId(source.getId());
        target.setUuid(source.getUuid());

        // DESCRIPTION
        target.setDescription(internationalStringMapper.toDto(source.getDescription()));

        target.setOptLock(source.getOptLock());

        return target;
    }

    public OfficialityTypeEntity toEntity(OfficialityTypeDto source) {
        if (source == null) {
            return null;
        }

        return officialityTypeRepository.findOne(source.getId());
    }
}
