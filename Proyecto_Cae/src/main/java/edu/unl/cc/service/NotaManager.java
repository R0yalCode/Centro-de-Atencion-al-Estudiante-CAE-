package edu.unl.cc.service;

import edu.unl.cc.modelo.Caso;
import java.util.List;

/**
 * Esta clase se encarga de gestionar las notas del caso que esta atendiendo
 * Nos permite agregar, eliminar, mostrar notas y verificar si el caso actual tiene notas registradas
 *
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.1
 */
public class NotaManager {

    private final CasoManager casoManager; // Para referenciar al gestor de casos

    public NotaManager(CasoManager casoManager) {
        this.casoManager = casoManager;
    }

    /**
     * Agrega una nota al caso actual si hay uno en atención
     * @param texto Texto de la nota a agregar
     */
    public void agregarNota(String texto) {
        Caso caso = casoManager.getCasoActual();
        if (caso == null) {
            System.out.println("No hay caso en atención.");
            return;
        }
        caso.agregarNota(texto);
        System.out.println("Nota agregada.");
    }

    /**
     * Elimina la nota ubicada en el índice indicado y devuelve su contenido
     * @param indice Índice de la nota a eliminar
     * @return Texto de la nota eliminada, o null si no se pudo eliminar
     */
    public String eliminarNotaPorIndice(int indice) {
        Caso caso = casoManager.getCasoActual();
        if (caso == null || caso.getNotas().estaVacia()) return null;

        List<String> notas = caso.obtenerNotas();
        if (indice < 0 || indice >= notas.size()) return null;

        return caso.getNotas().eliminarPorIndiceYObtenerTexto(indice);
    }

    /**
     * Muestra todas las notas del caso actual
     * Si el parámetro paraEliminar es true, se numeran para permitir selección
     * @param paraEliminar Indica si se mostrarán con índice para eliminar
     */
    public void mostrarNotasActual(boolean paraEliminar) {
        Caso caso = casoManager.getCasoActual();
        if (caso == null) {
            System.out.println("No hay caso en atención.");
            return;
        }

        List<String> notas = caso.obtenerNotas();
        if (notas.isEmpty()) {
            System.out.println("No hay notas registradas.");
            return;
        }

        System.out.println("Notas del caso actual:");
        for (int i = 0; i < notas.size(); i++) {
            String prefijo = paraEliminar ? (i + 1) + ". " : "- ";
            System.out.println(prefijo + notas.get(i));
        }

        if (paraEliminar) {
            System.out.println("0. Cancelar");
        }
    }

    /**
     * Verifica si el caso actual no tiene ninguna nota registrada
     * @return true si no hay notas si no false si hay al menos una
     */
    public boolean casoActualSinNotas() {
        Caso caso = casoManager.getCasoActual();
        return caso == null || caso.getNotas().estaVacia();
    }
}