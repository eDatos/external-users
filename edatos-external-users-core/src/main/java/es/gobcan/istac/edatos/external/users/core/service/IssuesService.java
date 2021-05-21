package es.gobcan.istac.edatos.external.users.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.gobcan.istac.edatos.external.users.core.domain.IssuesEntity;

public interface IssuesService {

	IssuesEntity create (IssuesEntity issue);
	
	IssuesEntity update(IssuesEntity user);
	
	boolean delete(Long id);
	
	Page<IssuesEntity> find(Pageable pageable, Boolean includeDeleted, String query);
}
