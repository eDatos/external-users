package es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository;

import static es.gobcan.istac.statistical.operations.roadmap.dbmigration.db.DBUtils.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain.ExternalItemEntity;

public class ExternalItemRepository extends AbstractRepository {

    public List<ExternalItemEntity> findAll(Connection connection) throws SQLException {

        final String query = "SELECT * FROM TB_EXTERNAL_ITEMS";

        List<ExternalItemEntity> entities = new ArrayList<>();

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

    private ExternalItemEntity toEntity(ResultSet rs) throws SQLException {
        ExternalItemEntity entity = new ExternalItemEntity();
        entity.setId(getLong(rs, ExternalItemEntity.Columns.ID));
        entity.setCode(rs.getString(ExternalItemEntity.Columns.CODE));
        entity.setCodeNested(rs.getString(ExternalItemEntity.Columns.CODE_NESTED));
        entity.setManagementAppUrl(rs.getString(ExternalItemEntity.Columns.MANAGEMENT_APP_URL));
        entity.setTitleFk(getLong(rs, ExternalItemEntity.Columns.TITLE_FK));
        entity.setType(rs.getString(ExternalItemEntity.Columns.TYPE));
        entity.setUri(rs.getString(ExternalItemEntity.Columns.URI));
        entity.setUrn(rs.getString(ExternalItemEntity.Columns.URN));
        entity.setUrnProvider(rs.getString(ExternalItemEntity.Columns.URN_PROVIDER));
        entity.setVersion(getLong(rs, ExternalItemEntity.Columns.VERSION));
        return entity;
    }
}
