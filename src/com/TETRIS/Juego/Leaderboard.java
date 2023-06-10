package com.TETRIS.Juego;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;


public class Leaderboard {
    private ArrayList<String> listaJugadores;
    private JPanel Leaderboard;
    private JTextArea puntajes;
    private JFrame frame;
    private final String archivoPuntajes = "Puntajes.txt";

    public Leaderboard() {
        frame = new JFrame("Leaderboard");
        añadirPuntajes();
    }

    private void añadirPuntajes() {
        listaJugadores = leerArchivoPuntaje(this.archivoPuntajes);
        Collections.sort(listaJugadores);
        puntajes.setText(rediseñarLista(listaJugadores));
        puntajes.setFont(new Font("arial", 3, 15));
    }

    private String rediseñarLista(ArrayList<String> listaPuntajes) {
        String jugadores = "";
        for (String jugador : listaPuntajes) {
            jugadores += jugador + "\n";
        }
        return jugadores;
    }

    protected ArrayList<String> leerArchivoPuntaje(String archivoPuntajes) {
        ArrayList<String> listaPuntajes = new ArrayList<>();
        File archivo = new File(archivoPuntajes);
        try {
            BufferedReader bf = new BufferedReader(new FileReader(archivo));
            String bfRead;
            while ((bfRead = bf.readLine()) != null) {
                listaPuntajes.add(bfRead);
            }
        } catch (Exception e) {
            System.err.println("No se encontro archivo archivoPuntajes");
        }
        return listaPuntajes;
    }

    public void iniciar() {
        frame.setContentPane(new Leaderboard().Leaderboard);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(300, 600);
        frame.setLocation(250, 240);
        frame.setVisible(true);
    }

    /*private static ArrayList<Jugador> leerArchivos(String puntajes) {
        ArrayList<Jugador> listaPuntajes = new ArrayList<>();
        File archivo = new File(puntajes);
        try {
            FileInputStream flujoDeEntrada = new FileInputStream(archivo);
            ObjectInputStream manejadorDeLectura = new ObjectInputStream(flujoDeEntrada);

                listaPuntajes.add((Jugador) manejadorDeLectura.readObject());

            manejadorDeLectura.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.print(e);
        }
        return listaPuntajes;
    }*/
}
