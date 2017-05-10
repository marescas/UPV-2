/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entregableipc.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
public class MenuestadisticasController implements Initializable {
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
    private void ventaDiariaClicked(ActionEvent event) {
        try {
            Stage estageActual = new Stage();
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/entregableipc/view/ventaDiaria.fxml"));
            Parent root = (Parent) miCargador.load();
            miCargador.<VentaDiariaController>getController().initStage(estageActual);
            Scene scene = new Scene(root,550,450);
            estageActual.setMinWidth(550);
            estageActual.setMinHeight(450);
            estageActual.setScene(scene);
            estageActual.initModality(Modality.APPLICATION_MODAL);
            estageActual.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void recaudacionPeliculaClicked(ActionEvent event) {
        try {
            Stage estageActual = new Stage();
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/entregableipc/view/recaudacionPelicula.fxml"));
            Parent root = (Parent) miCargador.load();
            miCargador.<RecaudacionPeliculaController>getController().initStage(estageActual);
            Scene scene = new Scene(root,550,450);
            estageActual.setMinWidth(550);
            estageActual.setMinHeight(450);
            estageActual.setScene(scene);
            estageActual.initModality(Modality.APPLICATION_MODAL);
            estageActual.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void salirClicked(ActionEvent event) {
        primaryStage.close();
    }
    
}
