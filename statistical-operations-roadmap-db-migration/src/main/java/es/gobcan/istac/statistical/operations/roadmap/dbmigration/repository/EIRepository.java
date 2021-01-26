package es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository;

import static es.gobcan.istac.statistical.operations.roadmap.dbmigration.db.DBUtils.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain.EIEntity;

public class EIRepository extends AbstractRepository {

    public List<EIEntity> findAll(Connection connection, String tableName, String columnName1, String columnName2) throws SQLException {

        final String query = "SELECT * FROM " + tableName;

        List<EIEntity> entities = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                entities.add(toEntity(rs, columnName1, columnName2));
            }
        } finally {
            close(preparedStatement, rs);
        }

        return entities;
    }

    private EIEntity toEntity(ResultSet rs, String columnName1, String columnName2) throws SQLException {
        EIEntity entity = new EIEntity();
        entity.setColumn1(getLong(rs, columnName1));
        entity.setColumn2(getLong(rs, columnName2));
        return entity;
    }
}
