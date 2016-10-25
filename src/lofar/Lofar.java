/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lofar;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juan
 */
public class Lofar extends JComponent {

    int inicioCascadaX = 75;
    int inicioCascadaY = 130;
    int sizeCanalX = 0;
    int sizeCanalY;
    int xi, yi, c;
    String infor;
    int ml[];
    int gn = 0;
    String info, infoRcv;

    public static void main(String[] args) {
        JFrame window = new JFrame();
        //window.setUndecorated(true);
        window.setType(javax.swing.JFrame.Type.UTILITY);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Lofar lf = new Lofar();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.add(lf);
        window.pack();
        comInterfaz c = new comInterfaz();
        window.setAlwaysOnTop(true);
        window.setFocusable(true);
        Properties prop = new Properties();
        InputStream input = null;
        int posicionX = 0;
        int posicionY = 0;
        try {
            input = new FileInputStream("config.properties");
            //load a properties file
            prop.load(input);
            //get the propperty value and print it out
            posicionX = Integer.parseInt(prop.getProperty("posicionX"));
            posicionY = Integer.parseInt(prop.getProperty("posicionY"));
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
        window.setLocation(posicionX, posicionY);
        c.run(window);
    }
    //private String hw;

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
        System.out.println("paint component ciclo numero: " + gn);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getSize().width, getSize().height);

        sizeCanalX = (getSize().width - inicioCascadaX) / 62;
        sizeCanalY = ((getSize().height) - inicioCascadaY) / 100;
        desp(g, sizeCanalX, sizeCanalY);

    }

    public void desp(Graphics g, int limX, int limY) {
        System.out.println("estoy en el despliegue");
        archivo a = new archivo();

        String DIR = "resource/lofarDataRcv.txt";   //variable estatica que guarda el nombre del archivo donde se guardara la informacion recivida para desplegarse
        int n;  //variable de control int que guarda el numero del color a desplegar
        xi = inicioCascadaX;     //variable de control grafico en Y que guarda la acumulacion del incremento para la graficacion
        yi = inicioCascadaY;     //variable de control grafico en Y que guarda la acumulacion del incremento para la graficacion
        String box = ""; //variable que guarda de char en char hasta llegar al tope asignado para proceder a convertirlo a int
        int[] topLine = new int[62];
        String topWord = "";
        boolean bTopLine = true;
        boolean bTopWord = true;
        c = 0;
        int t = 0;

        int colorUp = Integer.parseInt(a.leerTxtLine("resource/colorUp.txt"));
        int colorDw = Integer.parseInt(a.leerTxtLine("resource/colorDw.txt"));

        g.setColor(Color.WHITE);
        g.drawLine(inicioCascadaX - 5, 1, inicioCascadaX - 5, inicioCascadaY - 30);
        g.drawLine(inicioCascadaX - 5, inicioCascadaY - 30, getSize().width, inicioCascadaY - 30);
        g.drawLine(inicioCascadaX - 5, inicioCascadaY, inicioCascadaX - 5, getSize().height - 20);
        g.drawLine(inicioCascadaX - 10, 5, inicioCascadaX - 5, 5);
        g.drawString("-65", inicioCascadaX - 35, 11);
        g.drawLine(inicioCascadaX - 10, (((inicioCascadaY - 35) + 5) / 2), inicioCascadaX - 5, (((inicioCascadaY - 35) + 5) / 2));
        g.drawString("-95", inicioCascadaX - 35, (((inicioCascadaY - 35) + 5) / 2) + 5);
        g.drawLine(inicioCascadaX - 10, inicioCascadaY - 35, inicioCascadaX - 5, inicioCascadaY - 35);
        g.drawString("-125", inicioCascadaX - 40, inicioCascadaY - 30);
        for (int i = 0; i < 7; i++) {
            g.drawLine(inicioCascadaX + (((limX * 62) / 6) * i), inicioCascadaY - 30, inicioCascadaX + (((limX * 62) / 6) * i), inicioCascadaY - 25);
            if (i != 6) {
                g.drawString((i * 100) + "Hz", (inicioCascadaX - 20) + (((limX * 62) / 6) * i), inicioCascadaY - 10);
            } else {
                g.drawString((i * 100) + "Hz", (inicioCascadaX - 20) + (limX * 62) - 20, inicioCascadaY - 10);
            }
        }

        infoRcv = a.leerTxtLine(DIR, 100);
        char[] charArray = infoRcv.toCharArray();
        for (char temp : charArray) {
            if (!(temp == ',') && !(temp == ';')) {
                if (bTopWord) {
                    topWord += temp;
                } else {
                    box += "" + temp;
                }
            } else if (temp == ',') {
                if (bTopWord) {
                    bTopWord = false;
                } else {
                    n = Integer.parseInt(box);
                    if (n < 55) {
                        n = 55;
                    }
                    if (n > 115) {
                        n = 115;
                    }
                    if (bTopLine) {
                        topLine[c] = n;
                    }
                    if (((n - 55) * 4) < colorDw) {
                        g.setColor(Color.BLACK);
                    } else if (((n - 55) * 4) > colorUp) {
                        g.setColor(Color.GREEN);
                    } else {
                        g.setColor(new Color(0, ((((n - 55) * 4) - colorDw ) * 240 / (colorUp - colorDw )), 0));
                    }
                    g.fillRect(xi, yi, limX, limY);
                    xi += limX;
                    box = "";
                    c++;
                }
            } else if (temp == ';') {
                n = Integer.parseInt(box);
                if (n < 55) {
                    n = 55;
                }
                if (n > 115) {
                    n = 115;
                }
                if (bTopLine) {
                    topLine[c] = n;
                }
                if (n >= 0 && n <= 255) {
                    if (n < (colorDw * 60 / 255) + 55) {
                        n = 55;
                    }
                    if (n > (colorUp * 60 / 255) + 55) {
                        n = 115;
                    }
                    g.setColor(new Color(0, (n - 55) * 4, 0));
                    g.fillRect(xi, yi, limX, limY);
                    box = "";
                    bTopLine = false;
                }
                xi = inicioCascadaX;
                yi += limY;
                t++;
                if ((t % 10) == 0) {
                    g.setColor(Color.WHITE);
                    g.drawLine(inicioCascadaX - 10, yi, inicioCascadaX - 05, yi);
                    g.drawString(topWord + "", 5, yi + 3);
                }
                bTopWord = true;
                info = topWord + ",";
                topWord = "";
            } else {
                System.out.println("Error #??: el valor a desplegar no se reconoce");
            }
        }
        xi = (limX / 2) + inicioCascadaX;
        g.setColor(new Color(0, 150, 0));
        for (int i = 0; i < 61; i++) {
            g.drawLine(xi, 95 - (((topLine[i]) - 55) * 90 / 60), xi + limX, 95 - (((topLine[i + 1]) - 55) * 90 / 60));
            xi += limX;
            topLine[i] -= 180;
            if (i < 60) {
                info += topLine[i] + ",";
            } else {
                info += topLine[i] + ";";
                System.out.println("guardare en lofarData.txt: " + info);
                try {
                    a.escribirTxt("resource/lofarData.txt", info);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    System.exit(1);
                }
                info = "";
            }
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        float[] dash = {5};
        g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, dash, 0.0f));
        g2d.drawLine(inicioCascadaX - 5, 95 - (colorUp * 90 / 255), getSize().width, 95 - (colorUp * 90 / 255));
        g2d.drawLine(inicioCascadaX - 5, 95 - (colorDw * 90 / 255), getSize().width, 95 - (colorDw * 90 / 255));
    }
}
