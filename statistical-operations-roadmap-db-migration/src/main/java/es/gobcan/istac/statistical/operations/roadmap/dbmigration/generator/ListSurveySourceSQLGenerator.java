package es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator;

import es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository.ListRepository;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository.ListSurveySourceRepository;

public class ListSurveySourceSQLGenerator extends ListSQLGenerator {

    private static final String TABLE_NAME = "tb_lis_survey_sources";
    private static final String SEQUENCE_NAME = "seq_tb_lis_survey_sources";

    private ListSurveySourceRepository repository = new ListSurveySourceRepository();

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
