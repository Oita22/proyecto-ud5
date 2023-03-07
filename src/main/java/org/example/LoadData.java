package org.example;

import org.bson.types.ObjectId;
import org.example.dao.ActivityDAO;
import org.example.dao.EventDAO;
import org.example.dao.UserDAO;
import org.example.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

public class LoadData {
    private static UserDAO userDAO;
    private static ActivityDAO activityDAO;
    private static EventDAO eventDAO;

    public static void loadData() {
        userDAO = new UserDAO();
        eventDAO = new EventDAO();
        activityDAO = new ActivityDAO();

        users();
        activities();
        events();
    }

    private static void users() {
        userDAO.create(new User(new ObjectId("6403bc482a489b7f6489409a"), "Oita", "oita@email.com", true, new Profile(new ObjectId("6403bc482a489b7f6489409b"), "Iago", "Oitavén", "123456789", 2000),Arrays.asList(new Role(RoleType.USER), new Role(RoleType.MODERATOR)),Arrays.asList(new ObjectId("6403b7fbb9d3c76271a93715"), new ObjectId("6403b7fbb9d3c76271a93716"), new ObjectId("6403bc56a62cd87437d1c5d8"), new ObjectId("6403c7ea1340177d1a800fc0"), new ObjectId("6403c8a8987c3f07ae83544a")),Arrays.asList(new ObjectId("6403b7e0364f263a103349fa"), new ObjectId("6403b7e0364f263a103349fb"), new ObjectId("6403c14bb01432717759aa76"), new ObjectId("6403c1cb14c64723cfdfad8d"), new ObjectId("6403c20cc9473f73d39960b5"), new ObjectId("6403c24a3b98fe3543ae23a3"), new ObjectId("64040737d89f423df1b21d0a"), new ObjectId("64040737d89f423df1b21d0b"))));
        userDAO.create(new User(new ObjectId("6403c029001813739981d98e"), "Oita2", "oita2@email.com", false, new Profile(new ObjectId("6403c029001813739981d98f"), "Iago 2", "Oitavén", "123456789", 2000),Arrays.asList(new Role(RoleType.USER), new Role(RoleType.MODERATOR)),Arrays.asList(),Arrays.asList(new ObjectId("6403b7e0364f263a103349fa"), new ObjectId("6403b7e0364f263a103349fb"), new ObjectId("6403c14bb01432717759aa76"), new ObjectId("6403c1cb14c64723cfdfad8d"), new ObjectId("6403c20cc9473f73d39960b5"), new ObjectId("6403c24a3b98fe3543ae23a3"), new ObjectId("64040737d89f423df1b21d0a"), new ObjectId("64040737d89f423df1b21d0b"), new ObjectId("640419518e457c7557bce16e"))));
        userDAO.create(new User(new ObjectId("640419518e457c7557bce16a"), "User1629524261", "email-96750632@email.com", true, new Profile(new ObjectId("640419518e457c7557bce16b"), "User1629524261", "1629524261", "159482630", 1998),Arrays.asList(new Role(RoleType.USER)),Arrays.asList(),Arrays.asList(new ObjectId("640419518e457c7557bce16e"), new ObjectId("6403b7e0364f263a103349fa"), new ObjectId("6403b7e0364f263a103349fb"), new ObjectId("6403c14bb01432717759aa76"), new ObjectId("6403c1cb14c64723cfdfad8d"), new ObjectId("6403c20cc9473f73d39960b5"), new ObjectId("6403c24a3b98fe3543ae23a3"), new ObjectId("64040737d89f423df1b21d0a"), new ObjectId("64040737d89f423df1b21d0b"))));
        userDAO.create(new User(new ObjectId("6404cd79d3aee33e0f33fe57"), "User1497185538", "email1497185538@email.com", true, new Profile(new ObjectId("6404cd79d3aee33e0f33fe58"), "User1497185538", "1497185538", "398345372", 1918),Arrays.asList(new Role(RoleType.USER)),Arrays.asList(new ObjectId("6404cd79d3aee33e0f33fe59"), new ObjectId("6404cd79d3aee33e0f33fe5a"), new ObjectId("6404cd79d3aee33e0f33fe5b")),Arrays.asList(new ObjectId("6403b7e0364f263a103349fa"), new ObjectId("6403b7e0364f263a103349fb"), new ObjectId("6403c14bb01432717759aa76"), new ObjectId("6403c1cb14c64723cfdfad8d"), new ObjectId("6403c20cc9473f73d39960b5"), new ObjectId("6403c24a3b98fe3543ae23a3"), new ObjectId("64040737d89f423df1b21d0a"), new ObjectId("64040737d89f423df1b21d0b"), new ObjectId("640419518e457c7557bce16e"))));
        userDAO.create(new User(new ObjectId("6404cd79d3aee33e0f33fe61"), "User-648965095", "email-648965095@email.com", true, new Profile(new ObjectId("6404cd79d3aee33e0f33fe62"), "User-648965095", "-648965095", "213179365", 2020),Arrays.asList(new Role(RoleType.USER), new Role(RoleType.ADMIN)),Arrays.asList(new ObjectId("6404cd79d3aee33e0f33fe63"), new ObjectId("6404cd79d3aee33e0f33fe64"), new ObjectId("6404cd79d3aee33e0f33fe65")),Arrays.asList(new ObjectId("6403b7e0364f263a103349fb"), new ObjectId("6403b7e0364f263a103349fa"), new ObjectId("6403c14bb01432717759aa76"), new ObjectId("6403c1cb14c64723cfdfad8d"), new ObjectId("6403c20cc9473f73d39960b5"), new ObjectId("6403c24a3b98fe3543ae23a3"), new ObjectId("64040737d89f423df1b21d0a"), new ObjectId("64040737d89f423df1b21d0b"), new ObjectId("640419518e457c7557bce16e"))));
        userDAO.create(new User(new ObjectId("6404cd79d3aee33e0f33fe6a"), "User533551192", "email533551192@email.com", true, new Profile(new ObjectId("6404cd79d3aee33e0f33fe6b"), "User533551192", "Apellido mod-30269579", "481507216", 2004),Arrays.asList(new Role(RoleType.USER)),Arrays.asList(new ObjectId("6404cd79d3aee33e0f33fe6c"), new ObjectId("6404cd79d3aee33e0f33fe6d"), new ObjectId("6404cd79d3aee33e0f33fe6e")),Arrays.asList(new ObjectId("6403b7e0364f263a103349fa"), new ObjectId("640419518e457c7557bce16e"), new ObjectId("6403b7e0364f263a103349fb"), new ObjectId("6403c14bb01432717759aa76"), new ObjectId("6403c1cb14c64723cfdfad8d"), new ObjectId("6403c20cc9473f73d39960b5"), new ObjectId("6403c24a3b98fe3543ae23a3"), new ObjectId("64040737d89f423df1b21d0a"), new ObjectId("64040737d89f423df1b21d0b"))));
        userDAO.create(new User(new ObjectId("64059c4f9e66285fa7bb9e5f"), "User1070794892", "user1070794892@email.com", false, new Profile(new ObjectId("64059c4f9e66285fa7bb9e60"), "User", "1070794892", "99058154", 1971),Arrays.asList(new Role(RoleType.USER), new Role(RoleType.ADMIN))));
    }

    private static void activities() {
        activityDAO.create(new Activity(new ObjectId("6403b7fbb9d3c76271a93715"), "Tittle", "Description", false, LocalDate.of(2021, 03, 04), LocalTime.of(22, 28, 0), new ObjectId("6403bc482a489b7f6489409a")));
        activityDAO.create(new Activity(new ObjectId("6403b7fbb9d3c76271a93716"), "Title without Description and Time", "Nueva descripción mod394472134", false, LocalDate.of(2021, 03, 06), new ObjectId("6403bc482a489b7f6489409a")));
        activityDAO.create(new Activity(new ObjectId("6403bc56a62cd87437d1c5d8"), "New Tittle", "Nueva descripción mod394472134", false, LocalDate.of(2021, 03, 06), new ObjectId("6403bc482a489b7f6489409a")));
        activityDAO.create(new Activity(new ObjectId("6403c7ea1340177d1a800fc0"), "Tittle524703813", "Description", true, LocalDate.of(2023, 03, 04), LocalTime.of(23, 36, 0), new ObjectId("6403bc482a489b7f6489409a")));
        activityDAO.create(new Activity(new ObjectId("6403c8a8987c3f07ae83544a"), "Tittle1044260530", "Description", true, LocalDate.of(2023, 03, 04), LocalTime.of(23, 39, 0), new ObjectId("6403bc482a489b7f6489409a")));
        activityDAO.create(new Activity(new ObjectId("64040454232fb46890c6ea7b"), "Tittle", "Description", false, LocalDate.of(2023, 03, 05), LocalTime.of(03, 54, 0), DurationTime.MEDIUM, new ObjectId("6403bc482a489b7f6489409a")));
        activityDAO.create(new Activity(new ObjectId("640419518e457c7557bce16d"), "Activity Final Model", "Description 1469804349 - Modificada", false, LocalDate.of(2023, 03, 05), LocalTime.of(05, 23, 0), DurationTime.MEDIUM, new ObjectId("640419518e457c7557bce16a")));
        activityDAO.create(new Activity(new ObjectId("6404cd79d3aee33e0f33fe59"), "Activity2087432780", "Description", false, LocalDate.of(2023, 03, 05), LocalTime.of(18, 12, 0), DurationTime.LONG, new ObjectId("6404cd79d3aee33e0f33fe57")));
        activityDAO.create(new Activity(new ObjectId("6404cd79d3aee33e0f33fe5a"), "Activity1203229766", "Description", false, LocalDate.of(2023, 03, 05), LocalTime.of(18, 12, 0), DurationTime.MEDIUM, new ObjectId("6404cd79d3aee33e0f33fe57")));
        activityDAO.create(new Activity(new ObjectId("6404cd79d3aee33e0f33fe5b"), "Activity-74956027", "Description", false, LocalDate.of(2023, 03, 05), LocalTime.of(18, 12, 0), DurationTime.LONG, new ObjectId("6404cd79d3aee33e0f33fe57")));
        activityDAO.create(new Activity(new ObjectId("6404cd79d3aee33e0f33fe5c"), "Activity1459148647", "Description", false, LocalDate.of(2023, 03, 05), LocalTime.of(18, 12, 0), DurationTime.MEDIUM, new ObjectId("6404cd79d3aee33e0f33fe57")));
        activityDAO.create(new Activity(new ObjectId("6404cd79d3aee33e0f33fe63"), "Activity388791618", "Description", false, LocalDate.of(2023, 03, 05), LocalTime.of(18, 12, 0), DurationTime.MEDIUM, new ObjectId("6404cd79d3aee33e0f33fe61")));
        activityDAO.create(new Activity(new ObjectId("6404cd79d3aee33e0f33fe64"), "Activity-231122286", "Description", false, LocalDate.of(2023, 03, 05), LocalTime.of(18, 12, 0), DurationTime.LONG, new ObjectId("6404cd79d3aee33e0f33fe61")));
        activityDAO.create(new Activity(new ObjectId("6404cd79d3aee33e0f33fe65"), "Activity-1038542804", "Description", false, LocalDate.of(2023, 03, 05), LocalTime.of(18, 12, 0), DurationTime.MEDIUM, new ObjectId("6404cd79d3aee33e0f33fe61")));
        activityDAO.create(new Activity(new ObjectId("6404cd79d3aee33e0f33fe66"), "Activity450238987", "Description", false, LocalDate.of(2023, 03, 05), LocalTime.of(18, 12, 0), DurationTime.SHORT, new ObjectId("6404cd79d3aee33e0f33fe61")));
        activityDAO.create(new Activity(new ObjectId("6404cd79d3aee33e0f33fe6c"), "Activity508638177", "Description", false, LocalDate.of(2023, 03, 05), LocalTime.of(18, 12, 0), DurationTime.MEDIUM, new ObjectId("6404cd79d3aee33e0f33fe6a")));
        activityDAO.create(new Activity(new ObjectId("6404cd79d3aee33e0f33fe6d"), "Activity-577546260", "Description", false, LocalDate.of(2023, 03, 05), LocalTime.of(18, 12, 0), DurationTime.SHORT, new ObjectId("6404cd79d3aee33e0f33fe6a")));
        activityDAO.create(new Activity(new ObjectId("6404cd79d3aee33e0f33fe6e"), "Activity-1517983244", "Description", false, LocalDate.of(2023, 03, 05), LocalTime.of(21, 00, 0), DurationTime.SHORT, new ObjectId("6404cd79d3aee33e0f33fe6a")));
        activityDAO.create(new Activity(new ObjectId("6404cd79d3aee33e0f33fe6f"), "Nuevo Título mod-288639170", "Description", false, LocalDate.of(2000, 03, 04), LocalTime.of(18, 12, 0), DurationTime.LONG, new ObjectId("6404cd79d3aee33e0f33fe6a")));
    }

    private static void events() {
        eventDAO.create(new Event(new ObjectId("6403b7e0364f263a103349fa"), "Tittle", "Description", false, LocalDate.of(2023, 03, 19), Arrays.asList(new ObjectId("6403bc482a489b7f6489409a"), new ObjectId("6403c029001813739981d98e"), new ObjectId("6404cd79d3aee33e0f33fe6a"))));
        eventDAO.create(new Event(new ObjectId("6403b7e0364f263a103349fb"), "Title without Description and Time", "null", false, LocalDate.of(2023, 03, 05), Arrays.asList(new ObjectId("6403bc482a489b7f6489409a"), new ObjectId("6404cd79d3aee33e0f33fe61"))));
        eventDAO.create(new Event(new ObjectId("6403c14bb01432717759aa76"), "Tittle-594294049", "Description", true, LocalDate.of(2023, 03, 04), Arrays.asList(new ObjectId("6403bc482a489b7f6489409a"), new ObjectId("6403c029001813739981d98e"), new ObjectId("640419518e457c7557bce16a"), new ObjectId("6404cd79d3aee33e0f33fe57"))));
        eventDAO.create(new Event(new ObjectId("6403c1cb14c64723cfdfad8d"), "Nuevo título mod1641100342", "Nueva descripción mod1565279041", false, LocalDate.of(2023, 03, 18), Arrays.asList(new ObjectId("6403bc482a489b7f6489409a"), new ObjectId("6403c029001813739981d98e"), new ObjectId("6404cd79d3aee33e0f33fe61"))));
        eventDAO.create(new Event(new ObjectId("6403c20cc9473f73d39960b5"), "Tittle426801947", "Description", false, LocalDate.of(2023, 03, 04), Arrays.asList(new ObjectId("6403bc482a489b7f6489409a"), new ObjectId("6403c029001813739981d98e"), new ObjectId("640419518e457c7557bce16a"), new ObjectId("6404cd79d3aee33e0f33fe57"), new ObjectId("6404cd79d3aee33e0f33fe61"))));
        eventDAO.create(new Event(new ObjectId("6403c24a3b98fe3543ae23a3"), "Tittle1059650256", "Description", false, LocalDate.of(2023, 03, 04)));
        eventDAO.create(new Event(new ObjectId("64040737d89f423df1b21d0a"), "Tittle Owner", "Description", false, LocalDate.of(2023, 03, 05), new ObjectId("6403bc482a489b7f6489409a"), Arrays.asList(new ObjectId("6403bc482a489b7f6489409a"), new ObjectId("6403c029001813739981d98e"), new ObjectId("640419518e457c7557bce16a"), new ObjectId("6404cd79d3aee33e0f33fe57"))));
        eventDAO.create(new Event(new ObjectId("64040737d89f423df1b21d0b"), "Title - Owner - without Description and Time", "null", true, LocalDate.of(2023, 03, 05), new ObjectId("6403bc482a489b7f6489409a"), Arrays.asList(new ObjectId("6403bc482a489b7f6489409a"), new ObjectId("6403c029001813739981d98e"), new ObjectId("640419518e457c7557bce16a"), new ObjectId("6404cd79d3aee33e0f33fe57"), new ObjectId("6404cd79d3aee33e0f33fe61"))));
        eventDAO.create(new Event(new ObjectId("640419518e457c7557bce16e"), "Event Final Model", "Description -486839229", true, LocalDate.of(2023, 03, 05), new ObjectId("640419518e457c7557bce16a"), Arrays.asList(new ObjectId("640419518e457c7557bce16a"), new ObjectId("6404cd79d3aee33e0f33fe6a"), new ObjectId("6403c029001813739981d98e"), new ObjectId("6404cd79d3aee33e0f33fe57"))));
        eventDAO.create(new Event(new ObjectId("6404cd79d3aee33e0f33fe5d"), "Event172892226", "Description", false, LocalDate.of(2023, 03, 05), new ObjectId("6404cd79d3aee33e0f33fe57"), Arrays.asList(new ObjectId("6403bc482a489b7f6489409a"))));

    }
}
