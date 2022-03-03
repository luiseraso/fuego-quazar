# Fuego Quazar
## Project Organization
The project is divided into three packages: `domain`, `web` y `persistence`.

The implementation of the business functionalities is in the domain package. Therefore, this document focuses on 
explaining that module. The figure below includes the main concepts.

![dominio](https://github.com/luiseraso/fuego-quazar/blob/f456469ecb3ee16646da088dda4be6285d814fb3/doc/domain.png?raw=true "domain module")

### Domain
#### Domain classes
There are 4 domain classes represented in blue. `Satellite` which manages the information of each satellite including
its coordinates `Coordinate`. `InterceptedMessage` which manages the received messages, the message includes the
satellite, the distance and the encrypted message (known portion of the message). These have an associated domain 
repository which defines an interface to be implemented by different persistence models. The class `ResolvedMessage` 
does not include a repository because it is only used to respond requests from higher layers and does not need to be
persisted. Request messages from higher layers use the class `InterceptedMessage`.

#### Services
`StarshipFinder` and `MessageDecryptor`. Both are services that do not handle any state and therefore do not access any
repository.

`StarshipFinder` receives a list of intercepted messages (`InterceptedMessage`). With this it calculates the location of
the enemy ship.

`MessageDecryptor` receives a multidimensional array with all the messages and returns the encrypted message.

`CommunicationManager` implements the required functionalities to be consumed later by the REST controller. This service
has access to repositories to check satellite information and previously saved messages. On the other hand, it
asynchronously delegates the execution of the tasks of locating the enemy (`StarshipFinder`) ship and decrypting the
message (`MessageDecryptor`). Each method in this service is associated with REST calls that are subsequently
implemented in the web layer:

- `resolveWithReceivedMessages`: POST /topsecret
- `saveSplitMessage`: POST /topsecret_split/{satellite_name}
- `resolveWithSavedMessages`: GET /topsecret_split

#### Repository
There are only two interfaces that define the behavior for the repositories. `InterceptedMessageRepository` and
`SatelliteRepository`. The repositories are clean; that means, they only receive as parameters and return as a result
objects defined in the domain.

### Web
The web package implements the requested REST services using the controller `TopSecretController`. Its function is to
transform the messages received through DTOs to messages from the domain in order to subsequently delegate the execution
of the requests to the service `CommunicationManager`.
This package also includes some DTO classes responsible for mapping the REST request and response messages.

![web](https://github.com/luiseraso/fuego-quazar/blob/e4895c70419cf5e5560a5eac16f8808a3348c26d/doc/web.png?raw=true "web module")

- `topSecretResolveReceivedMessages`: POST /topsecret
- `topSecretSplitSaveSplitMessage`: POST /topsecret_split/{satellite_name}
- `topSecretSplitResolveWithSavedMessages`: GET /topsecret_split

### Persistence
The persistence layer was implemented using spring-data, storing the data in a database deployed in GCP Cloud SQL.
It should be noted that the repositories and business functions in the domain layer were organized cleanly and
independently of the persistence layer. Therefore, the project can quickly switch to a NoSQL model without having to
modify anything in the domain layer.

![persistence](https://github.com/luiseraso/fuego-quazar/blob/02127433979382ac6df9133db94783f8d22f3f7d/doc/persistence.png?raw=true "persistence module")

At the moment it is made up of three packages `entity` where the entity classes that are in charge of the ORM are
defined. The package `crud` that defines Spring-Data's own repositories. Finally, the package `mapper` that handles the
mapping of entity classes to domain classes and vice versa.

## Unit tests
Unit tests were included for each of the domain services.