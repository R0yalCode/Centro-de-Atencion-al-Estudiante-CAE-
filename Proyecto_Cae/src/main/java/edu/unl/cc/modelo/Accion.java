package edu.unl.cc.modelo;

/**
 * Esta clase representa una acción realizada sobre un caso dentro del sistema
 *
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.1
 */
public class Accion {

    /**
     * Tipos de acciones posibles que se pueden realizar sobre un caso
     * Se define dentro de la clase porque solo se usa aquí en esta clase
     */
    public enum Tipo {
        AGREGAR_NOTA,
        ELIMINAR_NOTA,
        CAMBIO_ESTADO
    }

    // Atributos de la acción
    private final int casoId;                  // ID del caso al que pertenece la acción
    private final Tipo tipo;                   // Tipo de acción realizada
    private final String dato;                 // Texto de la nota
    private final EstadoCaso estadoAnterior;
    private final EstadoCaso estadoNuevo;

    /**
     * Constructor para acciones relacionadas con notas
     * @param casoId ID del caso afectado
     * @param tipo Tipo de acción
     * @param dato Texto de la nota involucrada
     */
    public Accion(int casoId, Tipo tipo, String dato) {
        this.casoId = casoId;
        this.tipo = tipo;
        this.dato = dato;
        this.estadoAnterior = null;
        this.estadoNuevo = null;
    }

    /**
     * Constructor para acciones de cambio de estado
     * @param casoId ID del caso afectado
     * @param tipo Tipo de acción
     * @param anterior Estado anterior del caso
     * @param nuevo Estado nuevo del caso
     */
    public Accion(int casoId, Tipo tipo, EstadoCaso anterior, EstadoCaso nuevo) {
        this.casoId = casoId;
        this.tipo = tipo;
        this.estadoAnterior = anterior;
        this.estadoNuevo = nuevo;
        this.dato = null;
    }

    public int getCasoId() {
        return casoId;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public String getDato() {
        return dato;
    }

    public EstadoCaso getEstadoAnterior() {
        return estadoAnterior;
    }

    public EstadoCaso getEstadoNuevo() {
        return estadoNuevo;
    }

    /**
     * Devuelve el contenido textual de la acción si corresponde a una nota
     * @return Texto de la nota asociada a la acción
     */
    public String getContenido() {
        return dato;
    }
}