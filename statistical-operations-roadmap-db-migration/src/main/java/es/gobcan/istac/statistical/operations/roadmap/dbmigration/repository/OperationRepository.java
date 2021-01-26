package es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository;

import static es.gobcan.istac.statistical.operations.roadmap.dbmigration.db.DBUtils.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain.OperationEntity;

public class OperationRepository extends AbstractRepository {

    public List<OperationEntity> findAll(Connection connection) throws SQLException {

        final StringBuilder query = new StringBuilder();
        query.append("SELECT ");
        query.append(OperationEntity.Columns.ID + ", ");
        query.append(OperationEntity.Columns.CODE + ", ");
        query.append(OperationEntity.Columns.URN + ", ");
        query.append(OperationEntity.Columns.INDICATOR_SYSTEM + ", ");
        query.append(generateUTCDateQuery(OperationEntity.Columns.INTERNAL_INVENTORY_DATE) + ", ");
        query.append(OperationEntity.Columns.CURRENTLY_ACTIVE + ", ");
        query.append(OperationEntity.Columns.RELEASE_CALENDAR + ", ");
        query.append(OperationEntity.Columns.RELEASE_CALENDAR_ACCESS + ", ");
        query.append(generateUTCDateQuery(OperationEntity.Columns.INVENTORY_DATE) + ", ");
        query.append(generateUTCDateQuery(OperationEntity.Columns.UPDATE_DATE) + ", ");
        query.append(OperationEntity.Columns.UUID + ", ");
        query.append(generateUTCDateQuery(OperationEntity.Columns.CREATED_DATE) + ", ");
        query.append(OperationEntity.Columns.CREATED_BY + ", ");
        query.append(generateUTCDateQuery(OperationEntity.Columns.LAST_UPDATED) + ", ");
        query.append(OperationEntity.Columns.LAST_UPDATED_BY + ", ");
        query.append(OperationEntity.Columns.VERSION + ", ");
        query.append(OperationEntity.Columns.COMMON_METADATA_FK + ", ");
        query.append(OperationEntity.Columns.TITLE_FK + ", ");
        query.append(OperationEntity.Columns.ACRONYM_FK + ", ");
        query.append(OperationEntity.Columns.SUBJECT_AREA_FK + ", ");
        query.append(OperationEntity.Columns.OBJECTIVE_FK + ", ");
        query.append(OperationEntity.Columns.DESCRIPTION_FK + ", ");
        query.append(OperationEntity.Columns.SURVEY_TYPE_FK + ", ");
        query.append(OperationEntity.Columns.OFFICIALITY_TYPE_FK + ", ");
        query.append(OperationEntity.Columns.REL_POL_US_AC_FK + ", ");
        query.append(OperationEntity.Columns.REV_POLICY_FK + ", ");
        query.append(OperationEntity.Columns.REV_PRACTICE_FK + ", ");
        query.append(OperationEntity.Columns.SPECIFIC_LEGAL_ACTS_FK + ", ");
        query.append(OperationEntity.Columns.SPECIFIC_DATA_SHARING_FK + ", ");
        query.append(OperationEntity.Columns.COMMENT_FK + ", ");
        query.append(OperationEntity.Columns.NOTES_FK + ", ");
        query.append(OperationEntity.Columns.PROC_STATUS + ", ");
        query.append(OperationEntity.Columns.STATUS + " ");
        query.append("FROM TB_OPERATIONS");

        List<OperationEntity> entities = new ArrayList<>();

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

    private OperationEntity toEntity(ResultSet rs) throws SQLException {
        OperationEntity entity = new OperationEntity();
        entity.setId(getLong(rs, OperationEntity.Columns.ID));
        entity.setCode(rs.getString(OperationEntity.Columns.CODE));
        entity.setUrn(rs.getString(OperationEntity.Columns.URN));
        entity.setIndicatorSystem(getBoolean(rs, OperationEntity.Columns.INDICATOR_SYSTEM));
        entity.setInternalInventoryDate(getTimestampInUtc(rs, OperationEntity.Columns.INTERNAL_INVENTORY_DATE));
        entity.setCurrentlyActive(getBoolean(rs, OperationEntity.Columns.CURRENTLY_ACTIVE));
        entity.setReleaseCalendar(getBoolean(rs, OperationEntity.Columns.RELEASE_CALENDAR));
        entity.setReleaseCalendarAccess(rs.getString(OperationEntity.Columns.RELEASE_CALENDAR_ACCESS));
        entity.setInventoryDate(getTimestampInUtc(rs, OperationEntity.Columns.INVENTORY_DATE));
        entity.setUpdateDate(getTimestampInUtc(rs, OperationEntity.Columns.UPDATE_DATE));
        entity.setUuid(rs.getString(OperationEntity.Columns.UUID));
        entity.setCreatedDate(getTimestampInUtc(rs, OperationEntity.Columns.CREATED_DATE));
        entity.setCreatedBy(rs.getString(OperationEntity.Columns.CREATED_BY));
        entity.setLastUpdated(getTimestampInUtc(rs, OperationEntity.Columns.LAST_UPDATED));
        entity.setLastUpdatedBy(rs.getString(OperationEntity.Columns.LAST_UPDATED_BY));
        entity.setVersion(getLong(rs, OperationEntity.Columns.VERSION));
        entity.setCommonMetadataFk(getLong(rs, OperationEntity.Columns.COMMON_METADATA_FK));
        entity.setTitleFk(getLong(rs, OperationEntity.Columns.TITLE_FK));
        entity.setAcronymFk(getLong(rs, OperationEntity.Columns.ACRONYM_FK));
        entity.setSubjectAreaFk(getLong(rs, OperationEntity.Columns.SUBJECT_AREA_FK));
        entity.setObjectiveFk(getLong(rs, OperationEntity.Columns.OBJECTIVE_FK));
        entity.setDescriptionFk(getLong(rs, OperationEntity.Columns.DESCRIPTION_FK));
        entity.setSurveyTypeFk(getLong(rs, OperationEntity.Columns.SURVEY_TYPE_FK));
        entity.setOfficialityTypeFk(getLong(rs, OperationEntity.Columns.OFFICIALITY_TYPE_FK));
        entity.setRelPolUsAcFk(getLong(rs, OperationEntity.Columns.REL_POL_US_AC_FK));
        entity.setRevPolicyFk(getLong(rs, OperationEntity.Columns.REV_POLICY_FK));
        entity.setRevPracticeFk(getLong(rs, OperationEntity.Columns.REV_PRACTICE_FK));
        entity.setSpecificLegalActsFk(getLong(rs, OperationEntity.Columns.SPECIFIC_LEGAL_ACTS_FK));
        entity.setSpecificDataSharingFk(getLong(rs, OperationEntity.Columns.SPECIFIC_DATA_SHARING_FK));
        entity.setCommentFk(getLong(rs, OperationEntity.Columns.COMMENT_FK));
        entity.setNotesFk(getLong(rs, OperationEntity.Columns.NOTES_FK));
        entity.setProcStatus(rs.getString(OperationEntity.Columns.PROC_STATUS));
        entity.setStatus(rs.getString(OperationEntity.Columns.STATUS));
        return entity;
    }
}
