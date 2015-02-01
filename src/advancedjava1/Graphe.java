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
    
    public Graphe(String[] IPStrings) 
    {
        Graph graph = new SingleGraph("Traceroute");
        
        for (int i=0; i<IPStrings.length; i++)
        {
            if (graph.getNode(IPStrings[i])== null)
            graph.addNode(IPStrings[i]).addAttribute("ui.label", IPStrings[i]);;
        }
        
        for (int i=1; i<IPStrings.length; i++)
        {
            graph.addEdge("Bob"+i, IPStrings[i-1],IPStrings[i]);
        }
        
        graph.display();
    }
    
    
}