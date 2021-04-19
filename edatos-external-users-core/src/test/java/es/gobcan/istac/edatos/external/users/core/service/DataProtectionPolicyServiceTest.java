package es.gobcan.istac.edatos.external.users.core.service;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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

    @Spy
    private JavaMailSenderImpl javaMailSender;

    @SuppressWarnings("rawtypes")
    @Captor
    private ArgumentCaptor messageCaptor;

    /* Mio */
    @Autowired
    private DataProtectionPolicyRepository dataProtectionPolicyRepository;
    
    @Autowired
    private DataProtectionPolicyService dataProtectionPolicyService;
    
    @Autowired
    private MetadataProperties metadataProperties;

    private List<String> languages;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        languages = metadataProperties.getLanguages().stream().map(language -> language.toLowerCase()).collect(Collectors.toList());
    }

    @Test
    public void testFindDataProtectionPolicyAddLanguages() throws Exception {
        DataProtectionPolicyEntity databaseEntity = dataProtectionPolicyRepository.findOne(0L);
        databaseEntity.getValue().removeAllTexts();
        dataProtectionPolicyRepository.saveAndFlush(databaseEntity); 
        
        
        DataProtectionPolicyEntity returnedValue = dataProtectionPolicyService.find();
        assertThat(returnedValue.getId()).isZero();
        assertThat(returnedValue.getCreatedBy()).isEqualTo(databaseEntity.getCreatedBy());
        assertThat(returnedValue.getCreatedDate()).isEqualTo(databaseEntity.getCreatedDate());
        InternationalStringVO value = returnedValue.getValue();
        for(String language: languages) {
            assertThat(value.getLocalisedLabel(language)).isNotNull();
            assertThat(value.getLocalisedLabel(language)).isEmpty();
        }
        assertThat(value.getTexts()).hasSize(languages.size());
    }
    
    @Test
    public void testFindDataProtectionPolicyRemoveLanguages() throws Exception {
        DataProtectionPolicyEntity databaseEntity = dataProtectionPolicyRepository.findOne(0L);
        databaseEntity.getValue().removeAllTexts();
        InternationalStringVO updateParam = new InternationalStringVO();
        for(String language: languages) {
            LocalisedStringVO localisedString = new LocalisedStringVO();
            localisedString.setLabel(language);
            localisedString.setLocale(language);
            updateParam.addText(localisedString);
        }
        LocalisedStringVO localisedString = new LocalisedStringVO();
        localisedString.setLabel("klingon");
        localisedString.setLocale("klingon");
        updateParam.addText(localisedString);
        databaseEntity.setValue(updateParam);
        dataProtectionPolicyRepository.saveAndFlush(databaseEntity);
        
        
        DataProtectionPolicyEntity returnedValue = dataProtectionPolicyService.find();
        assertThat(returnedValue.getId()).isZero();
        assertThat(returnedValue.getCreatedBy()).isEqualTo(databaseEntity.getCreatedBy());
        assertThat(returnedValue.getCreatedDate()).isEqualTo(databaseEntity.getCreatedDate());
        InternationalStringVO value = returnedValue.getValue();
        for(String language: languages) {
            assertThat(value.getLocalisedLabel(language)).isNotNull();
            assertThat(value.getLocalisedLabel(language)).isEmpty();
        }
        assertThat(value.getTexts()).hasSize(languages.size());
    }
    
    @Test
    public void testUpdateDataProtectionPolicyOnlyNeededLanguages() throws Exception {
        DataProtectionPolicyEntity databaseEntity = dataProtectionPolicyRepository.findOne(0L);
        databaseEntity.getValue().removeAllTexts();
        LocalisedStringVO localisedString = new LocalisedStringVO();
        localisedString.setLabel("es");
        localisedString.setLocale("es");
        databaseEntity.getValue().addText(localisedString);
        dataProtectionPolicyRepository.saveAndFlush(databaseEntity);
        
        InternationalStringVO updateParam = new InternationalStringVO();
        localisedString = new LocalisedStringVO();
        localisedString.setLabel("en");
        localisedString.setLocale("en");
        updateParam.addText(localisedString);
        
        dataProtectionPolicyService.update(updateParam);
        DataProtectionPolicyEntity returnedValue = dataProtectionPolicyRepository.findOne(0L);
        assertThat(returnedValue.getId()).isZero();
        assertThat(returnedValue.getCreatedBy()).isEqualTo(databaseEntity.getCreatedBy());
        assertThat(returnedValue.getCreatedDate()).isEqualTo(databaseEntity.getCreatedDate());
        InternationalStringVO value = returnedValue.getValue();
        assertThat(value.getLocalisedLabel("es")).isEqualTo("es");
        assertThat(value.getLocalisedLabel("en")).isEqualTo("en");
        assertThat(value.getTexts()).hasSize(2);
    }
    
    @Test
    public void testUpdateDataProtectionPolicyIgnoreWrongLocales() throws Exception {
        DataProtectionPolicyEntity databaseEntity = dataProtectionPolicyRepository.findOne(0L);
        databaseEntity.getValue().removeAllTexts();
        LocalisedStringVO localisedString = new LocalisedStringVO();
        localisedString.setLabel("en");
        localisedString.setLocale("en");
        dataProtectionPolicyRepository.saveAndFlush(databaseEntity);
        
        InternationalStringVO updateParam = new InternationalStringVO();
        localisedString = new LocalisedStringVO();
        localisedString.setLabel("klingon");
        localisedString.setLocale("klingon");
        updateParam.addText(localisedString);
        localisedString = new LocalisedStringVO();
        localisedString.setLabel("English klingon");
        localisedString.setLocale("en");
        updateParam.addText(localisedString);
        
        
        dataProtectionPolicyService.update(updateParam);
        DataProtectionPolicyEntity returnedValue = dataProtectionPolicyRepository.findOne(0L);
        assertThat(returnedValue.getId()).isZero();
        assertThat(returnedValue.getCreatedBy()).isEqualTo(databaseEntity.getCreatedBy());
        assertThat(returnedValue.getCreatedDate()).isEqualTo(databaseEntity.getCreatedDate());
        InternationalStringVO value = returnedValue.getValue();
        assertThat(value.getLocalisedLabel("klingon")).isNull();
        assertThat(value.getLocalisedLabel("en")).isEqualTo("English klingon");
        assertThat(value.getTexts()).hasSize(2);
    }

    /** Tests pendientes
     * Comprobar que añade los idiomas en el find pero que los que están tienen valor
     * Comprobar que quita los idiomas en el find si ya no están en el retrieveLanguages
     * Comprobar que se ignoran los locales que no están en el retrieveLanguages en el update
     * Comprobar que no se borran/actualizan los lenguajes que no aparecen en el parámetro del update
     */
/*
    @Test
    public void testSendEmail() throws Exception {
        mailService.send("john.doe@example.com", "testSubject", "testContent", false, false);
        verify(javaMailSender).send((MimeMessage) messageCaptor.capture());
        MimeMessage message = (MimeMessage) messageCaptor.getValue();
        assertThat(message.getSubject()).isEqualTo("testSubject");
        assertThat(message.getAllRecipients()[0].toString()).isEqualTo("john.doe@example.com");
        assertThat(message.getFrom()[0].toString()).isEqualTo("test@localhost");
        assertThat(message.getContent()).isInstanceOf(String.class);
        assertThat(message.getContent().toString()).isEqualTo("testContent");
        assertThat(message.getDataHandler().getContentType()).isEqualTo("text/plain; charset=UTF-8");
    }

    @Test
    public void testSendHtmlEmail() throws Exception {
        mailService.send("john.doe@example.com", "testSubject", "testContent", false, true);
        verify(javaMailSender).send((MimeMessage) messageCaptor.capture());
        MimeMessage message = (MimeMessage) messageCaptor.getValue();
        assertThat(message.getSubject()).isEqualTo("testSubject");
        assertThat(message.getAllRecipients()[0].toString()).isEqualTo("john.doe@example.com");
        assertThat(message.getFrom()[0].toString()).isEqualTo("test@localhost");
        assertThat(message.getContent()).isInstanceOf(String.class);
        assertThat(message.getContent().toString()).isEqualTo("testContent");
        assertThat(message.getDataHandler().getContentType()).isEqualTo("text/html;charset=UTF-8");
    }

    @Test
    public void testSendMultipartEmail() throws Exception {
        mailService.send("john.doe@example.com", "testSubject", "testContent", true, false);
        verify(javaMailSender).send((MimeMessage) messageCaptor.capture());
        MimeMessage message = (MimeMessage) messageCaptor.getValue();
        MimeMultipart mp = (MimeMultipart) message.getContent();
        MimeBodyPart part = (MimeBodyPart) ((MimeMultipart) mp.getBodyPart(0).getContent()).getBodyPart(0);
        ByteArrayOutputStream aos = new ByteArrayOutputStream();
        part.writeTo(aos);
        assertThat(message.getSubject()).isEqualTo("testSubject");
        assertThat(message.getAllRecipients()[0].toString()).isEqualTo("john.doe@example.com");
        assertThat(message.getFrom()[0].toString()).isEqualTo("test@localhost");
        assertThat(message.getContent()).isInstanceOf(Multipart.class);
        assertThat(aos.toString()).isEqualTo("\r\ntestContent");
        assertThat(part.getDataHandler().getContentType()).isEqualTo("text/plain; charset=UTF-8");
    }

    @Test
    public void testSendMultipartHtmlEmail() throws Exception {
        mailService.send("john.doe@example.com", "testSubject", "testContent", true, true);
        verify(javaMailSender).send((MimeMessage) messageCaptor.capture());
        MimeMessage message = (MimeMessage) messageCaptor.getValue();
        MimeMultipart mp = (MimeMultipart) message.getContent();
        MimeBodyPart part = (MimeBodyPart) ((MimeMultipart) mp.getBodyPart(0).getContent()).getBodyPart(0);
        ByteArrayOutputStream aos = new ByteArrayOutputStream();
        part.writeTo(aos);
        assertThat(message.getSubject()).isEqualTo("testSubject");
        assertThat(message.getAllRecipients()[0].toString()).isEqualTo("john.doe@example.com");
        assertThat(message.getFrom()[0].toString()).isEqualTo("test@localhost");
        assertThat(message.getContent()).isInstanceOf(Multipart.class);
        assertThat(aos.toString()).isEqualTo("\r\ntestContent");
        assertThat(part.getDataHandler().getContentType()).isEqualTo("text/html;charset=UTF-8");
    }

    @Test
    public void testSendEmailFromTemplate() throws Exception {
        UsuarioEntity user = new UsuarioEntity();
        user.setLogin("john");
        user.setEmail("john.doe@example.com");
        mailService.sendFromTemplate(user, "testEmail", "email.test.title");
        verify(javaMailSender).send((MimeMessage) messageCaptor.capture());
        MimeMessage message = (MimeMessage) messageCaptor.getValue();
        assertThat(message.getSubject()).isEqualTo("test title");
        assertThat(message.getAllRecipients()[0].toString()).isEqualTo(user.getEmail());
        assertThat(message.getFrom()[0].toString()).isEqualTo("test@localhost");
        assertThat(message.getContent().toString()).isEqualTo("<html>test title, http://127.0.0.1:8080, john</html>\n");
        assertThat(message.getDataHandler().getContentType()).isEqualTo("text/html;charset=UTF-8");
    }

    @Test
    public void testCreationEmail() throws Exception {
        UsuarioEntity user = new UsuarioEntity();
        user.setLogin("john");
        user.setEmail("john.doe@example.com");
        mailService.sendCreationEmail(user);
        verify(javaMailSender).send((MimeMessage) messageCaptor.capture());
        MimeMessage message = (MimeMessage) messageCaptor.getValue();
        assertThat(message.getAllRecipients()[0].toString()).isEqualTo(user.getEmail());
        assertThat(message.getFrom()[0].toString()).isEqualTo("test@localhost");
        assertThat(message.getContent().toString()).isNotEmpty();
        assertThat(message.getDataHandler().getContentType()).isEqualTo("text/html;charset=UTF-8");
    }

    @Test
    public void testSendEmailWithException() throws Exception {
        doThrow(MailSendException.class).when(javaMailSender).send(any(MimeMessage.class));
        mailService.send("john.doe@example.com", "testSubject", "testContent", false, false);
    }
*/
}
