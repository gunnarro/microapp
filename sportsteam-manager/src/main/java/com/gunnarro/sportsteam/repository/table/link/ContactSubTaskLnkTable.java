package com.gunnarro.sportsteam.repository.table.link;

import com.gunnarro.sportsteam.repository.table.TableHelper;

public class ContactSubTaskLnkTable {

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, TABLE_FK_COLUMNS);
    }
    public static String[] getFKColumnNames() {
        return TABLE_FK_COLUMNS.clone();
    }
    // Database table
    public static final String TABLE_NAME = "contact_sub_task_lnk";
    private static final String COLUMN_FK_CONTACT_ID = "fk_contact_id";

    private static final String COLUMN_FK_SUB_TASK_ID = "fk_sub_task_id";

    private static final String[] TABLE_FK_COLUMNS = { COLUMN_FK_CONTACT_ID, COLUMN_FK_SUB_TASK_ID };
    
    private ContactSubTaskLnkTable() {
    }

}
