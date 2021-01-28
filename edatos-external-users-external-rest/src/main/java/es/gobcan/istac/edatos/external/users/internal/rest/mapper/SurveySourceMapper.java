package es.gobcan.istac.edatos.external.users.internal.rest.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.gobcan.istac.edatos.external.users.core.domain.SurveySourceEntity;
import es.gobcan.istac.edatos.external.users.core.repository.SurveySourceRepository;
import es.gobcan.istac.edatos.external.users.internal.rest.dto.SurveySourceDto;

@Component
public class SurveySourceMapper {

    @Autowired
    private InternationalStringMapper internationalStringMapper;

    @Autowired
    private SurveySourceRepository surveySourceRepository;

    public SurveySourceDto toDto(SurveySourceEntity source) {
        if (source == null) {
            return null;
        }

        SurveySourceDto target = new SurveySourceDto();
        target.setId(source.getId());
        target.setUuid(source.getUuid());

        // DESCRIPTION
        target.setDescription(internationalStringMapper.toDto(source.getDescription()));

        target.setOptLock(source.getOptLock());

        return target;
    }

    public SurveySourceEntity toEntity(SurveySourceDto source) {
        if (source == null) {
            return null;
        }

        return surveySourceRepository.findOne(source.getId());
    }
}
