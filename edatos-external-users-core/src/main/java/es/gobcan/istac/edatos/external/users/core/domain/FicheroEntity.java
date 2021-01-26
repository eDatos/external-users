package es.gobcan.istac.edatos.external.users.core.domain;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "tb_ficheros")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class FicheroEntity implements Serializable {

    private static final long serialVersionUID = -2461524079087639090L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_ficheros")
    @SequenceGenerator(name = "seq_tb_ficheros", sequenceName = "seq_tb_ficheros", allocationSize = 50, initialValue = 1)
    private Long id;

    @NotNull
    @Lob
    @Column(name = "datos", nullable = false)
    private Blob datos;

    @NotNull
    @Column(name = "tipo_contenido", nullable = false)
    private String tipoContenido;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "longitud")
    private Long longitud;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Blob getDatos() {
        return datos;
    }

    public void setDatos(Blob datos) {
        this.datos = datos;
    }

    public String getTipoContenido() {
        return tipoContenido;
    }

    public void setTipoContenido(String tipoContenido) {
        this.tipoContenido = tipoContenido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getLongitud() {
        return longitud;
    }

    public void setLongitud(Long longitud) {
        this.longitud = longitud;
    }
}
