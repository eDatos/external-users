package es.gobcan.istac.edatos.external.users.core.domain;

import es.gobcan.istac.edatos.external.users.core.config.Constants;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Gender;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Language;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Role;
import es.gobcan.istac.edatos.external.users.core.domain.interfaces.AbstractVersionedAndAuditingAndLogicalDeletionEntity;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_external_users")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExternalUserEntity extends AbstractVersionedAndAuditingAndLogicalDeletionEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_external_users")
    @SequenceGenerator(name = "seq_tb_external_users", sequenceName = "seq_tb_external_users", allocationSize = 50, initialValue = 1)
    private Long id;

    @Column(name = "name")
    private String nombre;

    @Column(name = "surname1")
    private String apellido1;

    @Column(name = "surname2")
    private String apellido2;

    @NotNull
    @Email
    @Size(min = 3)
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language;

    private String phoneNumber;

    @Size(min = 6)
    private String password;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return this.getClass().getName() + " (id = " + getId() + ", email" + getEmail() + ", Nombre = " + getNombre() + ")";
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
