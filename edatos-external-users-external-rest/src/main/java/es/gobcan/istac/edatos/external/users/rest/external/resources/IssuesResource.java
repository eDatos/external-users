package es.gobcan.istac.edatos.external.users.rest.external.resources;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.gobcan.istac.edatos.external.users.rest.common.dto.IssuesDto;

@RestController
@RequestMapping(IssuesResource.BASE_URL)
public class IssuesResource extends AbstractResource {
	
	public static final String ENTITY_NAME = "issues";
    public static final String BASE_URL = "/api/issues";
    

    public void createIssue(@RequestBody IssuesDto dto) {
    	
    }
}
