package es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository;

import static es.gobcan.istac.statistical.operations.roadmap.dbmigration.db.DBUtils.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain.ListEntity;

public abstract class ListRepository extends AbstractRepository {

    public List<ListEntity> findAll(Connection connection) throws SQLException {

        final String query = "SELECT * FROM " + getTableName();

        List<ListEntity> entities = new ArrayList<>();

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

    private ListEntity toEntity(ResultSet rs) throws SQLException {
        ListEntity entity = new ListEntity();
        entity.setId(getLong(rs, ListEntity.Columns.ID));
        entity.setDescription(getLong(rs, ListEntity.Columns.DESCRIPTION));
        entity.setIdentifier(rs.getString(ListEntity.Columns.IDENTIFIER));
        entity.setUuid(rs.getString(ListEntity.Columns.UUID));
        entity.setVersion(getLong(rs, ListEntity.Columns.VERSION));
        return entity;
    }

    protected abstract String getTableName();
}
