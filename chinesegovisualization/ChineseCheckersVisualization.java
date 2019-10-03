/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesegovisualization;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author G-sta
 */
public class ChineseCheckersVisualization extends Application {
    
    ProgressBar bar;
    
    ProgressIndicator indicator;
    
    Label status;
    
    private final int WIN_WIDTH = 1000, WIN_HEIGHT = 750;
    private final int CAN_WIDTH = 750, CAN_HEIGHT = 750;
    
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
        
        //Very Important!!
        int numPlayers = 6;
        
        Board board = new Board(canvas, pane, numPlayers);
        
        
        HBox main = new HBox();
        main.getChildren().add(pane);
        
        root.getChildren().add(main);
        
        Scene scene = new Scene(root, WIN_WIDTH, WIN_HEIGHT);
        
        primaryStage.setTitle("Hello World!");
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
