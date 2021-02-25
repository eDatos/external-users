package es.gobcan.istac.edatos.external.users.rest.common.dto;

import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Gender;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Language;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Role;
import es.gobcan.istac.edatos.external.users.rest.common.dto.interfaces.AbstractVersionedAndAuditingAndLogicalDeletionDto;
import es.gobcan.istac.edatos.external.users.rest.common.dto.interfaces.Identifiable;

import java.io.Serializable;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class ExternalUserDto extends AbstractVersionedAndAuditingAndLogicalDeletionDto implements Serializable, Identifiable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String email;

    private String nombre;
    private String apellido1;
    private String apellido2;

    private Gender gender;
    private Language language;
    private String phoneNumber;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void updateFrom(ExternalUserDto source) {
        this.id = source.getId();
        this.nombre = source.getNombre();
        this.apellido1 = source.getApellido1();
        this.apellido2 = source.getApellido2();
        this.email = source.getEmail();
        this.gender = source.getGender();
        this.language = source.getLanguage();
        this.phoneNumber = source.getPhoneNumber();
        this.password = source.getPassword();
        this.setOptLock(source.getOptLock());
        this.setCreatedDate(source.getCreatedDate());
        this.setCreatedBy(source.getCreatedBy());
    }

    @Override
    public String toString() {
        return "UsuarioDto [id=" + id + ", email=" + email + ", nombre=" + nombre + "]";
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
