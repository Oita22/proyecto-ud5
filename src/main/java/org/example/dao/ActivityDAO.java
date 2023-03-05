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

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;


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
        return activityCollection.find(eq("_id", id)).first();
    }


    public void update(Activity activity) {
        UpdateResult result = activityCollection.replaceOne(eq("_id", activity.getId()), activity);
        if (result.getModifiedCount() == 0) {
            throw new RuntimeException("UPDATE: No activity found with id " + activity.getId());
        }
    }

    public void delete(ObjectId id) {
        DeleteResult result = activityCollection.deleteOne(eq("_id", id));
        if (result.getDeletedCount() == 0) {
            throw new RuntimeException("DELETE: No activity found with id " + id);
        }
    }

    public void close() {
        mongoClient.close();
    }

    // CONSULTAS


    /**
     * Consulta empleando filtros.
     * Búsqueda de una Actividad por su título
     *
     * @param tittle Título
     * @return Activity. La primera que encuentra que corresponde con el título pasado
     */
    public Activity findByTittle(String tittle) {
        return activityCollection.find(eq("tittle", tittle)).first();
    }

    /**
     * Consulta empleando filtros.
     * Búsqueda de actividades en una fecha concreta
     *
     * @param date Fecha
     * @return FindIterable<Activity> con el resultado de la consulta
     */
    public FindIterable<Activity> findByDate(LocalDate date) {
        Bson query = eq("date", date);

        return activityCollection.find(query);
    }

    /**
     * Consulta empleando filtros.
     * Búsqueda de actividades entre 2 fechas dadas.
     *
     * @param startDate Fecha de inicio
     * @param endDate   Fecha de fin
     * @return FindIterable<Activity> con el resultado de la consulta
     */
    public FindIterable<Activity> findBetweenDates(LocalDate startDate, LocalDate endDate) {
        System.out.println(startDate + " - " + endDate);
        Bson query = and(Filters.gte("date", startDate),
                Filters.lt("date", endDate));

        return activityCollection.find(query);
    }

    /**
     * Consulta empleando filtros.
     * Búsqueda de actividades por un usuario y en un estado concreto
     *
     * @param userId ID del usuario sobre el que se realiza la consulta
     * @param finished Estado de la actividad. True - Finalizada | False - No finalizada
     * @return FindIterable<Activity> con el resultado de la consulta
     */
    public FindIterable<Activity> findByUserIdAndFinished(ObjectId userId, boolean finished) {
        return activityCollection.find(and(
                eq("finished", finished),
                eq("user", userId)
        ));
    }


}
