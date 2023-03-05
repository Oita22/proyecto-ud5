package org.example.dao;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.example.model.User;

import java.util.Arrays;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

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
        applyRestrictions();
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


    /**
     * Consulta empleando filtros.
     * Búsqueda de todos los usuarios de la base de datos
     *
     * @return FindIterable<Event> con el resultado de la consulta
     */
    public FindIterable<User> findAll() {
        return userCollection.find();
    }

    /**
     * Consulta empleando filtros.
     * Búsqueda de un Usuario por su nombre de usuario
     * El username tiene filtro asociado de unicidad, por lo que solo existe 1 como máximo
     *
     * @param username Nombre de usuario
     * @return User. Si existe devuelve el usuario buscado
     */
    public User findByUsername(String username) {
        return userCollection.find(Filters.eq("username", username)).first();
    }

    /**
     * Consulta empleando filtros.
     * Búsqueda de un Usuario por su nombre de usuario y estado
     *
     * @param username Nombre de usuario
     * @param enabled Estado. True - Activo | False - Ban
     * @return FindIterable<Event> con el resultado de la consulta
     */
    public User findByUsernameAndEnable(String username, boolean enabled) {
        return userCollection.find(and(
                eq("enabled", enabled),
                eq("username", username)
        )).first();
    }

    // ----------------------------------------------------------------------------------------------------------------

    /**
     * Actualiza el apellido de un Usuario que busca por su nombre de usuario
     *
     * @param username String - Nombre de usuario
     * @param surname String nuevo apellido
     * @return User asociado al username recibido
     */
    public User updateSurnameByUsername(String username, String surname) {
        Bson filter = eq("username", username);
        Bson update = Updates.set("profile.surname", surname);

        userCollection.updateMany(filter, update);

        return findByUsername(username);
    }

    /**
     * Actualiza el campo profile.birth_year de todos los usuarios que ese campo sea menor del año recibido
     * y lo aumenta en la cantidad pasada
     *
     * @param year int - Año hasta el que se modificará
     * @param increment int - Cantidad a aumentar
     */
    public void updateBirthYearIncreaseByYear(int year, int increment) {
        userCollection.updateMany(
                Filters.lt("profile.birth_year", year),
                Updates.inc("profile.birth_year", increment)
        );
    }
}
