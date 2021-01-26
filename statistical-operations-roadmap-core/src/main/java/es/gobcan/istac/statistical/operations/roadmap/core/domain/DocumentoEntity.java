package es.gobcan.istac.statistical.operations.roadmap.core.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.interfaces.AbstractVersionedAndAuditingAndLogicalDeletionEntity;

@Entity
@Table(name = "tb_documentos")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class DocumentoEntity extends AbstractVersionedAndAuditingAndLogicalDeletionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_documentos")
    @SequenceGenerator(name = "seq_tb_documentos", sequenceName = "seq_tb_documentos", allocationSize = 50, initialValue = 1)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "fichero_fk")
    private FicheroEntity fichero;

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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public FicheroEntity getFichero() {
        return fichero;
    }

    public void setFichero(FicheroEntity fichero) {
        this.fichero = fichero;
    }

    @Override
    public String toString() {
        return "Documento (id = " + id + ", Nombre = " + nombre + ")";
    }
}
