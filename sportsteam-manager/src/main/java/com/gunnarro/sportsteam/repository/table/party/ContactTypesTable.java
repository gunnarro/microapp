package com.gunnarro.sportsteam.repository.table.party;

import com.gunnarro.sportsteam.repository.table.TableHelper;

public class ContactTypesTable {

    // Database table
    public static final String TABLE_NAME = "contact_types";
    public static final String COLUMN_CONTACT_TYPE_ID = "contact_type_id";
    public static final String COLUMN_CONTACT_TYPE_NAME = "contact_type_name";

    private static String[] TABLE_COLUMNS = TableHelper.createColumns(new String[] { COLUMN_CONTACT_TYPE_ID, COLUMN_CONTACT_TYPE_NAME });

    private ContactTypesTable() {
    }
}
