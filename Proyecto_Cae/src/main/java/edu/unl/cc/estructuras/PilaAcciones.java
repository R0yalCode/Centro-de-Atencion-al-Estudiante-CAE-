package edu.unl.cc.estructuras;
/**
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.0
 */
import edu.unl.cc.modelo.Accion;

public class PilaAcciones {
    private NodoAccion cima;

    private static class NodoAccion {
        Accion accion;
        NodoAccion anterior;

        NodoAccion(Accion accion) {
            this.accion = accion;
        }
    }

    /**
     *
     * @param accion
     */
    public void registrar(Accion accion) {
        NodoAccion nuevo = new NodoAccion(accion);
        nuevo.anterior = cima;
        cima = nuevo;
    }

    public Accion deshacer() {
        if (cima == null) return null;
        Accion accion = cima.accion;
        cima = cima.anterior;
        return accion;
    }

    public void limpiar() {
        cima = null;
    }

    public boolean estaVacia() {
        return cima == null;
    }
}


