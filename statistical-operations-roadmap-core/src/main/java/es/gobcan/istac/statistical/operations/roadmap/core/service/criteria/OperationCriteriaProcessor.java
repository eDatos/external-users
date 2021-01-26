package es.gobcan.istac.statistical.operations.roadmap.core.service.criteria;

import static es.gobcan.istac.statistical.operations.roadmap.core.domain.InternationalStringEntity.Properties.TEXTS;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.LocalisedStringEntity.Properties.LABEL;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity.Properties.ACRONYM;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity.Properties.PUBLISHER;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity.Properties.SUBJECT_AREA;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity.Properties.SECONDARY_SUBJECT_AREAS;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity.Properties.SURVEY_TYPE;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity.Properties.OFFICIALITY_TYPE;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity.Properties.CODE;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity.Properties.CURRENTLY_ACTIVE;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity.Properties.DESCRIPTION;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity.Properties.INDICATOR_SYSTEM;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity.Properties.INTERNAL_INVENTORY_DATE;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity.Properties.INVENTORY_DATE;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity.Properties.PRODUCER;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity.Properties.PROC_STATUS;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity.Properties.STATUS;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity.Properties.TITLE;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity.Properties.URN;
import static org.hibernate.sql.JoinType.LEFT_OUTER_JOIN;

import com.arte.libs.grammar.orm.jpa.criteria.AbstractCriteriaProcessor;
import com.arte.libs.grammar.orm.jpa.criteria.OrderProcessorBuilder;
import com.arte.libs.grammar.orm.jpa.criteria.RestrictionProcessorBuilder;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.ExternalItemEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.OfficialityTypeEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity.StatusEnum;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.SurveyTypeEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.enumeration.ProcStatusEnum;

public class OperationCriteriaProcessor extends AbstractCriteriaProcessor {

    public OperationCriteriaProcessor() {
        super(OperationEntity.class);
    }

    public enum QueryProperty {
        // @formatter:off
        ID,
        CODE,
        URN,
        TITLE,
        ACRONYM,
        SUBJECT_AREA_URN,
        SECONDARY_SUBJECT_AREA_URN,
        DESCRIPTION,
        STATISTICAL_OPERATION_TYPE_ID,
        OFFICIALITY_TYPE_ID,
        IS_INDICATORS_SYSTEM,
        PRODUCER_URN,
        INTERNAL_INVENTORY_DATE,
        CURRENTLY_ACTIVE,
        STATUS,
        PROC_STATUS,
        PUBLISHER_URN,
        INVENTORY_DATE;
        // @formatter:off
    }

    @Override
    public void registerProcessors() {
        //@formatter:off
        
        // When the ID is specified, a search by CODE must be performed. 
        // In the internal and external API the search by ID correspond to a search by CODE.
        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.ID).sortable()
                .withEntityProperty(CODE).build());
        registerOrderProcessor(
                OrderProcessorBuilder.orderProcessor()
                .withQueryProperty(QueryProperty.ID)
                .withEntityProperty(CODE)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.CODE).sortable()
                .withEntityProperty(CODE).build());
        registerOrderProcessor(
                OrderProcessorBuilder.orderProcessor()
                .withQueryProperty(QueryProperty.CODE)
                .withEntityProperty(CODE)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.URN).sortable()
                .withEntityProperty(URN).build());
        
        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(TITLE, "t", LEFT_OUTER_JOIN)
                .withAlias("t." + TEXTS, "tl", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.TITLE)
                .withEntityProperty("tl." + LABEL)
                .build());
        registerOrderProcessor(
                OrderProcessorBuilder.orderProcessor()
                .withQueryProperty(QueryProperty.TITLE)
                .withEntityProperty(TITLE)
                .build());
        
        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(ACRONYM, "a", LEFT_OUTER_JOIN)
                .withAlias("a." + TEXTS, "al", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.ACRONYM)
                .withEntityProperty("al." + LABEL)
                .build());
        
        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(SUBJECT_AREA, "sa", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.SUBJECT_AREA_URN)
                .withEntityProperty("sa." + ExternalItemEntity.Properties.URN)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(SECONDARY_SUBJECT_AREAS, "ssa", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.SECONDARY_SUBJECT_AREA_URN)
                .withEntityProperty("ssa." + ExternalItemEntity.Properties.URN)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(DESCRIPTION, "d", LEFT_OUTER_JOIN)
                .withAlias("d." + TEXTS, "dl", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.DESCRIPTION)
                .withEntityProperty("dl." + LABEL)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(SURVEY_TYPE, "st", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.STATISTICAL_OPERATION_TYPE_ID)
                .withEntityProperty("st." + SurveyTypeEntity.Properties.IDENTIFIER)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(OFFICIALITY_TYPE, "ot", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.OFFICIALITY_TYPE_ID)
                .withEntityProperty("ot." + OfficialityTypeEntity.Properties.IDENTIFIER)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.booleanRestrictionProcessor()
                .withQueryProperty(QueryProperty.IS_INDICATORS_SYSTEM)
                .withEntityProperty(INDICATOR_SYSTEM)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(PRODUCER, "pro", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.PRODUCER_URN)
                .withEntityProperty("pro." + ExternalItemEntity.Properties.URN)
                .build());

        registerRestrictionProcessor(
            RestrictionProcessorBuilder.instantRestrictionProcessor()
                .withQueryProperty(QueryProperty.INTERNAL_INVENTORY_DATE) 
                .withEntityProperty(INTERNAL_INVENTORY_DATE)
                .build());

        registerRestrictionProcessor(
            RestrictionProcessorBuilder.booleanRestrictionProcessor()
                .withQueryProperty(QueryProperty.CURRENTLY_ACTIVE)
                .withEntityProperty(CURRENTLY_ACTIVE)
                .build());

        registerRestrictionProcessor(
            RestrictionProcessorBuilder.enumRestrictionProcessor(StatusEnum.class)
                .withQueryProperty(QueryProperty.STATUS)
                .withEntityProperty(STATUS)
                .build());
        
        registerRestrictionProcessor(
            RestrictionProcessorBuilder.enumRestrictionProcessor(ProcStatusEnum.class)
                .withQueryProperty(QueryProperty.PROC_STATUS)
                .withEntityProperty(PROC_STATUS)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(PUBLISHER, "pub", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.PUBLISHER_URN)
                .withEntityProperty("pub." + ExternalItemEntity.Properties.URN)
                .build());

        registerRestrictionProcessor(
            RestrictionProcessorBuilder.instantRestrictionProcessor()
                .withQueryProperty(QueryProperty.INVENTORY_DATE) 
                .withEntityProperty(INVENTORY_DATE)
                .build());

        //@formatter:on
    }
}
