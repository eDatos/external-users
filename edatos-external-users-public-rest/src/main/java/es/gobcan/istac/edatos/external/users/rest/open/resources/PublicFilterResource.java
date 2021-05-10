package es.gobcan.istac.edatos.external.users.rest.open.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.siemac.edatos.core.common.exception.CommonServiceExceptionType;
import org.siemac.edatos.core.common.exception.EDatosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import es.gobcan.istac.edatos.external.users.core.config.AuditConstants;
import es.gobcan.istac.edatos.external.users.core.config.audit.AuditEventPublisher;
import es.gobcan.istac.edatos.external.users.core.domain.DataProtectionPolicyEntity;
import es.gobcan.istac.edatos.external.users.core.domain.FilterEntity;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Language;
import es.gobcan.istac.edatos.external.users.core.service.DataProtectionPolicyService;
import es.gobcan.istac.edatos.external.users.core.service.FilterService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.DataProtectionPolicyDto;
import es.gobcan.istac.edatos.external.users.rest.common.dto.ExternalUserDto;
import es.gobcan.istac.edatos.external.users.rest.common.dto.FilterDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.DataProtectionPolicyMapper;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.FilterMapper;
import es.gobcan.istac.edatos.external.users.rest.common.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping(PublicFilterResource.BASE_URL)
public class PublicFilterResource extends AbstractResource {

    public static final String ENTITY_NAME = "filter";
    public static final String BASE_URL = "/api/filtersPublic";

    private final FilterService filterService;

    private final FilterMapper filterMapper;

    private final AuditEventPublisher auditPublisher;
    
    @Autowired
    private  DataProtectionPolicyService dataProtectionPolicyService;
    @Autowired
    private  DataProtectionPolicyMapper dataProtectionPolicyMapper;


    public PublicFilterResource(FilterService filterService, FilterMapper filterMapper, AuditEventPublisher auditPublisher) {
        this.filterService = filterService;
        this.filterMapper = filterMapper;
        this.auditPublisher = auditPublisher;
    }
    
    @GetMapping
    @Timed
    public ResponseEntity<DataProtectionPolicyDto> getDataProtectionPolicy() {
        DataProtectionPolicyEntity dataProtectionPolicy = dataProtectionPolicyService.find();
        DataProtectionPolicyDto dataProtectionPolicyDto = dataProtectionPolicyMapper.toDto(dataProtectionPolicy);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dataProtectionPolicyDto));
    }

    @PostMapping
    @Timed
    public ResponseEntity<FilterDto> createFilter(@RequestBody FilterDto dto) throws URISyntaxException {
        if (dto != null && dto.getId() != null) {
            throw new EDatosException(CommonServiceExceptionType.PARAMETER_UNEXPECTED, "id");
        }
        ExternalUserDto defaultUser = new ExternalUserDto();
        defaultUser.setId(1L);
        dto.setExternalUser(defaultUser);
        
        FilterEntity entity = filterMapper.toEntity(dto);
        entity = filterService.create(entity);
        FilterDto newDto = filterMapper.toDto(entity);

        auditPublisher.publish(AuditConstants.FILTER_CREATION, newDto.getExternalUser().getId().toString());
        return ResponseEntity.created(new URI(BASE_URL + SLASH + newDto.getId()))
                             .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, newDto.getId().toString()))
                             .body(newDto);
    }
}
