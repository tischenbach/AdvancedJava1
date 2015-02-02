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
import java.util.Random;
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
    private JButton generate = new JButton("Générer une adresse IP");
    private Graphe graph = new Graphe();
    
    public Fenetre(){
        
    this.setTitle("Traceroute");    
    this.setSize(600, 600);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);              
    
    JPanel panel = new JPanel();
    panel.add(ipAdress);
    panel.add(jtf);
    panel.add(traceroute);
    panel.add(generate);
    pan.add(panel,BorderLayout.NORTH);
    
    traceroute.addActionListener(this);
    generate.addActionListener(this);
    this.setContentPane(pan);
    this.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent arg0) 
    {
        if (arg0.getSource() == traceroute)
        {
            System.out.println("coucouc");
            if (jtf.getText()!=null && jtf.getText().contains(" ")!=true)
            {
                // Déclaration des ressources
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

                    //Récupération d'un tableau de chaine de caractères contenant ligne par ligne le résultat du tracert
                    buffer1 = parseClass.Convert(output.toString());

                    // Extraction des adresse IP dans un tableau
                    buffer2 = parseClass.extractIP(buffer1);

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
                                      
                    for (int i=0; i<buffer4.length; i++)
                    {
                        if (graph.graphe.getNode(buffer4[i])== null)
                        {    
                            if(i==0)
                            {
                                graph.graphe.addNode(buffer4[i]).addAttribute("ui.label", "Adresse de départ : "+buffer4[i]);
                            }
                            else if(i==buffer4.length-1)
                            {
                                graph.graphe.addNode(buffer4[i]).addAttribute("ui.label", "Adresse d'arrivée :"+buffer4[i]);
                            }
                            else
                            {
                                graph.graphe.addNode(buffer4[i]).addAttribute("ui.label",buffer4[i]);
                            }
                        }
                    }

                    for (int i=1; i<buffer4.length; i++)
                    {   
                        if (graph.graphe.getEdge(buffer4[i-1]+buffer4[i])== null && graph.graphe.getEdge(buffer4[i]+buffer4[i-1])== null)
                        graph.graphe.addEdge(buffer4[i-1]+buffer4[i], buffer4[i-1],buffer4[i]);
                    }
        
     
                }

                catch (IOException ex) 
                {
                    Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            
        }
        if (arg0.getSource() == generate)
            {  
                System.out.println("coucouc");
                // Déclaration des ressources
                Random r = new Random();
                String IpAddress;
                IpAddress = r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256); 
                jtf.setText(IpAddress);
                
            }
    }
    
}
