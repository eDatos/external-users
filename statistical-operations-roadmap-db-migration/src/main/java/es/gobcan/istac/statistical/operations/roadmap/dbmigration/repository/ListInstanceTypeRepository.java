package es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository;

public class ListInstanceTypeRepository extends ListRepository {

    @Override
    protected String getTableName() {
        return "TB_LIS_INSTANCE_TYPES";
    }
}
