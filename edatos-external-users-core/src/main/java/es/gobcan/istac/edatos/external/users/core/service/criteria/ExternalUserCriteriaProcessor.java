package es.gobcan.istac.edatos.external.users.core.service.criteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.arte.libs.grammar.domain.QueryPropertyRestriction;
import com.arte.libs.grammar.orm.jpa.criteria.AbstractCriteriaProcessor;
import com.arte.libs.grammar.orm.jpa.criteria.CriteriaProcessorContext;
import com.arte.libs.grammar.orm.jpa.criteria.OrderProcessorBuilder;
import com.arte.libs.grammar.orm.jpa.criteria.RestrictionProcessorBuilder;
import com.arte.libs.grammar.orm.jpa.criteria.converter.CriterionConverter;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Language;
import es.gobcan.istac.edatos.external.users.core.errors.CustomParameterizedExceptionBuilder;
import es.gobcan.istac.edatos.external.users.core.errors.ErrorConstants;
import es.gobcan.istac.edatos.external.users.core.service.criteria.util.CriteriaUtil;

public class ExternalUserCriteriaProcessor extends AbstractCriteriaProcessor {

    private static final String TABLE_FIELD_EMAIL = "email";
    private static final String TABLE_FIELD_NAME = "name";
    private static final String TABLE_FIELD_SURNAME1 = "surname1";
    private static final String TABLE_FIELD_SURNAME2 = "surname2";

    private static final String ENTITY_FIELD_NAME = "name";
    private static final String ENTITY_FIELD_SURNAME1 = "surname1";
    private static final String ENTITY_FIELD_SURNAME2 = "surname2";
    private static final String ENTITY_FIELD_EMAIL = "email";
    private static final String ENTITY_FIELD_DELETION_DATE = "deletionDate";
    private static final String ENTITY_FIELD_LANGUAGE = "language";
    private static final String ENTITY_FIELD_TREATMENT = "treatment";
    
    private static final String ENTITY_FIELD_FAVORITES = "favorites";

    public enum QueryProperty {
        NAME,
        SURNAME1,
        SURNAME2,
        EMAIL,
        LANGUAGE,
        TREATMENT,
        DELETION_DATE,
        FULLNAME,
        FAVORITES
    }

    public ExternalUserCriteriaProcessor() {
        super(ExternalUserEntity.class);
    }

    @Override
    public void registerProcessors() {
        //@formatter:off
        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.NAME).sortable()
                .withEntityProperty(ENTITY_FIELD_NAME).build());
        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.SURNAME1).sortable()
                .withEntityProperty(ENTITY_FIELD_SURNAME1).build());
        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.SURNAME2).sortable()
                .withEntityProperty(ENTITY_FIELD_SURNAME2).build());
        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.FULLNAME)
                .withCriterionConverter(new SqlCriterionBuilder())
                .build());
        registerRestrictionProcessor(RestrictionProcessorBuilder.dateRestrictionProcessor()
                .withQueryProperty(QueryProperty.DELETION_DATE)
                .withEntityProperty(ENTITY_FIELD_DELETION_DATE)
                .build());
        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.EMAIL).sortable()
                .withEntityProperty(ENTITY_FIELD_EMAIL)
                .build());
        registerRestrictionProcessor(RestrictionProcessorBuilder.enumRestrictionProcessor(Language.class)
                .withQueryProperty(QueryProperty.LANGUAGE)
                .withEntityProperty(ENTITY_FIELD_LANGUAGE)
                .build());
        registerOrderProcessor(OrderProcessorBuilder.orderProcessor()
                .withQueryProperty(QueryProperty.LANGUAGE)
                .withEntityProperty(ENTITY_FIELD_LANGUAGE)
                .build());
        registerOrderProcessor(OrderProcessorBuilder.orderProcessor()
                .withQueryProperty(QueryProperty.TREATMENT)
                .withEntityProperty(ENTITY_FIELD_TREATMENT)
                .build());
        registerRestrictionProcessor(
                RestrictionProcessorBuilder.restrictionProcessor()
                    .withQueryProperty(QueryProperty.FAVORITES)
                    .withCriterionConverter(new SqlCriterionBuilder())
                .build());
        //@formatter:on
    }

    private static class SqlCriterionBuilder implements CriterionConverter {

        @Override
        public Criterion convertToCriterion(QueryPropertyRestriction property, CriteriaProcessorContext context) {
            if (QueryProperty.FULLNAME.name().equalsIgnoreCase(property.getLeftExpression())) {
                List<String> fields = new ArrayList<>(Arrays.asList(TABLE_FIELD_EMAIL, TABLE_FIELD_NAME, TABLE_FIELD_SURNAME1, TABLE_FIELD_SURNAME2));
                return CriteriaUtil.buildAccentAndCaseInsensitiveCriterion(property, fields);
            }
            if (QueryProperty.FAVORITES.name().equalsIgnoreCase(property.getLeftExpression())) {
            	return favoritesCriterion(property);
            }
            throw new CustomParameterizedExceptionBuilder().message(String.format("Parámetro de búsqueda no soportado: '%s'", property))
                    .code(ErrorConstants.QUERY_NO_SOPORTADA, property.getLeftExpression(), property.getOperationType().name()).build();
        }
        
        private Criterion favoritesCriterion(QueryPropertyRestriction property) {
        	// @formatter:off
        	String sql = "{alias}.ID IN ( "
        			+ " SELECT tf.external_user_fk "
        			+ " FROM tb_favorites tf "
        			+ " WHERE tf.category_fk IN " + property.getRightExpressions().toString().replace('[', '(').replace(']', ')')
        			+ " ) ";
        	// @formatter:on
        	return Restrictions.sqlRestriction(sql);
        }
    }
}
