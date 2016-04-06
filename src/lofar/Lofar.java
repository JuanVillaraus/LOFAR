/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lofar;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author juan
 */
public class Lofar extends JComponent {

    int sizeCanalX = 0;
    int sizeCanalY;

    int xi, yi, c;
    //int inc = 255 / 11;
    String infor;
    int ml[];
    //Graphics g;
    int gn = 0;
    //despliegue d = new despliegue();
    String info;

    public static void main(String[] args) {
        JFrame window = new JFrame();
        //window.setUndecorated(true);
        window.setType(javax.swing.JFrame.Type.UTILITY);
        //window.setType(Window.Type.UTILITY);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Lofar lf = new Lofar();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.add(lf);
        window.pack();
        comInterfaz c = new comInterfaz() {
        };
        //c.run();
        //c.run(window);
    }
    //private String hw;

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(680, 550);
    }

    @Override
    protected void paintComponent(Graphics g) {
        gn++;
        System.out.println("paint component ciclo numero: " + gn);
        //System.out.println(hw);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getSize().width, getSize().height);

        sizeCanalX = (getSize().width - 50) / 101;
        sizeCanalY = ((getSize().height) - 100) / 101;
        //despliegue d = new despliegue();
        desp(g, sizeCanalX, sizeCanalY);

    }

    public void desp(Graphics g, int limX, int limY) {
        System.out.println("estoy en el despliegue");
        archivo a = new archivo();

        String DIR = "resource/dataEj1.txt";   //variable estatica que guarda el nombre del archivo donde se guardara la informacion recivida para desplegarse
        int n;  //variable de control int que guarda el numero del color a desplegar
        yi = 100;     //variable de control grafico en Y que guarda la acumulacion del incremento para la graficacion
        xi = 50;     //variable de control grafico en Y que guarda la acumulacion del incremento para la graficacion
        String box = ""; //variable que guarda de char en char hasta llegar al tope asignado para proceder a convertirlo a int
        int[] topLine = new int[101];
        boolean bTopLine = true;
        c = 0;
        int t = 0;

        g.setColor(Color.WHITE);
        g.drawLine(45, 100, 45, getSize().height - 20);
        g.drawLine(45, getSize().height - 20, getSize().width, getSize().height - 20);
        g.drawString("t/seg", 5, 100);
        for (int i = 0; i < 11; i++) {
            g.drawLine(45 + ((limX * 10) * i), getSize().height - 20, 45 + ((limX * 10) * i), getSize().height - 15);
            g.drawString((i * 500) + "", 30 + ((limX * 10) * i), getSize().height - 1);
        }

        info = a.leerTxtLine(DIR, 101);
        char[] charArray = info.toCharArray();
        for (char temp : charArray) {
            if (!(temp == ',') && !(temp == ';')) {
                box += "" + temp;
            } else if (temp == ',') {
                n = Integer.parseInt(box);
                if (bTopLine) {
                    topLine[c] = n;
                }
                if (n > 0 && n < 255) {
                    g.setColor(new Color(0, n, 0));
                    g.fillRect(xi, yi, limX, limY);
                    xi += limX;
                    box = "";
                    c++;
                } else {
                    System.out.println("Error #??: el valor a desplegar esta fuera de rango");
                }
            } else if (temp == ';') {
                n = Integer.parseInt(box);
                if (bTopLine) {
                    topLine[c] = n;
                }
                if (n > 0 && n < 255) {
                    g.setColor(new Color(0, n, 0));
                    g.fillRect(xi, yi, limX, limY);
                    box = "";
                    bTopLine = false;
                }
                xi = 50;
                yi += limY;
                t++;
                if ((t % 5) == 0) {
                    g.setColor(Color.WHITE);
                    g.drawLine(35, yi, 45, yi);
                    g.drawString(t + "", 15, yi + 3);
                }
            } else {
                System.out.println("Error #??: el valor a desplegar no se reconoce");
            }
        }
        xi = (limX / 2) + 50;
        yi = 95;
        g.setColor(new Color(0, 150, 0));
        for (int i = 0; i < 100; i++) {
            g.drawLine(xi, 95 - (topLine[i] * 90 / 255), xi + limX, 95 - (topLine[i + 1] * 90 / 255));
            xi += limX;
        }
    }
}
