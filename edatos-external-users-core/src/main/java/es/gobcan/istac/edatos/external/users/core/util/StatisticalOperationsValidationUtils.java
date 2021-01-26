package es.gobcan.istac.edatos.external.users.core.util;

import java.util.Collection;
import java.util.List;

import org.siemac.edatos.core.common.dto.ExternalItemDto;
import org.siemac.edatos.core.common.dto.InternationalStringDto;
import org.siemac.edatos.core.common.dto.LocalisedStringDto;
import org.siemac.edatos.core.common.enume.utils.TypeExternalArtefactsEnumUtils;
import org.siemac.edatos.core.common.exception.CommonServiceExceptionType;
import org.siemac.edatos.core.common.exception.EDatosExceptionItem;
import org.siemac.edatos.core.common.util.ValidationUtils;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalItemEntity;
import es.gobcan.istac.edatos.external.users.core.domain.InternationalStringEntity;
import es.gobcan.istac.edatos.external.users.core.domain.LocalisedStringEntity;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionType;

public class StatisticalOperationsValidationUtils extends ValidationUtils {

    /**
     * Check for a required metadata and add an exception for a failed validation.
     * 
     * @param parameter
     * @param parameterName
     * @param exceptions
     */
    public static void checkMetadataRequired(InternationalStringEntity parameter, String parameterName, List<EDatosExceptionItem> exceptions) {
        if (isEmpty(parameter)) {
            exceptions.add(new EDatosExceptionItem(CommonServiceExceptionType.METADATA_REQUIRED, parameterName));
        }
    }

    /**
     * Check for a required metadata and add an exception for a failed validation.
     * 
     * @param parameter
     * @param parameterName
     * @param exceptions
     */
    public static void checkMetadataRequired(ExternalItemEntity parameter, String parameterName, List<EDatosExceptionItem> exceptions) {
        if (isEmpty(parameter)) {
            exceptions.add(new EDatosExceptionItem(CommonServiceExceptionType.METADATA_REQUIRED, parameterName));
        } else {
            checkUrnExternalItemRequired(parameter, parameterName, exceptions);
        }
    }

    private static void checkUrnExternalItemRequired(ExternalItemEntity parameter, String parameterName, List<EDatosExceptionItem> exceptions) {
        if (TypeExternalArtefactsEnumUtils.isExternalItemOfCommonMetadataApp(parameter.getType())) {
            checkMetadataRequired(parameter.getUrn(), parameterName, exceptions);
            checkMetadataEmpty(parameter.getUrnProvider(), parameterName, exceptions);
        } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfStatisticalOperationsApp(parameter.getType())) {
            checkMetadataRequired(parameter.getUrn(), parameterName, exceptions);
            checkMetadataEmpty(parameter.getUrnProvider(), parameterName, exceptions);
        } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfSrmApp(parameter.getType())) {
            checkMetadataRequired(parameter.getUrn(), parameterName, exceptions);
            checkMetadataOptionalIsValid(parameter.getUrnProvider(), parameterName, exceptions);
        } else {
            exceptions.add(new EDatosExceptionItem(ServiceExceptionType.UNKNOWN, "Unknown type of ExternalItem"));
        }
    }

    /**
     * Check {@link InternationalStringEntity} is valid
     */
    public static void checkMetadataOptionalIsValid(InternationalStringEntity parameter, String parameterName, List<EDatosExceptionItem> exceptions) {
        if (parameter == null) {
            return;
        }

        // if it is not null, it must be complete
        if (isEmpty(parameter)) {
            exceptions.add(new EDatosExceptionItem(CommonServiceExceptionType.METADATA_INCORRECT, parameterName));
        }
    }

    /**
     * Check {@link ExternalItemEntity} is valid
     */
    public static void checkMetadataOptionalIsValid(ExternalItemEntity parameter, String parameterName, List<EDatosExceptionItem> exceptions) {
        if (parameter == null) {
            return;
        }

        // if it is not null, it must be complete
        if (isEmpty(parameter)) {
            exceptions.add(new EDatosExceptionItem(CommonServiceExceptionType.METADATA_INCORRECT, parameterName));
        } else {
            checkUrnExternalItemRequired(parameter, parameterName, exceptions);
        }
    }

    /**
     * Check if a collection metadata is valid.
     * 
     * @param parameter
     * @param parameterName
     * @param exceptions
     */

    @SuppressWarnings("rawtypes")
    public static void checkListMetadataOptionalIsValid(Collection parameter, String parameterName, List<EDatosExceptionItem> exceptions) {

        if (parameter == null) {
            return;
        }

        int exceptionSize = exceptions.size();

        for (Object item : parameter) {
            if (InternationalStringEntity.class.isInstance(item)) {
                checkMetadataOptionalIsValid((InternationalStringEntity) item, parameterName, exceptions);
            } else if (ExternalItemEntity.class.isInstance(item)) {
                checkMetadataOptionalIsValid((ExternalItemEntity) item, parameterName, exceptions);
            } else {
                checkMetadataOptionalIsValid(item, parameterName, exceptions);
            }

            // With one incorrect item is enough
            if (exceptions.size() > exceptionSize) {
                return;
            }
        }
    }

    public static Boolean isEmpty(Object parameter) {
        if (InternationalStringDto.class.isInstance(parameter)) {
            return isEmpty((InternationalStringDto) parameter);
        } else if (ExternalItemDto.class.isInstance(parameter)) {
            return isEmpty((ExternalItemDto) parameter);
        }
        return ValidationUtils.isEmpty(parameter);
    }

    /**
     * Check if an {@link InternationalStringEntity} is empty.
     */
    private static Boolean isEmpty(InternationalStringEntity parameter) {
        if (parameter == null) {
            return Boolean.TRUE;
        }
        if (parameter.getTexts().isEmpty()) {
            return Boolean.TRUE;
        }
        for (LocalisedStringEntity localisedString : parameter.getTexts()) {
            if (isEmpty(localisedString.getLabel()) || isEmpty(localisedString.getLocale())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * Check if an {@link ExternalItemEntity} is empty.
     * 
     * @param parameter
     * @return
     */
    private static Boolean isEmpty(ExternalItemEntity parameter) {
        if (parameter == null) {
            return Boolean.TRUE;
        }
        return isEmpty(parameter.getCode()) || isEmpty(parameter.getUri()) || isEmpty(parameter.getType());
    }

    private static Boolean isEmpty(InternationalStringDto parameter) {
        if (parameter == null) {
            return Boolean.TRUE;
        }
        if (parameter.getTexts().isEmpty()) {
            return Boolean.TRUE;
        }
        for (LocalisedStringDto localisedStringDto : parameter.getTexts()) {
            if (isEmpty(localisedStringDto.getLabel()) || isEmpty(localisedStringDto.getLocale())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    private static Boolean isEmpty(ExternalItemDto parameter) {
        if (parameter == null) {
            return Boolean.TRUE;
        }
        return isEmpty(parameter.getCode()) || isEmpty(parameter.getType()) || isEmpty(parameter.getUri()) || (isEmpty(parameter.getUrn()) && isEmpty(parameter.getUrnProvider()));
    }
}
