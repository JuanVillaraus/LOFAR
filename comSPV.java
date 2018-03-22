/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lofar;

import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JFrame;

/**
 *
 * @author siviso
 */
public class comSPV extends Thread {

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    Socket socket;
    byte[] mensaje_bytes = new byte[256];
    String mensaje = "";
    String msn;
    String texto;
    String word;
    int nDatos;
    boolean error;
    boolean habilitado = false;
    int t = 1000;
    JFrame window;
    despliegue desp;
    Properties prop = new Properties();
    InputStream input = null;
    String DIR = "";
    int PORT = 0;
    int[] sound = new int[262144];
    String audioTxt;

    public boolean getHabilitado() {
        return this.habilitado;
    }

    public void setHabilitado(boolean h) {
        this.habilitado = h;
    }

    public void setWindow(JFrame window) {
        this.window = window;
    }

    public void run() {
        try {
            try {
                input = new FileInputStream("config.properties");
                prop.load(input);
                DIR = prop.getProperty("dirSSPV");
                PORT = Integer.parseInt(prop.getProperty("portLF"));
                System.out.println("Lofar comSPV " + DIR + " " + PORT);
            } catch (IOException e) {
                System.err.println("Error al leer el archivo config: " + e.getMessage());
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        System.err.println("Error al cerrar el archivo config: " + e.getMessage());
                    }
                }
            }
            desp = new despliegue(window);
            socket = new Socket(DIR, PORT);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            BufferedReader inp = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            archivo a = new archivo();
            sleep(1000);
            String info;
            int limSound;
            audioTxt = "";

            while (true) {
                limSound = 0;
                if (getHabilitado()) {
                    mensaje = "selAudio " + a.leerTxt("resource/marcBTR.txt");
                    out.writeUTF(mensaje);
                    msn = inp.readLine();
                    //System.out.println(msn);
                    error = false;
                    texto = "";
                    word = "";
                    nDatos = 0;
                    mensaje = "activarAudio";
                    out.writeUTF(mensaje);
                    msn = inp.readLine();
                    //System.out.println(msn);
                    for (int i = 1; i <= 26; i++) {
                        mensaje = "Audio " + i;
                        //mensaje = "Audio 1";
                        out.writeUTF(mensaje);
                        msn = inp.readLine();
                        //System.out.println(msn);
                        if (!"null".equals(msn)) {
                            char[] charArray = msn.toCharArray();
                            info = "";
                            for (char temp : charArray) {
                                if (temp == '1' || temp == '2' || temp == '3' || temp == '4' || temp == '5' || temp == '6' || temp == '7' || temp == '8' || temp == '9' || temp == '0' || temp == ',' || temp == ';') {
                                    audioTxt += temp;
                                    if (!(temp == ',') && !(temp == ';')) {
                                        info += temp;

                                    } else {
                                        try {
                                            sound[limSound] = Integer.parseInt(info);
                                            //System.out.print(info + " ");

                                        } catch (Exception e) {
                                            System.err.println("Error: ParseInt: " + e.getMessage());
                                        }
                                        info = "";
                                        limSound++;
                                    }
                                }
                            }
                        }
                    }

                    /*mensaje = "DatosAUDIO\n";
                    out.writeUTF(mensaje);
                    System.out.println("Envie: " + mensaje);
                    msn = inp.readLine();
                    System.out.println("Recibí: " + msn);
                    if (!("Audio OK".equals(msn))) {
                        error = true;
                        System.out.println("Error: esperba <Audio OK> y recibí <" + msn + ">, Compruebe la comunicación");
                    } else {
                        mensaje = "DatosLOFAR\n";
                        out.writeUTF(mensaje);
                        System.out.println("Envie: " + mensaje);
                        msn = inp.readLine();
                        System.out.println("Recibí: " + msn);
                        if ("Beamforming OK".equals(msn)) {
                            error = true;
                            System.out.println("Error: esperba <Beamforming OK> y recibí <" + msn + ">, Compruebe la comunicación");
                        } else {
                            for (int n = 1; n <= 13; n++) {
                                if (!error) {
                                    mensaje = "LOFAR " + n + "\n";
                                    out.writeUTF(mensaje);
                                    System.out.println("Envie: " + mensaje);
                                    msn = inp.readLine();
                                    System.out.println("Recibí: " + msn);
                                    char[] charArray = msn.toCharArray();
                                    for (char temp : charArray) {
                                        if (temp == '1' || temp == '2' || temp == '3' || temp == '4' || temp == '5' || temp == '6' || temp == '7' || temp == '8' || temp == '9' || temp == '0') {
                                            word += temp;
                                        }
                                        if (temp == ',' || temp == ';') {
                                            nDatos++;
                                            if (word != null) {
                                                texto += word;
                                                if (n != 13 || temp == ',') {
                                                    texto += ",";
                                                }
                                                word = "";
                                            } else {
                                                error = true;
                                                System.out.println("Error: dato en la posicion " + nDatos + " del LOFAR" + n + " no fue encontrado");
                                            }
                                        }
                                        if (temp == ';' && n == 13) {
                                            texto += ";";
                                        }
                                    }
                                }
                            }
                        }
                    }*/
                    if (!error) {
                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        desp.setInfo(sound, sdf.format(cal.getTime()));
                    }
                } else {
                    try {
                        sleep(t);                                //espera un segundo
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                        System.err.println(e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

}
