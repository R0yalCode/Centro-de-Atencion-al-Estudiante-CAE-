package edu.unl.cc.estructuras;
/**
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.0
 * */
import edu.unl.cc.dominio.Caso;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class Cola {
    private final LinkedList<Caso> elementos = new LinkedList<>();
    //private LinkedList<Caso> cola = new LinkedList<>();

    public void agregarCaso(Caso c) {
        if (c != null) elementos.addLast(c);
        //cola.addLast(caso);
    }

    public Caso atenderCaso() {
        return elementos.isEmpty() ? null : elementos.removeFirst(); // saca el primero
    }

    public void mostrarCasosEnEspera() {
        if (elementos.isEmpty()) {
            System.out.println("No hay casos en espera.");
            return;
        }
        for (Caso c : elementos) {
            System.out.println(c);
            System.out.println("---------------");
        }
        /*
        System.out.println("Casos en espera:");
        for (Caso c : cola) {
            System.out.println("- Caso " + c.getId() + ": " + c.getEstudiante());
        }
        */
    }

    public boolean estaVacia() {
        return elementos.isEmpty();
    }

    // devuelve una copia de los casos en cola para lecturas (exportación, etc.)
    public List<Caso> listarCasos() {
        return new ArrayList<>(elementos);
    }

    @Override
    public String toString() {
        if (elementos.isEmpty()) return "- (sin casos en espera)";
        StringBuilder sb = new StringBuilder();
        for (Caso c : elementos) {
            sb.append("- Caso ").append(c.getId()).append(": ").append(c.getEstudiante()).append("\n");
        }
        return sb.toString();
    }
}
