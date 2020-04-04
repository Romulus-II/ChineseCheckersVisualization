/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesecheckersvisalization;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author G-sta
 */
public class Piece {
    
    private Space s;
    
    private Circle circle;
    private Color color;
    private double x, y;
    private final int player;
    
    public Piece(int width, Pane pane, Space s, Color c){
        player = 0;
        this.s = s;
        this.x = s.cenx;
        this.y = s.ceny;
        color = c;
        s.occupied = true;
        s.setPiece(this);
        circle = new Circle(x, y, width);
        circle.setStrokeWidth(2);
        circle.setStroke(Color.BLACK);
        circle.setFill(c);
        circle.setRadius(width);
        pane.getChildren().add(circle);
    }
    
    public Piece(Board b, Space s, Color c){
        this.s = s;
        player = b.player_index;
        this.x = s.cenx;
        this.y = s.ceny;
        color = c;
        s.occupied = true;
        s.setPiece(this);
        circle = new Circle(x, y, b.PIECE_WIDTH);
        circle.setStrokeWidth(2);
        circle.setStroke(Color.BLACK);
        circle.setFill(c);
        circle.setRadius(b.PIECE_WIDTH);
        b.pane.getChildren().add(circle);
    }
    
    public Color getColor(){return color;}
    
    public void configure(Game game, Board board){
        circle.setOnMousePressed(new EventHandler() {
            @Override
            public void handle(Event event) {
                if(board.player_turn == player){
                    game.showMoves(s);
                }
            }
        });
    }
    
    public void highlight(){
        if(circle.getFill()!=Color.YELLOW){
            circle.setStroke(Color.YELLOW);
        }else{
            circle.setStroke(Color.BLUE);
        }
    }
    public void unhighlight(){
        circle.setStroke(Color.BLACK);
    }
    
    //Find a way to change s.
    public void move(Space s, Space e){
        moveFrom(s);
        moveTo(e);
        circle.setCenterX(e.cenx);
        circle.setCenterY(e.ceny);
    }
    
    public void moveTo(Space s){
        x = s.x;
        y = s.y;
        s.occupied = true;
        s.piece = this;
    }
    public void moveFrom(Space s){
        s.occupied = false;
        s.piece = null;
    }
    
}
