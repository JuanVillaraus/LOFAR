/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lofar;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import javax.swing.*;

/**
 *
 * @author juan
 */
class comInterfaz extends Thread {

    DatagramSocket socket;
    InetAddress address;
    byte[] mensaje_bytes = new byte[256];
    String modelo = "";
    String mensaje = "";
    DatagramPacket paquete;
    int puerto = 0;
    String cadenaMensaje = "";
    DatagramPacket servPaquete;
    byte[] RecogerServidor_bytes = new byte[256];
    String texto = "";
    Properties prop = new Properties();
    InputStream input = null;
    despliegue desp;

    //@Override
    public void run() {
        JFrame window = new JFrame("LOFAR by SIVISO");
        try {
            comSend cspps = new comSend();
            comSPV cspv = new comSPV();
            cspv.setWindow(window);
            try {
                input = new FileInputStream("config.properties");
                prop.load(input);
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
            if ("SSPP".equals(modelo)) {
                puerto = 5001;
                cspps.setPuerto(puerto);
                cspps.start();
                desp = new despliegue(window);
            } else if ("SSF".equals(modelo)) {
                puerto = 5002;
                cspps.setPuerto(puerto);
                cspps.start();
                desp = new despliegue(window);
            } else if ("SSPV".equals(modelo)) {
                puerto = 5003;
                cspv.start();
            }
            address = InetAddress.getByName("localhost");
            mensaje = "runLF";
            mensaje_bytes = mensaje.getBytes();
            paquete = new DatagramPacket(mensaje_bytes, mensaje.length(), address, puerto);
            socket = new DatagramSocket();
            socket.send(paquete);
            System.out.println("enviamos " + mensaje + " para inicializar la comunicación con el software");

            do {
                RecogerServidor_bytes = new byte[256];
                servPaquete = new DatagramPacket(RecogerServidor_bytes, 256);
                socket.receive(servPaquete);
                cadenaMensaje = new String(RecogerServidor_bytes).trim();   //Convertimos el mensaje recibido en un string
                System.out.println("Recibí: " + cadenaMensaje);
                texto = "";
                if ("OFF".equals(cadenaMensaje)) {
                    window.setExtendedState(JFrame.ICONIFIED);
                    cspps.setHabilitado(false);
                    cspv.setHabilitado(false);
                } else if ("ON".equals(cadenaMensaje)) {
                    window.setExtendedState(JFrame.NORMAL);
                    cspps.setHabilitado(true);
                    cspv.setHabilitado(true);
                } else if ("EXIT".equals(cadenaMensaje)) {
                    System.exit(0);
                    /*} else if ("SAVE".equals(cadenaMensaje)) {
                    if ("SSPV".equals(modelo)) {
                        a.save("resource/btrData.txt", cspv.getSave());
                    }*/
                } else if ("RP".equals(cadenaMensaje)) {                    //BTR repaint
                    window.repaint();
                    /*} else if ("M_ON".equals(cadenaMensaje)) {                   
                    cspv.setMarcacion(true);
                } else if ("M_OFF".equals(cadenaMensaje)) {                   
                    cspv.setMarcacion(false);*/
                } else if ("LONG".equals(cadenaMensaje)) {
                    try {
                        input = new FileInputStream("config.properties");
                        prop.load(input);
                        mensaje = "LONG" + prop.getProperty("longLF") + ";";
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
                    mensaje_bytes = mensaje.getBytes();
                    paquete = new DatagramPacket(mensaje_bytes, mensaje.length(), address, 5002);
                    socket.send(paquete);
                } else if (!("START OK!".equals(cadenaMensaje))) {
                    char[] charArray = cadenaMensaje.toCharArray();
                    for (char temp : charArray) {
                        texto += temp;
                    }
                    if ("SSPP".equals(modelo) || "SSF".equals(modelo)) {
                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        desp.setInfo(texto, sdf.format(cal.getTime()));
                    }
                }
            } while (true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}

/*
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import javax.swing.*;

/**
 *
 * @author juan
 
class comInterfaz extends Thread {

    DatagramSocket socket;
    InetAddress address;
    byte[] mensaje_bytes = new byte[256];
    String mensaje = "";
    DatagramPacket paquete;
    int puerto = 0;
    String cadenaMensaje = "";
    DatagramPacket servPaquete;
    byte[] RecogerServidor_bytes = new byte[256];
    String texto = "";
    Properties prop = new Properties();
    InputStream input = null;
    comSend cspps = new comSend();
    comSPV cspv = new comSPV();
    despliegue desp;
    String modelo;

    //@Override
    public void run(JFrame window) {
        try {
            //cspv.setHabilitado(true);
            //cspv.setWindow(window);
            try {
                input = new FileInputStream("config.properties");
                prop.load(input);
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
            //if ("SSPP".equals(mensaje)) {
                puerto = 5001;
                cspps.setPuerto(puerto);
                cspps.start();
            /*} else if ("SSF".equals(mensaje)) {
                puerto = 5002;
                cspps.setPuerto(puerto);
                cspps.start();
            } else if ("SSPV".equals(mensaje)) {
                puerto = 5003;
            }
            address = InetAddress.getByName("localhost");
            mensaje = "runLF";
            mensaje_bytes = mensaje.getBytes();
            paquete = new DatagramPacket(mensaje_bytes, mensaje.length(), address, puerto);
            socket = new DatagramSocket();
            socket.send(paquete);
            System.out.println("enviamos " + mensaje + " para inicializar la comunicación con el software");
            archivo a = new archivo();
            String hora;

            do {
                RecogerServidor_bytes = new byte[256];
                servPaquete = new DatagramPacket(RecogerServidor_bytes, 256);
                socket.receive(servPaquete);
                cadenaMensaje = new String(RecogerServidor_bytes).trim();   //Convertimos el mensaje recibido en un string
                texto = "";
                if ("OFF".equals(cadenaMensaje)) {
                    window.setExtendedState(JFrame.ICONIFIED);
                    if (cspps.getHabilitado()) {
                        cspps.setHabilitado(false);
                    }
                    if (cspv.getHabilitado()) {
                        cspv.setHabilitado(false);
                    }
                } else if ("ON".equals(cadenaMensaje)) {
                    window.setExtendedState(JFrame.NORMAL);
                    if (!cspps.getHabilitado()) {
                        cspps.setHabilitado(true);
                    }
                    if (!cspv.getHabilitado()) {
                        cspv.setHabilitado(true);
                    }
                } else if ("EXIT".equals(cadenaMensaje)) {
                    System.exit(0);
                } else if ("SAVE".equals(cadenaMensaje)) {
                    //a.save("resource/lofarData.txt.txt");
                } else if ("RP".equals(cadenaMensaje)) {                    //BTR repaint
                    window.repaint();
                } else if ("LONG".equals(cadenaMensaje)) {
                    try {
                        input = new FileInputStream("config.properties");
                        prop.load(input);
                        mensaje = "#" + prop.getProperty("longLF");
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
                    mensaje_bytes = mensaje.getBytes();
                    paquete = new DatagramPacket(mensaje_bytes, mensaje.length(), address, 5002);
                    socket.send(paquete);
                    if (puerto == 5003){
                        cspps.setPuerto(puerto);
                        cspv.start();
                    }
                    } else if (!("START OK!".equals(cadenaMensaje))) {
                    char[] charArray = cadenaMensaje.toCharArray();
                    for (char temp : charArray) {
                        texto += temp;
                    }
                    if ("SSF".equals(modelo)) {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    hora = sdf.format(cal.getTime());
                    //a.escribirTxtLine("resource/btrData.txt", texto);
                    //window.repaint();
                    desp.setInfo(texto, hora);
                    }
                }
                /*} else if (!("START OK!".equals(cadenaMensaje))) {
                    char[] charArray = cadenaMensaje.toCharArray();
                    for (char temp : charArray) {
                        texto += temp;
                    }
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    texto = sdf.format(cal.getTime()) + "," + texto;
                    a.escribirTxtLine("resource/lofarDataRcv.txt", texto);

                    window.repaint();
                }
            } while (true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}*/
