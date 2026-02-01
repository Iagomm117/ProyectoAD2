# Spotify

## Índice
- [Introducción](#introducción)
- [Manual técnico para desarrolladores](#manual-técnico-para-desarrolladores)
  - [Requisitos previos](#requisitos-previos)
  - [Base de Datos. Modelo Entidad-Relación](#base-de-datos-modelo-entidad-relación)
    - [Tabla Artistas](#tabla-artistas)
    - [Tabla Canción](#tabla-canción)
    - [Tabla Álbum](#tabla-álbum)
    - [Tabla Género](#tabla-género)
    - [Tabla Usuario](#tabla-usuario)
  - [Estructura](#estructura)
  - [Metodología](#metodología)
  - [Configuracion de Ant](#configuración-de-ant)
  - [Ejecución del proyecto](#ejecución-del-proyecto)
  - [Manejo de errores](#manejo-de-errores)
- [Manual de usuario](#manual-de-usuario)
  - [Registro](#registro)
  - [Busquedas](#busquedas)
  - [Otras opciones (Borrar y añadir canción)](#otras-opciones-borrar-y-añadir-canción)
- [Reparto de tareas](#reparto-de-tareas)
- [Mejoras](#mejoras)
- [Conclusiones](#conclusiones)
- [Autores](#autores)

## Introducción
Este proyecto tratará sobre una aplicación donde intentaremos recrear(en la medida de lo posible) lo que es spotify hoy en día.

El programa nos dejará iniciar sesión con nombre y contraseña y en caso de que no tengamos cuenta podremos acceder a una pantalla de registro donde introduciremos los mismos datos dichos anteriormente, a parte de nuestro/a cantante favorito/a.

Con el inicio de sesión creado y accedido correctamente podremos acceder a una pantalla con información de artistas y sus álbumes y un botón para indicar si nos gusta cada artista,como así pantallas adicionales con la  la información de todas las canciones y  un pequeño audio de referencia de cada una.

También dejará tanto crear como borrar canciones al antojo del usuario.

[Volver al índice](#índice)
## Manual técnico para desarrolladores
### Requisitos previos
- **Java SE 17 o superior:** El proyecto se ha desarrollado usando la version 17 de Java, lo cual se requiere usar la misma version o superior
- **Ant** :El programa deberá ser inicializado en ant para poder implementarle las librerías correspondientes.
- **IDE Recomendado:** Para este proyecto se ha usado Apache NetBeans como IDE pero se puede usar cualquiera que soporte el lenguaje de Java.
- **MySQL:** El proyecto utiliza MySQL como sistema de gestión de bases de datos,por lo que deberá estar instalado en el equipo para que el programa funcione

### Base de Datos. Modelo Entidad-Relación

#### Tabla Artistas
| Campo          | Tipo de dato | Descripción                                   |
|----------------|--------------|-----------------------------------------------|             
| ID             | INT (PK)     | Identificador único del artista               |
| Nombre         | VARCHAR(20)  | Nombre del artista                            |
| Reproducciones | DOUBLE       | Reproducciones mensuales del artista          |
| Cancion        | INT          | Nombre de la canción                          |
| Album          | VARCHAR(80)  | Nombre del álbum al cual pertenece la canción |
| Genero         | VARCHAR(20)  | Género de música del artista                  |
| Nacionalidad   | VARCHAR(20)  | País de origen                                |
| Imagen         | VARCHAR(200) | Imagen del artista                            |

#### Tabla Canción
| Campo          | Tipo de dato | Descripción                          |
|----------------|--------------|--------------------------------------|
| ID             | INT (PK)     | Identificador único del artista      |
| Nombre         | VARCHAR(130) | Nombre de la canción                 |
| Reproducciones | DOUBLE       | Total de reproducciones de la canción|
| Album          | VARCHAR(80)  | Nombre del álbum                     |
| Genero         | VARCHAR(20)  | Género de música de la canción       |
| Artistas       | VARCHAR(130) | Artistas que participan en la canción|
| Imagen         | VARCHAR(200) | Imagen de la canción                 |
| Duración       | INT          | Duración total de la  canción        |

#### Tabla Álbum
| Campo             | Tipo de dato  | Descripción                        |
|----------------   |-------------- |---------------------------------   |
| ID                | INT (PK)      | Identificador único del artista    |
| Nombre            | VARCHAR(80)   | Nombre del álbum                   |
| Reproducciones    | DOUBLE        | Total de reproducciones del álbum  |
| Canciones         | VARCHAR(2000) | Canciones que componen el álbum    |
| Artistas          | VARCHAR(130)  | Artistas que participan en el álbum|
| Genero            | VARCHAR(20)   | Género de música del album         |
| Cantidad_Canciones| INT           | Cantidad de canciones del álbum    |
| Imagen            | VARCHAR(200)  | Imagen del álbum                   |
| Duración          | INT           | Duración total del álbum           |

#### Tabla Género

| Campo  | Tipo de dato | Descripción                                      |
|------- |--------------|--------------------------------------------------|
| ID     | INT (PK)     | Identificador único del género musical           |
| Nombre | VARCHAR(20)  | Nombre del género musical                        |

#### Tabla Usuario
| Campo          | Tipo de dato | Descripción                     |
|----------------|--------------|---------------------------------|
| ID             | INT (PK)     | Identificador único del artista |
| Nombre         | VARCHAR(50) | Nombre de usuario               |
| Contraseña     | VARCHAR(30)  | Contraseña del usuario          |
| masReproducido | VARCHAR(30)  | Artista favorito                |


### Estructura
El proyecto está planteado segun la estructura Modelo, Vista, Controlador.

#### Modelo

#### Vista

#### Controlador

### Metodología

#### Uso de Git

El proyecto utiliza la metodología de desarrollo incremental basado en ramas, lo cual da una gran acilidad a la hora de la gestión de versiones y el trabajo en equipo de todos los desarrolladores.
Las ramas base en este caso serían 'main' y 'developer' y las ramas que se utilizaron para el desarrollo fueron 2 con los nombres de los desarrolladores en este caso 'Nico' y 'Iago' donde cada uno trabajaba en ella independientemente.

Flujo de trabajo:

1. **Añadir funcionalidad:** cuando se quiere crear una nueva funcionalidad, se avisa al equipo y se trabaja en la propia rama para implementarla sabiendo de antemano que no va a pisar nada de lo que otro desarrollador este trabajando en otra rama.
2. **Testear:** Cuando se completa una nueva funcionalidad se realizan las pruebas pertinentes para asegurar que funciona correctamente y no rompe nada del resto del proyecto.
3. **Merge a Developer:** Al haber testeado las nuevas funcionalidades y ser exitosas, se realiza un mergeo de la rama a developer. Es importante este paso dado que todo lo que vaya a esta rama debera de ir  con los menos problemas posibles a la hora de mergear con el resto de desarrolladores.
4. **Merge a Main:** Para finalizar cuando la rama `developer` fue probada y es una version estable y sin errores, se realiza el merge a la rama `main`. Al tener todo el proyecto actualizado en el main dariamos paso a una nueva version del proyecto final.

Gracias a este flujo de trabajo nos permite trabajar varios desarrolladores simultáneamente cada uno con funciones diferentes y poder integrar todos los cambios a la vez, testeándolo todo al mismo tiempo y corregiendo errores que puedan salir al mergear.


### Configuración de Ant
  
  **mysql-connector-j-8.1.0.jar:**:Se utiliza para poder conectarse  así a la base de datos que   tengamos creada.
  Para poder añadirlo correctamente tendríamos que seguir estos pasos:
    
    1.Abrir el proyecto
    
    2.Hacer click derecho en "Libraries"
    
    3.Darle a ADD JAR/Folder 
    
    4.Seleccionar el conector en cuestión

### Ejecución del proyecto
**Desde el IDE (Apache NetBeans):**
``` 
  1.Importar el proyecto como un proyecto Ant.
  
  2.Asegurarse de de que el mysql-connector-j-8.1.0.jar este ya implementado en las librerias.
  
  3.Ejecutar el método bien para poder iniciar la aplicación
  ````
### Manejo de errores
Para este proyecto hemos usado los try catch para controlar las posibles excepciones que puedan salir en el manejo de la aplicación.
## Manual de usuario
[Volver al índice](#índice)
### Registro
Al abrir la aplicación, el usuario en cuestión verá la siguiente pantalla :

<img width="451" height="627" alt="image" src="https://github.com/user-attachments/assets/1b5af970-3170-4f08-8382-4949ee75a990" />

En caso de que quieras iniciar sesión pero no puedas debido a que nombre/contraseña son incorrectos,aparecerá esto:

<img width="455" height="540" alt="image" src="https://github.com/user-attachments/assets/e2d1dfe9-232b-46c1-89ea-3b53730e6ccd" />


Como no van las credenciales,le daremos al boton *Registrarse* para poder crear una cuenta:
<img width="451" height="543" alt="image" src="https://github.com/user-attachments/assets/ac11330e-eac3-4469-9e5b-2d061dcf7461" />

Al darle a este botón se nos abrirá esto:

<img width="437" height="632" alt="image" src="https://github.com/user-attachments/assets/3e211b8f-404a-4715-92d3-b499e1b72e52" />

Meteremos entonces los datos necesarios(nombre,contraseña,artista favorito), incluyendo un combobox con el artista a elegir:

<img width="531" height="645" alt="image" src="https://github.com/user-attachments/assets/9c3ffd2b-8a31-4557-9c93-0b02b08d71de" />

En caso de que exista(con usuario Pepito, que tenemos por defecto en la base de datos),si le damos a *Siguiente* aparecerá esto:

<img width="523" height="646" alt="image" src="https://github.com/user-attachments/assets/16731c50-00d9-4c32-bef0-9c7ae2282cf9" />

Si todo esta correcto nos aparecerá esto:

<img width="525" height="645" alt="image" src="https://github.com/user-attachments/assets/683a9652-03a9-4a8c-a04a-6de199fcfd83" />

Con ello ya se cerraría la pantalla de registro, y se abriría la de Inicio de Sesion,donde introduciríamos los datos de registro:

<img width="458" height="537" alt="image" src="https://github.com/user-attachments/assets/6d8489c4-ee6f-4c6c-af57-803a81d60405" />

Una vez que se validen los datos aparecerá esto:

<img width="460" height="537" alt="image" src="https://github.com/user-attachments/assets/d38261c7-ed69-4460-a91c-60fbeb6b9b4a" />


Y a continuacion ya accederíamos a la pantalla principal:

<img width="876" height="532" alt="image" src="https://github.com/user-attachments/assets/398a5dda-2c4e-433d-a2eb-040c67c28f8d" />


### Busquedas

### Otras opciones (Borrar y añadir canción)

## Reparto de tareas
[Volver al índice](#índice)
## Mejoras
En cuanto a posibles mejoras en un futuro comentamos las siguientes:
```
1.Introducción de información de podcasts.

2.Búsqueda a tiempo real por tecla pulsada.
```
[Volver al índice](#índice)
## Conclusiones
[Volver al índice](#índice)
## Autores

Nicolás Sanjuás Casal [@nico](https://github.com/Nicosanju)  
Iago Mariño Mota @
