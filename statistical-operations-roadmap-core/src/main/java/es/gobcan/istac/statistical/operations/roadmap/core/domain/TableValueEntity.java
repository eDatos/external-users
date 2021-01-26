package es.gobcan.istac.statistical.operations.roadmap.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.vo.InternationalStringVO;

@TypeDef(name = "json", typeClass = JsonStringType.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@MappedSuperclass
public abstract class TableValueEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_generator_name")
    @Column(name = "id")
    private Long id;

    @Column(name = "code", nullable = false, length = 255)
    @NotNull
    private String code;

    @Type(type = "json")
    @Column(name = "title", columnDefinition = "json", nullable = false)
    @NotNull
    private InternationalStringVO title = new InternationalStringVO();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public InternationalStringVO getTitle() {
        return title;
    }

    public void setTitle(InternationalStringVO title) {
        this.title = title;
    }

    public final class Properties {

        private Properties() {
        }

        public static final String CODE = "code";
    }
}
