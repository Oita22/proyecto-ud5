# Proyecto Unidad 5 - MongoDB
### Iago Oitavén Fraga

## Consultas empleando filtros y proyecciones
### 1. UserDAO
   - **findByUsername(String username)** - Búsqueda de un Usuario por su nombre de usuario
   - **findByUsernameAndEnable(String username, boolean enabled)** - Búsqueda de un usuario por su nombre de usuario y estado de actividad

### 2. ActivityDAO
   - **findByTittle()** - Búsqueda de una actividad por su título
   - **findByDate(LocalDate date)** - Búsqueda de actividades en una fecha concreta
   - **findBetweenDates(LocalDate startDate, LocalDate endDate)** - Búsqueda de actividades entre dos fechas dadas
   - **findByUserIdAndFinished(ObjectId userId, boolean finished)** - Búsqueda de actividades por un usuario y en un estado concreto

### 3. EventDAO
   - **findByTittle(String tittle)** - Búsqueda de unn evento por su título
   - **findByOwnerId(ObjectId userId)** - Búsqueda de los Eventos de los que es propietario un Usuario
   - **findByUserInEvents(ObjectId userId)** - Búsqueda de los Eventos en los que está inscrito un usuario
   - **findByBetweenDateAndAtLeastOneUser(LocalDate startDate, LocalDate endDate)** - Búsqueda de los Eventos comprendidos entre dos fechas y que al menos tienen un usuario inscrito
   - **findAll(boolean onlyEmpties)** - Búsqueda de todos los eventos o solo los que no tienen usuarios inscritos

## Operaciones de actualización
### 1. UserDAO
   - updateSurnameByUsername(String username, String surname) - Actualiza el apellido de un Usuario que busca por su nombre de usuario
   - updateBirthYearIncreaseByYear(int year, int increment) - Actualiza el campo profile.birth_year de todos los usuarios que ese campo sea menor del año recibido y lo aumenta en la cantidad pasada

### 2. ActivityDAO
   - updateTittleByActivityId(ObjectId activityId, String newTittle) - Actualiza el título de una actividad con un ID específico
   - updateFinishedByActivityId(ObjectId activityId, boolean finished) - Actualizar el estado de una actividad con un ID específico
   - updateDescriptionForActivitiesOnDate(LocalDate date) - Actualiza la descripción de todas las actividades con una fecha específica
   - updateDateByActivityId(ObjectId activityId, LocalDate date) - Actualiza la fecha de una actividad con un ID específico
   - updateTimeByActivityId(ObjectId activityId, LocalTime time) - Actualiza la hora de una actividad con un ID específico

### 3. EventDAO
   - updateTittleAndDescriptionByEventId(ObjectId eventId, String tittle, String description) - Actualiza el título y la descripción de un evento con un ID específico
   - updateStateByEventDate(LocalDate date, boolean finished) - Actualiza el estado de todos los eventos con una fecha específica
   - updateDateByEventId(ObjectId eventId, LocalDate date) - Actualiza la fecha del Evento del que recibe su ID por parámetro

## Operaciones de _pipeline_
### 1. UserDAO
   - A


### 2. ActivityDAO
   - A


### 3. EventDAO
   - A