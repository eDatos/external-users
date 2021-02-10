package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.gobcan.istac.edatos.external.users.core.domain.OfficialityTypeEntity;
import es.gobcan.istac.edatos.external.users.core.repository.OfficialityTypeRepository;
import es.gobcan.istac.edatos.external.users.rest.common.dto.OfficialityTypeDto;

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
