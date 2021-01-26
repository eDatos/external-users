package es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository;

public class ListOfficialityTypeRepository extends ListRepository {

    @Override
    protected String getTableName() {
        return "TB_LIS_OFFICIALITY_TYPES";
    }
}
