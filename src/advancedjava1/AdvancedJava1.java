/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedjava1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Romain
 */
public class AdvancedJava1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Process process =Runtime.getRuntime().exec("tracert yahoo.com");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            int data=0;
            while((data=reader.read())>0){
                System.out.print((char)data);
            }
        } catch (IOException ex) {
            Logger.getLogger(AdvancedJava1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
