package com.gunnarro.sportsteam.repository.table.link;

import com.gunnarro.sportsteam.repository.table.TableHelper;

public class TeamCupLnkTable {

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, TABLE_FK_COLUMNS);
    }
    public static String[] getFKColumnNames() {
        return TABLE_FK_COLUMNS.clone();
    }
    // Database table
    public static final String TABLE_NAME = "team_cup_lnk";
    private static final String COLUMN_FK_TEAM_ID = "fk_team_id";

    private static final String COLUMN_FK_CUP_ID = "fk_cup_id";
    
    private static final String[] TABLE_FK_COLUMNS = { COLUMN_FK_TEAM_ID, COLUMN_FK_CUP_ID };

    private TeamCupLnkTable() {
    }
}
