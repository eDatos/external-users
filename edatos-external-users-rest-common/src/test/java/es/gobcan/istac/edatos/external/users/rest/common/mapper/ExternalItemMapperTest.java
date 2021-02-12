package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.siemac.edatos.common.test.utils.ConfigurationMockConstants;
import org.siemac.edatos.common.test.utils.EDatosAsserts.MapperEnum;
import org.siemac.edatos.core.common.conf.ConfigurationService;
import org.siemac.edatos.core.common.constants.CoreCommonConstants;
import org.siemac.edatos.core.common.dto.ExternalItemDto;
import org.siemac.edatos.core.common.enume.TypeExternalArtefactsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import es.gobcan.istac.edatos.external.users.EdatosExternalUsersRestTestApp;
import es.gobcan.istac.edatos.external.users.core.domain.ExternalItemEntity;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalItemRepository;
import es.gobcan.istac.edatos.external.users.mock.MetadataConfigurationServiceMockImpl;
import es.gobcan.istac.edatos.external.users.util.StatisticalOperationsAsserts;
import es.gobcan.istac.edatos.external.users.util.StatisticalOperationsMocks;

import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdatosExternalUsersRestTestApp.class)
public class ExternalItemMapperTest {

    private static final String CODE_01 = "mock01";
    private static final String CODE_02 = "mock02";
    private static final String URN_01 = "lorem:ipsum:externalItem:mock:01";
    private static final String URN_02 = "lorem:ipsum:externalItem:mock:02";
    private static final String METADATA_NAME = "LOREM_IPSUM";

    @Autowired
    private ExternalItemMapper externalItemMapper;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ExternalItemRepository repository = Mockito.mock(ExternalItemRepository.class);

    private ConfigurationService configurationService = new MetadataConfigurationServiceMockImpl();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(externalItemMapper, "configurationService", configurationService);
        ReflectionTestUtils.setField(externalItemMapper, "externalItemRepository", repository);
    }

    @Test
    public void testExternalItemDoToDtoWithNullParameter() throws Exception {
        testExternalItemDoToDto(null);
    }

    @Test
    public void testExternalItemDoToDtoWithExistsParameter() throws Exception {
        testExternalItemDoToDto(StatisticalOperationsMocks.mockAgencyExternalItem());
    }

    @Test
    public void testExternalItemDoCollectionToDtoCollectionWithNullParameter() throws Exception {
        testExternalItemDoCollectionToDtoCollection(null);
    }

    @Test
    public void testExternalItemDoCollectionToDtoCollectionWithEmptyParameter() throws Exception {
        testExternalItemDoCollectionToDtoCollection(new HashSet<ExternalItemEntity>());
    }

    @Test
    public void testExternalItemDoCollectionToDtoCollectionWithExistsParameter() throws Exception {
        Set<ExternalItemEntity> entities = new HashSet<>();
        entities.add(StatisticalOperationsMocks.mockAgencyExternalItem());
        entities.add(StatisticalOperationsMocks.mockAgencyExternalItem());

        testExternalItemDoCollectionToDtoCollection(entities);
    }

    @Test
    public void testExternalItemDtoToEntityNullDtoAndNullDo() throws Exception {
        // NULL, NULL
        testExternalItemDtoToEntity(null, null);
    }

    @Test
    public void testExternalItemDtoToEntityExistsDtoAndNullDo() throws Exception {
        // EXISTS, NULL
        ExternalItemDto externalItemDto = StatisticalOperationsMocks.mockExternalItemDtoComplete(CODE_01, URN_01, TypeExternalArtefactsEnum.AGENCY);
        testExternalItemDtoToEntity(externalItemDto, null);
    }

    @Test
    public void testExternalItemDtoToEntityNullDtoAndExistsDo() throws Exception {
        // NULL, EXISTS
        ExternalItemEntity externalItem = StatisticalOperationsMocks.mockAgencyExternalItem();
        testExternalItemDtoToEntity(null, externalItem);
        Mockito.verify(repository).delete(Mockito.any(ExternalItemEntity.class));
    }

    @Test
    public void testExternalItemDtoToEntityExistsDtoAndExistsDo() throws Exception {
        // EXISTS, EXISTS
        ExternalItemDto externalItemDto = StatisticalOperationsMocks.mockExternalItemDtoComplete(CODE_01, URN_01, TypeExternalArtefactsEnum.AGENCY);
        ExternalItemEntity externalItem = StatisticalOperationsMocks.mockAgencyExternalItem();
        testExternalItemDtoToEntity(externalItemDto, externalItem);
    }

    @Test
    public void testExternalItemListToEntityEmptyDtoAndEmptyDo() throws Exception {
        // EMPTY, EMPTY
        Set<ExternalItemDto> dtos = new HashSet<ExternalItemDto>();
        Set<ExternalItemEntity> entities = new HashSet<>();
        testExternalItemDtoListToEntitiesList(dtos, entities);
    }

    @Test
    public void testExternalItemListToEntityExistsDtoAndEmptyDo() throws Exception {
        // EXISTS, EMPTY
        Set<ExternalItemDto> dtos = new HashSet<ExternalItemDto>();
        dtos.add(StatisticalOperationsMocks.mockExternalItemDtoComplete(CODE_01, URN_01, TypeExternalArtefactsEnum.AGENCY));
        dtos.add(StatisticalOperationsMocks.mockExternalItemDtoComplete(CODE_02, URN_02, TypeExternalArtefactsEnum.AGENCY));
        Set<ExternalItemEntity> entities = new HashSet<>();

        testExternalItemDtoListToEntitiesList(dtos, entities);
    }

    @Test
    public void testExternalItemListToEntityEmptyDtoAndExistsDo() throws Exception {
        // EMPTY, EXISTS
        Set<ExternalItemDto> dtos = new HashSet<ExternalItemDto>();
        Set<ExternalItemEntity> entities = new HashSet<ExternalItemEntity>();
        entities.add(StatisticalOperationsMocks.mockAgencyExternalItem());
        entities.add(StatisticalOperationsMocks.mockAgencyExternalItem());

        testExternalItemDtoListToEntitiesList(dtos, entities);

        Mockito.verify(repository, times(2)).delete(Mockito.any(ExternalItemEntity.class));
    }

    @Test
    public void testExternalItemListToEntityExistsDtoAndExistsDo() throws Exception {
        // EXISTS, EXISTS: Same elements
        Set<ExternalItemDto> dtos = new HashSet<ExternalItemDto>();
        dtos.add(StatisticalOperationsMocks
                .mockExternalItemDto("CODE_01", ConfigurationMockConstants.SRM_INTERNAL_API_URL_BASE + CoreCommonConstants.URL_SEPARATOR + "URI_01", "URN_01", "URN_01_provider",
                TypeExternalArtefactsEnum.AGENCY));
        dtos.add(StatisticalOperationsMocks
                .mockExternalItemDto("CODE_02", ConfigurationMockConstants.SRM_INTERNAL_API_URL_BASE + CoreCommonConstants.URL_SEPARATOR + "URI_02", "URN_02", "URN_02_provider",
                TypeExternalArtefactsEnum.CATEGORY));
        Set<ExternalItemEntity> entities = new HashSet<>();
        entities.add(
                StatisticalOperationsMocks.mockExternalItem("CODE_01", ConfigurationMockConstants.SRM_INTERNAL_API_URL_BASE + CoreCommonConstants.URL_SEPARATOR + "URI_01", "URN_01", "URN_01_provider",
                TypeExternalArtefactsEnum.AGENCY));
        entities.add(
                StatisticalOperationsMocks.mockExternalItem("CODE_02", ConfigurationMockConstants.SRM_INTERNAL_API_URL_BASE + CoreCommonConstants.URL_SEPARATOR + "URI_02", "URN_02", "URN_02_provider",
                TypeExternalArtefactsEnum.CATEGORY));

        testExternalItemDtoListToEntitiesList(dtos, entities);
    }

    @Test
    public void testExternalItemListToEntityExistsDtoAndExistsDoWithDtoMoreElements() throws Exception {
        // EXISTS, EXISTS: More elements
        Set<ExternalItemDto> dtos = new HashSet<ExternalItemDto>();
        dtos.add(StatisticalOperationsMocks
                .mockExternalItemDto("CODE_01", ConfigurationMockConstants.SRM_INTERNAL_API_URL_BASE + CoreCommonConstants.URL_SEPARATOR + "URI_01", "URN_01", "URN_01_provider",
                TypeExternalArtefactsEnum.AGENCY));
        dtos.add(StatisticalOperationsMocks
                .mockExternalItemDto("CODE_02", ConfigurationMockConstants.SRM_INTERNAL_API_URL_BASE + CoreCommonConstants.URL_SEPARATOR + "URI_02", "URN_02", "URN_02_provider",
                TypeExternalArtefactsEnum.CATEGORY));
        dtos.add(StatisticalOperationsMocks
                .mockExternalItemDto("CODE_03", ConfigurationMockConstants.SRM_INTERNAL_API_URL_BASE + CoreCommonConstants.URL_SEPARATOR + "URI_03", "URN_03", "URN_03_provider",
                TypeExternalArtefactsEnum.CATEGORY));
        Set<ExternalItemEntity> entities = new HashSet<>();
        entities.add(
                StatisticalOperationsMocks.mockExternalItem("CODE_01", ConfigurationMockConstants.SRM_INTERNAL_API_URL_BASE + CoreCommonConstants.URL_SEPARATOR + "URI_01", "URN_01", "URN_01_provider",
                TypeExternalArtefactsEnum.AGENCY));
        entities.add(
                StatisticalOperationsMocks.mockExternalItem("CODE_02", ConfigurationMockConstants.SRM_INTERNAL_API_URL_BASE + CoreCommonConstants.URL_SEPARATOR + "URI_02", "URN_02", "URN_02_provider",
                TypeExternalArtefactsEnum.CATEGORY));

        testExternalItemDtoListToEntitiesList(dtos, entities);
    }

    @Test
    public void testExternalItemListToEntityExistsDtoAndExistsDoWithDtoLessElements() throws Exception {
        // Can execute this test, because can not set id in entity
    }

    @Test
    public void testExternalItemListToEntityExistsDtoAndExistsDoWithDtoDifferentElements() throws Exception {
        // EXISTS, EXISTS: Different elements
        Set<ExternalItemDto> dtos = new HashSet<ExternalItemDto>();
        dtos.add(StatisticalOperationsMocks.mockExternalItemDtoComplete(CODE_01, URN_01, TypeExternalArtefactsEnum.AGENCY));
        dtos.add(StatisticalOperationsMocks.mockExternalItemDtoComplete(CODE_02, URN_02, TypeExternalArtefactsEnum.AGENCY));
        Set<ExternalItemEntity> entities = new HashSet<>();
        entities.add(StatisticalOperationsMocks.mockAgencyExternalItem());
        entities.add(StatisticalOperationsMocks.mockAgencyExternalItem());

        testExternalItemDtoListToEntitiesList(dtos, entities);

        Mockito.verify(repository, times(2)).delete(Mockito.any(ExternalItemEntity.class));
    }

    @Test
    public void testExternalItemListToEntityErrorExpectedAndActualNull() throws Exception {
        thrown.expect(NullPointerException.class);
        testExternalItemDtoListToEntitiesList(null, null);
    }

    @Test
    public void testExternalItemListToEntityErrorExpectedNull() throws Exception {
        thrown.expect(NullPointerException.class);
        Set<ExternalItemEntity> entities = new HashSet<>();
        testExternalItemDtoListToEntitiesList(null, entities);
    }

    @Test
    public void testExternalItemListToEntityErrorActualNull() throws Exception {
        thrown.expect(NullPointerException.class);
        Set<ExternalItemDto> dtos = new HashSet<ExternalItemDto>();
        testExternalItemDtoListToEntitiesList(dtos, null);
    }

    private void testExternalItemDtoToEntity(ExternalItemDto externalItemDto, ExternalItemEntity externalItemEntity) throws Exception {
        ExternalItemEntity result = externalItemMapper.toEntity(externalItemDto, externalItemEntity, METADATA_NAME);
        StatisticalOperationsAsserts.assertEqualsExternalItem(result, externalItemDto, MapperEnum.DTO2ENTITY);
    }

    private void testExternalItemDtoListToEntitiesList(Set<ExternalItemDto> dtos, Set<ExternalItemEntity> entities) throws Exception {
        Set<ExternalItemEntity> result = externalItemMapper.toEntities(dtos, entities, METADATA_NAME);
        StatisticalOperationsAsserts.assertEqualsExternalItemCollectionMapper(result, dtos, MapperEnum.DTO2ENTITY);
    }

    private void testExternalItemDoToDto(ExternalItemEntity externalItem) throws Exception {
        ExternalItemDto result = externalItemMapper.toDto(externalItem);
        StatisticalOperationsAsserts.assertEqualsExternalItem(externalItem, result, MapperEnum.ENTITY2DTO);
    }

    private void testExternalItemDoCollectionToDtoCollection(Set<ExternalItemEntity> entities) throws Exception {
        Object[] parameters = new Object[1];
        parameters[0] = entities;

        externalItemMapper.toDtos(entities);

        Set<ExternalItemDto> result = externalItemMapper.toDtos(entities);
        StatisticalOperationsAsserts.assertEqualsExternalItemCollectionMapper(entities, result, MapperEnum.ENTITY2DTO);
    }
}
