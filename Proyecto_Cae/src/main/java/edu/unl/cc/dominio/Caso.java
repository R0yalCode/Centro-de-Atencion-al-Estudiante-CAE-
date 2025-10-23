package edu.unl.cc.dominio;

/**
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.0
 */

import edu.unl.cc.estructuras.Lista;
import edu.unl.cc.estructuras.Nodo;

public class Caso {
    private int id; // Identificador único del caso
    private String estudiante; // Nombre del estudiante
    private TipoEstado estado; // Estado actual del caso
    private Lista notas; // Lista de notas asociadas al caso

    // Constructor que inicializa el caso con ID y nombre del estudiante
    public Caso(int id, String estudiante) {
        this.id = id;
        this.estudiante = estudiante;
        this.estado = TipoEstado.EN_COLA; // Estado inicial del caso
        this.notas = new Lista(); // Inicializamos la lista de notas
    }

    public int getId() {
        return id;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public TipoEstado getEstado() {
        return estado;
    }

    public void setEstado(TipoEstado nuevoEstado) {
        this.estado = nuevoEstado;
    }

    // Método para agregar una nota textual al caso
    public void agregarNota(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            System.out.println("La nota no puede estar vacía."); // Validación de entrada
            return;
        }
        notas.insertar(new Nodo(texto)); // Insertamos el texto como dato del nodo
    }

    // Método para eliminar la primera nota que coincida con el texto
    public void eliminarNota(String texto) {
        notas.eliminarPrimeraCoincidencia(texto);
    }

    // Método para verificar si la lista de notas está vacía
    public boolean notasVacias() {
        return notas.estaVacia();
    }

    // Para mostrar en consola
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Caso #").append(id).append(" - ").append(estudiante).append("\n");
        sb.append("Estado final: ").append(estado).append("\n");
        sb.append("Notas registradas:\n");
        sb.append(notasVacias() ? "- (sin notas)\n" : notas.toString()); // ✅ Mostramos notas si existen
        return sb.toString();
    }
}