package es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository;

import static es.gobcan.istac.statistical.operations.roadmap.dbmigration.db.DBUtils.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain.FamilyEntity;

public class FamilyRepository extends AbstractRepository {

    public List<FamilyEntity> findAll(Connection connection) throws SQLException {

        final StringBuilder query = new StringBuilder();
        query.append("SELECT ");
        query.append(FamilyEntity.Columns.ID + ", ");
        query.append(FamilyEntity.Columns.CODE + ", ");
        query.append(FamilyEntity.Columns.URN + ", ");
        query.append(generateUTCDateQuery(FamilyEntity.Columns.INTERNAL_INVENTORY_DATE) + ", ");
        query.append(generateUTCDateQuery(FamilyEntity.Columns.INVENTORY_DATE) + ", ");
        query.append(generateUTCDateQuery(FamilyEntity.Columns.UPDATE_DATE) + ", ");
        query.append(FamilyEntity.Columns.UUID + ", ");
        query.append(generateUTCDateQuery(FamilyEntity.Columns.CREATED_DATE) + ", ");
        query.append(FamilyEntity.Columns.CREATED_BY + ", ");
        query.append(generateUTCDateQuery(FamilyEntity.Columns.LAST_UPDATED) + ", ");
        query.append(FamilyEntity.Columns.LAST_UPDATED_BY + ", ");
        query.append(FamilyEntity.Columns.VERSION + ", ");
        query.append(FamilyEntity.Columns.TITLE_FK + ", ");
        query.append(FamilyEntity.Columns.ACRONYM_FK + ", ");
        query.append(FamilyEntity.Columns.DESCRIPTION_FK + ", ");
        query.append(FamilyEntity.Columns.PROC_STATUS + " ");
        query.append("FROM TB_FAMILIES");

        List<FamilyEntity> entities = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            preparedStatement = connection.prepareStatement(query.toString());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                entities.add(toEntity(rs));
            }
        } finally {
            close(preparedStatement, rs);
        }

        return entities;
    }

    private FamilyEntity toEntity(ResultSet rs) throws SQLException {
        FamilyEntity entity = new FamilyEntity();
        entity.setId(getLong(rs, FamilyEntity.Columns.ID));
        entity.setCode(rs.getString(FamilyEntity.Columns.CODE));
        entity.setAcronymFk(getLong(rs, FamilyEntity.Columns.ACRONYM_FK));
        entity.setCreatedBy(rs.getString(FamilyEntity.Columns.CREATED_BY));
        entity.setCreatedDate(getTimestampInUtc(rs, FamilyEntity.Columns.CREATED_DATE));
        entity.setDescriptionFk(getLong(rs, FamilyEntity.Columns.DESCRIPTION_FK));
        entity.setInternalInventoryDate(getTimestampInUtc(rs, FamilyEntity.Columns.INTERNAL_INVENTORY_DATE));
        entity.setInventoryDate(getTimestampInUtc(rs, FamilyEntity.Columns.INVENTORY_DATE));
        entity.setLastUpdated(getTimestampInUtc(rs, FamilyEntity.Columns.LAST_UPDATED));
        entity.setLastUpdatedBy(rs.getString(FamilyEntity.Columns.LAST_UPDATED_BY));
        entity.setProcStatus(rs.getString(FamilyEntity.Columns.PROC_STATUS));
        entity.setTitleFk(getLong(rs, FamilyEntity.Columns.TITLE_FK));
        entity.setUpdateDate(getTimestampInUtc(rs, FamilyEntity.Columns.UPDATE_DATE));
        entity.setUrn(rs.getString(FamilyEntity.Columns.URN));
        entity.setUuid(rs.getString(FamilyEntity.Columns.UUID));
        entity.setVersion(getLong(rs, FamilyEntity.Columns.VERSION));
        return entity;
    }

}
