package es.gobcan.istac.edatos.external.users.core.errors;

public final class ErrorConstants {

    public static final String ERR_CONCURRENCY_FAILURE    = "error.concurrencyFailure";
    public static final String ERR_ACCESS_DENIED          = "error.accessDenied";
    public static final String ERR_VALIDATION             = "error.validation";
    public static final String ERR_METHOD_NOT_SUPPORTED   = "error.methodNotSupported";
    public static final String ERR_INTERNAL_SERVER_ERROR  = "error.internalServerError";

    public static final String USUARIO_EXISTE             = "error.usuario-existe";
    public static final String USUARIO_LDAP_NO_ENCONTRADO = "error.userManagement.usuario-ldap-no-encontrado";
    public static final String USUARIO_NO_VALIDO          = "error.userManagement.usuario-no-valido";

    public static final String FICHERO_VACIO              = "error.file.empty";
    public static final String FICHERO_NO_ENCONTRADO      = "error.file.notFound";

    public static final String ENTIDAD_NO_ENCONTRADA      = "error.entidad-no-encontrada";
    public static final String ENTIDAD_ELIMINADA          = "error.entidadEliminada";
    public static final String ENTIDAD_NO_ELIMINADA       = "error.entidadNoEliminada";
    public static final String ID_EXISTE                  = "error.id-existe";
    public static final String ID_FALTA                   = "error.id-falta";

    public static final String QUERY_NO_SOPORTADA         = "error.query-no-soportada";

    private ErrorConstants() {
    }

}