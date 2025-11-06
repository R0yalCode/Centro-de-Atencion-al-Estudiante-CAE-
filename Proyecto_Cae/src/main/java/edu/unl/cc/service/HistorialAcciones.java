package edu.unl.cc.service;

import edu.unl.cc.estructuras.PilaAcciones;
import edu.unl.cc.modelo.Accion;
import edu.unl.cc.modelo.Caso;

public class HistorialAcciones {
    private final PilaAcciones pilaUndo = new PilaAcciones();
    private final PilaAcciones pilaRedo = new PilaAcciones();
    private final CasoManager casoManager;

    public HistorialAcciones(CasoManager casoManager) {
        this.casoManager = casoManager;
    }

    public void registrar(Accion accion) {
        pilaUndo.registrar(accion);
        pilaRedo.limpiar();
    }

    public void deshacer() {
        Accion accion = pilaUndo.deshacer();
        Caso caso = casoManager.getCasoActual();

        if (accion == null) {
            System.out.println("No hay acciones para deshacer.");
            return;
        }

        if (caso == null || accion.getCasoId() != caso.getId()) {
            System.out.println("La acción no corresponde al caso actual.");
            return;
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
    }

    public void rehacer() {
        Accion accion = pilaRedo.deshacer();
        Caso caso = casoManager.getCasoActual();

        if (accion == null) {
            System.out.println("No hay acciones para rehacer.");
            return;
        }

        if (caso == null || accion.getCasoId() != caso.getId()) {
            System.out.println("La acción no corresponde al caso actual.");
            return;
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
    }
}
