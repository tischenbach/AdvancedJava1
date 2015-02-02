/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedjava1;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nathanaël
 */
public class Parse {
    

public Parse(){

}

public String[] Convert(String ChaineAParser){
    
    int i = 0, j=0, k=0, l=0;
    String[] buffer;
    String bufferString;
    bufferString = "";
    
    //System.out.println("Test1.1");
    
    for (i=0; i<ChaineAParser.length(); i++)
    {
        while(ChaineAParser.length()!= i && ChaineAParser.charAt(i) != '\n')
        {
            i++;
        }
         j++;
    }
    
    //System.out.println("Test1.2");
    buffer = new String[j];
   
    for (i=0; i<ChaineAParser.length(); i++)
    { 
        while(ChaineAParser.length()!= i && ChaineAParser.charAt(i) != '\n')
        {
            bufferString+= ChaineAParser.charAt(i);
            l++;
            i++;
        }

        buffer[k] = bufferString;
        bufferString = "";
        k++;
    }
    
    //System.out.println("Test1.3");
    
    return buffer;
}

public String[] extractIP(String[] chaines){
    
    int j = 2, k=0;
    String[] buffer;
    buffer = new String[chaines.length-6];
    
    for (int i = 0; i<buffer.length; i++)
        buffer[i]="";
    
    for(int i = 3; i<chaines.length-2; i++)
    {   
        if (chaines[i].compareTo("")!=0)
        {
            if (chaines[i].charAt(chaines[i].length() - 1) == '.')
            {
                buffer[k] = "";
            }
            
           else
           {

                if(chaines[i].charAt(chaines[i].length() - 2) == ']')
                    {
                        j++;
                    }

                while(chaines[i].charAt(chaines[i].length() - j) != ' ' && chaines[i].charAt(chaines[i].length() - j) != '[')
                {
                    buffer[k]+= chaines[i].charAt(chaines[i].length() - j);
                    j++;
                }

            }
            
            k++;
            j=2;
            
        }
    }
    
    return buffer;
    
}

public String[] unixParse(BufferedReader buf)
        {
                String[] result = null;
                String address;
                String line = "";
                int i=1, j=0; 
                
                try {
                    while((line = buf.readLine()) != null)
                    {
                        if(i==0){
                            //hostname = line.split("\\s+")[1];
                            address = line.split("\\s+")[2].replaceAll("\\(|\\)", "");
                            result[j]=address;
                        }
                        i--;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Parse.class.getName()).log(Level.SEVERE, null, ex);
                }
                return result;
        }
    
}
