/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesecheckersvisalization;

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
    
    ProgressBar bar;
    
    ProgressIndicator indicator;
    
    Label status;
    
    private final int WIN_WIDTH = 1000, WIN_HEIGHT = 750;
    private final int CAN_WIDTH = 750, CAN_HEIGHT = 750;
    private final int TOOLS_WIDTH = WIN_WIDTH-CAN_WIDTH;
    
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
        int numPlayers = 2;
        
        Board board = new Board(canvas, pane, numPlayers);
        Game game = new Game(board);
        
        FileReader fr = new FileReader("2Player_Moves_Eric.txt", game);
        //game.move("d12", "e13");
        
        
        
        VBox tools = new VBox();
        tools.setPrefWidth(TOOLS_WIDTH);
        
        Label turnLabel = new Label("Turn 0");
        turnLabel.setPrefSize(TOOLS_WIDTH, TOOLS_WIDTH/4);
        turnLabel.setAlignment(Pos.CENTER);
        
        
        Button undo = new Button();
        undo.setPrefSize(TOOLS_WIDTH/2,TOOLS_WIDTH/2);
        try{
            Image img = new Image("/images/undo.png", (TOOLS_WIDTH/2)-15, (TOOLS_WIDTH/2)-15, false, false);
            ImageView icon = new ImageView(img);
            undo.setGraphic(icon);
        }catch(NullPointerException e){
            System.out.println(e + "happnened");
        }
        undo.setOnAction((ActionEvent event) -> {
            game.showPastTurn();
            turnLabel.setText("Turn " + game.getTurnNumber());
        });
        Button redo = new Button();
        redo.setPrefSize(TOOLS_WIDTH/2,TOOLS_WIDTH/2);
        try{
            Image img = new Image("/images/redo.png", (TOOLS_WIDTH/2)-15, (TOOLS_WIDTH/2)-15, false, false);
            ImageView icon = new ImageView(img);
            redo.setGraphic(icon);
        }catch(NullPointerException e){
            System.out.println(e + "happnened");
        }
        redo.setOnAction((ActionEvent event) -> {
            game.showNextTurn();
            turnLabel.setText("Turn " + game.getTurnNumber());
        });
        
        HBox v1 = new HBox();
        v1.getChildren().addAll(undo, redo);
        
        
        tools.getChildren().addAll(turnLabel, v1);
        
        
        
        HBox main = new HBox();
        main.getChildren().addAll(pane, tools);
        
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
