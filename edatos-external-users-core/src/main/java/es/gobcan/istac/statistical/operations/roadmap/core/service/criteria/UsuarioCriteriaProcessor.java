package es.gobcan.istac.statistical.operations.roadmap.core.service.criteria;

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

import es.gobcan.istac.statistical.operations.roadmap.core.domain.UsuarioEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.enumeration.Rol;
import es.gobcan.istac.statistical.operations.roadmap.core.errors.CustomParameterizedExceptionBuilder;
import es.gobcan.istac.statistical.operations.roadmap.core.errors.ErrorConstants;
import es.gobcan.istac.statistical.operations.roadmap.core.service.criteria.util.CriteriaUtil;

public class UsuarioCriteriaProcessor extends AbstractCriteriaProcessor {

    private static final String TABLE_FIELD_LOGIN = "login";
    private static final String TABLE_FIELD_NOMBRE = "nombre";
    private static final String TABLE_FIELD_APELLIDO1 = "apellido1";
    private static final String TABLE_FIELD_APELLIDO2 = "apellido2";

    private static final String ENTITY_FIELD_LOGIN = "login";
    private static final String ENTITY_FIELD_NOMBRE = "nombre";
    private static final String ENTITY_FIELD_APELLIDO1 = "apellido1";
    private static final String ENTITY_FIELD_APELLIDO2 = "apellido2";
    private static final String ENTITY_FIELD_EMAIL = "email";
    private static final String ENTITY_FIELD_DELETION_DATE = "deletionDate";

    public UsuarioCriteriaProcessor() {
        super(UsuarioEntity.class);
    }

    public enum QueryProperty {
        LOGIN, NOMBRE, APELLIDO1, APELLIDO2, ROL, EMAIL, USUARIO, DELETION_DATE
    }

    @Override
    public void registerProcessors() {
        //@formatter:off
        registerRestrictionProcessor(RestrictionProcessorBuilder.enumRestrictionProcessor(Rol.class)
                .withQueryProperty(QueryProperty.ROL)
                .withCriterionConverter(new RolCriterionBuilder())
                .build());
        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.LOGIN).sortable()
                .withEntityProperty(ENTITY_FIELD_LOGIN).build());
        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.NOMBRE).sortable()
                .withEntityProperty(ENTITY_FIELD_NOMBRE).build());
        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.APELLIDO1).sortable()
                .withEntityProperty(ENTITY_FIELD_APELLIDO1).build());
        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.APELLIDO2).sortable()
                .withEntityProperty(ENTITY_FIELD_APELLIDO2).build());
        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.USUARIO)
                .withCriterionConverter(new SqlCriterionBuilder())
                .build());
        registerRestrictionProcessor(RestrictionProcessorBuilder.dateRestrictionProcessor()
                .withQueryProperty(QueryProperty.DELETION_DATE)
                .withEntityProperty(ENTITY_FIELD_DELETION_DATE)
                .build());
        
        registerOrderProcessor(OrderProcessorBuilder.orderProcessor()
                .withQueryProperty(QueryProperty.EMAIL)
                .withEntityProperty(ENTITY_FIELD_EMAIL)
                .build());

        //@formatter:on
    }

    private static class SqlCriterionBuilder implements CriterionConverter {

        @Override
        public Criterion convertToCriterion(QueryPropertyRestriction property, CriteriaProcessorContext context) {
            if (QueryProperty.USUARIO.name().equalsIgnoreCase(property.getLeftExpression())) {
                List<String> fields = new ArrayList<>(Arrays.asList(TABLE_FIELD_LOGIN, TABLE_FIELD_NOMBRE, TABLE_FIELD_APELLIDO1, TABLE_FIELD_APELLIDO2));
                return CriteriaUtil.buildAccentAndCaseInsensitiveCriterion(property, fields);
            }
            throw new CustomParameterizedExceptionBuilder().message(String.format("Parámetro de búsqueda no soportado: '%s'", property))
                    .code(ErrorConstants.QUERY_NO_SOPORTADA, property.getLeftExpression(), property.getOperationType().name()).build();
        }
    }

    private static class RolCriterionBuilder implements CriterionConverter {

        @Override
        public Criterion convertToCriterion(QueryPropertyRestriction property, CriteriaProcessorContext context) {
            if ("EQ".equals(property.getOperationType().name())) {
                return buildUsersByRole(property);
            }
            throw new CustomParameterizedExceptionBuilder().message(String.format("Parámetro de búsqueda no soportado: '%s'", property))
                    .code(ErrorConstants.QUERY_NO_SOPORTADA, property.getLeftExpression(), property.getOperationType().name()).build();
        }

        private Criterion buildUsersByRole(QueryPropertyRestriction property) {
            String query = "{alias}.id in (select ur.usuario_fk from tb_usuarios_roles ur where rol = (%s))";
            String sql = String.format(query, property.getRightExpression());
            return Restrictions.sqlRestriction(sql);
        }
    }

}