
Vending machine that allows next points:

- add user coins to machine
- buy drink and get the rest coins from machine if there is enough coins for drink and reject in other case

#Running

The database pre-configured is an h2, so you just have to:


        $ sbt run

#Testing

To run all tests (routes and persistence tests):


        $ sbt test

#Using

	curl --request POST localhost:8080/drinkmachine/cons -H "Content-type: application/json" --data "{\"id\" : 3,\"number\" : 2}"

	curl http://localhost:8080/drinkmachine/drink/2
