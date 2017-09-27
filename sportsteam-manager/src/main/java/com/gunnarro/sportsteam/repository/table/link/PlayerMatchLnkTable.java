package com.gunnarro.sportsteam.repository.table.link;

import com.gunnarro.sportsteam.repository.table.TableHelper;

public class PlayerMatchLnkTable {

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, TABLE_FK_COLUMNS);
    }
    public static String[] getFKColumnNames() {
        return TABLE_FK_COLUMNS.clone();
    }
    // Database table
    public static final String TABLE_NAME = "player_match_lnk";
    private static final String COLUMN_FK_PLAYER_ID = "fk_player_id";

    private static final String COLUMN_FK_MATCH_ID = "fk_match_id";
    
    private static final String[] TABLE_FK_COLUMNS = { COLUMN_FK_PLAYER_ID, COLUMN_FK_MATCH_ID };

    private PlayerMatchLnkTable() {
    }
}
