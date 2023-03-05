package org.example.dao;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.example.model.Event;

import java.time.LocalDate;
import java.util.Arrays;

import static com.mongodb.client.model.Filters.*;

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

    public Event read(ObjectId id) {
        return eventsCollection.find(eq("_id", id)).first();
    }

    public void update(Event event) {
        UpdateResult result = eventsCollection.replaceOne(eq("_id", event.getId()), event);
        if (result.getModifiedCount() == 0) {
            throw new RuntimeException("UPDATE: No event found with id " + event.getId());
        }
    }

    public void delete(ObjectId id) {
        DeleteResult result = eventsCollection.deleteOne(eq("_id", id));
        if (result.getDeletedCount() == 0) {
            throw new RuntimeException("DELETE: No event found with id " + id);
        }
    }

    public void close() {
        mongoClient.close();
    }

    /**
     * Búsqueda de todos los Eventos o solo los que no tienen usuarios inscritos
     *
     * @return FindIterable<Event> con todos los eventos
     */
    public FindIterable<Event> findAll(boolean onlyEmpties) {
        if (onlyEmpties)
            return eventsCollection.find(exists("users", false));
        return eventsCollection.find();
    }

    /**
     * Consulta empleando filtros.
     * Búsqueda de un Evento por su título
     *
     * @param tittle Título
     * @return Event. El primero que encuentra que corresponde con el título pasado
     */
    public Event findByTittle(String tittle) {
        return eventsCollection.find(eq("tittle", tittle)).first();
    }

    /**
     * Consulta empleando filtros.
     * Búsqueda de los Eventos de los que es propietario un Usuario
     *
     * @param userId ID del usuario
     * @return FindIterable<Event> con el resultado de la consulta
     */
    public FindIterable<Event> findByOwnerId(ObjectId userId) {
        return eventsCollection.find(eq("owner", userId));
    }

    /**
     * Consulta empleando filtros.
     * Búsqueda de los Eventos en los que está inscrito un usuario
     *
     * @param userId ID del usuario
     * @return FindIterable<Event> con el resultado de la consulta
     */
    public FindIterable<Event> findByUserInEvents(ObjectId userId) {
        return eventsCollection.find(in("users", userId));
    }

    /**
     * Consulta empleando filtros.
     * Búsqueda de los Eventos comprendidos entre dos fechas y que al menos tienen un usuario inscrito
     *
     * @param startDate Fecha del margen de inicio
     * @param endDate Fecha de margen de finalización
     * @return FindIterable<Event> con el resultado de la consulta
     */
    public FindIterable<Event> findByBetweenDateAndAtLeastOneUser(LocalDate startDate, LocalDate endDate) {
        return eventsCollection.find(and(
                gte("date", startDate),
                lte("date", endDate),
                exists("users", true))
        );
    }
}
