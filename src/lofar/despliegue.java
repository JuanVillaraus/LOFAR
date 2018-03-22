/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lofar;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
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
    private int[][] waterfallProm;
    private String[] time;
    FFT f = new FFT();
    double[] audio;
    double[] fft;
    int[] fftInt;
    int act = 0;
    archivo a = new archivo();

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
        audio = new double[longLF * 2];
        fft = new double[longLF * 2];
        fftInt = new int[longLF];

        window.setSize(dimensionX, dimensionY);
        window.setLocation(posicionX, posicionY);
        waterfall = new int[60][longLF];
        for (int x = 0; x < waterfall.length; x++) {                                                 //inicializa el waterfall en cero
            for (int y = 0; y < longLF; y++) {
                waterfall[x][y] = 0;
            }
        }
        waterfallProm = new int[30][longLF];
        for (int x = 0; x < waterfallProm.length; x++) {                                                 //inicializa el waterfall en cero
            for (int y = 0; y < longLF; y++) {
                waterfallProm[x][y] = 0;
            }
        }
        setWaterfall(waterfall);
        iActual = new int[longLF];
        for (int i = 0; i < longLF; i++) {
            iActual[i] = LimYdw;
        }
        setIActual(iActual);
        time = new String[60];
        for (int i = 0; i < time.length; i++) {
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
    
    public void setWaterfallProm(int[][] waterfallProm) {
        this.waterfallProm = waterfallProm;
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
    
    public int[][] getWaterfallProm() {
        return this.waterfallProm;
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
        //colorDw = ((colorDw-LimYdw)*255)/(LimYup-LimYdw);
        //int marcacion = Integer.parseInt(a.leerTxtLine("resource/marcacion.txt"));

        g.setColor(Color.WHITE);
        /*g.drawLine(inicioCascadaX - 5, 1, inicioCascadaX - 5, inicioCascadaY - 30);
        g.drawLine(inicioCascadaX - 5, inicioCascadaY - 30, getSize().width, inicioCascadaY - 30);
        g.drawLine(inicioCascadaX - 5, inicioCascadaY, inicioCascadaX - 5, getSize().height - 20);*/

        g.drawLine(inicioCascadaX - 5, 1, inicioCascadaX - 5, inicioCascadaY - 30);
        g.drawLine(inicioCascadaX - 5, inicioCascadaY - 30, getSize().width, inicioCascadaY - 30);
        g.drawLine(inicioCascadaX - 5, inicioCascadaY +(limY*64), getSize().width, inicioCascadaY +(limY*64));
        g.drawLine(inicioCascadaX - 5, inicioCascadaY, inicioCascadaX - 5, getSize().height - 20);
        g.drawLine(inicioCascadaX - 10, 5, inicioCascadaX - 5, 5);
        g.drawLine(inicioCascadaX - 10, (((inicioCascadaY - 35) + 5) / 2), inicioCascadaX - 5, (((inicioCascadaY - 35) + 5) / 2));
        g.drawLine(inicioCascadaX - 10, inicioCascadaY - 35, inicioCascadaX - 5, inicioCascadaY - 35);
        if (null != modelo) {
            switch (modelo) {
                case "SSF":
                    g.drawString(LimYup - 120 + "", inicioCascadaX - 35, 11);
                    g.drawString(((LimYup + LimYdw) / 2) - 120 + "", inicioCascadaX - 35, (((inicioCascadaY - 35) + 5) / 2) + 5);
                    g.drawString(LimYdw - 120 + "", inicioCascadaX - 40, inicioCascadaY - 30);
                    break;
                default:
                    g.drawString(LimYup - 185 + "", inicioCascadaX - 35, 11);
                    g.drawString(((LimYup + LimYdw) / 2) - 185 + "", inicioCascadaX - 35, (((inicioCascadaY - 35) + 5) / 2) + 5);
                    g.drawString(LimYdw - 185 + "", inicioCascadaX - 40, inicioCascadaY - 30);
                    break;
            }
        }
        if (null != modelo) {
            switch (modelo) {
                case "SSPP":
                    for (int i = 0; i <= 6; i++) {
                        g.drawLine(inicioCascadaX + ((((limX * longLF) * 96) / 600) * i), inicioCascadaY - 30, inicioCascadaX + (((limX * longLF) / 6) * i), inicioCascadaY - 25);
                        if (i != 6) {
                            g.drawString((i * 100) + "Hz", (inicioCascadaX - 20) + ((((limX * longLF) * 96) / 600) * i), inicioCascadaY - 10);
                        } else {
                            g.drawString((i * 100) + "Hz", (inicioCascadaX - 20) + ((((limX * longLF) * 96) / 600) * i), inicioCascadaY - 10);
                        }
                    }
                    break;
                case "SSF":
                    for (int i = 0; i < 9; i++) {
                        g.drawLine(inicioCascadaX + ((((limX * longLF * 200) / 208) / 8) * i), inicioCascadaY - 30, inicioCascadaX + ((((limX * longLF * 200) / 208) / 8) * i), inicioCascadaY - 25);
                        if (i != 8) {
                            g.drawString((i * 2.5) + "KHz", inicioCascadaX + ((((limX * longLF * 200) / 208) / 8) * i) - 15, inicioCascadaY - 10);
                        } else {
                            g.drawString((i * 2.5) + "KHz", inicioCascadaX + ((((limX * longLF * 200) / 208) / 8) * i) - 23, inicioCascadaY - 10);
                        }
                    }
                    break;
                case "SSPV":
                    for (int i = 0; i < 7; i++) {
                        g.drawLine(inicioCascadaX + (((limX * longLF) / 6) * i), inicioCascadaY - 30, inicioCascadaX + (((limX * longLF) / 6) * i), inicioCascadaY - 25);
                        if (i != 6) {
                            g.drawString((i * 2.5) + "KHz", inicioCascadaX + (((limX * longLF) / 6) * i) - 10, inicioCascadaY - 10);
                        } else {
                            g.drawString((i * 2.5) + "KHz", inicioCascadaX + (((limX * longLF) / 6) * i) - 23, inicioCascadaY - 10);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        //new-------------------------------------------------------------------------------------------------------------------
        //System.out.println(LimYup + " " + LimYdw + " " + iActual[0] + " " + (LimYup - LimYdw) + " " + (iActual[0] * 90 / (LimYup - LimYdw)) + " " + waterfall[0][0] + " " + (-1 * (waterfall[0][0] - LimYup) * 255 / (LimYup - LimYdw)));
        g.setColor(new Color(0, 150, 0));
        if (null != modelo) {
            switch (modelo) {
                case "SSF":
                    //System.out.println("");
                    for (int i = 0; i < longLF - 1; i++) {
                        //System.out.print(" " + iActual[i]);
                        g.drawLine(xi, -1 * (((iActual[i]) - LimYup) * 90 / (LimYup - LimYdw)), xi + limX, -1 * (((iActual[i + 1]) - LimYup) * 90 / (LimYup - LimYdw)));
                        xi += limX;
                    }
                    break;
                case "SSPV":
                    for (int i = 0; i < longLF - 1; i++) {
                        /*if (iActual[i] < 0) {
                    iActual[i] = 0;
                    } else if (iActual[i] > 255) {
                    iActual[i] = 255;
                    }*/
                        //System.out.print(iActual[i] + " ");
                        //g.drawLine(xi, 95 - (iActual[i] * 90 / 255), xi + limX, 95 - (iActual[i + 1] * 90 / 255));
                        g.drawLine(xi, -1 * ((iActual[i] - LimYup) * 90 / (LimYup - LimYdw)), xi + limX, -1 * ((iActual[i + 1] - LimYup) * 90 / (LimYup - LimYdw)));
                        xi += limX;
                    }
                    break;
                default:
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
                    break;
            }
        }

        //System.out.println();
        for (int x = 0; x < waterfall.length; x++) {
            xi = inicioCascadaX;
            for (int y = 0; y < waterfall[x].length; y++) {
                if (waterfall[x][y] >= 0 && waterfall[x][y] <= 255) {
                    if (x == 0 && y < 50) {
                        //System.out.print(" " + waterfall[x][y]);
                    }
                    if (waterfall[x][y] < colorDw) {
                        g.setColor(Color.BLACK);
                    } else if (waterfall[x][y] > colorUp) {
                        g.setColor(Color.GREEN);
                    } else {
                        g.setColor(new Color(0, (waterfall[x][y] - colorDw) * 255 / (colorUp - colorDw), 0));
                        //g.setColor(new Color(0, waterfall[x][y], 0));
                    }
                    g.fillRect(xi, yi, limX, limY);
                    if ((x % 10) == 0) {
                        g.setColor(Color.WHITE);
                        g.drawLine(inicioCascadaX - 10, yi, inicioCascadaX - 05, yi);
                        g.drawString(time[x], 5, yi + 3);
                    }
                    xi += limX;
                } else {
                    System.out.println("Error #??: el valor a desplegar esta fuera de rango");
                }
            }
            yi += limY;
        }
        
        yi+=(limY*5);
        for (int x = 0; x < waterfallProm.length; x++) {
            xi = inicioCascadaX;
            for (int y = 0; y < waterfallProm[0].length; y++) {
                if (waterfallProm[x][y] >= 0 && waterfallProm[x][y] <= 255) {
                    if (x == 0 && y < 50) {
                        //System.out.print(" " + waterfall[x][y]);
                    }
                    if (waterfallProm[x][y] < colorDw) {
                        g.setColor(Color.BLACK);
                    } else if (waterfallProm[x][y] > colorUp) {
                        g.setColor(Color.GREEN);
                    } else {
                        g.setColor(new Color(0, (waterfallProm[x][y] - colorDw) * 255 / (colorUp - colorDw), 0));
                        //g.setColor(new Color(0, waterfall[x][y], 0));
                    }
                    g.fillRect(xi, yi, limX, limY);
                    xi += limX;
                } else {
                    System.out.println("Error #??: el valor a desplegar esta fuera de rango");
                }
            }
            yi += limY;
        }
        //System.out.println();
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
        int[][] Waterfall = getWaterfall();
        int[][] WaterfallProm = getWaterfallProm();
        int[] preWaterfall = new int[iActual.length];
        int[] preWaterfall2 = new int[iActual.length];
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
                    boolean isNumber = Pattern.matches("[0-9]+", info);
                    if (isNumber) {
                        infoActualNum[n] = Integer.parseInt(info);
                    } else {
                        infoActualNum[n] = 0;
                    }
                } catch (Exception e) {
                    System.err.println("LOFAR/despliegue - Error al convertir la información recivida en numero " + n + "\tError:" + e.getMessage());
                }
                if (infoActualNum[n] < LimYdw) {
                    //System.out.print("menor: " + infoActualNum[n] + " ");
                    infoActualNum[n] = LimYdw;
                    //System.out.println(infoActualNum[n]);
                }
                if (infoActualNum[n] > LimYup) {
                    infoActualNum[n] = LimYup;
                }
                info = "";
                if (n < (longLF - 1)) {
                    n++;
                }
            }
        }
        setIActual(infoActualNum);
        tiempoLocal++;
        for (int x = waterfall.length - 1; x > 0; x--) {
            waterfall[x] = waterfall[x - 1];
        }

        for (int i = 0; i < infoActualNum.length; i++) {
            preWaterfall[i] = (infoActualNum[i] - LimYdw) * 255 / (LimYup - LimYdw);
        }
        waterfall[0] = preWaterfall;
        setWaterfall(waterfall);
        for (int x = waterfallProm.length - 1; x > 0; x--) {
            waterfallProm[x] = waterfallProm[x - 1];
        }
        for (int y = 0; y < waterfallProm[0].length; y++) {
            preWaterfall2[y] = 0;
            for (int x = 0; x < waterfallProm.length; x++) {
                preWaterfall2[y] += waterfall[x][y];
            }
            preWaterfall2[y] = preWaterfall2[y]/waterfallProm.length;
        }
        waterfallProm[0] = preWaterfall2;
        setWaterfallProm(waterfallProm);
        time = getTime();
        for (int x = time.length - 1; x > 0; x--) {
            time[x] = time[x - 1];
        }
        time[0] = hora;
        setTime(time);
        tiempoLocal = 0;
        repaint();
    }

    public void setInfo(int[] infoActual, String hora) {
        act++;
        //double[] audio = new double[longLF*2];
        for (int x = 0; x < audio.length; x++) {
            audio[x] = infoActual[x];
        }

        //double[] fft = new double[longLF*2];
        //int[] fftInt = new int[longLF];
        fft = f.FFT(audio);
        for (int i = 0; i < fftInt.length; i++) {
            fftInt[i] = (int) fft[i];
        }

        int[] infoActualNum = new int[longLF];
        for (int x = 0; x < infoActualNum.length; x++) {
            infoActualNum[x] = fftInt[x];
        }

        for (int x = 0; x < infoActualNum.length; x++) {
            if (infoActualNum[x] < LimYdw) {
                infoActualNum[x] = LimYdw;
            }
            if (infoActualNum[x] > LimYup) {
                infoActualNum[x] = LimYup;
            }
        }
        setIActual(infoActualNum);
        if (act == 25) {
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
            act = 0;
        }
        //tiempoLocal = 0;
        //}
        repaint();
        System.out.println();
    }

    public void save() {
        String s = "";
        for (int x = 0; x < waterfall.length; x++) {
            for (int y = 0; y < waterfall[0].length; y++) {
                if (y == 0) {
                    s += time[y] + ",";
                }
                s += waterfall[x][y];
                if (y < waterfall[0].length - 1) {
                    s += ",";
                }
            }
            s += ";";
            if (x < waterfall.length - 1) {
                s += "\n";
            }
        }
        a.save("resource/LOFAR", s);
    }
}
