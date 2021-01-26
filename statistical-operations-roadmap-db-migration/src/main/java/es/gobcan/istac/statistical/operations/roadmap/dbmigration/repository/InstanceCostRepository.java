package es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository;

import static es.gobcan.istac.statistical.operations.roadmap.dbmigration.db.DBUtils.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain.InstanceCostEntity;

public class InstanceCostRepository extends AbstractRepository {

    public List<InstanceCostEntity> findAll(Connection connection) throws SQLException {

        final String query = "SELECT * FROM TB_INSTANCES_COSTS";

        List<InstanceCostEntity> entities = new ArrayList<>();

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

    private InstanceCostEntity toEntity(ResultSet rs) throws SQLException {
        InstanceCostEntity entity = new InstanceCostEntity();
        entity.setCostFk(getLong(rs, InstanceCostEntity.Columns.COST_FK));
        entity.setTbInstances(rs.getLong(InstanceCostEntity.Columns.TB_INSTANCES));
        return entity;
    }
}
