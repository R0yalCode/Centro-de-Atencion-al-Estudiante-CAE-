package edu.unl.cc.estructuras;
/**
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.0
 * */
import edu.unl.cc.dominio.Nota;

public class Lista {
    private Nodo principal;

    public void insertarInicio(Nota nota) {
        Nodo nuevo = new Nodo(nota);
        nuevo.siguiente = principal;
        principal = nuevo;
    }

    public boolean eliminarPrimeraCoincidencia(String texto) {
        if (principal == null) {
            return false;
        }
        if (principal.nota.getTexto().equals(texto)) {
            principal = principal.siguiente;
            return true;
        }
        Nodo actual = principal;
        while (actual.siguiente != null) {
            if (actual.siguiente.nota.getTexto().equals(texto)) {
                actual.siguiente = actual.siguiente.siguiente;
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public void mostrarNotas() {
        Nodo actual = principal;
        while (actual != null) {
            System.out.println("- " + actual.nota.getTexto());
            actual = actual.siguiente;
        }
    }

    public boolean estaVacia() {
        return principal == null;
    }

}