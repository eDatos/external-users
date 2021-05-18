package es.gobcan.istac.edatos.external.users.rest.external.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import es.gobcan.istac.edatos.external.users.core.domain.IssuesEntity;
import es.gobcan.istac.edatos.external.users.core.service.IssuesService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.IssuesDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.IssuesMapper;

@RestController
@RequestMapping(IssuesResource.BASE_URL)
public class IssuesResource extends AbstractResource {
	
	public static final String ENTITY_NAME = "issues";
    public static final String BASE_URL = "/api/issues";
    
    private final IssuesService issuesService;
    private final IssuesMapper issuesMapper;
    
    public IssuesResource(IssuesService issuesService, IssuesMapper issuesMapper) {
		this.issuesService = issuesService;
		this.issuesMapper = issuesMapper;
	}

    @PostMapping("/create")
	public ResponseEntity<IssuesDto> createIssue(@RequestBody IssuesDto dto) {
    	IssuesEntity issuesEntity = issuesMapper.toEntity(dto);
    	issuesService.create(issuesEntity);
    	return ResponseEntity.ok(issuesMapper.toDto(issuesEntity));
    }
}
