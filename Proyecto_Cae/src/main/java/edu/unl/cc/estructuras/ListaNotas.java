package edu.unl.cc.estructuras;

/**
 * Clase que representa una lista enlazada de notas enlazadas a un caso
 * Permite insertar, eliminar por coincidencia o por índice, y recorrer las notas
 *
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.1
 */
public class ListaNotas {
    private Nodo principal;

    public ListaNotas() {
        this.principal = null;
    }

    /**
     * Inserta una nueva nota al inicio de la lista
     * @param nuevo Nodo que contiene el texto de la nota
     */
    public void insertar(Nodo nuevo) {
        nuevo.setSiguiente(principal);
        principal = nuevo;
    }

    /**
     * Elimina la primera nota que coincida exactamente con el texto dado
     * @param texto Texto de la nota a eliminar.
     */
    public void eliminarPrimeraCoincidencia(String texto) {
        if (principal == null || texto == null) return;

        if (principal.getDato().equals(texto)) {
            principal = principal.getSiguiente();
            return;
        }

        Nodo anterior = principal;
        Nodo actual = principal.getSiguiente();

        while (actual != null) {
            if (actual.getDato().equals(texto)) {
                anterior.setSiguiente(actual.getSiguiente());
                return;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }
    }

    /**
     * Elimina la nota ubicada en la posición indicada y devuelve su texto
     * @param indice Posición de la nota a eliminar
     * @return Texto de la nota eliminada
     */
    public String eliminarPorIndiceYObtenerTexto(int indice) {
        if (indice < 0) return null;

        Nodo actual = principal;
        Nodo anterior = null;
        int contador = 0;

        while (actual != null) {
            if (contador == indice) {
                String texto = actual.getDato();
                if (anterior == null) {
                    principal = actual.getSiguiente();
                } else {
                    anterior.setSiguiente(actual.getSiguiente());
                }
                return texto;
            }
            anterior = actual;
            actual = actual.getSiguiente();
            contador++;
        }
        return null;
    }

    /**
     * Verifica si la lista de notas está vacía
     * @return true si no hay notas o si no false si hay al menos una
     */
    public boolean estaVacia() {
        return principal == null;
    }

    /**
     * Devuelve el nodo principal de la lista
     * @return Nodo cabeza de la lista
     */
    public Nodo getPrincipal() {
        return principal;
    }

    /**
     * Devuelve una representación en texto de todas las notas
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Nodo actual = principal;
        while (actual != null) {
            sb.append("- ").append(actual.getDato()).append("\n");
            actual = actual.getSiguiente();
        }
        return sb.toString();
    }
}