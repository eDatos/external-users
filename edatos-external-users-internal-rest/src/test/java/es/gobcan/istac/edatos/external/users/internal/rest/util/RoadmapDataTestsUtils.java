package es.gobcan.istac.edatos.external.users.internal.rest.util;

import java.util.stream.Collectors;

import org.siemac.edatos.core.common.util.GeneratorUrnUtils;

import es.gobcan.istac.edatos.external.users.core.domain.NeedEntity;
import es.gobcan.istac.edatos.external.users.core.domain.NeedStateEntity;
import es.gobcan.istac.edatos.external.users.core.domain.NeedTypeEntity;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.ProcStatusEnum;
import es.gobcan.istac.edatos.external.users.core.domain.vo.InternationalStringVO;
import es.gobcan.istac.edatos.external.users.core.domain.vo.LocalisedStringVO;

public class RoadmapDataTestsUtils {

    public static final String NEED_CODE_1 = "code1";
    public static final String NEED_CODE_2 = "code2";
    public static final String NEED_CODE_3 = "code3";
    public static final String NEED_CODE_UPDATE = "update_code";
    public static final String LOCALISED_STRING_LABEL_1 = "prueba!";
    public static final String LOCALISED_STRING_LOCALE_1 = "es";
    public static final String LOCALISED_STRING_LABEL_2 = "test!";
    public static final String LOCALISED_STRING_LOCALE_2 = "en";
    public static final String LOCALISED_STRING_LABEL_UPDATE = "teste!";
    public static final String LOCALISED_STRING_LOCALE_UPDATE = "pt";
    public static final String NEED_TYPE_CODE_1 = "need_type_code_1";
    public static final String NEED_TYPE_CODE_2 = "need_type_code_2";
    public static final String NEED_TYPE_CODE_3 = "need_type_code_3";
    public static final String NEED_TYPE_CODE_NO_EXIST = "need_type_code_no_exist";
    public static final String NEED_STATE_CODE_1 = "need_state_code_1";
    public static final String NEED_STATE_CODE_2 = "need_state_code_2";
    public static final String NEED_STATE_CODE_3 = "need_state_code_3";
    public static final String NEED_STATE_CODE_NO_EXIST = "need_state_code_no_exist";
    public static final InternationalStringVO LOCALISED_STRING = new InternationalStringVO();

    static {
        LOCALISED_STRING.getTexts().add(new LocalisedStringVO(LOCALISED_STRING_LABEL_1, LOCALISED_STRING_LOCALE_1));
        LOCALISED_STRING.getTexts().add(new LocalisedStringVO(LOCALISED_STRING_LABEL_2, LOCALISED_STRING_LOCALE_2));
    }

    private RoadmapDataTestsUtils() {
        // NOTHING TO DO HERE!
    }

    private static InternationalStringVO copyLocalizedString(InternationalStringVO internationalString) {
        InternationalStringVO is = new InternationalStringVO();
        is.getTexts().addAll(internationalString.getTexts().stream().collect(Collectors.toSet()));

        return is;
    }

    public static NeedEntity createEntity(String code, InternationalStringVO title, ProcStatusEnum procStatus) {
        NeedEntity need = new NeedEntity();
        need.setCode(code);
        need.setUrn(code != null ? GeneratorUrnUtils.generateSiemacStatisticalNeedUrn(code) : GeneratorUrnUtils.generateSiemacStatisticalNeedUrn("dummy"));
        need.setTitle(title != null ? copyLocalizedString(title) : null);
        need.setProcStatus(procStatus);

        return need;
    }

    public static NeedTypeEntity createNeedTypeEntity(String code, InternationalStringVO title) {
        NeedTypeEntity needTypeEntity = new NeedTypeEntity();
        needTypeEntity.setCode(code);
        needTypeEntity.setTitle(copyLocalizedString(title));

        return needTypeEntity;
    }

    public static NeedStateEntity createNeedStateEntity(String code, InternationalStringVO title) {
        NeedStateEntity needStateEntity = new NeedStateEntity();
        needStateEntity.setCode(code);
        needStateEntity.setTitle(copyLocalizedString(title));

        return needStateEntity;
    }

}
