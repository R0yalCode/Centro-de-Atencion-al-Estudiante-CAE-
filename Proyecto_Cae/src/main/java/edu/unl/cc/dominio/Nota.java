package edu.unl.cc.dominio;
/**
 * @author Steeven Pardo, Juan Calopino, Daniel Savedra, Royel Jima
 * @version 1.0
 * */
public class Nota {
    private String texto;

    public Nota(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }
}
