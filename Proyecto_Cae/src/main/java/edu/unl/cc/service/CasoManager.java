package edu.unl.cc.service;

import edu.unl.cc.estructuras.ColaCasos;
import edu.unl.cc.exception.NombreInvalidoException;
import edu.unl.cc.modelo.Caso;
import edu.unl.cc.modelo.EstadoCaso;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase se encarga de gestionar los casos dentro del sistema
 * Administra las colas de casos normales y urgentes, el caso que está siendo atendido, y los casos que ya han sido finalizados.
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.1
 */
public class CasoManager {

    private final ColaCasos colaNormal = new ColaCasos(); // Cola para casos normales
    private final ColaCasos colaUrgente = new ColaCasos(); // Cola para casos urgentes
    private final List<Caso> casosFinalizados = new ArrayList<>(); // Lista de casos finalizados
    private Caso casoActual; // Caso que está siendo atendido
    private int contadorId = 1; // Contador para asignar IDs únicos y evitar errores

    /**
     * Recibe un nuevo caso valida el nombre y lo agrega a la cola correspondiente
     * @param nombre Nombre del estudiante
     * @param esUrgente Indica si el caso es urgente
     * @return El caso creado
     * @throws NombreInvalidoException Si el nombre no cumple con los requisitos
     */
    public Caso recibirCaso(String nombre, boolean esUrgente) throws NombreInvalidoException {
        if (!esNombreValido(nombre)) {
            throw new NombreInvalidoException("Nombre inválido. Solo se permiten letras y mínimo dos caracteres.");
        }
        Caso nuevo = new Caso(contadorId++, nombre.trim(), esUrgente);
        if (esUrgente) {
            nuevo.cambiarEstado(EstadoCaso.URGENTE);
            colaUrgente.agregar(nuevo);
        } else {
            colaNormal.agregar(nuevo);
        }
        return nuevo;
    }

    /**
     * Atiende el siguiente caso disponible pero priorizando los casos urgentes
     * Si ya hay un caso en atención, no permite atender otro a si sea urgente
     */
    public void atenderSiguienteCaso() {
        if (casoActual != null && casoActual.getEstado() == EstadoCaso.EN_ATENCION) {
            System.out.println("Ya hay un caso en atención. Finalícelo antes de atender otro.");
            return;
        }
        if (!colaUrgente.estaVacia()) {
            casoActual = colaUrgente.atender();
            casoActual.cambiarEstado(EstadoCaso.EN_ATENCION);
            System.out.println("Caso " + casoActual.getId() + " pasa a atención (urgente).");
        } else if (!colaNormal.estaVacia()) {
            casoActual = colaNormal.atender();
            casoActual.cambiarEstado(EstadoCaso.EN_ATENCION);
            System.out.println("Atendiendo caso: " + casoActual.getId() + " - " + casoActual.getEstudiante());
        } else {
            System.out.println("No hay casos en espera.");
        }
    }

    /**
     * Cambia el estado del caso actual si está en atencion
     * @param nuevoEstado Estado al que se desea cambiar
     */
    public void cambiarEstado(EstadoCaso nuevoEstado) {
        if (casoActual == null) {
            System.out.println("No hay ningún caso en atención.");
            return;
        }
        EstadoCaso actual = casoActual.getEstado();
        if (actual == nuevoEstado) {
            System.out.println("El caso ya está en estado " + nuevoEstado + ".");
            return;
        }
        if (nuevoEstado == EstadoCaso.URGENTE && casoActual.isUrgente()) {
            System.out.println("Este caso ya fue marcado como urgente al ser ingresado.");
            return;
        }
        casoActual.cambiarEstado(nuevoEstado);
        System.out.println("Estado cambiado de " + actual + " a " + nuevoEstado + ".");
    }

    /**
     * Finaliza el caso que está siendo atendido y lo mueve a la lista de casos finalizados
     */
    public void finalizarCaso() {
        if (casoActual == null) {
            System.out.println("No hay caso en atención.");
            return;
        }
        casoActual.cambiarEstado(EstadoCaso.COMPLETADO);
        casosFinalizados.add(casoActual);
        casoActual = null;
        System.out.println("Caso finalizado.");
    }

    /**
     * Devuelve el caso que está siendo atendido actualmente.
     * @return Caso actual
     */
    public Caso getCasoActual() {
        return casoActual;
    }

    /**
     * Devuelve la lista de casos que ya han sido completados
     * @return Lista de casos finalizados
     */
    public List<Caso> getCasosFinalizados() {
        return casosFinalizados;
    }

    /**
     * Devuelve todos los casos que están en cola (tanto los normales y los urgentes)
     * @return Lista de casos en espera
     */
    public List<Caso> getCasosEnCola() {
        List<Caso> enCola = new ArrayList<>();
        enCola.addAll(colaNormal.getTodos());
        enCola.addAll(colaUrgente.getTodos());
        return enCola;
    }

    /**
     * Verifica si el nombre ingresado es válido (solo letras y mínimo dos caracteres).
     * @param nombre Nombre a validar
     * @return true si es válido si no devuelve false
     */
    private boolean esNombreValido(String nombre) {
        return nombre != null && nombre.trim().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{2,}");
    }

    /**
     * Lanza una excepción si el nombre no es válido.
     *
     * @param nombre Nombre a validar
     * @throws NombreInvalidoException Si el nombre no cumple con los requisitos
     */
    public void validarNombre(String nombre) throws NombreInvalidoException {
        if (!esNombreValido(nombre)) {
            throw new NombreInvalidoException("Nombre inválido. Solo se permiten letras y mínimo dos.");
        }
    }
}