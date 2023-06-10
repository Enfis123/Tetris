package com.TETRIS.Juego;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class TableroForma extends JFrame implements Serializable {//clase que permite al usuario observar el nivel la puntuacion y el Tablero
    private JPanel tetris;
    private JLabel puntajeBarra;
    private JLabel nivel;

    public TableroForma(JPanel tetris) {//constructor para inicio
        this.tetris = tetris;
        añadirPuntajeBarra();
        añadirNivelBarra();
        Tablero tablero = new Tablero(this, this.tetris);
        this.add(tablero);
        tablero.setBackground(Color.black);
        tablero.comenzar();
        this.setSize(800, 1000);
        this.setTitle("Tetris");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public TableroForma(JPanel tetris, Tablero tablero) {//constructor para carga
        this.tetris = tetris;
        añadirPuntajeBarra();
        añadirNivelBarra();
        tablero.setConstructorTablero(tetris, this);
        this.add(tablero);
        tablero.setBackground(Color.black);
        tablero.pausa();
        tablero.comenzarCargado();
        this.setSize(800, 1000);
        this.setTitle("Tetris");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void añadirNivelBarra() {
        nivel = new JLabel("     NIVEL 1  ");
        this.add(nivel, BorderLayout.EAST);
        nivel.setFont(new Font("montserrat", 1, 50));
        nivel.setOpaque(true);
        nivel.setBackground(Color.orange);
        nivel.setPreferredSize(new Dimension(300, 300));
    }

    private void añadirPuntajeBarra() {
        puntajeBarra = new JLabel("Puntaje: 0");
        this.add(puntajeBarra, BorderLayout.NORTH);
        puntajeBarra.setFont(new Font("montserrat", 1, 30));
        puntajeBarra.setOpaque(true);
        puntajeBarra.setBackground(Color.orange);
    }

    public JLabel getPuntajeBarra() {
        return puntajeBarra;
    }

    public JLabel getNivel() {
        return nivel;
    }

}
