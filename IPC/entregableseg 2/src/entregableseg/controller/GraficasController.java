/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entregableseg.controller;

import electionresults.model.ElectionResults;
import electionresults.model.PartyResults;
import electionresults.model.RegionResults;
import electionresults.persistence.io.DataAccessLayer;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author marcosesteve
 */
public class GraficasController implements Initializable {
    Map<String, PartyResults> resultadoPartido;
    Stage primaryStage;
    Integer año; 
    double v = 0;
            String provincia,  comarca;
    @FXML
    private PieChart piechart;
    @FXML
    private BarChart<String, Number> barchart;
    @FXML
    private Slider slider;
    @FXML
    private VBox vbox;
    @FXML
    private Label label1;
    @FXML
    private Label filtrotext;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           
    }    

    void initStage(Stage estageActual,Integer año1, String provincia1, String comarca1, boolean comunidad,Scene s) {
        List<XYChart.Series> series = new ArrayList<XYChart.Series>();
      
        primaryStage = estageActual;
        año = año1;
        provincia = provincia1;
        comarca = comarca1;
        barchart.setAnimated(false);
        if (comunidad) {
            //Dibujo PieChart si es referente a toda la comunidad
            //listo
            
            Task<Long> t1 =  new Task<Long>() {
                @Override
                protected Long call() throws Exception {
                    Platform.runLater(new Runnable() {
                @Override public void run() {
                s.setCursor(Cursor.WAIT);
                }});
                    ElectionResults result = DataAccessLayer.getElectionResults(año);
            resultadoPartido =result.getGlobalResults().getPartyResults();
            Platform.runLater(new Runnable() {
                @Override public void run() {
                    s.setCursor(Cursor.DEFAULT);
            }});
            return null;
                }
            };
            Thread th = new Thread(t1);
            t1.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    ObservableList<PieChart.Data> pieChartData =FXCollections.observableArrayList();
           XYChart.Series series1;
           for (Map.Entry<String, PartyResults> entry : resultadoPartido.entrySet()) {
                String key = entry.getKey();
                PartyResults value = entry.getValue();
                if (value.getPercentage()>v) {
                    series1 =new XYChart.Series();
                    series1.setName(key);  
                    series1.getData().add( new XYChart.Data("", value.getVotes()));
                    series.add(series1);
                }
                if (value.getSeats()>0) {
                    pieChartData.add(new PieChart.Data(key+"("+ String.valueOf(value.getSeats())+")", value.getSeats()));
                }
           }
           piechart.setData(pieChartData);
           XYChart.Series[] series2 = new XYChart.Series[series.size()];
                             for (int j = 0; j <series2.length; j++) {
                                series2[j] = series.get(j);
                            }
                             
                             barchart.getData().addAll(series2);
                             cargar();
                             
            slider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    DecimalFormat p = new DecimalFormat("%.2f");
                    filtrotext.setText( "Selecciona el porcentaje a filtrar: ("+String.format( "%.2f", newValue.doubleValue() )+")");
                    v = newValue.doubleValue();
                    List<XYChart.Series> series = new ArrayList<XYChart.Series>();
                series.clear();
                barchart.getData().clear();
                
                             XYChart.Series series1;
                             
                             for (Map.Entry<String, PartyResults> entry : resultadoPartido.entrySet()) {
                                String key = entry.getKey();
                                PartyResults value = entry.getValue();
                                 if (value.getPercentage()>v) {
                                     series1 =new XYChart.Series();
                                    series1.setName(key);
                                    series1.getData().add( new XYChart.Data("", value.getVotes()));
                                    
                                    series.add(series1);
                                 }



                        }
                             XYChart.Series[] series2 = new XYChart.Series[series.size()];
                             for (int j = 0; j <series2.length; j++) {
                                series2[j] = series.get(j);
                            }
                             
                             barchart.getData().addAll(series2);
                             cargar();
                             
                             
                }
            }); 
                }
            });
            th.start();
           
          
           
           
           
           
           
        }else 
        if ( comarca == null && !comunidad  ) {
            //listo
            Task<Long> task = new Task<Long>() {
                @Override
                protected Long call() throws Exception {
                       Platform.runLater(new Runnable() {
                @Override public void run() {
                s.setCursor(Cursor.WAIT);
                }});
                   ElectionResults result = DataAccessLayer.getElectionResults(año);
            resultadoPartido = result.getProvinceResults(provincia).getPartyResults();
               Platform.runLater(new Runnable() {
                    @Override public void run() {
                        s.setCursor(Cursor.DEFAULT);
                }});
               return null;
                }
            };
            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                 ObservableList<PieChart.Data> pieChartData =FXCollections.observableArrayList();
            XYChart.Series series1;
            for (Map.Entry<String, PartyResults> entry : resultadoPartido.entrySet()) {
                String key = entry.getKey();
                PartyResults value = entry.getValue();
                if (value.getPercentage()>v) {
                    series1 =new XYChart.Series();
                    series1.setName(key);
                    series1.getData().add( new XYChart.Data("", value.getVotes()));
                    series.add(series1);
                }
                if (value.getSeats()>0) {
                    pieChartData.add(new PieChart.Data(key+"("+ String.valueOf(value.getSeats())+")", value.getSeats()));
                }
                
            piechart.setData(pieChartData);
            
            
            } 
            XYChart.Series[] series2 = new XYChart.Series[series.size()];
                             for (int j = 0; j <series2.length; j++) {
                                series2[j] = series.get(j);
            }
                             
            barchart.getData().addAll(series2);
            cargar();
            
                             
            slider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    v = newValue.doubleValue();
                    List<XYChart.Series> series = new ArrayList<XYChart.Series>();
                series.clear();
                barchart.getData().clear();
                
                             XYChart.Series series1;
                             
                             for (Map.Entry<String, PartyResults> entry : resultadoPartido.entrySet()) {
                                String key = entry.getKey();
                                PartyResults value = entry.getValue();
                                 if (value.getPercentage()>v) {
                                     series1 =new XYChart.Series();
                                    series1.setName(key);
                                    series1.getData().add( new XYChart.Data("", value.getVotes()));
                                    
                                    series.add(series1);
                                 }



                        }
                             XYChart.Series[] series2 = new XYChart.Series[series.size()];
                             for (int j = 0; j <series2.length; j++) {
                                series2[j] = series.get(j);
                            }
                             cargar();
                             barchart.getData().addAll(series2);
                             cargar();
                }
            });
                }
            });
            Thread t1 = new Thread(task);
            t1.start();
           

            
        }else{
            vbox.getChildren().remove(label1);
            vbox.getChildren().remove(piechart);
            //seleccion año, provincia, comarca 
            //listo
            Task<Long> task = new Task<Long>() {
                @Override
                protected Long call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        s.setCursor(Cursor.WAIT);
                }});
                    ElectionResults result = DataAccessLayer.getElectionResults(año);
                    RegionResults resultaRegion = result.getRegionResults(comarca);
                    resultadoPartido = resultaRegion.getPartyResults();
                 Platform.runLater(new Runnable() {
                        @Override public void run() {
                            s.setCursor(Cursor.DEFAULT);
                }});
            
            return null;
                }
            };
            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                 series.clear();
                barchart.getData().clear();
                             XYChart.Series series1;
                             int i = 0;
                             for (Map.Entry<String, PartyResults> entry : resultadoPartido.entrySet()) {
                                String key = entry.getKey();
                                PartyResults value = entry.getValue();
                                 if (value.getPercentage()>v) {
                                     series1 =new XYChart.Series();
                                    series1.setName(key);
                                    series1.getData().add( new XYChart.Data("", value.getVotes()));
                                    
                                    series.add(series1);
                                 }

                        }
                             XYChart.Series[] series2 = new XYChart.Series[series.size()];
                             for (int j = 0; j <series2.length; j++) {
                                series2[j] = series.get(j);
                            }
                             cargar();
                             barchart.getData().addAll(series2);
                             cargar();
                             
            
     slider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    v = newValue.doubleValue();
                    List<XYChart.Series> series = new ArrayList<XYChart.Series>();
                series.clear();
                barchart.getData().clear();
                
                             XYChart.Series series1;
                             
                             for (Map.Entry<String, PartyResults> entry : resultadoPartido.entrySet()) {
                                String key = entry.getKey();
                                PartyResults value = entry.getValue();
                                 if (value.getPercentage()>v) {
                                     series1 =new XYChart.Series();
                                    series1.setName(key);
                                    series1.getData().add( new XYChart.Data("", value.getVotes()));
                                    
                                    series.add(series1);
                                 }



                        }
                             XYChart.Series[] series2 = new XYChart.Series[series.size()];
                             for (int j = 0; j <series2.length; j++) {
                                series2[j] = series.get(j);
                            }
                             
                             barchart.getData().addAll(series2);
                             cargar();
                             
                }
            }); 
        
                
                }
            });
            Thread th = new Thread(task);
            th.start();  
            
        }
        
    }

    @FXML
    private void salir(ActionEvent event) {
        primaryStage.close();
    }
    public void cargar(){
      for(Series<String, Number> series2:barchart.getData()) {
                    for (Data<String,Number> data : series2.getData()) {
               StackPane  bar2 = (StackPane) data.getNode();
               Text dataText = new Text(data.getYValue().intValue()+"");
               bar2.getChildren().add(dataText);
    
            }
     
        }
    }
   
    
}
