package es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository;

public class ListSurveyTypeRepository extends ListRepository {

    @Override
    protected String getTableName() {
        return "TB_LIS_SURVEY_TYPES";
    }
}
