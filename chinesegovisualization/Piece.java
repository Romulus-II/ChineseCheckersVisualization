/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesegovisualization;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author G-sta
 */
public class Piece {
    
    private Circle circle;
    private Color color;
    private double x, y;
    
    public Piece(Board b, Space s, Color c){
        this.x = s.cenx;
        this.y = s.ceny;
        color = c;
        s.occupied = true;
        s.piece = this;
        circle = new Circle(x, y, b.PIECE_WIDTH);
        circle.setStrokeWidth(2);
        circle.setStroke(Color.BLACK);
        circle.setFill(c);
        circle.setRadius(12.5);
        b.pane.getChildren().add(circle);
    }
    
    public void moveTo(Space s){
        s.occupied = true;
        s.piece = this;
    }
    public void moveFrom(Space s){
        s.occupied = false;
        s.piece = null;
    }
    
}
