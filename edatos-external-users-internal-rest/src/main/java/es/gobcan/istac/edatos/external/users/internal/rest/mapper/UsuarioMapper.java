package es.gobcan.istac.edatos.external.users.internal.rest.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import es.gobcan.istac.edatos.external.users.core.domain.UsuarioEntity;
import es.gobcan.istac.edatos.external.users.core.repository.UsuarioRepository;
import es.gobcan.istac.edatos.external.users.internal.rest.dto.UsuarioDto;
import es.gobcan.istac.edatos.external.users.internal.rest.vm.ManagedUserVM;

@Mapper(componentModel = "spring", uses = {})
public abstract class UsuarioMapper implements EntityMapper<UsuarioDto, UsuarioEntity> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioEntity fromId(Long id) {
        if (id == null) {
            return null;
        }
        return usuarioRepository.findOne(id);
    }

    public UsuarioEntity toEntity(UsuarioDto dto) {
        if (dto == null) {
            return null;
        }

        UsuarioEntity entity = dto.getId() != null ? fromId(dto.getId()) : new UsuarioEntity();

        entity.setLogin(dto.getLogin());
        entity.setNombre(dto.getNombre());
        entity.setApellido1(dto.getApellido1());
        entity.setApellido2(dto.getApellido2());
        entity.setEmail(dto.getEmail());
        entity.setRoles(dto.getRoles());
        entity.setOptLock(dto.getOptLock());

        return entity;
    }

    public UsuarioEntity updateFromDto(UsuarioEntity user, ManagedUserVM userVM) {
        if (user == null) {
            return null;
        }
        user.setLogin(userVM.getLogin());
        user.setNombre(userVM.getNombre());
        user.setApellido1(userVM.getApellido1());
        user.setApellido2(userVM.getApellido2());
        user.setEmail(userVM.getEmail());
        user.setOptLock(userVM.getOptLock());
        user.setRoles(userVM.getRoles());
        return user;
    }

}