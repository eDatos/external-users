package es.gobcan.istac.edatos.external.users.rest.internal.resources;

import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
public class AbstractResource {

    public static final String SLASH = "/";
}
