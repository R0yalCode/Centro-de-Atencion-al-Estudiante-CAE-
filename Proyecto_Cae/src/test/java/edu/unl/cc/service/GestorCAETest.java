package edu.unl.cc.service;

import edu.unl.cc.exception.NombreInvalidoException;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
public class GestorCAETest {
    @org.junit.jupiter.api.Test
    @Test
    public void testNombreValido() {
        GestorCAE gestor = new GestorCAE();
        try {
            gestor.recibirCaso("Juan Pérez");
        } catch (NombreInvalidoException e) {
            fail("No debería lanzar excepción para nombre válido.");
        }
    }

    @Test
    public void testNombreConNumeros() {
        GestorCAE gestor = new GestorCAE();
        try {
            gestor.recibirCaso("Juan123");
            fail("Debería lanzar NombreInvalidoException por contener números.");
        } catch (NombreInvalidoException e) {
            assertEquals("El nombre del estudiante no puede contener números ni símbolos ni espacops vacios.", e.getMessage());
        }
    }

    @Test
    public void testNombreConSimbolos() {
        GestorCAE gestor = new GestorCAE();
        try {
            gestor.recibirCaso("Ana@María");
            fail("Debería lanzar NombreInvalidoException por contener símbolos.");
        } catch (NombreInvalidoException e) {
            assertEquals("El nombre del estudiante no puede contener números ni símbolos.", e.getMessage());
        }
    }

    @Test
    public void testNombreVacio() {
        GestorCAE gestor = new GestorCAE();
        try {
            gestor.recibirCaso("");
            fail("Debería lanzar NombreInvalidoException por estar vacío.");
        } catch (NombreInvalidoException e) {
            assertEquals("El nombre del estudiante no puede contener números ni símbolos.", e.getMessage());
        }
    }

    @Test
    public void testNombreSoloEspacios() {
        GestorCAE gestor = new GestorCAE();
        try {
            gestor.recibirCaso("   ");
            fail("Debería lanzar NombreInvalidoException por contener solo espacios.");
        } catch (NombreInvalidoException e) {
            assertEquals("El nombre del estudiante no puede contener números ni símbolos.", e.getMessage());
        }
    }


    @Test
    public void deshacer() throws NombreInvalidoException {
        GestorCAE gestor = new GestorCAE();
        gestor.recibirCaso("Juan");
        gestor.recibirCaso("Daniel");
        gestor.atenderSiguienteCaso();
        gestor.agregarNota("Importante");
        gestor.deshacer();
        gestor.rehacer();
        gestor.mostrarHistorialFinalizados();
    }

    @org.junit.jupiter.api.Test
    @Test
    public void rehacer() throws NombreInvalidoException {
        GestorCAE gestor = new GestorCAE();
        gestor.recibirCaso("Juan");
        gestor.recibirCaso("Daniel");
        gestor.atenderSiguienteCaso();
        gestor.agregarNota("Hola");
        gestor.atenderSiguienteCaso();
        gestor.rehacer();
    }
}