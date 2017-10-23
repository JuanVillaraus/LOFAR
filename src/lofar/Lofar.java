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
public class Lofar {

    public static void main(String[] args) {
        comInterfaz c = new comInterfaz();
        c.start();
        //int[] audio = {139, 206, 167, 89, 118, 140, 93, 136, 217, 162, 66, 43, 190, 200, 115, 136, 135, 60, 81, 217, 214, 107, 32, 111, 144, 111, 141, 167, 90, 12, 123, 215, 153, 76, 121, 129, 104, 156, 221, 161, 54, 92, 172, 196, 112, 113, 127, 73, 55, 174, 218, 107, 56, 129, 157, 136, 185, 204, 94, 51, 121, 230, 165, 90, 90, 137, 101, 140, 199, 155, 20, 54, 166, 180, 125, 140, 122, 78, 94, 208, 198, 108, 44, 141, 172, 125, 156, 185, 63, 25, 124, 211, 134, 91, 98, 144, 86, 148, 222, 167, 26, 61, 172, 172, 117, 108, 134, 71, 69, 203, 205, 96, 53, 126, 129, 119, 148, 171, 78, 33, 127, 242, 165, 89, 104, 106, 80, 126, 207, 167, 29, 55, 182, 178, 107, 122, 127, 83, 83, 202, 213, 126, 47, 111, 143, 131, 148, 161, 89, 12, 138, 251, 173, 76, 123, 133, 70, 134, 213, 167, 38, 79, 161, 217, 140, 126, 140, 62, 68, 196, 232, 111, 74, 119, 157, 94, 149, 180, 96, 25, 113, 220, 192, 68, 106, 114, 114, 146, 215, 159, 59, 71, 178, 187, 143, 136, 139, 81, 79, 182, 224, 78, 37, 137, 151, 133, 150, 182, 101, 27, 102, 239, 156, 84, 118, 115, 115, 145, 214, 155, 55, 69, 185, 220, 130, 144, 135, 97, 79, 182, 219, 77, 62, 108, 163, 141, 159, 183, 84, 32, 141, 216, 147, 71, 117, 126, 102, 113, 202, 151, 23, 79, 192, 165, 126, 117, 134, 74, 102, 200, 210, 110, 57, 125, 189, 127, 147, 186, 78, 28, 122, 243, 172, 88, 117, 141, 99, 117, 191, 160, 61, 74, 192, 208, 116, 121, 150, 64, 109, 207, 211, 94, 53, 132, 175, 134, 140, 180, 75, 41, 137, 227, 151, 97, 118, 120, 82, 149, 220, 160, 43, 72, 173, 205, 127, 119, 130, 93, 72, 181, 234, 103, 75, 119, 168, 129, 159, 158, 98, 43, 129, 221, 164, 53, 122, 134, 106, 144, 216, 178, 25, 62, 192, 185, 131, 108, 140, 88, 51, 192, 203, 86, 54, 137, 157, 123, 176, 177, 100, 29, 136, 210, 164, 69, 103, 138, 124, 130, 219, 154, 41, 99, 181, 177, 136, 141, 116, 65, 74, 192, 220, 92, 52, 129, 149, 134, 139, 189, 92, 42, 116, 236, 160, 79, 102, 138, 122, 147, 213, 150, 41, 51, 177, 197, 121, 145, 129, 79, 84, 177, 212, 112, 31, 121, 147, 117, 141, 189, 95, 38, 122, 224, 174, 63, 96, 142, 121, 124, 218, 144, 48, 65, 165, 191, 139, 127, 124, 59, 64, 200, 223, 89, 56, 109, 179, 102, 149, 188, 95, 34, 123, 251, 157, 89, 121, 125, 118, 141, 193, 154, 38, 72, 188, 194, 104, 110, 153, 84, 85, 215, 243, 89, 47, 128, 161, 132, 156, 192, 60, 33, 126, 219, 165, 87, 102, 130, 78, 121, 208, 157, 26, 48, 186, 187, 140, 136, 121, 45, 82, 192, 226, 111, 43, 125, 150, 145, 154, 172, 89, 57, 136, 231, 168, 86, 112, 129, 121, 133, 202, 154, 55, 74, 194, 182, 125, 106, 148, 72, 81, 208, 215, 104, 37, 116, 167, 127, 133, 171, 133, 39, 132, 220, 140, 82, 66, 117, 80, 133, 226, 166, 34, 42, 172, 169, 128, 122, 154, 72, 60, 196, 228, 92, 54, 127, 165, 125, 151, 177, 94, 14, 129, 210, 165, 65, 123, 140, 92, 162, 227, 171, 13, 47, 169, 181, 106, 138, 129, 72, 73, 194, 237, 75, 34, 117, 167, 116, 146, 188, 79, 47, 134, 233, 163, 67, 85, 131, 108, 145, 211, 181, 42, 58, 180, 177, 132, 149, 149, 77, 63, 187, 224, 98, 64, 131, 141, 137, 162, 201, 96, 36, 109, 220, 144, 78, 95, 113, 99, 125, 229, 188, 42, 67, 153, 204, 115, 134, 133, 78, 79, 197, 205, 101, 27, 122, 162, 126, 157, 190, 75, 14, 121, 231, 168, 75, 113, 156, 85, 125, 189, 161, 50, 59, 193, 182, 126, 142, 144, 78, 99, 183, 213, 103, 41, 116, 169, 123, 158, 176, 90, 39, 133, 200, 159, 66, 107, 120, 99, 152, 234, 170, 19, 82, 192, 188, 154, 123, 159, 59, 75, 180, 226, 104, 38, 105, 125, 117, 134, 187, 109, 7, 107, 219, 176, 96, 125, 124, 90, 122, 220, 170, 20, 62, 172, 193, 137, 145, 142, 68, 74, 180, 234, 87, 21, 112, 178, 133, 148, 181, 100, 39, 127, 255, 177, 48, 93, 129, 105, 132, 212, 155, 23, 91, 176, 215, 111, 126, 119, 63, 92, 205, 208, 101, 40, 134, 150, 149, 133, 182, 76, 24, 115, 245, 161, 84, 87, 117, 103, 134, 221, 175, 28, 51, 184, 183, 123, 118, 124, 80, 100, 191, 233, 117, 60, 143, 164, 108, 142, 176, 76, 26, 146, 215, 166, 84, 129, 126, 107, 120, 201, 164, 46, 57, 182, 176, 130, 120, 139, 78, 99, 181, 222, 77, 48, 133, 146, 127, 169, 200, 95, 52, 127, 214, 162, 58, 95, 121, 117, 138, 239, 165, 44, 61, 167, 187, 118, 109, 143, 72, 83, 188, 228, 91, 51, 132, 158, 117, 156, 192, 67, 35, 131, 230, 158, 84, 98, 133, 114, 134, 199, 158, 45, 60, 200, 179, 117, 150, 140, 81, 97, 187, 216, 110, 36, 126, 145, 136, 182, 176, 90, 55, 117, 221, 163, 88, 91, 117, 103, 133, 191, 137, 17, 64, 184, 193, 109, 103, 144, 73, 80, 190, 215, 123, 44, 130, 132, 110, 143, 180, 100, 66, 107, 203, 160, 69, 92, 142, 93, 142, 215, 139, 50, 60, 170, 183, 128, 144, 137, 51, 91, 188, 243, 107, 55, 119, 149, 120, 136, 168, 111, 25, 128, 246, 157, 77, 114, 132, 105, 131, 210, 181, 66, 63, 168, 184, 115, 120, 110, 54, 80, 187, 236, 75, 34, 129, 166, 124, 140, 148, 89, 39, 128, 208, 176, 90, 103, 130, 100, 138, 222, 155, 60, 49, 174, 174, 114, 108, 151, 75, 78, 196, 210, 94, 58, 122, 166, 138, 166, 172, 86, 38, 117, 241, 149, 94};
        /*int[] audio = {120, 220, 112, 21, 135, 218, 98, 23, 150, 214, 83, 28, 164, 208, 70, 36, 177, 200, 57, 45, 188, 190, 46, 55, 199, 179, 37, 67, 207, 166, 30, 81, 213, 152, 24, 95, 218, 138, 21, 110, 220, 123, 20, 125, 220, 107, 21, 140, 217, 93, 25, 154, 213, 79, 31, 168, 206, 65, 38, 181, 197, 53, 48, 192, 187, 43, 59, 202, 175, 34, 72, 209, 161, 27, 86, 215, 147, 23, 100, 219, 133, 20, 115, 220, 117, 20, 130, 219, 102, 22, 145, 216, 88, 27, 159, 210, 74, 33, 173, 203, 61, 41, 185, 194, 50, 52, 195, 183, 40, 63, 204, 170, 32, 76, 212, 157, 26, 90, 217, 142, 22, 105, 219, 128, 20, 120, 220, 112, 21, 135, 218, 98, 23, 150, 214, 83, 28, 164, 208, 70, 36, 177, 200, 57, 45, 188, 190, 46, 55, 199, 179, 37, 67, 207, 166, 30, 81, 213, 152, 24, 95, 218, 138, 21, 110, 220, 123, 20, 125, 220, 107, 21, 140, 217, 93, 25, 154, 213, 79, 31, 168, 206, 65, 38, 181, 197, 53, 48, 192, 187, 43, 59, 202, 175, 34, 72, 209, 161, 27, 86, 215, 147, 23, 100, 219, 133, 20, 115, 220, 117, 20, 130, 219, 102, 22, 145, 216, 88, 27, 159, 210, 74, 33, 173, 203, 61, 41, 185, 194, 50, 52, 195, 183, 40, 63, 204, 170, 32, 76, 212, 157, 26, 90, 217, 142, 22, 105, 219, 128, 20, 120, 220, 112, 21, 135, 218, 98, 23, 150, 214, 83, 28, 164, 208, 70, 36, 177, 200, 57, 45, 188, 190, 46, 55, 199, 179, 37, 67, 207, 166, 30, 81, 213, 152, 24, 95, 218, 138, 21, 110, 220, 123, 20, 125, 220, 107, 21, 140, 217, 93, 25, 154, 213, 79, 31, 168, 206, 65, 38, 181, 197, 53, 48, 192, 187, 43, 59, 202, 175, 34, 72, 209, 161, 27, 86, 215, 147, 23, 100, 219, 133, 20, 115, 220, 117, 20, 130, 219, 102, 22, 145, 216, 88, 27, 159, 210, 74, 33, 173, 203, 61, 41, 185, 194, 50, 52, 195, 183, 40, 63, 204, 170, 32, 76, 212, 157, 26, 90, 217, 142, 22, 105, 219, 128, 20, 120, 220, 112, 21, 135, 218, 98, 23, 150, 214, 83, 28, 164, 208, 70, 36, 177, 200, 57, 45, 188, 190, 46, 55, 199, 179, 37, 67, 207, 166, 30, 81, 213, 152, 24, 95, 218, 138, 21, 110, 220, 123, 20, 125, 220, 107, 21, 140, 217, 93, 25, 154, 213, 79, 31, 168, 206, 65, 38, 181, 197, 53, 48, 192, 187, 43, 59, 202, 175, 34, 72, 209, 161, 27, 86, 215, 147, 23, 100, 219, 133, 20, 115, 220, 117, 20, 130, 219, 102, 22, 145, 216, 88, 27, 159, 210, 74, 33, 173, 203, 61, 41, 185, 194, 50, 52, 195, 183, 40, 63, 204, 170, 32, 76, 212, 157, 26, 90, 217, 142, 22, 105, 219, 128, 20, 120, 220, 112, 21, 135, 218, 98, 23, 150, 214, 83, 28, 164, 208, 70, 36, 177, 200, 57, 45, 188, 190, 46, 55, 199, 179, 37, 67, 207, 166, 30, 81, 213, 152, 24, 95, 218, 138, 21, 110, 220, 123, 20, 125, 220, 107, 21, 140, 217, 93, 25, 154, 213, 79, 31, 168, 206, 65, 38, 181, 197, 53, 48, 192, 187, 43, 59, 202, 175, 34, 72, 209, 161, 27, 86, 215, 147, 23, 100, 219, 133, 20, 115, 220, 117, 20, 130, 219, 102, 22, 145, 216, 88, 27, 159, 210, 74, 33, 173, 203, 61, 41, 185, 194, 50, 52, 195, 183, 40, 63, 204, 170, 32, 76, 212, 157, 26, 90, 217, 142, 22, 105, 219, 128, 20, 120, 220, 112, 21, 135, 218, 98, 23, 150, 214, 83, 28, 164, 208, 70, 36, 177, 200, 57, 45, 188, 190, 46, 55, 199, 179, 37, 67, 207, 166, 30, 81, 213, 152, 24, 95, 218, 138, 21, 110, 220, 123, 20, 125, 220, 107, 21, 140, 217, 93, 25, 154, 213, 79, 31, 168, 206, 65, 38, 181, 197, 53, 48, 192, 187, 43, 59, 202, 175, 34, 72, 209, 161, 27, 86, 215, 147, 23, 100, 219, 133, 20, 115, 220, 117, 20, 130, 219, 102, 22, 145, 216, 88, 27, 159, 210, 74, 33, 173, 203, 61, 41, 185, 194, 50, 52, 195, 183, 40, 63, 204, 170, 32, 76, 212, 157, 26, 90, 217, 142, 22, 105, 219, 128, 20, 120, 220, 112, 21, 135, 218, 98, 23, 150, 214, 83, 28, 164, 208, 70, 36, 177, 200, 57, 45, 188, 190, 46, 55, 199, 179, 37, 67, 207, 166, 30, 81, 213, 152, 24, 95, 218, 138, 21, 110, 220, 123, 20, 125, 220, 107, 21, 140, 217, 93, 25, 154, 213, 79, 31, 168, 206, 65, 38, 181, 197, 53, 48, 192, 187, 43, 59, 202, 175, 34, 72, 209, 161, 27, 86, 215, 147, 23, 100, 219, 133, 20, 115, 220, 117, 20, 130, 219, 102, 22, 145, 216, 88, 27, 159, 210, 74, 33, 173, 203, 61, 41, 185, 194, 50, 52, 195, 183, 40, 63, 204, 170, 32, 76, 212, 157, 26, 90, 217, 142, 22, 105, 219, 128, 20, 120, 220, 112, 21, 135, 218, 98, 23, 150, 214, 83, 28, 164, 208, 70, 36, 177, 200, 57, 45, 188, 190, 46, 55, 199, 179, 37, 67, 207, 166, 30, 81, 213, 152, 24, 95, 218, 138, 21, 110, 220, 123, 20, 125, 220, 107, 21, 140, 217, 93, 25, 154, 213, 79, 31, 168, 206, 65, 38, 181, 197, 53, 48, 192, 187, 43, 59, 202, 175, 34, 72, 209, 161, 27, 86, 215, 147, 23, 100, 219, 133, 20, 115, 220, 117, 20, 130, 219, 102, 22, 145, 216, 88, 27, 159, 210, 74, 33, 173, 203, 61, 41, 185, 194, 50, 52, 195, 183, 40, 63, 204, 170, 32, 76, 212, 157, 26, 90, 217, 142, 22, 105, 219, 128, 20, 120, 220, 112, 21, 135, 218, 98, 23, 150, 214, 83, 28, 164, 208, 70, 36, 177, 200, 57, 45, 188, 190, 46, 55};
        JFrame window = new JFrame("LOFAR by SIVISO");
        despliegue desp = new despliegue(window);
        desp.setInfo(audio, "14:12:54");*/
//        FFT f = new FFT();
//        double[] fft = new double[512];
//        int[] fftInt = new int[512];
//        fft = f.FFT(audio);
//        System.out.print("FFT Absoluto: [");
//        for (int i = 0; i < (fft.length) / 2; i++) {
//            System.out.print(( fft[i]) + ",");    
//            fftInt[i]=(int)fft[i];
//        }
//        System.out.println("]");

    }
}

/*public class Lofar extends JComponent {
    

    int inicioCascadaX = 75;
    int inicioCascadaY = 130;
    int sizeCanalX = 0;
    int sizeCanalY;
    int xi, yi, c;
    String infor;
    String modelo;
    int ml[];
    int gn = 0;
    String info, infoRcv;
    int longLF;

    public static void main(String[] args) {
        JFrame window = new JFrame();
        Lofar lf = new Lofar();
        window.add(lf);
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

        try {
            input = new FileInputStream("config.properties");
            prop.load(input);
            posicionX = Integer.parseInt(prop.getProperty("posicionX"));
            posicionY = Integer.parseInt(prop.getProperty("posicionY"));
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
        window.setSize(dimensionX, dimensionY);
        window.setLocation(posicionX, posicionY);
        
        
        //window.setUndecorated(true);
        //window.setType(javax.swing.JFrame.Type.UTILITY);
        //window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //Lofar lf = new Lofar();
        //window.setLocationRelativeTo(null);
        //window.setVisible(true);
        //window.add(lf);
        //window.pack();
        //comInterfaz c = new comInterfaz();
        //window.setAlwaysOnTop(true);
        //window.setFocusable(true);
        /*Properties prop = new Properties();
        InputStream input = null;
        int posicionX = 0;
        int posicionY = 0;
        int dimensionX = 0;
        int dimensionY = 0;
        try {
            input = new FileInputStream("config.properties");
            prop.load(input);
            posicionX = Integer.parseInt(prop.getProperty("posicionX"));
            posicionY = Integer.parseInt(prop.getProperty("posicionY"));
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
        //window.setSize(dimensionX, dimensionY);
        //window.setLocation(posicionX, posicionY);
        //c.run(window);
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
            prop.load(input);
            dimensionX = Integer.parseInt(prop.getProperty("dimensionX"));
            dimensionY = Integer.parseInt(prop.getProperty("dimensionY"));
            longLF = Integer.parseInt(prop.getProperty("longLF"));
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

    public void desp(Graphics g, int limX, int limY) {
        archivo a = new archivo();

        String DIR = "resource/lofarDataRcv.txt";   //variable estatica que guarda el nombre del archivo donde se guardara la informacion recivida para desplegarse
        int n;  //variable de control int que guarda el numero del color a desplegar
        xi = inicioCascadaX;     //variable de control grafico en Y que guarda la acumulacion del incremento para la graficacion
        yi = inicioCascadaY;     //variable de control grafico en Y que guarda la acumulacion del incremento para la graficacion
        String box = ""; //variable que guarda de char en char hasta llegar al tope asignado para proceder a convertirlo a int
        int[] topLine = new int[longLF];
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
        for (int i = 0; i <= 5; i++) {
            g.drawLine(inicioCascadaX + (((limX * longLF) / 5) * i), inicioCascadaY - 30, inicioCascadaX + (((limX * longLF) / 5) * i), inicioCascadaY - 25);
            if (i != 5) {
                g.drawString((i * 100) + "Hz", (inicioCascadaX - 20) + (((limX * longLF) / 5) * i), inicioCascadaY - 10);
            } else {
                g.drawString((i * 100) + "Hz", (inicioCascadaX - 20) + (limX * longLF) - 20, inicioCascadaY - 10);
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
        for (int i = 0; i < longLF-1; i++) {
            g.drawLine(xi, 95 - (((topLine[i]) - 55) * 90 / 60), xi + limX, 95 - (((topLine[i + 1]) - 55) * 90 / 60));
            xi += limX;
            topLine[i] -= 180;
            if (i < 60) {
                info += topLine[i] + ",";
            } else {
                info += topLine[i] + ";";
                //System.out.println("guardare en lofarData.txt: " + info);
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
}*/
