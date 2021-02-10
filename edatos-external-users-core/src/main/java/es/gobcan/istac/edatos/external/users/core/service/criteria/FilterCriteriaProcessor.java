package es.gobcan.istac.edatos.external.users.core.service.criteria;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.stereotype.Component;

import com.arte.libs.grammar.orm.jpa.criteria.AbstractCriteriaProcessor;
import com.arte.libs.grammar.orm.jpa.criteria.RestrictionProcessorBuilder;

import es.gobcan.istac.edatos.external.users.core.domain.FilterEntity;

@Component
public class FilterCriteriaProcessor extends AbstractCriteriaProcessor {

    public static final String ENTITY_FIELD_ID = validateFieldExists("id");
    public static final String ENTITY_FIELD_NAME = validateFieldExists("name");
    public static final String ENTITY_FIELD_USER = validateFieldExists("user");
    public static final String ENTITY_FIELD_PERMALINK = validateFieldExists("permalink");
    public static final String ENTITY_FIELD_CREATED_DATE = validateFieldExists("createdDate");
    public static final String ENTITY_FIELD_LAST_MODIFIED_DATE = validateFieldExists("lastModifiedDate");
    public static final String ENTITY_FIELD_LAST_ACCESS_DATE = validateFieldExists("lastAccessDate");
    public static final String ENTITY_FIELD_NOTES = validateFieldExists("notes");

    public enum QueryProperty {
        ID,
        NAME,
        LOGIN,
        PERMALINK,
        CREATED_DATE,
        LAST_MODIFIED_DATE,
        LAST_ACCESS_DATE,
        NOTES,
    }

    public FilterCriteriaProcessor() {
        super(FilterEntity.class);
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
     * like {@code user.login}.
     *
     * TODO(EDATOS-3280): Ask @frodgar if there's a better way to handle this. Maybe this should be
     *   implemented by com.arte.libs.grammar.orm.jpa.criteria.
     *
     * @param fieldName the name of the field that is going to be checked.
     * @return the same field name passed as argument, if it exists.
     *
     * @throws IllegalArgumentException if the field doesn't exists.
     */
    private static String validateFieldExists(String fieldName) {
        // TODO(EDATOS-3280): Ask @frodgar why AbstractCriteriaProcessor::entityClass is private.
        //  That attribute is useful in this case to avoid repetition of FilterEntity.class.
        List<String> fieldNames = FieldUtils.getAllFieldsList(FilterEntity.class)
                                            .stream()
                                            .map(Field::getName)
                                            .collect(Collectors.toList());
        if (fieldNames.contains(fieldName)) {
            return fieldName;
        } else {
            throw new IllegalArgumentException(
                "Field name '" + fieldName + "' doesn't exists in '" + FilterCriteriaProcessor.class.toString() + "'");
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
                .withQueryProperty(QueryProperty.NAME).sortable()
                .withEntityProperty(ENTITY_FIELD_NAME)
                .build());
        registerRestrictionProcessor(RestrictionProcessorBuilder
                .stringRestrictionProcessor()
                .withAlias(ENTITY_FIELD_USER, ENTITY_FIELD_USER)
                .withQueryProperty(QueryProperty.LOGIN)
                .withEntityProperty(ENTITY_FIELD_USER + ".login")
                .build());
        registerRestrictionProcessor(RestrictionProcessorBuilder
                .stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.PERMALINK).sortable()
                .withEntityProperty(ENTITY_FIELD_PERMALINK)
                .build());
        registerRestrictionProcessor(RestrictionProcessorBuilder
                .dateRestrictionProcessor()
                .withQueryProperty(QueryProperty.CREATED_DATE).sortable()
                .withEntityProperty(ENTITY_FIELD_CREATED_DATE)
                .build());
        registerRestrictionProcessor(RestrictionProcessorBuilder
                .dateRestrictionProcessor()
                .withQueryProperty(QueryProperty.LAST_MODIFIED_DATE).sortable()
                .withEntityProperty(ENTITY_FIELD_LAST_MODIFIED_DATE)
                .build());
        registerRestrictionProcessor(RestrictionProcessorBuilder
                .dateRestrictionProcessor()
                .withQueryProperty(QueryProperty.LAST_ACCESS_DATE).sortable()
                .withEntityProperty(ENTITY_FIELD_LAST_ACCESS_DATE)
                .build());
        registerRestrictionProcessor(RestrictionProcessorBuilder
                .stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.NOTES).sortable()
                .withEntityProperty(ENTITY_FIELD_NOTES)
                .build());
        // @formatter:on
    }
}
