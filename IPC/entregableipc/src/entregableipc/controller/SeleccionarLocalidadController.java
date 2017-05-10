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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modelo.Proyeccion;
import modelo.Reserva;
import modelo.Sala;

/**
 * FXML Controller class
 *
 * @author marcosesteve
 */
public class SeleccionarLocalidadController implements Initializable {
    Proyeccion actual;
    Stage primaryStage;
    Reserva reservaActual;
    int cont = 0;
    @FXML
    private VBox vbox;
    boolean[][] clicados = new boolean[18][12];
    Button comprar;
    Button cancelar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //initzialize se ejecuta antes que el initProyeccion
         
    }    

    void initStage(Stage estageActual ) {
        comprar = new Button("Comprar");
         comprar.setDisable(true);
         cancelar = new Button("Cancelar");
        primaryStage = estageActual;
        primaryStage.setTitle("Seleccionar localidades");
        
    }

    public void initProyeccion(Proyeccion seleccionada) {
        actual = seleccionada;
        //reservaActual = reserva;
        vbox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
         GridPane gridPane = new GridPane();
         gridPane.setPadding(new Insets(0, 20, 0, 20));
       gridPane.setGridLinesVisible(true);
         gridPane.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
         Label texto = new Label("Asiento");
        
         gridPane.getChildren().add(texto);
         GridPane.setHalignment(texto, HPos.CENTER);
        
         GridPane.setConstraints(texto, 0, 0);
         texto = new Label("Fila");
         GridPane.setHalignment(texto, HPos.CENTER);
         
         gridPane.getChildren().add(texto);
         GridPane.setConstraints(texto, 0, 1);
         
        for (int i = 1,k=2; i < 19; i++, k++) {
            texto = new Label(String.valueOf(i));
          
            gridPane.getChildren().add(texto);
            GridPane.setConstraints(texto, 0, k);
            GridPane.setHalignment(texto, HPos.CENTER);
        }
        for (int i = 1,k=1; i < 13; i++, k++) {
            texto = new Label(String.valueOf(i));
          
            gridPane.getChildren().add(texto);
            GridPane.setConstraints(texto, k, 0);
            GridPane.setHalignment(texto, HPos.CENTER);
        }
        
         for (int col =1 ; col < 13; col++) {
            for (int row = 2; row < 20; row++) {
            Button btn = new Button();
           
            btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
              if (actual.getSala().getLocalidades()[row-2][col-1].equals(Sala.localidad.libre)) {
                     btn.getStyleClass().add("boton_verde");
                }else{
                  btn.getStyleClass().remove("boton_verde");
                btn.getStyleClass().add("boton_ocupado");
                btn.setDisable(true);
                }
                
              
              int c = col;
              int r = row;
              btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                   
                    if (clicados[r-2][c-1]) {
                        cont--;
                        btn.getStyleClass().remove("boton_clicked");
                        clicados[r-2][c-1] = false;
                    }else{
                        cont++;
                        btn.getStyleClass().add("boton_clicked");
                        clicados[r-2][c-1] = true;
                    }
                }
            });
            btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                     if (cont == 0) {
                        comprar.setDisable(true);
                    }else{
                        comprar.setDisable(false);
                    }
                    btn.getStyleClass().add("boton_encima");
                }
            });
            btn.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    btn.getStyleClass().remove("boton_encima");
                   // btn.getStyleClass().clear();
                    btn.getStyleClass().add("boton_verde");
                }
            });
              
            gridPane.getChildren().add(btn);
            GridPane.setConstraints(btn, col, row);
            
            }
         }
        
           for (int j = 0; j < 13; j++) {
           ColumnConstraints cc = new ColumnConstraints();
           
           cc.setHgrow(Priority.ALWAYS);
           gridPane.getColumnConstraints().add(cc);
       }

       for (int j = 0; j < 20; j++) {
           RowConstraints rc = new RowConstraints();
           rc.setVgrow(Priority.ALWAYS);
           gridPane.getRowConstraints().add(rc);
       }
       
         
         HBox botones = new HBox();
         botones.setAlignment(Pos.BOTTOM_RIGHT);
         botones.setPadding(new Insets(20, 20, 0, 20));
        
         
         cancelar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            primaryStage.close();
            }
        });
         comprar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               
                    try {
                        Stage estageActual = new Stage();
                        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/entregableipc/view/imprimirEntrada.fxml"));
                        Parent root = (Parent) miCargador.load();
                        miCargador.<ImprimirEntradaController>getController().initStage(estageActual,seleccionada,clicados);
                        Scene scene = new Scene(root,900,750);
                        estageActual.setMinWidth(900);
                        estageActual.setMinHeight(750);
                        estageActual.setScene(scene);
                        estageActual.initModality(Modality.APPLICATION_MODAL);
                        estageActual.show();
                        estageActual.setOnCloseRequest(new EventHandler<WindowEvent>() {
                            @Override
                            public void handle(WindowEvent event) {
                                primaryStage.close();
                            }
                        });
                     } catch (IOException e) {
                            e.printStackTrace();
                     }
               
            }
        } );
         botones.getChildren().add(cancelar);
         botones.getChildren().add(comprar);
         
         botones.setSpacing(10);
         vbox.getChildren().add(gridPane);
         vbox.getChildren().add(botones);
        
    }
  
    
}

    