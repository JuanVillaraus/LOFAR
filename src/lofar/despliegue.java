/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lofar;

import java.awt.*;
//import javax.swing.JComponent;

/**
 *
 * @author juan
 */
class despliegue {

    int xi, yi, c;
    //int inc = 255 / 11;
    //int ml[];
    String ch = "";
    String info;
    String hw = "ESPERANDO... un hola";

    public void run(Graphics g, int limX, int limY) {
        System.out.println("estoy en el RUN del despliegue");
        archivo a = new archivo();

        String DIR = "resource/dataEj1.txt";   //variable estatica que guarda el nombre del archivo donde se guardara la informacion recivida para desplegarse
        int n = 0;  //variable de control int que guarda el numero del color a desplegar
        yi = 100;     //variable de control grafico en Y que guarda la acumulacion del incremento para la graficacion
        xi = 0;     //variable de control grafico en Y que guarda la acumulacion del incremento para la graficacion
        String box = ""; //variable que guarda de char en char hasta llegar al tope asignado para proceder a convertirlo a int
        int[] topLine = new int[101];
        boolean bTopLine = true;
        c = 0;

        for (int i = 0; i < 101; i++) {
            topLine[i] = 0;
        }

        //System.out.println(hw);
        info = a.leerTxtLine(DIR);
        System.out.println(info);
        char[] charArray = info.toCharArray();

        /*System.out.println(" ");
        for (char temp : charArray) {
            System.out.print(temp);
        }*/

        for (char temp : charArray) {
            if (!(temp == ',') && !(temp == ';')) {
                box += "" + temp;
            } else if (temp == ',') {
                n = Integer.parseInt(box);
                if (bTopLine) {
                    topLine[c] = n;
                    c++;
                }
                if (n > 0 && n < 255) {
                    g.setColor(new Color(0, 255 - n, 0));
                    g.fillRect(xi, yi, limX + 1, limY);
                    xi += limX + 1;
                    box = "";
                } else {
                    System.out.println("Error #??: el valor a desplegar esta fuera de rango");
                }
            } else if (temp == ';') {
                n = Integer.parseInt(box);
                if (bTopLine) {
                    topLine[c] = n;
                    bTopLine = false;
                }
                if (n > 0 && n < 255) {
                    g.setColor(new Color(0, 255 - n, 0));
                    g.fillRect(xi, yi, limX + 1, limY);
                    box = "";
                }
                xi = 0;
                yi += limY;
            } else {
                System.out.println("Error #??: el valor a desplegar no se reconoce");
            }
        }

        xi = limX / 2;
        yi = 95;
        g.setColor(new Color(0, 150, 0));
        for (int i = 0; i < 100; i++) {
            g.drawLine(xi, 95 - (topLine[i] * 90 / 255), xi + limX + 1, 95 - (topLine[i + 1] * 90 / 255));
            xi += limX + 1;
        }
        System.out.println("esto esta en el hw: " +gethw());
    }
    public String gethw() {
        return hw;
    }
    
    public void sethw(String hw){
        this.hw=hw;
    }
}
