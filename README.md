# Fuego Quazar

El proyecto está divido en tres paquetes: `domain`, `web` y `persistence`.

Las implementación de las funcionalidades del negocio se encuentran en el paquete dominio. Por lo tanto, este documento
se centra en explicar dicho módulo. La figura a continuación incluye los conceptos principales.

![dominio](https://github.com/luiseraso/fuego-quazar/blob/f456469ecb3ee16646da088dda4be6285d814fb3/doc/domain.png?raw=true "domain module")

## Domain
### Clases de Dominio
Se tiene 4 clases de dominio representadas en color azul. `Satellite` la cual gestiona la información de cada satélite
incluidas sus coordenadas `Coordinate`. `InterceptedMessage` que gestiona los mensajes recibidos, los que incluye el
satélite, la distancia y el mensaje encriptado (porción conocida del mensaje). Estas tienen asociado un repositorio de
dominio el cual define una interfaz a ser implementada por diferentes modelos de persistencia. La clase `ResolvedMessage`
no incluye un repositorio porque solo se utiliza para responder a las peticiones de las capas superiores y no requiere
ser persistida. Los mensajes de solicitud de las capas superiores utilizan la clase `InterceptedMessage`.

### Servicios
La funcionalidad a desarrollar en el nivel 1 del proyecto se implementó mediante los servicios `StarshipFinder` y
`MessageDecryptor`. Los dos son servicios que no manejan ningún estado y por lo tanto no acceden a ningún repositorio.

`StarshipFinder` recibe una lista de mensajes interceptados (`InterceptedMessage`). Con esto calcula la ubicación de la
nave enemiga.

`MessageDecryptor` recibe un array multidimensional con todos los mensajes y devuelve el mensaje cifrado.

`CommunicationManager` implementa las funcionalidades requeridas para posteriormente ser consumidas por el controlador
REST (Nivel 2 de los requisitos). Este servicio tiene acceso a los repositorios para verificar la información de los
satélites y los mensajes guardados previamente. Por otra parte, delega de forma asíncrona la ejecución de las tareas de
localización de la nave enemiga y descifrado del mensaje a los otros dos servicios `StarshipFinder` y`MessageDecryptor`.
Cada metodo en este servicio está asociada a las llamadas REST que se implementan posteriormente en la capa web:
- `resolveWithReceivedMessages`: POST /topsecret
- `saveSplitMessage`: POST /topsecret_split/{satellite_name}
- `resolveWithSavedMessages`: GET /topsecret_split

### Repository
Solo se tienen dos interfaces que definen el comportamiento para los repositorios. `InterceptedMessageRepository` y
`SatelliteRepository`. Los repositorios son limpios, es decir solo reciben como parámetros y devuelven como resultado
objetos definidos en el dominio.

## Web
El paquete web implementa los servicios REST solicitados mediante el controlador `TopSecretController`. Su función es
transformar los mensajes recibidos mediante DTOs a mensajes del dominio para posteriormente delegar la ejecución de las
solicitudes al servicio `CommunicationManager`.
En este paquete también se incluyen algunas clases DTOs encargadas de mapear los mensajes de solicitud y respuesta REST.

![web](https://github.com/luiseraso/fuego-quazar/blob/e4895c70419cf5e5560a5eac16f8808a3348c26d/doc/web.png?raw=true "web module")

- `topSecretResolveReceivedMessages`: POST /topsecret
- `topSecretSplitSaveSplitMessage`: POST /topsecret_split/{satellite_name}
- `topSecretSplitResolveWithSavedMessages`: GET /topsecret_split

## Persistence
La capa de persistencia se implementó mediante spring-data almacenando los datos en un BD SQL en memoria. Esto se hizo
por simplicidad en el despliegue. Pero se tuvo cuidado de organizar los repositorios de tal manera que sea fácilmente
extendible una persistencia más acorde con los requerimientos de un microservicio desplegado en la nube.

![persistence](https://github.com/luiseraso/fuego-quazar/blob/02127433979382ac6df9133db94783f8d22f3f7d/doc/persistence.png?raw=true "persistence module")

Por el momento se compone de tres paquetes `entity` donde se definen las clases entidad que se encargan del ORM.
El paquete `crud` que define los repositorios propios de Spring-Data. Y el paquete `mapper` que se encarga del mapeo
de las clases entidad a clases del dominio y viceversa.

# Pruebas unitaras
Se incluyeron pruebas unitarias para cada uno de los servicios del dominio.
