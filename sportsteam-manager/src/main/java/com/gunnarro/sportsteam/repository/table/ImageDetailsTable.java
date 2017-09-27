package com.gunnarro.sportsteam.repository.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.sportsteam.domain.ImageDetail;
import com.gunnarro.sportsteam.domain.party.Person;

public class ImageDetailsTable {

    // Database table
    public static final String TABLE_NAME = "image_details";
    public static final String COLUMN_IMAGE_DATE_TIME = "image_date_time";
    public static final String COLUMN_IMAGE_NAME = "image_name";
    public static final String COLUMN_IMAGE_PATH = "image_path";
    public static final String COLUMN_IMAGE_MAPPED_ABSOLUTE_PATH = "image_mapped_absolute_path";
    public static final String COLUMN_IMAGE_SIZE_BYTE = "image_size_byte";
    public static final String COLUMN_IMAGE_GEO_LOCATION = "image_geo_location";
    public static final String COLUMN_IMAGE_DESCRIPTION = "image_description";
    public static final String COLUMN_IMAGE_TYPE = "image_type";

    private static String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_IMAGE_DATE_TIME, COLUMN_IMAGE_NAME, COLUMN_IMAGE_PATH, COLUMN_IMAGE_MAPPED_ABSOLUTE_PATH,
            COLUMN_IMAGE_SIZE_BYTE, COLUMN_IMAGE_GEO_LOCATION, COLUMN_IMAGE_DESCRIPTION, COLUMN_IMAGE_TYPE };

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
                ps.setObject(3, imageDetail.getName());
                ps.setString(4, imageDetail.getFilePath());
                ps.setString(5, imageDetail.getMappedAbsoluteFilePath());
                ps.setLong(6, imageDetail.getSize());
                ps.setString(7, imageDetail.getGeoLocation());
                ps.setString(8, imageDetail.getDescription());
                ps.setString(9, imageDetail.getType());
                return ps;
            }
        };
    }

}
