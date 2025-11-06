#  Módulo de Consola CAE – Gestión de Tickets de Estudiantes


---

##  Descripción general

El **Módulo de Consola CAE (Centro de Atención al Estudiante)** permite la gestión de tickets de trámites académicos (certificados, constancias, homologaciones, etc.) mediante estructuras de datos implementadas desde cero.  
El sistema organiza la atención de los casos en orden de llegada, permite registrar observaciones durante la atención y conserva el historial completo del caso finalizado.  
Además, se incorpora un mecanismo de **deshacer (Undo)** y **rehacer (Redo)** para mantener la integridad de las operaciones recientes.

---

## Objetivo técnico

- Implementar tres estructuras de datos fundamentales:
  - **Cola (Queue)** → administración FIFO de tickets en espera.
  - **Lista enlazada simple (SLL)** → almacenamiento dinámico de notas por ticket.
  - **Pilas (Stack)** → registro de acciones para operaciones de deshacer/rehacer.
- Desarrollar un módulo de consola **sin dependencias externas**, utilizando únicamente la **biblioteca estándar de Java**.
- Garantizar **manejo seguro de referencias** y consistencia en los enlaces al agregar o eliminar nodos en estructuras dinámicas.

---

## Decisiones de diseño

| Aspecto | Decisión | Justificación |
|----------|-----------|---------------|
| **Separación por paquetes** | `service/`, `modelo/`, `dominio/`, `estructuras/`, `exception/` | Organiza la lógica de aplicación, modelos de negocio, entidades de dominio, estructuras de datos y excepciones para facilitar mantenimiento y navegación del código. |
| **Estructuras implementadas a mano** | `Cola`, `ColaCasos`, `Lista`, `ListaNotas`, `Nodo`, `Pila`, `PilaAcciones` | Implementación propia para control didáctico y evitar dependencias externas; permite entender y adaptar comportamiento de colas, pilas y listas. |
| **Encapsulamiento de estado** | `EstadoCaso` / `TipoEstado` *(clases/enums)* | Centraliza y tipa los estados del caso (`Abierto`, `En Atención`, `Finalizado`, etc.), facilitando transiciones y validaciones. |
| **Undo/Redo** | `PilaAcciones` / `HistorialAcciones` *(dos pilas lógicas: undo/redo)* | Permite deshacer y rehacer acciones relevantes (notas, cambios de estado, etc.) manteniendo trazabilidad. |
| **Integridad de referencias** | Métodos seguros en `ListaNotas` y `Lista` para manipulación (borrado/recorrido) | Evita inconsistencias al eliminar o actualizar nodos/notas; preserva la integridad de la colección en listas enlazadas simples. |
| **Interacción por consola** | `Main.java` + `MenuCAE.java` *(menú iterativo en consola)* | Proporciona una interfaz simple y reproducible para pruebas funcionales sin GUI; facilita la captura de evidencias y uso en entornos académicos. |
| **Persistencia simulada / trazabilidad** | `HistorialAcciones` en memoria y almacenamiento temporal de casos finalizados | Mantiene registro de acciones para auditoría durante la ejecución; no hay persistencia externa por defecto (archivo/BD). |


---

##  Catálogo de estados del ticket

| Estado | Descripción | Transiciones posibles |
|---------|--------------|------------------------|
| `EN_COLA` | Ticket recién recibido, en espera de atención | → `EN_ATENCION` |
| `EN_ATENCION` | Caso asignado a un agente de atención | → `EN_PROCESO`, `PENDIENTE_DOCS`, `COMPLETADO` |
| `EN_PROCESO` | Caso en seguimiento activo o revisión | → `PENDIENTE_DOCS`, `COMPLETADO` |
| `PENDIENTE_DOCS` | Esperando documentos del estudiante | → `EN_PROCESO`, `COMPLETADO` |
| `COMPLETADO` | Caso cerrado y archivado | Estado final (sin transición) |

---

## Casos borde considerados

| Situación | Manejo implementado |
|------------|---------------------|
| **Atender cuando la cola está vacía** | Se muestra advertencia: *"No hay tickets en espera."* y no se intenta atender. |
| **Registrar nota sin ticket activo** | Se muestra *"No hay ticket en atención."* y la nota no se agrega. |
| **Eliminar nota inexistente** | El método devuelve `false` o muestra mensaje; la lista no se rompe (borrado seguro en `ListaNotas`). |
| **Deshacer sin acciones previas** | Pila de *undo* vacía: operación sin efecto y sin excepción (control en `HistorialAcciones`). |
| **Rehacer sin acciones pendientes** | Pila de *redo* vacía: operación sin efecto y sin excepción. |
| **Finalizar ticket sin haber uno en atención** | Acción prevenida: se muestra advertencia y no se finaliza nada. |
| **Estructura vacía (cola/lista)** | Se informa al usuario mediante mensajes informativos en lugar de lanzar excepciones. |
| **Ingreso de opción no numérica en el menú** | Validación con `try/catch` al parsear enteros (se muestra *"Opción inválida"* o se solicita reintento). |
| **Crear caso con nombre inválido** | Se lanza o gestiona `NombreInvalidoException` según la validación definida; la creación se aborta hasta corregir. |
| **Deshacer una acción crítica (ej. creación de caso)** | El manejo depende del alcance de `HistorialAcciones`; se evita comportamiento destructivo sin confirmación (puede requerir confirmación para operaciones irreversibles). |


---

##  Pruebas iniciales por operación

| Estructura | Operación | Resultado esperado |
|-------------|------------|--------------------|
| **Cola** | Encolar / Desencolar | FIFO correcto |
| **Lista enlazada** | Insertar / Eliminar nota | Enlaces seguros, sin pérdida de nodos |
| **Pila** | Push / Pop | Último en entrar, primero en salir |
| **Undo/Redo** | Revertir o rehacer última acción | Estado del ticket y notas coherente |
| **Módulo Consola** | Flujo completo de atención | Ejecución sin excepciones, comportamiento coherente |

## Capturas de pruebas por operacion

- Al iniciar nuestro proyecto se nos va a desplegar un Menú el cual tiene varias opciones a elegir.
<img width="434" height="342" alt="image" src="https://github.com/user-attachments/assets/3132e051-042b-41b6-a6ab-96690b5cbb77" />


- En La primera opción tenemos "Recibir nuevo caso", esto lo que hará es añadir un caso nuevo y solicitar el nombre, acto seguido realiza una pregunta de que si es urgente o no, en caso de que si, seleccionar S y en caso contrario seleccionar N.
En caso de que seleccionemos S, el caso registrado tomara prioridad ante otros casos creados
<img width="446" height="443" alt="image" src="https://github.com/user-attachments/assets/dbb3db95-09f3-4113-ab97-82f151347d9e" />

<img width="506" height="439" alt="image" src="https://github.com/user-attachments/assets/a863fab0-cfdf-42cb-8447-99eb5bd839dd" />



- En la segunda opción tenemos "Atender siguiente caso, lo cual hará es tomar al primer caso recibido y a este lo atenderá, pero cómo anteriormente seleccionamos que Juan es caso urgente, lo toma cómo prioridad.

  <img width="521" height="370" alt="image" src="https://github.com/user-attachments/assets/2d006707-0ab8-4015-939a-41c670c84332" />

- En la Tercera opción tenemos "Agregar o Eliminar notas" lo cual básicamente primero  lo que realizará es desplegar un mini menú el cual tiene distintas opciones para la gestion de notas, cómo por ejemplo : agregar nota, eliminar nota por indice, mostrar notas actuales, deshacer, rehacer y por ultimo salir. 

<img width="533" height="636" alt="image" src="https://github.com/user-attachments/assets/abf6d704-dfc8-4c4c-8e52-c6ee92e752d4" />

Verificamos que se creen las notas :

<img width="540" height="662" alt="image" src="https://github.com/user-attachments/assets/ee673b91-92d0-44a0-a107-617c3aa39bc0" />

Verificamos que las notas se puedan eliminar:

<img width="740" height="463" alt="image" src="https://github.com/user-attachments/assets/33454d86-1381-4677-bdd4-4a932f71b506" />


Verificamos el historial de notas:

<img width="465" height="354" alt="image" src="https://github.com/user-attachments/assets/0f650cba-648a-4bca-ab59-04a4baa40f6a" />

Verificamos la opcion deshacer :

<img width="465" height="354" alt="image" src="https://github.com/user-attachments/assets/2bf5c9b9-d6b8-4a4e-a599-9682a66797ec" />

Verificamos la opción rehacer:

<img width="531" height="350" alt="image" src="https://github.com/user-attachments/assets/440e5188-c259-4db5-9362-c72ef025c81e" />

Y por ultimo salimos del mini menú:

<img width="419" height="318" alt="image" src="https://github.com/user-attachments/assets/f0f67809-adcf-4cac-a492-9eac8333bb4a" />


- En la cuarta opción tenemos "Cambiar estado del caso actual"  lo cual en si por defecto cuando se atiende un caso selecciona un estado el cual seria : "EN_ATENCION", aquí podemos básicamente cambiar el estado del caso, existen 6 tipos de estados cómo podemos observar, los cuales son: EN_COLA, URGENTE, EN_ATENCION, EN_PROCESO, PENDIENTE_DOCUMENTOS, COMPLETADO.

<img width="661" height="600" alt="image" src="https://github.com/user-attachments/assets/399d546f-591e-4a37-adc3-71a8b34733c1" />

  
- En la quinta opcion tenemos "Finalizar caso actual" esta opción básicamente es cuando ya queremos pasar a un caso nuevo , cabe recalcar , como mencionamos anteriormente, si hay un caso urgente, tomaría como prioridad ese caso.
<img width="685" height="427" alt="image" src="https://github.com/user-attachments/assets/b786e3a7-6b01-47c1-907a-95e7b61686ac" />

- En la sexta opción tenemos "Mostrar historial" lo que basicamente va a enseñar el orden de los tickets creados y su estado actual, al seleccionar cualquiera de los dos , va a mostrar una descripcion de ellos:

<img width="604" height="729" alt="image" src="https://github.com/user-attachments/assets/44dc6dea-8ce1-4559-8c6b-0e1a6c1d069f" />


- En la septima opcion tenemos" Borrar archivo de ticket finalizado" lo cual se lo elimina mediante un ID, como obseramos , cuando se acaba un caso se completa y genera un arhico.txt y a este le otorga un ID, es escencial para su eliminación:

<img width="676" height="416" alt="image" src="https://github.com/user-attachments/assets/26ea19fb-8710-469a-88f5-e2206ce5eb8e" />

  
- En la opcion numero Cero, es basicamente para salir del sistema.


<img width="538" height="437" alt="image" src="https://github.com/user-attachments/assets/c63fbba7-804b-4ee1-8457-8f08e9347037" />


---

##  Guía de ejecución

###  Requisitos previos
- **Java JDK 21** o superior.
- Editor o terminal con acceso al compilador `javac`.

### Nota sobre dependencias y diseño
No se utilizaron APIs externas ni librerías de terceros. Se optó por implementar manualmente las estructuras de datos fundamentales (pilas, colas y listas enlazadas) para comprender mejor su funcionamiento y controlar explícitamente las referencias y operaciones sobre nodos.

### Estructura del proyecto 
El proyecto está organizado bajo el paquete `edu.unl.cc`. La estructura relevante del código fuente es la siguiente:

```bash
Proyecto_Cae/
├── `.gitignore`
├── `pom.xml`
├── `.idea/`
│   └── (configuración del IDE)
├── `src/`
│   ├── `main/`
│   │   └── `java/edu/unl/cc/`
│   │       ├── `service/`
│   │       │   ├── `Main.java`
│   │       │   ├── `CasoManager.java`
│   │       │   ├── `GestorCAE.java`
│   │       │   ├── `HistorialAcciones.java`
│   │       │   ├── `MenuCAE.java`
│   │       │   └── `NotaManager.java`
│   │       ├── `modelo/`
│   │       │   ├── `Accion.java`
│   │       │   ├── `Caso.java`
│   │       │   └── `EstadoCaso.java`
│   │       ├── `dominio/`
│   │       │   ├── `Accion.java`
│   │       │   ├── `Caso.java`
│   │       │   ├── `Nota.java`
│   │       │   └── `TipoEstado.java`
│   │       ├── `estructuras/`
│   │       │   ├── `Cola.java`
│   │       │   ├── `ColaCasos.java`
│   │       │   ├── `Lista.java`
│   │       │   ├── `ListaNotas.java`
│   │       │   ├── `Nodo.java`
│   │       │   ├── `Pila.java`
│   │       │   └── `PilaAcciones.java`
│   │       └── `exception/`
│   │           └── `NombreInvalidoException.java`
│   └── `test/`
│       └── `java/edu/unl/cc/service/`
│           └── `GestorCAETest.java`
└── `README.md`  
```

 

## Autores: 
### [Steeven Pardo](https://github.com/Dan1el17)
### [Royel Jima](https://github.com/R0yalCode)
### [Juan Calopino](https://github.com/JuaaanCalopino)
### [Daniel Saavedra](https://github.com/Dan-San837)
### [Derick Vargas](https://github.com/DerickVar)

