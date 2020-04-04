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
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javax.imageio.ImageIO;

/**
 *
 * @author G-sta
 */
public class Board {
    
    protected Pane pane;
    private Canvas canvas;
    private GraphicsContext ctx;
    protected Space[][] board;
    
    protected final int PIECE_WIDTH = 15;
    private int numPlayers;
    
    protected int player_turn = 0;
    protected int player_index = 0;
    
    
    public void createBase(){
        
    }
    
    
    /**
     * Creates a board that fills each space with their respective type if fill 
     * set to true.
     * 
     * @param canvas
     * @param pane
     * @param dummy whether or not this board will be used for gameplay. If true,
     * board will not be able to be played on.
     * @param fill whether or not to show distinct space types. If true, will 
     * populate board with dummy pieces. Cannot be true if dummy is false.
     */
    public Board(Canvas canvas, Pane pane, boolean dummy, boolean fill){
        if(!dummy && fill){
            System.out.println("Incorrect Usage: Board cannot be filled if it "
                    + "is not a dummy board.");
            return;
        }
        this.pane = pane;
        this.canvas = canvas;
        ctx = canvas.getGraphicsContext2D();
        board = createBoard(board);
        drawBoard(board);
        if(!dummy){
            numPlayers = 1;
            createTeam1(board);
        }else{
            numPlayers = 0;
            if(fill){
                //              i%4 < 2   i%4 > 1
                // 0 = blue   | j%4 = 0 | j%4 = 2
                // 1 = green  | j%4 = 3 | j%4 = 1
                // 2 = red    | j%4 = 2 | j%4 = 4
                // 3 = yellow | j%4 = 1 | j%4 = 3
                for(int i = 0; i < board.length; i++){
                    for(int j = 0; j < board[0].length; j++){
                        if(board[i][j].onBoard){
                            Color c = Color.WHITE;
                            if(i%4 < 2){
                                switch(j%4){
                                    case 0:
                                        c = Color.BLUE;
                                        break;
                                    case 1:
                                        c = Color.GREEN;
                                        break;
                                    case 2:
                                        c = Color.RED;
                                        break;
                                    case 3:
                                        c = Color.YELLOW;
                                }
                            }else if(i%4 > 1){
                                switch(j%4){
                                    case 2:
                                        c = Color.BLUE;
                                        break;
                                    case 3:
                                        c = Color.GREEN;
                                        break;
                                    case 0:
                                        c = Color.RED;
                                        break;
                                    case 1:
                                        c = Color.YELLOW;
                                }
                            }
                            board[5][i].setPiece(new Piece(this, board[i][j], c));
                        }
                    }
                }
                
                try{
                    String file_name = "boards/filled_board.png";
                    File output = new File(file_name);
                    BufferedImage bimg = SwingFXUtils.fromFXImage(screenshot(), null);
                    ImageIO.write(bimg, "png", output);
                }catch(IOException ex){
                    System.out.println("Could not save blank board screenshot.");
                }
            }else{
                try{
                    String file_name = "boards/blank_board.png";
                    File output = new File(file_name);
                    BufferedImage bimg = SwingFXUtils.fromFXImage(screenshot(), null);
                    ImageIO.write(bimg, "png", output);
                }catch(IOException ex){
                    System.out.println("Could not save filled board screenshot.");
                }
            }
        }
    }
    
    public Board(Canvas canvas, Pane pane, int numPlayers){
        this.pane = pane;
        this.canvas = canvas;
        ctx = canvas.getGraphicsContext2D();
        this.numPlayers = numPlayers;
        board = createBoard(board);
        drawBoard(board);
        
        //createTeam1(board);
        switch(numPlayers){
            case 1:
                createTeam1(board);
                break;
            case 2:
                createTeam1(board);
                createTeam4(board);
                break;
            case 3:
                createTeam1(board);
                createTeam3(board);
                createTeam5(board);
                break;
            case 4:
                createTeam2(board);
                createTeam3(board);
                createTeam5(board);
                createTeam6(board);
                break;
            case 5:
                createTeam1(board);
                createTeam2(board);
                createTeam3(board);
                createTeam5(board);
                createTeam6(board);
                break;
            case 6:
                createTeam1(board);
                createTeam2(board);
                createTeam3(board);
                createTeam4(board);
                createTeam5(board);
                createTeam6(board);
                break;
            default:
                break;
        }
    }
    
    public Pane getPane(){return pane;}
    public Canvas getCanvas(){return canvas;}
    public int getNumPlayers(){return numPlayers;}
    public Space[][] getBoard(){return board;}
    
    public Image screenshot(){
        WritableImage tempImage = new WritableImage((int) pane.getPrefWidth(), (int) pane.getPrefHeight());
        pane.snapshot(null, tempImage);
        ImageView imgView = new ImageView(tempImage);
        return imgView.getImage();
    }
    
    private Space[][] createBoard(Space[][] b){
        //Instantiate the board as an empty grid
        b = new Space[17][25];
        //fill all of the spaces with "dummy" spaces
        int y = 8;
        for(int i = 0; i < b.length; i++){
            int x = -12;
            for(int j = 0; j < b[0].length; j++){
                b[i][j] = new Space(x, y);
                x++;
            }
            y--;
        }
        
        //Set pieces onto board
        b[0][12].onBoard = true;
        for(int i = 11; i <= 13; i+=2){b[1][i].onBoard = true;}
        for(int i = 10; i <= 14; i+=2){b[2][i].onBoard = true;}
        for(int i = 9; i <= 15; i+=2){b[3][i].onBoard = true;}
        for(int i = 0; i <= 24; i+=2){b[4][i].onBoard = true;}
        for(int i = 1; i <= 23; i+=2){b[5][i].onBoard = true;}
        for(int i = 2; i <= 22; i+=2){b[6][i].onBoard = true;}
        for(int i = 3; i <= 21; i+=2){b[7][i].onBoard = true;}
        for(int i = 4; i <= 20; i+=2){b[8][i].onBoard = true;}
        for(int i = 3; i <= 21; i+=2){b[9][i].onBoard = true;}
        for(int i = 2; i <= 22; i+=2){b[10][i].onBoard = true;}
        for(int i = 1; i <= 23; i+=2){b[11][i].onBoard = true;}
        for(int i = 0; i <= 24; i+=2){b[12][i].onBoard = true;}
        for(int i = 9; i <= 15; i+=2){b[13][i].onBoard = true;}
        for(int i = 10; i <= 14; i+=2){b[14][i].onBoard = true;}
        for(int i = 11; i <= 13; i+=2){b[15][i].onBoard = true;}
        b[16][12].onBoard = true;
        
        
        return b;
    }
    
    private void createBorders(){
        
        // Create borders for goals
        Double[] x_points = {
                40.0, 265.0, 150.0, 40.0,
                265.0, 485.0, 375.0, 265.0,
                485.0, 710.0, 600.0, 485.0,
                485.0, 710.0, 600.0, 485.0,
                265.0, 485.0, 375.0, 265.0,
                40.0, 265.0, 150.0, 40.0
        };
        Double[] y_points = {
                190.0, 190.0, 370.0, 190.0,
                190.0, 190.0, 10.0, 190.0,
                190.0, 190.0, 370.0, 190.0,
                550.0, 550.0, 370.0, 550.0,
                550.0, 550.0, 730.0, 550.0,
                550.0, 550.0, 370.0, 550.0
        };
                
        for(int i = 0; i < x_points.length; i++){
            if(i%4 == 0) {
                // Temp Arrays
                double[] points_x = {x_points[i], x_points[i+1], x_points[i+2], x_points[i+3]};
                double[] points_y = {y_points[i], y_points[i+1], y_points[i+2], y_points[i+3]};
                
                double temp_width = ctx.getLineWidth();
                ctx.beginPath();
                ctx.setLineWidth(3);
                ctx.setStroke(Color.BLACK);
                ctx.setFill(Color.LIGHTGRAY);
                ctx.strokePolygon(points_x, points_y, 4);
                ctx.fillPolygon(points_x, points_y, 4);
                ctx.closePath();               
                ctx.setLineWidth(temp_width);
            }
        }
    }

    private void drawBoard(Space[][] b){        
        double width = PIECE_WIDTH, x = 75, y = 50;
        final double BASE_X = 75, BASE_Y = 50;
        double xPadding = 25, yPadding = 40;
        
        ctx.beginPath();
        ctx.rect(0, 0, canvas.getWidth(), canvas.getHeight());
        ctx.setFill(Color.WHITE);
        ctx.fill();
        ctx.closePath();
        
        createBorders();
        
        for(int i = 0; i < b[0].length; i++){
            ctx.beginPath();
            ctx.moveTo(x, 0);
            ctx.lineTo(x, canvas.getHeight());
            ctx.setFill(Color.BLACK);
            ctx.stroke();
            ctx.closePath();
            x+=xPadding;
        }
        for(int i = 0; i < b.length; i++){
            ctx.beginPath();
            ctx.moveTo(0, y);
            ctx.lineTo(canvas.getWidth(), y);
            ctx.setFill(Color.BLACK);
            ctx.stroke();
            ctx.closePath();
            y+=yPadding;
        }
        y = BASE_Y;
        
        x = BASE_X;
        int index = 0;
        for(int i = 0; i < b[0].length; i++){
            ctx.beginPath();
            ctx.rect(BASE_X-(BASE_X/8)+(i*xPadding), (BASE_Y/16), BASE_X/4, BASE_X/4);
            ctx.setStroke(Color.BLACK);
            ctx.setFill(Color.LIGHTGRAY);
            ctx.stroke();
            ctx.fill();
            ctx.closePath();
            ctx.beginPath();
            ctx.setFill(Color.BLACK);
            ctx.fillText("" + index, x-(BASE_X/16), BASE_Y/3);
            ctx.closePath();
            index++;
            x+=xPadding;
        }
        String[] left_axis = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r"};
        for(int i = 0; i < b.length; i++){
            ctx.beginPath();
            ctx.rect((BASE_X/4), BASE_Y-((BASE_Y/4))+(i*yPadding), BASE_X/4, BASE_X/4);
            ctx.setStroke(Color.BLACK);
            ctx.setFill(Color.LIGHTGRAY);
            ctx.stroke();
            ctx.fill();
            ctx.closePath();
            ctx.beginPath();
            ctx.setFill(Color.BLACK);
            ctx.fillText(left_axis[i], BASE_X/3, y+(BASE_Y/18));
            ctx.closePath();
            y+=yPadding;
        }
        y = BASE_Y;
        
        for(int i = 0; i < b.length; i++){
            x = BASE_X;
            for(int j = 0; j < b[0].length; j++){
                if(b[i][j].onBoard)
                {
                    ctx.beginPath();
                    ctx.setLineWidth(1);
                    ctx.setStroke(Color.BLACK);
                    ctx.setFill(Color.GRAY);
                    ctx.arc(x, y, width, width, 0, 360);
                    ctx.fill();
                    ctx.stroke();
                    ctx.closePath();
                    ctx.beginPath();
                    ctx.setLineWidth(1);
                    ctx.setStroke(Color.BLACK);
                    ctx.setFill(Color.DARKGRAY);
                    ctx.arc(x, y, width/4, width/4, 0, 360);
                    ctx.fill();
                    ctx.stroke();
                    ctx.closePath();
                    b[i][j].setCoordinates(x-5, y-5);
                }
                x+=xPadding;
            }
            y+=yPadding;
        }
    }

    private void createTeam1(Space[][] b){
        Color c = Color.RED;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 25; j++){
                if(b[i][j].onBoard){
                    board[i][j].setPiece(new Piece(this, b[i][j], c));
                }
            }
        }
        player_index++;
    }
    private void createTeam2(Space[][] b){
        Color c = Color.PURPLE;
        for(int i = 18; i <= 24; i+=2){
            if(b[4][i].onBoard){
                board[4][i].setPiece(new Piece(this, b[4][i], c));
            }
        }
        for(int i = 19; i <= 23; i+=2){
            if(b[5][i].onBoard){
                board[5][i].setPiece(new Piece(this, b[5][i], c));
            }
        }
        board[6][20].setPiece(new Piece(this, b[6][20], c));
        board[6][22].setPiece(new Piece(this, b[6][22], c));
        board[7][21].setPiece(new Piece(this, b[7][21], c));
        player_index++;
    }
    private void createTeam3(Space[][] b){
        Color c = Color.GREEN;
        board[9][21].setPiece(new Piece(this, b[9][21], c));
        board[10][20].setPiece(new Piece(this, b[10][20], c));
        board[10][22].setPiece(new Piece(this, b[10][22], c));
        for(int i = 19; i <= 23; i+=2){
            if(b[11][i].onBoard){
                board[11][i].setPiece(new Piece(this, b[11][i], c));
            }
        }
        for(int i = 18; i <= 24; i+=2){
            if(b[12][i].onBoard){
                board[12][i].setPiece(new Piece(this, b[12][i], c));
            }
        }
        player_index++;
    }
    private void createTeam4(Space[][] b){
        Color c = Color.DARKORANGE;
        for(int i = b.length-4; i < b.length; i++){
            for(int j = 0; j < 25; j++){
                if(b[i][j].onBoard){
                    board[i][j].setPiece(new Piece(this, b[i][j], c));
                }
            }
        }
        player_index++;
    }
    private void createTeam5(Space[][] b){
        Color c = Color.BLUE;
        board[9][3].setPiece(new Piece(this, b[9][3], c));
        board[10][2].setPiece(new Piece(this, b[10][2], c));
        board[10][4].setPiece(new Piece(this, b[10][4], c));
        for(int i = 1; i <= 5; i+=2){
            if(b[11][i].onBoard){
                board[11][i].setPiece(new Piece(this, b[11][i], c));
            }
        }
        for(int i = 0; i <= 6; i+=2){
            if(b[12][i].onBoard){
                board[12][i].setPiece(new Piece(this, b[12][i], c));
            }
        }
        player_index++;
    }
    private void createTeam6(Space[][] b){
        Color c = Color.YELLOW;
        for(int i = 0; i <= 6; i+=2){
            if(b[4][i].onBoard){
                board[4][i].setPiece(new Piece(this, b[4][i], c));
            }
        }
        for(int i = 1; i <= 5; i+=2){
            if(b[5][i].onBoard){
                board[5][i].setPiece(new Piece(this, b[5][i], c));
            }
        }
        board[6][2].setPiece(new Piece(this, b[6][2], c));
        board[6][4].setPiece(new Piece(this, b[6][4], c));
        board[7][3].setPiece(new Piece(this, b[7][3], c));
        player_index++;
    }
}
