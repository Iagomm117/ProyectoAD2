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
    - [Tabla Podcast](#tabla-podcast)
  - [Estructura](#estructura)
  - [Metodología](#metodología)
  - [Configuracion de Ant](#configuración-de-ant)
  - [Ejecución del proyecto](#ejecución-del-proyecto)
  - [Manejo de errores](#manejo-de-errores)
- [Manual de usuario](#manual-de-usuario)
  - [Registro e Inicio de Sesión](#registro-e-inicio-de-sesión)
  - [Busquedas](#busquedas)
  - [Consultar información](#consultar-información)
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

<img width="1090" height="767" alt="image" src="https://github.com/user-attachments/assets/3956204f-0055-4782-8b47-a2f8b05b30ea" />


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
| ID_Genero      | INT (FK)     | Identificador único del genero                |

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
| ID_Genero      | INT (FK)     | Identificador único del genero       |

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
| ID_Genero         | INT (FK)     | Identificador único del genero      |

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

#### Tabla Podcast

| Campo          | Tipo de dato | Descripción                                   |
|----------------|--------------|-----------------------------------------------|             
| ID             | INT (PK)     | Identificador único del artista               |
| Nombre         | VARCHAR(20)  | Nombre del artista                            |
| Narradores     | DOUBLE       | Reproducciones mensuales del artista          |
| Album          | VARCHAR(80)  | Nombre del álbum al cual pertenece la canción |
| Genero         | VARCHAR(20)  | Género de música del artista                  |
| Oyentes        | VARCHAR(20)  | País de origen                                |
| ID_Genero      | INT (FK)     | Identificador único del genero                |

### Estructura
El proyecto está planteado segun la estructura Modelo, Vista, Controlador.

<img width="331" height="640" alt="image" src="https://github.com/user-attachments/assets/582d2cea-9adc-473d-b93c-732d43d88683" />


#### Modelo

En el modelo crearíamos las clases para los pojos, más un RenderTabla, para personalizar las tablas a nuestro antojo

#### Vista

En las vistas tendriamos:la pantalla principal, la de inicio de sesion, la de registro, la de información de albumes y canciones(utilizamos el mismo JDialog), la de añadir cancion y la de información de canción.

#### Controlador

Y el controlador serviría para gestionar de manera eficiente todas estas vistas mencionadas anteriormente, separando un controller para la información de albumes y otro para la de canciones a pesar de utilizar el mismo JDialog para las dos.

#### Main

Aquí tendríamos la clase principal para ejecutar el programa y también la clase OperacionsDB para todas las consultas que le hicieramos a la base de datos

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
  
  3.Ejecutar el método *Main* para poder iniciar la aplicación

  ````
### Manejo de errores
Para este proyecto hemos usado los try catch para controlar las posibles excepciones que puedan salir en el manejo de la aplicación.
## Manual de usuario
[Volver al índice](#índice)
### Registro e Inicio de Sesión:
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


Y a continuacion ya accederíamos a la pantalla principal, donde nos aparecerá por defecto marcado el artista favorito que escogimos en el registro:

<img width="878" height="537" alt="image" src="https://github.com/user-attachments/assets/94f856bc-085f-4e9c-a9d6-f1a8fad1d05e" />



### Busquedas

Una vez que estamos en la pantalla principal podremos acceder a las diferentes funcionalidades:

La primera será la opción del filtro de búsqueda por artista, la cual no hace distinción de mayúsculas y minúsculas

Como vemos si ponemos la letra correspondientes nos mostrará los datos de todos los artistas que contengan esa letra al darle a *Buscar*:

<img width="892" height="540" alt="image" src="https://github.com/user-attachments/assets/49df68f8-0ec1-4a5c-b8be-bd71b4c2988b" />

Como así buscaríamos uno en concreto(por ejemplo Melendi):

<img width="888" height="538" alt="image" src="https://github.com/user-attachments/assets/ddad379c-9a33-4a88-afd0-9f60b8bdd674" />

Si lo dejamos en blanco y le damos a *Buscar* otra vez, reaparecerían todos los artistas otra vez:

<img width="876" height="536" alt="image" src="https://github.com/user-attachments/assets/03df2b41-1da3-4993-b1d0-648c1c03b5c6" />


### Consultar información

#### Información de artista
      
Para poder consultar de forma individualizada la información del artista tendremos un *MouseListener* para que al pulsar  en la casilla del nombre del artista nos aparezca su información detallada.     

Si pulsamos en Rosalía, aparecerá esto:
``` 
-Género
-Nombre del artista
-Nacionalidad,reproducciones mensuales
-Tabla con :
  -Imagen de la canción
  -Álbum al que pertenece
  -Nombre de la canción
  -Botón de reproduccion
```
<img width="832" height="530" alt="image" src="https://github.com/user-attachments/assets/e5da5286-4c56-408b-a8a5-da500d77b426" />

En caso de que se pulse en la casilla de la canción, podremos ver la información de la canción en cuestión, que sería:

``` 
-Imagen de la canción
-Nombre de la canción
-Nombre del artista
-Duración de la canción
-Reproducciones totales
```

<img width="587" height="491" alt="image" src="https://github.com/user-attachments/assets/f1379d2d-d0c5-4a20-8299-83ef7ef2b3c4" />

Y si queremos reproducir la canción en cuestión le daremos a play, donde cambiará el icono si se está reproduciendo, y se volverá a poner igual cuando acabe la canción:

<img width="998" height="536" alt="image" src="https://github.com/user-attachments/assets/1f9ffdf5-bc30-4e1b-b76e-ca5f9262bb49" />

#### Información de álbum

Tendrá la misma funcionalidad que el *MouseListener* de artista,pero ahora enseñando la información detallada de los álbumes, que tendrá la misma estructura, pero enseñará el número de canciones que tiene el álbum y la duración del mismo:

<img width="815" height="532" alt="image" src="https://github.com/user-attachments/assets/5727e8f4-83d4-4c3a-9c12-c998590b31ab" />

En cuanto a la infomación de la canción vendría siendo lo mismo que en el apartado canción:

<img width="350" height="356" alt="image" src="https://github.com/user-attachments/assets/d9f2306b-93ca-4e83-8cc3-a801a3cc1481" />

Y cuando reproduces una de las canciones del álbum también tendría la misma funcionalidad:

<img width="956" height="530" alt="image" src="https://github.com/user-attachments/assets/92b97f86-9bcb-4439-9357-95a79e1cc56d" />

### Otras opciones (Borrar y añadir canción)

#### Añadir canción 

Para poder añadir una canción tendríamos que pulsar en el artista donde se quiera añadir la canción(en este caso esocogí Rels b), y pulsar *Añadir Cancion*:

<img width="770" height="532" alt="image" src="https://github.com/user-attachments/assets/a61d296a-7740-4bd8-b8e3-67219665c817" />

Se abrirá esta ventana, donde le meteremos:

```
-Nombre de la canción
-Álbum al que pertenecerá
-Duración de la canción

```

<img width="411" height="305" alt="image" src="https://github.com/user-attachments/assets/e8137c22-b793-4ffd-b90d-57bfc98dae74" />

Una vez guardado nos pondrá esto:

<img width="402" height="318" alt="image" src="https://github.com/user-attachments/assets/7d014fa4-9775-42b2-83a7-e21edc477da0" />

Y ya aparecería la canción en la pantalla:

<img width="738" height="538" alt="image" src="https://github.com/user-attachments/assets/fbdedbc2-9708-45b1-ab05-715eb2039485" />

#### Borrar canción

En caso de querer borrar una canción tendremos que acceder al apartado de artisa y pulsar en uno, y una vez accedido pulsar en la canción que se quiera eliminar:

<img width="742" height="532" alt="image" src="https://github.com/user-attachments/assets/19d976cd-635d-4c60-b4de-1366d83b1cc1" />

Una vez ahí, le daremos a *Borrar Cancion*:

<img width="742" height="532" alt="image" src="https://github.com/user-attachments/assets/ae894533-ceed-4982-a127-8d0c13a46874" />

Cuando le demos nos aparecerá este aviso:

<img width="513" height="323" alt="image" src="https://github.com/user-attachments/assets/9fddb542-e7c5-4615-b13d-524892157ac6" />

Y si le damos a *SI* ya no aparecerá la canción:

<img width="750" height="523" alt="image" src="https://github.com/user-attachments/assets/e29dba4a-c264-403c-89c4-ea082133d65b" />

## Reparto de tareas

En cuanto al reparto de las tareas, haríamos el siguiente reparto:

Nico realizaría la parte gráfica de las interfaces junto a la gestión de usuarios, como también descargaría parte de las canciones a reproducir en el programa, y crearía parte del script de creacón de las tablas, realizaría parte de la lógica del programa pero en menor parte que Iago, y por último realizaría el readme.

En cuanto a Iago, se encargaría de la inserción de todos los datos, como de la descarga de la totalidad de las imágenes, y parte de las canciones, y se encargaría, en mayor parte que Nico en realizar la lógica del programa,crearía parte del script de creacón de las tablas, y por último, también crearía el modelo entidad-relación para insertar en el Readme.

En cuanto a las horas trabajadas serían mas o menos las mismas ya que lo que no trabajamos en clase lo realizamos en llamada los dos, así que podrían  ser aproximadamente unas 35 horas aproximadamente.


[Volver al índice](#índice)
## Mejoras
En cuanto a posibles mejoras en un futuro comentamos las siguientes:
```
1.Información sobre podcasts.

2.Búsqueda a tiempo real por tecla pulsada.

3.Creación de un álbum con las canciones incluídas dentro de él.

4.Introducción de más artistas.

```
[Volver al índice](#índice)
## Conclusiones

En cuanto a las horas trabajadas serían mas o menos las mismas ya que lo que no trabajamos en clase lo realizamos en llamada los dos, así que podrían  ser aproximadamente unas 35 horas aproximadamente.

Consideramos que nuestro programa cumple con todos los requisitos que se solicitan, ya que conseguimos conectarnos a la BBDD de manera completamente satisfactoria. También nos permite tanto ver información detallada, como crear o eliminar registros.
Para los usuarios utilizamos un un registro e inicio de sesion en el cual se guardan las acciones que vayan haciendo de manera individualizada

El acceso a las diferentes pantallas y la navegación entre ellas es fácil e intuitivo, tanto así el acceso a los diferentes botones,que expresan de manera coherente lo que hace cada uno

Con la creación de este proyecto hemos podido aprender a realizar consultas más complejas dentro de la base de datos para sacar información más detallada, como así también a manejar otro tipo de archivos como son los .mp3(para las canciones) o también la introducción de imágenes dentro de la BBDD.


[Volver al índice](#índice)
## Autores

Nicolás Sanjuás Casal [@nico](https://github.com/Nicosanju)  
Iago Mariño Mota @
