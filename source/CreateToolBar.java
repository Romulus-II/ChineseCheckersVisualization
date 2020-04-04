/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesecheckersvisalization;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author G-sta
 */
public class CreateToolBar {
    
    private final int TOOLS_WIDTH;
    
    private final VBox tools;
    
    public CreateToolBar(Game game, int window_width, int canvas_width){
        TOOLS_WIDTH = window_width - canvas_width;
        tools = new VBox();
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
            System.out.println(e + "happened");
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
            System.out.println(e + "happened");
        }
        redo.setOnAction((ActionEvent event) -> {
            game.showNextTurn();
            turnLabel.setText("Turn " + game.getTurnNumber());
        });
        
        HBox v1 = new HBox();
        v1.getChildren().addAll(undo, redo);
        
        Button show_base = new Button("Display Base");
        show_base.setPrefSize(TOOLS_WIDTH,TOOLS_WIDTH/4);
        show_base.setOnAction((ActionEvent event) -> {
            BaseDisplay bd = new BaseDisplay(500, 450);
            bd.showWindow(true);
        });
        
        HBox v2 = new HBox();
        v2.getChildren().addAll(show_base);
        
        tools.getChildren().addAll(turnLabel, v1, v2);
    }
    
    public VBox getContent(){
        return tools;
    }
    
}
