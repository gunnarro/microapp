package com.gunnarro.sportsteam.repository.table.party;

import com.gunnarro.sportsteam.repository.table.TableHelper;

public class BoardMembersTable {

    // Database table
    public static final String TABLE_NAME = "board_mambers";
    public static final String COLUMN_FK_ADDRESS_ID = "fk_address_id";
    public static final String COLUMN_FK_CLUB_ID = "fk_club_id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_MIDDLE_NAME = "middle_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_MOBILE = "mobile";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_ROLE_TYPE = "role_type";

    private static String[] TABLE_COLUMNS = TableHelper.createColumns(new String[] { COLUMN_FK_ADDRESS_ID, COLUMN_FK_CLUB_ID, COLUMN_FIRST_NAME,
            COLUMN_MIDDLE_NAME, COLUMN_LAST_NAME, COLUMN_MOBILE, COLUMN_EMAIL });

    private BoardMembersTable() {
    }
}
