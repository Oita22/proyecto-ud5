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
import org.example.model.Activity;
import org.example.model.User;

import java.time.*;
import java.util.Arrays;

import static com.mongodb.client.model.Accumulators.push;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;


public class ActivityDAO {
    private static final String DB_NAME = "ud5db";
    private static final String COLLECTION_NAME = "activities";

    private final MongoClient mongoClient;
    private final MongoCollection<Activity> activityCollection;
    private final MongoCollection<Document> documentCollection;

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
        documentCollection = database.getCollection(COLLECTION_NAME);
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

    public void removeAll() {
        activityCollection.deleteMany(new Document());
    }

    public void close() {
        mongoClient.close();
    }

    public FindIterable<Document> findAllDocs() {
        return documentCollection.find();
    }

    // CONSULTAS

    /**
     * Busca todas las actividades
     *
     * @return FindIterable<Activity> con el resultado de la consulta
     */
    public FindIterable<Activity> findAll() {
        return activityCollection.find();
    }

    /**
     * Busca la Activity que tiene como ID el recibido por par??metros
     *
     * @param activityId ID de la actividad
     * @return Activity
     */
    public Activity findByActivityId(ObjectId activityId) {
        return activityCollection.find(eq("_id", activityId)).first();
    }

    /**
     * Consulta empleando filtros.
     * B??squeda de una Actividad por su t??tulo
     *
     * @param tittle T??tulo
     * @return Activity. La primera que encuentra que corresponde con el t??tulo pasado
     */
    public Activity findByTittle(String tittle) {
        return activityCollection.find(eq("tittle", tittle)).first();
    }

    /**
     * Consulta empleando filtros.
     * B??squeda de actividades en una fecha concreta
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
     * B??squeda de actividades entre 2 fechas dadas.
     *
     * @param startDate Fecha de inicio
     * @param endDate   Fecha de fin
     * @return FindIterable<Activity> con el resultado de la consulta
     */
    public FindIterable<Activity> findBetweenDates(LocalDate startDate, LocalDate endDate) {
        Bson query = and(Filters.gte("date", startDate),
                Filters.lt("date", endDate));

        return activityCollection.find(query);
    }

    /**
     * Consulta empleando filtros.
     * B??squeda de actividades por un usuario y en un estado concreto
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

    // ----------------------------------------------------------------------------------------------------------------

    /**
     * Actualiza el t??tulo de una actividad con un ID espec??fico
     *
     * @param activityId ID de la Activity
     * @param newTittle Nuevo t??tulo para actualizar
     * @return Activity del ID recibido
     */
    public Activity updateTittleByActivityId(ObjectId activityId, String newTittle) {
        Bson filter = Filters.eq("_id", activityId);
        Bson update = Updates.set("tittle", newTittle);

        activityCollection.updateOne(filter, update);

        return findByActivityId(activityId);
    }

    /**
     * Actualizar el estado de una actividad con un ID espec??fico
     *
     * @param activityId ObjectId - ID de la Activity
     * @param finished boolean - Estado al que se va a actualizar
     * @return Activity del ID recibido
     */
    public Activity updateFinishedByActivityId(ObjectId activityId, boolean finished) {
        Bson filter = Filters.eq("_id", activityId);
        Bson update = Updates.set("finished", finished);

        activityCollection.updateOne(filter, update);

        return findByActivityId(activityId);
    }

    /**
     * Actualiza la descripci??n de todas las actividades con una fecha espec??fica
     *
     * @param date LocalDate - Fecha de las actividades a las que se le actualizar?? la descripci??n
     */
    public void updateDescriptionForActivitiesOnDate(LocalDate date, String description) {
        Bson filter = Filters.eq("date", date);
        Bson update = Updates.set("description", description);

        activityCollection.updateMany(filter, update);
    }

    /**
     * Actualiza la fecha de una actividad con un ID espec??fico
     *
     * @param activityId ObjectId - ID de la actividad
     * @param date LocalDate - Fecha para actualizar
     * @return Activity asociada al ID recibido
     */
    public Activity updateDateByActivityId(ObjectId activityId, LocalDate date) {
        Bson filter = Filters.eq("_id", activityId);
        Bson update = Updates.set("date", date);

        activityCollection.updateOne(filter, update);

        return findByActivityId(activityId);
    }

    /**
     * Actualiza la hora de una actividad con un ID espec??fico
     *
     * @param activityId ObjectId - ID de la actividad
     * @param time DurationTime - Tipo de duraci??n de tiempo de la actividad
     * @return Activity asociada al ID
     */
    public Activity updateTimeByActivityId(ObjectId activityId, LocalTime time) {
        activityCollection.updateMany(
                Filters.eq("_id", activityId),
                Updates.set("time", time)
        );

        return findByActivityId(activityId);
    }

    // ----------------------------------------------------------------------------------------------------------------

    /**
     * Obtiene la cantidad de actividades finalizadas y no finalizadas.
     * Agrupamiento. Con funci??n de agregado
     */
    public void getCountActivitiesGroupByFinished() {
        AggregateIterable<Document> result = documentCollection.aggregate(Arrays.asList(
                Aggregates.group("$finished", Accumulators.sum("count", 1))));

        JsonExporter.exportToJson(result, SaveDirectory.AGGREGATION, "cantidad-actividades-por-finished.json");
    }

    /**
     * Obtiene las actividades de cada usuario junto con la cantidad total de actividades que ha creado.
     */
    public void getActivitiesAndTotalCountByUser() {
        AggregateIterable<Document> result = documentCollection.aggregate(
                Arrays.asList(
                        Aggregates.group("$user",
                                Accumulators.sum("count", 1),
                                push("activities", "$$ROOT")
                        ),
                        Aggregates.lookup("users", "_id", "_id", "user"),
                        Aggregates.unwind("$user"),
                        Aggregates.project(
                                Projections.fields(
                                        Projections.excludeId(),
                                        Projections.computed("user", "$user.username"),
                                        Projections.computed("totalActivities", "$count"),
                                        Projections.computed("activities", "$activities")
                                )
                        )
                )
        );

        JsonExporter.exportToJson(result, SaveDirectory.AGGREGATION, "actividades-y-total-por-usuario.json");
    }
}
