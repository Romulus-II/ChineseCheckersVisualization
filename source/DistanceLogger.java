/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesecheckersvisalization;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author G-sta
 */
public class DistanceLogger {
    
    private File logFile;
    private BufferedWriter logger;
    
    private Space[][] space;
    private Game game;
    
    public DistanceLogger(Game game){
        this.game = game;
        space = game.getSpaces();
        System.out.println("We have collected the spaces");
        System.out.print(space.length);
        System.out.println(" : " + space[0].length);
        try {
            logFile = new File("Log.txt");
            logger = new BufferedWriter(new FileWriter(logFile, true));
            
            logger.write(getDate().toString());
            logger.newLine();
            logger.newLine();
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            Logger.getLogger(DistanceLogger.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * Returns a date representation of today's date.
     * @return date
     */
    private Date getDate(){
        long millis=System.currentTimeMillis();
        Date date = new Date(millis);
        return date;
    }
    
    /**
     * For each space on the board, writes it's distance from the given coordinates
     * to the log file.
     * @param x
     * @param y
     * @throws IOException 
     */
    public void markDistances(int x, int y) throws IOException{
        System.out.println("Now calculating distances");
        for(int i = 0; i < space.length; i++){
            for(int j = 0; j < space[0].length; j++){
                System.out.println(i + " : " + j);
                if(space[i][j].onBoard){
                    String dist = "(";
                    if(space[i][j].x > 0 && space[j][j].x < 10){
                        dist = dist + " " + space[i][j].x;
                    }else{
                        dist = dist + space[i][j].x;
                    }
                    if(space[i][j].y < 10){
                        dist = dist + " " + space[i][j].y;
                    }else{
                        dist = dist + space[i][j].y;
                    }
                    dist = dist + ") : ";
                    dist += game.distance(space[i][j], 0, 0);
                    logger.newLine();
                    logger.write(dist);
                }
            }
        }
    }
    
    /**
     * Writes the final information and formats the log file for next use.
     * @throws IOException 
     */
    public void publish() throws IOException{
        logger.newLine();
        String divider = "";
        for(int i = 0; i < 150; i++){
            divider = divider + "_";
        }
        logger.write(divider);
        logger.newLine();
        logger.newLine();
        logger.flush();
        logger.close();
    }
    
}
