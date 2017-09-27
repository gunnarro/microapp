package com.gunnarro.sportsteam.repository.table.link;

import com.gunnarro.sportsteam.repository.table.TableHelper;

public class SubTaskTaskGroupLnkTable {

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, TABLE_FK_COLUMNS);
    }
    public static String[] getFKColumnNames() {
        return TABLE_FK_COLUMNS.clone();
    }
    // Database table
    public static final String TABLE_NAME = "sub_task_task_group_lnk";
    private static final String COLUMN_FK_SUB_TASK_ID = "fk_sub_task_id";

    private static final String COLUMN_FK_TASK_GROUP_ID = "fk_task_group_id";
    
    private static final String[] TABLE_FK_COLUMNS = { COLUMN_FK_SUB_TASK_ID, COLUMN_FK_TASK_GROUP_ID };

    private SubTaskTaskGroupLnkTable() {
    }

}
