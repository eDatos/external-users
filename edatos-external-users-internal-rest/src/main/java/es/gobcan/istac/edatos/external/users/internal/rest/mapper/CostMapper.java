package es.gobcan.istac.edatos.external.users.internal.rest.mapper;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.gobcan.istac.edatos.external.users.core.domain.CostEntity;
import es.gobcan.istac.edatos.external.users.core.repository.CostRepository;
import es.gobcan.istac.edatos.external.users.internal.rest.dto.CostDto;

@Component
public class CostMapper {

    @Autowired
    private InternationalStringMapper internationalStringMapper;

    @Autowired
    private CostRepository costRepository;

    public Set<CostDto> toDtos(Set<CostEntity> sources) {
        Set<CostDto> targets = new HashSet<>();
        if (sources != null) {
            for (CostEntity source : sources) {
                targets.add(toDto(source));
            }
        }
        return targets;
    }

    public CostDto toDto(CostEntity source) {
        if (source == null) {
            return null;
        }

        CostDto target = new CostDto();
        target.setId(source.getId());
        target.setUuid(source.getUuid());

        // DESCRIPTION
        target.setDescription(internationalStringMapper.toDto(source.getDescription()));

        target.setOptLock(source.getOptLock());

        return target;
    }

    public CostEntity toEntity(CostDto source) {
        if (source == null) {
            return null;
        }

        return costRepository.findOne(source.getId());
    }

    public Set<CostEntity> toEntities(Set<CostDto> sources) {
        Set<CostEntity> targets = new HashSet<>();
        if (sources != null) {
            for (CostDto source : sources) {
                targets.add(toEntity(source));
            }
        }
        return targets;
    }
}
