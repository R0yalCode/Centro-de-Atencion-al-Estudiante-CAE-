package edu.unl.cc.estructuras;
/**
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.0
 * */
import edu.unl.cc.dominio.Caso;
import java.util.LinkedList;

public class Cola {
    private LinkedList<Caso> cola = new LinkedList<>();

    public void agregarCaso(Caso caso) {
        cola.addLast(caso);
    }

    public Caso atenderCaso() {
        return cola.pollFirst(); // saca el primero
    }

    public void mostrarCasosEnEspera() {
        System.out.println("Casos en espera:");
        for (Caso c : cola) {
            System.out.println("- Caso " + c.getId() + ": " + c.getEstudiante());
        }
    }

    public boolean estaVacia() {
        return cola.isEmpty();
    }

    @Override
    public String toString() {
        if (cola.isEmpty()) return "- (sin casos en espera)";
        StringBuilder sb = new StringBuilder();
        for (Caso c : cola) {
            sb.append("- Caso ").append(c.getId()).append(": ").append(c.getEstudiante()).append("\n");
        }
        return sb.toString();
    }
}
