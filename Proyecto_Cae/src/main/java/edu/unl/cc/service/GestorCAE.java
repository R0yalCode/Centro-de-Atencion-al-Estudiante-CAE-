package edu.unl.cc.service;

import edu.unl.cc.exception.NombreInvalidoException;
import edu.unl.cc.modelo.Accion;
import edu.unl.cc.modelo.Caso;
import edu.unl.cc.modelo.EstadoCaso;

import java.util.ArrayList;
import java.util.List;

public class GestorCAE {
    private final CasoManager casoManager = new CasoManager();
    private final NotaManager notaManager = new NotaManager(casoManager);
    private final HistorialAcciones historial = new HistorialAcciones(casoManager);

    public void recibirCaso(String nombre, boolean esUrgente) {
        try {
            Caso nuevo = casoManager.recibirCaso(nombre, esUrgente);
            System.out.println("Caso recibido: " + nuevo.getId() + " - " + nuevo.getEstudiante());
        } catch (NombreInvalidoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void atenderSiguienteCaso() {
        casoManager.atenderSiguienteCaso();
    }

    public void agregarNota(String texto) {
        Caso caso = casoManager.getCasoActual();
        if (caso == null) {
            System.out.println("No se puede agregar nota. No hay caso en atención.");
            return;
        }

        notaManager.agregarNota(texto);
        historial.registrar(new Accion(caso.getId(), Accion.Tipo.AGREGAR_NOTA, texto));
    }

    public String eliminarNotaPorIndice(int indice) {
        Caso caso = casoManager.getCasoActual();
        if (caso == null || notaManager.casoActualSinNotas()) {
            System.out.println("No hay notas para eliminar.");
            return null;
        }

        String notaEliminada = notaManager.eliminarNotaPorIndice(indice);
        if (notaEliminada != null) {
            historial.registrar(new Accion(caso.getId(), Accion.Tipo.ELIMINAR_NOTA, notaEliminada));
            System.out.println("→ Nota eliminada: " + notaEliminada);
        } else {
            System.out.println("Índice inválido.");
        }

        return notaEliminada;
    }

    public void mostrarNotasActual() {
        notaManager.mostrarNotasActual();
    }

    public boolean casoActualSinNotas() {
        return notaManager.casoActualSinNotas();
    }

    public void cambiarEstado(EstadoCaso nuevoEstado) {
        Caso caso = casoManager.getCasoActual();
        if (caso == null) {
            System.out.println("No hay caso en atención.");
            return;
        }

        EstadoCaso estadoAnterior = caso.getEstado();
        casoManager.cambiarEstado(nuevoEstado);

        if (estadoAnterior != nuevoEstado) {
            historial.registrar(new Accion(caso.getId(), Accion.Tipo.CAMBIO_ESTADO, estadoAnterior, nuevoEstado));
        }
    }

    public void deshacer() {
        historial.deshacer();
    }

    public void rehacer() {
        historial.rehacer();
    }

    public void finalizarCaso() {
        casoManager.finalizarCaso();
    }

    public void mostrarCasosFinalizados() {
        List<Caso> finalizados = casoManager.getCasosFinalizados();
        if (finalizados.isEmpty()) {
            System.out.println("No hay casos finalizados.");
            return;
        }

        for (Caso c : finalizados) {
            System.out.println(c);
            System.out.println("---------------");
        }
    }

    public Caso getCasoActual() {
        return casoManager.getCasoActual();
    }

    public List<Caso> getCasosFinalizados() {
        return casoManager.getCasosFinalizados();
    }

    public void validarNombre(String nombre) throws NombreInvalidoException {
        casoManager.validarNombre(nombre);
    }

    public void mostrarHistorialDeTicket(int id) {
        List<Caso> todos = new ArrayList<>();
        todos.addAll(casoManager.getCasosFinalizados());

        Caso actual = casoManager.getCasoActual();
        if (actual != null) todos.add(actual);

        todos.addAll(casoManager.getCasosEnCola());
        todos.sort((a, b) -> Integer.compare(a.getId(), b.getId()));

        Caso buscado = todos.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);

        if (buscado == null) {
            System.out.println("No se encontró ningún ticket con ese ID.");
            return;
        }

        System.out.println("\nHistorial del Ticket #" + buscado.getId());
        System.out.println("Estudiante: " + buscado.getEstudiante());
        System.out.println("Estado actual: " + buscado.getEstado());
        System.out.println("Urgente: " + (buscado.isUrgente() ? "Sí" : "No"));

        List<String> notas = buscado.obtenerNotas();
        if (notas.isEmpty()) {
            System.out.println("Notas: Sin notas registradas.");
        } else {
            System.out.println("Notas:");
            for (int i = 0; i < notas.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + notas.get(i));
            }
        }
    }

    public List<Caso> getCasosEnCola() {
        return casoManager.getCasosEnCola();
    }

}