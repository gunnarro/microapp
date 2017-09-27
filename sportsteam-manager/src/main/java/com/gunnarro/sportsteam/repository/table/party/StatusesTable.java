package com.gunnarro.sportsteam.repository.table.party;

import com.gunnarro.sportsteam.repository.table.TableHelper;

public class StatusesTable {

    // Database table
    public static final String TABLE_NAME = "statuses";
    public static final String COLUMN_STATUS_ID = "status_id";
    public static final String COLUMN_STATUS_NAME = "status_name";

    private static String[] TABLE_COLUMNS = TableHelper.createColumns(new String[] { COLUMN_STATUS_ID, COLUMN_STATUS_NAME });

    private StatusesTable() {
    }
}
