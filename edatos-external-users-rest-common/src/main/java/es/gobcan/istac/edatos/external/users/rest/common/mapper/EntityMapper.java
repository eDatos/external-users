package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import java.util.Collection;

public interface EntityMapper<D, E> {

    public D toDto(E entity);

    public E toEntity(D dto);

    public Collection<E> toEntities(Collection<D> dtos);

    public Collection<D> toDtos(Collection<E> entities);
}
