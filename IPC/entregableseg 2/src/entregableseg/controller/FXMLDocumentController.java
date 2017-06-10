/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entregableseg.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author marcosesteve
 */
public class FXMLDocumentController implements Initializable {
    
   
  
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    }    

    @FXML
    private void eleccionesAno(ActionEvent event) {
        try {
            Stage estageActual = new Stage();
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/entregableseg/view/MainFXML.fxml"));
            Parent root = (Parent) miCargador.load();
             
            
            Scene scene = new Scene(root);
            estageActual.setMinWidth(700);
            estageActual.setMinHeight(800);
            estageActual.setScene(scene);
            estageActual.initModality(Modality.APPLICATION_MODAL);
            estageActual.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void historico(ActionEvent event) {
       
         try {
            Stage estageActual = new Stage();
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/entregableseg/view/historicosPortada.fxml"));
            Parent root = (Parent) miCargador.load();
            
            miCargador.<HistoricosPortadaController>getController().initStage(estageActual);
            Scene scene = new Scene(root);
            estageActual.setMinWidth(700);
            estageActual.setMinHeight(500);
            estageActual.setScene(scene);
            estageActual.initModality(Modality.APPLICATION_MODAL);
            estageActual.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
