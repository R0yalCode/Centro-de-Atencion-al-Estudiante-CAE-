package edu.unl.cc.service;
/**
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.0
 */
import edu.unl.cc.dominio.*;
import edu.unl.cc.estructuras.*;
import java.util.ArrayList;
import java.util.List;

public class GestorCAE {
    private Cola cola = new Cola();
    private Pila pilaUndo = new Pila();
    private Pila pilaRedo = new Pila();
    private Caso casoActual = null;
    private List<Caso> casosFinalizados = new ArrayList<>();
    private int contadorId = 1;

    // Recibe un nuevo caso y lo agrega a la cola
    public void recibirCaso(String nombreEstudiante) {
        Caso nuevo = new Caso(contadorId++, nombreEstudiante);
        cola.agregarCaso(nuevo);
        System.out.println("Caso recibido: " + nuevo.getId() + " - " + nombreEstudiante);
    }

    // Atiende el siguiente caso en la cola
    public void atenderSiguienteCaso() {
        if (cola.estaVacia()) {
            System.out.println("No hay casos en espera.");
            return;
        }
        casoActual = cola.atenderCaso();
        casoActual.setEstado(TipoEstado.EN_ATENCION);
        pilaUndo = new Pila();
        pilaRedo = new Pila();
        System.out.println("Atendiendo caso: " + casoActual.getId() + " - " + casoActual.getEstudiante());
    }

    // Agrega una nota al caso actual
    public void agregarNota(String texto) {
        if (casoActual == null) {
            System.out.println("No hay caso en atención.");
            return;
        }
        casoActual.agregarNota(texto);
        pilaUndo.registrarAccion(new Accion(casoActual.getId(), Accion.Tipo.AGREGAR_NOTA, texto));
        pilaRedo.limpiar();
        System.out.println("Nota agregada.");
    }

    // Elimina una nota del caso actual
    public void eliminarNota(String texto) {
        if (casoActual == null) {
            System.out.println("No hay caso en atención.");
            return;
        }
        casoActual.eliminarNota(texto);
        pilaUndo.registrarAccion(new Accion(casoActual.getId(), Accion.Tipo.ELIMINAR_NOTA, texto));
        pilaRedo.limpiar();
        System.out.println("Nota eliminada (si existía).");
    }

    // Cambia el estado del caso actual
    public void cambiarEstado(TipoEstado nuevoEstado) {
        if (casoActual == null) {
            System.out.println("No hay caso en atención.");
            return;
        }
        TipoEstado anterior = casoActual.getEstado();
        casoActual.setEstado(nuevoEstado);
        pilaUndo.registrarAccion(new Accion(casoActual.getId(), Accion.Tipo.CAMBIO_ESTADO, anterior, nuevoEstado));
        pilaRedo.limpiar();
        System.out.println("Estado cambiado a: " + nuevoEstado);
    }

    // Deshace la última acción realizada
    public void deshacer() {
        if (pilaUndo.estaVacia()) {
            System.out.println("Nada que deshacer.");
            return;
        }
        Accion ultima = pilaUndo.deshacer();
        if (ultima == null) {
            System.out.println("Nada que deshacer.");
            return;
        }
        pilaRedo.registrarAccion(ultima);

        if (casoActual == null || casoActual.getId() != ultima.getCasoId()) {
            System.out.println("La acción no pertenece al caso en atención. (soporte pendiente)");
            return;
        }

        switch (ultima.getTipo()) {
            case AGREGAR_NOTA:
                casoActual.eliminarNota(ultima.getDato());
                System.out.println("Deshecho: agregar nota.");
                break;
            case ELIMINAR_NOTA:
                casoActual.agregarNota(ultima.getDato());
                System.out.println("Deshecho: eliminar nota.");
                break;
            case CAMBIO_ESTADO:
                casoActual.setEstado(ultima.getEstadoAnterior());
                System.out.println("Deshecho: cambio de estado.");
                break;
        }
    }

    // Rehace la última acción deshecha
    public void rehacer() {
        if (pilaRedo.estaVacia()) {
            System.out.println("Nada que rehacer.");
            return;
        }
        Accion accion = pilaRedo.deshacer();
        if (accion == null) {
            System.out.println("Nada que rehacer.");
            return;
        }
        pilaUndo.registrarAccion(accion);

        if (casoActual == null || casoActual.getId() != accion.getCasoId()) {
            System.out.println("La acción no pertenece al caso en atención. (soporte pendiente)");
            return;
        }

        switch (accion.getTipo()) {
            case AGREGAR_NOTA:
                casoActual.agregarNota(accion.getDato());
                System.out.println("Rehecho: agregar nota.");
                break;
            case ELIMINAR_NOTA:
                casoActual.eliminarNota(accion.getDato());
                System.out.println("Rehecho: eliminar nota.");
                break;
            case CAMBIO_ESTADO:
                casoActual.setEstado(accion.getEstadoNuevo());
                System.out.println("Rehecho: cambio de estado.");
                break;
        }
    }

    // Finaliza el caso actual y lo guarda en el historial
    public void finalizarCaso() {
        if (casoActual == null) {
            System.out.println("No hay caso en atención.");
            return;
        }
        casoActual.setEstado(TipoEstado.COMPLETADO);
        casosFinalizados.add(casoActual);
        System.out.println("Caso finalizado: " + casoActual.getId());
        casoActual = null;
        pilaUndo = new Pila();
        pilaRedo = new Pila();
    }

    // Muestra todos los casos finalizados con sus notas
    public void mostrarHistorialFinalizados() {
        if (casosFinalizados.isEmpty()) {
            System.out.println("No hay casos finalizados.");
            return;
        }
        for (Caso c : casosFinalizados) {
            System.out.println(c);
            System.out.println("---------------");
        }
    }

    // Muestra los casos que están en espera
    public void mostrarCasosEnEspera() {
        cola.mostrarCasosEnEspera();
    }
}