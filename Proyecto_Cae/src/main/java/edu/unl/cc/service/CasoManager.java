package edu.unl.cc.service;

import edu.unl.cc.estructuras.ColaCasos;
import edu.unl.cc.exception.NombreInvalidoException;
import edu.unl.cc.modelo.Caso;
import edu.unl.cc.modelo.EstadoCaso;

import java.util.ArrayList;
import java.util.List;

public class CasoManager {
    private final ColaCasos colaNormal = new ColaCasos();
    private final ColaCasos colaUrgente = new ColaCasos();
    private final List<Caso> casosFinalizados = new ArrayList<>();
    private Caso casoActual;
    private int contadorId = 1;

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

    public Caso getCasoActual() {
        return casoActual;
    }

    public List<Caso> getCasosFinalizados() {
        return casosFinalizados;
    }

    private boolean esNombreValido(String nombre) {
        return nombre != null && nombre.trim().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{2,}");
    }

    public void validarNombre(String nombre) throws NombreInvalidoException {
        if (!esNombreValido(nombre)) {
            throw new NombreInvalidoException("Nombre inválido. Solo se permiten letras y mínimo dos.");
        }
    }

    public List<Caso> getCasosEnCola() {
        List<Caso> enCola = new ArrayList<>();
        enCola.addAll(colaNormal.getTodos());
        enCola.addAll(colaUrgente.getTodos());
        return enCola;
    }
}
