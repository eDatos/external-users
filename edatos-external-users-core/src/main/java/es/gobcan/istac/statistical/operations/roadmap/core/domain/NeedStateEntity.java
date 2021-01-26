package es.gobcan.istac.statistical.operations.roadmap.core.domain;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "tb_need_states", uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
@SequenceGenerator(name = "seq_generator_name", sequenceName = "seq_tb_need_states", allocationSize = 50, initialValue = 1)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NeedStateEntity extends TableValueEntity {

    private static final long serialVersionUID = 1L;

}
