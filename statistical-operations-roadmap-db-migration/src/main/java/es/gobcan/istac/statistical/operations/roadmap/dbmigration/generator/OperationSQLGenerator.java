package es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain.OperationEntity;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository.OperationRepository;

public class OperationSQLGenerator extends AbstractSQLGenerator {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String TABLE_NAME = "tb_operations";
    private static final String SEQUENCE_NAME = "seq_tb_operations";

    private OperationRepository repository = new OperationRepository();

    public void generate(Connection connection) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        createFile(repository.findAll(connection));
    }

    private void createFile(List<OperationEntity> entities) throws FileNotFoundException, UnsupportedEncodingException {
        if (entities.isEmpty()) {
            return;
        }

        PrintWriter writer = createPrintWriter(TABLE_NAME);

        writer.println(
                "INSERT INTO " + TABLE_NAME + "(id, code, urn, currently_active, indicator_system, internal_inventory_date, inventory_date, proc_status, release_calendar, release_calendar_access, "
                        + "status, update_date, acronym_fk, comment_fk, common_metadata_fk, description_fk, notes_fk, objective_fk, officiality_type_fk, rel_pol_us_ac_fk, rev_policy_fk, "
                        + "rev_practice_fk, specific_data_sharing_fk, specific_legal_acts_fk, subject_area_fk, survey_type_fk, title_fk, uuid, created_by, created_date, last_modified_by, "
                        + "last_modified_date, opt_lock) VALUES ");

        for (int i = 0; i < entities.size(); i++) {

            OperationEntity entity = entities.get(i);

            writer.print("  (");
            writer.print(entity.getId() + ", ");
            writer.print(toInsertionValue(entity.getCode()) + ", ");
            writer.print(toInsertionValue(entity.getUrn()) + ", ");
            writer.print(toInsertionValue(entity.getCurrentlyActive()) + ", ");
            writer.print(toInsertionValue(entity.getIndicatorSystem()) + ", ");
            writer.print(toInsertionValue(entity.getInternalInventoryDate()) + ", ");
            writer.print(toInsertionValue(entity.getInventoryDate()) + ", ");
            writer.print(toProcStatusInsertionValue(entity.getProcStatus()) + ", ");
            writer.print(toInsertionValue(entity.getReleaseCalendar()) + ", ");
            writer.print(toInsertionValue(entity.getReleaseCalendarAccess()) + ", ");
            writer.print(toInsertionValue(entity.getStatus()) + ", ");
            writer.print(toInsertionValue(entity.getUpdateDate()) + ", ");
            writer.print(toInsertionValue(entity.getAcronymFk()) + ", ");
            writer.print(toInsertionValue(entity.getCommentFk()) + ", ");
            writer.print(toInsertionValue(entity.getCommonMetadataFk()) + ", ");
            writer.print(toInsertionValue(entity.getDescriptionFk()) + ", ");
            writer.print(toInsertionValue(entity.getNotesFk()) + ", ");
            writer.print(toInsertionValue(entity.getObjectiveFk()) + ", ");
            writer.print(toInsertionValue(entity.getOfficialityTypeFk()) + ", ");
            writer.print(toInsertionValue(entity.getRelPolUsAcFk()) + ", ");
            writer.print(toInsertionValue(entity.getRevPolicyFk()) + ", ");
            writer.print(toInsertionValue(entity.getRevPracticeFk()) + ", ");
            writer.print(toInsertionValue(entity.getSpecificDataSharingFk()) + ", ");
            writer.print(toInsertionValue(entity.getSpecificLegalActsFk()) + ", ");
            writer.print(toInsertionValue(entity.getSubjectAreaFk()) + ", ");
            writer.print(toInsertionValue(entity.getSurveyTypeFk()) + ", ");
            writer.print(toInsertionValue(entity.getTitleFk()) + ", ");
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
