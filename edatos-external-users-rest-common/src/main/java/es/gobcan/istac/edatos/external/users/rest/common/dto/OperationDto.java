package es.gobcan.istac.edatos.external.users.rest.common.dto;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.siemac.edatos.core.common.dto.AuditableDto;
import org.siemac.edatos.core.common.dto.ExternalItemDto;
import org.siemac.edatos.core.common.dto.InternationalStringDto;

import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity.StatusEnum;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.ProcStatusEnum;

public class OperationDto extends AuditableDto implements Identifiable {

    private static final long serialVersionUID = 1L;

    private String code;
    private String urn;
    private Boolean indicatorSystem;
    private Instant internalInventoryDate;
    private Boolean currentlyActive;
    private Boolean releaseCalendar;
    private String releaseCalendarAccess;
    private Instant inventoryDate;
    private ExternalItemDto commonMetadata;
    private InternationalStringDto title;
    private InternationalStringDto acronym;
    private ExternalItemDto subjectArea;
    private InternationalStringDto objective;
    private InternationalStringDto description;
    private SurveyTypeDto surveyType;
    private OfficialityTypeDto officialityType;
    private ProcStatusEnum procStatus;
    private StatusEnum status;
    private InternationalStringDto relPolUsAc;
    private InstanceBaseDto currentInstance;
    private InstanceBaseDto currentInternalInstance;
    private InternationalStringDto revPolicy;
    private InternationalStringDto revPractice;
    private InternationalStringDto specificLegalActs;
    private InternationalStringDto specificDataSharing;
    private InternationalStringDto comment;
    private InternationalStringDto notes;
    private Set<ExternalItemDto> secondarySubjectAreas = new HashSet<>();
    private Set<ExternalItemDto> producer = new HashSet<>();
    private Set<ExternalItemDto> regionalResponsible = new HashSet<>();
    private Set<ExternalItemDto> regionalContributor = new HashSet<>();
    private Set<ExternalItemDto> publisher = new HashSet<>();
    private Set<ExternalItemDto> updateFrequency = new HashSet<>();

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

    public Boolean getIndicatorSystem() {
        return indicatorSystem;
    }

    public void setIndicatorSystem(Boolean indicatorSystem) {
        this.indicatorSystem = indicatorSystem;
    }

    public Instant getInternalInventoryDate() {
        return internalInventoryDate;
    }

    public void setInternalInventoryDate(Instant internalInventoryDate) {
        this.internalInventoryDate = internalInventoryDate;
    }

    public Boolean getCurrentlyActive() {
        return currentlyActive;
    }

    public void setCurrentlyActive(Boolean currentlyActive) {
        this.currentlyActive = currentlyActive;
    }

    public Boolean getReleaseCalendar() {
        return releaseCalendar;
    }

    public void setReleaseCalendar(Boolean releaseCalendar) {
        this.releaseCalendar = releaseCalendar;
    }

    public String getReleaseCalendarAccess() {
        return releaseCalendarAccess;
    }

    public void setReleaseCalendarAccess(String releaseCalendarAccess) {
        this.releaseCalendarAccess = releaseCalendarAccess;
    }

    public Instant getInventoryDate() {
        return inventoryDate;
    }

    public void setInventoryDate(Instant inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    public ExternalItemDto getCommonMetadata() {
        return commonMetadata;
    }

    public void setCommonMetadata(ExternalItemDto commonMetadata) {
        this.commonMetadata = commonMetadata;
    }

    public InternationalStringDto getTitle() {
        return title;
    }

    public void setTitle(InternationalStringDto title) {
        this.title = title;
    }

    public InternationalStringDto getAcronym() {
        return acronym;
    }

    public void setAcronym(InternationalStringDto acronym) {
        this.acronym = acronym;
    }

    public ExternalItemDto getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(ExternalItemDto subjectArea) {
        this.subjectArea = subjectArea;
    }

    public InternationalStringDto getObjective() {
        return objective;
    }

    public void setObjective(InternationalStringDto objective) {
        this.objective = objective;
    }

    public InternationalStringDto getDescription() {
        return description;
    }

    public void setDescription(InternationalStringDto description) {
        this.description = description;
    }

    public SurveyTypeDto getSurveyType() {
        return surveyType;
    }

    public void setSurveyType(SurveyTypeDto surveyType) {
        this.surveyType = surveyType;
    }

    public OfficialityTypeDto getOfficialityType() {
        return officialityType;
    }

    public void setOfficialityType(OfficialityTypeDto officialityType) {
        this.officialityType = officialityType;
    }

    public ProcStatusEnum getProcStatus() {
        return procStatus;
    }

    public void setProcStatus(ProcStatusEnum procStatus) {
        this.procStatus = procStatus;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public InternationalStringDto getRelPolUsAc() {
        return relPolUsAc;
    }

    public void setRelPolUsAc(InternationalStringDto relPolUsAc) {
        this.relPolUsAc = relPolUsAc;
    }

    public InstanceBaseDto getCurrentInstance() {
        return currentInstance;
    }

    public void setCurrentInstance(InstanceBaseDto currentInstance) {
        this.currentInstance = currentInstance;
    }

    public InstanceBaseDto getCurrentInternalInstance() {
        return currentInternalInstance;
    }

    public void setCurrentInternalInstance(InstanceBaseDto currentInternalInstance) {
        this.currentInternalInstance = currentInternalInstance;
    }

    public InternationalStringDto getRevPolicy() {
        return revPolicy;
    }

    public void setRevPolicy(InternationalStringDto revPolicy) {
        this.revPolicy = revPolicy;
    }

    public InternationalStringDto getRevPractice() {
        return revPractice;
    }

    public void setRevPractice(InternationalStringDto revPractice) {
        this.revPractice = revPractice;
    }

    public InternationalStringDto getSpecificLegalActs() {
        return specificLegalActs;
    }

    public void setSpecificLegalActs(InternationalStringDto specificLegalActs) {
        this.specificLegalActs = specificLegalActs;
    }

    public InternationalStringDto getSpecificDataSharing() {
        return specificDataSharing;
    }

    public void setSpecificDataSharing(InternationalStringDto specificDataSharing) {
        this.specificDataSharing = specificDataSharing;
    }

    public InternationalStringDto getComment() {
        return comment;
    }

    public void setComment(InternationalStringDto comment) {
        this.comment = comment;
    }

    public InternationalStringDto getNotes() {
        return notes;
    }

    public void setNotes(InternationalStringDto notes) {
        this.notes = notes;
    }

    public Set<ExternalItemDto> getSecondarySubjectAreas() {
        return secondarySubjectAreas;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getSecondarySubjectAreas}.
     */
    public void addSecondarySubjectArea(ExternalItemDto secondarySubjectAreaElement) {
        getSecondarySubjectAreas().add(secondarySubjectAreaElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getSecondarySubjectAreas}.
     */
    public void removeSecondarySubjectArea(ExternalItemDto secondarySubjectAreaElement) {
        getSecondarySubjectAreas().remove(secondarySubjectAreaElement);
    }

    /**
     * Removes all object from the unidirectional
     * to-many association.
     * All elements are removed from the collection {@link #getSecondarySubjectAreas}.
     */
    public void removeAllSecondarySubjectAreas() {
        getSecondarySubjectAreas().clear();
    }

    public Set<ExternalItemDto> getProducer() {
        return producer;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getProducer}.
     */
    public void addProducer(ExternalItemDto producerElement) {
        getProducer().add(producerElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getProducer}.
     */
    public void removeProducer(ExternalItemDto producerElement) {
        getProducer().remove(producerElement);
    }

    /**
     * Removes all object from the unidirectional
     * to-many association.
     * All elements are removed from the collection {@link #getProducer}.
     */
    public void removeAllProducer() {
        getProducer().clear();
    }

    public Set<ExternalItemDto> getRegionalResponsible() {
        return regionalResponsible;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getRegionalResponsible}.
     */
    public void addRegionalResponsible(ExternalItemDto regionalResponsibleElement) {
        getRegionalResponsible().add(regionalResponsibleElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getRegionalResponsible}.
     */
    public void removeRegionalResponsible(ExternalItemDto regionalResponsibleElement) {
        getRegionalResponsible().remove(regionalResponsibleElement);
    }

    /**
     * Removes all object from the unidirectional
     * to-many association.
     * All elements are removed from the collection {@link #getRegionalResponsible}.
     */
    public void removeAllRegionalResponsible() {
        getRegionalResponsible().clear();
    }

    public Set<ExternalItemDto> getRegionalContributor() {
        return regionalContributor;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getRegionalContributor}.
     */
    public void addRegionalContributor(ExternalItemDto regionalContributorElement) {
        getRegionalContributor().add(regionalContributorElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getRegionalContributor}.
     */
    public void removeRegionalContributor(ExternalItemDto regionalContributorElement) {
        getRegionalContributor().remove(regionalContributorElement);
    }

    /**
     * Removes all object from the unidirectional
     * to-many association.
     * All elements are removed from the collection {@link #getRegionalContributor}.
     */
    public void removeAllRegionalContributor() {
        getRegionalContributor().clear();
    }

    public Set<ExternalItemDto> getPublisher() {
        return publisher;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getPublisher}.
     */
    public void addPublisher(ExternalItemDto publisherElement) {
        getPublisher().add(publisherElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getPublisher}.
     */
    public void removePublisher(ExternalItemDto publisherElement) {
        getPublisher().remove(publisherElement);
    }

    /**
     * Removes all object from the unidirectional
     * to-many association.
     * All elements are removed from the collection {@link #getPublisher}.
     */
    public void removeAllPublisher() {
        getPublisher().clear();
    }

    public Set<ExternalItemDto> getUpdateFrequency() {
        return updateFrequency;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getUpdateFrequency}.
     */
    public void addUpdateFrequency(ExternalItemDto updateFrequencyElement) {
        getUpdateFrequency().add(updateFrequencyElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getUpdateFrequency}.
     */
    public void removeUpdateFrequency(ExternalItemDto updateFrequencyElement) {
        getUpdateFrequency().remove(updateFrequencyElement);
    }

    /**
     * Removes all object from the unidirectional
     * to-many association.
     * All elements are removed from the collection {@link #getUpdateFrequency}.
     */
    public void removeAllUpdateFrequency() {
        getUpdateFrequency().clear();
    }
}
