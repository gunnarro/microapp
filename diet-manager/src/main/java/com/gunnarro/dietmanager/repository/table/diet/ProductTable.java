package com.gunnarro.dietmanager.repository.table.diet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import com.gunnarro.dietmanager.domain.diet.Product;
import com.gunnarro.dietmanager.repository.table.TableHelper;

/**
 * 
 * @author admin
 * 
 */
public class ProductTable {

    // Database table
    public static final String TABLE_NAME = "products";

    private enum ColumnsEnum {
        name, type, category, description, amount, weightGr, kcal, fat, carbohydrates, fibre, protein, imgLink;

        public static String[] updateValues() {
            String[] s = new String[4];
            s[0] = TableHelper.ColumnsDefaultEnum.lastModifiedDateTime.name();
            s[1] = category.name();
            s[2] = description.name();
            s[3] = imgLink.name();
            return s;
        }
    }

    /**
     * In order to hide public constructor
     */
    private ProductTable() {
    }

    public static PreparedStatementCreator createInsertPreparedStatement(final Product product) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setTimestamp(2, new Timestamp(TableHelper.getToDay()));
                ps.setObject(3, product.getName());
                ps.setObject(4, product.getType());
                ps.setObject(5, product.getCategory());
                ps.setObject(6, product.getDescription());
                ps.setObject(7, product.getAmount());
                ps.setObject(8, product.getWeight());
                ps.setObject(9, product.getKcal());
                ps.setObject(10, product.getFat());
                ps.setObject(11, product.getCarbohydrates());
                ps.setObject(12, product.getFibre());
                ps.setObject(13, product.getProtein());
                ps.setObject(14, product.getImageLink());
                return ps;
            }
        };
    }

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, TableHelper.getColumnNames(ColumnsEnum.values()));
    }

    public static Object[] createUpdateParam(Product product) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), product.getCategory(), product.getDescription(), product.getImageLink(), product.getId() };
    }

    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, ColumnsEnum.updateValues());
    }

    public static RowMapper<Product> mapToProductRM() {
        return new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Product product = new Product();
                product.setId(resultSet.getInt(TableHelper.ColumnsDefaultEnum.id.name()));
                product.setCreatedDate(new Date(resultSet.getTimestamp(TableHelper.ColumnsDefaultEnum.createdDateTime.name()).getTime()));
                product.setLastModifiedTime(resultSet.getTimestamp(TableHelper.ColumnsDefaultEnum.lastModifiedDateTime.name()).getTime());
                product.setName(resultSet.getString(ColumnsEnum.name.name()));
                product.setCategory(resultSet.getString(ColumnsEnum.category.name()));
                product.setDescription(resultSet.getString(ColumnsEnum.description.name()));
                product.setImageLink(resultSet.getString(ColumnsEnum.imgLink.name()));
                return product;
            }
        };
    }
}
