package es.gobcan.istac.statistical.operations.roadmap.internal.api.v1_0.mapper;

import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.siemac.edatos.core.common.enume.TypeExternalArtefactsEnum;
import org.siemac.edatos.rest.common.util.RestUtils;
import org.siemac.edatos.rest.exception.RestException;
import org.siemac.edatos.rest.exception.util.RestExceptionUtils;
import org.siemac.edatos.rest.search.PaginationAttributes;
import org.siemac.edatos.rest.search.util.PaginationUtils;
import org.siemac.metamac.rest.common.v1_0.domain.ChildLinks;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.Item;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configuration;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ClassSystems;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.CollMethods;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Costs;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.DataSharings;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Families;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Family;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.FreqColls;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.GeographicGranularities;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.InformationSuppliers;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Instance;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.InstanceTypes;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Instances;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.LegalActs;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Measures;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.OfficialityTypes;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operation;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operations;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ProcStatus;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Producers;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Publishers;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.RegionalContributors;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.RegionalResponsibles;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.SecondarySubjectAreas;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.StatConcDefs;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.StatisticalOperationSources;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.StatisticalOperationTypes;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.StatisticalUnits;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.TemporalGranularities;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.UpdateFrequencies;
import org.siemac.metamac.srm.rest.common.SrmRestConstants;
import org.siemac.metamac.statistical_operations.rest.common.StatisticalOperationsRestConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.CollMethodEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.CostEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.ExternalItemEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.FamilyEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceTypeEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.InternationalStringEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.LocalisedStringEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.OfficialityTypeEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity.StatusEnum;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.SurveySourceEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.SurveyTypeEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.enumeration.ProcStatusEnum;
import es.gobcan.istac.statistical.operations.roadmap.core.service.MetadataConfigurationService;
import es.gobcan.istac.statistical.operations.roadmap.internal.api.exception.RestServiceExceptionType;
import es.gobcan.istac.statistical.operations.roadmap.internal.api.invocation.CommonMetadataRestExternalFacade;
import es.gobcan.istac.statistical.operations.roadmap.internal.api.invocation.SrmRestInternalFacade;
import es.gobcan.istac.statistical.operations.roadmap.internal.api.v1_0.service.utils.InternalWebApplicationNavigation;

@Component
public class Do2RestInternalMapperV10Impl implements Do2RestInternalMapperV10 {

    @Autowired
    private MetadataConfigurationService configurationService;

    @Autowired
    private CommonMetadataRestExternalFacade commonMetadataRestExternalFacade;

    @Autowired
    private SrmRestInternalFacade srmRestInternalFacade;

    private String statisticalOperationsApiInternalEndpointV10;
    private String statisticalOperationsInternalWebApplication;
    private String srmApiInternalEndpoint;
    private String srmInternalWebApplication;

    private InternalWebApplicationNavigation internalWebApplicationNavigation;

    @PostConstruct
    public void init() throws Exception {
        this.initEndpoints();

        this.internalWebApplicationNavigation = new InternalWebApplicationNavigation(this.statisticalOperationsInternalWebApplication);
    }

    @Override
    public Operation toOperation(OperationEntity source) {
        if (source == null) {
            return null;
        }
        Operation target = new Operation();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(StatisticalOperationsRestConstants.KIND_OPERATION);
        target.setSelfLink(this.toOperationSelfLink(source.getCode()));
        target.setName(this.toInternationalString(source.getTitle()));
        target.setAcronym(this.toInternationalString(source.getAcronym()));
        target.setSubjectArea(this.toResourceExternalItemSrm(source.getSubjectArea()));
        target.setSecondarySubjectAreas(this.toSecondarySubjectAreas(source.getSecondarySubjectAreas()));
        target.setObjective(this.toInternationalString(source.getObjective()));
        target.setDescription(this.toInternationalString(source.getDescription()));
        target.setStatisticalOperationType(this.toItem(source.getSurveyType()));
        target.setOfficialityType(this.toItem(source.getOfficialityType()));
        target.setIndicatorSystem(source.getIndicatorSystem());
        target.setProducers(this.toProducers(source.getProducer()));
        target.setRegionalResponsibles(this.toRegionalResponsibles(source.getRegionalResponsible()));
        target.setRegionalContributors(this.toRegionalContributors(source.getRegionalContributor()));
        target.setCreatedDate(this.toDate(source.getCreatedDate()));
        target.setInternalInventoryDate(this.toDate(source.getInternalInventoryDate()));
        target.setCurrentlyActive(source.getCurrentlyActive());
        target.setStatus(this.toStatus(source.getStatus()));
        target.setProcStatus(this.toProcStatus(source.getProcStatus()));
        target.setPublishers(this.toPublishers(source.getPublisher()));
        target.setRelPolUsAc(this.toInternationalString(source.getRelPolUsAc()));
        target.setReleaseCalendar(source.getReleaseCalendar());
        target.setReleaseCalendarAccess(source.getReleaseCalendarAccess());
        target.setUpdateFrequencies(this.toUpdateFrequencies(source.getUpdateFrequency()));
        target.setCurrentInstance(this.toResource(this.getInstanceInProcStatus(source.getInstances(), ProcStatusEnum.EXTERNALLY_PUBLISHED)));
        target.setCurrentInternalInstance(this.toResource(this.getInstanceInProcStatus(source.getInstances(), ProcStatusEnum.INTERNALLY_PUBLISHED)));
        target.setInventoryDate(this.toDate(source.getInventoryDate()));
        target.setRevPolicy(this.toInternationalString(source.getRevPolicy()));
        target.setRevPractice(this.toInternationalString(source.getRevPractice()));
        this.commonMetadataToOperation(source.getCommonMetadata(), target);
        target.setLegalActs(this.toOperationLegalActs(source.getSpecificLegalActs(), null, target.getLegalActs()));
        target.setDataSharings(this.toOperationDataSharings(source.getSpecificDataSharing(), null, target.getDataSharings()));
        target.setComment(this.toInternationalString(source.getComment()));
        target.setNotes(this.toInternationalString(source.getNotes()));
        target.setParentLink(this.toOperationParentLink());
        target.setChildLinks(this.toOperationChildLinks(source));
        target.setManagementAppLink(this.toOperationManagementApplicationLink(source.getCode()));
        return target;
    }

    @Override
    public Operations toOperations(Page<OperationEntity> paginatedOperations, String query, String orderBy, Integer limit) {

        Operations targets = new Operations();
        targets.setKind(StatisticalOperationsRestConstants.KIND_OPERATIONS);

        // Pagination
        String baseLink = this.toOperationsLink();
        // @formatter:off
        PaginationAttributes paginationAttributes = new PaginationAttributes.Builder()
                .offset(paginatedOperations.getNumber())
                .limit(limit)
                .total(paginatedOperations.getTotalElements())
                .totalElementsInPage(paginatedOperations.getNumberOfElements())
                .query(query)
                .orderBy(orderBy)
                .baseLink(baseLink)
                .build();
        // @formatter:on
        PaginationUtils.fillListBaseAttributes(targets, paginationAttributes);

        // Values
        for (OperationEntity source : paginatedOperations.getContent()) {
            ResourceInternal target = this.toResource(source);
            targets.getOperations().add(target);
        }
        return targets;
    }

    @Override
    public Operations toOperationsByFamily(FamilyEntity family, Page<OperationEntity> paginatedOperations, String query, String orderBy, Integer limit) {

        Operations targets = new Operations();
        targets.setKind(StatisticalOperationsRestConstants.KIND_OPERATIONS);

        // Pagination
        String baseLink = this.toOperationsByFamilyLink(family);
        // @formatter:off
        PaginationAttributes paginationAttributes = new PaginationAttributes.Builder()
                .offset(paginatedOperations.getNumber())
                .limit(limit)
                .total(paginatedOperations.getTotalElements())
                .totalElementsInPage(paginatedOperations.getNumberOfElements())
                .query(query)
                .orderBy(orderBy)
                .baseLink(baseLink)
                .build();
        // @formatter:on
        PaginationUtils.fillListBaseAttributes(targets, paginationAttributes);

        // Values
        for (OperationEntity source : paginatedOperations.getContent()) {
            ResourceInternal target = this.toResource(source);
            targets.getOperations().add(target);
        }
        return targets;
    }

    @Override
    public Family toFamily(FamilyEntity source) {
        if (source == null) {
            return null;
        }
        Family target = new Family();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(StatisticalOperationsRestConstants.KIND_FAMILY);
        target.setSelfLink(this.toFamilySelfLink(source));
        target.setName(this.toInternationalString(source.getTitle()));
        target.setAcronym(this.toInternationalString(source.getAcronym()));
        target.setDescription(this.toInternationalString(source.getDescription()));
        target.setCreatedDate(this.toDate(source.getCreatedDate()));
        target.setInternalInventoryDate(this.toDate(source.getInternalInventoryDate()));
        target.setProcStatus(this.toProcStatus(source.getProcStatus()));
        target.setInventoryDate(this.toDate(source.getInventoryDate()));
        target.setParentLink(this.toFamilyParentLink());
        target.setChildLinks(this.toFamilyChildLinks(source));
        target.setManagementAppLink(this.toFamilyManagementApplicationLink(source));
        return target;
    }

    @Override
    public Families toFamilies(Page<FamilyEntity> paginatedFamilies, String query, String orderBy, Integer limit) {

        Families targets = new Families();
        targets.setKind(StatisticalOperationsRestConstants.KIND_FAMILIES);

        // Pagination
        String baseLink = this.toFamiliesLink();
        // @formatter:off
        PaginationAttributes paginationAttributes = new PaginationAttributes.Builder()
                .offset(paginatedFamilies.getNumber())
                .limit(limit)
                .total(paginatedFamilies.getTotalElements())
                .totalElementsInPage(paginatedFamilies.getNumberOfElements())
                .query(query)
                .orderBy(orderBy)
                .baseLink(baseLink)
                .build();
        // @formatter:on
        PaginationUtils.fillListBaseAttributes(targets, paginationAttributes);

        // Values
        for (FamilyEntity source : paginatedFamilies.getContent()) {
            ResourceInternal target = this.toResource(source);
            targets.getFamilies().add(target);
        }
        return targets;
    }

    @Override
    public Families toFamiliesByOperation(List<FamilyEntity> sources) {

        Families targets = new Families();
        targets.setKind(StatisticalOperationsRestConstants.KIND_FAMILIES);

        if (sources == null) {
            targets.setTotal(BigInteger.ZERO);
        } else {
            for (FamilyEntity source : sources) {
                ResourceInternal target = this.toResource(source);
                targets.getFamilies().add(target);
            }
            targets.setTotal(BigInteger.valueOf(sources.size()));
        }

        return targets;
    }

    @Override
    public Instance toInstance(InstanceEntity source) {
        if (source == null) {
            return null;
        }
        Instance target = new Instance();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(StatisticalOperationsRestConstants.KIND_INSTANCE);
        target.setSelfLink(this.toInstanceSelfLink(source));
        target.setName(this.toInternationalString(source.getTitle()));
        target.setAcronym(this.toInternationalString(source.getAcronym()));
        target.setStatisticalOperation(this.toResource(source.getOperation()));
        target.setPredecessor(this.toResource(this.getInstanceInOrder(source.getOperation().getInstances(), source.getOrder() - 1)));
        target.setSuccessor(this.toResource(this.getInstanceInOrder(source.getOperation().getInstances(), source.getOrder() + 1)));
        target.setDataDescription(this.toInternationalString(source.getDataDescription()));
        target.setStatisticalPopulation(this.toInternationalString(source.getStatisticalPopulation()));
        target.setStatisticalUnits(this.toStatisticalUnits(source.getStatisticalUnit()));
        target.setGeographicGranularity(this.toGeographicGranularities(source.getGeographicGranularity()));
        target.setGeographicComparability(this.toInternationalString(source.getGeographicComparability()));
        target.setTemporalGranularity(this.toTemporalGranularities(source.getTemporalGranularity()));
        target.setTemporalComparability(this.toInternationalString(source.getTemporalComparability()));
        target.setBasePeriod(source.getBasePeriod());
        target.setMeasures(this.toMeasures(source.getUnitMeasure()));
        target.setStatConcDefsDescription(this.toInternationalString(source.getStatConcDef()));
        target.setStatConcDefs(this.toStatConcDefs(source.getStatConcDefList()));
        target.setClassSystemsDescription(this.toInternationalString(source.getClassSystem()));
        target.setClassSystems(this.toClassSystems(source.getClassSystemList()));
        target.setInstanceType(this.toItem(source.getInstanceType()));
        target.setCreatedDate(this.toDate(source.getCreatedDate()));
        target.setInternalInventoryDate(this.toDate(source.getInternalInventoryDate()));
        target.setProcStatus(this.toProcStatus(source.getProcStatus()));
        target.setDocMethod(this.toInternationalString(source.getDocMethod()));
        target.setStatisticalOperationSource(this.toItem(source.getSurveySource()));
        target.setCollMethod(this.toItem(source.getCollMethod()));
        target.setInformationSuppliers(this.toInformationSuppliers(source.getInformationSuppliers()));
        target.setFreqColls(this.toFreqColls(source.getFreqColl()));
        target.setDataValidation(this.toInternationalString(source.getDataValidation()));
        target.setDataCompilation(this.toInternationalString(source.getDataCompilation()));
        target.setAdjustment(this.toInternationalString(source.getAdjustment()));
        target.setCostBurden(this.toInternationalString(source.getCostBurden()));
        target.setCosts(this.toCosts(source.getCost()));
        target.setInventoryDate(this.toDate(source.getInventoryDate()));
        target.setQualityDoc(this.toInternationalString(source.getQualityDoc()));
        target.setQualityAssure(this.toInternationalString(source.getQualityAssure()));
        target.setQualityAssmnt(this.toInternationalString(source.getQualityAssmnt()));
        target.setUserNeeds(this.toInternationalString(source.getUserNeeds()));
        target.setUserSat(this.toInternationalString(source.getUserSat()));
        target.setCompleteness(this.toInternationalString(source.getCompleteness()));
        target.setTimeliness(this.toInternationalString(source.getTimeliness()));
        target.setPunctuality(this.toInternationalString(source.getPunctuality()));
        target.setAccuracyOverall(this.toInternationalString(source.getAccuracyOverall()));
        target.setSamplingErr(this.toInternationalString(source.getSamplingErr()));
        target.setNonsamplingErr(this.toInternationalString(source.getNonsamplingErr()));
        target.setCoherXDom(this.toInternationalString(source.getCoherXDomain()));
        target.setCoherInternal(this.toInternationalString(source.getCoherInternal()));
        target.setComment(this.toInternationalString(source.getComment()));
        target.setNotes(this.toInternationalString(source.getNotes()));
        target.setParentLink(this.toInstanceParentLink(source));
        target.setChildLinks(this.toInstanceChildLinks(source));
        target.setManagementAppLink(this.toInstanceManagementApplicationLink(source));
        return target;
    }

    @Override
    public Instances toInstances(OperationEntity operation, Page<InstanceEntity> paginatedInstances, String query, String orderBy, Integer limit) {

        Instances targets = new Instances();
        targets.setKind(StatisticalOperationsRestConstants.KIND_INSTANCES);

        // Pagination
        String baseLink = this.toInstancesLink(operation);
        // @formatter:off
        PaginationAttributes paginationAttributes = new PaginationAttributes.Builder()
                .offset(paginatedInstances.getNumber())
                .limit(limit)
                .total(paginatedInstances.getTotalElements())
                .totalElementsInPage(paginatedInstances.getNumberOfElements())
                .query(query)
                .orderBy(orderBy)
                .baseLink(baseLink)
                .build();
        // @formatter:on
        PaginationUtils.fillListBaseAttributes(targets, paginationAttributes);

        // Values
        for (InstanceEntity source : paginatedInstances.getContent()) {
            ResourceInternal target = this.toResource(source);
            targets.getInstances().add(target);
        }
        return targets;
    }

    @Override
    public StatisticalOperationTypes toStatisticalOperationTypes(List<SurveyTypeEntity> sources) {
        StatisticalOperationTypes targets = new StatisticalOperationTypes();
        targets.setKind(StatisticalOperationsRestConstants.KIND_STATISTICAL_OPERATION_TYPES);

        if (sources == null) {
            targets.setTotal(BigInteger.ZERO);
        } else {
            for (SurveyTypeEntity source : sources) {
                Item target = this.toItem(source);
                targets.getStatisticalOperationTypes().add(target);
            }
            targets.setTotal(BigInteger.valueOf(sources.size()));
        }

        return targets;
    }

    @Override
    public OfficialityTypes toOfficialityTypes(List<OfficialityTypeEntity> sources) {
        OfficialityTypes targets = new OfficialityTypes();
        targets.setKind(StatisticalOperationsRestConstants.KIND_OFFICIALITY_TYPES);

        if (sources == null) {
            targets.setTotal(BigInteger.ZERO);
        } else {
            for (OfficialityTypeEntity source : sources) {
                Item target = this.toItem(source);
                targets.getOfficialityTypes().add(target);
            }
            targets.setTotal(BigInteger.valueOf(sources.size()));
        }

        return targets;
    }

    @Override
    public InstanceTypes toInstanceTypes(List<InstanceTypeEntity> sources) {
        InstanceTypes targets = new InstanceTypes();
        targets.setKind(StatisticalOperationsRestConstants.KIND_INSTANCE_TYPES);

        if (sources == null) {
            targets.setTotal(BigInteger.ZERO);
        } else {
            for (InstanceTypeEntity source : sources) {
                Item target = this.toItem(source);
                targets.getInstanceTypes().add(target);
            }
            targets.setTotal(BigInteger.valueOf(sources.size()));
        }

        return targets;
    }

    @Override
    public StatisticalOperationSources toStatisticalOperationSources(List<SurveySourceEntity> sources) {
        StatisticalOperationSources targets = new StatisticalOperationSources();
        targets.setKind(StatisticalOperationsRestConstants.KIND_STATISTICAL_OPERATION_SOURCES);

        if (sources == null) {
            targets.setTotal(BigInteger.ZERO);
        } else {
            for (SurveySourceEntity source : sources) {
                Item target = this.toItem(source);
                targets.getStatisticalOperationSources().add(target);
            }
            targets.setTotal(BigInteger.valueOf(sources.size()));
        }

        return targets;
    }

    @Override
    public CollMethods toCollMethods(List<CollMethodEntity> sources) {
        CollMethods targets = new CollMethods();
        targets.setKind(StatisticalOperationsRestConstants.KIND_COLL_METHODS);

        if (sources == null) {
            targets.setTotal(BigInteger.ZERO);
        } else {
            for (CollMethodEntity source : sources) {
                Item target = this.toItem(source);
                targets.getCollMethods().add(target);
            }
            targets.setTotal(BigInteger.valueOf(sources.size()));
        }

        return targets;
    }

    @Override
    public Costs toCosts(List<CostEntity> sources) {
        return this.toCosts((Collection<CostEntity>) sources);
    }

    private Costs toCosts(Collection<CostEntity> sources) {
        Costs targets = new Costs();
        targets.setKind(StatisticalOperationsRestConstants.KIND_COSTS);

        if (sources == null) {
            targets.setTotal(BigInteger.ZERO);
        } else {
            for (CostEntity source : sources) {
                Item target = this.toItem(source);
                targets.getCosts().add(target);
            }
            targets.setTotal(BigInteger.valueOf(sources.size()));
        }

        return targets;
    }

    private void commonMetadataToOperation(ExternalItemEntity commonMetadata, Operation target) {
        if (commonMetadata == null) {
            return;
        }
        // Calls to CommonMetadata API
        Configuration configuration = this.commonMetadataRestExternalFacade.retrieveConfigurationById(commonMetadata.getCode());

        // Transform
        target.setContact(this.commonMetadataResourceInternalToResourceInternal(configuration.getContact()));
        target.setLegalActs(this.toOperationLegalActs(null, configuration.getLegalActs(), target.getLegalActs()));
        target.setDataSharings(this.toOperationDataSharings(null, configuration.getDataSharing(), target.getDataSharings()));
        target.setConfidentialityPolicy(configuration.getConfPolicy());
        target.setConfidentialityDataTreatment(configuration.getConfDataTreatment());
    }

    private LegalActs toOperationLegalActs(InternationalStringEntity source1, InternationalString source2, LegalActs target) {
        if (source1 == null && source2 == null) {
            return target; // it can have already other internationalString
        }
        if (target == null) {
            target = new LegalActs();
            target.setTotal(BigInteger.ZERO);
        }
        if (source1 != null) {
            target.getLegalActs().add(this.toInternationalString(source1));
            target.setTotal(target.getTotal().add(BigInteger.ONE));
        }
        if (source2 != null) {
            target.getLegalActs().add(source2);
            target.setTotal(target.getTotal().add(BigInteger.ONE));
        }
        return target;
    }

    private DataSharings toOperationDataSharings(InternationalStringEntity source1, InternationalString source2, DataSharings target) {
        if (source1 == null && source2 == null) {
            // it can have already other internationalString
            return target;
        }
        if (target == null) {
            target = new DataSharings();
            target.setTotal(BigInteger.ZERO);
        }
        if (source1 != null) {
            target.getDataSharings().add(this.toInternationalString(source1));
            target.setTotal(target.getTotal().add(BigInteger.ONE));
        }
        if (source2 != null) {
            target.getDataSharings().add(source2);
            target.setTotal(target.getTotal().add(BigInteger.ONE));
        }
        return target;
    }

    @Override
    public ResourceInternal toResource(OperationEntity source) {
        if (source == null) {
            return null;
        }
        ResourceInternal target = new ResourceInternal();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(StatisticalOperationsRestConstants.KIND_OPERATION);
        target.setSelfLink(this.toOperationSelfLink(source.getCode()));
        target.setManagementAppLink(this.toOperationManagementApplicationLink(source.getCode()));
        target.setName(this.toInternationalString(source.getTitle()));
        return target;
    }

    private ResourceInternal toResource(FamilyEntity source) {
        if (source == null) {
            return null;
        }
        ResourceInternal target = new ResourceInternal();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(StatisticalOperationsRestConstants.KIND_FAMILY);
        target.setManagementAppLink(this.toFamilyManagementApplicationLink(source));
        target.setSelfLink(this.toFamilySelfLink(source));
        target.setName(this.toInternationalString(source.getTitle()));
        return target;
    }

    private ResourceInternal toResource(InstanceEntity source) {
        if (source == null) {
            return null;
        }
        ResourceInternal target = new ResourceInternal();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(StatisticalOperationsRestConstants.KIND_INSTANCE);
        target.setSelfLink(this.toInstanceSelfLink(source));
        target.setManagementAppLink(this.toInstanceManagementApplicationLink(source));
        target.setName(this.toInternationalString(source.getTitle()));
        return target;
    }

    private Item toItem(SurveyTypeEntity source) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(this.toInternationalString(source.getDescription()));
        return target;
    }

    private Item toItem(OfficialityTypeEntity source) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(this.toInternationalString(source.getDescription()));
        return target;
    }

    private Item toItem(SurveySourceEntity source) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(this.toInternationalString(source.getDescription()));
        return target;
    }

    private Item toItem(InstanceTypeEntity source) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(this.toInternationalString(source.getDescription()));
        return target;
    }

    private Item toItem(es.gobcan.istac.statistical.operations.roadmap.core.domain.CollMethodEntity source) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(this.toInternationalString(source.getDescription()));
        return target;
    }

    private Item toItem(CostEntity source) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(this.toInternationalString(source.getDescription()));
        return target;
    }

    private void toResourcesExternalItems(Set<ExternalItemEntity> sources, List<ResourceInternal> targets, String apiExternalItem, String managementAppBaseUrl) {
        if (sources == null) {
            return;
        }
        for (ExternalItemEntity source : sources) {
            ResourceInternal target = this.toResourceExternalItem(source, apiExternalItem, managementAppBaseUrl);
            targets.add(target);
        }
    }

    private void toResourcesExternalItemsSrm(Set<ExternalItemEntity> sources, List<ResourceInternal> targets) {
        this.toResourcesExternalItems(sources, targets, this.srmApiInternalEndpoint, this.srmInternalWebApplication);
    }

    private ResourceInternal toResourceExternalItemSrm(ExternalItemEntity source) {
        if (source == null) {
            return null;
        }
        return this.toResourceExternalItem(source, this.srmApiInternalEndpoint, this.srmInternalWebApplication);
    }

    private ResourceInternal toResourceExternalItem(ExternalItemEntity source, String apiExternalItemBase, String managementAppBaseUrl) {
        if (source == null) {
            return null;
        }
        ResourceInternal target = new ResourceInternal();
        target.setId(source.getCode());
        target.setNestedId(source.getCodeNested());
        target.setUrn(source.getUrn());
        target.setKind(source.getType().getValue());
        target.setSelfLink(this.toResourceLink(target.getKind(), RestUtils.createLink(apiExternalItemBase, source.getUri())));
        if (source.getManagementAppUrl() != null) {
            target.setManagementAppLink(RestUtils.createLink(managementAppBaseUrl, source.getManagementAppUrl()));
        }
        target.setName(this.toInternationalString(source.getTitle()));
        return target;
    }

    @Override
    public ResourceLink toOperationSelfLink(String operationCode) {
        return this.toResourceLink(StatisticalOperationsRestConstants.KIND_OPERATION, this.toOperationLink(operationCode));
    }

    private ResourceLink toOperationParentLink() {
        return this.toResourceLink(StatisticalOperationsRestConstants.KIND_OPERATIONS, this.toOperationsLink());
    }

    private ChildLinks toOperationChildLinks(OperationEntity operation) {
        ChildLinks targets = new ChildLinks();

        targets.getChildLinks().add(this.toResourceLink(StatisticalOperationsRestConstants.KIND_INSTANCES, this.toInstancesLink(operation)));
        targets.getChildLinks().add(this.toResourceLink(StatisticalOperationsRestConstants.KIND_FAMILIES, this.toFamiliesByOperationLink(operation)));

        targets.setTotal(BigInteger.valueOf(targets.getChildLinks().size()));
        return targets;
    }

    private ResourceLink toFamilySelfLink(FamilyEntity source) {
        return this.toResourceLink(StatisticalOperationsRestConstants.KIND_FAMILY, this.toFamilyLink(source));
    }

    private ResourceLink toFamilyParentLink() {
        return this.toResourceLink(StatisticalOperationsRestConstants.KIND_FAMILIES, this.toFamiliesLink());
    }

    private ChildLinks toFamilyChildLinks(FamilyEntity family) {
        ChildLinks targets = new ChildLinks();

        // Operations of family
        targets.getChildLinks().add(this.toResourceLink(StatisticalOperationsRestConstants.KIND_OPERATIONS, this.toOperationsByFamilyLink(family)));

        targets.setTotal(BigInteger.valueOf(targets.getChildLinks().size()));
        return targets;
    }

    private ResourceLink toInstanceSelfLink(InstanceEntity instance) {
        return this.toResourceLink(StatisticalOperationsRestConstants.KIND_INSTANCE, this.toInstanceLink(instance));
    }

    private ResourceLink toInstanceParentLink(InstanceEntity instance) {
        return this.toResourceLink(StatisticalOperationsRestConstants.KIND_OPERATION, this.toOperationLink(instance.getOperation().getCode()));
    }

    private ChildLinks toInstanceChildLinks(InstanceEntity instance) {
        // No children
        return null;
    }

    private InternationalString toInternationalString(InternationalStringEntity sources) {
        if (sources == null) {
            return null;
        }
        InternationalString targets = new InternationalString();
        for (LocalisedStringEntity source : sources.getTexts()) {
            LocalisedString target = new LocalisedString();
            target.setValue(source.getLabel());
            target.setLang(source.getLocale());
            targets.getTexts().add(target);
        }
        return targets;
    }

    private Date toDate(Instant source) {
        if (source == null) {
            return null;
        }
        return Date.from(source);
    }

    // API/operations
    private String toOperationsLink() {
        return RestUtils.createLink(this.statisticalOperationsApiInternalEndpointV10, StatisticalOperationsRestConstants.LINK_SUBPATH_OPERATIONS);
    }

    // API/operations/OPERATION_ID
    private String toOperationLink(String operationCode) {
        String linkOperations = this.toOperationsLink();
        return RestUtils.createLink(linkOperations, operationCode);
    }

    // API/operations/OPERATION_ID/instances
    private String toInstancesLink(OperationEntity operation) {
        String linkOperation = null;
        if (operation != null) {
            linkOperation = this.toOperationLink(operation.getCode());
        } else {
            linkOperation = this.toOperationLink(StatisticalOperationsRestConstants.WILDCARD_ALL);
        }
        return RestUtils.createLink(linkOperation, StatisticalOperationsRestConstants.LINK_SUBPATH_INSTANCES);
    }

    // API/operations/OPERATION_ID/instances/INSTANCE_ID
    private String toInstanceLink(InstanceEntity instance) {
        String linkOperation = this.toInstancesLink(instance.getOperation());
        return RestUtils.createLink(linkOperation, instance.getCode());
    }

    // API/families
    private String toFamiliesLink() {
        return RestUtils.createLink(this.statisticalOperationsApiInternalEndpointV10, StatisticalOperationsRestConstants.LINK_SUBPATH_FAMILIES);
    }

    // API/families/family
    private String toFamilyLink(FamilyEntity family) {
        String linkFamilies = this.toFamiliesLink();
        return RestUtils.createLink(linkFamilies, family.getCode());
    }

    // API/operations/OPERATION_ID/families
    private String toFamiliesByOperationLink(OperationEntity operation) {
        String linkFamily = this.toOperationLink(operation.getCode());
        return RestUtils.createLink(linkFamily, StatisticalOperationsRestConstants.LINK_SUBPATH_FAMILIES);
    }

    // API/families/FAMILY_ID/operations
    private String toOperationsByFamilyLink(FamilyEntity family) {
        String linkFamily = this.toFamilyLink(family);
        return RestUtils.createLink(linkFamily, StatisticalOperationsRestConstants.LINK_SUBPATH_OPERATIONS);
    }

    private InstanceEntity getInstanceInProcStatus(List<InstanceEntity> instances, ProcStatusEnum procStatus) {

        for (InstanceEntity instance : instances) {
            if (procStatus.equals(instance.getProcStatus())) {
                return instance;
            }
        }
        return null;
    }

    private InstanceEntity getInstanceInOrder(List<InstanceEntity> instances, Integer order) {

        for (InstanceEntity instance : instances) {
            if (order.equals(instance.getOrder())) {
                if (ProcStatusEnum.INTERNALLY_PUBLISHED.equals(instance.getProcStatus()) || ProcStatusEnum.EXTERNALLY_PUBLISHED.equals(instance.getProcStatus())) {
                    return instance;
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    private ProcStatus toProcStatus(ProcStatusEnum source) {
        if (source == null) {
            return null;
        }
        switch (source) {
            case INTERNALLY_PUBLISHED:
                return ProcStatus.INTERNALLY_PUBLISHED;
            case EXTERNALLY_PUBLISHED:
                return ProcStatus.EXTERNALLY_PUBLISHED;
            default:
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }

    private org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Status toStatus(StatusEnum source) {
        if (source == null) {
            return null;
        }
        switch (source) {
            case PLANNING:
                return org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Status.PLANNING;
            case DESIGN:
                return org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Status.DESIGN;
            case PRODUCTION:
                return org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Status.PRODUCTION;
            case OUT_OF_PRINT:
                return org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Status.OUT_OF_PRINT;
            default:
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }

    private SecondarySubjectAreas toSecondarySubjectAreas(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.isEmpty()) {
            return null;
        }
        SecondarySubjectAreas targets = new SecondarySubjectAreas();
        this.toResourcesExternalItemsSrm(sources, targets.getSecondarySubjectAreas());
        targets.setKind(SrmRestConstants.KIND_CATEGORIES);
        targets.setTotal(BigInteger.valueOf(targets.getSecondarySubjectAreas().size()));
        return targets;
    }

    private Producers toProducers(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.isEmpty()) {
            return null;
        }
        Producers targets = new Producers();
        this.toResourcesExternalItemsSrm(sources, targets.getProducers());
        targets.setKind(SrmRestConstants.KIND_ORGANISATION_UNITS);
        targets.setTotal(BigInteger.valueOf(targets.getProducers().size()));
        return targets;
    }

    private RegionalResponsibles toRegionalResponsibles(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.isEmpty()) {
            return null;
        }
        RegionalResponsibles targets = new RegionalResponsibles();
        this.toResourcesExternalItemsSrm(sources, targets.getRegionalResponsibles());
        targets.setKind(SrmRestConstants.KIND_ORGANISATION_UNITS);
        targets.setTotal(BigInteger.valueOf(targets.getRegionalResponsibles().size()));
        return targets;
    }

    private RegionalContributors toRegionalContributors(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.isEmpty()) {
            return null;
        }
        RegionalContributors targets = new RegionalContributors();
        this.toResourcesExternalItemsSrm(sources, targets.getRegionalContributors());
        targets.setKind(SrmRestConstants.KIND_ORGANISATIONS);
        targets.setTotal(BigInteger.valueOf(targets.getRegionalContributors().size()));
        return targets;
    }

    private Publishers toPublishers(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.isEmpty()) {
            return null;
        }
        Publishers targets = new Publishers();
        this.toResourcesExternalItemsSrm(sources, targets.getPublishers());
        targets.setKind(SrmRestConstants.KIND_ORGANISATION_UNITS);
        targets.setTotal(BigInteger.valueOf(targets.getPublishers().size()));
        return targets;
    }

    private UpdateFrequencies toUpdateFrequencies(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.isEmpty()) {
            return null;
        }
        UpdateFrequencies targets = new UpdateFrequencies();
        this.toResourcesExternalItemsSrm(sources, targets.getUpdateFrequencies());
        targets.setKind(SrmRestConstants.KIND_CODES);
        targets.setTotal(BigInteger.valueOf(targets.getUpdateFrequencies().size()));
        return targets;
    }

    private StatisticalUnits toStatisticalUnits(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.isEmpty()) {
            return null;
        }
        StatisticalUnits targets = new StatisticalUnits();
        this.toResourcesExternalItemsSrm(sources, targets.getStatisticalUnits());
        targets.setKind(SrmRestConstants.KIND_CONCEPTS);
        targets.setTotal(BigInteger.valueOf(targets.getStatisticalUnits().size()));
        return targets;
    }

    private GeographicGranularities toGeographicGranularities(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.isEmpty()) {
            return null;
        }
        GeographicGranularities targets = new GeographicGranularities();
        this.toResourcesExternalItemsSrm(sources, targets.getGeographicGranularities());
        targets.setKind(SrmRestConstants.KIND_CODES);
        targets.setTotal(BigInteger.valueOf(targets.getGeographicGranularities().size()));
        return targets;
    }

    private TemporalGranularities toTemporalGranularities(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.isEmpty()) {
            return null;
        }
        TemporalGranularities targets = new TemporalGranularities();
        this.toResourcesExternalItemsSrm(sources, targets.getTemporalGranularities());
        targets.setKind(SrmRestConstants.KIND_CODES);
        targets.setTotal(BigInteger.valueOf(targets.getTemporalGranularities().size()));
        return targets;
    }

    private Measures toMeasures(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.isEmpty()) {
            return null;
        }
        Measures targets = new Measures();
        this.toResourcesInternalFromExternalItemWithConceptSchemesAndConcepts(sources, targets.getMeasures());
        targets.setKind(SrmRestConstants.KIND_CONCEPTS);
        targets.setTotal(BigInteger.valueOf(targets.getMeasures().size()));
        return targets;
    }

    private StatConcDefs toStatConcDefs(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.isEmpty()) {
            return null;
        }
        StatConcDefs targets = new StatConcDefs();
        this.toResourcesInternalFromExternalItemWithConceptSchemesAndConcepts(sources, targets.getStatConcDefs());
        targets.setKind(SrmRestConstants.KIND_CONCEPTS);
        targets.setTotal(BigInteger.valueOf(targets.getStatConcDefs().size()));
        return targets;
    }

    private void toResourcesInternalFromExternalItemWithConceptSchemesAndConcepts(Set<ExternalItemEntity> sources, List<ResourceInternal> targets) {
        if (sources == null || sources.isEmpty()) {
            return;
        }
        for (ExternalItemEntity source : sources) {
            if (TypeExternalArtefactsEnum.CONCEPT.equals(source.getType())) {
                ResourceInternal target = this.toResourceExternalItemSrm(source);
                targets.add(target);
            } else if (TypeExternalArtefactsEnum.CONCEPT_SCHEME.equals(source.getType())) {
                List<ResourceInternal> targetConcepts = this.srmConceptSchemeToResourceInternalConcepts(source);
                targets.addAll(targetConcepts);
            } else {
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
            }
        }
    }

    private ClassSystems toClassSystems(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.isEmpty()) {
            return null;
        }
        ClassSystems targets = new ClassSystems();
        this.toResourcesExternalItemsSrm(sources, targets.getClassSystems());
        targets.setKind(SrmRestConstants.KIND_CODELISTS);
        targets.setTotal(BigInteger.valueOf(targets.getClassSystems().size()));
        return targets;
    }

    private InformationSuppliers toInformationSuppliers(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.isEmpty()) {
            return null;
        }
        InformationSuppliers targets = new InformationSuppliers();
        this.toResourcesExternalItemsSrm(sources, targets.getInformationSuppliers());
        targets.setKind(SrmRestConstants.KIND_DATA_PROVIDERS);
        targets.setTotal(BigInteger.valueOf(targets.getInformationSuppliers().size()));
        return targets;
    }

    private FreqColls toFreqColls(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.isEmpty()) {
            return null;
        }
        FreqColls targets = new FreqColls();
        this.toResourcesExternalItemsSrm(sources, targets.getFreqColls());
        targets.setKind(SrmRestConstants.KIND_CODES);
        targets.setTotal(BigInteger.valueOf(targets.getFreqColls().size()));
        return targets;
    }

    private ResourceLink toResourceLink(String kind, String href) {
        ResourceLink target = new ResourceLink();
        target.setKind(kind);
        target.setHref(href);
        return target;
    }

    private void initEndpoints() {
        // Srm internal application
        this.srmInternalWebApplication = this.configurationService.retrieveSrmInternalWebApplicationUrlBase();
        this.srmInternalWebApplication = StringUtils.removeEnd(this.srmInternalWebApplication, "/");

        // Statistical operations internal application
        this.statisticalOperationsInternalWebApplication = this.configurationService.retrieveStatisticalOperationsInternalWebApplicationUrlBase();
        this.statisticalOperationsInternalWebApplication = StringUtils.removeEnd(this.statisticalOperationsInternalWebApplication, "/");

        // Statistical operations Internal Api v1.0
        String statisticalOperationsApiInternalEndpoint = this.configurationService.retrieveStatisticalOperationsInternalApiUrlBase();
        this.statisticalOperationsApiInternalEndpointV10 = RestUtils.createLink(statisticalOperationsApiInternalEndpoint, StatisticalOperationsRestConstants.API_VERSION_1_0);

        // Srm Internal Api (do not add api version! it is already stored in database)
        this.srmApiInternalEndpoint = this.configurationService.retrieveSrmInternalApiUrlBase();
    }

    @Override
    public String toOperationManagementApplicationLink(String operationCode) {
        return this.internalWebApplicationNavigation.buildOperationUrl(operationCode);
    }

    private String toFamilyManagementApplicationLink(FamilyEntity source) {
        return this.internalWebApplicationNavigation.buildFamilyUrl(source);
    }

    private String toInstanceManagementApplicationLink(InstanceEntity source) {
        return this.internalWebApplicationNavigation.buildInstanceUrl(source);
    }

    private List<ResourceInternal> srmConceptSchemeToResourceInternalConcepts(ExternalItemEntity conceptSchemeSource) {
        // Return from API
        List<org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal> conceptSources = this.srmRestInternalFacade
                .retrieveConceptsByConceptScheme(conceptSchemeSource.getUrn());

        // Transform
        List<ResourceInternal> targets = new ArrayList<>(conceptSources.size());
        for (org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal conceptSource : conceptSources) {
            ResourceInternal conceptTarget = this.srmResourceInternalToResourceInternal(conceptSource);
            targets.add(conceptTarget);
        }
        return targets;
    }

    private ResourceInternal commonMetadataResourceInternalToResourceInternal(org.siemac.metamac.rest.common_metadata.v1_0.domain.ResourceInternal source) {
        if (source == null) {
            return null;
        }
        ResourceInternal target = new ResourceInternal();
        target.setId(source.getId());
        target.setUrn(source.getUrn());
        target.setKind(source.getKind());
        target.setSelfLink(source.getSelfLink());
        target.setManagementAppLink(source.getManagementAppLink());
        target.setName(source.getName());
        return target;
    }

    private ResourceInternal srmResourceInternalToResourceInternal(org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal source) {
        if (source == null) {
            return null;
        }
        ResourceInternal target = new ResourceInternal();
        target.setId(source.getId());
        target.setUrn(source.getUrn());
        target.setKind(source.getKind());
        target.setSelfLink(source.getSelfLink());
        target.setManagementAppLink(source.getManagementAppLink());
        target.setName(source.getName());
        return target;
    }

}
