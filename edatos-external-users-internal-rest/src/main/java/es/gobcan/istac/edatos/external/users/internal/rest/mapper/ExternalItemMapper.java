package es.gobcan.istac.edatos.external.users.internal.rest.mapper;

import java.util.HashSet;
import java.util.Set;

import org.siemac.edatos.core.common.dto.ExternalItemDto;
import org.siemac.edatos.core.common.enume.utils.TypeExternalArtefactsEnumUtils;
import org.siemac.edatos.core.common.exception.EDatosException;
import org.siemac.edatos.core.common.util.CoreCommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalItemEntity;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionType;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalItemRepository;
import es.gobcan.istac.edatos.external.users.core.service.MetadataConfigurationService;

@Component
public class ExternalItemMapper {

    @Autowired
    private InternationalStringMapper internationalStringMapper;

    @Autowired
    private MetadataConfigurationService configurationService;

    @Autowired
    private ExternalItemRepository externalItemRepository;

    public Set<ExternalItemDto> toDtos(Set<ExternalItemEntity> entities) {
        HashSet<ExternalItemDto> result = new HashSet<>();
        if (entities != null) {
            for (ExternalItemEntity externalItem : entities) {
                result.add(toDto(externalItem));
            }
        }
        return result;
    }

    public ExternalItemDto toDto(ExternalItemEntity source) {
        ExternalItemDto target = externalItemWithoutUrlsToDto(source);
        if (target != null) {
            if (TypeExternalArtefactsEnumUtils.isExternalItemOfCommonMetadataApp(source.getType())) {
                target = commonMetadataExternalItemDoToDto(source, target);
            } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfSrmApp(source.getType())) {
                target = srmExternalItemDoToDto(source, target);
            } else {
                throw new EDatosException(ServiceExceptionType.UNKNOWN, "Type of externalItem not defined for externalItemDoToDto");
            }
        }

        return target;
    }

    public ExternalItemDto externalItemWithoutUrlsToDto(ExternalItemEntity source) {
        if (source == null) {
            return null;
        }
        ExternalItemDto target = new ExternalItemDto();
        target.setId(source.getId());
        target.setCode(source.getCode());
        target.setCodeNested(source.getCodeNested());
        target.setUri(source.getUri());
        target.setUrn(source.getUrn());
        target.setUrnProvider(source.getUrnProvider());
        target.setType(source.getType());
        target.setManagementAppUrl(source.getManagementAppUrl());
        target.setTitle(internationalStringMapper.toDto(source.getTitle()));
        return target;
    }

    public ExternalItemDto commonMetadataExternalItemDoToDto(ExternalItemEntity source, ExternalItemDto target) {
        target.setUri(commonMetadataExternalApiUrlDoToDto(source.getUri()));
        target.setManagementAppUrl(commonMetadataInternalWebAppUrlDoToDto(source.getManagementAppUrl()));
        return target;
    }

    public ExternalItemDto srmExternalItemDoToDto(ExternalItemEntity source, ExternalItemDto target) {
        target.setUri(srmInternalApiUrlDoToDto(source.getUri()));
        target.setManagementAppUrl(srmInternalWebAppUrlDoToDto(source.getManagementAppUrl()));
        return target;
    }

    public Set<ExternalItemEntity> toEntities(Set<ExternalItemDto> sources, Set<ExternalItemEntity> targets, String metadataName) {

        Set<ExternalItemEntity> targetsBefore = targets;
        Set<ExternalItemEntity> newTargets = new HashSet<>();

        for (ExternalItemDto source : sources) {
            boolean existsBefore = false;
            for (ExternalItemEntity target : targetsBefore) {
                if (source.getId() != null && target.getId().equals(source.getId())) {
                    newTargets.add(toEntity(source, target, metadataName));
                    existsBefore = true;
                    break;
                }
            }
            if (!existsBefore) {
                newTargets.add(toEntity(source, null, metadataName));
            }
        }

        // Delete missing
        for (ExternalItemEntity oldTarget : targetsBefore) {
            boolean found = false;
            for (ExternalItemEntity newTarget : newTargets) {
                if (newTarget.getId() != null && oldTarget.getId().equals(newTarget.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                // Delete
                toEntity(null, oldTarget, metadataName);
            }
        }

        targets.clear();
        for (ExternalItemEntity target : newTargets) {
            targets.add(target);
        }

        return targets;
    }

    public ExternalItemEntity toEntity(ExternalItemDto source, ExternalItemEntity target, String metadataName) {
        target = externalItemWithoutUrlDtoToEntity(source, target, metadataName);

        if (target != null) {
            if (TypeExternalArtefactsEnumUtils.isExternalItemOfCommonMetadataApp(source.getType())) {
                target = commonMetadataExternalItemDtoToDo(source, target);
            } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfSrmApp(source.getType())) {
                target = srmExternalItemDtoToDo(source, target);
            } else {
                throw new EDatosException(ServiceExceptionType.UNKNOWN, "Type of externalItem not defined for externalItemDtoToEntity");
            }
        }

        return target;
    }

    private ExternalItemEntity externalItemWithoutUrlDtoToEntity(ExternalItemDto source, ExternalItemEntity target, String metadataName) {
        if (source == null) {
            if (target != null) {
                // delete previous entity
                externalItemRepository.delete(target);
            }
            return null;
        }

        if (target == null) {
            target = new ExternalItemEntity();
        }
        target.setCode(source.getCode());
        target.setCodeNested(source.getCodeNested());
        target.setUrn(source.getUrn());
        target.setUrnProvider(source.getUrnProvider());
        target.setType(source.getType());
        target.setTitle(internationalStringMapper.toEntity(source.getTitle(), target.getTitle(), metadataName));
        return target;
    }

    private ExternalItemEntity commonMetadataExternalItemDtoToDo(ExternalItemDto source, ExternalItemEntity target) {
        target.setUri(commonMetadataExternalApiUrlDtoToDo(source.getUri()));
        target.setManagementAppUrl(commonMetadataInternalWebAppUrlDtoToDo(source.getManagementAppUrl()));
        return target;
    }

    private ExternalItemEntity srmExternalItemDtoToDo(ExternalItemDto source, ExternalItemEntity target) {
        target.setUri(srmInternalApiUrlDtoToDo(source.getUri()));
        target.setManagementAppUrl(srmInternalWebAppUrlDtoToDo(source.getManagementAppUrl()));
        return target;
    }

    private String srmInternalApiUrlDoToDto(String source) {
        return CoreCommonUtil.externalItemUrlDoToUrlDto(configurationService.retrieveSrmInternalApiUrlBase(), source);
    }

    private String srmInternalWebAppUrlDoToDto(String source) {
        return CoreCommonUtil.externalItemUrlDoToUrlDto(configurationService.retrieveSrmInternalWebApplicationUrlBase(), source);
    }

    private String commonMetadataExternalApiUrlDoToDto(String source) {
        return CoreCommonUtil.externalItemUrlDoToUrlDto(configurationService.retrieveCommonMetadataExternalApiUrlBase(), source);
    }

    private String commonMetadataInternalWebAppUrlDoToDto(String source) {
        return CoreCommonUtil.externalItemUrlDoToUrlDto(configurationService.retrieveCommonMetadataInternalWebApplicationUrlBase(), source);
    }

    private String commonMetadataExternalApiUrlDtoToDo(String source) {
        return CoreCommonUtil.externalItemUrlDtoToUrlDo(configurationService.retrieveCommonMetadataExternalApiUrlBase(), source);
    }

    private String commonMetadataInternalWebAppUrlDtoToDo(String source) {
        return CoreCommonUtil.externalItemUrlDtoToUrlDo(configurationService.retrieveCommonMetadataInternalWebApplicationUrlBase(), source);
    }

    public String srmInternalApiUrlDtoToDo(String source) {
        return CoreCommonUtil.externalItemUrlDtoToUrlDo(configurationService.retrieveSrmInternalApiUrlBase(), source);
    }

    public String srmInternalWebAppUrlDtoToDo(String source) {
        return CoreCommonUtil.externalItemUrlDtoToUrlDo(configurationService.retrieveSrmInternalWebApplicationUrlBase(), source);
    }
}
