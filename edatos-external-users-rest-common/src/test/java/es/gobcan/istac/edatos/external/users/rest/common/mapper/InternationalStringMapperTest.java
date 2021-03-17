package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.siemac.edatos.common.test.EDatosBaseTest;
import org.siemac.edatos.core.common.conf.ConfigurationService;
import org.siemac.edatos.core.common.dto.InternationalStringDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import es.gobcan.istac.edatos.external.users.EdatosExternalUsersRestTestApp;
import es.gobcan.istac.edatos.external.users.core.domain.InternationalStringEntity;
import es.gobcan.istac.edatos.external.users.core.repository.InternationalStringRepository;
import es.gobcan.istac.edatos.external.users.mock.MetadataConfigurationServiceMockImpl;
import es.gobcan.istac.edatos.external.users.util.StatisticalOperationsAsserts;
import es.gobcan.istac.edatos.external.users.util.StatisticalOperationsMocks;

import static org.mockito.Mockito.never;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdatosExternalUsersRestTestApp.class)
public class InternationalStringMapperTest extends EDatosBaseTest {

    @Autowired
    private InternationalStringMapper internationalStringMapper;

    private final InternationalStringRepository repository = Mockito.mock(InternationalStringRepository.class);

    private final ConfigurationService configurationService = new MetadataConfigurationServiceMockImpl();

    private static final String METADATA_NAME = "LOREM_IPSUM";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
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

    private void testInternationalStringDtoToEntity(InternationalStringDto internationalStringDto, InternationalStringEntity internationalString) throws Exception {
        InternationalStringEntity result = internationalStringMapper.toEntity(internationalStringDto/*, internationalString, METADATA_NAME*/);
        StatisticalOperationsAsserts.assertEqualsInternationalString(result, internationalStringDto);
    }

    private void testInternationalStringDoToDto(InternationalStringEntity internationalString) throws Exception {
        InternationalStringDto result = internationalStringMapper.toDto(internationalString);
        StatisticalOperationsAsserts.assertEqualsInternationalString(internationalString, result);
    }
}
