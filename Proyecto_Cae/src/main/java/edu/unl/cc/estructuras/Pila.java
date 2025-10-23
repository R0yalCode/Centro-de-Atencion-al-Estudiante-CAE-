package edu.unl.cc.estructuras;
/**
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.0
 */
import edu.unl.cc.dominio.Accion;
import java.util.ArrayDeque;
import java.util.Deque;

public class Pila {
    private Deque<Accion> stack = new ArrayDeque<>();

    public void registrarAccion(Accion a) {
        if (a != null) stack.push(a);
    }

    public Accion deshacer() {
        return stack.isEmpty() ? null : stack.pop();
    }

    public boolean estaVacia() {
        return stack.isEmpty();
    }

    public void limpiar() {
        stack.clear();
    }


}
