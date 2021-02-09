package es.gobcan.istac.edatos.external.users.core.domain;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import es.gobcan.istac.edatos.external.users.core.domain.interfaces.AbstractVersionedAndAuditingEntity;

@Entity
@Table(name = "tb_filter", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_fk", "permalink"})})
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class FilterEntity extends AbstractVersionedAndAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_filter")
    @SequenceGenerator(name = "seq_tb_filter", sequenceName = "seq_tb_filter", allocationSize = 50, initialValue = 1)
    private Long id;

    @NotNull
    @Column(nullable = false)
    @Size(min = 1)
    @NotBlank
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_fk", nullable = false)
    private UsuarioEntity user;

    @URL
    @NotNull
    @Column(nullable = false)
    private String permalink;

    @NotNull
    @Column(nullable = false)
    private Instant lastAccessDate = Instant.now();

    @Column(length = 4000)
    private String notes;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioEntity getUser() {
        return user;
    }

    public void setUser(UsuarioEntity user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Instant getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(Instant lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }
}
