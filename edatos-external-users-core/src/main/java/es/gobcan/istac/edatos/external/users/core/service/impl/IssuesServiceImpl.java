package es.gobcan.istac.edatos.external.users.core.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.IssuesEntity;
import es.gobcan.istac.edatos.external.users.core.repository.IssuesRepository;
import es.gobcan.istac.edatos.external.users.core.service.IssuesService;

@Service
public class IssuesServiceImpl implements IssuesService {

	private final IssuesRepository issuesRepository;
	
	public IssuesServiceImpl (IssuesRepository issuesRepository) {
		this.issuesRepository = issuesRepository;
	}
	
	@Override
	public IssuesEntity create(IssuesEntity issue) {
		return issuesRepository.saveAndFlush(issue);
	}

	@Override
	public IssuesEntity update(IssuesEntity user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Page<IssuesEntity> find(Pageable pageable, Boolean includeDeleted, String query) {
		// TODO Auto-generated method stub
		return null;
	}

}
