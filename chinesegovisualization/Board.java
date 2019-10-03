/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesegovisualization;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

/**
 *
 * @author G-sta
 */
public class Board {
    
    protected Pane pane;
    private Canvas canvas;
    private GraphicsContext ctx;
    protected Space[][] board;
    
    protected final int PIECE_WIDTH = 5;
    
    public Board(Canvas canvas, Pane pane, int numPlayers){
        this.pane = pane;
        this.canvas = canvas;
        ctx = canvas.getGraphicsContext2D();
        board = createBoard(board);
        drawBoard(board);
        
        //createTeam1(board);
        switch(numPlayers){
            case 1:
                createTeam1(board);
                break;
            case 2:
                createTeam2(board);
                break;
            case 3:
                createTeam2(board);
                createTeam4(board);
                createTeam6(board);
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

    private void drawBoard(Space[][] b){
        double width = 10, x = 40, y = 40;
        final double BASE_X = 40, BASE_Y = 40;
        double xPadding = 25, yPadding = 40;
        
        int index = 1;
        for(int i = 0; i < b[0].length; i++){
            ctx.beginPath();
            ctx.setFill(Color.BLACK);
            ctx.fillText("" + index, x, 15);
            ctx.closePath();
            index++;
            x+=xPadding;
        }
        String[] left_axis = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r"};
        for(int i = 0; i < b.length; i++){
            ctx.beginPath();
            ctx.setFill(Color.BLACK);
            ctx.fillText(left_axis[i], 15, y);
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
                    ctx.setLineWidth(2);
                    ctx.setStroke(Color.BLACK);
                    ctx.setFill(Color.DARKGRAY);
                    ctx.fillArc(x, y, width, width, 0, 360, ArcType.CHORD);
                    ctx.closePath();
                    b[i][j].setCoordinates(x, y);
                }
                /*else
                {
                    ctx.beginPath();
                    ctx.setLineWidth(2);
                    ctx.setStroke(Color.BLACK);
                    ctx.setFill(Color.RED);
                    ctx.fillArc(x, y, width, width, 0, 360, ArcType.CHORD);
                    ctx.closePath();
                }*/
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
        
    }
}
