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
    int inc = 255 / 11;
    String infor;
    int ml[];
    //Graphics g;
    int gn = 0;
    despliegue d = new despliegue();
    String hw="ESPERANDO... un hola";

    public static void main(String[] args) {
        JFrame window = new JFrame("LOFAR by SIVISO");
        Lofar lf = new Lofar();
        window.add(lf);
        window.pack();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        comInterfaz c = new comInterfaz() {};
        //c.run();
        c.run(window);
    }
    //private String hw;

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 500);
    }

    @Override
    protected void paintComponent(Graphics g) {
        gn++;
        System.out.println("paint component ciclo numero: " + gn);
        System.out.println(hw);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getSize().width, getSize().height);

        sizeCanalX = getSize().width / 101;
        sizeCanalY = ((getSize().height)-100) / 101;
        //despliegue d = new despliegue();
        d.run(g, sizeCanalX, sizeCanalY);

    }
    
    public void sethw(String hw){
        this.hw = hw;
    }
    
    public String gethw(){
        return hw;
    }
}
