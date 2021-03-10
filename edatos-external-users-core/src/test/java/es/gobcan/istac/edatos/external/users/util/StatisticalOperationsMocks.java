package es.gobcan.istac.edatos.external.users.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.siemac.edatos.common.test.utils.ConfigurationMockConstants;
import org.siemac.edatos.core.common.constants.CoreCommonConstants;
import org.siemac.edatos.core.common.dto.ExternalItemDto;
import org.siemac.edatos.core.common.dto.InternationalStringDto;
import org.siemac.edatos.core.common.dto.LocalisedStringDto;
import org.siemac.edatos.core.common.enume.TypeExternalArtefactsEnum;
import org.siemac.edatos.core.common.enume.utils.TypeExternalArtefactsEnumUtils;
import org.siemac.edatos.core.common.util.GeneratorUrnUtils;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalItemEntity;
import es.gobcan.istac.edatos.external.users.core.domain.InternationalStringEntity;
import es.gobcan.istac.edatos.external.users.core.domain.LocalisedStringEntity;
import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;
import es.gobcan.istac.edatos.external.users.core.domain.vo.InternationalStringVO;
import es.gobcan.istac.edatos.external.users.core.domain.vo.LocalisedStringVO;

import static org.junit.Assert.fail;

public final class StatisticalOperationsMocks {

    private static final String[] MAINTAINER_MOCK = new String[]{"MaintainerMock"};
    private static final String CODELIST_MOCK = "CodelistMock";
    private static final String AGENCY_SCHEME_MOCK = "AgencySchemeMock";

    private StatisticalOperationsMocks() {
    }

    //public static FamilyEntity mockFamily() {
    //    FamilyEntity family = new FamilyEntity();
    //
    //    // IDENTIFIER
    //    family.setCode("FAMILY-" + RandomStringUtils.random(50, true, true));
    //
    //    // TITLE
    //    InternationalStringEntity title = new InternationalStringEntity();
    //    LocalisedStringEntity title_es = new LocalisedStringEntity();
    //    title_es.setLabel("Título en español de familia");
    //    title_es.setLocale("es");
    //    LocalisedStringEntity title_en = new LocalisedStringEntity();
    //    title_en.setLabel("Título en inglés de familia");
    //    title_en.setLocale("en");
    //    title.addText(title_es);
    //    title.addText(title_en);
    //    family.setTitle(title);
    //
    //    // DESCRIPTION
    //    InternationalStringEntity description = new InternationalStringEntity();
    //    LocalisedStringEntity description_es = new LocalisedStringEntity();
    //    description_es.setLabel("Descripción en español de familia");
    //    description_es.setLocale("es");
    //    LocalisedStringEntity description_en = new LocalisedStringEntity();
    //    description_en.setLabel("Descripción en inglés de familia");
    //    description_en.setLocale("en");
    //    description.addText(description_es);
    //    description.addText(description_en);
    //    family.setDescription(description);
    //
    //    // PROC_STATUS
    //    family.setProcStatus(ProcStatusEnum.DRAFT);
    //
    //    return family;
    //}

    public static OperationEntity mockOperation() {
        OperationEntity operation = new OperationEntity();

        // IDENTIFIER
        operation.setCode("OPE-" + RandomStringUtils.random(7, true, true));

        // TITLE
        InternationalStringVO title = new InternationalStringVO();
        LocalisedStringVO title_es = new LocalisedStringVO();
        title_es.setLabel("Título en español de operación");
        title_es.setLocale("es");
        LocalisedStringVO title_en = new LocalisedStringVO();
        title_en.setLabel("Título en inglés de operación");
        title_en.setLocale("en");
        title.addText(title_es);
        title.addText(title_en);
        operation.setTitle(title);

        // ACRONYM
        InternationalStringVO acronym = new InternationalStringVO();
        LocalisedStringVO acronym_es = new LocalisedStringVO();
        acronym_es.setLabel("Acronynm en español de operación");
        acronym_es.setLocale("es");
        LocalisedStringVO acronym_en = new LocalisedStringVO();
        acronym_en.setLabel("Acronynm en inglés de operación");
        acronym_en.setLocale("en");
        acronym.addText(acronym_es);
        acronym.addText(acronym_en);
        operation.setAcronym(acronym);

        // CURRENTLY_ACTIVE
        operation.setCurrentlyActive(false);

        // SUBJECT_AREA
        //operation.setSubjectArea(mockExternalItem("HEALTH", "/uri/test/category", "URN:CATEGORY:HEALTH", "URN:CATEGORY:HEALTH:provider", TypeExternalArtefactsEnum.CATEGORY));

        // INDICATOR_SYSTEM
        operation.setIndicatorSystem(false);

        return operation;
    }

    public static OperationEntity mockOperationWithDescription() {
        OperationEntity operation = mockOperation();

        // DESCRIPTION
        InternationalStringVO description = new InternationalStringVO();
        LocalisedStringVO description_es = new LocalisedStringVO();
        description_es.setLabel("Acronynm en español de operación");
        description_es.setLocale("es");
        LocalisedStringVO description_en = new LocalisedStringVO();
        description_en.setLabel("Acronynm en inglés de operación");
        description_en.setLocale("en");
        description.addText(description_es);
        description.addText(description_en);
        operation.setDescription(description);

        return operation;
    }

    //public static OperationEntity mockOperationForInternalPublishing(OfficialityTypeEntity officialityType, SurveyTypeEntity surveyType) {
    //    OperationEntity operation = StatisticalOperationsMocks.mockOperation();
    //
    //    // OBJECTIVE
    //    InternationalStringVO objective = new InternationalStringVO();
    //    LocalisedStringVO objective_es = new LocalisedStringVO();
    //    objective_es.setLabel("OPERACION - OBJECTIVE - ES");
    //    objective_es.setLocale("es");
    //    LocalisedStringVO objective_en = new LocalisedStringVO();
    //    objective_en.setLabel("OPERACION - OBJECTIVE - EN");
    //    objective_en.setLocale("en");
    //    objective.addText(objective_es);
    //    objective.addText(objective_en);
    //    operation.setObjective(objective);
    //
    //    // SURVEY_TYPE
    //
    //    // PRODUCER
    //    operation.addProducer(mockExternalItem("ISTAC", "/uri/test/agency", "URN:AGENCY:ISTAC", null, TypeExternalArtefactsEnum.AGENCY));
    //    operation.addProducer(mockExternalItem("INE", "/uri/test/agency", "URN:AGENCY:INE", null, TypeExternalArtefactsEnum.AGENCY));
    //
    //    // REGIONAL_RESPONSIBLE
    //    operation.addRegionalResponsible(mockExternalItem("ISTAC", "/uri/test/agency", "URN:AGENCY:ISTAC", null, TypeExternalArtefactsEnum.AGENCY));
    //
    //    // PUBLISHER
    //    operation.addPublisher(mockExternalItem("ISTAC", "/uri/test/agency", "URN:AGENCY:ISTAC", null, TypeExternalArtefactsEnum.AGENCY));
    //
    //    // COMMON_METADATA
    //    operation.setCommonMetadata(mockExternalItem("ISTAC", "/uri/test/common_metadata", "URN:COMMON_METADATA:ISTAC", null, TypeExternalArtefactsEnum.CONFIGURATION));
    //
    //    // OFFICIALITY_TYPE
    //
    //    return operation;
    //}

    //public static InstanceEntity mockInstance() {
    //    InstanceEntity instance = new InstanceEntity();
    //
    //    // IDENTIFIER
    //    instance.setCode("INSTANCE-" + RandomStringUtils.random(50, true, true));
    //
    //    // TITLE
    //    InternationalStringEntity title = new InternationalStringEntity();
    //    LocalisedStringEntity title_es = new LocalisedStringEntity();
    //    title_es.setLabel("Título en español de instancia");
    //    title_es.setLocale("es");
    //    LocalisedStringEntity title_en = new LocalisedStringEntity();
    //    title_en.setLabel("Título en inglés de instancia");
    //    title_en.setLocale("en");
    //    title.addText(title_es);
    //    title.addText(title_en);
    //    instance.setTitle(title);
    //
    //    // PROC_STATUS
    //    instance.setProcStatus(ProcStatusEnum.DRAFT);
    //
    //    // ORDER
    //    instance.setOrder(0);
    //
    //    return instance;
    //}

    // -----------------------------------------------------------------
    // INTERNATIONAL STRING
    // -----------------------------------------------------------------

    public static InternationalStringEntity mockInternationalString() {
        InternationalStringEntity internationalString = new InternationalStringEntity();
        LocalisedStringEntity es = new LocalisedStringEntity();
        es.setLabel(mockString(10) + " en Espanol");
        es.setLocale("es");
        es.setOptLock(0L);
        LocalisedStringEntity en = new LocalisedStringEntity();
        en.setLabel(mockString(10) + " in English");
        en.setLocale("en");
        en.setOptLock(0L);
        internationalString.addText(es);
        internationalString.addText(en);
        internationalString.setOptLock(0L);
        return internationalString;
    }

    /**
     * Mock an InternationalString with one locale
     */
    public static InternationalStringEntity mockInternationalString(String locale, String label) {
        InternationalStringEntity target = new InternationalStringEntity();
        LocalisedStringEntity localisedString = new LocalisedStringEntity();
        localisedString.setLocale(locale);
        localisedString.setLabel(label);
        target.addText(localisedString);
        return target;
    }

    public static InternationalStringDto mockInternationalStringDto() {
        InternationalStringDto internationalStringDto = new InternationalStringDto();
        LocalisedStringDto es = new LocalisedStringDto();
        es.setLabel(mockString(10) + " en Español");
        es.setLocale("es");
        LocalisedStringDto en = new LocalisedStringDto();
        en.setLabel(mockString(10) + " in English");
        en.setLocale("en");
        internationalStringDto.addText(es);
        internationalStringDto.addText(en);
        return internationalStringDto;
    }

    public static InternationalStringDto mockInternationalStringDto(String locale, String label) {
        InternationalStringDto target = new InternationalStringDto();
        LocalisedStringDto localisedStringDto = new LocalisedStringDto();
        localisedStringDto.setLocale(locale);
        localisedStringDto.setLabel(label);
        target.addText(localisedStringDto);
        return target;
    }

    // -----------------------------------------------------------------
    // EXTERNAL ITEM
    // -----------------------------------------------------------------

    public static ExternalItemEntity mockAgencyExternalItem() {
        String code = mockCode();
        return mockSrmAppExternalItem(code, mockAgencyUrn(code), TypeExternalArtefactsEnum.AGENCY);
    }

    public static ExternalItemEntity mockCodeExternalItem() {
        String code = mockCode();
        return mockSrmAppExternalItem(code, mockCodeUrn(code), TypeExternalArtefactsEnum.CODE);
    }

    public static ExternalItemEntity mockExternalItem(String code, String uri, String urn, String urnProvider, TypeExternalArtefactsEnum type) {
        ExternalItemEntity target = new ExternalItemEntity();
        target.setVersion(Long.valueOf(0));
        target.setCode(code);
        target.setUri(uri);
        target.setUrn(urn);
        target.setUrnProvider(urnProvider);
        target.setType(type);
        return target;
    }

    public static ExternalItemEntity mockExternalItem(String code, String uri, String urn, String urnProvider, TypeExternalArtefactsEnum type, InternationalStringEntity title,
            String managementAppUrl) {
        ExternalItemEntity target = mockExternalItem(code, uri, urn, urnProvider, type);
        target.setTitle(title);
        target.setManagementAppUrl(managementAppUrl);
        return target;
    }

    public static String mockAgencyUrn(String code) {
        return GeneratorUrnUtils.generateSdmxAgencyUrn(MAINTAINER_MOCK, AGENCY_SCHEME_MOCK, "1.0", code);
    }

    public static String mockCodeUrn(String code) {
        return GeneratorUrnUtils.generateSdmxCodeUrn(MAINTAINER_MOCK, CODELIST_MOCK, "1.0", code);
    }

    public static String mockString(int count) {
        return RandomStringUtils.randomAlphanumeric(count);
    }

    public static String mockCode() {
        return mockString(8);
    }

    public static ExternalItemDto mockExternalItemDtoComplete(String code, String urn, TypeExternalArtefactsEnum type) {
        ExternalItemDto externalItemDto = mockExternalItemDto(code, urn, type);
        externalItemDto.setManagementAppUrl("managementAppUrl" + mockString(10));
        externalItemDto.setTitle(mockInternationalStringDto());

        if (TypeExternalArtefactsEnumUtils.isExternalItemOfCommonMetadataApp(type)) {
            externalItemDto.setManagementAppUrl(ConfigurationMockConstants.COMMON_METADATA_INTERNAL_WEB_APP_URL_BASE + "/" + urn);
        } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfStatisticalOperationsApp(type)) {
            externalItemDto.setManagementAppUrl(ConfigurationMockConstants.STATISTICAL_OPERATIONS_INTERNAL_WEB_APP_URL_BASE + "/" + urn);
        } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfSrmApp(type)) {
            externalItemDto.setManagementAppUrl(ConfigurationMockConstants.SRM_INTERNAL_WEB_APP_URL_BASE + "/" + urn);
        } else {
            fail("unexpected type of TypeExternalArtefactEnum");
        }

        return externalItemDto;
    }

    private static ExternalItemDto mockExternalItemDto(String code, String urn, TypeExternalArtefactsEnum type) {
        ExternalItemDto externalItemDto = new ExternalItemDto();

        // Required
        externalItemDto.setType(type);
        externalItemDto.setCode(code);

        if (TypeExternalArtefactsEnumUtils.isExternalItemWithNestedCode(type)) {
            externalItemDto.setCodeNested(code);
        }

        if (TypeExternalArtefactsEnumUtils.isExternalItemOfCommonMetadataApp(type)) {
            externalItemDto.setUri(ConfigurationMockConstants.COMMON_METADATA_EXTERNAL_API_URL_BASE + "/v1.0/" + urn);
            externalItemDto.setUrn(urn);
        } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfStatisticalOperationsApp(type)) {
            externalItemDto.setUri(ConfigurationMockConstants.STATISTICAL_OPERATIONS_INTERNAL_API_URL_BASE + "/v1.0/" + urn);
            externalItemDto.setUrn(urn);
        } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfSrmApp(type)) {
            externalItemDto.setUri(ConfigurationMockConstants.SRM_INTERNAL_API_URL_BASE + "/v1.0/" + urn);
            externalItemDto.setUrn(urn);
            externalItemDto.setUrnProvider(urn);
        } else {
            fail("unexpected type of TypeExternalArtefactEnum");
        }

        return externalItemDto;
    }

    public static ExternalItemDto mockExternalItemDto(String code, String uri, String urn, String urnProvider, TypeExternalArtefactsEnum type) {
        ExternalItemDto target = new ExternalItemDto();
        target.setCode(code);
        target.setUri(uri);
        target.setUrn(urn);
        target.setUrnProvider(urnProvider);
        target.setType(type);
        return target;
    }

    // -----------------------------------------------------------------
    // PRIVATE
    // -----------------------------------------------------------------

    private static ExternalItemEntity mockSrmAppExternalItem(String code, String urn, TypeExternalArtefactsEnum type) {
        return mockExternalItem(code, CoreCommonConstants.API_LATEST_WITH_SLASHES + code, urn, urn + ":provider", type, mockInternationalString(), CoreCommonConstants.URL_SEPARATOR + code);
    }
}
