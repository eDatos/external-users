package es.gobcan.istac.statistical.operations.roadmap.core.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.siemac.edatos.core.common.util.GeneratorUrnUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.NeedEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.enumeration.ProcStatusEnum;
import es.gobcan.istac.statistical.operations.roadmap.core.repository.NeedRepository;
import es.gobcan.istac.statistical.operations.roadmap.core.service.NeedService;
import es.gobcan.istac.statistical.operations.roadmap.core.service.validator.NeedValidator;
import es.gobcan.istac.statistical.operations.roadmap.core.util.QueryUtil;

@Service
public class NeedServiceImpl implements NeedService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NeedRepository needRepository;

    @Autowired
    private NeedValidator needValidator;

    @Autowired
    private QueryUtil queryUtil;

    @Override
    public NeedEntity create(NeedEntity need) {
        needValidator.checkCreateNeed(need);
        needValidator.checkNeedCodeUnique(null, need.getCode());

        need.setProcStatus(ProcStatusEnum.DRAFT);
        need.setUrn(GeneratorUrnUtils.generateSiemacStatisticalNeedUrn(need.getCode()));

        return needRepository.saveAndFlush(need);
    }

    @Override
    public NeedEntity update(NeedEntity need) {

        switch (need.getProcStatus()) {
            case DRAFT:
                needValidator.checkCreateNeed(need);
                needValidator.checkNeedCodeUnique(need.getId(), need.getCode());

                need.setUrn(GeneratorUrnUtils.generateSiemacStatisticalNeedUrn(need.getCode()));
                break;
            case INTERNALLY_PUBLISHED:
                needValidator.checkNeedForPublishInternally(need);
                break;
            case EXTERNALLY_PUBLISHED:
                needValidator.checkNeedForPublishExternally(need);
                break;
            default:
                break;
        }

        return needRepository.saveAndFlush(need);
    }

    @Override
    public Page<NeedEntity> find(String query, Pageable pageable) {
        DetachedCriteria criteria = queryUtil.queryToNeedCriteria(query, pageable);
        return needRepository.findAll(criteria, pageable);
    }

    @Override
    public List<NeedEntity> find(String query, Sort sort) {
        DetachedCriteria criteria = queryUtil.queryToNeedSortCriteria(query, sort);
        return needRepository.findAll(criteria);
    }

    @Override
    public NeedEntity findById(Long id) {
        return needRepository.findOne(id);
    }

    @Override
    public void delete(NeedEntity need) {
        needValidator.checkNeedProcStatusForDeletion(need);
        needRepository.delete(need);
    }

}
