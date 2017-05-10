/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pract7;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

/**
 *
 * @author marcosesteve
 */
public class FXMLDocumentController implements Initializable {
    final int NBRACKETS = 10;
        int hist[] = new int[NBRACKETS];
    private Label label;
    @FXML
    private LineChart<String, Number> linechart;
    @FXML
    private NumberAxis yvalues;
    @FXML
    private CategoryAxis xValues;
    @FXML
    private BarChart<String, Number> barchart;
    @FXML
    private NumberAxis barYValues;
    @FXML
    private CategoryAxis barXValues;
    @FXML
    private PieChart pieChart;
   
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        for (int i = 0; i < hist.length; i++) {
        hist[i] = 0;
        }
        for (int j = 0; j < 1000; j++) {
        double value = Math.random() * NBRACKETS;
        hist[(int) value]++;
        } 
        xValues.setLabel("Ranges");
        yvalues.setLabel("Frequencies");
        XYChart.Series<String,Number> series = new XYChart.Series();
        for (int i = 0; i < hist.length; i++)
        series.getData().add(new XYChart.Data<>(i + "-" + (i+1), hist[i]));
        series.setName("Histogram");
        linechart.getData().add(series);

        
    }   
    @FXML
    public void cargarBarChart(){
        barchart.getData().clear();
        barXValues.setLabel("Ranges");
        barYValues.setLabel("Frequencies");
        XYChart.Series series[] = new XYChart.Series[hist.length];
        
        XYChart.Series series1;
        
        for (int i = 0; i < hist.length; i++) {
            series1 =new XYChart.Series();
            series1.setName(i + "-" + (i+1));
            series1.getData().add(new XYChart.Data("", hist[i]));
            series[i] = series1;
            
        }
        
        barchart.getData().addAll(series);
       
    }
    public void cargarPieChart(){
    ObservableList<PieChart.Data> pieChartData =
    FXCollections.observableArrayList();
    pieChartData.clear();
    for (int i = 0; i < hist.length; i++) {
        pieChartData.add(new PieChart.Data(i + "-" + (i+1),hist[i] ));
    }
    pieChart.setData(pieChartData);
    
    }
    
}
