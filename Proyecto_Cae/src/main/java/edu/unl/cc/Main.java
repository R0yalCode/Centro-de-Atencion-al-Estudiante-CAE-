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
            System.out.println("11. Exportar datos a archivo");
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
                        TipoEstado[] estados = TipoEstado.values();
                        for (int i = 0; i < estados.length; i++) {
                            System.out.println((i + 1) + ". " + estados[i]);
                        }
                        System.out.print("Seleccione el número del nuevo estado: ");
                        if (sc.hasNextInt()) {
                            int seleccion = sc.nextInt();
                            sc.nextLine();
                            if (seleccion >= 1 && seleccion <= estados.length) {
                                TipoEstado nuevoEstado = estados[seleccion - 1];
                                gestor.cambiarEstado(nuevoEstado);
                            } else {
                                System.out.println("Selección fuera de rango.");
                            }
                        } else {
                            System.out.println("Entrada inválida. Debe ingresar un número.");
                            sc.nextLine();
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
                    case 11:
                        System.out.print("Ingrese el nombre del archivo (ej: datos_cae.txt): ");
                        String nombreArchivo = sc.nextLine();
                        // Si no incluye la extensión .txt, se la agregamos
                        if (!nombreArchivo.endsWith(".txt")) {
                            nombreArchivo += ".txt";
                        }
                        gestor.exportarDatos(nombreArchivo);
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
