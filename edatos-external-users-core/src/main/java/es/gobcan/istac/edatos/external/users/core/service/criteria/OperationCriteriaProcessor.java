package es.gobcan.istac.edatos.external.users.core.service.criteria;

import static org.hibernate.sql.JoinType.LEFT_OUTER_JOIN;

import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ProcStatus;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Status;

import com.arte.libs.grammar.orm.jpa.criteria.AbstractCriteriaProcessor;
import com.arte.libs.grammar.orm.jpa.criteria.OrderProcessorBuilder;
import com.arte.libs.grammar.orm.jpa.criteria.RestrictionProcessorBuilder;

import es.gobcan.istac.edatos.external.users.core.domain.InternationalStringEntity;
import es.gobcan.istac.edatos.external.users.core.domain.LocalisedStringEntity;
import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;

public class OperationCriteriaProcessor extends AbstractCriteriaProcessor {

    public enum QueryProperty {
        ID,
        CODE,
        URN,
        NAME,
        DESCRIPTION,
        STATUS,
        PROC_STATUS,
        CATEGORY_ID,
    }

    public OperationCriteriaProcessor() {
        super(OperationEntity.class);
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
                .withAlias(OperationEntity.Properties.NAME, "t", LEFT_OUTER_JOIN)
                .withAlias("t." + InternationalStringEntity.Properties.TEXTS, "tl", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.NAME)
                .withEntityProperty("tl." + LocalisedStringEntity.Properties.LABEL)
                .build());
        registerOrderProcessor(
                OrderProcessorBuilder.orderProcessor()
                .withQueryProperty(QueryProperty.NAME)
                .withEntityProperty(OperationEntity.Properties.NAME)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(OperationEntity.Properties.DESCRIPTION, "d", LEFT_OUTER_JOIN)
                .withAlias("d." + InternationalStringEntity.Properties.TEXTS, "dl", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.DESCRIPTION)
                .withEntityProperty("dl." + LocalisedStringEntity.Properties.LABEL)
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
                .withQueryProperty(QueryProperty.CATEGORY_ID)
                .withAlias(OperationEntity.Properties.CATEGORY, OperationEntity.Properties.CATEGORY)
                .withEntityProperty(OperationEntity.Properties.CATEGORY + ".id")
                .build());
        //@formatter:on
    }
}
