package edu.unl.cc.service;
/**
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.0
 */
import edu.unl.cc.dominio.*;
import edu.unl.cc.estructuras.*;
import java.util.ArrayList; // Para manejar listas dinámicas
import java.util.List;
import edu.unl.cc.exception.NombreInvalidoException; // Excepción personalizada para validar nombres

public class GestorCAE {
    private Cola cola = new Cola(); // Cola para casos en espera
    private Pila pilaUndo = new Pila(); // Pila para acciones que se pueden deshacer
    private Pila pilaRedo = new Pila(); // Pila para acciones que se pueden rehacer
    private Caso casoActual = null; // Caso que se está atendiendo actualmente
    private List<Caso> casosFinalizados = new ArrayList<>(); // Lista de casos ya finalizados
    private int contadorId = 1; // Contador para asignar ID único a cada caso

    // Método para recibir un nuevo caso
    public void recibirCaso(String nombreEstudiante) throws NombreInvalidoException {
        String limpio = nombreEstudiante.trim(); // Eliminamos espacios al inicio y al final
        // Validamos que el nombre no esté vacío y solo contenga letras y espacios
        if (limpio.isEmpty() || !limpio.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            throw new NombreInvalidoException("El nombre del estudiante no puede contener números ni símbolos ni espacios vacios.");
        }
        Caso nuevo = new Caso(contadorId++, nombreEstudiante); // Creamos el nuevo caso con ID único
        cola.agregarCaso(nuevo); // Lo agregamos a la cola de espera
        System.out.println("Caso recibido: " + nuevo.getId() + " - " + nombreEstudiante); // Confirmamos en consola
    }

    // Método para atender el siguiente caso en la cola
    public void atenderSiguienteCaso() {
        if (cola.estaVacia()) { // Si no hay casos en espera, mostramos mensaje
            System.out.println("No hay casos en espera.");
            return;
        }
        casoActual = cola.atenderCaso(); // Extraemos el siguiente caso de la cola
        casoActual.setEstado(TipoEstado.EN_ATENCION); // Cambiamos su estado a "En atención"
        pilaUndo = new Pila(); // Reiniciamos la pila de deshacer
        pilaRedo = new Pila(); // Reiniciamos la pila de rehacer
        System.out.println("Atendiendo caso: " + casoActual.getId() + " - " + casoActual.getEstudiante()); // Mostramos el caso atendido
    }

    // Método para agregar una nota al caso actual
    public void agregarNota(String texto) {
        if (casoActual == null) { // Validamos que haya un caso en atención
            System.out.println("No hay caso en atención.");
            return;
        }
        casoActual.agregarNota(texto); // Agregamos la nota al caso
        pilaUndo.registrarAccion(new Accion(casoActual.getId(), Accion.Tipo.AGREGAR_NOTA, texto)); // Registramos la acción
        pilaRedo.limpiar(); // Limpiamos la pila de rehacer ya que se hizo una nueva acción
        System.out.println("Nota agregada."); //    Mostramos en consola
    }

    // Método para eliminar una nota del caso actual
    public void eliminarNota(String texto) {
        if (casoActual == null) { // Validamos que haya un caso en atención
            System.out.println("No hay caso en atención.");
            return;
        }
        casoActual.eliminarNota(texto); // Eliminamos la nota del caso
        pilaUndo.registrarAccion(new Accion(casoActual.getId(), Accion.Tipo.ELIMINAR_NOTA, texto)); // Registramos la acción
        pilaRedo.limpiar(); // Limpiamos la pila de rehacer
        System.out.println("Nota eliminada (si es que esta existía)."); // Confirmamos en consola
    }

    // Método para cambiar el estado del caso actual
    public void cambiarEstado(TipoEstado nuevoEstado) {
        if (casoActual == null) { // Validamos que haya un caso en atención
            System.out.println("No hay caso en atención.");
            return;
        }
        TipoEstado anterior = casoActual.getEstado(); // Guardamos el estado anterior
        casoActual.setEstado(nuevoEstado); // Cambiamos al nuevo estado
        pilaUndo.registrarAccion(new Accion(casoActual.getId(), Accion.Tipo.CAMBIO_ESTADO, anterior, nuevoEstado)); // Registramos el cambio
        pilaRedo.limpiar(); // Limpiamos la pila de rehacer
        System.out.println("El sstado a sido cambiado a: " + nuevoEstado); // Mostramos el nuevo estado
    }

    // Método para deshacer la última acción realizada
    public void deshacer() {
        if (pilaUndo.estaVacia()) { // Si no hay acciones para deshacer, avisamos
            System.out.println("Nada que deshacer.");
            return;
        }
        Accion ultima = pilaUndo.deshacer(); // Extraemos la última acción
        if (ultima == null) {
            System.out.println("Nada que deshacer.");
            return;
        }
        pilaRedo.registrarAccion(ultima); // La guardamos en la pila de rehacer

        if (casoActual == null || casoActual.getId() != ultima.getCasoId()) { // Verificamos que la acción pertenezca al caso actual
            System.out.println("La acción no pertenece al caso en atención. (soporte pendiente)");
            return;
        }

        // Revertimos la acción según su tipo
        switch (ultima.getTipo()) {
            case AGREGAR_NOTA:
                casoActual.eliminarNota(ultima.getDato()); // Quitamos la nota agregada
                System.out.println("Deshecho: agregar nota."); // Confirmamos en consola
                break;
            case ELIMINAR_NOTA:
                casoActual.agregarNota(ultima.getDato()); // Restauramos la nota eliminada
                System.out.println("Deshecho: eliminar nota.");
                break;
            case CAMBIO_ESTADO:
                casoActual.setEstado(ultima.getEstadoAnterior()); // Volvemos al estado anterior
                System.out.println("Deshecho: cambio de estado.");
                break;
        }
    }

    // Método para rehacer la última acción deshecha
    public void rehacer() {
        if (pilaRedo.estaVacia()) { // Si no hay acciones para rehacer, avisamos
            System.out.println("Nada que rehacer.");
            return;
        }
        Accion accion = pilaRedo.deshacer(); // Extraemos la acción a rehacer
        if (accion == null) {
            System.out.println("Nada que rehacer.");
            return;
        }
        pilaUndo.registrarAccion(accion); // La volvemos a registrar en la pila de deshacer

        if (casoActual == null || casoActual.getId() != accion.getCasoId()) { // Verificamos que la acción pertenezca al caso actual
            System.out.println("La acción no pertenece al caso en atención. (soporte pendiente)");
            return;
        }

        // Reaplicamos la acción según su tipo
        switch (accion.getTipo()) {
            case AGREGAR_NOTA:
                casoActual.agregarNota(accion.getDato()); // Volvemos a agregar la nota
                System.out.println("Rehecho: agregar nota.");
                break;
            case ELIMINAR_NOTA:
                casoActual.eliminarNota(accion.getDato()); // Volvemos a eliminar la nota
                System.out.println("Rehecho: eliminar nota.");
                break;
            case CAMBIO_ESTADO:
                casoActual.setEstado(accion.getEstadoNuevo()); // Aplicamos el nuevo estado otra vez
                System.out.println("Rehecho: cambio de estado.");
                break;
        }
    }

    // Método para finalizar el caso actual
    public void finalizarCaso() {
        if (casoActual == null) { // Validamos que haya un caso en atención
            System.out.println("No hay caso en atención.");
            return;
        }
        casoActual.setEstado(TipoEstado.COMPLETADO); // Marcamos el caso como completado
        casosFinalizados.add(casoActual); // Lo agregamos al historial de finalizados
        System.out.println("Caso finalizado: " + casoActual.getId()); // Confirmamos en consola
        casoActual = null; // Liberamos el caso actual
        pilaUndo = new Pila(); // Reiniciamos las pilas
        pilaRedo = new Pila();
    }

    // Método para mostrar todos los casos finalizados
    public void mostrarHistorialFinalizados() {
        if (casosFinalizados.isEmpty()) {
            System.out.println("No hay casos finalizados.");
            return;
        }
        for (Caso c : casosFinalizados) {
            System.out.println(c);
            System.out.println("---------------");
        }
    }

    // Muestra los casos que están en espera
    public void mostrarCasosEnEspera() {
        cola.mostrarCasosEnEspera();
    }
}