package edu.unl.cc.dominio;
/**
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.0
 */
public class Accion {

    public enum Tipo {
        AGREGAR_NOTA,
        ELIMINAR_NOTA,
        CAMBIO_ESTADO
    }
    private Tipo tipo;
    private String dato; // texto de la nota (si aplica)
    private TipoEstado estadoAnterior; // para CAMBIO_ESTADO
    private TipoEstado estadoNuevo;    // para CAMBIO_ESTADO
    private int casoId; // id del caso sobre el que se aplicó la acción

    /**
     *
     * @param casoId
     * @param tipo
     * @param dato
     */
    public Accion(int casoId, Tipo tipo, String dato) {
        this.casoId = casoId;
        this.tipo = tipo;
        this.dato = dato;
    }

    /**
     *
     * @param casoId
     * @param tipo
     * @param anterior
     * @param nuevo
     */
    public Accion(int casoId, Tipo tipo, TipoEstado anterior, TipoEstado nuevo) {
        this.casoId = casoId;
        this.tipo = tipo;
        this.estadoAnterior = anterior;
        this.estadoNuevo = nuevo;
    }

    public Tipo getTipo() {
        return tipo;
    }
    public String getDato() {
        return dato;
    }
    public TipoEstado getEstadoAnterior() {
        return estadoAnterior;
    }
    public TipoEstado getEstadoNuevo() {
        return estadoNuevo;
    }
    public int getCasoId() {
        return casoId;
    }
}
