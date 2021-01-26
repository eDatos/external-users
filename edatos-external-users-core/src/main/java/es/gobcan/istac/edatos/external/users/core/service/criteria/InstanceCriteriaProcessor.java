package es.gobcan.istac.edatos.external.users.core.service.criteria;

import static org.hibernate.sql.JoinType.LEFT_OUTER_JOIN;

import com.arte.libs.grammar.orm.jpa.criteria.AbstractCriteriaProcessor;
import com.arte.libs.grammar.orm.jpa.criteria.OrderProcessorBuilder;
import com.arte.libs.grammar.orm.jpa.criteria.RestrictionProcessorBuilder;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalItemEntity;
import es.gobcan.istac.edatos.external.users.core.domain.InstanceEntity;
import es.gobcan.istac.edatos.external.users.core.domain.InstanceTypeEntity;
import es.gobcan.istac.edatos.external.users.core.domain.InternationalStringEntity;
import es.gobcan.istac.edatos.external.users.core.domain.LocalisedStringEntity;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.ProcStatusEnum;

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
                .withEntityProperty(InstanceEntity.Properties.CODE).build());
        registerOrderProcessor(
                OrderProcessorBuilder.orderProcessor()
                .withQueryProperty(QueryProperty.ID)
                .withEntityProperty(InstanceEntity.Properties.CODE)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.CODE).sortable()
                .withEntityProperty(InstanceEntity.Properties.CODE).build());
        registerOrderProcessor(
                OrderProcessorBuilder.orderProcessor()
                .withQueryProperty(QueryProperty.CODE)
                .withEntityProperty(InstanceEntity.Properties.CODE)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.URN).sortable()
                .withEntityProperty(InstanceEntity.Properties.URN).build());
        
        registerRestrictionProcessor(
                RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(InstanceEntity.Properties.TITLE, "t", LEFT_OUTER_JOIN)
                .withAlias("t." + InternationalStringEntity.Properties.TEXTS, "tl", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.TITLE)
                .withEntityProperty("tl." + LocalisedStringEntity.Properties.LABEL)
                .build());
        registerOrderProcessor(
                OrderProcessorBuilder.orderProcessor()
                .withQueryProperty(QueryProperty.TITLE)
                .withEntityProperty(InstanceEntity.Properties.TITLE)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(InstanceEntity.Properties.ACRONYM, "a", LEFT_OUTER_JOIN)
                .withAlias("a." + InternationalStringEntity.Properties.TEXTS, "al", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.ACRONYM)
                .withEntityProperty("al." + LocalisedStringEntity.Properties.LABEL)
                .build());

        registerRestrictionProcessor(
                RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(InstanceEntity.Properties.DATA_DESCRIPTION, "dd", LEFT_OUTER_JOIN)
                .withAlias("dd." + InternationalStringEntity.Properties.TEXTS, "ddl", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.DATA_DESCRIPTION)
                .withEntityProperty("ddl." + LocalisedStringEntity.Properties.LABEL)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(InstanceEntity.Properties.GEOGRAPHIC_GRANULARITY, "gg", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.GEOGRAPHIC_GRANULARITY_URN)
                .withEntityProperty("gg." + ExternalItemEntity.Properties.URN)
                .build());
        
        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(InstanceEntity.Properties.TEMPORAL_GRANULARITY, "tg", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.TEMPORAL_GRANULARITY_URN)
                .withEntityProperty("tg." + ExternalItemEntity.Properties.URN)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(InstanceEntity.Properties.INSTANCE_TYPE, "it", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.INSTANCE_TYPE_ID)
                .withEntityProperty("it." + InstanceTypeEntity.Properties.IDENTIFIER)
                .build());

        registerRestrictionProcessor(
                RestrictionProcessorBuilder.instantRestrictionProcessor()
                    .withQueryProperty(QueryProperty.INTERNAL_INVENTORY_DATE) 
                    .withEntityProperty(InstanceEntity.Properties.INTERNAL_INVENTORY_DATE)
                    .build());
        
        registerRestrictionProcessor(
                RestrictionProcessorBuilder.enumRestrictionProcessor(ProcStatusEnum.class)
                    .withQueryProperty(QueryProperty.PROC_STATUS)
                    .withEntityProperty(InstanceEntity.Properties.PROC_STATUS)
                    .build());

        registerRestrictionProcessor(
            RestrictionProcessorBuilder.instantRestrictionProcessor()
                .withQueryProperty(QueryProperty.INVENTORY_DATE) 
                .withEntityProperty(InstanceEntity.Properties.INVENTORY_DATE)
                .build());
        //@formatter:on
    }
}
