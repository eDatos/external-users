package es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain.ListEntity;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository.ListRepository;

public abstract class ListSQLGenerator extends AbstractSQLGenerator {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    public void generate(Connection connection) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        createFile(getRepository().findAll(connection));
    }

    private void createFile(List<ListEntity> entities) throws FileNotFoundException, UnsupportedEncodingException {
        if (entities.isEmpty()) {
            return;
        }

        PrintWriter writer = createPrintWriter(getTableName());

        writer.println("INSERT INTO " + getTableName() + "(id, opt_lock, identifier, uuid, description) VALUES ");

        for (int i = 0; i < entities.size(); i++) {

            ListEntity entity = entities.get(i);

            writer.print("  (");
            writer.print(entity.getId() + ", ");
            writer.print(OPT_LOCK + ", ");
            writer.print(toInsertionValue(entity.getIdentifier()) + ", ");
            writer.print(toInsertionValue(entity.getUuid()) + ", ");
            writer.print(toInsertionValue(entity.getDescription()));
            writer.println(i == entities.size() - 1 ? "); " : "), ");
        }

        writer.println();
        writer.println("SELECT setval('" + getSequenceName() + "', (SELECT MAX(id) FROM " + getTableName() + "));");

        writer.close();

        log.info(String.format("Generated script for %s", getTableName()));
    }

    protected abstract String getTableName();
    protected abstract String getSequenceName();
    protected abstract ListRepository getRepository();
}
