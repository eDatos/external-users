package es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain.FamilyOperationEntity;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository.FamilyOperationRepository;

public class FamilyOperationSQLGenerator extends AbstractSQLGenerator {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String TABLE_NAME = "tb_families_operations";

    private FamilyOperationRepository repository = new FamilyOperationRepository();

    public void generate(Connection connection) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        createFile(repository.findAll(connection));
    }

    private void createFile(List<FamilyOperationEntity> entities) throws FileNotFoundException, UnsupportedEncodingException {
        if (entities.isEmpty()) {
            return;
        }

        PrintWriter writer = createPrintWriter(TABLE_NAME);

        writer.println("INSERT INTO " + TABLE_NAME + "(family, operation) VALUES ");

        for (int i = 0; i < entities.size(); i++) {

            FamilyOperationEntity entity = entities.get(i);

            writer.print("  (");
            writer.print(toInsertionValue(entity.getFamily()) + ", ");
            writer.print(toInsertionValue(entity.getOperation()));
            writer.println(i == entities.size() - 1 ? "); " : "), ");
        }

        writer.println();

        writer.close();

        log.info(String.format("Generated script for %s", TABLE_NAME));
    }
}
