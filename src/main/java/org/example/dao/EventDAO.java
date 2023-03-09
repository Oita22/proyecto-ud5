package org.example.dao;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import com.mongodb.client.model.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.example.export.JsonExporter;
import org.example.export.SaveDirectory;
import org.example.model.Event;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Indexes.descending;

public class EventDAO {
    private static final String DB_NAME = "ud5db";
    private static final String COLLECTION_NAME = "events";

    private final MongoClient mongoClient;
    private final MongoCollection<Event> eventsCollection;
    private final MongoCollection<Document> documentCollection;

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

        documentCollection = database.getCollection(COLLECTION_NAME);
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

    public void removeAll() {
        eventsCollection.deleteMany(new Document());
    }

    public void close() {
        mongoClient.close();
    }

    public FindIterable<Document> findAllDocs() {
        return documentCollection.find();
    }

    public Event findByEventId(ObjectId eventId) {
        return eventsCollection.find(eq("_id", eventId)).first();
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
     * @param endDate   Fecha de margen de finalización
     * @return FindIterable<Event> con el resultado de la consulta
     */
    public FindIterable<Event> findByBetweenDateAndAtLeastOneUser(LocalDate startDate, LocalDate endDate) {
        return eventsCollection.find(and(
                gte("date", startDate),
                lte("date", endDate),
                exists("users", true))
        );
    }

    // ----------------------------------------------------------------------------------------------------------------

    /**
     * Actualiza el título y la descripción de un evento con un ID específico
     *
     * @param eventId     ObjectId del Event
     * @param tittle      String - Nuevo título
     * @param description String - Nueva descripción
     * @return Event asociado al ID pasado
     */
    public Event updateTittleAndDescriptionByEventId(ObjectId eventId, String tittle, String description) {
        Bson filter = eq("_id", eventId);

        Bson update = Updates.combine(
                Updates.set("tittle", tittle),
                Updates.set("description", description)
        );

        eventsCollection.updateOne(filter, update);

        return findByEventId(eventId);
    }

    /**
     * Actualiza el estado de todos los eventos con una fecha específica
     *
     * @param date     LocalDate - Fecha de los eventos para actualizar
     * @param finished boolean - Nuevo estado
     */
    public void updateStateByEventDate(LocalDate date, boolean finished) {
        Bson filter = eq("date", date);
        Bson update = Updates.set("finished", finished);

        eventsCollection.updateMany(filter, update);
    }

    /**
     * Actualiza la fecha del Evento del que recibe su ID por parámetro
     *
     * @param eventId ObjectId - ID del evento
     * @param date    LocalDate - Nueva fecha para el evento
     * @return Evento asociado al ID recibido
     */
    public Event updateDateByEventId(ObjectId eventId, LocalDate date) {
        Bson filter = eq("_id", eventId);
        Bson update = Updates.set("date", date);

        eventsCollection.updateMany(filter, update);

        return findByEventId(eventId);
    }

    // ----------------------------------------------------------------------------------------------------------------

    /**
     * Obtiene la cantidad total de eventos creados por cada usuario.
     * Agrupamiento. Con función de agregado
     */
    public void getCountOfEventsCreatedGroupByUserId() {
        AggregateIterable<Document> result = documentCollection.aggregate(
                Arrays.asList(group("$owner", sum("count", 1))));

//        for (Document doc : result) {
//            System.out.println(doc.toJson());
//        }

        JsonExporter.exportToJson(result, SaveDirectory.AGGREGATION, "cantidad-eventos-creados-por-usuario.json");
    }

    /**
     * Obtiene el promedio de edad de los usuarios que han creado eventos
     */
    public void getAverageBirthYearOfOwners() {
        AggregateIterable<Document> result = documentCollection.aggregate(
                Arrays.asList(
                        Aggregates.lookup("users", "owner", "_id", "owner_user"),
                        Aggregates.unwind("$owner_user"),
                        group(null, Accumulators.avg("avgAge", "$owner_user.profile.birth_year"))
                )
        );

        JsonExporter.exportToJson(result, SaveDirectory.AGGREGATION, "anho-nacimiento-medio-creadores-eventos.json");
    }

    /**
     * Obtiene los eventos que se realizarán en los próximos X días
     *
     * @param days int - Días hasta lo que busca por los eventos
     */
    public void getEventsInNextDays(long days) {
        AggregateIterable<Document> result = documentCollection.aggregate(Arrays.asList(
                match(and(gte("date", LocalDate.now()), lte("date", LocalDate.now().plusDays(days))))
        ));

        JsonExporter.exportToJson(result, SaveDirectory.AGGREGATION, "eventos-proximos-dias.json");
    }

    /**
     * Obtiene la lista de eventos y la cantidad de usuarios que se han unido a cada uno
     */
    public void getCountUserJoinedByEvent() {
        List<Bson> pipeline = Arrays.asList(
                new Document("$lookup",
                        new Document("from", "users")
                                .append("localField", "users")
                                .append("foreignField", "_id")
                                .append("as", "users")
                ),
                new Document("$project",
                        new Document("_id", 1)
                                .append("title", 1)
                                .append("userCount", new Document("$size", "$users"))
                )
        );

        AggregateIterable<Document> result = documentCollection.aggregate(pipeline);

        JsonExporter.exportToJson(result, SaveDirectory.AGGREGATION, "cantidad-usuario-por-evento.json");
    }

    /**
     * Obtiene todos los eventos acabados y los muestra en orden descendente.
     * * Nota: El campo 'date' se muestra así porque está en formato de milisegundos, desde el 1 de enero de 1970
     */
    public void getFinishedEventsOrderByDesc() {
        List<Bson> pipeline = Arrays.asList(
                Aggregates.match(Filters.eq("finished", true)),
                Aggregates.sort(Sorts.descending("date"))
        );

        AggregateIterable<Document> result = documentCollection.aggregate(pipeline);

        JsonExporter.exportToJson(result, SaveDirectory.AGGREGATION, "eventos-finalizados-orden-desc.json");
    }
}
