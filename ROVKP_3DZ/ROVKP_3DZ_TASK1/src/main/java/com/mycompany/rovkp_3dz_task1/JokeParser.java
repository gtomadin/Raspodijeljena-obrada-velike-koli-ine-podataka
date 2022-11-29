/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_3dz_task1;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.text.StringEscapeUtils;

/**
 *
 * @author gtoma
 */
public class JokeParser {
    
    Map<Integer, String> jokes;
    
    void parse(String file) {
        jokes = new HashMap<>();
        String[] jokeArray = file.split("\n\n");
        
        for(int i = 0; i<jokeArray.length; ++i){
            
            //System.out.println(jokeArray[i]);
            
            String[] chucks = jokeArray[i].split("\n");
            
            int ID = Integer.parseInt(chucks[0].replace(":", ""));
            String jokeText = "";
        
            for(int j = 1; j<chucks.length; ++j){
                jokeText += StringEscapeUtils.unescapeXml(chucks[j].toLowerCase().replaceAll("\\<.*?\\>", ""));
            }
        
            //System.out.println(jokeText);
        
            //System.out.println(ID);
            jokes.put(ID, jokeText);
            
            //break;
        }
    }

    Map<Integer, String> getJokes() {
       return jokes;
    }
}
