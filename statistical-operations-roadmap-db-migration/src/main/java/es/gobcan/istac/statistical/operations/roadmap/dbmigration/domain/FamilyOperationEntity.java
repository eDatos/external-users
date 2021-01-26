package es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain;

public class FamilyOperationEntity {

    public final class Columns {

        public static final String OPERATION = "OPERATION";
        public static final String FAMILY = "FAMILY";

        private Columns() {
        }
    }

    private Long operation;
    private Long family;

    public Long getOperation() {
        return operation;
    }

    public void setOperation(Long operation) {
        this.operation = operation;
    }

    public Long getFamily() {
        return family;
    }

    public void setFamily(Long family) {
        this.family = family;
    }
}
