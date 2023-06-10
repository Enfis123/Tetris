package com.TETRIS.Juego;

import java.io.Serializable;

public class Jugador implements Serializable {
    private  String alias;
    private  String puntaje;

    public Jugador(String alias, String puntaje) {
        this.alias=alias;
        this.puntaje=puntaje;
    }

    public String getNombre() {
        return alias;
    }

    public String getPuntajeBarra() {
        return puntaje;
    }
}
