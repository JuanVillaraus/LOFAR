/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lofar;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author juan
 */
class comInterfaz extends Thread {

    DatagramSocket socket;
    InetAddress address;
    byte[] mensaje_bytes = new byte[2048];
    String modelo = "";
    String mensaje = "";
    DatagramPacket paquete;
    DatagramPacket paqueteSend;
    int puerto = 0;
    String cadenaMensaje = "";
    DatagramPacket servPaquete;
    byte[] RecogerServidor_bytes = new byte[2048];
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
            if (null != modelo) {
                switch (modelo) {
                    case "SSPP":
                        puerto = 5001;
                        cspps.setPuerto(puerto);
                        cspps.start();
                        desp = new despliegue(window);
                        break;
                    case "SSF":
                        puerto = 5002;
                        cspps.setPuerto(puerto);
                        cspps.start();
                        desp = new despliegue(window);
                        break;
                    case "SSPV":
                        puerto = 5003;
                        break;
                    default:
                        break;
                }
            }
            address = InetAddress.getByName("localhost");
            socket = new DatagramSocket();
            mensaje = "LOFAR";
            mensaje_bytes = mensaje.getBytes();
            paqueteSend = new DatagramPacket(mensaje_bytes, mensaje.length(), address, puerto);
            //socket.send(paqueteSend);
            mensaje = "runLF";
            mensaje_bytes = mensaje.getBytes();
            paquete = new DatagramPacket(mensaje_bytes, mensaje.length(), address, puerto);
            socket.send(paquete);
            System.out.println("LOFAR/comInterfaz - enviamos " + mensaje + " para inicializar la comunicación con el software");

            do {
                RecogerServidor_bytes = new byte[2048];
                servPaquete = new DatagramPacket(RecogerServidor_bytes, 2048);
                socket.receive(servPaquete);
                cadenaMensaje = new String(RecogerServidor_bytes).trim();   //Convertimos el mensaje recibido en un string
                System.out.println("LOFAR/comInterfaz - Recibí: " + cadenaMensaje);
                texto = "";
                if (null != cadenaMensaje) {
                    switch (cadenaMensaje) {
                        case "RUN":
                            cspv.start();
                            try {
                                sleep(300);
                            } catch (Exception e) {
                                Thread.currentThread().interrupt();
                                System.err.println("LOFAR/comInterfaz - Error en el sleep del start en comInterfaz" + e.getMessage());
                            }
                        case "OFF":
                            window.setExtendedState(JFrame.ICONIFIED);
                            cspps.setHabilitado(false);
                            cspv.setHabilitado(false);
                            break;
                        case "ON":
                            window.setExtendedState(JFrame.NORMAL);
                            cspps.setHabilitado(true);
                            cspv.setHabilitado(true);
                            break;
                        case "EXIT":
                            System.exit(0);
                            break;
                        case "SAVE":
                            /*if ("SSPV".equals(modelo)) {
                                a.save("resource/btrData.txt", cspv.getSave());
                            }*/
                            desp.save();
                            break;
                        case "RP":
                            window.repaint();
                            break;
                        case "LONG":
                            try {
                                input = new FileInputStream("config.properties");
                                prop.load(input);
                                mensaje = "LONG" + prop.getProperty("longLF") + ";";
                            } catch (IOException e) {
                                System.err.println("LOFAR/comInterfaz - Error al leer el longLF del archivo config " + e.getMessage());
                            } finally {
                                if (input != null) {
                                    try {
                                        input.close();
                                    } catch (IOException e) {
                                        System.err.println("LOFAR/comInterfaz - Error al cerrar el input" + e.getMessage());
                                    }
                                }
                            }
                            mensaje_bytes = mensaje.getBytes();
                            paquete = new DatagramPacket(mensaje_bytes, mensaje.length(), address, 5002);
                            socket.send(paquete);
                            break;
                        default:
                            char[] charArray = cadenaMensaje.toCharArray();
                            for (char temp : charArray) {
                                texto += temp;
                            }
                            if ("SSPP".equals(modelo) || "SSF".equals(modelo)) {
                                Calendar cal = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                                desp.setInfo(texto, sdf.format(cal.getTime()));
                                cspps.setEstadoEnvio(false);
                                socket.send(paqueteSend);
                            }
                            break;
                    }
                }
            } while (true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
