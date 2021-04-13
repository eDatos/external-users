package es.gobcan.istac.edatos.external.users.rest.internal.resources;

import java.util.Optional;

import org.siemac.edatos.core.common.dto.InternationalStringDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import es.gobcan.istac.edatos.external.users.core.domain.DataProtectionPolicyEntity;
import es.gobcan.istac.edatos.external.users.core.service.DataProtectionPolicyService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.DataProtectionPolicyDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.DataProtectionPolicyMapper;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.InternationalStringVOMapper;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping(DataProtectionPolicyResource.BASE_URL)
public class DataProtectionPolicyResource extends AbstractResource {

    public static final String BASE_URL = "/api/data-protection-policy";

    private final DataProtectionPolicyService dataProtectionPolicyService;
    private final DataProtectionPolicyMapper dataProtectionPolicyMapper;
    private final InternationalStringVOMapper internationalStringVOMapper;

    public DataProtectionPolicyResource(DataProtectionPolicyService dataProtectionPolicyService, DataProtectionPolicyMapper dataProtectionPolicyMapper, InternationalStringVOMapper internationalStringVOMapper) {
        this.dataProtectionPolicyService = dataProtectionPolicyService;
        this.dataProtectionPolicyMapper = dataProtectionPolicyMapper;
        this.internationalStringVOMapper = internationalStringVOMapper;
    }

    @GetMapping
    @Timed
    public ResponseEntity<DataProtectionPolicyDto> getDataProtectionPolicy() {
        DataProtectionPolicyEntity dataProtectionPolicy = dataProtectionPolicyService.find();
        DataProtectionPolicyDto dataProtectionPolicyDto = dataProtectionPolicyMapper.toDto(dataProtectionPolicy);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dataProtectionPolicyDto));
    }

    @PutMapping
    @Timed
    @PreAuthorize("@secChecker.canUpdateDataProtectionPolicy(authentication)")
    public ResponseEntity<DataProtectionPolicyDto> updateDataProtectionPolicy(@RequestBody InternationalStringDto dataProtectionPolicyValue) {
        DataProtectionPolicyEntity dataProtectionPolicy = dataProtectionPolicyService.update(internationalStringVOMapper.toEntity(dataProtectionPolicyValue));
        DataProtectionPolicyDto dataProtectionPolicyDto = dataProtectionPolicyMapper.toDto(dataProtectionPolicy);
        return ResponseEntity.ok().body(dataProtectionPolicyDto);
    }
}
