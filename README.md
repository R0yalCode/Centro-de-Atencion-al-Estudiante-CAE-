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

##  Casos borde considerados

| Situación | Manejo implementado |
|------------|---------------------|
| Atender cuando la cola está vacía | Mensaje de advertencia: *"No hay tickets en espera."* |
| Registrar nota sin ticket activo | Mensaje: *"No hay ticket en atención."* |
| Eliminar nota inexistente | Retorna `false` sin romper la lista |
| Deshacer sin acciones previas | Pila vacía, sin efecto ni error |
| Rehacer sin acciones pendientes | Pila vacía, sin efecto ni error |
| Finalizar ticket sin haber uno en atención | Previene acción y muestra advertencia |
| Estructura vacía (cola/lista) | Mensaje informativo en lugar de excepción |
| Ingreso de opción no numérica | Validación con `try/catch` al leer enteros |

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


- En La primera opción tenemos "Recibir nuevo caso", esto lo que hará es añadir un caso nuevo y solicitar el nombre, acto seguido realiza una pregunta de que si es urgente o no, en caso de que si, seleccionar S y en caso contrario seleccionar N.
En caso de que seleccionemos S, el caso registrado tomara prioridad ante otros casos creados

- En la segunda opción tenemos "Atender siguiente caso, lo cual hará es tomar al primer caso recibido y a este lo atenderá
  
- En la Tercera opción tenemos "Agregar nota del caso actual" lo cual básicamente lo que realizará es agregarle cómo una descripción sobre su caso, esta puede ser opcional, y además se puede añadir más de una nota, a cada nota se le otorga un ID unico.

- En la cuarta opción tenemos "Eliminar nota del caso actual" lo cual básicamente lo que te solicita es el id de la nota que se registro en el paso anterior y a esta la elimina de la lista de notas de el caso atendido
  
- En la quinta opción tenemos "Cambiar estado del caso actual"  lo cual en si por defecto cuando se atiende un caso selecciona un estado el cual seria : "EN_ATENCION", aquí podemos básicamente cambiar el estado del caso, existen 6 tipos de estados cómo podemos observar, los cuales son: EN_COLA, URGENTE, EN_ATENCION, EN_PROCESO, PENDIENTE_DOCUMENTOS, COMPLETADO.

- En la sexta opción tenemos "Deshacer la ultima acción" lo cual básicamente es como un ctr+z ya que por ejemplo si eliminamos una nota o cambiamos de estado al caso, lo que hará es dar un paso atrás a las acciones realizadas, y así sucesivamente dependiendo del contexto.

- En la séptima opción tenemos el rehacer acción desecha lo cual básicamente es lo contrario a la opción anterior ya que es cómo un ctr+y, ya que digamos queríamos eliminar una nota, y se nos fueron dos notas eliminadas, lo que hace esta opción es básicamente arreglar eso, ya que es cómo por asi decir restablece lo ya re eliminado.
  
- En la octava opcion tenemos "Finalizar caso actual" esta opción básicamente es cuando ya queremos pasar a un caso nuevo , cabe recalcar , como mencionamos anteriormente, si hay un caso urgente, tomaría como prioridad ese caso.





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

