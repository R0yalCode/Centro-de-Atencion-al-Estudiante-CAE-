package edu.unl.cc.service;

import edu.unl.cc.estructuras.PilaAcciones;
import edu.unl.cc.modelo.Accion;
import edu.unl.cc.modelo.Caso;

/**
 * Esta clase se encarga de registrar y gestionar el historial de acciones
 * realizadas sobre el caso actual
 *
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.1
 */
public class HistorialAcciones {

    // Atributos
    private final PilaAcciones pilaUndo = new PilaAcciones(); // Pila para deshacer acciones
    private final PilaAcciones pilaRedo = new PilaAcciones(); // Pila para rehacer acciones
    private final CasoManager casoManager;                    // Referencia al gestor de casos

    // Constructor
    public HistorialAcciones(CasoManager casoManager) {
        this.casoManager = casoManager;
    }

    /**
     * Registra una nueva acción en la pila de deshacer y limpia la pila de rehacer
     * @param accion Acción realizada sobre el caso actual
     */
    public void registrar(Accion accion) {
        pilaUndo.registrar(accion);
        pilaRedo.limpiar();
    }

    /**
     * Deshace la última acción realizada sobre el caso actual
     * @return La acción que fue deshecha o si no null si no se pudo deshacer
     */
    public Accion deshacer() {
        Accion accion = pilaUndo.deshacer();
        Caso caso = casoManager.getCasoActual();

        if (accion == null) {
            System.out.println("No hay acciones para deshacer.");
            return null;
        }

        if (caso == null || accion.getCasoId() != caso.getId()) {
            System.out.println("La acción no corresponde al caso actual.");
            return null;
        }
        pilaRedo.registrar(accion);

        switch (accion.getTipo()) {
            case AGREGAR_NOTA -> {
                caso.eliminarNota(accion.getDato());
                System.out.println("Deshacer: se eliminó la nota → " + accion.getDato());
            }
            case ELIMINAR_NOTA -> {
                caso.agregarNota(accion.getDato());
                System.out.println("Deshacer: se restauró la nota → " + accion.getDato());
            }
            case CAMBIO_ESTADO -> {
                caso.cambiarEstado(accion.getEstadoAnterior());
                System.out.println("Deshacer: estado restaurado a → " + accion.getEstadoAnterior());
            }
        }

        return accion;
    }

    /**
     * Rehace la última acción que fue deshecha previamente
     * @return La acción que fue rehecha o si no null si no se pudo rehacer
     */
    public Accion rehacer() {
        Accion accion = pilaRedo.deshacer();
        Caso caso = casoManager.getCasoActual();

        if (accion == null) {
            System.out.println("No hay acciones para rehacer.");
            return null;
        }

        if (caso == null || accion.getCasoId() != caso.getId()) {
            System.out.println("La acción no corresponde al caso actual.");
            return null;
        }

        pilaUndo.registrar(accion);

        switch (accion.getTipo()) {
            case AGREGAR_NOTA -> {
                caso.agregarNota(accion.getDato());
                System.out.println("Rehacer: se agregó la nota → " + accion.getDato());
            }
            case ELIMINAR_NOTA -> {
                caso.eliminarNota(accion.getDato());
                System.out.println("Rehacer: se eliminó la nota → " + accion.getDato());
            }
            case CAMBIO_ESTADO -> {
                caso.cambiarEstado(accion.getEstadoNuevo());
                System.out.println("Rehacer: estado cambiado a → " + accion.getEstadoNuevo());
            }
        }

        return accion;
    }
}