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

## Operaciones de actualización

## Operaciones de _pipeline_
