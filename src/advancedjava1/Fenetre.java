/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedjava1;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author Nathanaël
 */
public class Fenetre extends JFrame implements ActionListener {
    
    private JPanel pan = new JPanel();
    private JLabel ipAdress = new JLabel("Saisir une adresse IP");
    private JTextField jtf = new JTextField("Ex : 192.168.0.1");
    private JButton traceroute = new JButton("Traceroute");
    
    public Fenetre(){
        
    this.setTitle("Traceroute");    
    this.setSize(400, 400);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);              
    
    JPanel panel = new JPanel();
    panel.add(ipAdress);
    panel.add(jtf);
    panel.add(traceroute);
    pan.add(panel,BorderLayout.NORTH);
    
    traceroute.addActionListener(this);
    this.setContentPane(pan);
    this.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent arg0) {
    
    BufferedReader in = null;
    Process traceRt = null;
    String line = "";
    StringBuffer output = new StringBuffer();
        
    try 
    {
        traceRt = Runtime.getRuntime().exec("tracert google.fr");
        in = new BufferedReader(new InputStreamReader(traceRt.getInputStream()));

        while ((line = in.readLine())!= null)
        {
        output.append(line + "\n");
        }
        
        System.out.print(output.toString());

    }
    
    catch (IOException ex) 
    {
        Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
    }
   
    }
    
}
