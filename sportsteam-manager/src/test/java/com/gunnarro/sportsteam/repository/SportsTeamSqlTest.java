package com.gunnarro.sportsteam.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.gunnarro.sportsteam.repository.impl.SportsTeamSql;

public class SportsTeamSqlTest {
    
    @Test
    public void getDeleteAllDataBatchQuery() {
        assertEquals(11, SportsTeamSql.getDeleteAllDataBatchQuery().length);
        assertEquals("DELETE FROM sub_tasks WHERE id > 0", SportsTeamSql.getDeleteAllDataBatchQuery()[0]);
        assertEquals("DELETE FROM addresses WHERE id > 0", SportsTeamSql.getDeleteAllDataBatchQuery()[10]);
    }

}
