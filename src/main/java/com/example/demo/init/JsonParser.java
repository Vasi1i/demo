package com.example.demo.init;

import com.example.demo.model.db.CpeEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

@Component
public class JsonParser {

    public List<CpeEntity> parse(String filePath) {
        List<CpeEntity> productList = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
            objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JsonNode rootNode = objectMapper.readTree(new File(filePath));
            if (rootNode != null && rootNode.has("products")) {
                JsonNode productsNode = rootNode.get("products");
                for (JsonNode productNode : productsNode) {
                    CpeEntity cpeEntity = objectMapper.treeToValue(productNode.get("cpe"), CpeEntity.class);
                    productList.add(cpeEntity);
                }
            } else {
                System.out.println("JSON file does not contain the 'products' array");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productList;
    }

}