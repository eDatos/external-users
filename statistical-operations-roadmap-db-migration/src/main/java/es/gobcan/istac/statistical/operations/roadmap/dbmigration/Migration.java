package es.gobcan.istac.statistical.operations.roadmap.dbmigration;

import static es.gobcan.istac.statistical.operations.roadmap.dbmigration.db.DBUtils.close;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.gobcan.istac.statistical.operations.roadmap.dbmigration.db.DBUtils;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator.EISQLGenerator;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator.ExternalItemSQLGenerator;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator.FamilyOperationSQLGenerator;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator.FamilySQLGenerator;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator.InstanceCostSQLGenerator;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator.InstanceSQLGenerator;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator.InternationalStringSQLGenerator;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator.ListCollMethodSQLGenerator;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator.ListCostSQLGenerator;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator.ListInstanceTypeSQLGenerator;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator.ListOfficialityTypeSQLGenerator;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator.ListSurveySourceSQLGenerator;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator.ListSurveyTypeSQLGenerator;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator.LocalisedStringSQLGenerator;
import es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator.OperationSQLGenerator;

public class Migration {

    protected static final Logger log = LoggerFactory.getLogger(Migration.class);

    public static String targetDirectory = "target/";
    public static String datasourceLocation = "/datasource.properties";

    public static void main(String[] args) throws Exception {

        if (args.length == 2) {
            targetDirectory = args[0];
            datasourceLocation = args[1];
        }

        log.info(datasourceLocation);
        
        Connection connection = null;

        try {
            connection = DBUtils.getConnection();

            new InternationalStringSQLGenerator().generate(connection);
            new LocalisedStringSQLGenerator().generate(connection);
            new ExternalItemSQLGenerator().generate(connection);
            new ListCollMethodSQLGenerator().generate(connection);
            new ListCostSQLGenerator().generate(connection);
            new ListInstanceTypeSQLGenerator().generate(connection);
            new ListOfficialityTypeSQLGenerator().generate(connection);
            new ListSurveyTypeSQLGenerator().generate(connection);
            new ListSurveySourceSQLGenerator().generate(connection);
            new FamilySQLGenerator().generate(connection);
            new OperationSQLGenerator().generate(connection);
            new InstanceSQLGenerator().generate(connection);
            new InstanceCostSQLGenerator().generate(connection);
            new FamilyOperationSQLGenerator().generate(connection);
            new EISQLGenerator().generate(connection);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (connection != null) {
                close(connection);
            }
        }
    }
}
