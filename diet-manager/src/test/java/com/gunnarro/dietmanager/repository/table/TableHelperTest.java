package com.gunnarro.dietmanager.repository.table;

import org.junit.Assert;
import org.junit.Test;

import com.gunnarro.dietmanager.service.exception.ApplicationException;

public class TableHelperTest {
	@Test
	public void createUpdateParam() {
		Assert.assertEquals("UPDATE tablename SET col1 = ?,col2 = ? WHERE id = ?",
				TableHelper.createUpdateQuery("tablename", new String[] { "col1", "col2" }));
	}

	@Test
	public void createInsertQuery() {
		Assert.assertEquals("INSERT INTO tablename(col1,col2) VALUES (?,?)",
				TableHelper.createInsertQuery("tablename", new String[] { "col1", "col2" }));
	}

	@Test
	public void createInsertPreparedStatement() {
		Assert.assertNotNull(
				TableHelper.createInsertPreparedStatement("select * from users", new String[] { "val1", "val2" }));
	}

	@Test(expected = ApplicationException.class)
	public void checkInputsFails() {
		TableHelper.checkInputs(new String[] { "col1", "col2" }, new String[] { "val1", "val2", "val3" });
	}

	@Test
	public void checkInputsOK() {
		TableHelper.checkInputs(new String[] { "col1", "col2" }, new String[] { "val1", "val2" });
	}
	
}
