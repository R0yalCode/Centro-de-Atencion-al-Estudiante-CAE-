package edu.unl.cc.estructuras;

/**
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.1
 */
import edu.unl.cc.modelo.Caso;

import java.util.ArrayList;
import java.util.List;

public class ColaCasos {
    private NodoCaso frente;
    private NodoCaso fin;

    private static class NodoCaso {
        Caso caso;
        NodoCaso siguiente;

        NodoCaso(Caso caso) {
            this.caso = caso;
        }
    }

    public void agregar(Caso nuevo) {
        NodoCaso nodo = new NodoCaso(nuevo);
        if (frente == null) {
            frente = nodo;
            fin = nodo;
        } else {
            fin.siguiente = nodo;
            fin = nodo;
        }
    }

    public Caso atender() {
        if (frente == null) return null;
        Caso caso = frente.caso;
        frente = frente.siguiente;
        if (frente == null) fin = null;
        return caso;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public void mostrar() {
        NodoCaso actual = frente;
        while (actual != null) {
            System.out.println(actual.caso);
            actual = actual.siguiente;
        }
    }

    public List<Caso> getTodos() {
        List<Caso> resultado = new ArrayList<>();
        NodoCaso actual = frente;
        while (actual != null) {
            resultado.add(actual.caso);
            actual = actual.siguiente;
        }
        return resultado;
    }
}