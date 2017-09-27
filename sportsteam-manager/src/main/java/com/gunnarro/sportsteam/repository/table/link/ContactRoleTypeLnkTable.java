package com.gunnarro.sportsteam.repository.table.link;

import com.gunnarro.sportsteam.repository.table.TableHelper;

public class ContactRoleTypeLnkTable {

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, TABLE_FK_COLUMNS);
    }
    public static String[] getFKColumnNames() {
        return TABLE_FK_COLUMNS.clone();
    }
    // Database table
    public static final String TABLE_NAME = "contact_role_type_lnk";
    public static final String COLUMN_FK_TEAM_ROLE_TYPE_ID = "fk_team_role_type_id";
    public static final String COLUMN_FK_CONTACT_ID = "fk_contact_id";

    private static final String[] TABLE_FK_COLUMNS = { COLUMN_FK_CONTACT_ID, COLUMN_FK_TEAM_ROLE_TYPE_ID };

    private ContactRoleTypeLnkTable() {
    }
}
