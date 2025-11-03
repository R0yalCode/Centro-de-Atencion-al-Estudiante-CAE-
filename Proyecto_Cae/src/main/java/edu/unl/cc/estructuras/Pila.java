package edu.unl.cc.estructuras;
/**
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.0
 */
import edu.unl.cc.dominio.Accion;
// import java.util.ArrayDeque;
// import java.util.Deque;
import java.util.ArrayList;
import java.util.List;

public class Pila {
    private final List<Accion> acciones = new ArrayList<>();
    //private Deque<Accion> stack = new ArrayDeque<>();

    public void registrarAccion(Accion a) {
        if (a != null) acciones.add(a);
    }

    public Accion deshacer() {
        if (acciones.isEmpty()) return null;
        return acciones.remove(acciones.size() - 1);
    }

    public boolean estaVacia() {
        return acciones.isEmpty();
    }

    public void limpiar() {
        acciones.clear();
    }

    // Nuevo: devuelve la cantidad de acciones guardadas
    public int tamanio() {
        return acciones.size();
    }


}
