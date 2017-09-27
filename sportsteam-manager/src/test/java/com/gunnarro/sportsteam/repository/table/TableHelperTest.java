package com.gunnarro.sportsteam.repository.table;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gunnarro.sportsteam.service.exception.ApplicationException;

public class TableHelperTest {

    @Test
    public void createInsertQuery() {
        String createInsertQuery = TableHelper.createInsertQuery("test_table", new String[] { "col_name_1", "col_name_2" });
        assertNotNull(createInsertQuery);
        assertEquals("INSERT INTO test_table(col_name_1,col_name_2) VALUES (?,?)", createInsertQuery);
    }

    @Test
    public void createUpdateQuery() {
        String createUpdateQuery = TableHelper.createUpdateQuery("test_table", new String[] { "col_name_1", "col_name_2" });
        assertNotNull(createUpdateQuery);
        assertEquals("UPDATE test_table SET col_name_1 = ?,col_name_2 = ? WHERE id = ?", createUpdateQuery);
    }

    @Test
    public void createInsertPreparedStatement() {
        assertNotNull(TableHelper.createInsertPreparedStatement("SELECT * FROM table WHERE col1=? AND col2=?", new Object[] { "value1", "value2" }));
    }

    @Test
    public void checkInputsValid() {
        TableHelper.checkInputs(new Object[] { "col1", "col2" }, new Object[] { "value1", "value2" });
    }

    @Test(expected = ApplicationException.class)
    public void checkInputsInvalidArrayLenght() {
        TableHelper.checkInputs(new Object[] { "col1", "col2" }, new Object[] { "value1" });
    }

    @Test(expected = ApplicationException.class)
    public void checkInputsInvalidArrayWithNulls() {
        TableHelper.checkInputs(new Object[] { "col1", "col2" }, new Object[] { "value1", null });
    }

    @Test(expected = ApplicationException.class)
    public void checkInputsInvalidInputNull() {
        TableHelper.checkInputs(null, new Object[] { "value1", "value2" });
    }
}
