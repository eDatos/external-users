package es.gobcan.istac.edatos.external.users.internal.rest.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.gobcan.istac.edatos.external.users.core.domain.CollMethodEntity;
import es.gobcan.istac.edatos.external.users.core.repository.CollMethodRepository;
import es.gobcan.istac.edatos.external.users.internal.rest.dto.CollMethodDto;

@Component
public class CollMethodMapper {

    @Autowired
    private InternationalStringMapper internationalStringMapper;

    @Autowired
    private CollMethodRepository collMethodRepository;

    public CollMethodDto toDto(CollMethodEntity source) {
        if (source == null) {
            return null;
        }

        CollMethodDto target = new CollMethodDto();
        target.setId(source.getId());
        target.setUuid(source.getUuid());

        // DESCRIPTION
        target.setDescription(internationalStringMapper.toDto(source.getDescription()));

        target.setOptLock(source.getOptLock());

        return target;
    }

    public CollMethodEntity toEntity(CollMethodDto source) {
        if (source == null) {
            return null;
        }

        return collMethodRepository.findOne(source.getId());
    }
}
