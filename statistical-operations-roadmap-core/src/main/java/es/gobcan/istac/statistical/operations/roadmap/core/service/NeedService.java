package es.gobcan.istac.statistical.operations.roadmap.core.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.NeedEntity;

public interface NeedService {

    NeedEntity create(NeedEntity need);
    NeedEntity update(NeedEntity need);
    Page<NeedEntity> find(String query, Pageable pageable);
    List<NeedEntity> find(String query, Sort sort);
    NeedEntity findById(Long id);
    void delete(NeedEntity need);

}
