package es.gobcan.istac.edatos.external.users.core.service.criteria;

import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ProcStatus;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Status;

import com.arte.libs.grammar.orm.jpa.criteria.AbstractCriteriaProcessor;
import com.arte.libs.grammar.orm.jpa.criteria.OrderProcessorBuilder;
import com.arte.libs.grammar.orm.jpa.criteria.RestrictionProcessorBuilder;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalItemEntity;
import es.gobcan.istac.edatos.external.users.core.domain.InternationalStringEntity;
import es.gobcan.istac.edatos.external.users.core.domain.LocalisedStringEntity;
import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;

import static org.hibernate.sql.JoinType.LEFT_OUTER_JOIN;

public class OperationCriteriaProcessor extends AbstractCriteriaProcessor {

    public OperationCriteriaProcessor() {
        super(OperationEntity.class);
    }

    public enum QueryProperty {
        ID,
        CODE,
        URN,
        TITLE,
        ACRONYM,
        SUBJECT_AREA_URN,
        SECONDARY_SUBJECT_AREA_URN,
        DESCRIPTION,
        STATISTICAL_OPERATION_TYPE_ID,
        IS_INDICATORS_SYSTEM,
        PRODUCER_URN,
        INTERNAL_INVENTORY_DATE,
        CURRENTLY_ACTIVE,
        STATUS,
        PROC_STATUS,
        PUBLISHER_URN,
        INVENTORY_DATE,
    }

    @Override
    public void registerProcessors() {
        //@formatter:off

        // When the ID is specified, a search by CODE must be performed.
        // In the internal and external API the search by ID correspond to a search by CODE.
        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.ID).sortable()
                .withEntityProperty(OperationEntity.Properties.CODE).build());
        registerOrderProcessor(
                OrderProcessorBuilder.orderProcessor()
                .withQueryProperty(QueryProperty.ID)
                .withEntityProperty(OperationEntity.Properties.CODE)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.CODE).sortable()
                .withEntityProperty(OperationEntity.Properties.CODE).build());
        registerOrderProcessor(
                OrderProcessorBuilder.orderProcessor()
                .withQueryProperty(QueryProperty.CODE)
                .withEntityProperty(OperationEntity.Properties.CODE)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.URN).sortable()
                .withEntityProperty(OperationEntity.Properties.URN).build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(OperationEntity.Properties.TITLE, "t", LEFT_OUTER_JOIN)
                .withAlias("t." + InternationalStringEntity.Properties.TEXTS, "tl", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.TITLE)
                .withEntityProperty("tl." + LocalisedStringEntity.Properties.LABEL)
                .build());
        registerOrderProcessor(
                OrderProcessorBuilder.orderProcessor()
                .withQueryProperty(QueryProperty.TITLE)
                .withEntityProperty(OperationEntity.Properties.TITLE)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(OperationEntity.Properties.ACRONYM, "a", LEFT_OUTER_JOIN)
                .withAlias("a." + InternationalStringEntity.Properties.TEXTS, "al", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.ACRONYM)
                .withEntityProperty("al." + LocalisedStringEntity.Properties.LABEL)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(OperationEntity.Properties.SUBJECT_AREA, "sa", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.SUBJECT_AREA_URN)
                .withEntityProperty("sa." + ExternalItemEntity.Properties.URN)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(OperationEntity.Properties.SECONDARY_SUBJECT_AREAS, "ssa", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.SECONDARY_SUBJECT_AREA_URN)
                .withEntityProperty("ssa." + ExternalItemEntity.Properties.URN)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(OperationEntity.Properties.DESCRIPTION, "d", LEFT_OUTER_JOIN)
                .withAlias("d." + InternationalStringEntity.Properties.TEXTS, "dl", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.DESCRIPTION)
                .withEntityProperty("dl." + LocalisedStringEntity.Properties.LABEL)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.booleanRestrictionProcessor()
                .withQueryProperty(QueryProperty.IS_INDICATORS_SYSTEM)
                .withEntityProperty(OperationEntity.Properties.INDICATOR_SYSTEM)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(OperationEntity.Properties.PRODUCER, "pro", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.PRODUCER_URN)
                .withEntityProperty("pro." + ExternalItemEntity.Properties.URN)
                .build());

        registerRestrictionProcessor(
            RestrictionProcessorBuilder.instantRestrictionProcessor()
                .withQueryProperty(QueryProperty.INTERNAL_INVENTORY_DATE)
                .withEntityProperty(OperationEntity.Properties.INTERNAL_INVENTORY_DATE)
                .build());

        registerRestrictionProcessor(
            RestrictionProcessorBuilder.booleanRestrictionProcessor()
                .withQueryProperty(QueryProperty.CURRENTLY_ACTIVE)
                .withEntityProperty(OperationEntity.Properties.CURRENTLY_ACTIVE)
                .build());

        registerRestrictionProcessor(
            RestrictionProcessorBuilder.enumRestrictionProcessor(Status.class)
                .withQueryProperty(QueryProperty.STATUS)
                .withEntityProperty(OperationEntity.Properties.STATUS)
                .build());

        registerRestrictionProcessor(
            RestrictionProcessorBuilder.enumRestrictionProcessor(ProcStatus.class)
                .withQueryProperty(QueryProperty.PROC_STATUS)
                .withEntityProperty(OperationEntity.Properties.PROC_STATUS)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(OperationEntity.Properties.PUBLISHER, "pub", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.PUBLISHER_URN)
                .withEntityProperty("pub." + ExternalItemEntity.Properties.URN)
                .build());

        registerRestrictionProcessor(
            RestrictionProcessorBuilder.instantRestrictionProcessor()
                .withQueryProperty(QueryProperty.INVENTORY_DATE)
                .withEntityProperty(OperationEntity.Properties.INVENTORY_DATE)
                .build());

        //@formatter:on
    }
}
