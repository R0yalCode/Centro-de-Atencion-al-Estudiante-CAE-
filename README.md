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

##  Decisiones de diseño

| Aspecto | Decisión | Justificación |
|----------|-----------|---------------|
| **Separación por paquetes** | `modelo/`, `estructuras/`, `consola/` | Mejora la modularidad y claridad del código |
| **Estructuras implementadas a mano** | Nodos y referencias propias | Requisito del proyecto: evitar dependencias externas |
| **Encapsulamiento de estado** | `enum Estado` | Facilita el control de flujo del ticket |
| **Undo/Redo** | Dos pilas (`undoStack`, `redoStack`) | Permite revertir y rehacer acciones relevantes |
| **Integridad de referencias** | Eliminación segura en SLL | Evita pérdida de nodos al eliminar notas |
| **Interacción por consola** | Clase `ModuloConsola` con menú iterativo | Permite pruebas funcionales sin interfaz gráfica |
| **Persistencia simulada** | Historial de tickets finalizados en memoria | Mantiene la trazabilidad del proceso sin archivos externos |

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

#### Cola - Encolar/ Desencolar
<img width="261" height="117" alt="image" src="https://github.com/user-attachments/assets/98a0fbbb-214a-41e5-ae2f-ee0c031b5ed2" />

- Se muestran las lineas de la recepcion de los casos, con la lista de los casos en espera con la opcion 10.

#### Flujo completo
<img width="361" height="246" alt="image" src="https://github.com/user-attachments/assets/c7ea3bef-1142-44f8-9a6d-9d8ecebd049f" />

- Flujo completo del sistema donde se recibe los casos, se los atiende, se les agrega una nota, se finaliza el caso, con su respectiva nota.

#### Lista enlazada -  Insertar / ELimnar nota
<img width="360" height="223" alt="image" src="https://github.com/user-attachments/assets/22c133fb-eb57-42aa-bf63-6be121ee63b6" />

- Se añade dos notas y se elimina la primera, despues se visualiza los casos finalizados con la opcion 9.

#### Pila - Pus/Pop
<img width="288" height="241" alt="image" src="https://github.com/user-attachments/assets/83eb51a4-ec53-42c7-8d61-42f1b7626dee" />

- Se añaden dos notas, despues se coloca deshacer dos veces, visualizando como se han deshecho. 

#### Undo/Redo - Revertir o rehacer la ultima accion
<img width="315" height="195" alt="image" src="https://github.com/user-attachments/assets/6f0aed03-3bdb-410f-a939-2845620025e5" />

- Se muestra como se puede deshacer y rehacer una nota, con todo el flujo del sistema.  



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
src/
└── main/
  └── java/
    └── edu/
      └── unl/
        └── cc/
          ├── Main.java
          ├── dominio/
          │   ├── Accion.java
          │   ├── Caso.java
          │   ├── Nota.java
          │   └── TipoEstado.java
          │
          ├── estructuras/
          │   ├── Cola.java
          │   ├── Lista.java
          │   ├── Nodo.java
          │   └── Pila.java
          │
          ├── exception/
          │   └── NombreInvalidoException.java
          │
          └── service/
            └── GestorCAE.java
```

 

## Autores: 
### [Steeven Pardo](https://github.com/Dan1el17)
### [Royel Jima](https://github.com/R0yalCode)
### [Juan Calopino](https://github.com/JuaaanCalopino)
### [Daniel Saavedra](https://github.com/Dan-San837)

