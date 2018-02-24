package com.gunnarro.dietmanager.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.gunnarro.dietmanager.config.DefaultTestConfig;
import com.gunnarro.dietmanager.config.TestMariDBDataSourceConfiguration;
import com.gunnarro.dietmanager.config.TestRepositoryConfiguration;
import com.gunnarro.dietmanager.domain.diet.DietMenu;
import com.gunnarro.dietmanager.domain.diet.DietPlan;
import com.gunnarro.dietmanager.domain.diet.FoodProduct;
import com.gunnarro.dietmanager.domain.diet.MenuItem;
import com.gunnarro.dietmanager.domain.diet.Product;
import com.gunnarro.dietmanager.domain.diet.Rule;
import com.gunnarro.dietmanager.domain.diet.Type;
import com.gunnarro.dietmanager.domain.health.HealthLogEntry;
import com.gunnarro.dietmanager.domain.log.LogEntry;
import com.gunnarro.dietmanager.domain.statistic.BodyMeasurementStatistic;
import com.gunnarro.dietmanager.domain.statistic.KeyValuePair;
import com.gunnarro.dietmanager.domain.statistic.MealStatistic;
import com.gunnarro.dietmanager.domain.view.KeyValuePairList;
import com.gunnarro.dietmanager.endpoint.rest.ChartData;
import com.gunnarro.dietmanager.repository.impl.DietManagerRepositoryImpl;

@ContextConfiguration(classes = { DietManagerRepositoryImpl.class, TestMariDBDataSourceConfiguration.class, TestRepositoryConfiguration.class })
@Transactional
//@Rollback(value = true)
 @Ignore
public class DietManagerRepositoryTest extends DefaultTestConfig {

    @Autowired
    private DietManagerRepository dietManagerRepository;

    @Before
    public void setUp() throws Exception {
    }

    /**
     * Manual
     */
    @Test
    @Ignore
    public void conflictStatistic() {
        List<KeyValuePair> list = dietManagerRepository.getConflictStatistic(30);
        assertNotNull(list);
        // for (KeyValuePair kv : list) {
        // System.out.println(kv);
        // }
    }

    @Test
    public void CRUDBodyMesuarementsLog() {
        HealthLogEntry log = new HealthLogEntry();
        log.setLogDate(new Date());
        log.setWeight(44.1);
        log.setHeight(158.6);
        log.setComment("comment");
        log.setHeightMetric("cm");
        log.setWeightMetric("kg");
        log.setUserId(1);
        Integer id = dietManagerRepository.createPersonalHealthData(log);
        assertTrue(id > 0);
        HealthLogEntry bodyMeasurementLog = dietManagerRepository.getBodyMeasurementLog(id);
        assertEquals(id, bodyMeasurementLog.getId());
        Integer rows = dietManagerRepository.deletePersonalHealthData(id);
        assertEquals(1, rows.intValue());
    }

    @Test
    public void CRUDietMenu() {
        DietMenu newMenu = new DietMenu();
        newMenu.setName("unit test menu name");
        newMenu.setDescription("unit test menu description");
        newMenu.setActive(true);
        // create new
        int newMenuId = dietManagerRepository.createDietMenu(newMenu);
        DietMenu menu = dietManagerRepository.getDietMenu(newMenuId);
        assertEquals(newMenuId, menu.getId().intValue());
        assertEquals("unit test menu name", menu.getName());
        assertEquals("unit test menu description", menu.getDescription());
        assertTrue(menu.isActive());
        // update existing
        DietMenu updateMenu = new DietMenu();
        updateMenu.setId(newMenuId);
        updateMenu.setDescription("unit test menu description UPDATED");
        updateMenu.setName("unit test menu name UPDATED");
        updateMenu.setActive(false);
        dietManagerRepository.updateDietMenu(updateMenu);
        menu = dietManagerRepository.getDietMenu(newMenuId);
        assertEquals(newMenuId, menu.getId().intValue());
        assertEquals("unit test menu name UPDATED", menu.getName());
        assertEquals("unit test menu description UPDATED", menu.getDescription());
        // delete diet menu item
        int deleteDietMenuRows = dietManagerRepository.deleteDietMenu(newMenuId);
        assertEquals(1, deleteDietMenuRows);
        menu = dietManagerRepository.getDietMenu(newMenuId);
        assertNull(menu);
    }

    @Test
    public void CRUDietMenuItem() {
        MenuItem newMenuItem = new MenuItem();
        newMenuItem.setFkDietMenuId(1);
        newMenuItem.setName("unit test menu item");
        newMenuItem.setCategory("category");
        newMenuItem.setEnergy(234);
        newMenuItem.setDescription("unit test menu item description");
        newMenuItem.setImageLink("http://gunnarro.no/image-manager/gallery/image/4");
        newMenuItem.setEnabled(true);
        // create new
        int newMenuItemId = dietManagerRepository.createDietMenuItem(newMenuItem);
        MenuItem menuItem = dietManagerRepository.getDietMenuItem(newMenuItemId);
        assertEquals(newMenuItemId, menuItem.getId().intValue());
        assertEquals(1, menuItem.getFkDietMenuId().intValue());
        assertEquals("unit test menu item", menuItem.getName());
        assertEquals("category", menuItem.getCategory());
        assertEquals(234, menuItem.getEnergy().intValue());
        assertEquals("unit test menu item description", menuItem.getDescription());
        assertEquals("http://gunnarro.no/image-manager/gallery/image/4", menuItem.getImageLink());
        assertTrue(menuItem.isEnabled());
        // update existing
        MenuItem updateMenuItem = new MenuItem();
        updateMenuItem.setId(newMenuItemId);
        updateMenuItem.setFkDietMenuId(1);
        updateMenuItem.setDescription("unit test menu item description UPDATED");
        updateMenuItem.setCategory("category UPDATED");
        updateMenuItem.setEnergy(1234);
        updateMenuItem.setEnabled(false);
        dietManagerRepository.updateDietMenuItem(updateMenuItem);
        menuItem = dietManagerRepository.getDietMenuItem(newMenuItemId);
        assertEquals(newMenuItemId, menuItem.getId().intValue());
        assertEquals(1, menuItem.getFkDietMenuId().intValue());
        assertEquals("unit test menu item", menuItem.getName());
        assertEquals("unit test menu item description UPDATED", menuItem.getDescription());
        assertEquals("category UPDATED", menuItem.getCategory());
        assertEquals(1234, menuItem.getEnergy().intValue());
        assertFalse(menuItem.isEnabled());
        // delete diet menu item
        int deleteDietMenuItemRows = dietManagerRepository.deleteDietMenuItem(newMenuItemId);
        assertEquals(1, deleteDietMenuItemRows);
        menuItem = dietManagerRepository.getDietMenuItem(newMenuItemId);
        assertNull(menuItem);
    }

    @Test
    public void CRUDProduct() {
        Product newProduct = new Product();
        newProduct.setName("product name");
        newProduct.setCategory("category");
        newProduct.setDescription("product description");
        newProduct.setImageLink("http://gunnarro.no/image-manager/gallery/image/4");
        // create new
        int newProductId = dietManagerRepository.createProduct(newProduct);
        Product product = dietManagerRepository.getProduct(newProductId);
        assertEquals(newProductId, product.getId().intValue());
        assertEquals("product name", product.getName());
        assertEquals("category", product.getCategory());
        assertEquals("product description", product.getDescription());
        assertEquals("http://gunnarro.no/image-manager/gallery/image/4", product.getImageLink());
        // update existing
        Product updateProduct = new Product();
        updateProduct.setId(newProductId);
        updateProduct.setDescription("product description UPDATED");
        updateProduct.setCategory("category UPDATED");
        dietManagerRepository.updateProduct(updateProduct);
        product = dietManagerRepository.getProduct(newProductId);
        assertEquals(newProductId, product.getId().intValue());
        assertEquals("product name", product.getName());
        assertEquals("product description UPDATED", product.getDescription());
        assertEquals("category UPDATED", product.getCategory());
        // delete diet menu item
        int deleteProductRows = dietManagerRepository.deleteProduct(newProductId);
        assertEquals(1, deleteProductRows);
        product = dietManagerRepository.getProduct(newProductId);
        assertNull(product);
    }

    @Test
    public void CRUDDietRule() {
        Rule rule = new Rule();
        rule.setFkDietPlanId(1);
        rule.setName("spisetid");
        rule.setDescription("30 minutter");
        rule.setActive(true);
        int id = dietManagerRepository.createDietRule(rule);
        assertTrue(id > 0);
        Rule newRule = dietManagerRepository.getDietRule(id);
        assertEquals("spisetid", newRule.getName());
        assertEquals("30 minutter", newRule.getDescription());
        assertEquals(1, newRule.getFkDietPlanId().intValue());
        assertEquals(true, newRule.isActive());

        // update rule
        newRule.setDescription("45 minutter");
        newRule.setActive(false);
        dietManagerRepository.updateDietRule(newRule);
        Rule updatedRule = dietManagerRepository.getDietRule(id);

        assertEquals("spisetid", updatedRule.getName());
        assertEquals("45 minutter", updatedRule.getDescription());
        assertEquals(1, updatedRule.getFkDietPlanId().intValue());
        assertEquals(false, updatedRule.isActive());

        // delete rule
        int rows = dietManagerRepository.deleteDietRule(id);
        assertTrue(rows == 1);
        assertTrue(dietManagerRepository.getDietRule(id) == null);
    }

    @Test
    public void getBmireferenceData() {
        List<ChartData> bmiReferenceData = dietManagerRepository.getBmiReferenceData();
        assertEquals(168, bmiReferenceData.size());
        // for (ChartData data : bmiReferenceData) {
        // System.out.println(data.toString());
        // }
    }

    @Test
    public void getBodyMesuarementsLog() {
        List<HealthLogEntry> bodyMeasurementsLogs = dietManagerRepository.getBodyMeasurementLogs(1);
        assertNotNull(bodyMeasurementsLogs);
        // assertEquals(0,
        // bodyMeasurementsLogs.get(0).getReferenceHeight().intValue());
        // assertEquals(0,
        // bodyMeasurementsLogs.get(0).getReferenceWeight().intValue());
        // for (HealthLogEntry h : bodyMeasurementsLogs) {
        // System.out.println("UnitTest getBodyMesuarementsLog: " +
        // h.getTrendWeight());
        // }
    }

    @Test
    public void getDietMenu() {
        DietMenu dietMenu = dietManagerRepository.getDietMenu(1);
        assertNotNull(dietMenu);
        assertEquals(1, dietMenu.getId().intValue());
        assertEquals("Menu", dietMenu.getName());
        assertEquals("Meny", dietMenu.getDescription());
        assertEquals(null, dietMenu.getPeriode());
        assertTrue(dietMenu.isActive());

        assertNotNull(dietMenu.getMenuCategoriesMap());
        assertTrue(dietMenu.getBreakfastMenuItems().size() > 0);
        assertEquals("Frokost", dietMenu.getBreakfastMenuItems().get(0).getName());
        assertTrue(dietMenu.getLunchMenuItems().size() > 0);
        assertEquals("Lunsj", dietMenu.getLunchMenuItems().get(0).getName());
        assertTrue(dietMenu.getDinnerMenuItems().size() > 0);
        assertEquals("Middag", dietMenu.getDinnerMenuItems().get(0).getName());
        assertTrue(dietMenu.getDessertMenuItems().size() > 0);
        assertEquals("Dessert", dietMenu.getDessertMenuItems().get(0).getName());
        assertTrue(dietMenu.getDinnerPortionMenuItems().size() > 0);
        assertEquals("Middag Porsjon", dietMenu.getDinnerPortionMenuItems().get(0).getName());
        assertTrue(dietMenu.getEveningMenuItems().size() > 0);
        assertEquals("Kveldsmat", dietMenu.getEveningMenuItems().get(0).getName());

        // problem with ������ and ������ when running from maven
        // assertTrue(dietMenu.getMealBetweenMenuItems().size() > 0);
        // assertTrue(dietMenu.getDinnerAccessoriesMenuItems().size() > 0);
        // Set<Entry<String, List<MenuItem>>> entrySet =
        // dietMenu.getMenuCategoriesMap().entrySet();
        // for (Entry<String, List<MenuItem>> e : entrySet) {
        // List<MenuItem> items = (List<MenuItem>) e.getValue();
        // System.out.println("Menu: " + e.getKey() + ": " + items);
        // for (MenuItem item : items) {
        // System.out.println("id_" + item.getId() +
        // " Menu Item Selection Trend: " + item.getSelectedCount() + " " +
        // item.getSelectionTrends());
        // }
        // }
    }

    @Test
    public void getDietMenuItemTypes() {
        List<Type> dietMenuItemTypes = dietManagerRepository.getDietMenuItemTypes();
        assertEquals(8, dietMenuItemTypes.size());
        // System.out.println("Selected menu items: " + dietMenuItemTypes);
    }

    @Test
    public void getDietPlanCurrent() {
        DietPlan dietPlan = dietManagerRepository.getDietPlan(1);
        assertNotNull(dietPlan);
        assertEquals(1, dietPlan.getId().intValue());
        assertEquals("EP Diet Plan 1", dietPlan.getName());
        assertEquals("Kostplan fra uke 16 i 2016 til uke 4 i 2017", dietPlan.getDescription());
        assertEquals(null, dietPlan.getPeriode());
        assertFalse(dietPlan.isActive());
        assertNotNull(dietPlan.getPlanItems());
        assertTrue(dietPlan.getPlanItems().size() > 1);
        assertNotNull(dietPlan.getPlanItems().get(0).getKey());
        assertNotNull(dietPlan.getPlanItems().get(0).getValue());
        assertNotNull(dietPlan.getPlanItems().get(0).getName());
        assertNotNull(dietPlan.getDietPlanRules());
        assertEquals(7, dietPlan.getDietPlanRules().size());
        // for (KeyValuePairList p : dietPlan.getPlanItems()) {
        // System.out.println("UnitTest DietPlan: " + p);
        // }
    }

    @Test
    public void getDietPlans() {
        List<DietPlan> dietPlans = dietManagerRepository.getDietPlans(2);
        assertNotNull(dietPlans);
        assertTrue(dietPlans.size() > 1);
        assertEquals(1, dietPlans.get(0).getId().intValue());
        assertEquals("EP Diet Plan 1", dietPlans.get(0).getName());
        assertEquals("Kostplan fra uke 16 i 2016 til uke 4 i 2017", dietPlans.get(0).getDescription());
        assertFalse(dietPlans.get(0).isActive());
    }

    @Test
    public void getDietProductChangeList() {
        List<FoodProduct> products = dietManagerRepository.getDietProductChangeList();
        assertNotNull(products);
        assertTrue(products.size() > 0);
        assertEquals("Yoghurt", products.get(0).getName());
        assertEquals("Porsjoner som tilsvarer 1 frukt yoghurt", products.get(0).getDescription());
        assertNotNull(products.get(0).getProductEquivalents());
        // for (KeyValuePair p : products.get(0).getProductEquivalents()) {
        // System.out.println(products.get(0).getName() + " Equivalent " + p);
        // }
    }

    @Test
    public void getFoodRecipes() {
        List<KeyValuePairList> foodRecipes = dietManagerRepository.getFoodRecipes();
        assertTrue(foodRecipes.size() > 0);
    }

    /**
     * use date_format which is not supported by hsql
     */
    @Ignore
    @Test
    public void getMenuItemSelectionTrend() {
        dietManagerRepository.createUserDietMenuItemLnk(1, createMenuItem(1, 10, null, 4, 4, 1, getLogEventId()));
        List<String> menuItemSelectionTrend = dietManagerRepository.getMenuItemSelectionTrend(1, 10, 7);
        assertTrue(menuItemSelectionTrend.size() == 1);
        // System.out.println("Selected menu items: " + menuItemSelectionTrend);
    }

    // @Ignore
    @Test
    public void getMissingMealsForUser() {
        Map<Date, List<String>> map = dietManagerRepository.getMissingMealsForUser(23, 7);
        // assertEquals(168, list.size());
        for (Map.Entry<Date, List<String>> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    @Test
    public void getProducts() {
        List<Product> products = dietManagerRepository.getProducts();
        assertEquals(22, products.size());
    }

    @Test
    public void getNumberOfActivitiesLastDays() {
        int numberOfActivitiesLastDays = dietManagerRepository.getNumberOfActivitiesLastDays(1);
        assertEquals(0, numberOfActivitiesLastDays);
    }

    @Test
    public void getNumberOfConflictsLastDays() {
        int numberOfConflictsLastDays = dietManagerRepository.getNumberOfConflictsLastDays(1);
        assertEquals(0, numberOfConflictsLastDays);
    }

    @Test
    public void getNumberOfMealsLastDays() {
        int numberOfMealsLastDays = dietManagerRepository.getNumberOfMealsLastDays(1);
        assertEquals(0, numberOfMealsLastDays);
    }

    //FIXME
    @Ignore
    @Test
    public void getSelectedMenuItemCountForUser() {
        int userId = 1;
        int menuItemId = 2;
        Integer userSelectedMenuItemCount = dietManagerRepository.getUserSelectedMenuItemCount(userId, menuItemId);
        assertTrue(userSelectedMenuItemCount == 0);
        dietManagerRepository.createUserDietMenuItemLnk(userId, createMenuItem(1, menuItemId, null, 4, 4, 1, getLogEventId()));
        dietManagerRepository.createUserDietMenuItemLnk(userId, createMenuItem(2, 3, null, 4, 4, 1, getLogEventId()));
        userSelectedMenuItemCount = dietManagerRepository.getUserSelectedMenuItemCount(userId, menuItemId);
        assertEquals(2, userSelectedMenuItemCount.intValue());
        // seems like that hsql do not support order by in delete statement
        // dietManagerRepository.deleteUserDietMenuItemLnk(1, 2);
        // userSelectedMenuItemCount =
        // dietManagerRepository.getUserSelectedMenuItemCount(1, 2);
        // assertTrue(userSelectedMenuItemCount == 1);
    }

    @Test
    public void getSelectedMenuItemsForUser() {
        Integer userId = 1;
        dietManagerRepository.createUserDietMenuItemLnk(userId, createMenuItem(1, 35, new Date(), 4, 4, 1, getLogEventId()));// breakfast
        dietManagerRepository.createUserDietMenuItemLnk(userId, createMenuItem(2, 38, new Date(), 4, 4, 1, getLogEventId())); // lunch
        dietManagerRepository.createUserDietMenuItemLnk(userId, createMenuItem(3, 45, new Date(), 4, 4, 1, getLogEventId())); // meal
        // between
        dietManagerRepository.createUserDietMenuItemLnk(userId, createMenuItem(4, 4, new Date(), 4, 4, 1, getLogEventId())); // dinner
        dietManagerRepository.createUserDietMenuItemLnk(userId, createMenuItem(5, 33, new Date(), 4, 4, 1, getLogEventId())); // dinner
        // dessert
        dietManagerRepository.createUserDietMenuItemLnk(userId, createMenuItem(6, 42, new Date(), 4, 4, 1, getLogEventId())); // evening
        List<MenuItem> menuItems = dietManagerRepository.getSelectedMenuItemsForUser(userId, 60);
        assertEquals(6, menuItems.size());
        assertNotNull(menuItems.get(0).getId());
        assertNotNull(menuItems.get(0).getPrimaryKeyId());
        assertNotNull(menuItems.get(0).getName());
        assertNotNull(menuItems.get(0).getSortByValue());
        assertEquals(1, menuItems.get(0).getFkDietMenuId().intValue());
        assertEquals(4, menuItems.get(0).getControlledByUserId().intValue());
        assertEquals(1, menuItems.get(0).getCausedConflict().intValue());
    }

    @Test
    public void getSelectedMenuItemTrendForUser() {
        Integer userId = 1;
        Integer controlledByuserId = 4;
        Integer preparedByuserId = 4;
        Integer causedConfict = 1;
        dietManagerRepository.createUserDietMenuItemLnk(userId,
                createMenuItem(1, 1, new Date(), controlledByuserId, preparedByuserId, causedConfict, getLogEventId()));
        dietManagerRepository.createUserDietMenuItemLnk(userId,
                createMenuItem(2, 2, new Date(), controlledByuserId, preparedByuserId, causedConfict, getLogEventId()));
        dietManagerRepository.createUserDietMenuItemLnk(userId,
                createMenuItem(3, 3, new Date(), controlledByuserId, preparedByuserId, causedConfict, getLogEventId()));
        dietManagerRepository.createUserDietMenuItemLnk(userId,
                createMenuItem(4, 4, new Date(), controlledByuserId, preparedByuserId, causedConfict, getLogEventId()));
        dietManagerRepository.createUserDietMenuItemLnk(userId,
                createMenuItem(5, 5, new Date(), controlledByuserId, preparedByuserId, causedConfict, getLogEventId()));
        dietManagerRepository.createUserDietMenuItemLnk(userId,
                createMenuItem(6, 6, new Date(), controlledByuserId, preparedByuserId, causedConfict, getLogEventId()));
        dietManagerRepository.createUserDietMenuItemLnk(userId,
                createMenuItem(7, 7, new Date(), controlledByuserId, preparedByuserId, causedConfict, getLogEventId()));
        dietManagerRepository.createUserDietMenuItemLnk(userId,
                createMenuItem(8, 8, new Date(), controlledByuserId, preparedByuserId, causedConfict, getLogEventId()));
        dietManagerRepository.createUserDietMenuItemLnk(userId,
                createMenuItem(9, 9, new Date(), controlledByuserId, preparedByuserId, causedConfict, getLogEventId()));
        List<MenuItem> list = dietManagerRepository.getMenuSelctionTrendForUser(1, 7);
        assertTrue(list.size() == 7);
        assertNotNull(list);

        // for (MenuItem m : list) {
        // System.out.println(m);
        // }
    }

    /**
     * use date_format which is not supported by hsql
     */
    @Test
    public void isMealRegistered() {
        Date date = new Date();
        int userId = 4;
        int dietMenuId = 2;
        int id = 1;
        String mealName = "Middag";
        MenuItem selectedMenuItem = createMenuItem(id, dietMenuId, null, 5, 5, 0, getLogEventId());
        assertTrue(selectedMenuItem.getCreatedTime() > 0);
        assertNotNull(selectedMenuItem.getCreatedDate());
        assertEquals(0, dietManagerRepository.checkUserDietMenuItemLnk(userId, date, mealName));
        assertTrue(dietManagerRepository.createUserDietMenuItemLnk(userId, selectedMenuItem) > 0);
        List<MealStatistic> mealStatistic = dietManagerRepository.getMealStatistic(userId, 2);
        System.out.println(dietManagerRepository.getSelecedMealNamesForDate(date));
        for (MealStatistic s : mealStatistic) {
            System.out.println(s);
        }

        assertTrue(dietManagerRepository.checkUserDietMenuItemLnk(userId, date, mealName) > 0);
    }

    // ------------------- Products ------------------------------

    /**
     * Manual
     */
    @Test
    public void mealsManagedByUserStatistic() {
        dietManagerRepository.createUserDietMenuItemLnk(4, createMenuItem(1, 12, new Date(), 4, 4, 1, getLogEventId()));
        dietManagerRepository.createUserDietMenuItemLnk(4, createMenuItem(2, 13, new Date(), 5, 5, 0, getLogEventId()));
        dietManagerRepository.createUserDietMenuItemLnk(4, createMenuItem(3, 14, new Date(), 6, 6, 0, getLogEventId()));
        dietManagerRepository.createUserDietMenuItemLnk(4, createMenuItem(4, 32, new Date(), 4, 4, 0, getLogEventId())); // dinner
        // desert,
        // should
        // not
        // be
        // counted
        List<KeyValuePair> list = dietManagerRepository.getMealsManagedByUserStatistic(30);
        assertEquals(3, list.size());
        assertNotNull(list.get(0).getKey());// weeknumber
        assertEquals("mamma", list.get(0).getValue());
        assertEquals(1, list.get(0).getCount().intValue());
        assertEquals(0, list.get(0).getTotalCount().intValue());
        // for (KeyValuePair kv : list) {
        // System.out.println(kv);
        // }
    }

    @Test
    public void mealsPreparedByUserStatistic() {
        dietManagerRepository.createUserDietMenuItemLnk(4, createMenuItem(1, 12, new Date(), 4, 4, 1, getLogEventId()));
        dietManagerRepository.createUserDietMenuItemLnk(4, createMenuItem(2, 13, new Date(), 5, 5, 0, getLogEventId()));
        dietManagerRepository.createUserDietMenuItemLnk(4, createMenuItem(3, 14, new Date(), 6, 6, 0, getLogEventId()));
        dietManagerRepository.createUserDietMenuItemLnk(4, createMenuItem(4, 32, new Date(), 4, 4, 0, getLogEventId())); // dinner
        // desert,
        // should
        // not
        // be
        // counted
        List<KeyValuePair> list = dietManagerRepository.getMealsPreparedByUserStatistic(30);
        // assertEquals(3, list.size());
        assertNotNull(list.get(0).getKey());// weeknumber
        assertEquals("mamma", list.get(0).getValue());
        assertEquals(1, list.get(0).getCount().intValue());
        assertEquals(0, list.get(0).getTotalCount().intValue());
        // for (KeyValuePair kv : list) {
        // System.out.println(kv);
        // }
    }

    @Ignore
    @Test
    public void getMealStatisticForUser_localdb() {
        List<MealStatistic> list = dietManagerRepository.getMealStatistic(6, 30);
        assertEquals(16, list.size());
        assertNotNull(list.get(0).getWeekNumber());
        // assertEquals("mamma", list.get(0).getUserName());
        // assertEquals(6, list.get(0).getUserId().intValue());
        assertEquals(0, list.get(0).getMealsCausedConflictCount().intValue());
        assertEquals(0, list.get(0).getMealsControlledByUserCount().intValue());
        assertEquals(1, list.get(0).getMealsPreparedByUserCount().intValue());
        // for (KeyValuePair kv : list) {
        // System.out.println(kv);
        // }
    }

    @Test
    public void mealTypeStatistic() {
        dietManagerRepository.createUserDietMenuItemLnk(4, createMenuItem(1, 12, new Date(), 4, 4, 0, getLogEventId()));
        dietManagerRepository.createUserDietMenuItemLnk(4, createMenuItem(2, 13, new Date(), 5, 5, 0, getLogEventId()));
        dietManagerRepository.createUserDietMenuItemLnk(4, createMenuItem(3, 14, new Date(), 6, 6, 1, getLogEventId()));
        List<KeyValuePair> list = dietManagerRepository.getMealTypesStatistic(30);
        assertEquals("Middag", list.get(0).getKey());
        assertEquals(3, list.get(0).getCount().intValue());
        // for (KeyValuePair kv : list) {
        // System.out.println(kv);
        // }
    }

    /**
     * Manual
     */
    // @Ignore
    @Test
    public void selectedMealsStatistic() {
        // System.out.println(DietManagerSql.createSelectedMealsQuery());
        dietManagerRepository.createUserDietMenuItemLnk(4, createMenuItem(1, 12, new Date(), 4, 4, 1, getLogEventId()));
        dietManagerRepository.createUserDietMenuItemLnk(4, createMenuItem(2, 12, new Date(), 5, 5, 0, getLogEventId()));
        dietManagerRepository.createUserDietMenuItemLnk(4, createMenuItem(3, 14, new Date(), 6, 6, 0, getLogEventId()));
        dietManagerRepository.createUserDietMenuItemLnk(4, createMenuItem(4, 32, new Date(), 4, 4, 0, getLogEventId()));
        List<KeyValuePair> list = dietManagerRepository.getSelectedMealsStatistic(30);
        assertNotNull(list);
        // for (KeyValuePair kv : list) {
        // System.out.println(kv);
        // }
    }

    /**
     * Manual
     */
    @Test
    public void summaryStatistic() {
        dietManagerRepository.createUserDietMenuItemLnk(4, createMenuItem(1, 12, new Date(), 4, 4, 0, getLogEventId()));
        dietManagerRepository.createUserDietMenuItemLnk(4, createMenuItem(2, 13, new Date(), 5, 5, 0, getLogEventId()));
        dietManagerRepository.createUserDietMenuItemLnk(4, createMenuItem(3, 14, new Date(), 6, 6, 1, getLogEventId()));
        List<KeyValuePair> list = dietManagerRepository.getSummaryStatistic(30);
        assertEquals(3, list.size());
        assertEquals("mamma", list.get(0).getKey());
        assertEquals("mamma", list.get(0).getValue());
        assertEquals(1, list.get(0).getCount().intValue());
        assertEquals(1, list.get(0).getTotalCount().intValue());
        // for (KeyValuePair kv : list) {
        // System.out.println(kv);
        // }
    }

    @Test
    public void getDietRules() {
        List<Rule> allDietRules = dietManagerRepository.getDietRules(1);
        assertEquals(2, allDietRules.size());
    }

    @Test
    public void getAllDietRules() {
        List<Rule> allDietRules = dietManagerRepository.getAllDietRules();
        System.out.println("getAllDietRules: " + allDietRules);
        assertEquals(2, allDietRules.size());
    }

    /**
     * hsql do not support date function that is used in this query
     */
    // @Ignore
    @Test
    public void getConflictStatistic() {
        List<KeyValuePair> conflictStatistic = dietManagerRepository.getConflictStatistic(7);
        assertNotNull(conflictStatistic.size());
    }

    @Test
    public void getMenus() {
        List<DietMenu> menus = dietManagerRepository.getMenus(1);
        assertNotNull(menus);
    }

    // @Ignore
    @Test
    public void getBodyMeasurementsLogAverage() {
        HealthLogEntry newLog = new HealthLogEntry();
        newLog.setLogDate(new Date());
        newLog.setWeight(44.0);
        newLog.setHeight(158.6);
        newLog.setComment("comment");
        newLog.setHeightMetric("cm");
        newLog.setWeightMetric("kg");
        newLog.setUserId(4);
        dietManagerRepository.createPersonalHealthData(newLog);

        HealthLogEntry newLog2 = new HealthLogEntry();
        newLog2.setLogDate(new Date());
        newLog2.setWeight(48.0);
        newLog2.setHeight(160.6);
        newLog2.setComment("comment");
        newLog2.setHeightMetric("cm");
        newLog2.setWeightMetric("kg");
        newLog2.setUserId(4);
        dietManagerRepository.createPersonalHealthData(newLog2);

        HealthLogEntry newLog3 = new HealthLogEntry();
        newLog3.setLogDate(new Date());
        newLog3.setWeight(50.0);
        newLog3.setHeight(160.6);
        newLog3.setComment("comment");
        newLog3.setHeightMetric("cm");
        newLog3.setWeightMetric("kg");
        newLog3.setUserId(4);
        dietManagerRepository.createPersonalHealthData(newLog3);

        BodyMeasurementStatistic log = dietManagerRepository.getBodyMeasurementStatistic(4, 30);
        assertNotNull(log);
        assertNotNull(log.getStartDate());
        assertNotNull(log.getEndDate());
        assertEquals("44.0", Double.toString(log.getMinWeight()));
        assertEquals("50.0", Double.toString(log.getMaxWeight()));
        assertEquals("158.6", Double.toString(log.getMinHeight()));
        assertEquals("160.6", Double.toString(log.getMaxHeight()));
        // assertEquals("158.6", Double.toString(log.getAverageHeight()));
        // assertEquals("160.6", Double.toString(log.getAverageWeight()));
    }

    private int getLogEventId() {
        LogEntry log = new LogEntry();
        log.setId(1);
        return log.getId();
    }

    /**
     * date_fomat function not supported by hsql da
     */
    // @Ignore
    @Test
    public void getSelectedMealNamesForDate() {
        List<String> list = dietManagerRepository.getSelecedMealNamesForDate(new Date());
        assertEquals(0, list.size());
    }

    @After
    public void tearDown() throws Exception {
    }

    // Test data

    private static MenuItem createMenuItem(int id, int dietMenuId, Date createdDate, int controlledByUserId, int preparedByUserId, int causedConflict,
            int logId) {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(id);
        menuItem.setFkDietMenuId(dietMenuId);
        menuItem.setCreatedDate(createdDate);
        menuItem.setControlledByUserId(controlledByUserId);
        menuItem.setPreparedByUserId(preparedByUserId);
        menuItem.setCausedConflict(causedConflict);
        menuItem.setFkLogId(logId);
        return menuItem;
    }
}
