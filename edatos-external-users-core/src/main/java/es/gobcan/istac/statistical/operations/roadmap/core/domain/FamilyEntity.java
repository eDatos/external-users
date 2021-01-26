package es.gobcan.istac.statistical.operations.roadmap.core.domain;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.enumeration.ProcStatusEnum;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.interfaces.AbstractVersionedAndAuditingEntity;

@Entity
@Table(name = "tb_families", uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class FamilyEntity extends AbstractVersionedAndAuditingEntity {

    private static final long serialVersionUID = 7203190836494327022L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_families")
    @SequenceGenerator(name = "seq_tb_families", sequenceName = "seq_tb_families", allocationSize = 50, initialValue = 1)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false, length = 255)
    private String code;

    @NotNull
    @Column(name = "urn", nullable = false, length = 4000)
    @Length(max = 4000)
    private String urn;

    @Column(name = "internal_inventory_date")
    private Instant internalInventoryDate;

    @Column(name = "inventory_date")
    private Instant inventoryDate;

    @Column(name = "update_date")
    private Instant updateDate;

    @Column(name = "uuid", nullable = false, length = 36, unique = true)
    private String uuid;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "title_fk", foreignKey = @ForeignKey(name = "FK_TB_FAMILIES_TITLE_FK"))
    @NotNull
    private InternationalStringEntity title;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "acronym_fk", foreignKey = @ForeignKey(name = "FK_TB_FAMILIES_ACRONYM_FK"))
    private InternationalStringEntity acronym;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "description_fk", foreignKey = @ForeignKey(name = "FK_TB_FAMILIES_DESCRIPTION_FK"))
    private InternationalStringEntity description;

    @Column(name = "proc_status", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProcStatusEnum procStatus;

    @ManyToMany()
    @JoinTable(name = "TB_FAMILIES_OPERATIONS", joinColumns = @JoinColumn(name = "FAMILY"), inverseJoinColumns = @JoinColumn(name = "OPERATION"), foreignKey = @ForeignKey(name = "FK_TB_FAMILIES_OPERATIONS_FA19"))
    @NotNull
    private Set<OperationEntity> operations = new HashSet<>();

    @Override
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

    /**
     * Semantic identifier
     */
    public String getCode() {
        return code;
    }

    /**
     * Semantic identifier
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * URN
     */
    public String getUrn() {
        return urn;
    }

    /**
     * URN
     */
    public void setUrn(String urn) {
        this.urn = urn;
    }

    /**
     * INTERNAL_INVENTORY_DATE
     */
    public Instant getInternalInventoryDate() {
        return internalInventoryDate;
    }

    /**
     * INTERNAL_INVENTORY_DATE
     */
    public void setInternalInventoryDate(Instant internalInventoryDate) {
        this.internalInventoryDate = internalInventoryDate;
    }

    /**
     * INVENTORY_DATE
     */
    public Instant getInventoryDate() {
        return inventoryDate;
    }

    /**
     * INVENTORY_DATE
     */
    public void setInventoryDate(Instant inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    /**
     * Last update to optimistic locking
     */
    public Instant getUpdateDate() {
        return updateDate;
    }

    /**
     * Last update to optimistic locking
     */
    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This domain object doesn't have a natural key
     * and this random generated identifier is the
     * unique identifier for this domain object.
     */
    public String getUuid() {

        // lazy init of UUID
        if (uuid == null) {
            uuid = java.util.UUID.randomUUID().toString();
        }
        return uuid;
    }

    /**
     * TITLE
     */
    public InternationalStringEntity getTitle() {
        return title;
    }

    /**
     * TITLE
     */
    public void setTitle(InternationalStringEntity title) {
        this.title = title;
    }

    /**
     * TITLE_ALTERNATIVE
     */
    public InternationalStringEntity getAcronym() {
        return acronym;
    }

    /**
     * TITLE_ALTERNATIVE
     */
    public void setAcronym(InternationalStringEntity acronym) {
        this.acronym = acronym;
    }

    /**
     * DESCRIPTION
     */
    public InternationalStringEntity getDescription() {
        return description;
    }

    /**
     * DESCRIPTION
     */
    public void setDescription(InternationalStringEntity description) {
        this.description = description;
    }

    /**
     * PROC_STATUS
     */
    public ProcStatusEnum getProcStatus() {
        return procStatus;
    }

    /**
     * PROC_STATUS
     */
    public void setProcStatus(ProcStatusEnum procStatus) {
        this.procStatus = procStatus;
    }

    /**
     * Related statistical operations
     */
    public Set<OperationEntity> getOperations() {
        return operations;
    }

    /**
     * Adds an object to the bidirectional many-to-many
     * association in both ends.
     * It is added the collection {@link #getOperations}
     * at this side and to the collection
     * {@link OperationEntity#getFamilies}
     * at the opposite side.
     */
    public void addOperation(OperationEntity operationElement) {
        getOperations().add(operationElement);
        operationElement.getFamilies().add((FamilyEntity) this);
    }

    /**
     * Removes an object from the bidirectional many-to-many
     * association in both ends.
     * It is removed from the collection {@link #getOperations}
     * at this side and from the collection
     * {@link OperationEntity#getFamilies}
     * at the opposite side.
     */
    public void removeOperation(OperationEntity operationElement) {
        getOperations().remove(operationElement);
        operationElement.getFamilies().remove((FamilyEntity) this);
    }

    /**
     * Removes all object from the bidirectional
     * many-to-many association in both ends.
     * All elements are removed from the collection {@link #getOperations}
     * at this side and from the collection
     * {@link OperationEntity#getFamilies}
     * at the opposite side.
     */
    public void removeAllOperations() {
        for (OperationEntity d : getOperations()) {
            d.getFamilies().remove((FamilyEntity) this);
        }
        getOperations().clear();

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
        public static final String TITLE = "title";
        public static final String ACRONYM = "acronym";
        public static final String DESCRIPTION = "description";
        public static final String URN = "urn";
        public static final String PROC_STATUS = "procStatus";
        public static final String INTERNAL_INVENTORY_DATE = "internalInventoryDate";
        public static final String INVENTORY_DATE = "inventoryDate";
        public static final String OPERATIONS = "operations";
    }
}
