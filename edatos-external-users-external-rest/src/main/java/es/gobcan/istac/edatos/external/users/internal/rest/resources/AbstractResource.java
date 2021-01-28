package es.gobcan.istac.edatos.external.users.internal.rest.resources;

import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
public class AbstractResource {

    public static final String SLASH = "/";
}
