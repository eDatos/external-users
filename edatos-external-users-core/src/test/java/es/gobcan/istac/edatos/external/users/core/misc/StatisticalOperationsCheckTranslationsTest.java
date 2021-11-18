package es.gobcan.istac.edatos.external.users.core.misc;

import java.util.Locale;

import org.siemac.edatos.common.test.CheckTranslationsTestBase;

import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionParameters;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionType;

public class StatisticalOperationsCheckTranslationsTest extends CheckTranslationsTestBase {

    // ----------------------------------------------------------------------------------------
    // MORE TESTS
    // ----------------------------------------------------------------------------------------

    // ----------------------------------------------------------------------------------------
    // OVERRIDE METHODS
    // ----------------------------------------------------------------------------------------

    @Override
    @SuppressWarnings("rawtypes")
    public Class[] getServiceExceptionTypeClasses() {
        return new Class[]{ServiceExceptionType.class};
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Class[] getServiceExceptionParameterClasses() {
        return new Class[]{ServiceExceptionParameters.class};
    }

    @Override
    public Locale[] getLocalesToTranslate() {
        return LOCALES_TO_TRANSLATE;
    }
}
