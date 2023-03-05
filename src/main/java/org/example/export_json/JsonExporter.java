package org.example.export_json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class JsonExporter {
    public static void exportToJson(List<Object> items, SaveDirectory saveDirectory, String fileName) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(saveDirectory.getPath() + fileName), items);
        } catch (Exception e) {
            System.out.println("Error export file: " + fileName + "\nCause: " + e.getMessage());
        }
    }

    public static void exportToJson(AggregateIterable<Document> items, SaveDirectory saveDirectory, String fileName) {
        MongoCursor<Document> cursor = items.iterator();
        JSONArray jsonArray = new JSONArray();

        while (cursor.hasNext()) {
            Document document = cursor.next();
            JSONObject jsonObject = new JSONObject(document.toJson());
            jsonArray.put(jsonObject);
        }

        try (FileWriter file = new FileWriter(saveDirectory.getPath() + fileName)) {
            file.write(jsonArray.toString());
        } catch (Exception e) {
            System.out.println("Error export file: " + fileName + "\nCause: " + e.getMessage());
        }
    }
}
