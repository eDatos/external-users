package es.gobcan.istac.statistical.operations.roadmap.internal.rest.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceTypeEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.repository.InstanceTypeRepository;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.dto.InstanceTypeDto;

@Component
public class InstanceTypeMapper {

    @Autowired
    private InternationalStringMapper internationalStringMapper;

    @Autowired
    private InstanceTypeRepository instanceTypeRepository;

    public InstanceTypeDto toDto(InstanceTypeEntity source) {
        if (source == null) {
            return null;
        }

        InstanceTypeDto target = new InstanceTypeDto();
        target.setId(source.getId());
        target.setUuid(source.getUuid());

        // DESCRIPTION
        target.setDescription(internationalStringMapper.toDto(source.getDescription()));

        target.setOptLock(source.getOptLock());

        return target;
    }

    public InstanceTypeEntity toEntity(InstanceTypeDto source) {
        if (source == null) {
            return null;
        }

        return instanceTypeRepository.findOne(source.getId());
    }
}
