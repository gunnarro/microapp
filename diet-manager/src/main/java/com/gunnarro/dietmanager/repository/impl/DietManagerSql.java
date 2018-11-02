package com.gunnarro.dietmanager.repository.impl;

/**
 * This class contains all the query which are fired on DietManager
 * 
 */
public class DietManagerSql {

    /**
     * statistic conflicts last 30 days query
     * 
     * @return
     */
    public static String createConfictsQuery() {
        String totalMealsCountSubQuery = "(SELECT count(*) FROM user_diet_menu_item_lnk WHERE l.created_date_time >= CURRENT_DATE - INTERVAL ? DAY) AS total_cont";
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(
                "SELECT count(l.caused_conflict) AS count, i.menu_item_name, u.username, (CURRENT_DATE - INTERVAL ? DAY) AS from_date, CURRENT_DATE AS to_date,");
        sqlQuery.append(totalMealsCountSubQuery);
        sqlQuery.append(" FROM user_diet_menu_item_lnk l, diet_menu_items i, users u");
        sqlQuery.append(" WHERE l.created_date_time >= CURRENT_DATE - INTERVAL ? DAY");
        sqlQuery.append(" AND l.caused_conflict = 1");
        sqlQuery.append(" AND l.fk_diet_menu_item_id = i.id");
        sqlQuery.append(" AND l.fk_controlled_by_user_id = u.id");
        sqlQuery.append(" GROUP BY l.caused_conflict,i.menu_item_name");
        sqlQuery.append(" ORDER BY count DESC");
        return sqlQuery.toString();
    }

    public static String createDroppedMealsOverviewQuery() {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT DISTINCT DATE_FORMAT(created_date_time, '%d.%m.%Y') AS created_date, count(*) AS meals");
        sqlQuery.append(" FROM user_diet_menu_item_lnk l");
        sqlQuery.append(" WHERE l.created_date_time >= CURRENT_DATE - INTERVAL ? DAY");
        sqlQuery.append(" GROUP BY DATE_FORMAT(created_date_time, '%d.%m.%Y')");
        sqlQuery.append(" HAVING meals < 5");
        // sqlQuery.append(" ORDER BY l.created_date_time DESC");
        return sqlQuery.toString();
    }

    /**
     * statistic selected meals last 30 days query
     * 
     * @return
     */
    /**
     * public static String createSelectedMealsQuery() { StringBuilder sqlQuery
     * = new StringBuilder(); sqlQuery.append( "SELECT
     * count(l.fk_diet_menu_item_id) AS count, i.menu_item_name,
     * i.menu_item_description, (CURRENT_DATE - INTERVAL ? DAY) AS from_date,
     * CURRENT_DATE AS to_date" ); sqlQuery.append(" FROM
     * user_diet_menu_item_lnk l, diet_menu_items i"); sqlQuery .append(" WHERE
     * l.created_date_time >= CURRENT_DATE - INTERVAL ? DAY"); sqlQuery.append("
     * AND l.fk_diet_menu_item_id = i.id"); sqlQuery.append(" GROUP BY
     * l.fk_diet_menu_item_id"); sqlQuery.append(" ORDER BY count"); return
     * sqlQuery.toString(); }
     */

    public static String createGetMealsForDate() {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT i.*");
        sqlQuery.append(" FROM user_diet_menu_item_lnk l, diet_menu_items i");
        sqlQuery.append(" WHERE DATE_FORMAT(l.created_date_time, '%d.%m.%Y') = ?");
        sqlQuery.append(" AND l.fk_diet_menu_item_id = i.id");
        return sqlQuery.toString();
    }

    public static String createLeagueTableQuery() {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT * FROM league_table_view");
        return sqlQuery.toString();
    }

    public static String createLogLevelCountQuery() {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT count(*) AS count");
        sqlQuery.append(" FROM event_log");
        sqlQuery.append(" WHERE created_date_time > CURRENT_DATE - INTERVAL ? DAY");
        sqlQuery.append(" AND level = ?");
        return sqlQuery.toString();
    }

    /**
     * statistic meals managed by user
     * 
     * @return
     */
    public static String createMealsManagedByUserQuery() {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT week(l.created_date_time) AS weeknumber");
        sqlQuery.append(", count(l.fk_controlled_by_user_id) AS meals_controlled_by_user_count");
        sqlQuery.append(", sum(l.caused_conflict = 1) AS total_count");
        sqlQuery.append(", u.id");
        sqlQuery.append(", u.username");
        sqlQuery.append(" FROM user_diet_menu_item_lnk l, users u, diet_menu_items i");
        sqlQuery.append(" WHERE l.fk_controlled_by_user_id = u.id");
        sqlQuery.append(" AND l.fk_diet_menu_item_id = i.id");
        sqlQuery.append(" AND i.menu_item_name IN('Frokost','Lunsj', 'Mellom måltid','Middag','Kveldsmat')");
        sqlQuery.append(" GROUP BY weeknumber, l.fk_controlled_by_user_id");
        sqlQuery.append(" ORDER BY weeknumber, l.fk_controlled_by_user_id DESC");
        return sqlQuery.toString();
    }

    /**
     * statistic meals managed by user
     * 
     * @return
     */
    public static String createMealsPreparedByUserQuery() {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT week(l.created_date_time) AS weeknumber");
        sqlQuery.append(", count(l.fk_prepared_by_user_id) AS meals_prepared_by_user_count");
        sqlQuery.append(", sum(l.caused_conflict = 1) AS total_count");
        sqlQuery.append(", u.id");
        sqlQuery.append(", u.username");
        sqlQuery.append(" FROM user_diet_menu_item_lnk l, users u, diet_menu_items i");
        sqlQuery.append(" WHERE l.fk_prepared_by_user_id = u.id");
        sqlQuery.append(" AND l.fk_diet_menu_item_id = i.id");
        sqlQuery.append(" AND i.menu_item_name IN('Frokost','Lunsj', 'Mellom måltid','Middag','Kveldsmat')");
        sqlQuery.append(" GROUP BY weeknumber, l.fk_prepared_by_user_id");
        sqlQuery.append(" ORDER BY weeknumber, l.fk_prepared_by_user_id DESC");
        return sqlQuery.toString();
    }

    public static String createSelectedMealsMissingDatesQuery() {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT DISTINCT d.db_date");
        sqlQuery.append(" FROM time_dimension d");
        sqlQuery.append(" WHERE d.db_date BETWEEN '2016-10.01' AND '2016-10-31'");
        sqlQuery.append(" AND d.db_date NOT IN (SELECT DATE_FORMAT(l.created_date_time, '%Y-%m-%d')");
        sqlQuery.append(" FROM user_diet_menu_item_lnk l)");
        return sqlQuery.toString();
    }

    public static String createSelectedMealsStatisticQuery() {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT count(l.id) AS count, i.id, i.menu_item_name, i.menu_item_description");
        sqlQuery.append(" FROM user_diet_menu_item_lnk l, diet_menu_items i");
        sqlQuery.append(" WHERE l.fk_diet_menu_item_id = i.id");
        sqlQuery.append(" GROUP BY l.fk_diet_menu_item_id");
        sqlQuery.append(" ORDER BY i.menu_item_name, count DESC");
        return sqlQuery.toString();
    }

    public static String createSummaryStatisticQuery() {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(
                "SELECT count(l.fk_controlled_by_user_id) AS meals_controlled_by_user_count, sum(l.caused_conflict = 1) AS total_count, u.id, u.username");
        sqlQuery.append(" FROM user_diet_menu_item_lnk l, users u");
        sqlQuery.append(" WHERE l.fk_controlled_by_user_id = u.id");
        sqlQuery.append(" GROUP BY l.fk_controlled_by_user_id");
        sqlQuery.append(" ORDER BY l.fk_controlled_by_user_id DESC");
        return sqlQuery.toString();
    }

    public static String createMealStatisticQuery() {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT l.created_date_time AS created_date");
        sqlQuery.append(", year(l.created_date_time) AS year");
        sqlQuery.append(", week(l.created_date_time, 1) AS weeknumber");// 1:
                                                                        // week
                                                                        // from
                                                                        // monday
                                                                        // to
                                                                        // sunday
        sqlQuery.append(", count(IF (l.fk_controlled_by_user_id = ?, 1, null)) AS meals_controlled_by_user_count");
        sqlQuery.append(", count(IF (l.fk_prepared_by_user_id = ?, 1, null)) AS meals_prepared_by_user_count");
        sqlQuery.append(", sum(IF (l.fk_controlled_by_user_id = ? AND l.caused_conflict = 1, 1, null)) AS meals_caused_conflict_count");
        sqlQuery.append(", (SELECT u.username FROM users u WHERE u.id = ?) AS username ");
        sqlQuery.append(" FROM user_diet_menu_item_lnk l, diet_menu_items i");
        sqlQuery.append(" WHERE l.fk_diet_menu_item_id = i.id");
        sqlQuery.append(" AND i.menu_item_name IN('Frokost','Lunsj', 'Mellom måltid','Middag','Kveldsmat')");
        sqlQuery.append(" GROUP BY year, weeknumber");
        sqlQuery.append(" ORDER BY year, weeknumber ASC");
        return sqlQuery.toString();
    }

    private DietManagerSql() {
    }
}