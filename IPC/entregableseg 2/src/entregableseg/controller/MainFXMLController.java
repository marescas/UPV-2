/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entregableseg.controller;

import electionresults.model.ElectionResults;
import electionresults.model.ProvinceInfo;
import electionresults.persistence.io.DataAccessLayer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author marcosesteve
 */
public class MainFXMLController implements Initializable {
    ElectionResults resultados;
   
    ProvinceInfo provinciaInfo;
    @FXML
    private ComboBox<Integer> año;
    @FXML
    private CheckBox comunidad;
    @FXML
    private ComboBox<String> provincia;
    @FXML
    private ComboBox<String> comarca;
    @FXML
    private Button verResultados;
    @FXML
    private ImageView imagen;
    Task<Long> task;
    @FXML
    private GridPane gridPane;
    @FXML
    private VBox vbox;
    
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ProgressBar p = new ProgressBar();
        p.setVisible(false);
        vbox.getChildren().add(1, p);
        
       comunidad.selectedProperty().addListener(new ChangeListener<Boolean>() {
           @Override
           public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
               if (newValue) {
                 
                 imagen.setImage(new Image("/entregableseg/imagenes/normal.png"));
               }}
             
           
       });
       provincia.disableProperty().bind(año.valueProperty().isNull() 
               .or(comunidad.pressedProperty().isNotEqualTo(comunidad.selectedProperty())));
       comunidad.disableProperty().bind(año.valueProperty().isNull());
       comarca.disableProperty().bind(provincia.valueProperty().isNull() 
               .or(comunidad.pressedProperty().isNotEqualTo(comunidad.selectedProperty())));
       verResultados.disableProperty().bind( comunidad.pressedProperty().isEqualTo(comunidad.selectedProperty())
               .and(provincia.valueProperty().isNull()));
      
        año.setItems( FXCollections.observableArrayList(DataAccessLayer.getElectionYears()));
        año.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
               
                task = new Task<Long>() {
                    @Override
                    protected Long call() throws Exception {
                       Platform.runLater(new Runnable() {
                    @Override public void run() {
                        
                        p.setVisible(true);
                        p.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
                        
                        gridPane.setDisable(true);
                        
                    }});
                   resultados  = DataAccessLayer.getElectionResults(newValue);
                   
                   return null;
                    }
                }; 
                task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        p.setVisible(false);
                        gridPane.setDisable(false);
                    }
                });
                new Thread(task).start();
            }
        });
        
       provincia.setItems(FXCollections.observableArrayList("Castellón","Valencia","Alicante"));
       provincia.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equals("Valencia")) {
                    imagen.setImage(new Image("/entregableseg/imagenes/valencia.png"));
                }else if(newValue.equals("Castellón")){
                    imagen.setImage(new Image("/entregableseg/imagenes/castellon.png"));
                }else if(newValue.equals("Alicante")){
                    imagen.setImage(new Image("/entregableseg/imagenes/alicante.png"));
                }
               
                try{
                provinciaInfo = resultados.getProvinces().get(newValue);
                comarca.setItems(FXCollections.observableArrayList(provinciaInfo.getRegions()));
                }catch(NullPointerException e){
                }
            }
            
        });
       
        
    }    

    @FXML
    private void resultados(ActionEvent event) {
         try {
             
            Stage estageActual = new Stage();
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/entregableseg/view/graficas.fxml"));
            Parent root = (Parent) miCargador.load();
            Scene scene = new Scene(root);
             if (comunidad.isSelected()) {
                 miCargador.<GraficasController>getController().initStage(
                    estageActual, año.getSelectionModel().getSelectedItem(),
                    provincia.getSelectionModel().getSelectedItem(),
                    comarca.getSelectionModel().getSelectedItem(),true,scene);
             }else{
             miCargador.<GraficasController>getController().initStage(
                    estageActual, año.getSelectionModel().getSelectedItem(),
                    provincia.getSelectionModel().getSelectedItem(),
                    comarca.getSelectionModel().getSelectedItem(),false,scene);
             }
            
            
            estageActual.setMinWidth(750);
            estageActual.setMinHeight(600);
            estageActual.setScene(scene);
            estageActual.initModality(Modality.APPLICATION_MODAL);
            estageActual.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
