package com.gunnarro.imagemanager.repository.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.imagemanager.domain.ImageDetail;

public class ImageDetailsTable {

    // Database table
    public static final String TABLE_NAME = "image_details";
    public static final String COLUMN_IMAGE_DATE_TIME = "image_date_time";
    public static final String COLUMN_FK_USER_ID = "fk_user_id";
    public static final String COLUMN_IMAGE_NAME = "image_name";
    public static final String COLUMN_IMAGE_TITLE = "image_title";
    public static final String COLUMN_IMAGE_PATH = "image_path";
    public static final String COLUMN_IMAGE_MAPPED_ABSOLUTE_PATH = "image_mapped_absolute_path";
    public static final String COLUMN_IMAGE_SIZE_BYTE = "image_size_byte";
    public static final String COLUMN_IMAGE_GEO_LOCATION = "image_geo_location";
    public static final String COLUMN_IMAGE_DESCRIPTION = "image_description";
    public static final String COLUMN_IMAGE_TYPE = "image_type";

    private static String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_IMAGE_DATE_TIME, COLUMN_FK_USER_ID, COLUMN_IMAGE_NAME,
            COLUMN_IMAGE_TITLE, COLUMN_IMAGE_PATH, COLUMN_IMAGE_MAPPED_ABSOLUTE_PATH, COLUMN_IMAGE_SIZE_BYTE, COLUMN_IMAGE_GEO_LOCATION, COLUMN_IMAGE_DESCRIPTION,
            COLUMN_IMAGE_TYPE };

    private static String[] UPDATE_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_IMAGE_TITLE, COLUMN_IMAGE_GEO_LOCATION,
            COLUMN_IMAGE_DESCRIPTION };

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }

    public static PreparedStatementCreator createInsertPreparedStatement(final ImageDetail imageDetail) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setTimestamp(2, new Timestamp(imageDetail.getCreatedDate().getTime()));
                ps.setInt(3, imageDetail.getUserId() == null ? 3 : imageDetail.getUserId());
                ps.setObject(4, imageDetail.getName());
                ps.setString(5, imageDetail.getTitle());
                ps.setString(6, imageDetail.getFilePath());
                ps.setString(7, imageDetail.getMappedAbsoluteFilePath());
                ps.setLong(8, imageDetail.getSize());
                ps.setString(9, imageDetail.getGeoLocation());
                ps.setString(10, imageDetail.getDescription());
                ps.setString(11, imageDetail.getType());
                return ps;
            }
        };
    }

    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, UPDATE_TABLE_COLUMNS);
    }

    public static Object[] createUpdateParam(ImageDetail imageDetail) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), imageDetail.getTitle(), imageDetail.getGeoLocation(), imageDetail.getDescription(), imageDetail.getId() };
    }

}
