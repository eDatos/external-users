package es.gobcan.istac.edatos.external.users.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.siemac.edatos.common.test.utils.ConfigurationMockConstants;
import org.siemac.edatos.common.test.utils.EDatosAsserts;
import org.siemac.edatos.core.common.constants.CoreCommonConstants;
import org.siemac.edatos.core.common.constants.shared.RegularExpressionConstants;
import org.siemac.edatos.core.common.dto.ExternalItemDto;
import org.siemac.edatos.core.common.dto.InternationalStringDto;
import org.siemac.edatos.core.common.enume.utils.TypeExternalArtefactsEnumUtils;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalItemEntity;
import es.gobcan.istac.edatos.external.users.core.domain.InternationalStringEntity;
import es.gobcan.istac.edatos.external.users.core.domain.LocalisedStringEntity;

public class StatisticalOperationsAsserts extends EDatosAsserts {

    // -----------------------------------------------------------------
    // EXTERNAL ITEMS: DTO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsExternalItem(ExternalItemEntity entity, ExternalItemDto dto, MapperEnum mapperEnum) {
        assertEqualsNullability(entity, dto);
        if (entity == null) {
            return;
        }

        String baseApi = null;
        String baseWebApplication = null;

        if (TypeExternalArtefactsEnumUtils.isExternalItemOfCommonMetadataApp(dto.getType())) {
            baseWebApplication = ConfigurationMockConstants.COMMON_METADATA_INTERNAL_WEB_APP_URL_BASE;
            baseApi = ConfigurationMockConstants.COMMON_METADATA_EXTERNAL_API_URL_BASE;
        } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfStatisticalOperationsApp(dto.getType())) {
            baseWebApplication = ConfigurationMockConstants.STATISTICAL_OPERATIONS_INTERNAL_WEB_APP_URL_BASE;
            baseApi = ConfigurationMockConstants.STATISTICAL_OPERATIONS_INTERNAL_API_URL_BASE;
        } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfSrmApp(dto.getType())) {
            baseWebApplication = ConfigurationMockConstants.SRM_INTERNAL_WEB_APP_URL_BASE;
            baseApi = ConfigurationMockConstants.SRM_INTERNAL_API_URL_BASE;
        } else {
            fail("unexpected type of external item");
        }

        assertEqualsExternalItem(entity, dto, baseApi, baseWebApplication, mapperEnum);
    }

    private static void assertEqualsExternalItem(ExternalItemEntity entity, ExternalItemDto dto, String baseApi, String baseWebApplication, MapperEnum mapperEnum) {
        assertEqualsExternalItem(entity, dto);

        assertEqualsNullability(entity.getUri(), dto.getUri());
        if (entity.getUri() != null) {
            if (MapperEnum.ENTITY2DTO.equals(mapperEnum)) {
                assertEquals(baseApi + entity.getUri(), dto.getUri());
            } else if (MapperEnum.DTO2ENTITY.equals(mapperEnum)) {
                String expectedDoUri = dto.getUri().replaceFirst(baseApi, StringUtils.EMPTY);
                expectedDoUri = expectedDoUri.replaceFirst(RegularExpressionConstants.API_VERSION_REG_EXP, CoreCommonConstants.API_LATEST);
                assertEquals(expectedDoUri, entity.getUri());
            } else {
                fail("Mapper unexpected: " + mapperEnum);
            }
        }

        assertEqualsNullability(entity.getManagementAppUrl(), dto.getManagementAppUrl());
        if (entity.getManagementAppUrl() != null) {
            if (MapperEnum.ENTITY2DTO.equals(mapperEnum)) {
                assertEquals(baseWebApplication + entity.getManagementAppUrl(), dto.getManagementAppUrl());
            } else if (MapperEnum.DTO2ENTITY.equals(mapperEnum)) {
                assertEquals(dto.getManagementAppUrl().replaceFirst(baseWebApplication, StringUtils.EMPTY), entity.getManagementAppUrl());
            } else {
                fail("Mapper unexpected: " + mapperEnum);
            }
        }
    }

    private static void assertEqualsExternalItem(ExternalItemEntity entity, ExternalItemDto dto) {
        assertEquals(entity.getCode(), dto.getCode());
        assertEquals(entity.getCodeNested(), dto.getCodeNested());
        assertEquals(entity.getUrn(), dto.getUrn());
        assertEquals(entity.getUrnProvider(), dto.getUrnProvider());
        assertEquals(entity.getType(), dto.getType());
        assertEqualsInternationalString(entity.getTitle(), dto.getTitle());
    }

    public static void assertEqualsExternalItemCollectionMapper(Collection<ExternalItemEntity> entities, Collection<ExternalItemDto> dtos, MapperEnum mapperEnum) {
        if (entities == null) {
            entities = new ArrayList<>();
        }

        if (dtos == null) {
            dtos = new ArrayList<>();
        }

        assertEquals(entities.size(), dtos.size());
        for (ExternalItemEntity entity : entities) {
            boolean found = false;
            Iterator<ExternalItemDto> itDto = dtos.iterator();
            while (itDto.hasNext() && !found) {
                ExternalItemDto dto = itDto.next();
                found = true;
                try {
                    assertEqualsExternalItem(entity, dto, mapperEnum);
                } catch (AssertionError e) {
                    found = false;
                }
            }
            if (!found) {
                Assert.fail("Not equal collections");
            }
        }
    }

    // -----------------------------------------------------------------
    // INTERNATIONAL STRING: DO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsInternationalString(InternationalStringEntity expected, InternationalStringEntity actual) {
        assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }

        assertEquals(expected.getTexts().size(), actual.getTexts().size());
        for (LocalisedStringEntity localisedStringExpected : expected.getTexts()) {
            assertEquals(localisedStringExpected.getLabel(), actual.getLocalisedLabel(localisedStringExpected.getLocale()));
        }
    }

    public static void assertEqualsInternationalString(InternationalStringEntity internationalString, String locale1, String label1, String locale2, String label2) {
        int count = 0;
        if (locale1 != null) {
            assertEquals(label1, internationalString.getLocalisedLabel(locale1));
            count++;
        }
        if (locale2 != null) {
            assertEquals(label2, internationalString.getLocalisedLabel(locale2));
            count++;
        }
        assertEquals(count, internationalString.getTexts().size());
    }

    // -----------------------------------------------------------------
    // INTERNATIONAL STRING: DTO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsInternationalString(InternationalStringEntity entity, InternationalStringDto dto) {
        assertEqualsNullability(entity, dto);
        if (entity == null) {
            return;
        }

        assertEquals(entity.getTexts().size(), dto.getTexts().size());
        for (LocalisedStringEntity localisedStringExpected : entity.getTexts()) {
            assertEquals(localisedStringExpected.getLabel(), dto.getLocalisedLabel(localisedStringExpected.getLocale()));
        }
    }
}
