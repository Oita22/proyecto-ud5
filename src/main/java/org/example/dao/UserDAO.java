package org.example.dao;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.example.model.User;

import java.util.Arrays;

public class UserDAO {
    private static final String DB_NAME = "ud5db";
    private static final String COLLECTION_NAME = "users";

    private final MongoClient mongoClient;
    private final MongoCollection<User> userCollection;

    public UserDAO() {
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .applyToClusterSettings(builder -> builder.hosts(Arrays.asList(new ServerAddress("localhost", 27017))))
                        .codecRegistry(codecRegistry)
                        .build());

        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        userCollection = database.getCollection(COLLECTION_NAME, User.class);

        // Restrictions
        //applyRestrictions();
    }

    private void applyRestrictions() {
        // Unique Username
        userCollection.createIndex(Indexes.ascending("username"), new IndexOptions().unique(true));
    }

    public void create(User user) {
        userCollection.insertOne(user);
    }

    public User read(ObjectId id) {
        return userCollection.find(Filters.eq("_id", id)).first();
    }

    public User findByUsername(String username) {
        return userCollection.find(Filters.eq("username", username)).first();
    }

    public void update(User user) {
        UpdateResult result = userCollection.replaceOne(Filters.eq("_id", user.getId()), user);
        if (result.getModifiedCount() == 0) {
            throw new RuntimeException("UPDATE: No user found with id " + user.getId());
        }
    }

    public void delete(ObjectId id) {
        DeleteResult result = userCollection.deleteOne(Filters.eq("_id", id));
        if (result.getDeletedCount() == 0) {
            throw new RuntimeException("DELETE: No user found with id " + id);
        }
    }

    public void close() {
        mongoClient.close();
    }
}
