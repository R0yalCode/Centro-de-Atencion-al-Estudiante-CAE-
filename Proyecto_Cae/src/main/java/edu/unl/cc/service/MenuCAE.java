package edu.unl.cc.service;

import edu.unl.cc.exception.NombreInvalidoException;
import edu.unl.cc.modelo.Caso;
import edu.unl.cc.modelo.EstadoCaso;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuCAE {
    private final GestorCAE gestor;
    private final Scanner scanner;

    public MenuCAE(GestorCAE gestor, Scanner scanner) {
        this.gestor = gestor;
        this.scanner = scanner;
    }

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

    public void agregarNotas() {
        if (gestor.getCasoActual() == null) {
            System.out.println("No se pueden agregar notas, debido a que no se está atendiendo ningún caso.");
            return;
        }

        boolean seguirAgregando = true;
        while (seguirAgregando) {
            System.out.print("Ingrese la nota: ");
            String nota = scanner.nextLine();
            gestor.agregarNota(nota);

            while (true) {
                System.out.print("¿Desea agregar otra nota? (s/n): ");
                String respuesta = scanner.nextLine().trim().toLowerCase();
                if (respuesta.equals("s")) break;
                else if (respuesta.equals("n")) {
                    System.out.println("Regresando al menú principal.");
                    seguirAgregando = false;
                    break;
                } else {
                    System.out.println("Entrada inválida. Por favor escriba 's' para sí o 'n' para no.");
                }
            }
        }
    }

    public void eliminarNotas() {
        if (gestor.casoActualSinNotas()) {
            System.out.println("No hay notas en el caso actual.");
            return;
        }

        while (true) {
            gestor.mostrarNotasActual();
            System.out.print("Seleccione el número de la nota a eliminar (0 para cancelar): ");
            String entrada = scanner.nextLine().trim();

            try {
                int seleccion = Integer.parseInt(entrada);
                if (seleccion == 0) {
                    System.out.println("Eliminación cancelada. Regresando al menú principal.");
                    break;
                }

                String notaEliminada = gestor.eliminarNotaPorIndice(seleccion);
                if (notaEliminada != null) {
                    System.out.println("→ Nota eliminada: " + notaEliminada);
                    if (gestor.casoActualSinNotas()) {
                        System.out.println("Ya no quedan más notas.");
                        break;
                    }
                } else {
                    System.out.println("Índice inválido. Intente nuevamente.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor ingrese un número válido.");
            }
        }
    }

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

        // Ordenar por ID
        todos.sort((a, b) -> Integer.compare(a.getId(), b.getId()));

        boolean seguirConsultando = true;
        while (seguirConsultando) {
            System.out.println("\nIDs disponibles:");
            for (Caso c : todos) {
                System.out.println("→ Ticket #" + c.getId());
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
}
