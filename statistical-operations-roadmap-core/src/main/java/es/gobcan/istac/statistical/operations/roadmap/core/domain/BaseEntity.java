package es.gobcan.istac.statistical.operations.roadmap.core.domain;

import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.interfaces.AbstractVersionedAndAuditingEntity;

@TypeDef(name = "json", typeClass = JsonStringType.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@MappedSuperclass
public abstract class BaseEntity extends AbstractVersionedAndAuditingEntity {

}
