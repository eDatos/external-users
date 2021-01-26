package es.gobcan.istac.statistical.operations.roadmap.internal.rest.resources;

import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
public class AbstractResource {

    public static final String SLASH = "/";
}
