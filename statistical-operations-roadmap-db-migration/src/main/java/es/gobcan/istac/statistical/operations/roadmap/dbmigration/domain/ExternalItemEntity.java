package es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain;

public class ExternalItemEntity {

    public final class Columns {

        public static final String ID = "ID";
        public static final String CODE = "CODE";
        public static final String URI = "URI";
        public static final String URN = "URN";
        public static final String URN_PROVIDER = "URN_PROVIDER";
        public static final String MANAGEMENT_APP_URL = "MANAGEMENT_APP_URL";
        public static final String VERSION = "VERSION";
        public static final String TITLE_FK = "TITLE_FK";
        public static final String TYPE = "TYPE";
        public static final String CODE_NESTED = "CODE_NESTED";

        private Columns() {
        }
    }

    private Long id;
    private String code;
    private String uri;
    private String urn;
    private String urnProvider;
    private String managementAppUrl;
    private Long version;
    private Long titleFk;
    private String type;
    private String codeNested;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUrn() {
        return urn;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    public String getUrnProvider() {
        return urnProvider;
    }

    public void setUrnProvider(String urnProvider) {
        this.urnProvider = urnProvider;
    }

    public String getManagementAppUrl() {
        return managementAppUrl;
    }

    public void setManagementAppUrl(String managementAppUrl) {
        this.managementAppUrl = managementAppUrl;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getTitleFk() {
        return titleFk;
    }

    public void setTitleFk(Long titleFk) {
        this.titleFk = titleFk;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCodeNested() {
        return codeNested;
    }

    public void setCodeNested(String codeNested) {
        this.codeNested = codeNested;
    }
}
