# spring-boot-redis-rest-demo

Spring Boot REST API and Redis as database.

## TODO
 - Add Unit Tests

## Rest Controller
The class `EmployeeController.java` contains all endpoint.

**AVAILABLE ENDPOINTS**

| method            | resource          | description                                                                                   |
|:------------------|:------------------|:----------------------------------------------------------------------------------------------|
| `GET`			| `/employees`		| get the collection of employees -> 200(OK)														|
| `GET`			| `/employees/:id`	| get a employee by Id -> 200(OK), 400(wrong id format), 404(no employee with such id)					|
| `POST`			| `/employees`		| creates a employee in the DB -> 201(created)														|
| `PUT`			| `/employees/:id`	| updates a employee in the DB -> 204(updated), 400(wrong id format), 404(no employee with such id)		|
| `DELETE`		| `/employees/:id`	| deletes a employee from the DB -> 204(deleted), 400(wrong id format), 404(no employee with such id)	|

## Exception Handling
An Spring Boot advice `EmployeeException.java` has been created for handling exceptions and convert them into desired responses.

| exception										| response     				   			|
|:----------------------------------------------|:--------------------------------------|
| `NotFoundException`						| `404 NOT FOUND`					|
| `RedisConnectionFailureException`	| `500 INTERNAL SERVER ERROR`	|
| `IllegalArgumentException`				| `400 BAD REQUEST`					|

## CRUD service
The CRUD is done in `EmployeeService.java` through a **RedisTemplate<String, employee>**, created as a @bean in `SpringRedisApplication.java`. A RedisTemplate should be created for each entity that will be persisted to Redis.

Redis keys for stored employees will follow the format `employees:<uuid of employee>`. That way, the whole collection of employees can be fetched through the pattern `employees:*`.  The code is dead simple and very self-descriptive.

## Redis connection
The connection to Redis is established through a `Jedis client`. A `JedisConnectionFactory` is defined in `SpringRedisApplication.java`, taking Redis hostname and password from application properties.

Notice that a `StringRedisSerializer` has been used for keys, otherwise the marshalling would be done through the default serializer (as happens with stored values since we are not specifying any serializer).

## Libraries used
- Spring Boot
- Spring Configuration
- Spring REST Controller
- Redis
- Development Tools

Run local **Redis server** in Docker:
	docker pull redis
	docker run -d --name redis -p 6379:6379 redis
	
## Compilation Command
- `mvn clean install` - Plain maven clean and install
