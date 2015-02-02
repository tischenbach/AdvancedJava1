/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedjava1;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

/**
 *
 * @author Nathanaël
 */
public class Graphe {
    
    public Graph graphe;
    
    public Graphe() 
    {
        this.graphe = new SingleGraph("Traceroute");
        this.graphe.display();
    }
    
    
}