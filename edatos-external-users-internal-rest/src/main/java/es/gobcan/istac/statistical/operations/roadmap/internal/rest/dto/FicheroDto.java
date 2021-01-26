package es.gobcan.istac.statistical.operations.roadmap.internal.rest.dto;

import java.io.Serializable;

public class FicheroDto implements Serializable {

    private static final long serialVersionUID = 378729243990053776L;

    private Long id;
    private String nombre;
    private String tipoContenido;
    private Long longitud;

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

    public String getTipoContenido() {
        return tipoContenido;
    }

    public void setTipoContenido(String tipoContenido) {
        this.tipoContenido = tipoContenido;
    }

    public Long getLongitud() {
        return longitud;
    }

    public void setLongitud(Long longitud) {
        this.longitud = longitud;
    }
}
