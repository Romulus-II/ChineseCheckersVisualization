/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesecheckersvisalization;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;

/**
 *
 * @author G-sta
 */
public class FileReader {
    
    private Game game;
    
    private File file;
    
    private Scanner x;
    
    public FileReader(String name, Game game) throws IOException {
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
            
            ArrayList<Image> turns = game.getAllTurns();
            for(int i = 0; i < turns.size(); i++){
                String file_name = "turns/Turn " + i + ".png";
                File output = new File(file_name);
                BufferedImage bimg = SwingFXUtils.fromFXImage(turns.get(i), null);
                ImageIO.write(bimg, "png", output);
            }
            System.out.println("Finished saving list of moves");
        }catch(FileNotFoundException e){
            System.out.println(e.getStackTrace());
            System.out.println("File not found");
        }
    }
    
}
