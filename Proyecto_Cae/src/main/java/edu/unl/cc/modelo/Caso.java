package edu.unl.cc.modelo;

import edu.unl.cc.estructuras.ListaNotas;
import edu.unl.cc.estructuras.Nodo;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representa un caso dentro del sistema de atención estudiantil
 * Cada caso contiene un identificador único, el nombre del estudiante,
 * su estado actual, una lista de notas asociadas y una marca de urgencia.
 * Y nos permite agregar y eliminar notas, cambiar el estado y consultar información.
 *
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.1
 */
public class Caso {

    // Atributos del caso
    private final int id;
    private final String estudiante;       // Nombre del estudiante asociado al caso
    private EstadoCaso estado;
    private final ListaNotas notas;        // Lista enlazada de notas asociadas al caso
    private final boolean urgente;         // Indica si el caso fue marcado como urgente

    /**
     * @param id Identificador único del caso
     * @param estudiante Nombre del estudiante
     * @param urgente Indica si el caso es urgente
     */
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

    public ListaNotas getNotas() {
        return notas;
    }

    public boolean isUrgente() {
        return urgente;
    }


    /**
     * Cambia el estado actual del caso al nuevo estado indicado
     * @param nuevoEstado Estado al que se desea cambiar
     */
    public void cambiarEstado(EstadoCaso nuevoEstado) {
        this.estado = nuevoEstado;
    }

    /**
     * Agrega una nueva nota al caso si el texto no está vacio
     * @param texto Contenido de la nota
     */
    public void agregarNota(String texto) {
        if (texto == null || texto.trim().isEmpty()) return;
        notas.insertar(new Nodo(texto.trim()));
    }

    /**
     * Elimina la primera nota que coincida exactamente con el texto indicado
     * @param texto Texto de la nota a eliminar
     */
    public void eliminarNota(String texto) {
        notas.eliminarPrimeraCoincidencia(texto.trim());
    }
    /**
     * Devuelve todas las notas del caso como una lista de cadenas de texto
     * @return Lista de notas en orden
     */
    public List<String> obtenerNotas() {
        List<String> resultado = new ArrayList<>();
        Nodo actual = notas.getPrincipal();
        while (actual != null) {
            resultado.add(actual.getDato());
            actual = actual.getSiguiente();
        }
        return resultado;
    }

    /**
     * @return Cadena con la información del caso
     */
    @Override
    public String toString() {
        return "Caso #" + id + " - " + estudiante + "\n" +
                "Estado: " + estado + "\n" +
                "Urgente: " + (urgente ? "Sí" : "No") + "\n" +
                "Notas: " + (obtenerNotas().isEmpty() ? "Sin notas" : String.join(", ", obtenerNotas()));
    }
}