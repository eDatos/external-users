package es.gobcan.istac.statistical.operations.roadmap.internal.rest.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.FicheroEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.repository.FicheroRepository;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.dto.FicheroDto;

@Mapper(componentModel = "spring", uses = {})
public abstract class FicheroMapper implements EntityMapper<FicheroDto, FicheroEntity> {

    @Autowired
    FicheroRepository ficheroRepository;

    public FicheroEntity fromId(Long id) {
        return id != null ? ficheroRepository.findOne(id) : null;
    }

    public FicheroEntity toEntity(FicheroDto dto) {
        if (dto == null) {
            return null;
        }

        return dto.getId() != null ? fromId(dto.getId()) : new FicheroEntity();
    }
}
