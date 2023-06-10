package com.TETRIS.Juego;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import javax.swing.*;


public class Tablero extends JPanel implements ActionListener, Serializable {
    private transient TableroForma formaTab;
    private Dificultad difucultad;
    private transient JPanel tetris;
    private transient MenuPausa menuPausa;
    private final int anchodeCuadricula = 11;
    private final int alturaCuadricula = 22;
    private Timer tiempo;
    private boolean enPausa = false;
    private int lineasRemovidas = 0;
    private int ejeX = 0;
    private int ejeY = 0;
    private transient JLabel puntajeBarra;
    private transient JLabel nivelBarra;
    private Figura figura;
    private Figura.Figuras[] figuras;

    public Tablero(TableroForma forma, JPanel tetris) {
        this.tetris = tetris;
        this.formaTab = forma;
        figura = new Figura();
        tiempo = new Timer(400, this);
        tiempo.start();
        nivelBarra = forma.getNivel();
        puntajeBarra = forma.getPuntajeBarra();
        figuras = new Figura.Figuras[anchodeCuadricula * alturaCuadricula];
        moverPiezaTeclado();
    }

    public void setConstructorTablero(JPanel tetris, TableroForma tableroForma) {
        this.tetris = tetris;
        this.formaTab = tableroForma;
        nivelBarra = tableroForma.getNivel();
        puntajeBarra = tableroForma.getPuntajeBarra();
        moverPiezaTeclado();
    }

    private void moverPiezaTeclado() {
        setFocusable(true);
        addKeyListener(new TAdapter());
    }

    public void actionPerformed(ActionEvent e) {
        bajarFigura();
    }

    private int anchodeCuadro() {
        return (int) getSize().getWidth() / anchodeCuadricula;
    }

    private int alturadeCuadro() {
        return (int) getSize().getHeight() / alturaCuadricula;
    }

    private Figura.Figuras cordenadasFigura(int x, int y) {
        return figuras[(y * anchodeCuadricula) + x];
    }

    protected void comenzar() {
        iniciarTablero();
        posicionarPieza();
        tiempo.start();
    }

    protected void comenzarCargado() {
        posicionarPieza();
        tiempo.start();
    }

    protected void pausa() {
        enPausa = !enPausa;
        if (enPausa) {
            tiempo.stop();
            puntajeBarra.setText("pausado");
            this.menuPausa = new MenuPausa(formaTab, tetris, this);
            this.menuPausa.iniciar();
        } else {
            tiempo.start();
            puntajeBarra.setText("Puntaje: " + String.valueOf(asignarPuntaje(this.lineasRemovidas)));
        }
    }

    protected int asignarPuntaje(int lineasRemovidas) {
        this.difucultad = new Dificultad(lineasRemovidas, tiempo, nivelBarra);
        return difucultad.asignarDificultad();
    }

    private void Caer() {
        int newY = ejeY;
        while (newY > 0) {
            if (!comprobarMovimiento(figura, ejeX, newY - 1)) {
                break;
            }
            moverFigura(figura, ejeX, newY - 1);
            --newY;
        }
        invocarPieza();
    }

    private void bajarFigura() {
        if (!comprobarMovimiento(figura, ejeX, ejeY - 1)) {
            invocarPieza();
        } else {
            moverFigura(figura, ejeX, ejeY - 1);
        }
    }


    private void iniciarTablero() {
        for (int i = 0; i < alturaCuadricula * anchodeCuadricula; ++i)
            figuras[i] = Figura.Figuras.NoForma;
    }

    private void invocarPieza() {
        for (int i = 0; i < 4; ++i) {
            int x = ejeX + figura.getx(i);
            int y = ejeY - figura.gety(i);
            figuras[(y * anchodeCuadricula) + x] = figura.getFigura();
        }
        removerLineasLLenas();
        posicionarPieza();
    }

    private void posicionarPieza() {
        figura.setFiguraAleatoria();
        int r = (int) ((Math.random() * 7) - 4);
        ejeX = anchodeCuadricula / 2 + r;//origen x
        ejeY = alturaCuadricula - 1 + figura.minY();//origen y

        if (!comprobarMovimiento(figura, ejeX, ejeY)) {
            terminarJuego();
            tetris.setVisible(true);
        }
    }

    private void terminarJuego() {
        figura.setForma(Figura.Figuras.NoForma);
        tiempo.stop();
        guardarPuntuacion();
        formaTab.dispose();
    }

    private void guardarPuntuacion() {
        String nombre = "             ";
        while (nombre.length() > 4) {
            nombre = JOptionPane.showInputDialog(null, "Game Over, Cual es tu nickname?", "puntaje", 3);
            if (nombre.length() > 4) {
                JOptionPane.showMessageDialog(null, "El nickname no debe ser tan largo!!!", "puntaje", 2);
            }
        }
        //Jugador jugador=new Jugador(nombre,puntajeBarra.getText());
        String mensaje = "Jugador: " + nombre + "\t" + puntajeBarra.getText();
        String archivoPuntajes = "Puntajes.txt";
        try {
            escribirArchivoPuntaciones(archivoPuntajes, mensaje);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void escribirArchivoPuntaciones(String archivoPuntajes, String mensaje) throws IOException {
        FileWriter escribir;
        PrintWriter linea;
        File archivo = new File(archivoPuntajes);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                escribir = new FileWriter(archivoPuntajes, true);
                linea = new PrintWriter(escribir);
                linea.println(mensaje);
                linea.close();
                escribir.close();

            } catch (IOException e) {
                throw e;
            }
        } else {
            try {
                archivo.createNewFile();
                escribir = new FileWriter(archivoPuntajes, true);
                linea = new PrintWriter(escribir);
                linea.println(mensaje);
                linea.close();
                escribir.close();
            } catch (IOException e) {
                throw e;
            }
        }
    }


    private boolean comprobarMovimiento(Figura nuevaFigura, int nuevoX, int nuevoY) {
        for (int i = 0; i < 4; ++i) {
            int x = nuevoX + nuevaFigura.getx(i);
            int y = nuevoY - nuevaFigura.gety(i);
            if (x < 0 || x >= anchodeCuadricula || y < 0 || y >= alturaCuadricula)
                return false;
            if (cordenadasFigura(x, y) != Figura.Figuras.NoForma)
                return false;
        }
        return true;
    }

    private void moverFigura(Figura nuevaFigura, int nuevoX, int nuevoY) {
        figura = nuevaFigura;
        ejeX = nuevoX;
        ejeY = nuevoY;
    }

    private void removerLineasLLenas() {
        int numeroDeLineasLLenas = 0;
        for (int i = alturaCuadricula - 1; i >= 0; --i) {
            boolean lineallena = true;

            for (int j = 0; j < anchodeCuadricula; ++j) {
                if (cordenadasFigura(j, i) == Figura.Figuras.NoForma) {
                    lineallena = false;
                    break;
                }
            }

            if (lineallena) {
                ++numeroDeLineasLLenas;
                for (int k = i; k < alturaCuadricula - 1; ++k) {
                    for (int j = 0; j < anchodeCuadricula; ++j)
                        figuras[(k * anchodeCuadricula) + j] = cordenadasFigura(j, k + 1);
                }
            }
        }

        if (numeroDeLineasLLenas > 0) {
            lineasRemovidas += numeroDeLineasLLenas;
            puntajeBarra.setText("Puntaje: " + String.valueOf(asignarPuntaje(lineasRemovidas)));
            figura.setForma(Figura.Figuras.NoForma);
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        Dimension tamaño = getSize();
        int boardTop = (int) tamaño.getHeight() - alturaCuadricula * alturadeCuadro();


        for (int i = 0; i < alturaCuadricula; ++i) {
            for (int j = 0; j < anchodeCuadricula; ++j) {
                Figura.Figuras pieza = cordenadasFigura(j, alturaCuadricula - i - 1);
                if (pieza != Figura.Figuras.NoForma)
                    dibujarCuadro(g, 0 + j * anchodeCuadro(),
                            boardTop + i * alturadeCuadro(), pieza);
            }
        }

        if (figura.getFigura() != Figura.Figuras.NoForma) {
            for (int i = 0; i < 4; ++i) {
                int x = ejeX + figura.getx(i);
                int y = ejeY - figura.gety(i);
                dibujarCuadro(g, 0 + x * anchodeCuadro(),
                        boardTop + (alturaCuadricula - y - 1) * alturadeCuadro(),
                        figura.getFigura());
            }
        }
        repaint();
    }

    private void dibujarCuadro(Graphics g, int x, int y, Figura.Figuras pieza) {
        Color colores[] = {new Color(0, 0, 0), new Color(204, 102, 102),
                new Color(102, 204, 102), new Color(102, 102, 204),
                new Color(204, 204, 102), new Color(204, 102, 204),
                new Color(102, 204, 204), new Color(218, 170, 0),
                new Color(102, 204, 204), new Color(218, 170, 0)

        };
        Color color = colores[pieza.ordinal()];
        g.setColor(color);
        g.fillRect(x + 1, y + 1, anchodeCuadro() - 2, alturadeCuadro() - 2);
        g.setColor(color.brighter());
        g.drawLine(x, y + alturadeCuadro() - 1, x, y);
        g.drawLine(x, y, x + anchodeCuadro() - 1, y);
        g.setColor(color.darker());
        g.drawLine(x + 1, y + alturadeCuadro() - 1,
                x + anchodeCuadro() - 1, y + alturadeCuadro() - 1);
        g.drawLine(x + anchodeCuadro() - 1, y + alturadeCuadro() - 1,
                x + anchodeCuadro() - 1, y + 1);
    }


    class TAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent eventoTeclado) {
            int codigoTecla = eventoTeclado.getKeyCode();
            switch (codigoTecla) {
                case KeyEvent.VK_LEFT:
                    if (comprobarMovimiento(figura, ejeX - 1, ejeY))
                        moverFigura(figura, ejeX - 1, ejeY);
                    break;
                case KeyEvent.VK_RIGHT:
                    if (comprobarMovimiento(figura, ejeX + 1, ejeY))
                        moverFigura(figura, ejeX + 1, ejeY);
                    break;
                case KeyEvent.VK_DOWN:
                    if (comprobarMovimiento(figura.rotarDerecha(), ejeX, ejeY))
                        moverFigura(figura.rotarDerecha(), ejeX, ejeY);
                    break;
                case KeyEvent.VK_UP:
                    if (comprobarMovimiento(figura.rotarIzquierda(), ejeX, ejeY))
                        moverFigura(figura.rotarIzquierda(), ejeX, ejeY);
                    break;
                case KeyEvent.VK_SPACE:
                    Caer();
                    break;
                case KeyEvent.VK_D:
                    bajarFigura();
                    break;
                case KeyEvent.VK_P:
                    pausa();
                    break;
            }
        }
    }

}
