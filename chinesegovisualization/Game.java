/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesegovisualization;

import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author G-sta
 */
public class Game {
    
    private ArrayList<Image> turn = new ArrayList<>();
    
    
    private Canvas canvas;
    private GraphicsContext ctx;
    
    private Board board;
    private Space[][] space;
     
    private final int NUM_PLAYERS;
    private int player = 1;
    private int turnNumber = 0;
    
    private Circle placeHolder;
    
    public Game(Board board){
        this.board = board;
        this.space = board.getBoard();
        this.canvas = board.getCanvas();
        ctx = canvas.getGraphicsContext2D();
        NUM_PLAYERS = board.getNumPlayers();
        turn.add(board.screenshot());
        placeHolder = new Circle(0, 0, board.PIECE_WIDTH);
        placeHolder.setFill(Color.WHITE);
        board.pane.getChildren().add(placeHolder);
    }
    
    public int getTurnNumber(){return turnNumber;}
    
    public void finish(){
        board.pane.getChildren().remove(placeHolder);
        endTurn();
        turnNumber = 0;
        showTurn(turnNumber);
    }
    
    private void endTurn(){
        if(player<NUM_PLAYERS){
            player++;
        }else{
            player = 1;
        }
        turn.add(board.screenshot());
        turnNumber++;
    }
    public void playTurn(){
        
        endTurn();
    }
    
    /**
     * Positions are given as coordinates with a letter and number: (a15, b17)
     * 
     * 
     * @param startPos
     * @param endPos 
     */
    public void move(String startPos, String endPos){
        String startY = startPos.substring(0,1);
        startY = startY.toLowerCase();
        int sy = getY(startY);
        int sx = Integer.parseInt(startPos.substring(1)) - 1;
        
        String endY = endPos.substring(0,1);
        endY = endY.toLowerCase();
        int ey = getY(endY);
        int ex = Integer.parseInt(endPos.substring(1)) - 1;
        
        if(space[sy][sx].onBoard){
            if(space[sy][sx].occupied){
                if(space[ey][ex].onBoard){
                    if(space[ey][ex].occupied){
                        System.out.println("Piece already on ending space");
                    }else{
                        placeHolder.setStroke(space[sy][sx].getPiece().getColor());
                        placeHolder.setCenterX(space[sy][sx].cenx);
                        placeHolder.setCenterY(space[sy][sx].ceny);
                        System.out.println("Successfully moved piece from " +
                                startPos + " to " + endPos);
                        space[sy][sx].getPiece().highlight();
                        space[sy][sx].getPiece().move(space[sy][sx], space[ey][ex]);
                        endTurn();
                        space[ey][ex].getPiece().unhighlight();
                    }
                }else{
                    System.out.println("Ending space not on board");
                }
            }else{
                System.out.println("No piece found");
            }
        }else{
            System.out.println("Starting space not on board");
        }
    }
    
    private int getY(String s){
        switch(s){
            case "a":
                return 0;
            case "b":
                return 1;
            case "c":
                return 2;
            case "d":
                return 3;
            case "e":
                return 4;
            case "f":
                return 5;
            case "g":
                return 6;
            case "h":
                return 7;
            case "i":
                return 8;
            case "j":
                return 9;
            case "k":
                return 10;
            case "l":
                return 11;
            case "m":
                return 12;
            case "n":
                return 13;
            case "o":
                return 14;
            case "p":
                return 15;
            case "q":
                return 16;
            default:
                return 0;
        }
    }
    
    
    public void showTurn(int i){
        System.out.println("Showing turn " + i);
        ImageView img = new ImageView(turn.get(i));
        board.pane.getChildren().add(img);
        ctx.drawImage(turn.get(i), 0, 0, canvas.getWidth(), canvas.getHeight());
    }
    
    public void showPastTurn(){
        if(turnNumber>0){
            turnNumber--;
            showTurn(turnNumber);
            
        }
    }
    
    public void showNextTurn(){
        if(turnNumber<turn.size()-1){
            turnNumber++;
            showTurn(turnNumber);
           
        }
    }
    
}
