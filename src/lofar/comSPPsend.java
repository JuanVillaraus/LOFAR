 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lofar;

//import java.io.BufferedReader;
//import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Properties;

/**
 *
 * @author juan
 */
public class comSPPsend extends Thread {

    //Definimos el sockets, número de bytes del buffer, y mensaje.
    DatagramSocket socket;
    InetAddress address;
    byte[] mensaje_bytes = new byte[256];
    String mensaje = "";
    //Paquete
    DatagramPacket paquete;
    //String cadenaMensaje = "";
    //DatagramPacket servPaquete;
    //byte[] RecogerServidor_bytes = new byte[256];
    boolean habilitado = false;
    int t = 1000;

    public comSPPsend() {

    }

    public boolean getHabilitado() {
        return this.habilitado;
    }

    public void setHabilitado(boolean h) {
        this.habilitado = h;
    }

    @Override
    public void run() {
        try {
            mensaje_bytes = mensaje.getBytes();
            //address = InetAddress.getByName("192.168.1.178");
            address = InetAddress.getByName("localhost");
            /*mensaje = "START COMMUNICATION";
            mensaje_bytes = mensaje.getBytes();
            paquete = new DatagramPacket(mensaje_bytes, mensaje.length(), address, 8888);
            socket = new DatagramSocket();
            socket.send(paquete);*/
            mensaje = "LOFAR";
            mensaje_bytes = mensaje.getBytes();
            paquete = new DatagramPacket(mensaje_bytes, mensaje.length(), address, 5002);
            socket = new DatagramSocket();
            int n = 0;
            Properties prop = new Properties();
            InputStream input = null;
            try {
                input = new FileInputStream("config.properties");
                prop.load(input);
                t = Integer.parseInt(prop.getProperty("timeSend"));
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
            while (true) {
                if (getHabilitado()) {
                    n++;
                    System.out.println(n);
                    socket.send(paquete);
                }
                try {
                    sleep(t);                                //espera un segundo
                } catch (Exception e) {                     
                    Thread.currentThread().interrupt();
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

}
