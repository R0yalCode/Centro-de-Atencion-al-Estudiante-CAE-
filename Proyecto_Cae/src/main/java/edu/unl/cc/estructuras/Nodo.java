package edu.unl.cc.estructuras;

/**
 * Esta clase representa un nodo dentro de una lista enlazada simple.
 * Cada nodo contiene un dato de tipo texto y una referencia al siguiente nodo
 *
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.1
 */
public class Nodo {

    private final String dato;
    private Nodo siguiente; // Referencia al siguiente nodo

    /**
     * Constructor que inicializa el nodo con un dato
     * El siguiente nodo se establece como null por defecto
     * @param dato Texto que se desea almacenar en el nodo
     */
    public Nodo(String dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public String getDato() {
        return dato;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
}