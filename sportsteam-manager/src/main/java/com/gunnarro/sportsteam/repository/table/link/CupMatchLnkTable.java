package com.gunnarro.sportsteam.repository.table.link;

public class CupMatchLnkTable {

    public static String[] getFKColumnNames() {
        return TABLE_FK_COLUMNS.clone();
    }
    // Database table
    public static final String TABLE_NAME = "cup_match_lnk";
    public static final String COLUMN_FK_CUP_ID = "fk_cup_id";
    public static final String COLUMN_FK_MATCH_ID = "fk_match_id";
    
    private static final String[] TABLE_FK_COLUMNS = { COLUMN_FK_CUP_ID, COLUMN_FK_MATCH_ID };
    
    private CupMatchLnkTable() {
    }

}
