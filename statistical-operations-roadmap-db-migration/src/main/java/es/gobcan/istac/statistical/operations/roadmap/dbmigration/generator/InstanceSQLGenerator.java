package es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain.InstanceEntity;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository.InstanceRepository;

public class InstanceSQLGenerator extends AbstractSQLGenerator {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String TABLE_NAME = "tb_instances";
    private static final String SEQUENCE_NAME = "seq_tb_instances";

    private InstanceRepository repository = new InstanceRepository();

    public void generate(Connection connection) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        createFile(repository.findAll(connection));
    }

    private void createFile(List<InstanceEntity> entities) throws FileNotFoundException, UnsupportedEncodingException {
        if (entities.isEmpty()) {
            return;
        }

        PrintWriter writer = createPrintWriter(TABLE_NAME);

        writer.println("INSERT INTO " + TABLE_NAME + "(id, created_by, created_date, last_modified_by, last_modified_date, opt_lock, base_period, code, internal_inventory_date, "
                + "inventory_date, order_idx, proc_status, update_date, urn, uuid, accuracy_overall_fk, acronym_fk, adjustment_fk, class_system_fk, coher_internal_fk, "
                + "coher_x_domain_fk, coll_method_fk, comment_fk, completeness_fk, cost_burden_fk, data_compilation_fk, data_description_fk, data_validation_fk, doc_method_fk, "
                + "geographic_comparability_fk, instance_type_fk, nonsampling_err_fk, notes_fk, operation_fk, punctuality_fk, quality_assmnt_fk, quality_assure_fk, "
                + "quality_doc_fk, sampling_err_fk, stat_conc_def_fk, statistical_population_fk, survey_source_fk, temporal_comparability_fk, timeliness_fk, title_fk, "
                + "user_needs_fk, user_sat_fk) VALUES ");

        for (int i = 0; i < entities.size(); i++) {

            InstanceEntity entity = entities.get(i);

            writer.print("  (");
            writer.print(entity.getId() + ", ");
            writer.print(toInsertionValue(entity.getCreatedBy()) + ", ");
            writer.print(toInsertionValue(entity.getCreatedDate()) + ", ");
            writer.print(toInsertionValue(entity.getLastUpdatedBy()) + ", ");
            writer.print(toInsertionValue(entity.getLastUpdated()) + ", ");
            writer.print(OPT_LOCK + ", ");
            writer.print(toInsertionValue(entity.getBasePeriod()) + ", ");
            writer.print(toInsertionValue(entity.getCode()) + ", ");
            writer.print(toInsertionValue(entity.getInternalInventoryDate()) + ", ");
            writer.print(toInsertionValue(entity.getInventoryDate()) + ", ");
            writer.print(toInsertionValue(entity.getOrderIdx()) + ", ");
            writer.print(toProcStatusInsertionValue(entity.getProcStatus()) + ", ");
            writer.print(toInsertionValue(entity.getUpdateDate()) + ", ");
            writer.print(toInsertionValue(entity.getUrn()) + ", ");
            writer.print(toInsertionValue(entity.getUuid()) + ", ");
            writer.print(toInsertionValue(entity.getAccuracyOverallFk()) + ", ");
            writer.print(toInsertionValue(entity.getAcronymFk()) + ", ");
            writer.print(toInsertionValue(entity.getAdjustmentFk()) + ", ");
            writer.print(toInsertionValue(entity.getClassSystemFk()) + ", ");
            writer.print(toInsertionValue(entity.getCoherInternalFk()) + ", ");
            writer.print(toInsertionValue(entity.getCoherXDomainFk()) + ", ");
            writer.print(toInsertionValue(entity.getCollMethodFk()) + ", ");
            writer.print(toInsertionValue(entity.getCommentFk()) + ", ");
            writer.print(toInsertionValue(entity.getCompletenessFk()) + ", ");
            writer.print(toInsertionValue(entity.getCostBurdenFk()) + ", ");
            writer.print(toInsertionValue(entity.getDataCompilationFk()) + ", ");
            writer.print(toInsertionValue(entity.getDataDescriptionFk()) + ", ");
            writer.print(toInsertionValue(entity.getDataValidationFk()) + ", ");
            writer.print(toInsertionValue(entity.getDocMethodFk()) + ", ");
            writer.print(toInsertionValue(entity.getGeographicComparabilityFk()) + ", ");
            writer.print(toInsertionValue(entity.getInstanceTypeFk()) + ", ");
            writer.print(toInsertionValue(entity.getNonsamplingErrFk()) + ", ");
            writer.print(toInsertionValue(entity.getNotesFk()) + ", ");
            writer.print(toInsertionValue(entity.getOperationFk()) + ", ");
            writer.print(toInsertionValue(entity.getPunctualityFk()) + ", ");
            writer.print(toInsertionValue(entity.getQualityAssmntFk()) + ", ");
            writer.print(toInsertionValue(entity.getQualityAssureFk()) + ", ");
            writer.print(toInsertionValue(entity.getQualityDocFk()) + ", ");
            writer.print(toInsertionValue(entity.getSamplingErrFk()) + ", ");
            writer.print(toInsertionValue(entity.getStatConcDefFk()) + ", ");
            writer.print(toInsertionValue(entity.getStatisticalPopulationFk()) + ", ");
            writer.print(toInsertionValue(entity.getSurveySourceFk()) + ", ");
            writer.print(toInsertionValue(entity.getTemporalComparabilityFk()) + ", ");
            writer.print(toInsertionValue(entity.getTimelinessFk()) + ", ");
            writer.print(toInsertionValue(entity.getTitleFk()) + ", ");
            writer.print(toInsertionValue(entity.getUserNeedsFk()) + ", ");
            writer.print(toInsertionValue(entity.getUserSatFk()));
            writer.println(i == entities.size() - 1 ? "); " : "), ");
        }

        writer.println();
        writer.println("SELECT setval('" + SEQUENCE_NAME + "', (SELECT MAX(id) FROM " + TABLE_NAME + "));");

        writer.close();

        log.info(String.format("Generated script for %s", TABLE_NAME));
    }
}
