/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica32;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.util.converter.NumberStringConverter;

/**
 *
 * @author marcosesteve
 */
public class FXMLDocumentController implements Initializable {
    
    
    @FXML
    private Label conversionRate;
    @FXML
    private TextField textoInput;
    @FXML
    private TextField textoOutput;
    @FXML
    private Slider valorDato;
    @FXML
    private CheckBox convertirAutomaticamente;
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conversionRate.textProperty().bindBidirectional(valorDato.valueProperty(), new NumberStringConverter());
        
    }    

    @FXML
    private void convert(ActionEvent event) {
        try {
           
                double ratio = Double.parseDouble(conversionRate.getText().replace(",","."));
                double numero =Double.parseDouble(textoInput.getText());
                double multiplicacion  = ratio * numero;
                textoOutput.setText(multiplicacion+"");
            
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eres tonto");
            alert.setContentText("Introduce un número correcto, informático de pacotilla.");
            alert.showAndWait();
        }
    }

    @FXML
    private void clear(ActionEvent event) {
        textoOutput.setText("");
        textoInput.setText("");
    }

    @FXML
    private void conversionAutomatica(ActionEvent event) {
        if (convertirAutomaticamente.isSelected()) {
            textoInput.textProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                     try {
           
                        double ratio = Double.parseDouble(conversionRate.getText().replace(",","."));
                        double numero =Double.parseDouble(newValue.toString());
                        double multiplicacion  = ratio * numero;
                        textoOutput.setText(multiplicacion+"");
            
                    } catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Eres tonto");
                        alert.setContentText("Introduce un número correcto, informático de pacotilla.");
                        alert.showAndWait();
                    }
                }
            });
            valorDato.valueProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                 try {
           
                        double ratio = Double.parseDouble(newValue.toString().replace(",","."));
                        double numero =Double.parseDouble(textoInput.getText());
                        double multiplicacion  = ratio * numero;
                        textoOutput.setText(multiplicacion+"");
            
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eres tonto");
                    alert.setContentText("Introduce un número correcto, informático de pacotilla.");
                    alert.showAndWait();
                }
                }
            });
            
        }
    }

    @FXML
    private void valor(DragEvent event) {
        
    }
    
}
    