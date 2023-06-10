package com.TETRIS.Juego;

import javax.swing.*;
import java.io.Serializable;

public class Dificultad implements Serializable {
    private JLabel nivelBarra;
    private Timer tiempo;
    private int lineasRemovidas;

    public Dificultad(int lineasRemovidas, Timer tiempo, JLabel nivel) {
        this.lineasRemovidas = lineasRemovidas;
        this.tiempo = tiempo;
        this.nivelBarra = nivel;
    }

    public int asignarDificultad() {
        if (lineasRemovidas >= 3 && lineasRemovidas < 6) {
            this.nivelBarra.setText("NIVEL 2");
            tiempo.setDelay(300);
            return lineasRemovidas * 200;
        } else if (lineasRemovidas >= 6 && lineasRemovidas < 10) {
            this.nivelBarra.setText("NIVEL 3");
            tiempo.setDelay(200);
            return lineasRemovidas * 300;
        } else if (lineasRemovidas >= 10 && lineasRemovidas <= 15) {
            this.nivelBarra.setText("NIVEL 4");
            tiempo.setDelay(100);
            return lineasRemovidas * 400;
        } else if (lineasRemovidas >= 15) {
            this.nivelBarra.setText("INSANE!!");
            tiempo.setDelay(50);
            return lineasRemovidas * 500;
        } else {
            return lineasRemovidas * 100;
        }
    }
}
