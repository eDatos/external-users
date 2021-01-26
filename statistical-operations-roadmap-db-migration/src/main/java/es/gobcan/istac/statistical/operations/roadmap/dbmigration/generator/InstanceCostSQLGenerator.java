package es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain.InstanceCostEntity;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository.InstanceCostRepository;

public class InstanceCostSQLGenerator extends AbstractSQLGenerator {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String TABLE_NAME = "tb_instances_costs";

    private InstanceCostRepository repository = new InstanceCostRepository();

    public void generate(Connection connection) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        createFile(repository.findAll(connection));
    }

    private void createFile(List<InstanceCostEntity> entities) throws FileNotFoundException, UnsupportedEncodingException {
        if (entities.isEmpty()) {
            return;
        }

        PrintWriter writer = createPrintWriter(TABLE_NAME);

        writer.println("INSERT INTO " + TABLE_NAME + "(tb_instances, cost_fk) VALUES ");

        for (int i = 0; i < entities.size(); i++) {

            InstanceCostEntity entity = entities.get(i);

            writer.print("  (");
            writer.print(toInsertionValue(entity.getTbInstances()) + ", ");
            writer.print(toInsertionValue(entity.getCostFk()));
            writer.println(i == entities.size() - 1 ? "); " : "), ");
        }

        writer.println();

        writer.close();

        log.info(String.format("Generated script for %s", TABLE_NAME));
    }
}
