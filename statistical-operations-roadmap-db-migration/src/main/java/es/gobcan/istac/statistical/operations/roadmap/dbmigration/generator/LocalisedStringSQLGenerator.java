package es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain.LocalisedStringEntity;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository.LocalisedStringRepository;

public class LocalisedStringSQLGenerator extends AbstractSQLGenerator {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String TABLE_NAME = "tb_localised_strings";
    private static final String SEQUENCE_NAME = "seq_tb_localised_strings";

    private LocalisedStringRepository repository = new LocalisedStringRepository();

    public void generate(Connection connection) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        createFile(repository.findAll(connection));
    }

    private void createFile(List<LocalisedStringEntity> entities) throws FileNotFoundException, UnsupportedEncodingException {
        if (entities.isEmpty()) {
            return;
        }

        PrintWriter writer = createPrintWriter(TABLE_NAME);

        writer.println("INSERT INTO " + TABLE_NAME + "(id, is_unmodifiable, label, locale, version, international_string_fk) VALUES ");

        for (int i = 0; i < entities.size(); i++) {

            LocalisedStringEntity entity = entities.get(i);

            writer.print("  (");
            writer.print(entity.getId() + ", ");
            writer.print(toInsertionValue(entity.getIsUnmodifiable()) + ", ");
            writer.print(toInsertionValue(entity.getLabel()) + ", ");
            writer.print(toInsertionValue(entity.getLocale()) + ", ");
            writer.print(OPT_LOCK + ", ");
            writer.print(toInsertionValue(entity.getInternationalStringFk()));
            writer.println(i == entities.size() - 1 ? "); " : "), ");
        }

        writer.println();
        writer.println("SELECT setval('" + SEQUENCE_NAME + "', (SELECT MAX(id) FROM " + TABLE_NAME + "));");

        writer.close();

        log.info(String.format("Generated script for %s", TABLE_NAME));
    }
}
