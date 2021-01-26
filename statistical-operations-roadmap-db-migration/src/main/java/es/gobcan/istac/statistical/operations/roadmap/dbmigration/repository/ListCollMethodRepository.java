package es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository;

public class ListCollMethodRepository extends ListRepository {

    @Override
    protected String getTableName() {
        return "TB_LIS_COLL_METHODS";
    }
}
