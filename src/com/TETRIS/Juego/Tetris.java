package com.TETRIS.Juego;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Tetris extends JFrame {
    private JFrame frame;
    private JButton SALIRButton;
    private JButton PUNTUACIONESButton;
    private JButton NUEVOJUEGOButton;
    private JButton CARGARPARTIDAButton;
    private JPanel Tetris;
    private final String archivoGuardar = "Guardado.txt";

    public Tetris() {
        this.frame = new JFrame("tetris");

        NUEVOJUEGOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent nuevo) {
                iniciarJuego();
            }
        });
        CARGARPARTIDAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent cargar) {
                cargarPartida();
            }
        });
        PUNTUACIONESButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent puntuacion) {
                mostrarPuntuaciones();
            }
        });
        SALIRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent salir) {
                salirJuego();
            }
        });
    }

    private void salirJuego() {
        JOptionPane.showMessageDialog(null, "Hasta pronto ;b", "Adios", 2);
        System.exit(0);
    }

    private void mostrarPuntuaciones() {
        JOptionPane.showMessageDialog(null, "Bienvenido a la leaderboard", "Puntos", 1);
        Leaderboard puntajes = new Leaderboard();
        puntajes.iniciar();
    }


    protected void iniciarJuego() {
        JOptionPane.showMessageDialog(null, "Empezando Nuevo juego", "Nuevo", 1);
        TableroForma tablero = new TableroForma(this.Tetris);
        tablero.setLocationRelativeTo(null);
        tablero.setVisible(true);
        Tetris.setVisible(false);
    }

    private void cargarPartida() {
        JOptionPane.showMessageDialog(null, "Cargando", "Carga", 1);
        Tablero tablero = cargarArchivoPartida(archivoGuardar);
        TableroForma tableroForma = new TableroForma(this.Tetris, tablero);
        tableroForma.setLocationRelativeTo(null);
        tableroForma.setVisible(true);
        Tetris.setVisible(false);
    }

    protected static Tablero cargarArchivoPartida(String guardar) {
        File archivo = new File(guardar);
        try {
            FileInputStream flujoDeEntrada = new FileInputStream(archivo);
            ObjectInputStream manejadorDeLectura = new ObjectInputStream(flujoDeEntrada);
            Tablero tablero = (Tablero) manejadorDeLectura.readObject();
            manejadorDeLectura.close();
            return tablero;

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("No se encontro archivo");
            return null;
        }
    }


    public void iniciarInterfaz() {
        frame.setContentPane(new Tetris().Tetris);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void musica() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File audio = new File("Tetris.wav");
        AudioInputStream audstrim = AudioSystem.getAudioInputStream(audio);
        Clip clip = AudioSystem.getClip();
        clip.open(audstrim);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-29.0f);
        clip.start();
    }
}
