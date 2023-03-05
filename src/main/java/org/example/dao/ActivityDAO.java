package org.example.dao;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.example.model.Activity;

import java.time.*;
import java.util.Arrays;
import java.util.Date;


public class ActivityDAO {
    private static final String DB_NAME = "ud5db";
    private static final String COLLECTION_NAME = "activities";

    private final MongoClient mongoClient;
    private final MongoCollection<Activity> activityCollection;

    public ActivityDAO() {
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .applyToClusterSettings(builder -> builder.hosts(Arrays.asList(new ServerAddress("localhost", 27017))))
                        .codecRegistry(codecRegistry)
                        .build());

        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        activityCollection = database.getCollection(COLLECTION_NAME, Activity.class);
    }

    public void create(Activity activity) {
        activityCollection.insertOne(activity);
    }

    public Activity read(ObjectId id) {
        return activityCollection.find(Filters.eq("_id", id)).first();
    }

    public Activity findByTittle(String tittle) {
        return activityCollection.find(Filters.eq("tittle", tittle)).first();
    }

    public void update(Activity activity) {
        UpdateResult result = activityCollection.replaceOne(Filters.eq("_id", activity.getId()), activity);
        if (result.getModifiedCount() == 0) {
            throw new RuntimeException("UPDATE: No activity found with id " + activity.getId());
        }
    }

    public void delete(ObjectId id) {
        DeleteResult result = activityCollection.deleteOne(Filters.eq("_id", id));
        if (result.getDeletedCount() == 0) {
            throw new RuntimeException("DELETE: No activity found with id " + id);
        }
    }

    public void close() {
        mongoClient.close();
    }


    public FindIterable<Activity> findBetweenDates(LocalDate startDate, LocalDate endDate) {
        System.out.println(startDate + " - " + endDate);
        Bson query = Filters.and(Filters.gte("date", startDate),
                Filters.lt("date", endDate));

        // FindIterable<Activity> results = activityCollection.find(query);
        return activityCollection.find(query);
    }

    public FindIterable<Activity> findByDate(LocalDate date) {
        Bson query = Filters.eq("date", date);

        // FindIterable<Activity> results = activityCollection.find(query);
        return activityCollection.find(query);
    }

    public FindIterable<Activity> findBetweenHours(LocalDateTime startTime, LocalDateTime endTime) {
        System.out.println(startTime + " - " + endTime);
        FindIterable<Activity> query = activityCollection.find(
                Filters.and(
                        Filters.gte("time", startTime),
                        Filters.lt("time", endTime)));

        return query;
    }


}
