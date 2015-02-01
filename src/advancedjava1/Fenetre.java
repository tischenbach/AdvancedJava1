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
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

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
    
    public void actionPerformed(ActionEvent arg0) 
    {
    
        if (jtf.getText()!=null)
        {
            // Déclairation des ressources
            BufferedReader in = null;
            Process traceRt = null;
            String line = "";
            String[]buffer1, buffer2, buffer3, buffer4;
            int compteurStringVide=0;

            StringBuffer output = new StringBuffer();
            Parse parseClass = new Parse();

            try 
            {
                // Exécution de la commande tracert
                traceRt = Runtime.getRuntime().exec("tracert " + jtf.getText());
                in = new BufferedReader(new InputStreamReader(traceRt.getInputStream()));

                while ((line = in.readLine())!= null)
                {
                output.append(line + "\n");
                }

                //System.out.print(output.toString());
                //System.out.println("Test1");

                //Récupération d'un tableau de chaine de caractères contenant ligne par ligne le résultat du tracert
                buffer1 = parseClass.Convert(output.toString());
                // buffer = parseClass.Convert("Bonjour\nDebile\nBoum\nSalut");

                // Affichage du tableau
                /*for (int i = 0; i<buffer1.length; i++){

                    System.out.println(buffer1[i]);
                }*/

                //System.out.println("Test2"); 

                // Extraction des adresse IP dans un tableau
                buffer2 = parseClass.extractIP(buffer1);

                 // Affichage du nouveau tableau contenant les adresses IP (non retournées dans le bon sens)
                /*for (int i =0; i<buffer2.length; i++)
                {
                    if (buffer2[i]!=null)
                    {
                        System.out.println(buffer2[i]);
                    }
                }*/

                // Allocation d'un tableau de même taille que le tableau contenant les adresses IP
                buffer3 = new String[buffer2.length];

                // Initialisation du tableau qui contiendra les adresses IP
                for (int i = 0; i<buffer3.length; i++)
                {
                   buffer3[i]="";
                }

                // On effectue une rotation des chaines de caractères contenant les adresses IP
                for (int i =0; i<buffer2.length; i++)
                {
                    if (buffer2[i].compareTo("")!= 0) 
                    {  
                        for (int j=buffer2[i].length()-1; j>=0; j--)
                        {
                            buffer3[i]+=buffer2[i].charAt(j);
                        } 
                    }                  
                    else
                    {
                        buffer3[i]="";
                    }
                }
                
        
                // Affichage du nouveau tableau contenant les adresses IP (retournées)
                for (int i =0; i<buffer3.length; i++)
                {   
                    if (buffer3[i].compareTo("")!= 0)
                    {
                        System.out.println(buffer3[i]);
                    }
                    else
                    {
                        compteurStringVide++;
                    }                                
                }
                
                //System.out.println("Test3");
                
                // Allocation du tableau contenant les adresses IP sans chaine vide
                buffer4= new String[buffer3.length-compteurStringVide];
                
                // Remplissage du tableau d'IP sans chaines vides
                int j=0;
                for (int i =0; i<buffer3.length; i++)
                {
                    if (buffer3[i].compareTo("") != 0)
                    {
                        buffer4[j] = buffer3[i];
                        j++;
                    }
                }
                
                // Construction du graphe avec le tableau d'IP
                Graphe graph = new Graphe(buffer4);

            }

            catch (IOException ex) 
            {
                Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    
    }
    
}
