package es.gobcan.istac.statistical.operations.roadmap.dbmigration.db;

import static es.gobcan.istac.statistical.operations.roadmap.dbmigration.Migration.datasourceLocation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public final class DBUtils {

    private DBUtils() {
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
        return getConnection(getDatasource());
    }

    public static Connection getConnection(DataSourceDefinition datasource) throws SQLException, ClassNotFoundException {
        Class.forName(datasource.getDriverClassName());
        return DriverManager.getConnection(datasource.getUrl(), datasource.getUser(), datasource.getPassword());
    }

    public static void close(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public static void close(PreparedStatement preparedStatement, ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (preparedStatement != null) {
            preparedStatement.close();
        }
    }

    private static DataSourceDefinition getDatasource() throws IOException {
        DataSourceDefinition dataSourceDefinition = new DataSourceDefinition();
        Properties prop = new Properties();
        InputStream input = null;
        try {
            File initialFile = new File(datasourceLocation);
            input = FileUtils.openInputStream(initialFile);
            prop.load(input);
            dataSourceDefinition.setDriverClassName(prop.getProperty(DBProperties.DRIVER));
            dataSourceDefinition.setUrl(prop.getProperty(DBProperties.URL));
            dataSourceDefinition.setUser(prop.getProperty(DBProperties.USER));
            dataSourceDefinition.setPassword(prop.getProperty(DBProperties.PASSWORD));
        } finally {
            if (input != null) {
                IOUtils.closeQuietly(input);
            }
        }
        return dataSourceDefinition;
    }
}
