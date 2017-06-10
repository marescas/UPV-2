/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entregableseg.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author marcosesteve
 */
public class HistoricosPortadaController implements Initializable {
Stage primaryStage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    void initStage(Stage estageActual) {
        primaryStage = estageActual;
    }
    @FXML
    private void historicoGeneral(ActionEvent event) {
        try {
            Stage estageActual = new Stage();
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/entregableseg/view/HistoricosGeneral.fxml"));
            Parent root = (Parent) miCargador.load();
             Scene scene = new Scene(root);
            
            miCargador.<HistoricosGeneralController>getController().initStage(estageActual,scene);
           
            estageActual.setMinWidth(700);
            estageActual.setMinHeight(500);
            estageActual.setScene(scene);
            estageActual.initModality(Modality.APPLICATION_MODAL);
            estageActual.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void historicoPartidos(ActionEvent event) {
        try {
            Stage estageActual = new Stage();
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/entregableseg/view/historicoEspecifico.fxml"));
            Parent root = (Parent) miCargador.load();
             Scene scene = new Scene(root);
            
            miCargador.<HistoricoEspecificoController>getController().initStage(estageActual,scene);
           
            estageActual.setMinWidth(700);
            estageActual.setMinHeight(500);
            estageActual.setScene(scene);
            estageActual.initModality(Modality.APPLICATION_MODAL);
            estageActual.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void atras(ActionEvent event) {
        primaryStage.close();
       
    }
    
}
