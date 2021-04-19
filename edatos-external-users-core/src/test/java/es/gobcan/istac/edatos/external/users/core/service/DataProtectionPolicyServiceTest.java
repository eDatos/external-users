package es.gobcan.istac.edatos.external.users.core.service;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import es.gobcan.istac.edatos.external.users.EdatosExternalUsersCoreTestApp;
import es.gobcan.istac.edatos.external.users.core.config.MetadataProperties;
import es.gobcan.istac.edatos.external.users.core.domain.DataProtectionPolicyEntity;
import es.gobcan.istac.edatos.external.users.core.domain.vo.InternationalStringVO;
import es.gobcan.istac.edatos.external.users.core.domain.vo.LocalisedStringVO;
import es.gobcan.istac.edatos.external.users.core.repository.DataProtectionPolicyRepository;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdatosExternalUsersCoreTestApp.class)
public class DataProtectionPolicyServiceTest {

    @Autowired
    private DataProtectionPolicyRepository dataProtectionPolicyRepository;
    
    @Autowired
    private DataProtectionPolicyService dataProtectionPolicyService;
    
    @Autowired
    private MetadataProperties metadataProperties;

    private List<String> languages;

    @Before
    public void setup() {
        languages = metadataProperties.getLanguages().stream().map(language -> language.toLowerCase()).collect(Collectors.toList());
        
    }

    @Test
    public void testFindDataProtectionPolicyAddLanguages() throws Exception {
        DataProtectionPolicyEntity databaseEntity = dataProtectionPolicyRepository.findFirstByOrderByIdAsc();
        databaseEntity.getValue().removeAllTexts();
        dataProtectionPolicyRepository.saveAndFlush(databaseEntity);
        DataProtectionPolicyEntity returnedValue = dataProtectionPolicyService.find();
        
        assertThat(returnedValue.getId()).isEqualTo(databaseEntity.getId());
        assertThat(returnedValue.getOptLock()).isNotEqualTo(databaseEntity.getOptLock());
        assertThat(returnedValue.getCreatedBy()).isEqualTo(databaseEntity.getCreatedBy());
        assertThat(returnedValue.getCreatedDate()).isEqualTo(databaseEntity.getCreatedDate());
        assertThat(returnedValue.getLastModifiedBy()).isEqualTo(databaseEntity.getLastModifiedBy());
        assertThat(returnedValue.getLastModifiedDate()).isNotEqualTo(databaseEntity.getLastModifiedDate());
        assertThat(returnedValue.getValue().getTexts()).hasSize(languages.size());
        
        InternationalStringVO value = returnedValue.getValue();
        for(String language: languages) {
            assertThat(value.getLocalisedLabel(language)).isEmpty();
        }
    }
    
    @Test
    public void testFindDataProtectionPolicyRemoveLanguages() throws Exception {
        DataProtectionPolicyEntity databaseEntity = dataProtectionPolicyRepository.findFirstByOrderByIdAsc();
        InternationalStringVO updateParam = new InternationalStringVO();
        for(String language: languages) {
            LocalisedStringVO rightLocaleString = new LocalisedStringVO(language, language);
            updateParam.addText(rightLocaleString);
        }
        LocalisedStringVO wrongLocaleString = new LocalisedStringVO("klingon", "klingon");
        updateParam.addText(wrongLocaleString);
        databaseEntity.setValue(updateParam);
        dataProtectionPolicyRepository.saveAndFlush(databaseEntity);
        
        DataProtectionPolicyEntity returnedValue = dataProtectionPolicyService.find();
        InternationalStringVO value = returnedValue.getValue();

        assertThat(returnedValue.getId()).isEqualTo(databaseEntity.getId());
        assertThat(returnedValue.getOptLock()).isNotEqualTo(databaseEntity.getOptLock());
        assertThat(returnedValue.getCreatedBy()).isEqualTo(databaseEntity.getCreatedBy());
        assertThat(returnedValue.getCreatedDate()).isEqualTo(databaseEntity.getCreatedDate());
        assertThat(returnedValue.getLastModifiedBy()).isEqualTo(databaseEntity.getCreatedBy());
        assertThat(returnedValue.getLastModifiedDate()).isNotEqualTo(databaseEntity.getLastModifiedDate());
        for(String language: languages) {
            assertThat(value.getLocalisedLabel(language)).isEqualTo(language);
        }
        assertThat(value.getTexts()).hasSize(languages.size());
    }
    
    @Test
    public void testUpdateDataProtectionPolicyOnlyNeededLanguages() throws Exception {
        DataProtectionPolicyEntity databaseEntity = dataProtectionPolicyRepository.findFirstByOrderByIdAsc();
        DataProtectionPolicyEntity updateParam = new DataProtectionPolicyEntity();
        InternationalStringVO texts = new InternationalStringVO();
        for(String language: languages.subList(1, languages.size())) {
            LocalisedStringVO rightLocaleString = new LocalisedStringVO(language, language);
            texts.addText(rightLocaleString);
        }
        updateParam.setValue(texts);
        
        dataProtectionPolicyService.update(updateParam);
        DataProtectionPolicyEntity returnedValue = dataProtectionPolicyRepository.findFirstByOrderByIdAsc();
        InternationalStringVO value = returnedValue.getValue();

        assertThat(returnedValue.getId()).isEqualTo(databaseEntity.getId());
        assertThat(returnedValue.getOptLock()).isNotEqualTo(databaseEntity.getOptLock());
        assertThat(returnedValue.getCreatedBy()).isEqualTo(databaseEntity.getCreatedBy());
        assertThat(returnedValue.getCreatedDate()).isEqualTo(databaseEntity.getCreatedDate());
        assertThat(returnedValue.getLastModifiedBy()).isEqualTo(databaseEntity.getCreatedBy());
        assertThat(returnedValue.getLastModifiedDate()).isNotEqualTo(databaseEntity.getLastModifiedDate());
        for(String language: languages.subList(1, languages.size())) {
            assertThat(value.getLocalisedLabel(language)).isEqualTo(language);
        }
        assertThat(value.getTexts()).hasSize(languages.size() - 1);
    }
    
    @Test
    public void testUpdateDataProtectionPolicyIgnoreWrongLocales() throws Exception {
        DataProtectionPolicyEntity databaseEntity = dataProtectionPolicyRepository.findFirstByOrderByIdAsc();
        databaseEntity.getValue().removeAllTexts();
        databaseEntity.getValue().addText(new LocalisedStringVO("en", "en"));
        dataProtectionPolicyRepository.saveAndFlush(databaseEntity);
        
        DataProtectionPolicyEntity updateParam = new DataProtectionPolicyEntity();
        InternationalStringVO texts = new InternationalStringVO();
        texts.addText(new LocalisedStringVO("klingon", "klingon"));
        texts.addText(new LocalisedStringVO("English klingon", "en"));
        updateParam.setValue(texts);
        
        dataProtectionPolicyService.update(updateParam);
        DataProtectionPolicyEntity returnedValue = dataProtectionPolicyRepository.findFirstByOrderByIdAsc();
        InternationalStringVO value = returnedValue.getValue();

        assertThat(returnedValue.getId()).isEqualTo(databaseEntity.getId());
        assertThat(returnedValue.getOptLock()).isNotEqualTo(databaseEntity.getOptLock());
        assertThat(returnedValue.getCreatedBy()).isEqualTo(databaseEntity.getCreatedBy());
        assertThat(returnedValue.getCreatedDate()).isEqualTo(databaseEntity.getCreatedDate());
        assertThat(returnedValue.getLastModifiedBy()).isEqualTo(databaseEntity.getCreatedBy());
        assertThat(returnedValue.getLastModifiedDate()).isNotEqualTo(databaseEntity.getLastModifiedDate());
        assertThat(value.getLocalisedLabel("klingon")).isNull();
        assertThat(value.getLocalisedLabel("en")).isEqualTo("English klingon");
        assertThat(value.getTexts()).hasSize(1);
    }
}
