package com.gunnarro.sportsteam.repository.table.link;

import com.gunnarro.sportsteam.repository.table.TableHelper;

public class PlayerCupLnkTable {

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, TABLE_FK_COLUMNS);
    }
    public static String[] getFKColumnNames() {
        return TABLE_FK_COLUMNS.clone();
    }
    // Database table
    public static final String TABLE_NAME = "player_cup_lnk";
    private static final String COLUMN_FK_PLAYER_ID = "fk_player_id";

    private static final String COLUMN_FK_CUP_ID = "fk_cup_id";
    
    private static final String[] TABLE_FK_COLUMNS = { COLUMN_FK_PLAYER_ID, COLUMN_FK_CUP_ID };
    
    private PlayerCupLnkTable() {
    }
}
