package edu.unl.cc;
/**
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.0
 */
import edu.unl.cc.dominio.TipoEstado;
import edu.unl.cc.exception.NombreInvalidoException;
import edu.unl.cc.service.GestorCAE;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GestorCAE gestor = new GestorCAE();
        Scanner sc = new Scanner(System.in);
        int opcion = -1;

        do {
            System.out.println("\n--- BIENVENIDO AL SISTEMA CAE ---");
            System.out.println("1. Recibir nuevo caso");
            System.out.println("2. Atender siguiente caso");
            System.out.println("3. Agregar nota");
            System.out.println("4. Eliminar nota");
            System.out.println("5. Cambiar estado");
            System.out.println("6. Deshacer");
            System.out.println("7. Rehacer");
            System.out.println("8. Finalizar caso");
            System.out.println("9. Mostrar historial finalizados");
            System.out.println("10. Mostrar casos en espera");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
                sc.nextLine();
                switch (opcion) {
                    case 1:
                        System.out.print("Nombre del estudiante: ");
                        String nombre = sc.nextLine();
                        try {
                            gestor.recibirCaso(nombre);
                        } catch (NombreInvalidoException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case 2:
                        gestor.atenderSiguienteCaso();
                        break;
                    case 3:
                        System.out.print("Ingrese una nota: ");
                        String nota = sc.nextLine();
                        gestor.agregarNota(nota);
                        break;
                    case 4:
                        System.out.print("Ingrese una nota a eliminar: ");
                        String eliminar = sc.nextLine();
                        gestor.eliminarNota(eliminar);
                        break;
                    case 5:
                        System.out.println("Estados disponibles:");
                        for (TipoEstado e : TipoEstado.values()) {
                            System.out.println("- " + e);
                        }
                        System.out.print("Nuevo estado: ");
                        String estadoStr = sc.nextLine();
                        try {
                            TipoEstado nuevoEstado = TipoEstado.valueOf(estadoStr);
                            gestor.cambiarEstado(nuevoEstado);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Estado inválido.");
                        }
                        break;
                    case 6:
                        gestor.deshacer();
                        break;
                    case 7:
                        gestor.rehacer();
                        break;
                    case 8:
                        gestor.finalizarCaso();
                        break;
                    case 9:
                        gestor.mostrarHistorialFinalizados();
                        break;
                    case 10:
                        gestor.mostrarCasosEnEspera();
                        break;
                    case 0:
                        System.out.println("Saliendo del sistma...");
                        break;
                    default:
                        System.out.println("Opción inválida. Ingrese un valor de los indicados en pantalla.");
                }
            } else {
                System.out.println("Entrada inválida. Debe ingresar un número.");
                sc.nextLine();
            }
        } while (opcion != 0);
    }
}