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
import java.text.SimpleDateFormat;
import java.util.Calendar;
//import java.net.SocketException;
//import java.net.UnknownHostException;
import javax.swing.*;

/**
 *
 * @author juan
 */
class comInterfaz extends Thread {

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
    //int[] n = new int[62];
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
            //address = InetAddress.getByName("192.168.1.178");
            //address = InetAddress.getByName("127.0.0.1");
            address = InetAddress.getByName("localhost");
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
            /*for (int i = 0; i < 62; i++) {
                n[i] = 0;
            }*/
            archivo a = new archivo();

            int i;
            do {
                RecogerServidor_bytes = new byte[256];
                servPaquete = new DatagramPacket(RecogerServidor_bytes, 256);
                socket.receive(servPaquete);
                cadenaMensaje = new String(RecogerServidor_bytes).trim();   //Convertimos el mensaje recibido en un string
                //System.out.println(cadenaMensaje);                          //Imprimimos el paquete recibido
                if ("LF_OFF".equals(cadenaMensaje)) {
                    window.setExtendedState(JFrame.ICONIFIED);
                    System.out.println("LOFAR esta deshabilitado");
                    if (cspps.getHabilitado()) {
                        cspps.setHabilitado(false);
                    }
                } else if ("LF_ON".equals(cadenaMensaje)) {
                    window.setExtendedState(JFrame.NORMAL);
                    System.out.println("LOFAR esta habilitado");
                    if (!cspps.getHabilitado()) {
                        cspps.setHabilitado(true);
                    }
                } else if ("LF_EXIT".equals(cadenaMensaje)) {
                    System.exit(0);
                } else if ("LF_SAVE".equals(cadenaMensaje)) {
                    a.save("resource/lofarDataRcv.txt");
                } else if (!("START OK!".equals(cadenaMensaje))) {
                    i = 0;
                    char[] charArray = cadenaMensaje.toCharArray();

                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    texto = sdf.format(cal.getTime()) + ",";
                    //texto = "";
                    //if (!(charArray[1] == 's')) {
                    /*for (char temp : charArray) {
                            if (i < 11 && ((int) temp > 0) && ((int) temp < 255)) {
                                texto += Integer.toString((int) temp);
                                if (i == 10) {
                                    texto += ";";
                                } else {
                                    texto += ",";
                                }
                            } else {

                            }
                            System.out.println("Error #??: el valor a guardar esta fuera de rango");
                            i++;
                        }*/
                    //} else {
                    for (char temp : charArray) {
                        texto += temp;
                    }
                    //}
                    a.escribirTxt("resource/lofarDataRcv.txt", texto);
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
}
