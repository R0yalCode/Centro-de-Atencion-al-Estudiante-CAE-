package edu.unl.cc.service;

import edu.unl.cc.modelo.Caso;
import edu.unl.cc.estructuras.Nodo;

public class NotaManager {
    private final CasoManager casoManager;

    public NotaManager(CasoManager casoManager) {
        this.casoManager = casoManager;
    }

    public void agregarNota(String texto) {
        Caso caso = casoManager.getCasoActual();
        if (caso == null) {
            System.out.println("No hay caso en atención.");
            return;
        }

        caso.agregarNota(texto);
        System.out.println("Nota agregada.");
    }

    public String eliminarNotaPorIndice(int indice) {
        Caso caso = casoManager.getCasoActual();
        if (caso == null || caso.getNotas().estaVacia()) return null;

        return caso.getNotas().eliminarPorIndiceYObtenerTexto(indice - 1);
    }

    public void mostrarNotasActual() {
        Caso caso = casoManager.getCasoActual();
        if (caso == null || caso.getNotas().estaVacia()) {
            System.out.println("No hay notas en el caso actual.");
            return;
        }

        System.out.println("Notas del caso actual:");
        Nodo actual = caso.getNotas().getPrincipal();
        int index = 1;
        while (actual != null) {
            System.out.println(index + ". " + actual.getDato());
            actual = actual.getSiguiente();
            index++;
        }
        System.out.println("0. Cancelar");
    }

    public boolean casoActualSinNotas() {
        Caso caso = casoManager.getCasoActual();
        return caso == null || caso.getNotas().estaVacia();
    }
}