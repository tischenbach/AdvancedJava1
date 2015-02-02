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
    private JTextField jtf = new JTextField("");
    private JButton traceroute = new JButton("Traceroute");
    private JButton generate = new JButton("Générer une adresse IP");
    private JOptionPane jop1 = new JOptionPane();
    private JOptionPane jop2 = new JOptionPane();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu test1 = new JMenu("Options");
    private JMenu item1 = new JMenu("Nombre de sauts");
    private Graphe graph = new Graphe();
    private JMenuItem[] JMenuItemSauts = new JMenuItem[30];
    private String Commande="";
    
    public Fenetre(){
        
    this.setTitle("Traceroute");    
    this.setSize(800, 800);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
    this.jtf.setPreferredSize(new Dimension(150, 30));
    
    this.test1.add(this.item1);   
     
    for (int i = 0; i<30; i++)
    {
        JMenuItemSauts[i] = new JMenuItem(i+1 + " Saut(s)");
        this.item1.add(this.JMenuItemSauts[i]);
    }
    
    for (int i = 0; i<30; i++)
    {
        JMenuItemSauts[i].addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent arg0)
        {
            for (int j = 0; j<30; j++)
            {
                if(arg0.getSource() == JMenuItemSauts[j])
                {
                    j++;
                    Commande = "-h "+ j + " ";
                    jop2.showMessageDialog(null, "Vous avez choisi d'effectuer le traceroute avec " + j + " sauts !", "Nombre de sauts choisi", JOptionPane.INFORMATION_MESSAGE);
                    j = 30;
                }
            }
                
        }        
        });
    }
     
    this.menuBar.add(this.test1);
    this.setJMenuBar(menuBar);
    
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

            if (jtf.getText().isEmpty()!=true && jtf.getText().contains(" ")!=true)
            {
                // Déclaration des ressources
                BufferedReader in = null;
                Process traceRt = null;
                String line = "";
                String[]buffer1, buffer2, buffer3, buffer4 = null;
                int compteurStringVide=0;

                StringBuffer output = new StringBuffer();
                Parse parseClass = new Parse();
                
                    
                
                try 
                {
                    if (System.getProperty("os.name").indexOf("Windows") != -1){
                        // Exécution de la commande tracert
                        traceRt = Runtime.getRuntime().exec("tracert " + Commande + jtf.getText());
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
                    }
                    else{
                        traceRt = Runtime.getRuntime().exec("traceroute "+ jtf.getText());
                        in = new BufferedReader(new InputStreamReader(traceRt.getInputStream()));
                        buffer4=parseClass.unixParse(in);
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
            
            else if (jtf.getText().isEmpty()==true || jtf.getText().contains(" ") == true)
            {
                jop1.showMessageDialog(null, "L'adresse IP saisie n'est pas conforme\nEssayez une adresse IP au format suivant : 255.255.255.255", "Attention", JOptionPane.INFORMATION_MESSAGE);
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
