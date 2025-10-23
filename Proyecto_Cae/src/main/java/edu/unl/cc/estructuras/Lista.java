package edu.unl.cc.estructuras;
/**
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.0
 */
public class Lista {
    private Nodo principal; // Primer nodo de la lista

    // Inserta un nodo al final de la lista
    public void insertar(Nodo nuevo) {
        if (principal == null) {
            principal = nuevo; // Si la lista está vacía, el nuevo nodo es la cabeza
        } else {
            Nodo actual = principal;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente(); // Recorremos hasta el último nodo
            }
            actual.setSiguiente(nuevo); // Enlazamos el último con el nuevo
        }
    }

    // Elimina la primera nota que coincida con el texto
    public boolean eliminarPrimeraCoincidencia(String texto) {
        if (principal == null) {
            return false; // Lista vacía, no hay nada que eliminar
        }

        if (principal.getDato().equals(texto)) {
            principal = principal.getSiguiente(); // Eliminamos la cabeza
            return true;
        }

        Nodo actual = principal;
        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getDato().equals(texto)) {
                actual.setSiguiente(actual.getSiguiente().getSiguiente()); // Saltamos el nodo que coincide
                return true;
            }
            actual = actual.getSiguiente();
        }

        return false; // No se encontró coincidencia
    }

    // Muestra todas las notas en consola
    public void mostrarNotas() {
        Nodo actual = principal;
        while (actual != null) {
            System.out.println("- " + actual.getDato()); // Imprime el contenido del nodo
            actual = actual.getSiguiente();
        }
    }

    // Verifica si la lista está vacía
    public boolean estaVacia() {
        return principal == null;
    }

    // Getters y setters para el nodo principal
    public Nodo getPrincipal() {
        return principal;
    }

    public void setPrincipal(Nodo principal) {
        this.principal = principal;
    }

    // Representación textual de la lista, útil para imprimir desde Caso
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Nodo actual = principal;
        while (actual != null) {
            sb.append("- ").append(actual.getDato()).append("\n"); // Agrega cada nota al texto
            actual = actual.getSiguiente();
        }
        return sb.toString();
    }
}