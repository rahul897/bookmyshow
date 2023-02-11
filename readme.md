# BMS assignment

#Frameworks used
 - springboot
 - hibernate/JPA is used so db can be configured on the fly(currently using h2 for portability)

project structure(generic MVC structure)
##how to run
mvn clean install
run main class BMSApplication(spring Boot)
###Assumptions
- movie ran one time/booking can only be done once(it can be extended to future booking etc)

## below APIs can be used
- Add user

POST http://localhost:8080/api/user
Content-Type: application/json

{
  "name": "rahul"
}
- Add theatre

POST http://localhost:8080/api/theatre
Content-Type: application/json

{
  "name": "pvr",
  "location": "vizag"
}
- add movie (require theatre id) 

POST http://localhost:8080/api/theatre/2/movie
Content-Type: application/json

{
  "name": "bahubali",
  "seatingLayout": ["1","2","3"]
}
- get available locations

GET http://localhost:8080/api/locations
Accept: application/json
- get theatres in specific location

GET http://localhost:8080/api/location/vizag
Accept: application/json
- get available seats for a movie

GET http://localhost:8080/api/movie/3
Accept: application/json

- book seats for user and movie

POST http://localhost:8080/api/book
Content-Type: application/json

{
 "userId": 1,
  "movieId": 3,
  "selectedSeats" : [2,3]
}


