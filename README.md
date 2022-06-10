# What was done?

Implementation of the game Kalah using Java and React, This application let the users create games and join other games and play the game on their own computers.

# Kalah game
You can find the rules of the game on Wikipedia: https://en.wikipedia.org/wiki/Kalah.


# Design
For the backend, I choose a Java Monolithic application with clean architecture and tried to use Test driven development approach for implementing this project. I also implement an SQL database and for the development, I used the H2 database. UI communicating with the backend over REST, and because this is a turn-based game, I used WebSocket to notify all players in a game about changes on the board.

The sowing logic is implemented through the Chain of Responsibility pattern using the GameRule interface. Every turn goes through a chain of rules, and if a rule changes or a new rule is added to the game, you just need to edit these interface implementations or create a concrete class for that.
the logic of pits arrangement is in board Entity because it knows the structure of the pits in a board. for simplifying the database I have one Board Entity, but it would be better to have different entities for game and board. 

For the front end, I used React.js and typescript and I also tried to use the TDD approach but due to time limits, I just implement tests for the board component. I used functional components and hooks like useState, useEffect, useCallback, useContext. I also implement some end-to-end tests with Cypress to test important parts of the game.

I used GitHub tasks for managing the tasks and I create different branches for every feature or bug. I also create PRs and merge branches with squashing.


# Structure
The followings are the directories of this project:

- `design` For designing this application I tried to make it simple and clean. The class diagram of the project is available in the design folder, you can open it using https://draw.io website.


- `kalah-server` is the backend of the application, main technology stack for this project are:
    * [Spring Boot](https://projects.spring.io/spring-boot/) - The framework used
    * [Maven](https://maven.apache.org) - Dependency management
    * [H2 Database](https://www.h2database.com/) - Used for database
    * [JUnit](https://junit.org) - Test framework
    * [Mockito](https://site.mockito.org/) - Tasty mocking framework for unit tests in Java
    * [Swagger](https://swagger.io) - Used to generate API docs
    * [Spring Websocket](https://spring.io/guides/gs/messaging-stomp-websocket/) - Used to build an interactive web application



- `kalah-ui` is the frontend of the application, main technology stack for this project are:
    * [React](https://reactjs.org/) library for building ui
    * [TypeScript](https://www.typescriptlang.org/) Used typed syntax
    * [Axios](https://axios-http.com/) http client for communicating with backend
    * [Cypress](https://www.cypress.io/) end to end testing
    * [styled-components](https://styled-components.com/) used for CSS-in-JS styling
    * [react-stomp-hooks](https://github.com/fallobst22/react-stomp-hooks#readme) A react library to manage a application wide STOMP connection via SockJS or Websockets

# How to run

## Backend
> _NOTE: requires java
>

Go to the project directory
```bash
cd kalah-server
```

Install dependencies using: 
```bash
./mvnw install
```
Run the project using: 
```bash
./mvnw spring-boot:run
```
Swagger:  
http://localhost:8080/swagger-ui/index.html

Database:  
http://localhost:8080/h2-console



## Frontend
> _NOTE: requires node
>

Go to the project directory
```bash
cd kalah-ui
```

Install dependencies using: 
```bash
npm install
```
Run the project using: 
```bash
npm start
```
The project is running in:  
http://localhost:3000


# Security
I did not implement security issues a lot, for detecting the current User, I pushed the `username` header in requests and try to decode the current user from that token. If I had time I think it was better to implement User and jwt for authorization.

# Gameplay

start game:
1. Open https://localhost:3000
2. Press login and try to log in with a username.

![Login](design/Login.png?raw=true "Login")

3. After login you can see two parts, `My Boards` are the board created by this username, and `Available Boards` are the boards that were created by other users and nobody joins them yet. you can create a new board by clicking the `Add Board` button.

![Home](design/Home.png?raw=true "Home")

4. After selecting a game in `My Board` or joining a board in `Available Boards` you go to the Board Page and can play the game.

![Board](design/Board.png?raw=true "Board")


5. Each player sees their pits on the button of the board, the pits that can be sowed have hovered. 
6. If a player sows the pit, the board will automatically update for both players.
7. Play until the application notifies you that the game is over.
8. Enjoy!

# Docker-compose deployment
> _NOTE: requires docker, docker-compose
>

Build the project using:

```bash
docker-compose build
```

Create the required volume (change `/Users/jalal/kalah-db` to your custom directory):

```bash
docker volume create --name kalah-db-data --opt type=none --opt device=/Users/jalal/kalah-db --opt o=bind --driver local
```


You can run the project using:
```bash
docker-compose up -d
```

You can stop the project using:
```bash
docker-compose down
```
# Thing's I learn
There are some things that I learned during this sample project:
* Spring Websocket library
* Styled-components
* CSS transitions
* CQRS architecture
* react useContext 
* JHipster

# Further improvements
Because of time limits, I could not implement all of the features, but there are some things that if I had more time, I would do:

## Design
* I read about CQRS and I think it would be a good architecture for this game, By Using this we can have Undo command and replay for the game. looks amazing.
* Implement the project using application generators like JHipster.

## Security
* JWT token support to make sure consistency.
* Secure web sockets so only the board players can access events.
* Register, Login, and forget your password.
* Make the checking of current user a spring request filter

## Representation
* improves the overall UI of the system.
* Make it better for mobile devices.
* i18n and locale

## Persistence
* Add User entity.
* Keep events instead of current Entity, and try to generate board using events.
* Use flyway to manage database migrations
* Change database drive and use MySQL in docker deployment.

## Game flow
* implement an AI agent for playing with users.

