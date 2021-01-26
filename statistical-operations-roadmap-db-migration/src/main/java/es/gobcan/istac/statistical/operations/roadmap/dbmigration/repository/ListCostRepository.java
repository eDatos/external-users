package es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository;

public class ListCostRepository extends ListRepository {

    @Override
    protected String getTableName() {
        return "TB_LIS_COSTS";
    }
}
