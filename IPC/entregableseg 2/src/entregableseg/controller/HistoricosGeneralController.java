/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entregableseg.controller;

import electionresults.model.ElectionResults;
import electionresults.persistence.io.DataAccessLayer;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author marcosesteve
 */
public class HistoricosGeneralController implements Initializable {
    Stage primaryStage;
    XYChart.Series[] series;
    @FXML
    private BarChart<String,Number> historico;
    XYChart.Series series1 = new XYChart.Series();
                XYChart.Series seriesAlicante = new XYChart.Series();
                XYChart.Series seriesCastellon = new XYChart.Series();
                XYChart.Series seriesValencia = new XYChart.Series();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void back(ActionEvent event) {
        primaryStage.close();
    }

    void initStage(Stage estageActual, Scene _scene) {
        primaryStage = estageActual;
        Task<Long> task = new Task<Long>() {
            @Override
            protected Long call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        _scene.setCursor(Cursor.WAIT);
                    }});
               List<ElectionResults> election = DataAccessLayer.getAllElectionResults();
                
                for (ElectionResults electionResults : election) {
                    double v = (double)(electionResults.getGlobalResults().getPollData().getVotes()) / electionResults.getGlobalResults().getPollData().getCensus();
                    series1.setName("Comunidad Valenciana");
                    series1.getData().add(new XYChart.Data(String.valueOf(electionResults.getYear()),(v*100)));
                    //alicante
                    double al = (double)(electionResults.getProvinceResults("Alicante").getPollData().getVotes()) / electionResults.getProvinceResults("Alicante").getPollData().getCensus();
                    seriesAlicante.setName("Alicante");
                    seriesAlicante.getData().add(new XYChart.Data(String.valueOf(electionResults.getYear()),(al*100)));
                    //valencia
                    double val = (double)(electionResults.getProvinceResults("Valencia").getPollData().getVotes()) / electionResults.getProvinceResults("Valencia").getPollData().getCensus();
                    seriesValencia.setName("Valencia");
                    seriesValencia.getData().add(new XYChart.Data(String.valueOf(electionResults.getYear()),(val*100)));
                    //castellon
                    double cas = (double)(electionResults.getProvinceResults("Castellón").getPollData().getVotes()) / electionResults.getProvinceResults("Castellón").getPollData().getCensus();
                    seriesCastellon.setName("Castellón");
                    seriesCastellon.getData().add(new XYChart.Data(String.valueOf(electionResults.getYear()),(cas*100)));
                    }
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        _scene.setCursor(Cursor.DEFAULT);
                    }});
            return null;
            }
        };
     task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
            historico.getData().addAll(series1,seriesAlicante,seriesValencia,seriesCastellon);
            }
        });
     new Thread(task).start();
        
        
    }
    
    
}
