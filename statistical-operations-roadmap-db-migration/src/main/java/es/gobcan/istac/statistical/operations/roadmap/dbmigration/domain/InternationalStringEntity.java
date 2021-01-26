package es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain;

public class InternationalStringEntity {

    public final class Columns {

        public static final String ID = "ID";
        public static final String VERSION = "VERSION";

        private Columns() {
        }
    }

    private Long id;
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
