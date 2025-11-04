package edu.unl.cc.io;

import edu.unl.cc.dominio.Caso;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 guardar y cargar casos.
 */
public class Persistence {

    public static void guardarCasos(List<Caso> casos, String path) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8))) {
            writer.write("id,nombre,tramite,estado,urgente");
            writer.newLine();
            for (Caso c : casos) {
                writer.write(String.join(",",
                        c.getIdCaso(),
                        c.getNombreEstudiante(),
                        c.getTramite(),
                        c.getEstado().name(),
                        String.valueOf(c.isUrgente())));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar casos: " + e.getMessage());
        }
    }

    public static List<String> cargarLineas(String path) {
        List<String> lines = new ArrayList<>();
        File f = new File(path);
        if (!f.exists()) return lines;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
            String l;
            while ((l = reader.readLine()) != null) lines.add(l);
        } catch (IOException e) {
            System.err.println("Error al leer archivo: " + e.getMessage());
        }
        return lines;
    }
}
