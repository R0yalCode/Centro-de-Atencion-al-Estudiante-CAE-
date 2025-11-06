package edu.unl.cc.modelo;

public class Accion {
    public enum Tipo { // Se coloca el enum dentro de la clase ya que solo es usado por esta
        AGREGAR_NOTA,
        ELIMINAR_NOTA,
        CAMBIO_ESTADO
    }

    private final int casoId;
    private final Tipo tipo;
    private final String dato;
    private final EstadoCaso estadoAnterior;
    private final EstadoCaso estadoNuevo;

    public Accion(int casoId, Tipo tipo, String dato) {
        this.casoId = casoId;
        this.tipo = tipo;
        this.dato = dato;
        this.estadoAnterior = null;
        this.estadoNuevo = null;
    }

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
}