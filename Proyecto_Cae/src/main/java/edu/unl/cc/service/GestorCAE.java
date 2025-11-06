package edu.unl.cc.service;

import edu.unl.cc.exception.NombreInvalidoException;
import edu.unl.cc.modelo.Accion;
import edu.unl.cc.modelo.Caso;
import edu.unl.cc.modelo.EstadoCaso;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
/**
 * Esta clase coordina todas las operaciones del sistema
 * Se encarga de recibir casos, atenderlos, gestionar notas, cambiar estados,
 * registrar acciones y guardar la información en archivos txt
 *
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.1
 */
public class GestorCAE {

    private final CasoManager casoManager = new CasoManager(); // Gestor de casos
    private final NotaManager notaManager = new NotaManager(casoManager); // Gestor de notas
    private final HistorialAcciones historial = new HistorialAcciones(casoManager); // Historial para las acciones

    /**
     * Recibe un nuevo caso y lo agrega al sistema
     * @param nombre Nombre del estudiante
     * @param esUrgente Indica si el caso es urgente
     */
    public void recibirCaso(String nombre, boolean esUrgente) {
        try {
            Caso nuevo = casoManager.recibirCaso(nombre, esUrgente);
            System.out.println("Caso recibido: " + nuevo.getId() + " - " + nuevo.getEstudiante());
        } catch (NombreInvalidoException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Atiende el siguiente caso disponible en la cola
     */
    public void atenderSiguienteCaso() {
        casoManager.atenderSiguienteCaso();
    }

    /**
     * Agrega una nota al caso actual y la registra en el historial
     * @param texto Contenido de la nota
     */
    public void agregarNota(String texto) {
        Caso caso = casoManager.getCasoActual();
        if (caso == null) {
            System.out.println("No se puede agregar nota. No hay un caso en atención.");
            return;
        }
        notaManager.agregarNota(texto);
        historial.registrar(new Accion(caso.getId(), Accion.Tipo.AGREGAR_NOTA, texto));
    }

    /**
     * Elimina una nota por índice y la registra en el historial
     * @param indice Índice de la nota a eliminar
     * @return Texto de la nota eliminada
     */
    public String eliminarNotaPorIndice(int indice) {
        Caso caso = casoManager.getCasoActual();
        if (caso == null || notaManager.casoActualSinNotas()) {
            System.out.println("No hay notas para eliminar.");
            return null;
        }
        String notaEliminada = notaManager.eliminarNotaPorIndice(indice);
        if (notaEliminada != null) {
            historial.registrar(new Accion(caso.getId(), Accion.Tipo.ELIMINAR_NOTA, notaEliminada));
        } else {
            System.out.println("Índice inválido.");
        }
        return notaEliminada;
    }

    /**
     * Muestra las notas del caso actual
     * @param paraEliminar Indica si se mostrarán con índice para eliminar
     */
    public void mostrarNotasActual(boolean paraEliminar) {
        notaManager.mostrarNotasActual(paraEliminar);
    }

    /**
     * Verifica si el caso actual no tiene notas.
     * @return true si no hay notas, false si hay al menos una
     */
    public boolean casoActualSinNotas() {
        return notaManager.casoActualSinNotas();
    }

    /**
     * Cambia el estado del caso actual y registra la acción si hubo cambio
     * @param nuevoEstado Estado al que se desea cambiar
     */
    public void cambiarEstado(EstadoCaso nuevoEstado) {
        Caso caso = casoManager.getCasoActual();
        if (caso == null) {
            System.out.println("No hay caso en atención.");
            return;
        }
        EstadoCaso estadoAnterior = caso.getEstado();
        casoManager.cambiarEstado(nuevoEstado);

        if (estadoAnterior != nuevoEstado) {
            historial.registrar(new Accion(caso.getId(), Accion.Tipo.CAMBIO_ESTADO, estadoAnterior, nuevoEstado));
        }
    }

    /**
     * Deshace la última acción registrada
     * @return Texto de la nota afectada
     */
    public String deshacer() {
        Accion accion = historial.deshacer();
        if (accion == null) return null;

        if (accion.getTipo() == Accion.Tipo.AGREGAR_NOTA || accion.getTipo() == Accion.Tipo.ELIMINAR_NOTA) {
            return accion.getContenido();
        }
        return null;
    }

    /**
     * Rehace la última acción deshecha
     * @return Texto de la nota afectada
     */
    public String rehacer() {
        Accion accion = historial.rehacer();
        if (accion == null) return null;

        if (accion.getTipo() == Accion.Tipo.AGREGAR_NOTA || accion.getTipo() == Accion.Tipo.ELIMINAR_NOTA) {
            return accion.getContenido();
        }

        return null;
    }

    /**
     * Finaliza el caso actual y lo guarda en un archivo de texto
     */
    public void finalizarCaso() {
        Caso caso = casoManager.getCasoActual();
        if (caso == null) {
            System.out.println("No hay caso en atención.");
            return;
        }
        casoManager.finalizarCaso();
        guardarCasoEnArchivo(caso);
    }

    /**
     * Guarda la información del caso en un archivo .txt
     * @param caso Caso a guardar
     */
    private void guardarCasoEnArchivo(Caso caso) {
        String nombreArchivo = "ticket_" + caso.getId() + ".txt";

        try (PrintWriter writer = new PrintWriter(nombreArchivo)) {
            writer.println("Ticket #" + caso.getId());
            writer.println("Estudiante: " + caso.getEstudiante());
            writer.println("Estado final: " + caso.getEstado());
            writer.println("Urgente: " + (caso.isUrgente() ? "Sí" : "No"));
            writer.println("Notas:");

            List<String> notas = caso.obtenerNotas();
            if (notas.isEmpty()) {
                writer.println("  Sin notas registradas.");
            } else {
                for (int i = 0; i < notas.size(); i++) {
                    writer.println("  " + (i + 1) + ". " + notas.get(i));
                }
            }

            System.out.println("→ Caso guardado en archivo: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar el caso en archivo: " + e.getMessage());
        }
    }

    /**
     * Muestra todos los casos que han sido finalizados.
     */
    public void mostrarCasosFinalizados() {
        List<Caso> finalizados = casoManager.getCasosFinalizados();
        if (finalizados.isEmpty()) {
            System.out.println("No hay casos finalizados.");
            return;
        }

        for (Caso c : finalizados) {
            System.out.println(c);
            System.out.println("---------------");
        }
    }

    /**
     * Muestra el historial completo de un ticket por su ID
     * @param id ID del ticket a consultar
     */
    public void mostrarHistorialDeTicket(int id) {
        List<Caso> todos = new ArrayList<>();
        todos.addAll(casoManager.getCasosFinalizados());

        Caso actual = casoManager.getCasoActual();
        if (actual != null) todos.add(actual);

        todos.addAll(casoManager.getCasosEnCola());
        todos.sort((a, b) -> Integer.compare(a.getId(), b.getId()));

        Caso buscado = todos.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);

        if (buscado == null) {
            System.out.println("No se encontró ningún ticket con ese ID.");
            return;
        }
        System.out.println("\nHistorial del Ticket #" + buscado.getId());
        System.out.println("Estudiante: " + buscado.getEstudiante());
        System.out.println("Estado actual: " + buscado.getEstado());

        switch (buscado.getEstado()) { // Enseña un mensaje adicional según el estado del ticket
            case COMPLETADO -> System.out.println("→ Este ticket ya fue FINALIZADO.");
            case URGENTE -> System.out.println("→ Este ticket está marcado como URGENTE.");
            case EN_COLA -> System.out.println("→ Este ticket está REGISTRADO y en espera.");
            case EN_ATENCION -> System.out.println("→ Este ticket está siendo ATENDIDO actualmente.");
        }
        System.out.println("Urgente: " + (buscado.isUrgente() ? "Sí" : "No"));

        List<String> notas = buscado.obtenerNotas();
        if (notas.isEmpty()) {
            System.out.println("Notas: Sin notas registradas.");
        } else {
            System.out.println("Notas:");
            for (int i = 0; i < notas.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + notas.get(i));
            }
        }
    }

    /**
     * Para borrar los Archivos creados, directamente desde consola
     * @param id
     */
    public void borrarArchivoDeTicket(int id) {
        String nombreArchivo = "ticket_" + id + ".txt";
        File archivo = new File(nombreArchivo);

        if (archivo.exists()) {
            if (archivo.delete()) {
                System.out.println("Archivo " + nombreArchivo + " eliminado correctamente.");
            } else {
                System.out.println("No se pudo eliminar el archivo " + nombreArchivo + ".");
            }
        } else {
            System.out.println("El archivo " + nombreArchivo + " no existe.");
        }
    }

    public Caso getCasoActual() {
        return casoManager.getCasoActual();
    }

    public List<Caso> getCasosFinalizados() {
        return casoManager.getCasosFinalizados();
    }

    public List<Caso> getCasosEnCola() {
        return casoManager.getCasosEnCola();
    }

    public void validarNombre(String nombre) throws NombreInvalidoException {
        casoManager.validarNombre(nombre);
    }
}