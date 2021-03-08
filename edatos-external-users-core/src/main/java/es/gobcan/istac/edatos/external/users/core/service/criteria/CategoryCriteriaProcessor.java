package es.gobcan.istac.edatos.external.users.core.service.criteria;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.stereotype.Component;

import com.arte.libs.grammar.orm.jpa.criteria.AbstractCriteriaProcessor;
import com.arte.libs.grammar.orm.jpa.criteria.RestrictionProcessorBuilder;

import es.gobcan.istac.edatos.external.users.core.domain.CategoryEntity;

@Component
public class CategoryCriteriaProcessor extends AbstractCriteriaProcessor {

    public static final String ENTITY_FIELD_ID = validateFieldExists("id");
    public static final String ENTITY_FIELD_CODE = validateFieldExists("code");

    public enum QueryProperty {
        ID,
        CODE,
    }

    public CategoryCriteriaProcessor() {
        super(CategoryEntity.class);
    }

    /**
     * Tells if a field name exists for a class.
     * <p>
     * The reasoning behind this method is that it fails quickly if the declared
     * entity field name doesn't exists, either because it was changed, mispelled, or
     * another reason that may cause to fail silently in production otherwise, if not
     * correctly tested.
     * <p>
     * Be aware that to fail at startup this class need to be instantiated early.
     * Marking this as a {@code @Component} will delegate this to Spring.
     *
     * @implNote Limitations: this method DOES NOT check for nested entity properties
     *           like {@code user.login}.
     * @param fieldName the name of the field that is going to be checked.
     * @return the same field name passed as argument, if it exists.
     * @throws IllegalArgumentException if the field doesn't exists.
     */
    private static String validateFieldExists(String fieldName) {
        List<String> fieldNames = FieldUtils.getAllFieldsList(CategoryEntity.class).stream().map(Field::getName).collect(Collectors.toList());
        if (fieldNames.contains(fieldName)) {
            return fieldName;
        } else {
            throw new IllegalArgumentException("Field name '" + fieldName + "' doesn't exists in '" + CategoryCriteriaProcessor.class.toString() + "'");
        }
    }

    @Override
    public void registerProcessors() {
        // @formatter:off
        registerRestrictionProcessor(RestrictionProcessorBuilder
                .longRestrictionProcessor()
                .withQueryProperty(QueryProperty.ID).sortable()
                .withEntityProperty(ENTITY_FIELD_ID)
                .build());
        registerRestrictionProcessor(RestrictionProcessorBuilder
                .stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.CODE).sortable()
                .withEntityProperty(ENTITY_FIELD_CODE)
                .build());
        // @formatter:on
    }
}
