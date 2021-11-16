package es.gobcan.istac.edatos.external.users.core.repository;

public interface DetachEntityRepositoryCustom<E> {
    
    void detachEntity(E entity);
}
