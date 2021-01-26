package es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain.InternationalStringEntity;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository.InternationalStringRepository;

public class InternationalStringSQLGenerator extends AbstractSQLGenerator {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String TABLE_NAME = "tb_international_strings";
    private static final String SEQUENCE_NAME = "seq_tb_international_strings";

    private InternationalStringRepository repository = new InternationalStringRepository();

    public void generate(Connection connection) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        createFile(repository.findAll(connection));
    }

    private void createFile(List<InternationalStringEntity> entities) throws FileNotFoundException, UnsupportedEncodingException {
        if (entities.isEmpty()) {
            return;
        }

        PrintWriter writer = createPrintWriter(TABLE_NAME);

        writer.println("INSERT INTO " + TABLE_NAME + "(id, version) VALUES ");

        for (int i = 0; i < entities.size(); i++) {

            InternationalStringEntity entity = entities.get(i);

            writer.print("  (");
            writer.print(entity.getId() + ", ");
            writer.print(OPT_LOCK);
            writer.println(i == entities.size() - 1 ? "); " : "), ");
        }

        writer.println();
        writer.println("SELECT setval('" + SEQUENCE_NAME + "', (SELECT MAX(id) FROM " + TABLE_NAME + "));");

        writer.close();

        log.info(String.format("Generated script for %s", TABLE_NAME));
    }
}
