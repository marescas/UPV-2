/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entregableseg.controller;

import electionresults.model.ElectionResults;
import electionresults.model.Party;
import electionresults.model.PartyResults;
import electionresults.model.RegionResults;
import electionresults.persistence.io.DataAccessLayer;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author marcosesteve
 */
public class HistoricoEspecificoController implements Initializable {
    List<XYChart.Series> series = new ArrayList<>();
    XYChart.Series<String,Number> serie1,serie2;
    Scene scene;
    List<ElectionResults> result;
     GridPane gridpane = new GridPane();
     Map<String,Boolean> map;
Stage primaryStage;
    @FXML
    private StackedBarChart<String, Number> stackedBar;
    @FXML
    private LineChart<String, Number> lineChart;
    @FXML
    private HBox hbox;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stackedBar.setAnimated(false);
    }    

    @FXML
    private void salir(ActionEvent event) {
        primaryStage.close();
    }

    void initStage(Stage estageActual, Scene scene1) {
        scene = scene1;
       primaryStage = estageActual;
       gridpane.setDisable(true);
       Task<Long> task = new Task<Long>() {
            @Override
            protected Long call() throws Exception {
                
                 Platform.runLater(new Runnable() {
                    @Override public void run() {
                        scene.setCursor(Cursor.WAIT);
                    }});
                result = DataAccessLayer.getAllElectionResults();
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        scene.setCursor(Cursor.DEFAULT);
                    }});
                return null;
                }
        };
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                gridpane.setDisable(false);
            }
        });
        new Thread(task).start();
        
        gridpane.getStylesheets().add(getClass().getResource("botoncitos.css").toExternalForm());
        map= new HashMap<String,Boolean>();
        Party[] t = Party.values();
        int cont = 0,  cont2 = 0;
        boolean[] activado = new boolean[t.length];
        for (int i = 0, j = 0; i < t.length; i++) {
            map.put(t[i].getName(), Boolean.FALSE);
            
            BackgroundImage backgroundImage = new BackgroundImage( t[i].getLogo(), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);

        Button button = new Button("\t"+t[i].getName());
        
        button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println(button.getText().trim());
                    if (map.get(button.getText().trim())) {
                        map.put(button.getText().trim(), Boolean.FALSE);
                        
                        for (XYChart.Series sery : lineChart.getData()) {
                            if (sery.getName().equals(button.getText().trim())) {
                                lineChart.getData().remove(sery);
                                break;
                            }
                        }
                        for (XYChart.Series sery : stackedBar.getData()) {
                            if (sery.getName().equals(button.getText().trim())) {
                                stackedBar.getData().remove(sery);
                                break;
                            }
                        }
       
                        button.getStyleClass().clear();
                        button.setStyle(null);
                    }else{
                         map.put(button.getText().trim(), Boolean.TRUE);
                         agregarGrafica(button.getText().trim());
                    button.getStyleClass().add("btn");
                    }
                    
                           
                }
            });
        button.autosize();
        button.setPadding(new Insets(17));
        button.setBackground(background);
           gridpane.add(button, 0, i);
         
     
        }
        gridpane.setHgap(5);
        gridpane.setVgap(5);
        
        gridpane.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        
        hbox.getChildren().add(0,gridpane);
        HBox.setMargin(gridpane, new Insets(200, 10, 200, 10));
        
    }
    public void agregarGrafica(String partido){
        XYChart.Series<String, Number>[] series4 = new XYChart.Series[result.size()];
        
        Task t = new Task() {
        @Override
        protected Object call() throws Exception {
            int i =0;
            
             Platform.runLater(new Runnable() {
                    @Override public void run() {
                        scene.setCursor(Cursor.WAIT);
                    }});
             serie1 = new XYChart.Series();
             serie2 = new XYChart.Series();
                serie1.setName(partido);
                serie2.setName(partido);
            for  (ElectionResults res: result) {
                int sumaEscaños = 0, sumaVotos = 0;
                Party p =Party.getPartyByName(partido);
                List<String> partidAcron = p.getAcronyms();
                for (String string : partidAcron) {
                    
                    PartyResults r = res.getGlobalResults().getPartyResults(string);
                    if (r != null) {
                                sumaEscaños+=r.getSeats();
                                sumaVotos+=r.getVotes();
                       
                    }
                    
                }
                 serie1.getData().add(new XYChart.Data<>(String.valueOf(res.getYear()),sumaEscaños));
                       
                        serie2.getData().add(new XYChart.Data<>(String.valueOf(res.getYear()),sumaVotos));
                        //System.out.println(string +" "+ r.getSeats());
                
                
            }
             Platform.runLater(new Runnable() {
                    @Override public void run() {
                        scene.setCursor(Cursor.DEFAULT);
                    }});
 
         return null;   
        }
        };
        t.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                stackedBar.getData().addAll(serie1);
                lineChart.getData().addAll(serie2);
            }
        });
        new Thread(t).start();
    }
}
