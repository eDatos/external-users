package es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository;

import static es.gobcan.istac.statistical.operations.roadmap.dbmigration.db.DBUtils.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain.InstanceEntity;

public class InstanceRepository extends AbstractRepository {

    public List<InstanceEntity> findAll(Connection connection) throws SQLException {

        final StringBuilder query = new StringBuilder();
        query.append("SELECT ");
        query.append(InstanceEntity.Columns.ID + ", ");
        query.append(InstanceEntity.Columns.ORDER_IDX + ", ");
        query.append(InstanceEntity.Columns.CODE + ", ");
        query.append(InstanceEntity.Columns.URN + ", ");
        query.append(InstanceEntity.Columns.BASE_PERIOD + ", ");
        query.append(generateUTCDateQuery(InstanceEntity.Columns.INTERNAL_INVENTORY_DATE) + ", ");
        query.append(generateUTCDateQuery(InstanceEntity.Columns.INVENTORY_DATE) + ", ");
        query.append(generateUTCDateQuery(InstanceEntity.Columns.UPDATE_DATE) + ", ");
        query.append(InstanceEntity.Columns.UUID + ", ");
        query.append(generateUTCDateQuery(InstanceEntity.Columns.CREATED_DATE) + ", ");
        query.append(InstanceEntity.Columns.CREATED_BY + ", ");
        query.append(generateUTCDateQuery(InstanceEntity.Columns.LAST_UPDATED) + ", ");
        query.append(InstanceEntity.Columns.LAST_UPDATED_BY + ", ");
        query.append(InstanceEntity.Columns.VERSION + ", ");
        query.append(InstanceEntity.Columns.TITLE_FK + ", ");
        query.append(InstanceEntity.Columns.ACRONYM_FK + ", ");
        query.append(InstanceEntity.Columns.OPERATION_FK + ", ");
        query.append(InstanceEntity.Columns.DATA_DESCRIPTION_FK + ", ");
        query.append(InstanceEntity.Columns.STATISTICAL_POPULATION_FK + ", ");
        query.append(InstanceEntity.Columns.GEOGRAPHIC_COMPARABILITY_FK + ", ");
        query.append(InstanceEntity.Columns.TEMPORAL_COMPARABILITY_FK + ", ");
        query.append(InstanceEntity.Columns.STAT_CONC_DEF_FK + ", ");
        query.append(InstanceEntity.Columns.CLASS_SYSTEM_FK + ", ");
        query.append(InstanceEntity.Columns.INSTANCE_TYPE_FK + ", ");
        query.append(InstanceEntity.Columns.DOC_METHOD_FK + ", ");
        query.append(InstanceEntity.Columns.SURVEY_SOURCE_FK + ", ");
        query.append(InstanceEntity.Columns.COLL_METHOD_FK + ", ");
        query.append(InstanceEntity.Columns.DATA_VALIDATION_FK + ", ");
        query.append(InstanceEntity.Columns.DATA_COMPILATION_FK + ", ");
        query.append(InstanceEntity.Columns.ADJUSTMENT_FK + ", ");
        query.append(InstanceEntity.Columns.COST_BURDEN_FK + ", ");
        query.append(InstanceEntity.Columns.QUALITY_DOC_FK + ", ");
        query.append(InstanceEntity.Columns.QUALITY_ASSURE_FK + ", ");
        query.append(InstanceEntity.Columns.QUALITY_ASSMNT_FK + ", ");
        query.append(InstanceEntity.Columns.USER_NEEDS_FK + ", ");
        query.append(InstanceEntity.Columns.USER_SAT_FK + ", ");
        query.append(InstanceEntity.Columns.COMPLETENESS_FK + ", ");
        query.append(InstanceEntity.Columns.TIMELINESS_FK + ", ");
        query.append(InstanceEntity.Columns.PUNCTUALITY_FK + ", ");
        query.append(InstanceEntity.Columns.ACCURACY_OVERALL_FK + ", ");
        query.append(InstanceEntity.Columns.SAMPLING_ERR_FK + ", ");
        query.append(InstanceEntity.Columns.NONSAMPLING_ERR_FK + ", ");
        query.append(InstanceEntity.Columns.COHER_X_DOMAIN_FK + ", ");
        query.append(InstanceEntity.Columns.COHER_INTERNAL_FK + ", ");
        query.append(InstanceEntity.Columns.COMMENT_FK + ", ");
        query.append(InstanceEntity.Columns.NOTES_FK + ", ");
        query.append(InstanceEntity.Columns.PROC_STATUS + " ");
        query.append("FROM TB_INSTANCES");

        List<InstanceEntity> entities = new ArrayList<>();

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

    private InstanceEntity toEntity(ResultSet rs) throws SQLException {
        InstanceEntity entity = new InstanceEntity();
        entity.setId(getLong(rs, InstanceEntity.Columns.ID));
        entity.setOrderIdx(rs.getInt(InstanceEntity.Columns.ORDER_IDX));
        entity.setCode(rs.getString(InstanceEntity.Columns.CODE));
        entity.setUrn(rs.getString(InstanceEntity.Columns.URN));
        entity.setBasePeriod(rs.getString(InstanceEntity.Columns.BASE_PERIOD));
        entity.setInternalInventoryDate(getTimestampInUtc(rs, InstanceEntity.Columns.INTERNAL_INVENTORY_DATE));
        entity.setInventoryDate(getTimestampInUtc(rs, InstanceEntity.Columns.INVENTORY_DATE));
        entity.setUpdateDate(getTimestampInUtc(rs, InstanceEntity.Columns.UPDATE_DATE));
        entity.setUuid(rs.getString(InstanceEntity.Columns.UUID));
        entity.setCreatedDate(getTimestampInUtc(rs, InstanceEntity.Columns.CREATED_DATE));
        entity.setCreatedBy(rs.getString(InstanceEntity.Columns.CREATED_BY));
        entity.setLastUpdated(getTimestampInUtc(rs, InstanceEntity.Columns.LAST_UPDATED));
        entity.setLastUpdatedBy(rs.getString(InstanceEntity.Columns.LAST_UPDATED_BY));
        entity.setVersion(getLong(rs, InstanceEntity.Columns.VERSION));
        entity.setTitleFk(getLong(rs, InstanceEntity.Columns.TITLE_FK));
        entity.setAcronymFk(getLong(rs, InstanceEntity.Columns.ACRONYM_FK));
        entity.setOperationFk(getLong(rs, InstanceEntity.Columns.OPERATION_FK));
        entity.setDataDescriptionFk(getLong(rs, InstanceEntity.Columns.DATA_DESCRIPTION_FK));
        entity.setStatisticalPopulationFk(getLong(rs, InstanceEntity.Columns.STATISTICAL_POPULATION_FK));
        entity.setGeographicComparabilityFk(getLong(rs, InstanceEntity.Columns.GEOGRAPHIC_COMPARABILITY_FK));
        entity.setTemporalComparabilityFk(getLong(rs, InstanceEntity.Columns.TEMPORAL_COMPARABILITY_FK));
        entity.setStatConcDefFk(getLong(rs, InstanceEntity.Columns.STAT_CONC_DEF_FK));
        entity.setClassSystemFk(getLong(rs, InstanceEntity.Columns.CLASS_SYSTEM_FK));
        entity.setInstanceTypeFk(getLong(rs, InstanceEntity.Columns.INSTANCE_TYPE_FK));
        entity.setDocMethodFk(getLong(rs, InstanceEntity.Columns.DOC_METHOD_FK));
        entity.setSurveySourceFk(getLong(rs, InstanceEntity.Columns.SURVEY_SOURCE_FK));
        entity.setCollMethodFk(getLong(rs, InstanceEntity.Columns.COLL_METHOD_FK));
        entity.setDataValidationFk(getLong(rs, InstanceEntity.Columns.DATA_VALIDATION_FK));
        entity.setDataCompilationFk(getLong(rs, InstanceEntity.Columns.DATA_COMPILATION_FK));
        entity.setAdjustmentFk(getLong(rs, InstanceEntity.Columns.ADJUSTMENT_FK));
        entity.setCostBurdenFk(getLong(rs, InstanceEntity.Columns.COST_BURDEN_FK));
        entity.setQualityDocFk(getLong(rs, InstanceEntity.Columns.QUALITY_DOC_FK));
        entity.setQualityAssureFk(getLong(rs, InstanceEntity.Columns.QUALITY_ASSURE_FK));
        entity.setQualityAssmntFk(getLong(rs, InstanceEntity.Columns.QUALITY_ASSMNT_FK));
        entity.setUserNeedsFk(getLong(rs, InstanceEntity.Columns.USER_NEEDS_FK));
        entity.setUserSatFk(getLong(rs, InstanceEntity.Columns.USER_SAT_FK));
        entity.setCompletenessFk(getLong(rs, InstanceEntity.Columns.COMPLETENESS_FK));
        entity.setTimelinessFk(getLong(rs, InstanceEntity.Columns.TIMELINESS_FK));
        entity.setPunctualityFk(getLong(rs, InstanceEntity.Columns.PUNCTUALITY_FK));
        entity.setAccuracyOverallFk(getLong(rs, InstanceEntity.Columns.ACCURACY_OVERALL_FK));
        entity.setSamplingErrFk(getLong(rs, InstanceEntity.Columns.SAMPLING_ERR_FK));
        entity.setNonsamplingErrFk(getLong(rs, InstanceEntity.Columns.NONSAMPLING_ERR_FK));
        entity.setCoherXDomainFk(getLong(rs, InstanceEntity.Columns.COHER_X_DOMAIN_FK));
        entity.setCoherInternalFk(getLong(rs, InstanceEntity.Columns.COHER_INTERNAL_FK));
        entity.setCommentFk(getLong(rs, InstanceEntity.Columns.COMMENT_FK));
        entity.setNotesFk(getLong(rs, InstanceEntity.Columns.NOTES_FK));
        entity.setProcStatus(rs.getString(InstanceEntity.Columns.PROC_STATUS));
        return entity;
    }
}
