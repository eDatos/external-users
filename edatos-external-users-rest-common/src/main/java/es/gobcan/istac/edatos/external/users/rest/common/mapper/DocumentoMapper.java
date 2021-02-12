package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import java.util.Set;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import es.gobcan.istac.edatos.external.users.core.domain.DocumentoEntity;
import es.gobcan.istac.edatos.external.users.core.repository.DocumentoRepository;
import es.gobcan.istac.edatos.external.users.rest.common.dto.DocumentoDto;

@Mapper(componentModel = "spring", uses = {})
public abstract class DocumentoMapper implements EntityMapper<DocumentoDto, DocumentoEntity> {

    @Autowired
    DocumentoRepository documentoRepository;

    abstract Set<DocumentoEntity> toEntities(Set<DocumentoDto> dtos);

    abstract Set<DocumentoDto> toDtos(Set<DocumentoEntity> entities);

    public DocumentoEntity fromId(Long id) {
        if (id == null) {
            return null;
        }

        return documentoRepository.findOne(id);
    }

    public DocumentoEntity toEntity(DocumentoDto dto) {
        if (dto == null) {
            return null;
        }

        DocumentoEntity entity = dto.getId() != null ? fromId(dto.getId()) : new DocumentoEntity();

        entity.setNombre(dto.getNombre());
        return entity;
    }
}
