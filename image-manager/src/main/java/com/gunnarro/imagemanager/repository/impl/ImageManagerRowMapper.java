package com.gunnarro.imagemanager.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.gunnarro.imagemanager.domain.ImageDetail;
import com.gunnarro.imagemanager.domain.party.User;
import com.gunnarro.imagemanager.repository.table.ImageDetailsTable;
import com.gunnarro.imagemanager.repository.table.TableHelper;

/**
 * This class contains RowMapper which is required for converting ResultSet into
 * java domain class
 * 
 */
public class ImageManagerRowMapper {

    private ImageManagerRowMapper() {
    }

    public static RowMapper<User> mapToUserRM() {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                User user = new User();
                user.setId(resultSet.getInt(TableHelper.COLUMN_ID));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setActivated(resultSet.getInt("enabled") == 1 ? true : false);
                return user;
            }
        };
    }
    
    public static RowMapper<String> mapToRoleRM() {
        return new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return resultSet.getString("role");
            }
        };
    }

    public static RowMapper<ImageDetail> mapToImageDetailRM() {
        return new RowMapper<ImageDetail>() {
            @Override
            public ImageDetail mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                ImageDetail imageDetail = new ImageDetail();
                imageDetail.setId(resultSet.getInt(TableHelper.COLUMN_ID));
                imageDetail.setUserId(resultSet.getInt(ImageDetailsTable.COLUMN_FK_USER_ID));
                imageDetail.setName(resultSet.getString(ImageDetailsTable.COLUMN_IMAGE_NAME));
                imageDetail.setTitle(resultSet.getString(ImageDetailsTable.COLUMN_IMAGE_TITLE));
                imageDetail.setFilePath(resultSet.getString(ImageDetailsTable.COLUMN_IMAGE_PATH));
                imageDetail.setMappedAbsoluteFilePath(resultSet.getString(ImageDetailsTable.COLUMN_IMAGE_MAPPED_ABSOLUTE_PATH));
                imageDetail.setSize(resultSet.getLong(ImageDetailsTable.COLUMN_IMAGE_SIZE_BYTE));
                imageDetail.setGeoLocation(resultSet.getString(ImageDetailsTable.COLUMN_IMAGE_GEO_LOCATION));
                imageDetail.setCreatedDate(new Date(resultSet.getTimestamp(ImageDetailsTable.COLUMN_IMAGE_DATE_TIME).getTime()));
                imageDetail.setLastModifiedDate(new Date(resultSet.getTimestamp(TableHelper.COLUMN_LAST_MODIFIED_DATETIME).getTime()));
                imageDetail.setDescription(resultSet.getString(ImageDetailsTable.COLUMN_IMAGE_DESCRIPTION));
                imageDetail.setType(resultSet.getString(ImageDetailsTable.COLUMN_IMAGE_TYPE));
                return imageDetail;
            }
        };
    }

}