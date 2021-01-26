package es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain.FamilyEntity;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository.FamilyRepository;

public class FamilySQLGenerator extends AbstractSQLGenerator {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String TABLE_NAME = "tb_families";
    private static final String SEQUENCE_NAME = "seq_tb_families";

    private FamilyRepository repository = new FamilyRepository();

    public void generate(Connection connection) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        createFile(repository.findAll(connection));
    }

    private void createFile(List<FamilyEntity> entities) throws FileNotFoundException, UnsupportedEncodingException {
        if (entities.isEmpty()) {
            return;
        }

        PrintWriter writer = createPrintWriter(TABLE_NAME);

        writer.println("INSERT INTO " + TABLE_NAME
                + "(id, code, urn, acronym_fk, description_fk, internal_inventory_date, inventory_date, proc_status, title_fk, update_date, uuid, created_by, created_date, "
                + "last_modified_by, last_modified_date, opt_lock) VALUES ");

        for (int i = 0; i < entities.size(); i++) {

            FamilyEntity entity = entities.get(i);

            writer.print("  (");
            writer.print(entity.getId() + ", ");
            writer.print(toInsertionValue(entity.getCode()) + ", ");
            writer.print(toInsertionValue(entity.getUrn()) + ", ");
            writer.print(toInsertionValue(entity.getAcronymFk()) + ", ");
            writer.print(toInsertionValue(entity.getDescriptionFk()) + ", ");
            writer.print(toInsertionValue(entity.getInternalInventoryDate()) + ", ");
            writer.print(toInsertionValue(entity.getInventoryDate()) + ", ");
            writer.print(toProcStatusInsertionValue(entity.getProcStatus()) + ", ");
            writer.print(toInsertionValue(entity.getTitleFk()) + ", ");
            writer.print(toInsertionValue(entity.getUpdateDate()) + ", ");
            writer.print(toInsertionValue(entity.getUuid()) + ", ");
            writer.print(toInsertionValue(entity.getCreatedBy()) + ", ");
            writer.print(toInsertionValue(entity.getCreatedDate()) + ", ");
            writer.print(toInsertionValue(entity.getLastUpdatedBy()) + ", ");
            writer.print(toInsertionValue(entity.getLastUpdated()) + ", ");
            writer.print(OPT_LOCK);
            writer.println(i == entities.size() - 1 ? "); " : "), ");
        }

        writer.println();
        writer.println("SELECT setval('" + SEQUENCE_NAME + "', (SELECT MAX(id) FROM " + TABLE_NAME + "));");

        writer.close();

        log.info(String.format("Generated script for %s", TABLE_NAME));
    }
}
