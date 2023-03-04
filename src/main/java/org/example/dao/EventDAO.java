package org.example.dao;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.example.model.Activity;
import org.example.model.Event;

import java.util.Arrays;

public class EventDAO {
    private static final String DB_NAME = "ud5db";
    private static final String COLLECTION_NAME = "events";

    private final MongoClient mongoClient;
    private final MongoCollection<Event> eventsCollection;

    public EventDAO() {
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .applyToClusterSettings(builder -> builder.hosts(Arrays.asList(new ServerAddress("localhost", 27017))))
                        .codecRegistry(codecRegistry)
                        .build());

        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        eventsCollection = database.getCollection(COLLECTION_NAME, Event.class);
    }

    public void create(Event event) {
        eventsCollection.insertOne(event);
    }

    public Event findByTittle(String tittle) {return eventsCollection.find(Filters.eq("tittle", tittle)).first();}

    public Event read(ObjectId id) {
        return eventsCollection.find(Filters.eq("_id", id)).first();
    }

    public void update(Event event) {
        UpdateResult result = eventsCollection.replaceOne(Filters.eq("_id", event.getId()), event);
        if (result.getModifiedCount() == 0) {
            throw new RuntimeException("UPDATE: No event found with id " + event.getId());
        }
    }

    public void delete(ObjectId id) {
        DeleteResult result = eventsCollection.deleteOne(Filters.eq("_id", id));
        if (result.getDeletedCount() == 0) {
            throw new RuntimeException("DELETE: No event found with id " + id);
        }
    }

    public void close() {
        mongoClient.close();
    }
}
