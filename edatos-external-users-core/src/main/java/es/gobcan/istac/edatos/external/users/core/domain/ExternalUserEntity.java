package es.gobcan.istac.edatos.external.users.core.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;

import es.gobcan.istac.edatos.external.users.core.domain.enumeration.ExternalUserRole;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Language;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Treatment;
import es.gobcan.istac.edatos.external.users.core.domain.interfaces.AbstractVersionedAndAuditingAndLogicalDeletionEntity;

@Entity
@Table(name = "tb_external_users")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExternalUserEntity extends AbstractVersionedAndAuditingAndLogicalDeletionEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_external_users")
    @SequenceGenerator(name = "seq_tb_external_users", sequenceName = "seq_tb_external_users", allocationSize = 50, initialValue = 1)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false)
    private String surname1;

    @Size(max = 255)
    private String surname2;

    @NotNull
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Treatment treatment;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language;

    @Size(max = 255)
    private String phoneNumber;

    @Size(max = 255)
    private String organism;

    @NotNull
    @Column(nullable = false)
    private boolean emailNotificationsEnabled = true;

    @Size(min = 6)
    @NotNull
    @Column(nullable = false)
    private String password;

    @ElementCollection(targetClass = ExternalUserRole.class)
    @JoinTable(name = "tb_external_users_roles", joinColumns = @JoinColumn(name = "external_user_fk", referencedColumnName = "id"))
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<ExternalUserRole> roles = new HashSet<>();

    @NotNull
    @OneToMany(mappedBy = "externalUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final Set<FavoriteEntity> favorites = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String firstName) {
        this.name = firstName;
    }

    public String getSurname1() {
        return surname1;
    }

    public void setSurname1(String lastName) {
        this.surname1 = lastName;
    }

    public String getSurname2() {
        return surname2;
    }

    public void setSurname2(String lastName) {
        this.surname2 = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
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

    public String getOrganism() {
        return organism;
    }

    public void setOrganism(String organism) {
        this.organism = organism;
    }

    public boolean isEmailNotificationsEnabled() {
        return emailNotificationsEnabled;
    }

    public void setEmailNotificationsEnabled(boolean emailNotificationsEnabled) {
        this.emailNotificationsEnabled = emailNotificationsEnabled;
    }

    public Set<ExternalUserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<ExternalUserRole> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " (id = " + getId() + ", email" + getEmail() + ", Nombre = " + getName() + ")";
    }

    public Set<FavoriteEntity> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<FavoriteEntity> favorites) {
        for (FavoriteEntity favorite : favorites) {
            favorite.setExternalUser(this);
        }
        this.favorites.clear();
        this.favorites.addAll(favorites);
    }

    public void addFavorite(FavoriteEntity favorite) {
        favorite.setExternalUser(this);
        this.favorites.add(favorite);
    }

    public void removeFavorite(FavoriteEntity favorite) {
        favorite.setExternalUser(null);
        this.favorites.remove(favorite);
    }
}
