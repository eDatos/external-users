package es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator;

import es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository.ListInstanceTypeRepository;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository.ListRepository;

public class ListInstanceTypeSQLGenerator extends ListSQLGenerator {

    private static final String TABLE_NAME = "tb_lis_instance_types";
    private static final String SEQUENCE_NAME = "seq_tb_lis_instance_types";

    private ListInstanceTypeRepository repository = new ListInstanceTypeRepository();

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
