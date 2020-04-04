/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesecheckersvisalization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javafx.scene.Node;

/**
 *
 * @author G-sta
 */
public class Machine {
    
    private ArrayList<Node> list = new ArrayList<Node>();

    
    public Machine() throws IOException, ClassNotFoundException{
        File file = new File("data.dat");
        if(file.exists()){
            FileInputStream temp_file = new FileInputStream(file);
            ObjectInputStream previous_save_file = new ObjectInputStream(temp_file);
            list = (ArrayList<Node>) previous_save_file.readObject();
            System.out.println(previous_save_file.readObject());
            //tree = preorderRestoreTree();
        }
        
        FileOutputStream temp_file2 = new FileOutputStream("data.dat");
        ObjectOutputStream new_save_file = new ObjectOutputStream(temp_file2);
        new_save_file.writeObject(list);
        
    }
    
}
