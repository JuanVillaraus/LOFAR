/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lofar;

//import java.io.BufferedReader;
//import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
//import java.net.SocketException;
//import java.net.UnknownHostException;
import javax.swing.*;

/**
 *
 * @author juan
 */
class comInterfaz extends Lofar implements Runnable {

    //Definimos el sockets, número de bytes del buffer, y mensaje.
    DatagramSocket socket;
    InetAddress address;
    byte[] mensaje_bytes = new byte[256];
    String mensaje = "";
    //Paquete
    DatagramPacket paquete;
    String cadenaMensaje = "";
    DatagramPacket servPaquete;
    byte[] RecogerServidor_bytes = new byte[256];
    //String str;
    //despliegue d = new despliegue();
    int[] n = new int[101];
    String texto = "";
    String hw = "...esperando un hola";

    public void comInterfaz() { // throws SocketException, UnknownHostException

        System.out.println("inicia public void comunicacion");
    }

    //@Override
    //public void run(JFrame window, despliegue d) {
    public void run(JFrame window) {
        try {
            mensaje_bytes = mensaje.getBytes();
            address = InetAddress.getByName("192.168.1.178");
            mensaje = "runLF";
            mensaje_bytes = mensaje.getBytes();
            paquete = new DatagramPacket(mensaje_bytes, mensaje.length(), address, 5002);
            socket = new DatagramSocket();
            socket.send(paquete);
            System.out.println("enviamos runLOFAR");
            //RecogerServidor_bytes = new byte[256];
            comSPPsend cspps = new comSPPsend();
            cspps.start();
            //comSSPreceive csppr = new comSSPreceive();
            //csppr.start();
            for (int i = 0; i < 101; i++) {
                n[i] = 0;
            }
            archivo a = new archivo();

            int i;
            do {
                RecogerServidor_bytes = new byte[256];
                servPaquete = new DatagramPacket(RecogerServidor_bytes, 256);
                socket.receive(servPaquete);
                cadenaMensaje = new String(RecogerServidor_bytes).trim();   //Convertimos el mensaje recibido en un string
                //System.out.println(cadenaMensaje);                          //Imprimimos el paquete recibido
                if ("LF_OFF".equals(cadenaMensaje)) {
                    window.setVisible(false);
                    //super.hw="HOLA MUNDO lo he logrado ";
                    //super.d.sethw("HOLA MUNDO!!! LO LOGRE");
                    System.out.println("LOFAR esta deshabilitado");
                    if (cspps.getHabilitado()) {
                        cspps.setHabilitado(false);
                    }
                } else if ("LF_ON".equals(cadenaMensaje)) {
                    window.setVisible(true);
                    System.out.println("LOFAR esta habilitado");
                    if (!cspps.getHabilitado()) {
                        cspps.setHabilitado(true);
                    }
                } else if (!("START OK!".equals(cadenaMensaje))) {
                    i = 0;
                    char[] charArray = cadenaMensaje.toCharArray();
                    texto = "";
                    for (char temp : charArray) {
                        if (i < 101 && ((int) temp > 0) && ((int) temp < 255)) {
                            texto += Integer.toString((int) temp);
                            if (i == 100) {
                                texto += ";";
                            } else {
                                texto += ",";
                            }
                        }
                        i++;
                    }
                    a.escribirTxt("resource/lofarData.txt", texto);
                    //System.out.println("intento mandar un hola...");
                    //super.hw="HOLA MUNDO + "+texto;
                    window.repaint();
                } else if (!("EXIT!".equals(cadenaMensaje))) {
                    System.exit(0);
                }
            } while (true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public void sethw(String hw) {
        this.hw = hw;
    }

    public String gethw() {
        return hw;
    }

    @Override
    public void run() {                                 //trato de usar el run predefinido del thread, pero aun marca un error, la vintana no se hace visible e invisible 
        /*try {
            mensaje_bytes = mensaje.getBytes();
            address = InetAddress.getByName("192.168.1.178");
            mensaje = "runLF";
            mensaje_bytes = mensaje.getBytes();
            paquete = new DatagramPacket(mensaje_bytes, mensaje.length(), address, 5002);
            socket = new DatagramSocket();
            socket.send(paquete);
            System.out.println("enviamos runLOFAR");
            //RecogerServidor_bytes = new byte[256];
            comSPPsend cspps = new comSPPsend();
            cspps.start();
            //comSSPreceive csppr = new comSSPreceive();
            //csppr.start();
            for (int i = 0; i < 101; i++) {
                n[i] = 0;
            }
            archivo a = new archivo();

            int i;
            do {
                RecogerServidor_bytes = new byte[256];
                servPaquete = new DatagramPacket(RecogerServidor_bytes, 256);
                socket.receive(servPaquete);
                cadenaMensaje = new String(RecogerServidor_bytes).trim();   //Convertimos el mensaje recibido en un string
                //System.out.println(cadenaMensaje);                          //Imprimimos el paquete recibido
                if ("LF_OFF".equals(cadenaMensaje)) {
                     super.setVisible(false);
                     super.repaint();
                     super.hw="HOLA MUNDO lo he logrado ";
                     super.d.sethw("HOLA MUNDO!!! LO LOGRE");
                    System.out.println("LOFAR esta deshabilitado");
                    if (cspps.getHabilitado()) {
                        cspps.setHabilitado(false);
                    }
                } else if ("LF_ON".equals(cadenaMensaje)) {
                      super.setVisible(true);
                      super.repaint();
                    System.out.println("LOFAR esta habilitado");
                    if (!cspps.getHabilitado()) {
                        cspps.setHabilitado(true);
                    }
                } else if (!("START OK!".equals(cadenaMensaje))) {
                    i = 0;
                    char[] charArray = cadenaMensaje.toCharArray();
                        texto = "";
                    for (char temp : charArray) {
                        if (i < 101 && ((int) temp > 0) && ((int) temp < 255)) {
                            texto += Integer.toString((int)temp);
                            if(i==100)
                                texto += ";";
                            else
                                texto += ",";
                        }
                        i++;
                    }
                    //a.escribirTxt("resource/dataEj1.txt", texto);
                    //System.out.println("intento mandar un hola...");
                    super.hw="HOLA MUNDO + "+texto;
                    super.repaint();
                }
            } while (true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }*/
    }
}
