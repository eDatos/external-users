package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.io.IOException;
import java.sql.Blob;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ValidationException;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.gobcan.istac.edatos.external.users.core.domain.FicheroEntity;
import es.gobcan.istac.edatos.external.users.core.repository.FicheroRepository;
import es.gobcan.istac.edatos.external.users.core.service.FicheroService;

@Service
public class FicheroServiceImpl implements FicheroService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    FicheroRepository ficheroRepository;

    @Override
    public FicheroEntity create(MultipartFile multipartFile) {
        Blob datos;

        try {
            datos = Hibernate.getLobCreator((Session) entityManager.getDelegate()).createBlob(multipartFile.getInputStream(), multipartFile.getSize());
        } catch (IOException e) {
            throw new ValidationException(e);
        }

        FicheroEntity fichero = new FicheroEntity();
        fichero.setNombre(multipartFile.getOriginalFilename());
        fichero.setDatos(datos);
        fichero.setLongitud(multipartFile.getSize());
        fichero.setTipoContenido(multipartFile.getContentType());
        return ficheroRepository.saveAndFlush(fichero);
    }

    @Override
    public FicheroEntity get(Long id) {
        return ficheroRepository.findOne(id);
    }

    @Override
    public void delete(Long id) {
        ficheroRepository.delete(id);
    }

    @Override
    public void deleteHuerfanos() {
        ficheroRepository.deleteFicherosHuerfanos();
    }
}
