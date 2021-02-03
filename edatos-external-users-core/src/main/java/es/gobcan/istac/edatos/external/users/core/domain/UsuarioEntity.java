package es.gobcan.istac.edatos.external.users.core.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;

import es.gobcan.istac.edatos.external.users.core.config.Constants;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Gender;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Language;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Rol;
import es.gobcan.istac.edatos.external.users.core.domain.interfaces.AbstractVersionedAndAuditingAndLogicalDeletionEntity;

@Entity
@Table(name = "tb_usuarios")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UsuarioEntity extends AbstractVersionedAndAuditingAndLogicalDeletionEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_usuarios")
    @SequenceGenerator(name = "seq_tb_usuarios", sequenceName = "seq_tb_usuarios", allocationSize = 50, initialValue = 1)
    private Long id;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1)
    @Column(unique = true, nullable = false)
    private String login;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido1")
    private String apellido1;

    @Column(name = "apellido2")
    private String apellido2;

    @Email
    @Size(min = 3)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language;

    @Size(max = 255)
    private String organization;

    private String phoneNumber;

    @ElementCollection(targetClass = Rol.class)
    @JoinTable(name = "tb_usuarios_roles", joinColumns = @JoinColumn(name = "usuario_fk", referencedColumnName = "id"))
    @Column(name = "rol", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Rol> roles = new HashSet<>();

    @Override
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
        this.login = StringUtils.lowerCase(login);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String firstName) {
        this.nombre = firstName;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String lastName) {
        this.apellido1 = lastName;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String lastName) {
        this.apellido2 = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " (id = " + getId() + ", login" + getLogin() + ", Nombre = " + getNombre() + ", email" + getEmail() + ")";
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
