package es.gobcan.istac.edatos.external.users.rest.common.mapper.resolver;

public class GenericMapperExcetion extends RuntimeException {

    public GenericMapperExcetion(ReflectiveOperationException e) {
        super(e);
    }

    public GenericMapperExcetion(String string, ReflectiveOperationException e) {
        super(string, e);
    }

}
