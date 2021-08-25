package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.config.MetadataProperties;
import es.gobcan.istac.edatos.external.users.core.domain.DataProtectionPolicyEntity;
import es.gobcan.istac.edatos.external.users.core.domain.vo.InternationalStringVO;
import es.gobcan.istac.edatos.external.users.core.domain.vo.LocalisedStringVO;
import es.gobcan.istac.edatos.external.users.core.repository.DataProtectionPolicyRepository;
import es.gobcan.istac.edatos.external.users.core.service.DataProtectionPolicyService;

@Service
public class DataProtectionPolicyServiceImpl implements DataProtectionPolicyService {

    private final DataProtectionPolicyRepository dataProtectionPolicyRepository;
    private final MetadataProperties metadataProperties;

    public DataProtectionPolicyServiceImpl(DataProtectionPolicyRepository configurationRepository, MetadataProperties metadataProperties) {
        this.dataProtectionPolicyRepository = configurationRepository;
        this.metadataProperties = metadataProperties;
    }

    @Override
    public DataProtectionPolicyEntity find() {
        DataProtectionPolicyEntity entity = this.dataProtectionPolicyRepository.findFirstByOrderByIdAsc();
        dataProtectionPolicyRepository.detachEntity(entity);
        Set<LocalisedStringVO> texts = new HashSet<>();
        List<String> languages = metadataProperties.getLanguages().stream().map(language -> language.toLowerCase()).collect(Collectors.toList());
        for (String language : languages) {
            LocalisedStringVO localisedString = entity.getValue().getLocalisedLabelEntity(language);
            if (localisedString == null) {
                localisedString = new LocalisedStringVO();
                localisedString.setLabel("");
                localisedString.setLocale(language);
            }
            texts.add(localisedString);
        }
        entity.getValue().removeAllTexts();
        texts.forEach(text -> entity.getValue().addText(text));
        return entity;
    }

    @Override
    public DataProtectionPolicyEntity update(DataProtectionPolicyEntity updatedEntity) {
        DataProtectionPolicyEntity entity = this.dataProtectionPolicyRepository.findFirstByOrderByIdAsc();
        List<String> languages = metadataProperties.getLanguages().stream().map(language -> language.toLowerCase()).collect(Collectors.toList());
        for (LocalisedStringVO localisedString : new ArrayList<>(updatedEntity.getValue().getTexts())) {
            if (localisedString != null) {
                LocalisedStringVO storedLocalisedString = entity.getValue().getLocalisedLabelEntity(localisedString.getLocale());
                if (storedLocalisedString != null) {
                    entity.getValue().removeText(storedLocalisedString);
                    entity.getValue().addText(localisedString);
                } else if (languages.contains(localisedString.getLocale())) {
                    entity.getValue().addText(localisedString);
                }
            }
        }
        return dataProtectionPolicyRepository.save(entity);
    }
}
