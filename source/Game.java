/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesecheckersvisalization;

import java.util.ArrayList;
import javafx.event.EventType;
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
    
    private final Circle placeHolder;
    private boolean showingPlaceHolder = false;
    
    ArrayList<Circle> markers = new ArrayList<Circle>();
    
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
        showingPlaceHolder = true;
    }
    
    protected ArrayList<Image> getAllTurns(){return turn;}
    public int getTurnNumber(){return turnNumber;}
    public Space[][] getSpaces(){return space;}
    
    public void showPlaceHolder(boolean show){
        if(show){
            if(!showingPlaceHolder){
                board.pane.getChildren().add(placeHolder);
                showingPlaceHolder = true;
            }
        }else{
            if(showingPlaceHolder){
                board.pane.getChildren().remove(placeHolder);
                showingPlaceHolder = false;
            }
        }
    }
    
    public void finish(){
        //board.pane.getChildren().remove(placeHolder);
        //endTurn();
        turnNumber = 0;
        showTurn(turnNumber);
    }
    
    public void showMoves(Space s){
        for(int i = 0; i < markers.size(); i++){
            board.pane.getChildren().remove(markers.get(i));
        }
        ArrayList<Space> posMoves = getPossibleMoves(s);
        markers = new ArrayList<>();
        for(int i = 0; i < posMoves.size(); i++){
            Space temp = posMoves.get(i);
            Circle circle = new Circle(temp.cenx, temp.ceny, board.PIECE_WIDTH);
            circle.setStroke(Color.YELLOW);
            circle.setFill(Color.WHITE);
            
            markers.add(circle);
            
            board.pane.getChildren().add(markers.get(i));
        }
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
        //We messed with this
        int sx = Integer.parseInt(startPos.substring(1));
        
        String endY = endPos.substring(0,1);
        endY = endY.toLowerCase();
        int ey = getY(endY);
        //We messed with this
        int ex = Integer.parseInt(endPos.substring(1));
        System.out.print("Turn " + turnNumber + ": ");
        if(space[sy][sx].onBoard){
            if(space[sy][sx].occupied){
                if(space[ey][ex].onBoard){
                    if(space[ey][ex].occupied){
                        System.out.print(startPos + " to " + endPos + "Piece already on ending space");
                        System.out.print(" (" + space[sy][sx].toString() + "," + space[ey][ex].toString() + ")");
                    }else{
                        placeHolder.setStroke(space[sy][sx].getPiece().getColor());
                        placeHolder.setCenterX(space[sy][sx].cenx);
                        placeHolder.setCenterY(space[sy][sx].ceny);
                        
                        System.out.print("Successfully moved piece from " +
                                startPos + " to " + endPos);
                        
                        space[sy][sx].getPiece().highlight();
                        space[sy][sx].getPiece().move(space[sy][sx], space[ey][ex]);
                        endTurn();
                        space[ey][ex].getPiece().unhighlight();
                    }
                }else{
                    System.out.print(startPos + " to " + endPos + "Ending space not on board");
                    System.out.print(" (" + space[sy][sx].toString() + "," + space[ey][ex].toString() + ")");
                }
            }else{
                System.out.print(startPos + " to " + endPos + "No piece found");
                System.out.print(" (" + space[sy][sx].toString() + "," + space[ey][ex].toString() + ")");
            }
        }else{
            System.out.print(startPos + " to " + endPos + "Starting space not on board");
            System.out.print(" (" + space[sy][sx].toString() + "," + space[ey][ex].toString() + ")");
        }
        System.out.println();
    }
    
    public boolean isLegal(Space s) {
        if(s.onBoard){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isOccupied(Space s) {
        if(s.onBoard){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Returns a list of the possible moves one space away from the piece.
     * *Currently does not factor jumps.
     * @param s
     * @return 
     */
    public ArrayList<Space> getPossibleMoves(Space s) {
        ArrayList<Space> posMoves = new ArrayList<>();
        if(s.x > 0){
            if(isLegal(space[s.x-1][s.y]) && !isOccupied(space[s.x-1][s.y])){
                posMoves.add(space[s.x-1][s.y]);
            }
        }
        if(s.y > 0){
            if(isLegal(space[s.x-1][s.y-1]) && !isOccupied(space[s.x-1][s.y-1])){
                posMoves.add(space[s.x-1][s.y-1]);
            }
            if(isLegal(space[s.x+1][s.y-1]) && !isOccupied(space[s.x+1][s.y-1])){
                posMoves.add(space[s.x+1][s.y-1]);
            }
        }
        if(s.x < 24){
            if(isLegal(space[s.x+1][s.y]) && !isOccupied(space[s.x+1][s.y])){
                posMoves.add(space[s.x+1][s.y]);
            }
        }
        if(s.y < 16){
            if(isLegal(space[s.x-1][s.y+1]) && !isOccupied(space[s.x-1][s.y+1])){
                posMoves.add(space[s.x-1][s.y+1]);
            }
            if(isLegal(space[s.x+1][s.y+1]) && !isOccupied(space[s.x+1][s.y+1])){
                posMoves.add(space[s.x+1][s.y+1]);
            }
        }
        
        return posMoves;
    }
    
    /*public ArrayList<Space> factorJump(Space s) {
        
    }*/
    
    
    //Find a way to get a coordinate of 
    private int distance(Piece p, int x, int y){
        int startingX;
        return 0;
    }
    
    /**
     * Calculates the distance of a the given space from the given coordinates.
     * @param s
     * @param x
     * @param y
     * @return 
     */
    public double distance(Space s, int x, int y){
        double horizMovement = Math.abs(s.x-x);
        double vertMovement = Math.abs(s.y-y);
        double dist;
        if(horizMovement > vertMovement){
            dist = vertMovement + (.5*(horizMovement-vertMovement));
        }else{
            dist = horizMovement + (vertMovement-horizMovement);
        }
        return dist;
        
        /*
         * Tim's Formula
        double dist = Math.ceil(((.5)*(Math.max(Math.abs(s.x-x), Math.abs(s.y-y)))));
        return dist;
        */
    }
    
    /**
     * Formats the y axis for documenting on the log.
     * 
     * @param y
     * @return 
     */
    private String formatY(int y){
        switch(y){
            case 0:
                return "a";
            case 1:
                return "b";
            case 2:
                return "c";
            case 3:
                return "d";
            case 4:
                return "e";
            case 5:
                return "f";
            case 6:
                return "g";
            case 7:
                return "h";
            case 8:
                return "i";
            case 9:
                return "j";    
            case 10:
                return "k";
            case 11:
                return "l";
            case 12:
                return "m";
            case 13:
                return "n";
            case 14:
                return "o";
            case 15:
                return "p";
            case 16:
                return "q";
            default:
                return "";
        }
    } 
    /**
     * Reads the integer value from a move list.
     * 
     * @param s
     * @return 
     */
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
            case "L":
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
