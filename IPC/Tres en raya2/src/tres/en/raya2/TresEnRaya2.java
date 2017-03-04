/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tres.en.raya2;

import java.awt.Insets;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author marcosesteve
 */
public class TresEnRaya2 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
       GridPane grid = new GridPane();
        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                
                Button boton = new Button();
                boton.setPrefSize(10000000, 10000000);
                grid.getChildren().add(boton); //añado un elemento al grid.
                GridPane.setConstraints(boton, col, row); //pone la columna y fila al elemento.
                
                
            }   
        }
        grid.setPadding(new javafx.geometry.Insets(0, 10, 0, 10));
        BorderPane root = new BorderPane();
        HBox hTextoTop = new HBox();
        hTextoTop.setAlignment(Pos.CENTER);
        Label textoTop =new Label("Tres en raya");
        hTextoTop.getChildren().add(textoTop);
        
        
        textoTop.setPadding(new javafx.geometry.Insets(10, 0, 10, 0));
        HBox botonesBottom  = new HBox(5);
        botonesBottom.getChildren().add(new Button("Empezar"));
        botonesBottom.getChildren().add(new Button("Récords"));
        botonesBottom.setAlignment(Pos.CENTER);
        
        botonesBottom.setPadding(new javafx.geometry.Insets(10, 0, 10, 0)); //padding a top y bot.
        root.setTop(hTextoTop);
        root.setBottom(botonesBottom);
        
      
        root.setCenter(grid);
        Scene n = new Scene(root,300,300);
        primaryStage.setScene(n);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
        
}
