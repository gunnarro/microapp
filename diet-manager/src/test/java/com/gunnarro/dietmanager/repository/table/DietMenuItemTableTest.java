package com.gunnarro.dietmanager.repository.table;

import org.junit.Assert;
import org.junit.Test;

import com.gunnarro.dietmanager.domain.diet.MenuItem;
import com.gunnarro.dietmanager.repository.table.diet.DietMenuItemTable;

public class DietMenuItemTableTest {

	@Test
	public void createInsertPreparedStatement() {
		MenuItem menuItem = new MenuItem();
		Assert.assertNotNull(DietMenuItemTable.createInsertPreparedStatement(menuItem));
	}

	@Test
	public void createUpdateParam() {
		MenuItem menuItem = new MenuItem();
		menuItem.setDescription("description");
		menuItem.setCategory("dinner");
		Assert.assertNotNull(DietMenuItemTable.createUpdateParam(menuItem));
	}

	@Test
	public void createInsertQuery() {
		Assert.assertNotNull(DietMenuItemTable.createInsertQuery());
	}

	@Test
	public void createUpdateQuery() {
		Assert.assertNotNull(DietMenuItemTable.createUpdateQuery());
	}

}
