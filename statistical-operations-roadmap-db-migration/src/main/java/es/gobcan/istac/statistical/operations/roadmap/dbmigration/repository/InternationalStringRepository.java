package es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository;

import static es.gobcan.istac.statistical.operations.roadmap.dbmigration.db.DBUtils.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain.InternationalStringEntity;

public class InternationalStringRepository extends AbstractRepository {

    public List<InternationalStringEntity> findAll(Connection connection) throws SQLException {

        final String query = "SELECT * FROM TB_INTERNATIONAL_STRINGS";

        List<InternationalStringEntity> entities = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                entities.add(toEntity(rs));
            }
        } finally {
            close(preparedStatement, rs);
        }

        return entities;
    }

    private InternationalStringEntity toEntity(ResultSet rs) throws SQLException {
        InternationalStringEntity entity = new InternationalStringEntity();
        entity.setId(getLong(rs, InternationalStringEntity.Columns.ID));
        entity.setVersion(getLong(rs, InternationalStringEntity.Columns.VERSION));
        return entity;
    }
}
