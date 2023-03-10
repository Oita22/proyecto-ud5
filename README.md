# Proyecto Unidad 5 - MongoDB
### Iago Oitavén Fraga

## Modelo
### Relacional
![uml](https://user-images.githubusercontent.com/94072971/224020140-ca183bc3-e10a-4bf5-b1c8-211bf007ec67.jpg)

### MongoDB
![model](https://user-images.githubusercontent.com/94072971/223995008-c828aef0-e4b6-45f9-b9dc-210920e3f1db.jpg)


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
   - **findByTittle(String tittle)** - Búsqueda de un evento por su título
   - **findByOwnerId(ObjectId userId)** - Búsqueda de los Eventos de los que es propietario un Usuario
   - **findByUserInEvents(ObjectId userId)** - Búsqueda de los Eventos en los que está inscrito un usuario
   - **findByBetweenDateAndAtLeastOneUser(LocalDate startDate, LocalDate endDate)** - Búsqueda de los Eventos comprendidos entre dos fechas y que al menos tienen un usuario inscrito
   - **findAll(boolean onlyEmpties)** - Búsqueda de todos los eventos o solo los que no tienen usuarios inscritos

## Operaciones de actualización
### 1. UserDAO
   - **updateSurnameByUsername(String username, String surname)** - Actualiza el apellido de un Usuario que busca por su nombre de usuario
   - **updateBirthYearIncreaseByYear(int year, int increment)** - Actualiza el campo profile.birth_year de todos los usuarios que ese campo sea menor del año recibido y lo aumenta en la cantidad pasada

### 2. ActivityDAO
   - **updateTittleByActivityId(ObjectId activityId, String newTittle)** - Actualiza el título de una actividad con un ID específico
   - **updateFinishedByActivityId(ObjectId activityId, boolean finished)** - Actualizar el estado de una actividad con un ID específico
   - **updateDescriptionForActivitiesOnDate(LocalDate date)** - Actualiza la descripción de todas las actividades con una fecha específica
   - **updateDateByActivityId(ObjectId activityId, LocalDate date)** - Actualiza la fecha de una actividad con un ID específico
   - **updateTimeByActivityId(ObjectId activityId, LocalTime time)** - Actualiza la hora de una actividad con un ID específico

### 3. EventDAO
   - **updateTittleAndDescriptionByEventId(ObjectId eventId, String tittle, String description)** - Actualiza el título y la descripción de un evento con un ID específico
   - **updateStateByEventDate(LocalDate date, boolean finished)** - Actualiza el estado de todos los eventos con una fecha específica
   - **updateDateByEventId(ObjectId eventId, LocalDate date)** - Actualiza la fecha del Evento del que recibe su ID por parámetro

## Operaciones de agregación _pipeline_
Comentar que como estas operaciones se realizan antes que las de actualización, los datos que quedan la en la base de datos no corresponden con los de las consultas. Está puesto en ese orden para que todas las agregaciones muestren resultados (ya que las actualizaciones son aleatorias).
Para cambiar este orden, mover las 2 siguientes líneas en la clase Main, dejando `updateOperations()` primero:
```java
aggregationPipelineOperations();    // Línea 42
updateOperations();                 // Línea 43
```
  
### 1. UserDAO
   - **getUserCountPerBirthYear()** - Obtiene la cantidad de usuarios por año de nacimiento (Agrupamiento)
   - **getUsersWithNoEvents()** - Obtiene la lista de usuarios que no tienen eventos asociados (Lookup - events)
   - **getUserCountPerRole()** - Obtiene la cantidad de usuarios por cada rol (Agrupamiento)


### 2. ActivityDAO
   - **getCountActivitiesGroupByFinished()** - Obtiene la cantidad de actividades finalizadas y no finalizadas. (Agrupamiento)
   - **getActivitiesAndTotalCountByUser()** - Obtiene las actividades de cada usuario junto con la cantidad total de actividades que ha creado. (Lookup - users)


### 3. EventDAO
   - **getCountOfEventsCreatedGroupByUserId()** - Obtiene la cantidad total de eventos creados por cada usuario. (Agrupamiento)
   - **getAverageBirthYearOfOwners()** - Obtiene el promedio de año de nacimiento de los usuarios que han creado eventos. (Lookup - users)
   - **getEventsInNextDays(long days)** - Obtiene los eventos que se realizarán en los próximos X días
   - **eventDAO.getCountUserJoinedByEvent()** - Obtiene la lista de eventos y la cantidad de usuarios que se han unido a cada uno. (Lookup - users)
   - **getFinishedEventsOrderByDesc()** - Obtiene todos los eventos acabados y los muestra en orden descendente.
