package es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator;

import es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository.ListCostRepository;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository.ListRepository;

public class ListCostSQLGenerator extends ListSQLGenerator {

    private static final String TABLE_NAME = "tb_lis_costs";
    private static final String SEQUENCE_NAME = "seq_tb_lis_costs";

    private ListCostRepository repository = new ListCostRepository();

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
