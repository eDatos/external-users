package es.gobcan.istac.edatos.external.users.core.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.siemac.edatos.core.common.enume.TypeExternalArtefactsEnum;
import org.siemac.edatos.core.common.enume.converter.TypeExternalArtefactsEnumConverter;

/**
 * Entity for store information about ExternalItems.
 */
@Entity
@Table(name = "tb_external_items")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class ExternalItemEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_external_items")
    @SequenceGenerator(name = "seq_tb_external_items", sequenceName = "seq_tb_external_items", allocationSize = 50, initialValue = 1)
    private Long id;

    @Column(name = "code", nullable = false, length = 255)
    @NotNull
    private String code;

    @Column(name = "code_nested", length = 255)
    private String codeNested;

    @Column(name = "uri", nullable = false, length = 4000)
    @Length(max = 4000)
    @NotNull
    private String uri;

    @Column(name = "urn", length = 4000)
    @Length(max = 4000)
    private String urn;

    @Column(name = "urn_provider", length = 4000)
    @Length(max = 4000)
    private String urnProvider;

    @Column(name = "management_app_url", length = 4000)
    @Length(max = 4000)
    private String managementAppUrl;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "type", nullable = false)
    @Convert(converter = TypeExternalArtefactsEnumConverter.class)
    @NotNull
    private TypeExternalArtefactsEnum type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "title_fk")
    private InternationalStringEntity title;

    public Long getId() {
        return id;
    }

    /**
     * The id is not intended to be changed or assigned manually, but
     * for test purpose it is allowed to assign the id.
     */
    protected void setId(Long id) {
        if ((this.id != null) && !this.id.equals(id)) {
            throw new IllegalArgumentException("Not allowed to change the id property.");
        }
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeNested() {
        return codeNested;
    }

    public void setCodeNested(String codeNested) {
        this.codeNested = codeNested;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUrn() {
        return urn;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    public String getUrnProvider() {
        return urnProvider;
    }

    public void setUrnProvider(String urnProvider) {
        this.urnProvider = urnProvider;
    }

    public String getManagementAppUrl() {
        return managementAppUrl;
    }

    public void setManagementAppUrl(String managementAppUrl) {
        this.managementAppUrl = managementAppUrl;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public TypeExternalArtefactsEnum getType() {
        return type;
    }

    public void setType(TypeExternalArtefactsEnum type) {
        this.type = type;
    }

    public InternationalStringEntity getTitle() {
        return title;
    }

    public void setTitle(InternationalStringEntity title) {
        this.title = title;
    }

    /**
     * This method is used by equals and hashCode.
     * 
     * @return {@link #getId}
     */
    public Object getKey() {
        return getId();
    }

    public final class Properties {

        private Properties() {
        }

        public static final String URN = "urn";
    }
}
