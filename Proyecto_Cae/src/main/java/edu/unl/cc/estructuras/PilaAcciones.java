package edu.unl.cc.estructuras;

import edu.unl.cc.modelo.Accion;

/**
 * Esta clase implementa una pila enlazada para almacenar acciones realizadas sobre un caso
 * Cada acción se guarda en un nodo que apunta al anterior
 *
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.1
 */
public class PilaAcciones {

    private NodoAccion cima;

    /**
     * Clase interna que representa un nodo en la pila
     * Contiene una acción y una referencia al nodo anterior
     */
    private static class NodoAccion {
        Accion accion;
        NodoAccion anterior;

        NodoAccion(Accion accion) {
            this.accion = accion;
        }
    }

    /**
     * Agrega una nueva acción a la pila
     * Esta acción va a ser la próxima en ser deshecha si se solicita
     * @param accion Acción que se desea registrar
     */
    public void registrar(Accion accion) {
        NodoAccion nuevo = new NodoAccion(accion);
        nuevo.anterior = cima;
        cima = nuevo;
    }

    /**
     * Elimina y devuelve la última acción registrada en la pila
     * Se utiliza para deshacer una acción
     * @return Última acción registrada o si no null si la pila está vacía
     */
    public Accion deshacer() {
        if (cima == null) return null;
        Accion accion = cima.accion;
        cima = cima.anterior;
        return accion;
    }

    /**
     * Elimina todas las acciones de la pila
     * Se utiliza al registrar una nueva acción para limpiar la pila de rehacer
     */
    public void limpiar() {
        cima = null;
    }

    /**
     * Verifica si la pila está vacía.
     * @return true si no hay acciones registrada, false en caso contrario
     */
    public boolean estaVacia() {
        return cima == null;
    }
}