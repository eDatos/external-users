package es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator;

import es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository.ListRepository;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository.ListSurveyTypeRepository;

public class ListSurveyTypeSQLGenerator extends ListSQLGenerator {

    private static final String TABLE_NAME = "tb_lis_survey_types";
    private static final String SEQUENCE_NAME = "seq_tb_lis_survey_types";

    private ListSurveyTypeRepository repository = new ListSurveyTypeRepository();

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getSequenceName() {
        return SEQUENCE_NAME;
    }

    @Override
    protected ListRepository getRepository() {
        return repository;
    }
}
