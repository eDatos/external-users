package es.gobcan.istac.statistical.operations.roadmap.core.domain;

import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.enumeration.ProcStatusEnum;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.vo.InternationalStringVO;

@Entity
@Table(name = "tb_needs", uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NeedEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_needs")
    @SequenceGenerator(name = "seq_tb_needs", sequenceName = "seq_tb_needs", allocationSize = 50, initialValue = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", nullable = false, length = 255)
    @Length(max = 255)
    @NotNull
    private String code;

    @Column(name = "urn", nullable = false, length = 4000)
    @Length(max = 4000)
    @NotNull
    private String urn;

    @Type(type = "json")
    @Column(name = "title", columnDefinition = "json", nullable = false)
    @NotNull
    private InternationalStringVO title = new InternationalStringVO();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_fk")
    private NeedTypeEntity type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "state_fk")
    private NeedStateEntity state;

    @Column(name = "identification_date")
    private Instant identificationDate;

    @Column(name = "proc_status", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProcStatusEnum procStatus;

    @Column(name = "uuid", nullable = false, length = 36, unique = true)
    private String uuid;

    @Override
    public Long getId() {
        return this.id;
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

    public String getUrn() {
        return urn;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    public InternationalStringVO getTitle() {
        return title;
    }

    public void setTitle(InternationalStringVO title) {
        this.title = title;
    }

    public NeedTypeEntity getType() {
        return type;
    }

    public void setType(NeedTypeEntity type) {
        this.type = type;
    }

    public NeedStateEntity getState() {
        return state;
    }

    public void setState(NeedStateEntity state) {
        this.state = state;
    }

    public Instant getIdentificationDate() {
        return identificationDate;
    }

    public void setIdentificationDate(Instant identificationDate) {
        this.identificationDate = identificationDate;
    }

    public ProcStatusEnum getProcStatus() {
        return procStatus;
    }

    public void setProcStatus(ProcStatusEnum procStatus) {
        this.procStatus = procStatus;
    }

    public String getUuid() {
        // lazy init of UUID
        if (uuid == null) {
            uuid = java.util.UUID.randomUUID().toString();
        }
        return uuid;
    }

    @PrePersist
    protected void prePersist() {
        getUuid();
    }

    /**
     * This method is used by equals and hashCode.
     * 
     * @return {{@link #getUuid}
     */
    public Object getKey() {
        return getUuid();
    }

    public final class Properties {

        private Properties() {
        }

        public static final String CODE = "code";
        public static final String URN = "urn";
        public static final String TITLE = "title";
        public static final String TYPE = "type";
        public static final String STATE = "state";
        public static final String IDENTIFICATION_DATE = "identificationDate";
        public static final String PROC_STATUS = "procStatus";
    }

}
