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

