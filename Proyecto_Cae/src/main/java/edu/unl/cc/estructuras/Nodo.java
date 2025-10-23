package edu.unl.cc.estructuras;
/**
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.0
 * */
import edu.unl.cc.dominio.Nota;

public class Nodo {
    public Nota nota;
    public Nodo siguiente;

    public Nodo(Nota nota) {
        this.nota = nota;
        this.siguiente = null;
    }
}