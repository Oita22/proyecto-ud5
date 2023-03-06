package org.example;

import com.mongodb.client.FindIterable;
import org.bson.types.ObjectId;
import org.example.dao.ActivityDAO;
import org.example.dao.EventDAO;
import org.example.dao.UserDAO;
import org.example.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;


public class Main {
    private static UserDAO userDAO;
    private static ActivityDAO activityDAO;
    private static EventDAO eventDAO;

    private static Random random;

    public static void main(String[] args) {
        random = new Random();

        userDAO = new UserDAO();
        eventDAO = new EventDAO();
        activityDAO = new ActivityDAO();

        filtersOperations();
        aggregationPipelineOperations();
        updateOperations();
    }

    /**
     * Consultas usando filtros y proyecciones
     */
    private static void filtersOperations() {
        System.out.println("-".repeat(200));
        System.out.println("\t".repeat(10) + "- CONSULTAS -\n\n");

        // Búsqueda de un Usuario por su nombre de usuario
        System.out.println("\t\tBúsqueda de un Usuario por su nombre de usuario: Oita");
        System.out.println(userDAO.findByUsername("Oita"));


        // Búsqueda de un Usuario por su username y estado de actividad
        System.out.println("\n".repeat(2) + "\t\tBúsqueda de un Usuario por nombre de usuario y estado: Oita, enable: true");
        System.out.println(userDAO.findByUsernameAndEnable("Oita", true));






        // Búsqueda de actividades por usuario y estado
        System.out.println("\n".repeat(4) + "\t\tBúsqueda de actividades por usuario y estado: Oita, finished: true");
        FindIterable<Activity> activities = activityDAO.findByUserIdAndFinished(
                userDAO.findByUsername("Oita").getId(),
                true
        );
        for (Activity activity : activities)
            System.out.println(activity);


        // Búsqueda de Actividades por su título
        System.out.println("\n".repeat(2) + "\t\tBúsqueda de Actividades por su título: Activity1203229766");
        System.out.println(activityDAO.findByTittle("Activity1203229766"));


        // Búsqueda de actividades en una fecha concreta
        System.out.println("\n".repeat(2) + "\t\tBúsqueda de actividades en una fecha concreta: 2023-3-4");
        FindIterable<Activity> activities1 = activityDAO.findByDate(LocalDate.of(2023, 3, 4));
        for (Activity activity : activities1)
            System.out.println(activity);


        // Búsqueda de actividades entre dos fechas dadas
        System.out.println("\n".repeat(2) + "\t\tBúsqueda de actividades entre dos fechas dadas: 2021-3-4 / 2023-3-5");
        FindIterable<Activity> activities2 = activityDAO.findBetweenDates(
                LocalDate.of(2021, 3, 4),
                LocalDate.of(2023, 3, 5));
        for (Activity activity : activities2)
            System.out.println(activity);






        // Búsqueda de un evento por su título
        System.out.println("\n".repeat(4) + "\t\tBúsqueda de un evento por su título: Event172892226");
        System.out.println(eventDAO.findByTittle("Event172892226"));


        // Búsqueda de eventos por su propietario
        System.out.println("\n".repeat(2) + "\t\tBúsqueda de eventos por su propietario: User1629524261");
        FindIterable<Event> events = eventDAO.findByOwnerId(userDAO.findByUsername("User1629524261").getId());
        for (Event event : events)
            System.out.println(event);


        // Búsqueda de los eventos en los que está inscrito un usuario
        System.out.println("\n".repeat(2) + "\t\tBúsqueda de los eventos en los que está inscrito un usuario: Oita");
        FindIterable<Event> events1 = eventDAO.findByUserInEvents(userDAO.findByUsername("Oita").getId());
        for (Event event : events1)
            System.out.println(event);


        // Búsqueda de los Eventos comprendidos entre dos fechas y que al menos tienen un usuario inscrito
        System.out.println("\n".repeat(2) + "\t\tBúsqueda de los Eventos comprendidos entre dos fechas y que al " +
                "menos tienen un usuario inscrito: 2022-1-1 / <hoy>");
        FindIterable<Event> events2 = eventDAO.findByBetweenDateAndAtLeastOneUser(LocalDate.of(2022, 1, 1), LocalDate.now());
        for (Event event : events2)
            System.out.println(event);


        // Búsqueda de los eventos en función de si tienen o no Usuarios inscritos
        System.out.println("\n".repeat(2) + "\t\tBúsqueda de los eventos en función de si tienen " +
                "o no Usuarios inscritos. Este caso: No tengan usuarios");
        FindIterable<Event> events3 = eventDAO.findAll(true);
        for (Event event : events3)
            System.out.println(event);
    }

    /**
     * Operaciones de actualización
     */
    private static void updateOperations() {
        System.out.println("-".repeat(200));
        System.out.println("\t".repeat(10) + "- ACTUALIZACIONES -\n\n");

        // Actualiza el título de una actividad con un ID específico:
        System.out.println("\t\tActualiza el título de una actividad con un ID específico: 6404cd79d3aee33e0f33fe6f");
        System.out.println(activityDAO.findByActivityId(new ObjectId("6404cd79d3aee33e0f33fe6f")));
        System.out.println(activityDAO.updateTittleByActivityId(
                new ObjectId("6404cd79d3aee33e0f33fe6f"),
                "Nuevo Título mod" + random.nextInt()));


        // Actualiza el estado de una actividad con un ID específico
        System.out.println("\n".repeat(2) + "\t\tActualiza el estado de una actividad (finished) con un ID específico: 6404cd79d3aee33e0f33fe6f");
        Activity activity = activityDAO.findByActivityId(new ObjectId("6404cd79d3aee33e0f33fe6f"));
        System.out.println(activity);
        System.out.println(activityDAO.updateFinishedByActivityId(activity.getId(), !activity.isFinished()));


        // Actualiza la descripción de todas las actividades con una fecha específica
        System.out.println("\n".repeat(2) + "\t\tActualizar la descripción de todas las actividades con una fecha específica: 2021-3-6");
        printFindIterable(activityDAO.findByDate(LocalDate.of(2021, 3, 6)));
        activityDAO.updateDescriptionForActivitiesOnDate(LocalDate.of(2021, 3, 6), ("Nueva descripción mod" + random.nextInt()));
        printFindIterable(activityDAO.findByDate(LocalDate.of(2021, 3, 6)));


        // Actualiza la fecha de una actividad con un ID específico
        System.out.println("\n".repeat(2) + "\t\tActualiza la fecha de una actividad con un ID específico: 6404cd79d3aee33e0f33fe6f");
        System.out.println(activityDAO.findByActivityId(new ObjectId("6404cd79d3aee33e0f33fe6f")));
        System.out.println(activityDAO.updateDateByActivityId(new ObjectId("6404cd79d3aee33e0f33fe6f"), LocalDate.of(2002, 2, 2)));


        // Actualiza la hora de una actividad con un ID específico
        DurationTime[] durationTimes = {DurationTime.SHORT, DurationTime.MEDIUM, DurationTime.LONG};
        System.out.println("\n".repeat(4) + "\t\tActualizala hora de una actividad con un ID específico: 6404cd79d3aee33e0f33fe6e");
        System.out.println(activityDAO.findByActivityId(new ObjectId("6404cd79d3aee33e0f33fe6e")));
        System.out.println(activityDAO.updateTimeByActivityId(new ObjectId("6404cd79d3aee33e0f33fe6e"), LocalTime.now()));






        // Actualiza el título y la descripción de un evento con un ID específico
        System.out.println("\n".repeat(2) + "\t\tActualiza el título y la descripción de un evento con un ID específico: 6403c1cb14c64723cfdfad8d");
        System.out.println(eventDAO.findByEventId(new ObjectId("6403c1cb14c64723cfdfad8d")));
        System.out.println(eventDAO.updateTittleAndDescriptionByEventId(new ObjectId("6403c1cb14c64723cfdfad8d"),
                "Nuevo título mod" + random.nextInt(), "Nueva descripción mod" + random.nextInt()));


        // Actualiza el estado de todos los eventos con una fecha específica
        System.out.println("\n".repeat(2) + "\t\tActualiza el estado de todos los eventos con una fecha específica: 2023-3-4");
        eventDAO.updateStateByEventDate(LocalDate.of(2023, 3, 4), random.nextBoolean());
        System.out.println("VER EN LA DB > {date: ISODate('2023-03-04T00:00:00.000+00:00')}");


        // Actualiza la fecha del Evento del que recibe su ID por parámetro
        System.out.println("\n".repeat(4) + "\t\tActualiza la fecha del Evento del que recibe su ID por parámetro: 6403b7e0364f263a103349fb");
        System.out.println(eventDAO.findByEventId(new ObjectId("6403b7e0364f263a103349fb")));
        System.out.println(eventDAO.updateDateByEventId(new ObjectId("6403b7e0364f263a103349fb"), LocalDate.now()));






        // Actualiza el apellido de un Usuario que busca por su nombre de usuario
        System.out.println("\n".repeat(2) + "\t\tActualiza el apellido de un Usuario que busca por su nombre de usuario: User533551192");
        System.out.println(userDAO.findByUsername("User533551192"));
        System.out.println(userDAO.updateSurnameByUsername("User533551192", "Apellido mod" + random.nextInt()));


        // Actualiza el campo profile.birth_year de todos los usuarios que ese campo sea menor del año recibido y lo aumenta en la cantidad pasada
        System.out.println("\n".repeat(2) + "\t\tActualiza el campo profile.birth_year de todos los usuarios que ese campo sea menor " +
                "del año recibido y lo aumenta en la cantidad pasada: 1950, incremento=1");
        userDAO.updateBirthYearIncreaseByYear(1950, 1);
        System.out.println("VER EN LA DB > {\"profile.birth_year\": {$lt: 1950}}");
    }


    /**
     * Operaciones de agregación pipeline
     */
    private static void aggregationPipelineOperations() {
        eventDAO.getCountOfEventsCreatedGroupByUserId(); // 1 - Agrupación
        activityDAO.getCountActivitiesGroupByFinished(); // 2 - Agrupación
        userDAO.getUserCountPerBirthYear(); // 3 - Agrupación
        userDAO.getUserCountPerRole(); // 4 - Agrupación


        eventDAO.getAverageBirthYearOfOwners(); // 1 - Lookup
        activityDAO.getActivitiesAndTotalCountByUser(); // 2 - Lookup
        eventDAO.getCountUserJoinedByEvent(); // 3 - Lookup
        userDAO.getUsersWithNoEvents(); // 4 - Lookup
          //eventDAO.getEventAndUserListOrderByAsc(); // 5 - lookup


        eventDAO.getEventsInNextDays(30); // 1
        eventDAO.getFinishedEventsOrderByDesc(); // 2

    }


    private static void printFindIterable(FindIterable findIterable) {
        printFindIterable(findIterable, null);
    }

    private static void printFindIterable(FindIterable findIterable, String msg) {
        if (msg != null && !msg.equals(""))
            System.out.println("\t\t" + msg);

        for (Object o : findIterable)
            System.out.println(o);
    }
}