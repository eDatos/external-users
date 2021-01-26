package es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository;

import static es.gobcan.istac.statistical.operations.roadmap.dbmigration.db.DBUtils.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain.FamilyOperationEntity;

public class FamilyOperationRepository extends AbstractRepository {

    public List<FamilyOperationEntity> findAll(Connection connection) throws SQLException {

        final String query = "SELECT * FROM TB_FAMILIES_OPERATIONS";

        List<FamilyOperationEntity> entities = new ArrayList<>();

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

    private FamilyOperationEntity toEntity(ResultSet rs) throws SQLException {
        FamilyOperationEntity entity = new FamilyOperationEntity();
        entity.setFamily(getLong(rs, FamilyOperationEntity.Columns.FAMILY));
        entity.setOperation(rs.getLong(FamilyOperationEntity.Columns.OPERATION));
        return entity;
    }
}
