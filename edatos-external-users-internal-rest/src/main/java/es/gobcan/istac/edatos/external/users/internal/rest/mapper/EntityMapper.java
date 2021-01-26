package es.gobcan.istac.edatos.external.users.internal.rest.mapper;

import java.util.List;

public interface EntityMapper<D, E> {

    public D toDto(E entity);

    public List<E> toEntities(List<D> dtos);

    public List<D> toDtos(List<E> entities);
}
