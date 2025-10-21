package edu.unl.cc.dominio;
/**
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.0
 * */
import edu.unl.cc.estructuras.Lista;

public class Caso {
    public enum Estado{
        EN_COLA,
        EN_ATENCION,
        EN_PROCESO,
        PENDIENTE,
        COMPLETADO
    }
    private int id;
    private String estudiante;
    private TipoEstado estado;
    private Lista notas;

    /**
     * @param id
     * @param estudiante
     */
    public Caso(int id, String estudiante) {
        this.id = id;
        this.estudiante = estudiante;
        this.estado = TipoEstado.EN_COLA; // Estado inicial
        this.notas = new Lista();
    }


    public int getId() {
        return id;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public TipoEstado getEstado() {
        return estado;
    }

    public void setEstado(TipoEstado nuevoEstado) {
        this.estado = nuevoEstado;
    }

    // Operaciones sobre notas
    public void agregarNota(String texto) {
        notas.insertarInicio(new Nota(texto));
    }

    public void eliminarNota(String texto) {
        notas.eliminarPrimeraCoincidencia(texto);
    }

    public boolean notasVacias() {
        return notas.estaVacia();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Caso #").append(id).append(" - ").append(estudiante).append("\n");
        sb.append("Estado final: ").append(estado).append("\n");
        sb.append("Notas registradas:\n");
        if (notasVacias()) {
            sb.append("- (sin notas)\n");
        } else {
            sb.append(notas.toString());
        }
        return sb.toString();
    }
}
