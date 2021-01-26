package es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain;

public class InstanceCostEntity {

    public final class Columns {

        public static final String COST_FK = "COST_FK";
        public static final String TB_INSTANCES = "TB_INSTANCES";

        private Columns() {
        }
    }

    private Long costFk;
    private Long tbInstances;

    public Long getCostFk() {
        return costFk;
    }

    public void setCostFk(Long costFk) {
        this.costFk = costFk;
    }

    public Long getTbInstances() {
        return tbInstances;
    }

    public void setTbInstances(Long tbInstances) {
        this.tbInstances = tbInstances;
    }
}
