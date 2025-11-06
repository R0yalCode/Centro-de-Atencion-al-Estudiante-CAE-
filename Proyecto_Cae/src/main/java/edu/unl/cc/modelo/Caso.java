package edu.unl.cc.modelo;

import edu.unl.cc.estructuras.ListaNotas;
import edu.unl.cc.estructuras.Nodo;
import java.util.ArrayList;
import java.util.List;

public class Caso {
    private final int id;
    private final String estudiante;
    private EstadoCaso estado;
    private final ListaNotas notas;
    private final boolean urgente;

    public Caso(int id, String estudiante, boolean urgente) {
        this.id = id;
        this.estudiante = estudiante;
        this.estado = EstadoCaso.EN_COLA;
        this.notas = new ListaNotas();
        this.urgente = urgente;
    }

    public int getId() {
        return id;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public EstadoCaso getEstado() {
        return estado;
    }

    public void cambiarEstado(EstadoCaso nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public void agregarNota(String texto) {
        if (texto == null || texto.trim().isEmpty()) return;
        notas.insertar(new Nodo(texto.trim()));
    }

    public void eliminarNota(String texto) {
        notas.eliminarPrimeraCoincidencia(texto.trim());
    }

    public List<String> obtenerNotas() {
        List<String> resultado = new ArrayList<>();
        Nodo actual = notas.getPrincipal();
        while (actual != null) {
            resultado.add(actual.getDato());
            actual = actual.getSiguiente();
        }
        return resultado;
    }

    public ListaNotas getNotas() {
        return notas;
    }

    public boolean isUrgente() {
        return urgente;
    }


    @Override
    public String toString() {
        return "Caso #" + id + " - " + estudiante + "\n" +
                "Estado: " + estado + "\n" +
                "Urgente: " + (urgente ? "Sí" : "No") + "\n" +
                "Notas: " + (obtenerNotas().isEmpty() ? "Sin notas" : String.join(", ", obtenerNotas()));
    }
}