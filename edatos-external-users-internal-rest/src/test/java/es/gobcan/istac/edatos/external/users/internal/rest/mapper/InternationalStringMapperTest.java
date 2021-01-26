package es.gobcan.istac.edatos.external.users.internal.rest.mapper;

import static es.gobcan.istac.edatos.external.users.util.StatisticalOperationsAsserts.assertEqualsInternationalString;
import static es.gobcan.istac.edatos.external.users.util.StatisticalOperationsMocks.mockInternationalString;
import static es.gobcan.istac.edatos.external.users.util.StatisticalOperationsMocks.mockInternationalStringDto;
import static org.mockito.Mockito.never;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.siemac.edatos.core.common.conf.ConfigurationService;
import org.siemac.edatos.core.common.dto.InternationalStringDto;
import org.siemac.edatos.core.common.exception.EDatosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import es.gobcan.istac.edatos.external.users.StatisticalOperationsRoadmapRestTestApp;
import es.gobcan.istac.edatos.external.users.mock.MetadataConfigurationServiceMockImpl;
import es.gobcan.istac.edatos.external.users.util.StatisticalOperationsAsserts;
import es.gobcan.istac.edatos.external.users.util.StatisticalOperationsMocks;
import es.gobcan.istac.edatos.external.users.util.StatisticalOperationsRoadmapBaseTest;
import es.gobcan.istac.edatos.external.users.core.domain.InternationalStringEntity;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionType;
import es.gobcan.istac.edatos.external.users.core.repository.InternationalStringRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StatisticalOperationsRoadmapRestTestApp.class)
public class InternationalStringMapperTest extends StatisticalOperationsRoadmapBaseTest {

    @Autowired
    private InternationalStringMapper internationalStringMapper;

    private InternationalStringRepository repository = Mockito.mock(InternationalStringRepository.class);

    private ConfigurationService configurationService = new MetadataConfigurationServiceMockImpl();

    private static final String METADATA_NAME = "LOREM_IPSUM";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(internationalStringMapper, "configurationService", configurationService);
        ReflectionTestUtils.setField(internationalStringMapper, "internationalStringRepository", repository);
    }

    // ------------------------------------------------------------
    // INTERNATIONAL STRINGS
    // ------------------------------------------------------------

    @Test
    public void testInternationalStringDo2DtoWithNullParameter() throws Exception {
        testInternationalStringDoToDto(null);
    }

    @Test
    public void testInternationalStringDo2DtoWithExistsParameter() throws Exception {
        testInternationalStringDoToDto(StatisticalOperationsMocks.mockInternationalString());
    }

    @Test
    public void testInternationalStringDto2DoWithDtoNullAndDoNull() throws Exception {
        // NULL, NULL
        testInternationalStringDtoToEntity(null, null);
        Mockito.verify(repository, never()).delete(Mockito.any(InternationalStringEntity.class));
    }

    @Test
    public void testInternationalStringDto2DoWithDtoNullAndDoExists() throws Exception {
        // NULL, EXISTS
        InternationalStringEntity internationalString = StatisticalOperationsMocks.mockInternationalString();
        testInternationalStringDtoToEntity(null, internationalString);
        Mockito.verify(repository).delete(Mockito.any(InternationalStringEntity.class));
    }

    @Test
    public void testInternationalStringDto2DoWithDtoExistsAndDoNull() throws Exception {
        // EXISTS, NULL
        testInternationalStringDtoToEntity(StatisticalOperationsMocks.mockInternationalStringDto(), null);
        Mockito.verify(repository, never()).delete(Mockito.any(InternationalStringEntity.class));
    }

    @Test
    public void testInternationalStringDto2DoWithDtoExistsAndDoExists() throws Exception {
        // EXISTS, EXISTS
        InternationalStringEntity internationalString = StatisticalOperationsMocks.mockInternationalString();
        testInternationalStringDtoToEntity(StatisticalOperationsMocks.mockInternationalStringDto(), internationalString);
        Mockito.verify(repository, never()).delete(Mockito.any(InternationalStringEntity.class));
    }

    @Test
    public void testInternationalStringDto2DoWithDtoExistsAndDoNullAndDtoWithoutLocalisedStrings() throws Exception {
        // EXISTS, EXISTS
        expectedEDatosException(new EDatosException(ServiceExceptionType.METADATA_REQUIRED, METADATA_NAME));

        InternationalStringDto internationalStringDto = StatisticalOperationsMocks.mockInternationalStringDto();
        internationalStringDto.getTexts().clear();

        testInternationalStringDtoToEntity(internationalStringDto, null);
        Mockito.verify(repository, never()).delete(Mockito.any(InternationalStringEntity.class));
    }

    @Test
    public void testInternationalStringDto2DoWithDtoNullAndWithoutLocaleInDefaultLanguage() throws Exception {
        expectedEDatosException(new EDatosException(ServiceExceptionType.METADATA_WITHOUT_DEFAULT_LANGUAGE, METADATA_NAME));
        testInternationalStringDtoToEntity(StatisticalOperationsMocks.mockInternationalStringDto("rs", "text"), null);
        Mockito.verify(repository, never()).delete(Mockito.any(InternationalStringEntity.class));
    }

    @Test
    public void testInternationalStringDto2DoWithoutLocaleInDefaultLanguage() throws Exception {
        expectedEDatosException(new EDatosException(ServiceExceptionType.METADATA_WITHOUT_DEFAULT_LANGUAGE, METADATA_NAME));
        testInternationalStringDtoToEntity(
                StatisticalOperationsMocks.mockInternationalStringDto("rs", "text"), StatisticalOperationsMocks.mockInternationalString(configurationService.retrieveLanguageDefault(), "texto"));
        Mockito.verify(repository, never()).delete(Mockito.any(InternationalStringEntity.class));
    }

    @Test
    public void testInternationalStringDto2DoWithDtoMaximumLength() throws Exception {
        expectedEDatosException(new EDatosException(ServiceExceptionType.METADATA_MAXIMUM_LENGTH, METADATA_NAME, "4000"));
        testInternationalStringDtoToEntity(StatisticalOperationsMocks.mockInternationalStringDto("rs", RandomStringUtils.randomAlphabetic(4050)),
                StatisticalOperationsMocks.mockInternationalString(configurationService.retrieveLanguageDefault(), "texto"));
        Mockito.verify(repository, never()).delete(Mockito.any(InternationalStringEntity.class));
    }

    private void testInternationalStringDtoToEntity(InternationalStringDto internationalStringDto, InternationalStringEntity internationalString) throws Exception {
        InternationalStringEntity result = internationalStringMapper.toEntity(internationalStringDto, internationalString, METADATA_NAME);
        StatisticalOperationsAsserts.assertEqualsInternationalString(result, internationalStringDto);
    }

    private void testInternationalStringDoToDto(InternationalStringEntity internationalString) throws Exception {
        InternationalStringDto result = internationalStringMapper.toDto(internationalString);
        StatisticalOperationsAsserts.assertEqualsInternationalString(internationalString, result);
    }
}
