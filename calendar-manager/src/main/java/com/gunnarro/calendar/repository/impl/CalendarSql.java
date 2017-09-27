package com.gunnarro.calendar.repository.impl;

/**
 * This class contains all the query which are fired on DietManager
 * 
 */
public class CalendarSql {

	private CalendarSql() {
	}

	public static String createLeagueTableQuery() {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT * FROM league_table_view");
		return sqlQuery.toString();
	}
}
