package es.gobcan.istac.statistical.operations.roadmap.dbmigration.repository;

import static es.gobcan.istac.statistical.operations.roadmap.dbmigration.db.DBUtils.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain.LocalisedStringEntity;

public class LocalisedStringRepository extends AbstractRepository {

    public List<LocalisedStringEntity> findAll(Connection connection) throws SQLException {

        final String query = "SELECT * FROM TB_LOCALISED_STRINGS";

        List<LocalisedStringEntity> entities = new ArrayList<>();

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

    private LocalisedStringEntity toEntity(ResultSet rs) throws SQLException {
        LocalisedStringEntity entity = new LocalisedStringEntity();
        entity.setId(getLong(rs, LocalisedStringEntity.Columns.ID));
        entity.setInternationalStringFk(getLong(rs, LocalisedStringEntity.Columns.INTERNATIONAL_STRING_FK));
        entity.setIsUnmodifiable(getBoolean(rs, LocalisedStringEntity.Columns.IS_UNMODIFIABLE));
        entity.setLabel(rs.getString(LocalisedStringEntity.Columns.LABEL));
        entity.setLocale(rs.getString(LocalisedStringEntity.Columns.LOCALE));
        entity.setVersion(getLong(rs, LocalisedStringEntity.Columns.VERSION));
        return entity;
    }
}
