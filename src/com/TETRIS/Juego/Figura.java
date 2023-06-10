package com.TETRIS.Juego;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

public class Figura implements Serializable {
    enum Figuras {
        NoForma, ZForma, SForma, IForma,
        TForma, CuadradoForma, LForma, LForma2
    }

    private Figuras piezaForma;
    private int coordenadas[][];
    private int[][][] coordenadasFigura;

    public Figura() {
        coordenadas = new int[5][2];
    }


    private void setX(int indice, int x) {
        coordenadas[indice][0] = x;
    }

    private void setY(int indice, int y) {
        coordenadas[indice][1] = y;
    }

    public int getx(int indice) {
        return coordenadas[indice][0];
    }

    public int gety(int indice) {
        return coordenadas[indice][1];
    }

    public Figuras getFigura() {
        return piezaForma;
    }

    protected void setForma(Figuras forma) {
        coordenadasFigura = new int[][][]{
                {{0, 0}, {0, 0}, {0, 0}, {0, 0}},
                {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}},
                {{0, -1}, {0, 0}, {1, 0}, {1, 1}},
                {{0, -1}, {0, 0}, {0, 1}, {0, 2}},
                {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},
                {{0, 0}, {1, 0}, {0, 1}, {1, 1}},
                {{-1, -1}, {0, -1}, {0, 0}, {0, 1}},
                {{1, -1}, {0, -1}, {0, 0}, {0, 1}}
        };

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; ++j) {
                coordenadas[i][j] = coordenadasFigura[forma.ordinal()][i][j];
            }
        }
        piezaForma = forma;
    }

    protected void setFiguraAleatoria() {
        Random r = new Random();
        int x = Math.abs(r.nextInt()) % 7+ 1;
        Figuras[] figuras = Figuras.values();
        setForma(figuras[x]);
    }

    protected int minY() {
        int m = coordenadas[0][0];
        for (int i = 0; i < 4; i++) {
            m = Math.min(m, coordenadas[i][1]);//retorna -1 o 0 dependiendo la figura
        }
        return m;
    }

    public Figura rotarIzquierda() {
        if (piezaForma == Figuras.CuadradoForma)
            return this;

        Figura resultado = new Figura();
        resultado.piezaForma = piezaForma;

        for (int i = 0; i < 4; ++i) {
            resultado.setX(i, gety(i));
            resultado.setY(i, -getx(i));
        }
        return resultado;
    }

    public Figura rotarDerecha() {
        if (piezaForma == Figuras.CuadradoForma)
            return this;

        Figura resultado = new Figura();
        resultado.piezaForma = piezaForma;

        for (int i = 0; i < 4; ++i) {
            resultado.setX(i, -gety(i));
            resultado.setY(i, getx(i));
        }
        return resultado;
    }
}