/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesegovisualization;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

/**
 *
 * @author G-sta
 */
class DoWork extends Task<Integer>{
   
    @Override
    protected Integer call() throws Exception {
        for(int i = 0; i < 10; i++){
            System.out.println(i + 1);
            updateProgress(i + 1, 10);
            Thread.sleep(500);
            if(isCancelled()){
                
                return i;
            }
        }
        return 10;
    }
    
    @Override
    public boolean cancel(boolean mayInterruptIfRunning){
        updateMessage("Cancelled!");
        return super.cancel(mayInterruptIfRunning);
    }
    
    @Override
    protected void updateProgress(double workDone, double max){
        updateMessage("progress!" + workDone);
        super.updateProgress(workDone, max);
    }
}

public class FirstController implements Initializable {
    
    ProgressBar bar;
    
    ProgressIndicator indicator;
     
    Label status;
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        
        DoWork task = new DoWork();
        
        bar.progressProperty().bind(task.progressProperty());
        indicator.progressProperty().bind(task.progressProperty());
        status.textProperty().bind(task.messageProperty());
    
        new Thread(task).start();
    
    }
    
}
