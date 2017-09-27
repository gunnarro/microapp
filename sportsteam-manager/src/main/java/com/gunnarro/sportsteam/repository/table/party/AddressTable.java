package com.gunnarro.sportsteam.repository.table.party;

import java.sql.Timestamp;

import com.gunnarro.sportsteam.domain.party.Address;
import com.gunnarro.sportsteam.repository.table.TableHelper;

public class AddressTable {

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }

    public static Object[] createUpdateParam(Address address) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), address.getStreetName(), address.getStreetNumber(), address.getStreetNumberPostfix(),
                address.getPostCode(), address.getCity(), address.getCountry(), address.getId() };
    }
    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, UPDATE_TABLE_COLUMNS);
    }
    // Database table
    public static final String TABLE_NAME = "addresses";
    public static final String COLUMN_STREET_NAME = "street_name";
    public static final String COLUMN_STREET_NUMBER = "street_number";
    public static final String COLUMN_STREET_NUMBER_POSTFIX = "street_number_postfix";
    public static final String COLUMN_ZIP_CODE = "zip_code";
    public static final String COLUMN_CITY = "city";

    public static final String COLUMN_POST_CODE = "post_code";

    public static final String COLUMN_POST_BOX = "post_box";

    public static final String COLUMN_COUNTRY = "country";

    private static String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_STREET_NAME, COLUMN_STREET_NUMBER,
            COLUMN_STREET_NUMBER_POSTFIX, COLUMN_ZIP_CODE, COLUMN_CITY, COLUMN_COUNTRY };

    private static String[] UPDATE_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_STREET_NAME, COLUMN_STREET_NUMBER,
            COLUMN_STREET_NUMBER_POSTFIX, COLUMN_ZIP_CODE, COLUMN_CITY, COLUMN_COUNTRY };

    private AddressTable() {
    }

}
