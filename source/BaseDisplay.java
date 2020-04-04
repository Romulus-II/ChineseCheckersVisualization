/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesecheckersvisalization;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author G-sta
 */
public class BaseDisplay {
    
    private final int WIDTH, HEIGHT;
    
    private Pane pane;
    private Canvas canvas;
    private GraphicsContext ctx;
    private Space[][] board;
    private ArrayList colors;
    
    protected final int PIECE_WIDTH = 25;
    
    public BaseDisplay(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        canvas = new Canvas(WIDTH, HEIGHT);
        ctx = canvas.getGraphicsContext2D();
        pane = new Pane(canvas);
        board = new Space[4][7];
        createBoard(board);
        colors = new ArrayList<>();
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j].onBoard){
                    Color c = Color.WHITE;
                    if(i%4 < 2){
                        switch(j%4){
                            case 0:
                                c = Color.GREEN;
                                break;
                            case 1:
                                c = Color.RED;
                                break;
                            case 2:
                                c = Color.YELLOW;
                                break;
                            case 3:
                                c = Color.BLUE;
                        }
                    }else if(i%4 > 1){
                        switch(j%4){
                            case 0:
                                c = Color.YELLOW;
                                break;
                            case 1:
                                c = Color.BLUE;
                                break;
                            case 2:
                                c = Color.GREEN;
                                break;
                            case 3:
                                c = Color.RED;
                        }
                    }
                    colors.add(c);
                    System.out.println(colors.size());
                    board[i][j].setPiece(new Piece(PIECE_WIDTH ,pane, board[i][j], c));
                }
            }
        }
    }
    
    
    private void createBoard(Space[][] b){  
        int y_index = 8;
        for(int i = 0; i < b.length; i++){
            int x_index = -12;
            for(int j = 0; j < b[0].length; j++){
                b[i][j] = new Space(x_index, y_index);
                x_index++;
            }
            y_index--;
        }
        int start = 0;
        for(int i = b.length-1; i >= 0; i--) {
            for(int j = start; j < b[0].length-start; j+=2) {
                b[i][j].onBoard = true;
            }
            start++;
        }
         
        double width = PIECE_WIDTH, x = 75, y = 50;
        final double BASE_X = 105, BASE_Y = 105;
        double xPadding = PIECE_WIDTH*2, yPadding = PIECE_WIDTH*3;
        
        ctx.beginPath();
        ctx.rect(0, 0, canvas.getWidth(), canvas.getHeight());
        ctx.setFill(Color.WHITE);
        ctx.fill();
        ctx.closePath();
        
        y = BASE_Y;
        
        System.out.println(colors.size());
        
        int index = 0;
        for(int i = 0; i < b.length; i++){
            x = BASE_X;
            for(int j = 0; j < b[0].length; j++){
                if(b[i][j].onBoard)
                {
                    b[i][j].setCoordinates(x-5, y-5);
                    ctx.beginPath();
                    ctx.setLineWidth(BASE_X/21);
                    ctx.arc(x, y, PIECE_WIDTH, PIECE_WIDTH, 0, 2*Math.PI);
                    ctx.setStroke(Color.BLACK);
                    ctx.setFill(board[i][j].getPiece().getColor());
                    ctx.stroke();
                    ctx.fill();
                    ctx.closePath();
                    index++;
                    pane.getChildren().remove(b[i][j].getPiece());
                }
                x+=xPadding;
            }
            y+=yPadding;
        }
    }
    
    public void showWindow(boolean screenshot) {
        try{
            Stage popupwindow=new Stage();
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.setTitle("Example Base");

            VBox layout= new VBox();

            layout.getChildren().addAll(pane);
            layout.setAlignment(Pos.CENTER);

            Scene scene1= new Scene(layout, canvas.getWidth(), canvas.getHeight());
            popupwindow.setScene(scene1);
            popupwindow.show();

            String file_name = "boards/base.png";
            File output = new File(file_name);
            BufferedImage bimg = SwingFXUtils.fromFXImage(screenshot(), null);
            ImageIO.write(bimg, "png", output);
            
        } catch (IOException io) {
            System.out.println("Could not display base.");
        } catch (Exception e) {
            
        }
    }
          
    public Image screenshot(){
        WritableImage tempImage = new WritableImage((int) pane.getPrefWidth(), (int) pane.getPrefHeight());
        pane.snapshot(null, tempImage);
        ImageView imgView = new ImageView(tempImage);
        return imgView.getImage();
    }
    

}
