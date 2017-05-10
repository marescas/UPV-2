/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entregableipc.controller;

import accesoaBD.AccesoaBD;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Pelicula;

/**
 *
 * @author marcosesteve
 */
public class mainviewController implements Initializable {

   
    @FXML
    private VBox vboxImagen;
    @FXML
    private ImageView imagenCartelera;
    int i= 0;
    Image imagen;
    List<Pelicula> peliculas;
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AccesoaBD bd = new AccesoaBD();
        peliculas = bd.getTodasPeliculas();
        
        imagen= new Image(peliculas.get(i).getPathImage());
        
        imagenCartelera.setImage(imagen);
        
        vboxImagen.setAlignment(Pos.CENTER);
       
        
    }    

    @FXML
    private void reservarClicked(ActionEvent event) {
         try {
            Stage estageActual = new Stage();
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/entregableipc/view/reservas.fxml"));
            Parent root = (Parent) miCargador.load();
            miCargador.<ReservasController>getController().initStage(estageActual);
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
    private void venderClicked(ActionEvent event) {
        try {
            Stage estageActual = new Stage();
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/entregableipc/view/ventaentradas.fxml"));
            Parent root = (Parent) miCargador.load();
            miCargador.<VentaentradasController>getController().initStage(estageActual);
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
    private void clicadaimagen(MouseEvent event) {
        i = (i+1)%peliculas.size();
        Image img = new Image(peliculas.get(i).getPathImage());
        imagenCartelera.setImage(img);
    }

    @FXML
    private void estadisticasClicked(ActionEvent event) {
        try {
            Stage estageActual = new Stage();
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/entregableipc/view/menuestadisticas.fxml"));
            Parent root = (Parent) miCargador.load();
            miCargador.<MenuestadisticasController>getController().initStage(estageActual);
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

   

    
}
