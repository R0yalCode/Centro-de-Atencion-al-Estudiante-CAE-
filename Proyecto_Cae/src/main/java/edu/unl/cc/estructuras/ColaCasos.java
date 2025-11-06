package edu.unl.cc.estructuras;

import edu.unl.cc.modelo.Caso;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representa una cola de atención para casos estudiantiles.
 * Utiliza una estructura enlazada para almacenar los casos en orden de llegada.
 * Permite agregar casos, atender el siguiente, verificar si está vacía y mostrar todos los casos y obtenerlos como lista.
 *
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.1
 */
public class ColaCasos {

    private NodoCaso frente;
    private NodoCaso fin;

    /**
     * Clase interna que representa un nodo en la cola
     * Cada nodo contiene un caso y una referencia al siguiente nodo
     */
    private static class NodoCaso {
        Caso caso;
        NodoCaso siguiente;

        NodoCaso(Caso caso) {
            this.caso = caso;
        }
    }

    /**
     * Agrega un nuevo caso al final de la cola
     * @param nuevo Caso que se desea agregar
     */
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

    /**
     * Atiende el caso que está al frente de la cola
     * Lo elimina de la cola y lo devuelve
     * @return Caso atendido o null si la cola está vacía
     */
    public Caso atender() {
        if (frente == null) return null;
        Caso caso = frente.caso;
        frente = frente.siguiente;
        if (frente == null) fin = null;
        return caso;
    }

    /**
     * Verifica si la cola está vacía
     * @return true si no hay casos en la cola, false si hay al menos uno
     */
    public boolean estaVacia() {
        return frente == null;
    }

    /**
     * Muestra por consola todos los casos en la cola
     * Se utiliza principalmente para depuración
     */
    public void mostrar() {
        NodoCaso actual = frente;
        while (actual != null) {
            System.out.println(actual.caso);
            actual = actual.siguiente;
        }
    }

    /**
     * Devuelve todos los casos en la cola como una lista
     * @return Lista de casos en orden de llegada
     */
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