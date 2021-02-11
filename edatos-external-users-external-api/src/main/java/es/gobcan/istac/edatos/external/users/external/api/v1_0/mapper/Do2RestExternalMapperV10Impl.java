package es.gobcan.istac.edatos.external.users.external.api.v1_0.mapper;

import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Response.Status;

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
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configuration;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.ClassSystems;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.CollMethods;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Costs;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.DataSharings;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Families;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Family;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.FreqColls;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.GeographicGranularities;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.InformationSuppliers;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Instance;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.InstanceTypes;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Instances;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.LegalActs;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Measures;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.OfficialityTypes;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Operation;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Operations;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Producers;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Publishers;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.RegionalContributors;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.RegionalResponsibles;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.SecondarySubjectAreas;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.StatConcDefs;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.StatisticalOperationSources;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.StatisticalOperationTypes;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.StatisticalUnits;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.TemporalGranularities;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.UpdateFrequencies;
import org.siemac.metamac.srm.rest.common.SrmRestConstants;
import org.siemac.metamac.statistical_operations.rest.common.StatisticalOperationsRestConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import es.gobcan.istac.edatos.external.users.core.domain.CollMethodEntity;
import es.gobcan.istac.edatos.external.users.core.domain.CostEntity;
import es.gobcan.istac.edatos.external.users.core.domain.ExternalItemEntity;
import es.gobcan.istac.edatos.external.users.core.domain.FamilyEntity;
import es.gobcan.istac.edatos.external.users.core.domain.InstanceEntity;
import es.gobcan.istac.edatos.external.users.core.domain.InstanceTypeEntity;
import es.gobcan.istac.edatos.external.users.core.domain.InternationalStringEntity;
import es.gobcan.istac.edatos.external.users.core.domain.LocalisedStringEntity;
import es.gobcan.istac.edatos.external.users.core.domain.OfficialityTypeEntity;
import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;
import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity.StatusEnum;
import es.gobcan.istac.edatos.external.users.core.domain.SurveySourceEntity;
import es.gobcan.istac.edatos.external.users.core.domain.SurveyTypeEntity;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.ProcStatusEnum;
import es.gobcan.istac.edatos.external.users.core.service.MetadataConfigurationService;
import es.gobcan.istac.edatos.external.users.external.api.invocation.SrmRestExternalFacade;
import es.gobcan.istac.edatos.external.users.external.api.exception.RestServiceExceptionType;
import es.gobcan.istac.edatos.external.users.external.api.invocation.CommonMetadataRestExternalFacade;

@Component
public class Do2RestExternalMapperV10Impl implements Do2RestExternalMapperV10 {

    @Autowired
    private MetadataConfigurationService configurationService;

    @Autowired
    private CommonMetadataRestExternalFacade commonMetadataRestExternalFacade;

    @Autowired
    private SrmRestExternalFacade srmRestExternalFacade;

    private String statisticalOperationsApiExternalEndpointV10;
    private String srmApiExternalEndpoint;

    @PostConstruct
    public void init() throws Exception {
        this.initEndpoints();
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
        target.setSelfLink(toOperationSelfLink(source));
        target.setName(toInternationalString(source.getTitle()));
        target.setAcronym(toInternationalString(source.getAcronym()));
        target.setSubjectArea(toResourceExternalItemSrm(source.getSubjectArea()));
        target.setSecondarySubjectAreas(toSecondarySubjectAreas(source.getSecondarySubjectAreas()));
        target.setObjective(toInternationalString(source.getObjective()));
        target.setDescription(toInternationalString(source.getDescription()));
        target.setStatisticalOperationType(toItem(source.getSurveyType()));
        target.setOfficialityType(toItem(source.getOfficialityType()));
        target.setIndicatorSystem(source.getIndicatorSystem());
        target.setProducers(toProducers(source.getProducer()));
        target.setRegionalResponsibles(toRegionalResponsibles(source.getRegionalResponsible()));
        target.setRegionalContributors(toRegionalContributors(source.getRegionalContributor()));
        target.setCurrentlyActive(source.getCurrentlyActive());
        target.setStatus(toStatus(source.getStatus()));
        target.setPublishers(toPublishers(source.getPublisher()));
        target.setRelPolUsAc(toInternationalString(source.getRelPolUsAc()));
        target.setReleaseCalendar(source.getReleaseCalendar());
        target.setReleaseCalendarAccess(source.getReleaseCalendarAccess());
        target.setUpdateFrequencies(toUpdateFrequencies(source.getUpdateFrequency()));
        target.setCurrentInstance(toResource(getInstanceInProcStatus(source.getInstances(), ProcStatusEnum.EXTERNALLY_PUBLISHED)));
        target.setInventoryDate(toDate(source.getInventoryDate()));
        target.setRevPolicy(toInternationalString(source.getRevPolicy()));
        target.setRevPractice(toInternationalString(source.getRevPractice()));
        commonMetadataToOperation(source.getCommonMetadata(), target);
        target.setLegalActs(toOperationLegalActs(source.getSpecificLegalActs(), null, target.getLegalActs()));
        target.setDataSharings(toOperationDataSharings(source.getSpecificDataSharing(), null, target.getDataSharings()));
        target.setComment(toInternationalString(source.getComment()));
        target.setParentLink(toOperationParentLink());
        target.setChildLinks(toOperationChildLinks(source));
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
            Resource target = toResource(source);
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
            Resource target = this.toResource(source);
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
        target.setSelfLink(toFamilySelfLink(source));
        target.setName(toInternationalString(source.getTitle()));
        target.setAcronym(toInternationalString(source.getAcronym()));
        target.setDescription(toInternationalString(source.getDescription()));
        target.setInventoryDate(toDate(source.getInventoryDate()));
        target.setParentLink(toFamilyParentLink());
        target.setChildLinks(toFamilyChildLinks(source));
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
            Resource target = this.toResource(source);
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
                Resource target = this.toResource(source);
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
        target.setSelfLink(toInstanceSelfLink(source));
        target.setName(toInternationalString(source.getTitle()));
        target.setAcronym(toInternationalString(source.getAcronym()));
        target.setStatisticalOperation(toResource(source.getOperation()));
        target.setPredecessor(toResource(getInstanceInOrder(source.getOperation().getInstances(), source.getOrder() - 1)));
        target.setSuccessor(toResource(getInstanceInOrder(source.getOperation().getInstances(), source.getOrder() + 1)));
        target.setDataDescription(toInternationalString(source.getDataDescription()));
        target.setStatisticalPopulation(toInternationalString(source.getStatisticalPopulation()));
        target.setStatisticalUnits(toStatisticalUnits(source.getStatisticalUnit()));
        target.setGeographicGranularity(toGeographicGranularities(source.getGeographicGranularity()));
        target.setGeographicComparability(toInternationalString(source.getGeographicComparability()));
        target.setTemporalGranularity(toTemporalGranularities(source.getTemporalGranularity()));
        target.setTemporalComparability(toInternationalString(source.getTemporalComparability()));
        target.setBasePeriod(source.getBasePeriod());
        target.setMeasures(toMeasures(source.getUnitMeasure()));
        target.setStatConcDefsDescription(toInternationalString(source.getStatConcDef()));
        target.setStatConcDefs(toStatConcDefs(source.getStatConcDefList()));
        target.setClassSystemsDescription(toInternationalString(source.getClassSystem()));
        target.setClassSystems(toClassSystems(source.getClassSystemList()));
        target.setDocMethod(toInternationalString(source.getDocMethod()));
        target.setStatisticalOperationSource(toItem(source.getSurveySource()));
        target.setCollMethod(toItem(source.getCollMethod()));
        target.setInformationSuppliers(toInformationSuppliers(source.getInformationSuppliers()));
        target.setFreqColls(toFreqColls(source.getFreqColl()));
        target.setDataValidation(toInternationalString(source.getDataValidation()));
        target.setDataCompilation(toInternationalString(source.getDataCompilation()));
        target.setAdjustment(toInternationalString(source.getAdjustment()));
        target.setInventoryDate(toDate(source.getInventoryDate()));
        target.setQualityDoc(toInternationalString(source.getQualityDoc()));
        target.setQualityAssure(toInternationalString(source.getQualityAssure()));
        target.setQualityAssmnt(toInternationalString(source.getQualityAssmnt()));
        target.setUserNeeds(toInternationalString(source.getUserNeeds()));
        target.setUserSat(toInternationalString(source.getUserSat()));
        target.setCompleteness(toInternationalString(source.getCompleteness()));
        target.setTimeliness(toInternationalString(source.getTimeliness()));
        target.setPunctuality(toInternationalString(source.getPunctuality()));
        target.setAccuracyOverall(toInternationalString(source.getAccuracyOverall()));
        target.setSamplingErr(toInternationalString(source.getSamplingErr()));
        target.setNonsamplingErr(toInternationalString(source.getNonsamplingErr()));
        target.setCoherXDom(toInternationalString(source.getCoherXDomain()));
        target.setCoherInternal(toInternationalString(source.getCoherInternal()));
        target.setComment(toInternationalString(source.getComment()));
        target.setParentLink(toInstanceParentLink(source));
        target.setChildLinks(toInstanceChildLinks(source));
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
            Resource target = this.toResource(source);
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
                Item target = toItem(source);
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
        Configuration configuration = commonMetadataRestExternalFacade.retrieveConfigurationById(commonMetadata.getCode());

        // Transform
        target.setContact(configuration.getContact());
        target.setLegalActs(toOperationLegalActs(null, configuration.getLegalActs(), target.getLegalActs()));
        target.setDataSharings(toOperationDataSharings(null, configuration.getDataSharing(), target.getDataSharings()));
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
            target.getLegalActs().add(toInternationalString(source1));
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
            return target; // it can have already other internationalString
        }
        if (target == null) {
            target = new DataSharings();
            target.setTotal(BigInteger.ZERO);
        }
        if (source1 != null) {
            target.getDataSharings().add(toInternationalString(source1));
            target.setTotal(target.getTotal().add(BigInteger.ONE));
        }
        if (source2 != null) {
            target.getDataSharings().add(source2);
            target.setTotal(target.getTotal().add(BigInteger.ONE));
        }
        return target;
    }

    private Resource toResource(OperationEntity source) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(StatisticalOperationsRestConstants.KIND_OPERATION);
        target.setSelfLink(toOperationSelfLink(source));
        target.setName(toInternationalString(source.getTitle()));
        return target;
    }

    private Resource toResource(FamilyEntity source) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(StatisticalOperationsRestConstants.KIND_FAMILY);
        target.setSelfLink(toFamilySelfLink(source));
        target.setName(toInternationalString(source.getTitle()));
        return target;
    }

    private Resource toResource(InstanceEntity source) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(StatisticalOperationsRestConstants.KIND_INSTANCE);
        target.setSelfLink(toInstanceSelfLink(source));
        target.setName(toInternationalString(source.getTitle()));
        return target;
    }

    private Item toItem(SurveyTypeEntity source) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(toInternationalString(source.getDescription()));
        return target;
    }

    private Item toItem(OfficialityTypeEntity source) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(toInternationalString(source.getDescription()));
        return target;
    }

    private Item toItem(SurveySourceEntity source) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(toInternationalString(source.getDescription()));
        return target;
    }

    private Item toItem(InstanceTypeEntity source) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(toInternationalString(source.getDescription()));
        return target;
    }

    private Item toItem(CollMethodEntity source) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(toInternationalString(source.getDescription()));
        return target;
    }

    private Item toItem(CostEntity source) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(toInternationalString(source.getDescription()));
        return target;
    }

    private void toResourcesExternalItems(Set<ExternalItemEntity> sources, List<Resource> targets, String apiExternalItem) {
        if (sources == null) {
            return;
        }
        for (ExternalItemEntity source : sources) {
            Resource target = toResourceExternalItem(source, apiExternalItem);
            targets.add(target);
        }
    }

    private void toResourcesExternalItemsSrm(Set<ExternalItemEntity> sources, List<Resource> targets) {
        toResourcesExternalItems(sources, targets, srmApiExternalEndpoint);
    }

    private Resource toResourceExternalItemSrm(ExternalItemEntity source) {
        if (source == null) {
            return null;
        }
        return toResourceExternalItem(source, srmApiExternalEndpoint);
    }

    private Resource toResourceExternalItem(ExternalItemEntity source, String apiExternalItem) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        target.setId(source.getCode());
        target.setNestedId(source.getCodeNested());
        target.setUrn(source.getUrn());
        target.setKind(source.getType().getValue());
        target.setSelfLink(toResourceLink(target.getKind(), RestUtils.createLink(apiExternalItem, source.getUri())));
        target.setName(toInternationalString(source.getTitle()));
        return target;
    }

    private ResourceLink toOperationSelfLink(OperationEntity operation) {
        return toResourceLink(StatisticalOperationsRestConstants.KIND_OPERATION, toOperationLink(operation));
    }

    private ResourceLink toOperationParentLink() {
        return toResourceLink(StatisticalOperationsRestConstants.KIND_OPERATIONS, toOperationsLink());
    }

    private ChildLinks toOperationChildLinks(OperationEntity operation) {
        ChildLinks targets = new ChildLinks();

        targets.getChildLinks().add(toResourceLink(StatisticalOperationsRestConstants.KIND_INSTANCES, toInstancesLink(operation)));
        targets.getChildLinks().add(toResourceLink(StatisticalOperationsRestConstants.KIND_FAMILIES, toFamiliesByOperationLink(operation)));

        targets.setTotal(BigInteger.valueOf(targets.getChildLinks().size()));
        return targets;
    }

    private ResourceLink toFamilySelfLink(FamilyEntity family) {
        return toResourceLink(StatisticalOperationsRestConstants.KIND_FAMILY, toFamilyLink(family));
    }

    private ResourceLink toFamilyParentLink() {
        return toResourceLink(StatisticalOperationsRestConstants.KIND_FAMILIES, toFamiliesLink());
    }

    private ChildLinks toFamilyChildLinks(FamilyEntity family) {
        ChildLinks targets = new ChildLinks();

        // Operations of family
        targets.getChildLinks().add(toResourceLink(StatisticalOperationsRestConstants.KIND_OPERATIONS, toOperationsByFamilyLink(family)));

        targets.setTotal(BigInteger.valueOf(targets.getChildLinks().size()));
        return targets;
    }

    private ResourceLink toInstanceSelfLink(InstanceEntity instance) {
        return toResourceLink(StatisticalOperationsRestConstants.KIND_INSTANCE, toInstanceLink(instance));
    }

    private ResourceLink toInstanceParentLink(InstanceEntity instance) {
        return toResourceLink(StatisticalOperationsRestConstants.KIND_OPERATION, toOperationLink(instance.getOperation()));
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
        return RestUtils.createLink(statisticalOperationsApiExternalEndpointV10, StatisticalOperationsRestConstants.LINK_SUBPATH_OPERATIONS);
    }

    // API/operations/OPERATION_ID
    private String toOperationLink(String operationCode) {
        String linkOperations = toOperationsLink();
        return RestUtils.createLink(linkOperations, operationCode);
    }

    private String toOperationLink(OperationEntity operation) {
        return toOperationLink(operation.getCode());
    }

    // API/operations/OPERATION_ID/instances
    private String toInstancesLink(OperationEntity operation) {
        String linkOperation = null;
        if (operation != null) {
            linkOperation = toOperationLink(operation);
        } else {
            linkOperation = toOperationLink(StatisticalOperationsRestConstants.WILDCARD_ALL);
        }
        return RestUtils.createLink(linkOperation, StatisticalOperationsRestConstants.LINK_SUBPATH_INSTANCES);
    }

    // API/operations/OPERATION_ID/instances/INSTANCE_ID
    private String toInstanceLink(InstanceEntity instance) {
        String linkOperation = toInstancesLink(instance.getOperation());
        return RestUtils.createLink(linkOperation, instance.getCode());
    }

    // API/families
    private String toFamiliesLink() {
        return RestUtils.createLink(statisticalOperationsApiExternalEndpointV10, StatisticalOperationsRestConstants.LINK_SUBPATH_FAMILIES);
    }

    // API/families/family
    private String toFamilyLink(FamilyEntity family) {
        String linkFamilies = toFamiliesLink();
        return RestUtils.createLink(linkFamilies, family.getCode());
    }

    // API/operations/OPERATION_ID/families
    private String toFamiliesByOperationLink(OperationEntity operation) {
        String linkFamily = toOperationLink(operation);
        return RestUtils.createLink(linkFamily, StatisticalOperationsRestConstants.LINK_SUBPATH_FAMILIES);
    }

    // API/families/FAMILY_ID/operations
    private String toOperationsByFamilyLink(FamilyEntity family) {
        String linkFamily = toFamilyLink(family);
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
                if (ProcStatusEnum.EXTERNALLY_PUBLISHED.equals(instance.getProcStatus())) {
                    return instance;
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    private org.siemac.metamac.rest.statistical_operations.v1_0.domain.Status toStatus(StatusEnum source) {
        if (source == null) {
            return null;
        }
        switch (source) {
            case PLANNING:
                return org.siemac.metamac.rest.statistical_operations.v1_0.domain.Status.PLANNING;
            case DESIGN:
                return org.siemac.metamac.rest.statistical_operations.v1_0.domain.Status.DESIGN;
            case PRODUCTION:
                return org.siemac.metamac.rest.statistical_operations.v1_0.domain.Status.PRODUCTION;
            case OUT_OF_PRINT:
                return org.siemac.metamac.rest.statistical_operations.v1_0.domain.Status.OUT_OF_PRINT;
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
        toResourcesExternalItemsSrm(sources, targets.getSecondarySubjectAreas());
        targets.setKind(SrmRestConstants.KIND_CATEGORIES);
        targets.setTotal(BigInteger.valueOf(targets.getSecondarySubjectAreas().size()));
        return targets;
    }

    private Producers toProducers(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.isEmpty()) {
            return null;
        }
        Producers targets = new Producers();
        toResourcesExternalItemsSrm(sources, targets.getProducers());
        targets.setKind(SrmRestConstants.KIND_ORGANISATION_UNITS);
        targets.setTotal(BigInteger.valueOf(targets.getProducers().size()));
        return targets;
    }

    private RegionalResponsibles toRegionalResponsibles(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.isEmpty()) {
            return null;
        }
        RegionalResponsibles targets = new RegionalResponsibles();
        toResourcesExternalItemsSrm(sources, targets.getRegionalResponsibles());
        targets.setKind(SrmRestConstants.KIND_ORGANISATION_UNITS);
        targets.setTotal(BigInteger.valueOf(targets.getRegionalResponsibles().size()));
        return targets;
    }

    private RegionalContributors toRegionalContributors(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.isEmpty()) {
            return null;
        }
        RegionalContributors targets = new RegionalContributors();
        toResourcesExternalItemsSrm(sources, targets.getRegionalContributors());
        targets.setKind(SrmRestConstants.KIND_ORGANISATIONS);
        targets.setTotal(BigInteger.valueOf(targets.getRegionalContributors().size()));
        return targets;
    }

    private Publishers toPublishers(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.isEmpty()) {
            return null;
        }
        Publishers targets = new Publishers();
        toResourcesExternalItemsSrm(sources, targets.getPublishers());
        targets.setKind(SrmRestConstants.KIND_ORGANISATION_UNITS);
        targets.setTotal(BigInteger.valueOf(targets.getPublishers().size()));
        return targets;
    }

    private UpdateFrequencies toUpdateFrequencies(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.isEmpty()) {
            return null;
        }
        UpdateFrequencies targets = new UpdateFrequencies();
        toResourcesExternalItemsSrm(sources, targets.getUpdateFrequencies());
        targets.setKind(SrmRestConstants.KIND_CODES);
        targets.setTotal(BigInteger.valueOf(targets.getUpdateFrequencies().size()));
        return targets;
    }

    private StatisticalUnits toStatisticalUnits(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.isEmpty()) {
            return null;
        }
        StatisticalUnits targets = new StatisticalUnits();
        toResourcesExternalItemsSrm(sources, targets.getStatisticalUnits());
        targets.setKind(SrmRestConstants.KIND_CONCEPTS);
        targets.setTotal(BigInteger.valueOf(targets.getStatisticalUnits().size()));
        return targets;
    }

    private GeographicGranularities toGeographicGranularities(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.isEmpty()) {
            return null;
        }
        GeographicGranularities targets = new GeographicGranularities();
        toResourcesExternalItemsSrm(sources, targets.getGeographicGranularities());
        targets.setKind(SrmRestConstants.KIND_CODES);
        targets.setTotal(BigInteger.valueOf(targets.getGeographicGranularities().size()));
        return targets;
    }

    private TemporalGranularities toTemporalGranularities(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.isEmpty()) {
            return null;
        }
        TemporalGranularities targets = new TemporalGranularities();
        toResourcesExternalItemsSrm(sources, targets.getTemporalGranularities());
        targets.setKind(SrmRestConstants.KIND_CODES);
        targets.setTotal(BigInteger.valueOf(targets.getTemporalGranularities().size()));
        return targets;
    }

    private Measures toMeasures(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.isEmpty()) {
            return null;
        }
        Measures targets = new Measures();
        toResourcesFromExternalItemWithConceptSchemesAndConcepts(sources, targets.getMeasures());
        targets.setKind(SrmRestConstants.KIND_CONCEPTS);
        targets.setTotal(BigInteger.valueOf(targets.getMeasures().size()));
        return targets;
    }

    private StatConcDefs toStatConcDefs(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.isEmpty()) {
            return null;
        }
        StatConcDefs targets = new StatConcDefs();
        toResourcesFromExternalItemWithConceptSchemesAndConcepts(sources, targets.getStatConcDefs());
        targets.setKind(SrmRestConstants.KIND_CONCEPTS);
        targets.setTotal(BigInteger.valueOf(targets.getStatConcDefs().size()));
        return targets;
    }

    private void toResourcesFromExternalItemWithConceptSchemesAndConcepts(Set<ExternalItemEntity> sources, List<Resource> targets) {
        if (sources == null || sources.isEmpty()) {
            return;
        }
        for (ExternalItemEntity source : sources) {
            if (TypeExternalArtefactsEnum.CONCEPT.equals(source.getType())) {
                Resource target = toResourceExternalItemSrm(source);
                targets.add(target);
            } else if (TypeExternalArtefactsEnum.CONCEPT_SCHEME.equals(source.getType())) {
                List<Resource> targetConcepts = srmConceptSchemeToResourceConcepts(source);
                targets.addAll(targetConcepts);
            } else {
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
            }
        }
    }

    private List<Resource> srmConceptSchemeToResourceConcepts(ExternalItemEntity conceptSchemeSource) {
        // Return from API
        List<org.siemac.metamac.rest.structural_resources.v1_0.domain.ItemResource> conceptSources = srmRestExternalFacade.retrieveConceptsByConceptScheme(conceptSchemeSource.getUrn());

        // Transform
        List<Resource> targets = new ArrayList<>(conceptSources.size());
        for (org.siemac.metamac.rest.common.v1_0.domain.Resource conceptSource : conceptSources) {
            Resource conceptTarget = srmResourceToResource(conceptSource);
            targets.add(conceptTarget);
        }
        return targets;
    }

    private Resource srmResourceToResource(org.siemac.metamac.rest.common.v1_0.domain.Resource source) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        target.setId(source.getId());
        target.setUrn(source.getUrn());
        target.setKind(source.getKind());
        target.setSelfLink(source.getSelfLink());
        target.setName(source.getName());
        return target;
    }

    private ClassSystems toClassSystems(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        ClassSystems targets = new ClassSystems();
        toResourcesExternalItemsSrm(sources, targets.getClassSystems());
        targets.setKind(SrmRestConstants.KIND_CODELISTS);
        targets.setTotal(BigInteger.valueOf(targets.getClassSystems().size()));
        return targets;
    }

    private InformationSuppliers toInformationSuppliers(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        InformationSuppliers targets = new InformationSuppliers();
        toResourcesExternalItemsSrm(sources, targets.getInformationSuppliers());
        targets.setKind(SrmRestConstants.KIND_DATA_PROVIDERS);
        targets.setTotal(BigInteger.valueOf(targets.getInformationSuppliers().size()));
        return targets;
    }

    private FreqColls toFreqColls(Set<ExternalItemEntity> sources) {
        if (sources == null || sources.size() == 0) {
            return null;
        }
        FreqColls targets = new FreqColls();
        toResourcesExternalItemsSrm(sources, targets.getFreqColls());
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
        // Statistical operations External Api v1.0
        String statisticalOperationsApiExternalEndpoint = configurationService.retrieveStatisticalOperationsExternalApiUrlBase();
        statisticalOperationsApiExternalEndpointV10 = RestUtils.createLink(statisticalOperationsApiExternalEndpoint, StatisticalOperationsRestConstants.API_VERSION_1_0);

        // Srm External Api (do not add api version! it is already stored in database)
        srmApiExternalEndpoint = configurationService.retrieveSrmExternalApiUrlBase();
    }
}
