package edu.unl.cc.service;

/**
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.1
 */

import java.util.Scanner;
import edu.unl.cc.exception.NombreInvalidoException;
import edu.unl.cc.modelo.EstadoCaso;

public class Main {
    public static void main(String[] args) {
        GestorCAE gestor = new GestorCAE();
        Scanner scanner = new Scanner(System.in);
        MenuCAE menu = new MenuCAE(gestor, scanner);
        int opcion;

        do {
            System.out.println("\n--- MENÚ CAE ---");
            System.out.println("1. Recibir nuevo caso");
            System.out.println("2. Atender siguiente caso");
            System.out.println("3. Agregar nota al caso actual");
            System.out.println("4. Eliminar nota del caso actual");
            System.out.println("5. Cambiar estado del caso actual");
            System.out.println("6. Deshacer última acción");
            System.out.println("7. Rehacer acción deshecha");
            System.out.println("8. Finalizar caso actual");
            System.out.println("9. Mostrar historial");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1 -> menu.recibirCaso();
                case 2 -> gestor.atenderSiguienteCaso();
                case 3 -> menu.agregarNotas();
                case 4 -> menu.eliminarNotas();
                case 5 -> menu.cambiarEstado();
                case 6 -> gestor.deshacer();
                case 7 -> gestor.rehacer();
                case 8 -> gestor.finalizarCaso();
                case 9 -> menu.mostrarHistorialTickets();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 0);

        scanner.close();
    }
}