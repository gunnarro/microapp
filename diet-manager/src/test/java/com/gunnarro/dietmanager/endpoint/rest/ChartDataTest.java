package com.gunnarro.dietmanager.endpoint.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChartDataTest {

    @Test
    public void mapToJson() throws JsonGenerationException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<ChartData> list = new ArrayList<>();
        list.add(new ChartData("label1", 2));
        mapper.writeValue(System.out, new ChartData("label1", 2));
        // mapper.writeValue(System.out, list);
    }

    @Test
    public void mapFromJson() throws JsonGenerationException, JsonMappingException, IOException {
        String json = "{\"label\":\"Label-1\",\"value1\":\"21.0\",\"value2\":\"22.0\",\"value3\":\"23.0\",\"value4\":\"24.0\"}";
        ObjectMapper mapper = new ObjectMapper();
        ChartData data = mapper.readValue(json, ChartData.class);
        Assert.assertEquals("ChartData [id=null, label=Label-1, value1=21.0, value2=22.0, value3=23.0, value4=24.0]", data.toString());
    }
}
