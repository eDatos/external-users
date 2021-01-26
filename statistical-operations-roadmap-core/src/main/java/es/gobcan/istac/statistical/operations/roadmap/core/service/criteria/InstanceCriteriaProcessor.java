package es.gobcan.istac.statistical.operations.roadmap.core.service.criteria;

import static es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceEntity.Properties.ACRONYM;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceEntity.Properties.CODE;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceEntity.Properties.DATA_DESCRIPTION;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceEntity.Properties.GEOGRAPHIC_GRANULARITY;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceEntity.Properties.INSTANCE_TYPE;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceEntity.Properties.INTERNAL_INVENTORY_DATE;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceEntity.Properties.INVENTORY_DATE;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceEntity.Properties.PROC_STATUS;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceEntity.Properties.TEMPORAL_GRANULARITY;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceEntity.Properties.TITLE;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceEntity.Properties.URN;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.InternationalStringEntity.Properties.TEXTS;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.LocalisedStringEntity.Properties.LABEL;
import static org.hibernate.sql.JoinType.LEFT_OUTER_JOIN;

import com.arte.libs.grammar.orm.jpa.criteria.AbstractCriteriaProcessor;
import com.arte.libs.grammar.orm.jpa.criteria.OrderProcessorBuilder;
import com.arte.libs.grammar.orm.jpa.criteria.RestrictionProcessorBuilder;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.ExternalItemEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceTypeEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.enumeration.ProcStatusEnum;

public class InstanceCriteriaProcessor extends AbstractCriteriaProcessor {

    public InstanceCriteriaProcessor() {
        super(InstanceEntity.class);
    }

    public enum QueryProperty {
        // @formatter:off
        ID,
        CODE,
        URN,
        TITLE,
        ACRONYM,
        DATA_DESCRIPTION,
        GEOGRAPHIC_GRANULARITY_URN,
        TEMPORAL_GRANULARITY_URN,
        INSTANCE_TYPE_ID,
        INTERNAL_INVENTORY_DATE,
        PROC_STATUS,
        INVENTORY_DATE;
        // @formatter:on
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
        
        registerRestrictionProcessor(
                RestrictionProcessorBuilder.stringRestrictionProcessor()
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

        registerRestrictionProcessor(
                RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(DATA_DESCRIPTION, "dd", LEFT_OUTER_JOIN)
                .withAlias("dd." + TEXTS, "ddl", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.DATA_DESCRIPTION)
                .withEntityProperty("ddl." + LABEL)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(GEOGRAPHIC_GRANULARITY, "gg", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.GEOGRAPHIC_GRANULARITY_URN)
                .withEntityProperty("gg." + ExternalItemEntity.Properties.URN)
                .build());
        
        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(TEMPORAL_GRANULARITY, "tg", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.TEMPORAL_GRANULARITY_URN)
                .withEntityProperty("tg." + ExternalItemEntity.Properties.URN)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(INSTANCE_TYPE, "it", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.INSTANCE_TYPE_ID)
                .withEntityProperty("it." + InstanceTypeEntity.Properties.IDENTIFIER)
                .build());

        registerRestrictionProcessor(
                RestrictionProcessorBuilder.instantRestrictionProcessor()
                    .withQueryProperty(QueryProperty.INTERNAL_INVENTORY_DATE) 
                    .withEntityProperty(INTERNAL_INVENTORY_DATE)
                    .build());
        
        registerRestrictionProcessor(
                RestrictionProcessorBuilder.enumRestrictionProcessor(ProcStatusEnum.class)
                    .withQueryProperty(QueryProperty.PROC_STATUS)
                    .withEntityProperty(PROC_STATUS)
                    .build());

        registerRestrictionProcessor(
            RestrictionProcessorBuilder.instantRestrictionProcessor()
                .withQueryProperty(QueryProperty.INVENTORY_DATE) 
                .withEntityProperty(INVENTORY_DATE)
                .build());
        //@formatter:on
    }
}
