/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lofar;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author juan
 */
public class comSend extends Thread {

    DatagramSocket socket;
    InetAddress address;
    byte[] mensaje_bytes = new byte[256];
    String mensaje = "";
    int puerto = 0;
    //Paquete
    DatagramPacket paquete;
    boolean habilitado = false;
    int t = 1000;
    boolean estadoEnvio = true;

    public comSend() {

    }

    public boolean getHabilitado() {
        return this.habilitado;
    }

    public boolean getEstadoEnvio() {
        return this.estadoEnvio;
    }

    public void setHabilitado(boolean h) {
        this.habilitado = h;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public void setEstadoEnvio(boolean estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }

    @Override
    public void run() {
        try {
            mensaje_bytes = mensaje.getBytes();
            address = InetAddress.getByName("localhost");
            mensaje = "LOFAR";
            mensaje_bytes = mensaje.getBytes();
            paquete = new DatagramPacket(mensaje_bytes, mensaje.length(), address, puerto);
            socket = new DatagramSocket();
            int n = 0;
            Properties prop = new Properties();
            InputStream input = null;
            try {
                input = new FileInputStream("config.properties");
                prop.load(input);
                t = Integer.parseInt(prop.getProperty("timeSend"));
                t *= 10;
            } catch (IOException e) {
                System.err.println(e.getMessage());
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
            while (true) {
                if (getHabilitado()) {
                    n++;
                    System.out.println(n);
                    if (getEstadoEnvio()) {
                        socket.send(paquete);
                    }
                    setEstadoEnvio(true);
                }
                try {
                    sleep(t);                                //espera un segundo
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

}
