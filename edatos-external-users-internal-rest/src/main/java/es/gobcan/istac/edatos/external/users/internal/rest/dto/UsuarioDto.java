package es.gobcan.istac.edatos.external.users.internal.rest.dto;

import java.io.Serializable;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Gender;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Language;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Role;

public class UsuarioDto extends AbstractVersionedAndAuditingAndLogicalDeletionDto implements Serializable, Identifiable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String login;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String email;
    private Gender gender;
    private Language language;
    private String organization;
    private String phoneNumber;
    private SortedSet<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> set) {
        if (set == null) {
            this.roles = new TreeSet<>();
        } else {
            this.roles = new TreeSet<>(set);
        }
    }

    public void updateFrom(UsuarioDto source) {
        this.id = source.getId();
        this.login = source.getLogin();
        this.nombre = source.getNombre();
        this.apellido1 = source.getApellido1();
        this.apellido2 = source.getApellido2();
        this.email = source.getEmail();
        this.roles = new TreeSet<>(source.getRoles());
        this.gender = source.getGender();
        this.language = source.getLanguage();
        this.organization = source.getOrganization();
        this.phoneNumber = source.getPhoneNumber();
        this.setOptLock(source.getOptLock());
        this.setCreatedDate(source.getCreatedDate());
        this.setCreatedBy(source.getCreatedBy());
    }

    @Override
    public String toString() {
        return "UsuarioDto [id=" + id + ", login=" + login + ", nombre=" + nombre + ", email=" + email + "]";
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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
