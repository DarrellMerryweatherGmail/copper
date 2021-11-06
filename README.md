# Copper Developer Test

# API Description

This project is using the OpenApi 3.0 Specification([Swagger][1]) as API description Language and is 
the solution, or at least as much as possible, to the developer test from [copper.co](http://copper.co)

## Development

The Swagger Console is automatically updated based on the src/main/resources/public/api-docs.yaml file , 
and can be viewed within a browser via the [http://localhost:8080](http://localhost:8080) URL. The swagger 
console will automatically load, read the api description and render the swagger console. 
             
The easiest way to run this application is via docker compose following these easy steps to run: 

- The spring boot jar needs to be built, using the command: -

      $ ./gradlew clean build

- Start up the docker compose. This will build the docker image if this service, and start up both a postgres 13
  database, and this service. The service will then provision the database, using [Flyway](https://flywaydb.org/). 
  To do this, simply use this command: -

      $ docker-compose up --build

- The service should then be available via [http://localhost:8080](http://localhost:8080), at which point you
  can use the swagger console to test the API's. 

## Command Line API testing

The API's can, of course, be test via a regular curl command. The first thing that should really be done is to run the 
/deribit/sync-accounts endpoint, as this will synchronise the acocunts and sub-accounts linked to the user associated
with the client-id/client-secret (These are currently linked to my current test account, however, they could easily
be overridden with the following environment variables: -

    DERIBIT_OAUTH_CLIENTID
    DERIBIT_OAUTH_CLIENTSECRET

To execute this command, the following curl command can be issued: - 

    curl -X 'GET' 'http://localhost:8080/deribit/sync-accounts' -H 'accept: */*'

Once the account has been synced, you can then use the other account / wallet APi's: - 

**/accounts** GET

Gets all of the synchronised accounts (main and sub-accounts)

      curl -X 'GET' 'http://localhost:8080/accounts' -H 'accept: application/json'

**/accounts/{id}** GET
                      
Gets a single Account with the specified ID

      curl -X 'GET' 'http://localhost:8080/accounts/34826' -H 'accept: application/json'

**/accounts/{id}** DELETE

Deletes a single Account with the specified ID

      curl -X 'DELETE' 'http://localhost:8080/accounts/34826' -H 'accept: */*'

**/wallet/deposits/{currency}** GET

Gets all of the deposits for the user for the given currency

      curl -X 'GET' 'http://localhost:8080/wallet/deposits/BTC' -H 'accept: application/json'

**/wallet/withdrawals/{currency}** GET

Gets all of the withdrawals for the user for the given currency

      curl -X 'GET' 'http://localhost:8080/wallet/withdrawals/BTC' -H 'accept: application/json'

**/wallet/transfer/{currency}** POST

Submits a transfer from the main account to the sub-account for the given currency

      curl -X 'POST' 'http://localhost:8080/wallet/transfers/BTC' -H 'accept: application/json' -H 'Content-Type: application/json' -d '{"destination": "34829","amount": 1 }'

## Gradle

The './gradlew' can be changed for 'gradle' if installed and available in the PATH,
just be aware of using the same version as the gradle wrapper

    ./gradlew clean build

## Java

The current supported Java version is 11
