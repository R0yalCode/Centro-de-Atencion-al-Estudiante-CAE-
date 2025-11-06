package edu.unl.cc.service;

import edu.unl.cc.exception.NombreInvalidoException;
import edu.unl.cc.modelo.Caso;
import edu.unl.cc.modelo.EstadoCaso;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Esta clase representa el menú de interacción con el usuario
 * Permite recibir nuevos casos, cambiar estados, gestionar notas y consultar el historial de tickets registrados
 *
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.1
 */
public class MenuCAE {

    // Atributos
    private final GestorCAE gestor; // Coordina las operaciones del sistema
    private final Scanner scanner;  // Para la entrada de datos por consola

    // Constructor
    public MenuCAE(GestorCAE gestor, Scanner scanner) {
        this.gestor = gestor;
        this.scanner = scanner;
    }

    /**
     * Solicita al usuario el nombre del estudiante y pregunta si el caso es urgente
     * Despues manda el caso al sistema
     */
    public void recibirCaso() {
        String nombre;
        while (true) {
            System.out.print("Nombre del estudiante: ");
            nombre = scanner.nextLine();
            try {
                gestor.validarNombre(nombre);
                break;
            } catch (NombreInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }

        boolean esUrgente;
        while (true) {
            System.out.print("¿Es urgente? (s/n): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            if (respuesta.equals("s")) {
                esUrgente = true;
                break;
            } else if (respuesta.equals("n")) {
                esUrgente = false;
                break;
            } else {
                System.out.println("Entrada inválida. Por favor escriba 's' para sí o 'n' para no.");
            }
        }
        gestor.recibirCaso(nombre, esUrgente);
    }

    /**
     * Permite al usuario cambiar el estado del caso que está siendo atendido
     */
    public void cambiarEstado() {
        if (gestor.getCasoActual() == null) {
            System.out.println("No se puede cambiar el estado porque no se está atendiendo ningún caso.");
            return;
        }

        EstadoCaso[] estados = EstadoCaso.values();
        System.out.println("Estados disponibles:");
        for (int i = 0; i < estados.length; i++) {
            System.out.println((i + 1) + ". " + estados[i]);
        }

        while (true) {
            System.out.print("Seleccione el número del nuevo estado: ");
            String entrada = scanner.nextLine().trim();

            try {
                int seleccion = Integer.parseInt(entrada);
                if (seleccion >= 1 && seleccion <= estados.length) {
                    EstadoCaso nuevoEstado = estados[seleccion - 1];
                    gestor.cambiarEstado(nuevoEstado);
                    break;
                } else {
                    System.out.println("Número fuera de rango. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor ingrese un número válido.");
            }
        }
    }

    /**
     * Muestra todos los tickets registrados y permite consultar el historial de uno de acuerdo a su ID
     */
    public void mostrarHistorialTickets() {
        List<Caso> todos = new ArrayList<>();
        todos.addAll(gestor.getCasosFinalizados());

        Caso actual = gestor.getCasoActual();
        if (actual != null) todos.add(actual);

        todos.addAll(gestor.getCasosEnCola());

        if (todos.isEmpty()) {
            System.out.println("No hay tickets registrados.");
            return;
        }
        todos.sort((a, b) -> Integer.compare(a.getId(), b.getId()));

        boolean seguirConsultando = true;
        while (seguirConsultando) {
            System.out.println("\nTickets disponibles:");
            for (Caso c : todos) {
                System.out.println("→ Ticket #" + c.getId() + " (" + c.getEstado() + ")");
            }
            System.out.print("Ingrese el ID del ticket que desea consultar: ");
            String entrada = scanner.nextLine().trim();
            try {
                int idBuscado = Integer.parseInt(entrada);
                gestor.mostrarHistorialDeTicket(idBuscado);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Debe ingresar un número.");
            }

            while (true) {
                System.out.print("\n¿Desea consultar otro historial? (s/n): ");
                String respuesta = scanner.nextLine().trim().toLowerCase();
                if (respuesta.equals("s")) break;
                else if (respuesta.equals("n")) {
                    seguirConsultando = false;
                    System.out.println("Regresando al menú principal.");
                    break;
                } else {
                    System.out.println("Entrada inválida. Por favor escriba 's' para sí o 'n' para no.");
                }
            }
        }
    }

    /**
     * Mediante el ID borramos su .txt creado
     */
    public void borrarArchivoTicket() {
        System.out.print("Ingrese el ID del ticket cuyo archivo desea borrar: ");
        String entrada = scanner.nextLine().trim();

        try {
            int id = Integer.parseInt(entrada);
            gestor.borrarArchivoDeTicket(id);
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Debe ingresar un número.");
        }
    }

    /**
     * Muestra el menu de las notas del caso actual
     * Permite agregar, eliminar, mostrar, deshacer y rehacer notas
     */
    public void menuNotas() {
        if (gestor.getCasoActual() == null) {
            System.out.println("No se pueden gestionar notas porque no hay un caso en atención.");
            return;
        }

        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Menú de Notas ---");
            System.out.println("1 = Agregar nota");
            System.out.println("2 = Eliminar nota por índice");
            System.out.println("3 = Mostrar notas actuales");
            System.out.println("4 = Deshacer");
            System.out.println("5 = Rehacer");
            System.out.println("6 = Salir");

            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    System.out.print("Ingrese la nota: ");
                    String nota = scanner.nextLine();
                    gestor.agregarNota(nota);
                    break;
                case "2":
                    if (gestor.casoActualSinNotas()) {
                        System.out.println("No hay notas en el caso actual.");
                        break;
                    }
                    gestor.mostrarNotasActual(true);
                    System.out.print("Seleccione el número de la nota a eliminar (0 para cancelar): ");
                    try {
                        int seleccion = Integer.parseInt(scanner.nextLine());
                        if (seleccion == 0) {
                            System.out.println("Eliminación cancelada. Regresando al menú de notas.");
                            break;
                        }

                        String notaEliminada = gestor.eliminarNotaPorIndice(seleccion - 1);
                        if (notaEliminada != null) {
                            System.out.println("→ Nota eliminada: " + notaEliminada);
                            if (gestor.casoActualSinNotas()) {
                                System.out.println("Ya no quedan más notas.");
                            }
                        } else {
                            System.out.println("No se encontró ninguna nota con ese número.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida. Debe ingresar un número.");
                    }
                    break;
                case "3":
                    gestor.mostrarNotasActual(false);
                    break;
                case "4":
                    String notaDeshecha = gestor.deshacer();
                    if (notaDeshecha != null) {
                        System.out.println("→ Nota eliminada: " + notaDeshecha);
                    } else {
                        System.out.println("No hay acciones para deshacer.");
                    }
                    break;
                case "5":
                    String notaRehecha = gestor.rehacer();
                    if (notaRehecha != null) {
                        System.out.println("→ Nota restaurada: " + notaRehecha);
                    } else {
                        System.out.println("No hay acciones para rehacer.");
                    }
                    break;
                case "6":
                    System.out.println("Saliendo del módulo de notas...");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }
}