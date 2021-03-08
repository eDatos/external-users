package es.gobcan.istac.edatos.external.users.core.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import es.gobcan.istac.edatos.external.users.core.domain.FavoriteEntity;
import es.gobcan.istac.edatos.external.users.core.domain.UsuarioEntity;

public interface FavoriteService {

    FavoriteEntity create(FavoriteEntity filter);

    FavoriteEntity update(FavoriteEntity filter);

    FavoriteEntity find(Long id);

    List<FavoriteEntity> findAll();

    List<FavoriteEntity> findAllByUser(UsuarioEntity user);

    Page<FavoriteEntity> find(String query, Pageable pageable);

    List<FavoriteEntity> find(String query, Sort sort);

    void delete(FavoriteEntity filter);
}
