package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.gobcan.istac.edatos.external.users.core.domain.SurveyTypeEntity;
import es.gobcan.istac.edatos.external.users.core.repository.SurveyTypeRepository;
import es.gobcan.istac.edatos.external.users.rest.common.dto.SurveyTypeDto;

@Component
public class SurveyTypeMapper {

    @Autowired
    private InternationalStringMapper internationalStringMapper;

    @Autowired
    private SurveyTypeRepository surveyTypeRepository;

    public SurveyTypeDto toDto(SurveyTypeEntity source) {
        if (source == null) {
            return null;
        }

        SurveyTypeDto target = new SurveyTypeDto();
        target.setId(source.getId());
        target.setUuid(source.getUuid());

        // DESCRIPTION
        target.setDescription(internationalStringMapper.toDto(source.getDescription()));

        target.setOptLock(source.getOptLock());

        return target;
    }

    public SurveyTypeEntity toEntity(SurveyTypeDto source) {
        if (source == null) {
            return null;
        }

        return surveyTypeRepository.findOne(source.getId());
    }
}
