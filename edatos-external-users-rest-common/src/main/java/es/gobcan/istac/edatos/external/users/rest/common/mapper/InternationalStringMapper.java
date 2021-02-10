package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import java.util.HashSet;
import java.util.Set;

import org.siemac.edatos.core.common.conf.ConfigurationService;
import org.siemac.edatos.core.common.constants.CoreCommonConstants;
import org.siemac.edatos.core.common.dto.InternationalStringDto;
import org.siemac.edatos.core.common.dto.LocalisedStringDto;
import org.siemac.edatos.core.common.exception.CommonServiceExceptionType;
import org.siemac.edatos.core.common.exception.EDatosException;
import org.siemac.edatos.core.common.exception.EDatosExceptionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.gobcan.istac.edatos.external.users.core.domain.InternationalStringEntity;
import es.gobcan.istac.edatos.external.users.core.domain.LocalisedStringEntity;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionType;
import es.gobcan.istac.edatos.external.users.core.repository.InternationalStringRepository;
import es.gobcan.istac.edatos.external.users.core.util.StatisticalOperationsValidationUtils;

@Component
public class InternationalStringMapper {

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private InternationalStringRepository internationalStringRepository;

    public InternationalStringEntity toEntity(InternationalStringDto source, InternationalStringEntity target, String metadataName) {

        // Check it is valid
        checkInternationalStringDtoValid(source, metadataName);

        // Transform
        if (source == null) {
            if (target != null) {
                // delete previous entity
                internationalStringRepository.delete(target);
            }
            return null;
        }

        if (StatisticalOperationsValidationUtils.isEmpty(source)) {
            throw new EDatosException(ServiceExceptionType.METADATA_REQUIRED, metadataName);
        }

        if (target == null) {
            target = new InternationalStringEntity();
        }

        Set<LocalisedStringEntity> localisedStringEntities = localisedStringDtoToDo(source.getTexts(), target.getTexts(), target);
        target.getTexts().clear();
        target.getTexts().addAll(localisedStringEntities);
        return target;
    }

    public InternationalStringDto toDto(InternationalStringEntity source) {
        if (source == null) {
            return null;
        }
        InternationalStringDto target = new InternationalStringDto();
        target.getTexts().addAll(localisedStringDoToDto(source.getTexts()));
        return target;
    }

    private void checkInternationalStringDtoValid(InternationalStringDto source, String metadataName) {
        if (source == null) {
            return;
        }
        if (StatisticalOperationsValidationUtils.isEmpty(source)) {
            throw EDatosExceptionBuilder.builder().withExceptionItems(CommonServiceExceptionType.METADATA_REQUIRED).withMessageParameters(metadataName).build();
        }
        checkInternationalStringDtoLength(source, metadataName);
        checkInternationalStringDtoTranslations(source, metadataName);
    }

    private void checkInternationalStringDtoTranslations(InternationalStringDto source, String metadataName) {
        if (source == null) {
            return;
        }
        String locale = configurationService.retrieveLanguageDefault();
        if (locale == null) {
            return;
        }
        LocalisedStringDto localisedString = source.getLocalised(locale);
        if (localisedString == null) {
            throw EDatosExceptionBuilder.builder().withExceptionItems(CommonServiceExceptionType.METADATA_WITHOUT_DEFAULT_LANGUAGE).withMessageParameters(metadataName).build();
        }
    }

    private void checkInternationalStringDtoLength(InternationalStringDto source, String metadataName) {
        if (source == null) {
            return;
        }
        int maximumLength = CoreCommonConstants.LOCALISED_STRING_MAXIMUM_LENGTH;
        for (LocalisedStringDto localisedStringDto : source.getTexts()) {
            if (localisedStringDto.getLabel() != null && localisedStringDto.getLabel().length() > maximumLength) {
                throw EDatosExceptionBuilder.builder().withExceptionItems(CommonServiceExceptionType.METADATA_MAXIMUM_LENGTH).withMessageParameters(metadataName, String.valueOf(maximumLength))
                        .build();
            }
        }
    }

    private Set<LocalisedStringEntity> localisedStringDtoToDo(Set<LocalisedStringDto> sources, Set<LocalisedStringEntity> targets, InternationalStringEntity internationalStringTarget) {

        Set<LocalisedStringEntity> targetsBefore = targets;
        targets = new HashSet<>();

        for (LocalisedStringDto source : sources) {
            boolean existsBefore = false;
            for (LocalisedStringEntity target : targetsBefore) {
                if (source.getLocale().equals(target.getLocale())) {
                    targets.add(localisedStringDtoToDo(source, target, internationalStringTarget));
                    existsBefore = true;
                    break;
                }
            }
            if (!existsBefore) {
                targets.add(localisedStringDtoToDo(source, internationalStringTarget));
            }
        }
        return targets;
    }

    private LocalisedStringEntity localisedStringDtoToDo(LocalisedStringDto source, InternationalStringEntity internationalStringTarget) {
        LocalisedStringEntity target = new LocalisedStringEntity();
        localisedStringDtoToDo(source, target, internationalStringTarget);
        return target;
    }

    private LocalisedStringEntity localisedStringDtoToDo(LocalisedStringDto source, LocalisedStringEntity target, InternationalStringEntity internationalStringTarget) {
        target.setLabel(source.getLabel());
        target.setLocale(source.getLocale());
        target.setIsUnmodifiable(source.getIsUnmodifiable());
        target.setInternationalString(internationalStringTarget);
        return target;
    }

    private Set<LocalisedStringDto> localisedStringDoToDto(Set<LocalisedStringEntity> sources) {
        Set<LocalisedStringDto> targets = new HashSet<>();
        for (LocalisedStringEntity source : sources) {
            LocalisedStringDto target = new LocalisedStringDto();
            target.setLabel(source.getLabel());
            target.setLocale(source.getLocale());
            target.setIsUnmodifiable(source.getIsUnmodifiable());
            targets.add(target);
        }
        return targets;
    }
}
