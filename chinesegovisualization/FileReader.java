/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesegovisualization;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author G-sta
 */
public class FileReader {
    
    private Game game;
    
    private File file;
    
    private Scanner x;
    
    public FileReader(String name, Game game) {
        this.game = game;
        
        file = new File(name);
        
        try{
            x = new Scanner(file);
            
            while(x.hasNext()){
                String s = x.next();
                String e = x.next();
                
                game.move(s, e);
            }
            game.finish();
            System.out.println("Finished loading game");
        }catch(FileNotFoundException e){
            System.out.println(e.getStackTrace());
            System.out.println("File not found");
        }
    }
    
}
