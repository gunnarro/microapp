package com.gunnarro.imagemanager.repository.table;


import org.junit.Assert;
import org.junit.Test;

public class ImageDetailsTableTest {
    
    @Test
    public void createQuery() {
        String sqlQuery = ImageDetailsTable.createInsertQuery();
        Assert.assertEquals("INSERT INTO image_details(last_modified_date_time,image_date_time,fk_user_id,image_name,image_title,image_path,image_mapped_absolute_path,image_size_byte,image_geo_location,image_description,image_type) VALUES (?,?,?,?,?,?,?,?,?,?,?)", sqlQuery);
    }

}
