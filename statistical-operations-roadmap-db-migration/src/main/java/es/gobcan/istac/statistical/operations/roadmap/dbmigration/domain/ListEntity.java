package es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain;

public class ListEntity {

    public final class Columns {

        public static final String ID = "ID";
        public static final String IDENTIFIER = "IDENTIFIER";
        public static final String UUID = "UUID";
        public static final String VERSION = "VERSION";
        public static final String DESCRIPTION = "DESCRIPTION";

        private Columns() {
        }
    }

    private Long id;
    private String identifier;
    private String uuid;
    private Long version;
    private Long description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getDescription() {
        return description;
    }

    public void setDescription(Long description) {
        this.description = description;
    }
}
