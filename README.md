# Game Sorting service

### Overview
Spring Boot service, that take an arbitrary list of games as input (JSON), and produce a sorted list of games (JSON).
Current service with one Api method: **POST** '{domain:port}**/api/v1/game-sorting**', with parameters specified below in examples.
Service don't have any specific configuration for deploying, connection, or etc. Also service work without database.
Currently this service focused on some functional challenges, all specific configuration can be add optional in the future.

### Technologies

Spring Boot + Maven

### Sorting rules

- The sorting should be on start-time for the game
- First are todays games.
- Second is the "big" games.
- Then the rest of the games.
- The sort window should be per week.

### Game Types

- **V64**
- **V75**
- **V86**
- **GS75**

### Big game type configuration

IIn config file can be configure when game is considered a "Big"-game based on it's type and specific day (possible to config list of day)

Current configuration for big game:

- "V86" and start-time is on any Wednesday
- "V75" and start-time is on any Saturday
- "GS75" and start-time is on any Sunday

Also in configuration file can be configured parameters for **Winterburst**

### Game model 

Examples:
`{
"name": "Sunday1 Game",
"type": "V64",
"start": "2021-02-14T16:30:00"
}`

### Input params

Method POST **/api/v1/game-sorting** wait for object with gameList:List<Game>

`{"gameList":[...// Unsorted game model]}`

### Output result

Method POST **/api/v1/game-sorting** return sorted array. 

`[Sorted list]`

Currently returned result worked without any response wrappers, because currently don't have any specific chalendge about this. Can be changed in the future. 

### Exceptions and validation

The service will fully check the input parameters, if some of the parameters are incorrect, the service will return an error with code **400** and without any specific message.
In game model all field required, and strict types

For **start** field used LocaleDateTime type base on task description file. 
In other situation I preferred other type who represents a moment in timeline for examples Instant type

### Testing

Added basic test coverage, line coverage at, or over, 80% for each file.
