package edu.unl.cc.estructuras;

/**
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.0
 */

public class Nodo {
    private String dato; // Contenido textual del nodo
    private Nodo siguiente; // Referencia al siguiente nodo

    // Constructor que recibe el texto de la nota
    public Nodo(String dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    // Devuelve el contenido del nodo
    public String getDato() {
        return dato;
    }

    // Devuelve el siguiente nodo
    public Nodo getSiguiente() {
        return siguiente;
    }

    // Asigna el siguiente nodo
    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
}