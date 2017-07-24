/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lofar;

import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author siviso
 */
public class despliegue extends JComponent {

    int inicioCascadaX = 75;
    int inicioCascadaY = 130;
    int sizeCanalX = 0;
    int sizeCanalY;
    int xi, yi, c;
    String infor;
    int gn = 0;
    String info;
    String modelo;
    int longLF;
    int tiempoOper;
    int tiempoLocal;
    int fX, fY;
    int LimYup = -65;
    int LimYdw = -125;
    private boolean bMarcacion;
    private int[] iActual;
    private int[][] waterfall;
    private String[] time;

    public despliegue(JFrame window) {
        window.add(this);
        //window.pack();
        window.setUndecorated(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setAlwaysOnTop(true);
        //window.setFocusable(true);
        Properties prop = new Properties();
        InputStream input = null;

        int posicionX = 0;
        int posicionY = 0;
        int dimensionX = 0;
        int dimensionY = 0;

        tiempoOper = 100;
        tiempoLocal = 0;

        try {
            input = new FileInputStream("config.properties");
            prop.load(input);
            posicionX = Integer.parseInt(prop.getProperty("posicionX"));
            posicionY = Integer.parseInt(prop.getProperty("posicionY"));
            dimensionX = Integer.parseInt(prop.getProperty("dimensionX"));
            dimensionY = Integer.parseInt(prop.getProperty("dimensionY"));
            LimYup = Integer.parseInt(prop.getProperty("LFlimYup"));
            LimYdw = Integer.parseInt(prop.getProperty("LFlimYdw"));
            longLF = Integer.parseInt(prop.getProperty("longLF"));
            modelo = prop.getProperty("modelo");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        window.setSize(dimensionX, dimensionY);
        window.setLocation(posicionX, posicionY);
        if ("SSPV".equals(modelo)) {
            waterfall = new int[100][longLF];
            for (int x = 0; x < 100; x++) {                                                 //inicializa el waterfall en cero
                for (int y = 0; y < longLF; y++) {
                    waterfall[x][y] = LimYdw;
                }
            }
            setWaterfall(waterfall);
            iActual = new int[longLF];
            for (int i = 0; i < longLF; i++) {
                iActual[i] = 0;
            }
            setIActual(iActual);
        } else {
            waterfall = new int[100][longLF];
            for (int x = 0; x < 100; x++) {                                                 //inicializa el waterfall en cero
                for (int y = 0; y < longLF; y++) {
                    waterfall[x][y] = 55;
                }
            }
            setWaterfall(waterfall);
            iActual = new int[longLF];
            for (int i = 0; i < longLF; i++) {
                iActual[i] = 55;
            }
            setIActual(iActual);
        }
        time = new String[100];
        for (int i = 0; i < 100; i++) {
            time[i] = "";
        }
        setTime(time);
    }

    @Override
    public Dimension getPreferredSize() {
        int dimensionX = 100;
        int dimensionY = 100;
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("config.properties");
            //load a properties file
            prop.load(input);
            //get the propperty value and print it out
            dimensionX = Integer.parseInt(prop.getProperty("dimensionX"));
            dimensionY = Integer.parseInt(prop.getProperty("dimensionY"));

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new Dimension(dimensionX, dimensionY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        gn++;
        //System.out.println("paint component ciclo numero: " + gn);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getSize().width, getSize().height);

        sizeCanalX = (getSize().width - inicioCascadaX) / longLF;
        sizeCanalY = ((getSize().height) - inicioCascadaY) / 100;
        desp(g, sizeCanalX, sizeCanalY);

    }

    public void setWaterfall(int[][] waterfall) {
        this.waterfall = waterfall;
    }

    public void setIActual(int[] iActual) {
        this.iActual = iActual;
    }

    public void setTime(String[] time) {
        this.time = time;
    }

    public void setBMarcacion(boolean bMarcacion) {
        this.bMarcacion = bMarcacion;
        repaint();
    }

    public int[][] getWaterfall() {
        return this.waterfall;
    }

    public int[] getIActual() {
        return this.iActual;
    }

    public String[] getTime() {
        return this.time;
    }

    public boolean getBMarcacion() {
        return this.bMarcacion;
    }

    public void desp(Graphics g, int limX, int limY) {
        archivo a = new archivo();
        String DIR = "resource/btrData.txt";   //variable estatica que guarda el nombre del archivo donde se guardara la informacion recivida para desplegarse
        yi = inicioCascadaY;     //variable de control grafico en Y que guarda la acumulacion del incremento para la graficacion
        xi = inicioCascadaX;     //variable de control grafico en Y que guarda la acumulacion del incremento para la graficacion
        c = 0;
        int t = 0;
        waterfall = getWaterfall();
        iActual = getIActual();
        time = getTime();

        int colorUp = Integer.parseInt(a.leerTxtLine("resource/colorUp.txt"));
        int colorDw = Integer.parseInt(a.leerTxtLine("resource/colorDw.txt"));
        //int marcacion = Integer.parseInt(a.leerTxtLine("resource/marcacion.txt"));

        g.setColor(Color.WHITE);
        g.drawLine(inicioCascadaX - 5, 1, inicioCascadaX - 5, inicioCascadaY - 30);
        g.drawLine(inicioCascadaX - 5, inicioCascadaY - 30, getSize().width, inicioCascadaY - 30);
        g.drawLine(inicioCascadaX - 5, inicioCascadaY, inicioCascadaX - 5, getSize().height - 20);

        if ("SSPP".equals(modelo)) {
            g.drawLine(inicioCascadaX - 5, 1, inicioCascadaX - 5, inicioCascadaY - 30);
            g.drawLine(inicioCascadaX - 5, inicioCascadaY - 30, getSize().width, inicioCascadaY - 30);
            g.drawLine(inicioCascadaX - 5, inicioCascadaY, inicioCascadaX - 5, getSize().height - 20);
            g.drawLine(inicioCascadaX - 10, 5, inicioCascadaX - 5, 5);
            g.drawString(LimYup + "", inicioCascadaX - 35, 11);
            g.drawLine(inicioCascadaX - 10, (((inicioCascadaY - 35) + 5) / 2), inicioCascadaX - 5, (((inicioCascadaY - 35) + 5) / 2));
            g.drawString(((LimYup + LimYdw) / 2) + "", inicioCascadaX - 35, (((inicioCascadaY - 35) + 5) / 2) + 5);
            g.drawLine(inicioCascadaX - 10, inicioCascadaY - 35, inicioCascadaX - 5, inicioCascadaY - 35);
            g.drawString(LimYdw + "", inicioCascadaX - 40, inicioCascadaY - 30);
            for (int i = 0; i <= 6; i++) {
                g.drawLine(inicioCascadaX + (((limX * longLF) / 6) * i), inicioCascadaY - 30, inicioCascadaX + (((limX * longLF) / 6) * i), inicioCascadaY - 25);
                if (i != 6) {
                    g.drawString((i * 100) + "Hz", (inicioCascadaX - 20) + (((limX * longLF) / 6) * i), inicioCascadaY - 10);
                } else {
                    g.drawString((i * 100) + "Hz", (inicioCascadaX - 20) + (limX * longLF) - 10, inicioCascadaY - 10);
                }
            }
        } else if ("SSF".equals(modelo)) {
            g.drawLine(inicioCascadaX - 5, 1, inicioCascadaX - 5, inicioCascadaY - 30);
            g.drawLine(inicioCascadaX - 5, inicioCascadaY - 30, getSize().width, inicioCascadaY - 30);
            g.drawLine(inicioCascadaX - 5, inicioCascadaY, inicioCascadaX - 5, getSize().height - 20);
            g.drawLine(inicioCascadaX - 10, 5, inicioCascadaX - 5, 5);
            g.drawString("-65", inicioCascadaX - 35, 11);
            g.drawLine(inicioCascadaX - 10, (((inicioCascadaY - 35) + 5) / 2), inicioCascadaX - 5, (((inicioCascadaY - 35) + 5) / 2));
            g.drawString("-95", inicioCascadaX - 35, (((inicioCascadaY - 35) + 5) / 2) + 5);
            g.drawLine(inicioCascadaX - 10, inicioCascadaY - 35, inicioCascadaX - 5, inicioCascadaY - 35);
            g.drawString("-125", inicioCascadaX - 40, inicioCascadaY - 30);
            for (int i = 0; i < 6; i++) {
                g.drawLine(inicioCascadaX + (((limX * longLF) / 6) * i), inicioCascadaY - 30, inicioCascadaX + (((limX * longLF) / 6) * i), inicioCascadaY - 25);
                if (i != 5) {
                    g.drawString((i * 2.5) + "KHz", inicioCascadaX + (((limX * longLF) / 6) * i) - 10, inicioCascadaY - 10);
                } else {
                    g.drawString((i * 2.5) + "KHz", inicioCascadaX + (((limX * longLF) / 6) * i) - 23, inicioCascadaY - 10);
                }
            }
        } else if ("SSPV".equals(modelo)) {
            g.drawLine(inicioCascadaX - 5, 1, inicioCascadaX - 5, inicioCascadaY - 30);
            g.drawLine(inicioCascadaX - 5, inicioCascadaY - 30, getSize().width, inicioCascadaY - 30);
            g.drawLine(inicioCascadaX - 5, inicioCascadaY, inicioCascadaX - 5, getSize().height - 20);
            g.drawLine(inicioCascadaX - 10, 5, inicioCascadaX - 5, 5);
            g.drawString(LimYup + "", inicioCascadaX - 35, 11);
            g.drawLine(inicioCascadaX - 10, (((inicioCascadaY - 35) + 5) / 2), inicioCascadaX - 5, (((inicioCascadaY - 35) + 5) / 2));
            g.drawString(((LimYup + LimYdw) / 2) + "", inicioCascadaX - 35, (((inicioCascadaY - 35) + 5) / 2) + 5);
            g.drawLine(inicioCascadaX - 10, inicioCascadaY - 35, inicioCascadaX - 5, inicioCascadaY - 35);
            g.drawString(LimYdw + "", inicioCascadaX - 40, inicioCascadaY - 30);
            for (int i = 0; i < 7; i++) {
                g.drawLine(inicioCascadaX + (((limX * longLF) / 6) * i), inicioCascadaY - 30, inicioCascadaX + (((limX * longLF) / 6) * i), inicioCascadaY - 25);
                if (i != 6) {
                    g.drawString((i * 2.5) + "KHz", inicioCascadaX + (((limX * longLF) / 6) * i) - 10, inicioCascadaY - 10);
                } else {
                    g.drawString((i * 2.5) + "KHz", inicioCascadaX + (((limX * longLF) / 6) * i) - 23, inicioCascadaY - 10);
                }
            }
        }
        //new-------------------------------------------------------------------------------------------------------------------
        System.out.println(LimYup + " " + LimYdw + " " + iActual[0] + " " + (LimYup - LimYdw) + " " + (iActual[0] * 90 / (LimYup - LimYdw)) + " " + waterfall[0][0] + " " + (-1 * (waterfall[0][0] - LimYup) * 255 / (LimYup - LimYdw)));
        g.setColor(new Color(0, 150, 0));
        if ("SSPV".equals(modelo)) {
            for (int i = 0; i < longLF - 1; i++) {
                /*if (iActual[i] < 0) {
                    iActual[i] = 0;
                } else if (iActual[i] > 255) {
                    iActual[i] = 255;
                }*/
                System.out.print(iActual[i] + " ");
                //g.drawLine(xi, 95 - (iActual[i] * 90 / 255), xi + limX, 95 - (iActual[i + 1] * 90 / 255));
                g.drawLine(xi, -1 * ((iActual[i] - LimYup) * 90 / (LimYup - LimYdw)), xi + limX, -1 * ((iActual[i + 1] - LimYup) * 90 / (LimYup - LimYdw)));
                xi += limX;
            }
        } else {
            for (int i = 0; i < longLF - 1; i++) {
                if (iActual[i] < 55) {
                    iActual[i] = 55;
                } else if (iActual[i] > 115) {
                    iActual[i] = 115;
                }
                g.drawLine(xi, 95 - (((iActual[i]) - 55) * 90 / 60), xi + limX, 95 - (((iActual[i + 1]) - 55) * 90 / 60));
                //g.drawLine(xi, 95 - (iActual[i] * 90 / 255), xi + limX, 95 - (iActual[i + 1] * 90 / 255));
                xi += limX;
            }
        }

        for (int x = 0; x < waterfall.length; x++) {
            xi = inicioCascadaX;
            for (int y = 0; y < waterfall[x].length; y++) {
                if ("SSPV".equals(modelo)) {
                    if (waterfall[x][y] > LimYup && waterfall[x][y] < LimYdw) {
                        System.out.println("Error #??: el valor a desplegar esta fuera de rango, se ajustará");
                        if (waterfall[x][y] > LimYup) {
                            waterfall[x][y] = LimYup;
                        } else if (waterfall[x][y] < LimYdw) {
                            waterfall[x][y] = LimYdw;
                        }
                    }
                    if ((-1 * (waterfall[x][y] - LimYup) * 255 / (LimYup - LimYdw)) < colorDw) {
                        g.setColor(Color.BLACK);
                    } else if ((-1 * (waterfall[x][y] - LimYup) * 255 / (LimYup - LimYdw)) > colorUp) {
                        g.setColor(Color.GREEN);
                    } else {
                    
                        //g.setColor(new Color(0, (waterfall[x][y] - colorDw) * 255 / (colorUp - colorDw), 0));
                        g.setColor(new Color(0, 255-(((-1 * ((waterfall[x][y] - LimYup) * 255 / (LimYup - LimYdw))) - colorDw) * 255 / (colorUp - colorDw)), 0));
                    }
                } else {
                    if (waterfall[x][y] < 0 && waterfall[x][y] > 255) {
                        System.out.println("Error #??: el valor a desplegar esta fuera de rango, se ajustará");
                        if (waterfall[x][y] < 55) {
                            waterfall[x][y] = 55;
                        } else if (waterfall[x][y] > 115) {
                            waterfall[x][y] = 115;
                        }
                    }
                    if (waterfall[x][y] < colorDw) {
                        g.setColor(Color.BLACK);
                    } else if (waterfall[x][y] > colorUp) {
                        g.setColor(Color.GREEN);
                    } else {
                        g.setColor(new Color(0, ((((waterfall[x][y] - 55) * 4) - colorDw) * 240 / (colorUp - colorDw)), 0));
                        //g.setColor(new Color(0, (waterfall[x][y] - colorDw) * 255 / (colorUp - colorDw), 0));
                    }
                }
                g.fillRect(xi, yi, limX, limY);
                if ((x % 10) == 0) {
                    g.setColor(Color.WHITE);
                    g.drawLine(inicioCascadaX - 10, yi, inicioCascadaX - 05, yi);
                    g.drawString(time[x], 5, yi + 3);
                }
                xi += limX;
            }
            yi += limY;
        }
        /*if (getBMarcacion()) {
            fX = (((getSize().width - 5 - inicioCascadaX) / 36) * (marcacion / 10)) + inicioCascadaX;
            System.out.println(fX);
            fY = inicioCascadaY - 3;
            marcacion(g, fX, fY);
        }*/
        //new-------------------------------------------------------------------------------------------------------------------
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        float[] dash = {5};
        g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, dash, 0.0f));
        g2d.drawLine(inicioCascadaX - 5, 95 - (colorUp * 90 / 255), getSize().width, 95 - (colorUp * 90 / 255));
        g2d.drawLine(inicioCascadaX - 5, 95 - (colorDw * 90 / 255), getSize().width, 95 - (colorDw * 90 / 255));
    }

    public void setInfo(String infoActual, String hora) {
        info = "";
        int[] infoActualNum = new int[iActual.length];
        for (int x = 0; x < infoActualNum.length; x++) {
            infoActualNum[x] = 0;
        }
        int n = 0;
        char[] charArray = infoActual.toCharArray();
        for (char temp : charArray) {
            if (!(temp == ',') && !(temp == ';')) {
                info += temp;
            } else {
                try {
                    infoActualNum[n] = Integer.parseInt(info);
                    if (infoActualNum[n] < 55) {
                        infoActualNum[n] = 55;
                    }
                    if (infoActualNum[n] > 115) {
                        infoActualNum[n] = 115;
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                info = "";
                n++;
            }
        }
        setIActual(infoActualNum);
        tiempoLocal++;
        //if (tiempoOper < tiempoLocal) {
        waterfall = getWaterfall();
        for (int x = waterfall.length - 1; x > 0; x--) {
            waterfall[x] = waterfall[x - 1];
        }
        waterfall[0] = infoActualNum;
        setWaterfall(waterfall);
        time = getTime();
        for (int x = time.length - 1; x > 0; x--) {
            time[x] = time[x - 1];
        }
        time[0] = hora;
        setTime(time);
        tiempoLocal = 0;
        //}
        repaint();
    }

    public void setInfo(int[] infoActualNum, String hora) {
        setIActual(infoActualNum);
        waterfall = getWaterfall();
        for (int x = waterfall.length - 1; x > 0; x--) {
            waterfall[x] = waterfall[x - 1];
        }
        waterfall[0] = infoActualNum;
        setWaterfall(waterfall);
        time = getTime();
        for (int x = time.length - 1; x > 0; x--) {
            time[x] = time[x - 1];
        }
        time[0] = hora;
        setTime(time);
        tiempoLocal = 0;
        //}
        repaint();
    }
}
