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
            System.out.println("3. Agregar o eliminar notas");
            System.out.println("4. Cambiar estado del caso actual");
            System.out.println("5. Finalizar caso actual");
            System.out.println("6. Mostrar historial");
            System.out.println("7. Borrar archivo de ticket finalizado");
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
                case 3 -> menu.menuNotas();
                case 4 -> menu.cambiarEstado();
                case 5 -> gestor.finalizarCaso();
                case 6 -> menu.mostrarHistorialTickets();
                case 7 -> menu.borrarArchivoTicket();
                case 0 -> System.out.println("GRACIAS: Saliendo del sistema...");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 0);

        scanner.close();
    }
}