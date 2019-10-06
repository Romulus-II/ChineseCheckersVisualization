/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesecheckersvisalization;

/**
 *
 * @author G-sta
 */
public class Space {
    
    protected int x, y;
    
    //Used to draw piece on board
    protected double cenx, ceny;
    
    protected boolean occupied;
    protected Piece piece;
    
    protected boolean onBoard = false;
    
    public Space(int x, int y){
        this.x = x;
        this.y = y;
        occupied = false;
        piece = null;
    }
    
    public Space(int x, int y, Piece piece){
        this.x = x;
        this.y = y;
        occupied = true;
        this.piece = piece;
    }
    
    public Piece getPiece(){return piece;}
    public void setPiece(Piece piece){
        this.piece = piece;
        this.occupied = true;
    }
    public void setCoordinates(double x, double y){
        cenx = x+5;
        ceny = y+5;
    }
}
