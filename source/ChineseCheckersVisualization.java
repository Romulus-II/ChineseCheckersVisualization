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
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author G-sta
 */
public class ChineseCheckersVisualization extends Application {
    
//Predefined Variables    
    private final int NUM_PLAYERS = 6;
    private final boolean ANALYZING_MOVE_SHEET = true;
    private final boolean CHECKING_DISTANCES = false;
    
    
    
    ProgressBar bar;
    
    ProgressIndicator indicator;
    
    Label status;
    
    private final int WIN_WIDTH = 1000, WIN_HEIGHT = 750;
    private final int CAN_WIDTH = 750, CAN_HEIGHT = 750;
    
    private BufferedWriter logger;
    
    @Override
    public void start(Stage primaryStage) {
        /*Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });*/
        StackPane root = new StackPane();
        
        Canvas canvas = new Canvas(CAN_WIDTH, CAN_HEIGHT);
        Pane pane = new Pane(canvas);
        pane.setPrefSize(CAN_WIDTH, CAN_HEIGHT);
        
        //Very Important!!
        //Later change to ask for user input 1st.
        int numPlayers = NUM_PLAYERS;
        
        // Can only use one board type
        //Board board = new Board(canvas, pane, true, true);
        Board board = new Board(canvas, pane, numPlayers);
        Game game = new Game(board);
        
        /**/
        // Can only be used if using the 2nd board type. Make sure number of players in
        // move list matches variable numPlayers.
        if(ANALYZING_MOVE_SHEET){
            try {
                FileReader fr = new FileReader("move_lists/new_6_player_moves_Eric.txt", game);
            } catch (IOException ex) {
                Logger.getLogger(ChineseCheckersVisualization.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Couldn't find moves list");
            }
        }
        /**/
        
        CreateToolBar toolbar = new CreateToolBar(game, WIN_WIDTH, CAN_WIDTH);
       
        
        //----------------------------------------------------------------------      
        if(CHECKING_DISTANCES){
            System.out.println("We have collected the spaces");
            System.out.print(game.getSpaces().length);
            System.out.println(" : " + game.getSpaces()[0].length);
            try {
                File logFile = new File("Log.txt");
                logger = new BufferedWriter(new FileWriter(logFile, true));

                logger.write(new Date(System.currentTimeMillis()).toString());
                logger.newLine();
                logger.write("Distance from center");

                System.out.println("Now calculating distances");

                for(int i = 0; i < game.getSpaces().length; i++){
                    for(int j = 0; j < game.getSpaces()[0].length; j++){
                        //System.out.println(i + " : " + j);
                        if(game.getSpaces()[i][j].onBoard){
                            String dist = "(";
                            if(game.getSpaces()[i][j].x > 0 && game.getSpaces()[i][j].x < 10){
                                dist = dist + " " + game.getSpaces()[i][j].x;
                            }else{
                                dist = dist + game.getSpaces()[i][j].x;
                            }
                            if(game.getSpaces()[i][j].y < 10){
                                dist = dist + " " + game.getSpaces()[i][j].y;
                            }else{
                                dist = dist + game.getSpaces()[i][j].y;
                            }
                            dist = dist + ") : ";
                            dist += game.distance(game.getSpaces()[i][j], 0, 0);
                            //System.out.println(dist);
                            logger.newLine();
                            logger.write(dist);
                        }
                    }
                }

                logger.flush();
                logger.close();

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                Logger.getLogger(DistanceLogger.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        //----------------------------------------------------------------------
        
        /**
        try {
            DistanceLogger logger = new DistanceLogger(game);
            logger.markDistances(0, 0);
            logger.publish();
        } catch (IOException ex) {
            Logger.getLogger(ChineseCheckersVisualization.class.getName()).log(Level.SEVERE, null, ex);
        }
        **/
        
        
        HBox main = new HBox();
        main.getChildren().addAll(pane, toolbar.getContent());
        
        root.getChildren().add(main);
        
        Scene scene = new Scene(root, WIN_WIDTH, WIN_HEIGHT);
        
        primaryStage.setTitle("Chinese Checkers Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
