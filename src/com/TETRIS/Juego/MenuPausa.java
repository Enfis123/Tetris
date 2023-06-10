package com.TETRIS.Juego;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MenuPausa extends JFrame {
    private TableroForma forma;
    private JPanel tetris;
    private Tablero tablero;
    private JButton guardarButton;
    private JButton salirButton;
    private JPanel Pausa;
    private JFrame frame;
    private final String archivoGuardar = "Guardado.txt";


    public MenuPausa(TableroForma forma, JPanel tetris, Tablero tablero) {
        this.frame = new JFrame("MenuPausa");
        this.tetris = tetris;
        this.forma = forma;
        this.tablero = tablero;
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarPartida();
            }
        });
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarMenu();
            }
        });
    }

    private void cerrarMenu() {
        JOptionPane.showMessageDialog(null, "Volviendo al menu", "Salir", 2);
        tetris.setVisible(true);
        forma.dispose();
    }

    private void guardarPartida() {
        try {
            guardarArchivoPartida(archivoGuardar, tablero);
            JOptionPane.showMessageDialog(null, "Partida Guardada ;b", "Guardado", 1);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    protected void guardarArchivoPartida(String archivoDeGuardado, Tablero tablero) throws IOException {
        File archivo = new File(archivoDeGuardado);
        try {
            FileOutputStream flujoDeSalida = new FileOutputStream(archivo);
            ObjectOutputStream manejadorDeEscritura = new ObjectOutputStream(flujoDeSalida);
            manejadorDeEscritura.writeObject(tablero);
            manejadorDeEscritura.close();
        } catch (IOException e) {
            throw e;
        }
    }

    public void iniciar() {
        frame.setContentPane(new MenuPausa(forma, tetris, tablero).Pausa);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
