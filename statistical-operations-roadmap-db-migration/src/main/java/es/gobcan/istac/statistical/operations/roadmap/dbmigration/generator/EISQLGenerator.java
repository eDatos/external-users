package es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain.EIEntity;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository.EIRepository;

public class EISQLGenerator extends AbstractSQLGenerator {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    private EIRepository repository = new EIRepository();

    public void generate(Connection connection) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        generateClassSystemList(connection);
        generateConcDefList(connection);
        generateFreqColl(connection);
        generateGeoGranularity(connection);
        generateInfSupplier(connection);
        generateProducer(connection);
        generatePublisher(connection);
        generateRegContributor(connection);
        generateRegResponsible(connection);
        generateSecondaryArea(connection);
        generateStatisticalUnit(connection);
        generateTimeGranularity(connection);
        generateUnitMeasure(connection);
        generateUpdateFrequency(connection);
    }

    private void generateClassSystemList(Connection connection) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        List<EIEntity> entities = repository.findAll(connection, "TB_EI_CLASS_SYSTEM_LISTS", "CLASS_SYSTEM_LIST_FK", "TB_INSTANCES");
        createFile(entities, "tb_ei_class_system_lists", "class_system_list_fk", "tb_instances");
    }

    private void generateConcDefList(Connection connection) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        List<EIEntity> entities = repository.findAll(connection, "TB_EI_CONC_DEF_LISTS", "STAT_CONC_DEF_LIST_FK", "TB_INSTANCES");
        createFile(entities, "tb_ei_conc_def_lists", "stat_conc_def_list_fk", "tb_instances");
    }

    private void generateFreqColl(Connection connection) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        List<EIEntity> entities = repository.findAll(connection, "TB_EI_FREQ_COLL", "FREQ_COLL_FK", "TB_INSTANCES");
        createFile(entities, "tb_ei_freq_coll", "freq_coll_fk", "tb_instances");
    }

    private void generateGeoGranularity(Connection connection) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        List<EIEntity> entities = repository.findAll(connection, "TB_EI_GEO_GRANULARITIES", "GEOGRAPHIC_GRANULARITY_FK", "TB_INSTANCES");
        createFile(entities, "tb_ei_geo_granularities", "geographic_granularity_fk", "tb_instances");
    }

    private void generateInfSupplier(Connection connection) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        List<EIEntity> entities = repository.findAll(connection, "TB_EI_INF_SUPPLIERS", "INFORMATION_SUPPLIERS_FK", "TB_INSTANCES");
        createFile(entities, "tb_ei_inf_suppliers", "information_suppliers_fk", "tb_instances");
    }

    private void generateProducer(Connection connection) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        List<EIEntity> entities = repository.findAll(connection, "TB_EI_PRODUCERS", "PRODUCER_FK", "TB_OPERATIONS");
        createFile(entities, "tb_ei_producers", "producer_fk", "tb_operations");
    }

    private void generatePublisher(Connection connection) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        List<EIEntity> entities = repository.findAll(connection, "TB_EI_PUBLISHERS", "PUBLISHER_FK", "TB_OPERATIONS");
        createFile(entities, "tb_ei_publishers", "publisher_fk", "tb_operations");
    }

    private void generateRegContributor(Connection connection) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        List<EIEntity> entities = repository.findAll(connection, "TB_EI_REG_CONTRIBUTORS", "REGIONAL_CONTRIBUTOR_FK", "TB_OPERATIONS");
        createFile(entities, "tb_ei_reg_contributors", "regional_contributor_fk", "tb_operations");
    }

    private void generateRegResponsible(Connection connection) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        List<EIEntity> entities = repository.findAll(connection, "TB_EI_REG_RESPONSIBLES", "REGIONAL_RESPONSIBLE_FK", "TB_OPERATIONS");
        createFile(entities, "tb_ei_reg_responsibles", "regional_responsible_fk", "tb_operations");
    }

    private void generateSecondaryArea(Connection connection) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        List<EIEntity> entities = repository.findAll(connection, "TB_EI_SECONDARY_AREAS", "SECUN_SUBJECT_AREAS_FK", "TB_OPERATIONS");
        createFile(entities, "tb_ei_secondary_areas", "secun_subject_areas_fk", "tb_operations");
    }

    private void generateStatisticalUnit(Connection connection) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        List<EIEntity> entities = repository.findAll(connection, "TB_EI_STATISTICAL_UNITS", "STATISTICAL_UNIT_FK", "TB_INSTANCES");
        createFile(entities, "tb_ei_statistical_units", "statistical_unit_fk", "tb_instances");
    }

    private void generateTimeGranularity(Connection connection) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        List<EIEntity> entities = repository.findAll(connection, "TB_EI_TIME_GRANULARITIES", "TEMPORAL_GRANULARITY_FK", "TB_INSTANCES");
        createFile(entities, "tb_ei_time_granularities", "temporal_granularity_fk", "tb_instances");
    }

    private void generateUnitMeasure(Connection connection) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        List<EIEntity> entities = repository.findAll(connection, "TB_EI_UNITS_MEASURE", "UNIT_MEASURE_FK", "TB_INSTANCES");
        createFile(entities, "tb_ei_units_measure", "unit_measure_fk", "tb_instances");
    }

    private void generateUpdateFrequency(Connection connection) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        List<EIEntity> entities = repository.findAll(connection, "TB_EI_UPDATE_FREQUENCY", "UPDATE_FREQUENCY_FK", "TB_OPERATIONS");
        createFile(entities, "tb_ei_update_frequency", "update_frequency_fk", "tb_operations");
    }

    private void createFile(List<EIEntity> entities, String targetTableName, String targetColunmName1, String targetColunmName2) throws FileNotFoundException, UnsupportedEncodingException {
        if (entities.isEmpty()) {
            return;
        }

        PrintWriter writer = createPrintWriter(targetTableName);

        writer.println("INSERT INTO " + targetTableName + "(" + targetColunmName1 + ", " + targetColunmName2 + ") VALUES ");

        for (int i = 0; i < entities.size(); i++) {

            EIEntity entity = entities.get(i);

            writer.print("  (");
            writer.print(toInsertionValue(entity.getColumn1()) + ", ");
            writer.print(toInsertionValue(entity.getColumn2()));
            writer.println(i == entities.size() - 1 ? "); " : "), ");
        }

        writer.println();
        writer.close();

        log.info(String.format("Generated script for %s", targetTableName));
    }
}
