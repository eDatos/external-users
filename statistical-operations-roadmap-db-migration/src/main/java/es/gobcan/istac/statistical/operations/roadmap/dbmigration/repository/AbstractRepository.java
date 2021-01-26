package es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public abstract class AbstractRepository {

    protected String generateUTCDateQuery(String columnName) {
        return "SYS_EXTRACT_UTC(FROM_TZ(CAST(" + columnName + " AS TIMESTAMP), " + columnName + "_TZ)) as " + columnName + "_UTC ";
    }

    protected Timestamp getTimestampInUtc(ResultSet rs, String columnName) throws SQLException {
        return rs.getTimestamp(columnName + "_UTC");
    }

    protected Long getLong(ResultSet rs, String columnName) throws SQLException {
        Object value = rs.getObject(columnName);
        if (value == null) {
            return null;
        }
        return rs.getLong(columnName);
    }

    protected Boolean getBoolean(ResultSet rs, String columnName) throws SQLException {
        Object value = rs.getObject(columnName);
        if (value == null) {
            return null;
        }
        return rs.getBoolean(columnName);
    }
}
