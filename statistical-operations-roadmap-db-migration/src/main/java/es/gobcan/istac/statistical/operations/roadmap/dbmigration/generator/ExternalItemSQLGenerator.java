package es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain.ExternalItemEntity;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository.ExternalItemRepository;

public class ExternalItemSQLGenerator extends AbstractSQLGenerator {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String TABLE_NAME = "tb_external_items";
    private static final String SEQUENCE_NAME = "seq_tb_external_items";

    private ExternalItemRepository repository = new ExternalItemRepository();

    public void generate(Connection connection) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        createFile(repository.findAll(connection));
    }

    private void createFile(List<ExternalItemEntity> entities) throws FileNotFoundException, UnsupportedEncodingException {
        if (entities.isEmpty()) {
            return;
        }

        PrintWriter writer = createPrintWriter(TABLE_NAME);

        writer.println("INSERT INTO " + TABLE_NAME + "(id, code, code_nested, management_app_url, type, uri, urn, urn_provider, version, title_fk) VALUES ");

        for (int i = 0; i < entities.size(); i++) {

            ExternalItemEntity entity = entities.get(i);

            writer.print("  (");
            writer.print(entity.getId() + ", ");
            writer.print(toInsertionValue(entity.getCode()) + ", ");
            writer.print(toInsertionValue(entity.getCodeNested()) + ", ");
            writer.print(toInsertionValue(entity.getManagementAppUrl()) + ", ");
            writer.print(toInsertionValue(entity.getType()) + ", ");
            writer.print(toInsertionValue(entity.getUri()) + ", ");
            writer.print(toInsertionValue(entity.getUrn()) + ", ");
            writer.print(toInsertionValue(entity.getUrnProvider()) + ", ");
            writer.print(OPT_LOCK + ", ");
            writer.print(toInsertionValue(entity.getTitleFk()));
            writer.println(i == entities.size() - 1 ? "); " : "), ");
        }

        writer.println();
        writer.println("SELECT setval('" + SEQUENCE_NAME + "', (SELECT MAX(id) FROM " + TABLE_NAME + "));");

        writer.close();

        log.info(String.format("Generated script for %s", TABLE_NAME));
    }
}
